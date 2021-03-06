package com.JayPi4c;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class Graph {

	// Quellen:
	// https://github.com/ngadde/playground/blob/master/com.iis.sample1/src/main/java/demo/OverlaidXYPlotDemo1.java
	// https://stackoverflow.com/questions/35876221/jfreechart-real-time-line-graph-with-two-lines
	// http://www.jfree.org/forum/viewtopic.php?t=13651
	// https://www.tutorialspoint.com/jfreechart/jfreechart_timeseries_chart.htm
	// https://stackoverflow.com/questions/21427762/jfreechart-set-line-colors-for-xy-chart-4-series-2-datasets-dual-axes

	public static JFreeChart createChart(ArrayList<DataPair> pairs) {
		XYDataset data1 = createDataset(pairs);
		XYItemRenderer renderer1 = new StandardXYItemRenderer();
		renderer1.setSeriesPaint(0, Color.RED);
		renderer1.setSeriesPaint(1, Color.GREEN);
		DateAxis domainAxis = new DateAxis(Messages.getString("chart.axis.name.x"));
		domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis rangeAxis = new NumberAxis(Messages.getString("chart.axis.name.y"));
		XYPlot plot = new XYPlot(data1, domainAxis, rangeAxis, renderer1);

		XYDataset data2 = createCostsDataset(pairs);
		XYItemRenderer renderer2 = new XYBarRenderer();
		renderer2.setSeriesPaint(0, new Color(135, 206, 250));
		plot.setDataset(1, data2);
		plot.setRenderer(1, renderer2);

		return new JFreeChart(plot);
	}

	private static XYDataset createDataset(ArrayList<DataPair> pairs) {
		final TimeSeries series1 = new TimeSeries(Messages.getString("chart.timeseries.daily"));
		final TimeSeries series2 = new TimeSeries(Messages.getString("chart.timeseries.average"));
		for (DataPair p : pairs) {
			Day d = new Day(new Date(p.unixtimeStamp));
			series1.add(d, 8.53);
			series2.add(d, p.averageCosts);
		}
		TimeSeriesCollection tsc = new TimeSeriesCollection();
		tsc.addSeries(series1);
		tsc.addSeries(series2);
		tsc.setXPosition(TimePeriodAnchor.MIDDLE);
		return tsc;
	}

	private static XYDataset createCostsDataset(ArrayList<DataPair> pairs) {
		final TimeSeries series = new TimeSeries(Messages.getString("chart.timeseries.absolute"));
		for (DataPair p : pairs) {
			Day d = new Day(new Date(p.unixtimeStamp));
			series.add(d, p.costs);
		}
		return new TimeSeriesCollection(series);
	}

}
