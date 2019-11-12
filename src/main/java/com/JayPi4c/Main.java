package com.JayPi4c;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

//http://zetcode.com/java/jfreechart/
public class Main {
	public static void main(String... args) throws IOException, GeneralSecurityException {
		final String spreadsheetId = "17Dy3kRz5EAhK6_zimq8lOMbBz6OsEdqxmDdHdx_PX1s";
		final String range = "A:D";
		Sheets service = SpreadsheetConnector.getService();

		HashMap<Number, Number> map = getValues(service, spreadsheetId, range);
		ArrayList<DataPair> pairs = new ArrayList<DataPair>();
		TreeMap<Number, Number> sortedMap = new TreeMap<Number, Number>(map);
		for (Map.Entry<Number, Number> entry : sortedMap.entrySet())
			pairs.add(new DataPair((Long) entry.getKey(), (Double) entry.getValue()));

		double previousCosts = 0;
		for (int i = 0; i < pairs.size(); i++) {
			DataPair pair = pairs.get(i);
			pair.addToSummedCosts(previousCosts);
			previousCosts = pair.summedCosts;
			pairs.get(i).print();
		}

		/*
		 * for (Map.Entry<Number, Number> entry : sortedMap.entrySet()) {
		 * System.out.printf("%s, %s\n", new Date((Long)
		 * entry.getKey()).toString().substring(4, 10), entry.getValue()); }
		 */

	}

	public static HashMap<Number, Number> getValues(Sheets service, String spreadsheetId, String range)
			throws IOException {
		ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();

		List<List<Object>> values = response.getValues();

		HashMap<Number, Number> map = new HashMap<Number, Number>();
		if (values == null || values.isEmpty()) {
			System.out.println("No data found.");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			for (List<Object> row : values) {
				String key = (String) row.get(0);
				Date d = null;
				try {
					d = sdf.parse(key);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				long keyVal = d.getTime();
				Double val = Double.parseDouble(((String) row.get(1)).replaceAll("\\U+20AC", "").replace(',', '.'));
				if (map.containsKey(keyVal)) {
					Double value = (Double) map.get(keyVal);
					map.put(keyVal, value + val);
				} else {
					map.put(keyVal, val);
				}

			}
		}
		return map;
	}

	/*
	 * 
	 * public static void main(String... args) { /* // create the data double[][]
	 * data = new double[][] { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
	 * 15, 16, 17, 18, 19, 20 } };
	 * 
	 * // create the dateset CategoryDataset dataset =
	 * DatasetUtils.createCategoryDataset(new String[] { "Year" }, new String[] {
	 * "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008",
	 * "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017",
	 * "2018", "2019", "2020" }, data); // build the chart JFreeChart chart =
	 * ChartFactory.createAreaChart("Age over time", "Time", "Age", dataset,
	 * PlotOrientation.VERTICAL, false, false, false); // customize the plot
	 * CategoryPlot plot = (CategoryPlot) chart.getPlot();
	 * plot.setForegroundAlpha(0.3f); AreaRenderer renderer = (AreaRenderer)
	 * plot.getRenderer(); renderer.setEndType(AreaRendererEndType.TRUNCATE);
	 * 
	 * try { // save as image ChartUtils.saveChartAsPNG(new File("line_chart.png"),
	 * chart, 800, 400); } catch (IOException e) { e.printStackTrace(); }
	 */
	/* } */
}
