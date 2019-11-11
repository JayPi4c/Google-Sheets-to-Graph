package com.JayPi4c;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AreaRendererEndType;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtils;

//http://zetcode.com/java/jfreechart/
public class Main {
	public static void main(String... args) {

		// create the data
		double[][] data = new double[][] {
				{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 } };

		// create the dateset
		CategoryDataset dataset = DatasetUtils.createCategoryDataset(new String[] { "Year" },
				new String[] { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010",
						"2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020" },
				data);
		// build the chart
		JFreeChart chart = ChartFactory.createAreaChart("Age over time", "Time", "Age", dataset,
				PlotOrientation.VERTICAL, false, false, false);
		// customize the plot
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setForegroundAlpha(0.3f);
		AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
		renderer.setEndType(AreaRendererEndType.TRUNCATE);

		try {
			// save as image
			ChartUtils.saveChartAsPNG(new File("line_chart.png"), chart, 800, 400);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
