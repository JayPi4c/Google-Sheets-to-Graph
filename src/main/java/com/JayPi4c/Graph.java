package com.JayPi4c;

import java.util.ArrayList;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graph {

	public static JFreeChart createChart(ArrayList<DataPair> pairs) {

		DefaultCategoryDataset[] datasets = createDataset(pairs);

		CategoryPlot plot = new CategoryPlot();
		CategoryItemRenderer lineRenderer = new LineAndShapeRenderer();
		plot.setDataset(0, datasets[1]);
		plot.setRenderer(0, lineRenderer);

		CategoryItemRenderer lineRenderer1 = new LineAndShapeRenderer();
		plot.setDataset(1, datasets[2]);
		plot.setRenderer(1, lineRenderer1);

		CategoryItemRenderer barRenderer = new BarRenderer();
		plot.setDataset(2, datasets[0]);
		plot.setRenderer(2, barRenderer);

		plot.setDomainAxis(new CategoryAxis("Zeit"));
		plot.setRangeAxis(new NumberAxis("Ausgaben"));
		JFreeChart chart = new JFreeChart(plot);
		chart.setTitle("Ausgaben im Laufe der Zeit");
		return chart;
	}

	private static DefaultCategoryDataset[] createDataset(ArrayList<DataPair> pairs) {
		String series1 = "Ausgaben";
		String series2 = "durchschnittliche Ausgaben";
		String series3 = "tägliches Budget";
		DefaultCategoryDataset datasets[] = new DefaultCategoryDataset[3];
		for (int i = 0; i < datasets.length; i++)
			datasets[i] = new DefaultCategoryDataset();

		for (DataPair pair : pairs) {
			String s = pair.datePart;
			datasets[0].addValue(pair.costs, series1, s);
			datasets[1].addValue(pair.averageCosts, series2, s);
			datasets[2].addValue(8.22, series3, s);
		}
		return datasets;
	}
}
