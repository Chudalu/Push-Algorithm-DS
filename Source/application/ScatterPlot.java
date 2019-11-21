package application;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * This is the scatter plot class and it handles the generation of the scatter plot graph.
 * @author Chudalu Ezenwafor
 *@version 2018.3.26
 */

public class ScatterPlot {
	XYSeriesCollection data;
	XYSeries series;
	JFreeChart chart;
	ChartFrame frame;
	//any object created, is assigned plot data for the scatter plot
	public ScatterPlot(double[][] datasett) {
		addDataset(datasett);
		chart = ChartFactory.createScatterPlot("Probability Drop Model of Push Algorithm",
				"Probability", "Time", data);
		frame = new ChartFrame("Scatter Plot", chart);
		frame.pack();
		frame.setVisible(true);
	}
	//the data for plot is added to data series for scatter plot
	public XYDataset addDataset(double[][] dataset) {
		data = new XYSeriesCollection();
		series = new XYSeries("Probabilitydata");
		
		for (int i = 0; i<20; i++) {
			double prob = dataset[0][i];
			double time = dataset[1][i];
			series.add(prob, time);
		}
		data.addSeries(series);
		
		return data;
	}

}
