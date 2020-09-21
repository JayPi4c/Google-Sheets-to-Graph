package com.JayPi4c;

import java.awt.Rectangle;
import java.io.File;
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

import org.jfree.chart.JFreeChart;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import com.JayPi4c.utils.DataPair;
import com.JayPi4c.utils.Graph;
import com.JayPi4c.utils.PropertyHelper;
import com.JayPi4c.utils.SpreadsheetConnector;
import com.JayPi4c.view.Frame;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

//http://zetcode.com/java/jfreechart/
public class Main {
	public static void main(String... args) throws IOException, GeneralSecurityException {
		PropertyHelper.init();

		String spreadsheetId;
		if (args.length > 0)
			spreadsheetId = args[0];
		else if ((spreadsheetId = PropertyHelper.getProperty("google.spreadsheet.ID")) == null)
			exitByError("No spreadsheetID given", 1);

		String range;

		if (args.length > 1)
			range = args[1];
		else if ((range = PropertyHelper.getProperty("google.spreadsheet.range")) == null)
			exitByError("No spreadsheet range given", 1);

		String name = null;
		if (args.length > 2)
			name = args[2];
		else
			name = PropertyHelper.getProperty("google.spreadsheet.name");
		if (name != null)
			range = name + "!" + range;

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
			// pairs.get(i).print();
		}

		JFreeChart chart = Graph.createChart(pairs);
		File chartFile;
		try {
			// ChartUtils.saveChartAsPNG(new File("chart.png"), chart, 800, 400);

			SVGGraphics2D g2 = new SVGGraphics2D(800, 400);
			Rectangle r = new Rectangle(0, 0, 800, 400);
			chart.draw(g2, r);
			SVGUtils.writeToSVG(chartFile = new File("./chart.svg"), g2.getSVGElement());
			new Frame(chartFile);

			// File chartFile = new File("./chart.svg");
			// new Frame(chartFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("finished successfully");

		// for (Map.Entry<Number, Number> entry : sortedMap.entrySet()) {
		// System.out.printf("%s, %s\n", new Date((Long)
		// entry.getKey()).toString().substring(4, 10),
		// entry.getValue());
		// }

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
				// removes the € sign from the cells value
				Double val = Double.parseDouble(((String) row.get(1)).replaceAll("\\u20AC", "").replace(',', '.'));
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

	static void exitByError(String error, int exitcode) {
		System.err.println(error);
		System.err.println("exiting...");
		System.exit(exitcode);
	}
}
