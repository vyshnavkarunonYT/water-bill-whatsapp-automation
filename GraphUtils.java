package utils;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class GraphUtils {
	public static CategoryDataset createDataSet(List<String[]>list) {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i =0; i<list.size(); i++) {
			dataset.addValue(Double.parseDouble(list.get(i)[1]), list.get(i)[2], list.get(i)[0]);
		}
		return dataset;
	}
	
	
	public static void BarChart( String ApplicationTitle, String chartTitle, CategoryDataset dataset) {
		
		JFreeChart chart = ChartFactory.createBarChart(chartTitle,
				"Month","Consumption in Litres", dataset, PlotOrientation.VERTICAL, true, true, true);
			CategoryPlot plot = chart.getCategoryPlot();
			plot.setRangeGridlinePaint(Color.black);
			plot.setBackgroundPaint(Color.white);
			plot.setOutlinePaint(Color.black);
			ChartFrame chartFrame = new ChartFrame(ApplicationTitle, chart, true );
			chartFrame.setVisible(true);
			chartFrame.pack();
		
	}
}

