import java.io.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.statistics.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;


public class DataPlotting {

	DefaultStatisticalCategoryDataset bar;
	XYSeriesCollection xy;
	CategoryPlot plot;
	JFreeChart chart, chart1;

	private Processing2 Process2;

	public DataPlotting (Processing2 Process2) {
		this.Process2 = Process2;
	}


	/*This method sets up */
	public void statisticalBarGraph () {

		int rows = Process2.getPillar().size();
		int columns = 3;

		Object [][] graph = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {
			graph [i][0] = Process2.getPillar().get(i);
			graph [i][1] = Process2.getMean().get(i);
			graph [i][2] = Process2.getStandard_deviation().get(i);
		}

		bar = new DefaultStatisticalCategoryDataset (); 

		double avg = 0;
		double stdv = 0.0;
		String cat = "";
		String title = "";

		for (int i = 0; i < rows; i++) { 
			avg = (double) graph [i][1];
			stdv = (double) graph [i][2];
			cat = "Pillar";
			title = Integer.toString((int) graph[i][0]); 
			bar.add (avg, stdv, cat, title);
		}

		graphLayout();
	}


	/*This method is for setting up the bar chart layout*/
	public void graphLayout () {

		plot = new CategoryPlot(
				bar,
				new CategoryAxis("PillarID"),
				new NumberAxis("Average Force (pN)"),
				new StatisticalBarRenderer());


		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.LIGHT_GRAY);

		chart = new JFreeChart("Pillar Data",JFreeChart.DEFAULT_TITLE_FONT,plot,true);

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

		saveGraph();
		scatterGraph();
	}

	
	public void scatterGraph () {

		int rows = Process2.getPillar().size();
		int columns = 3;

		Object [][] graph = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {
			graph [i][0] = Process2.getPillar().get(i);
			graph [i][1] = Process2.getMean().get(i);
			graph [i][2] = Process2.getStandard_deviation().get(i);
		}

		XYSeriesCollection dataset = new XYSeriesCollection ();
		XYSeries series = new XYSeries ("Pillars");

		for (int i =0; i< rows; i++ ) {

			int x = (int) graph [i][0];
			double y = (double) graph [i][1];
			series.add (x,y);
		}
		
		dataset.addSeries(series);

		chart = ChartFactory.createScatterPlot("Pillars", "Pillar ID", "Force (pN)", dataset);
		XYPlot plot = (XYPlot)chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		chart.setBackgroundPaint(Color.lightGray);
		chart.removeLegend();

		NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

		ValueAxis axisX = plot.getDomainAxis();
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		renderer.setSeriesPaint(0, gradientpaint);
		Shape diamond = ShapeUtilities.createDiamond(1f);
		XYDotRenderer seagul = new XYDotRenderer ();
		seagul.setSeriesShape (0, diamond);
	
		

		ChartFrame frame = new ChartFrame("I'm having a breakdown", chart);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	
	public void saveGraph () {

		try {

			ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 1200, 800);
		} 

		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well");
	}	
}