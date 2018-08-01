import java.io.*;
import java.awt.*;
import java.util.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.renderer.category.*;


public class DataPlotting {

	DefaultStatisticalCategoryDataset bar;
	CategoryPlot plot;

	private Processing2 Process2;

	public DataPlotting (Processing2 Process2) {
		this.Process2 = Process2;
	}

	
	/*This method sets up */
	public void graph () {

		ArrayList <Double> average = new ArrayList <Double> ();
		ArrayList <Double> standard_deviation = new ArrayList <Double> ();
		ArrayList <Integer> pillar_index = new ArrayList <Integer> (); 
		
		pillar_index = Process2.getPillar();
		average = Process2.getMean();
		standard_deviation = Process2.getStandard_deviation();
		
		bar = new DefaultStatisticalCategoryDataset (); 

		double avg = 0;
		double stdv = 0.0;
		String cat = "";
		String title = "";

		for (int i = 0; i < average.size(); i++) {
			avg = average.get(i);
			stdv = standard_deviation.get(i);
			cat = "Pillar";
			title = Integer.toString(pillar_index.get(i)); 

			bar.add (avg, stdv, cat, title);
		}
		
		graphLayout();
	}
	
	public void graphLayout () {

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
		
		NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));
		
		CategoryAxis axisX = plot.getDomainAxis();
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
//		axisX.setCategoryMargin(0.1);
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);
		

		StatisticalBarRenderer renderer = (StatisticalBarRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		renderer.setSeriesPaint(0, gradientpaint);
		renderer.setDrawBarOutline(true);
		renderer.setSeriesOutlinePaint(0, Color.black);
		renderer.setSeriesOutlineStroke(0, new BasicStroke(1));
		renderer.setErrorIndicatorPaint(Color.black);
		renderer.setMaximumBarWidth(10);
		
		
		ChartFrame frame = new ChartFrame("I'm having a breakdown", chart);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		try {

			ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 1200, 800);
		} 
		
		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well");
	}	
}