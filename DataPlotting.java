import java.io.*;
import java.text.NumberFormat;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.data.statistics.*;
//import org.jfree.chart.ChartFrame.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.renderer.xy.XYErrorRenderer; 


public class DataPlotting {

	DefaultStatisticalCategoryDataset bar;
	CategoryPlot plot;


	public DataPlotting () {
	}


	public void graph () {

		bar = new DefaultStatisticalCategoryDataset (); 

		double [] mean = {107537.5, 97510, 113415.7, 131559.2, 83028.2};
		double [] sd = {72662.6, 73122.6, 56713.9, 71039.5, 59956};
		int [] id = {2559, 2563, 2566, 2570, 2607};

		double avg = 0;
		double stdv = 0.0;
		String cat = "";
		String title = "";

		for (int i = 0; i < mean.length; i++) {
			avg = mean [i];
			stdv = sd [i];
			cat = "Pillar";
			title = Integer.toString(id [i]); 

			bar.add (avg, stdv, cat, title);
		}


		plot = new CategoryPlot(
				bar,
				new CategoryAxis("PillarID"),
				new NumberAxis("Average Force (pN)"),
				new StatisticalBarRenderer());


		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.LIGHT_GRAY);

		JFreeChart chart = new JFreeChart("Pillar Data",JFreeChart.DEFAULT_TITLE_FONT,plot,true);

		plot = chart.getCategoryPlot(); 
		chart.removeLegend();
		chart.getPlot().setBackgroundPaint(Color.white );

		Axis axisY = plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

		Axis axisX = plot.getDomainAxis();
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));

		StatisticalBarRenderer renderer = (StatisticalBarRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		renderer.setSeriesPaint(0, gradientpaint);
		renderer.setDrawBarOutline(true);
		renderer.setSeriesOutlinePaint(0, Color.black);
		renderer.setSeriesOutlineStroke(0, new BasicStroke(1));
		renderer.setErrorIndicatorPaint(Color.black);
		
	
		ChartFrame frame = new ChartFrame("I'm having a breakdown", chart);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		try {

			ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);
		} 
		
		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well");
	}	
}