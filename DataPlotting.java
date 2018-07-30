import java.io.*;
import java.awt.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.renderer.category.*; 


public class DataPlotting {

	DefaultStatisticalCategoryDataset bar;
	DefaultStatisticalCategoryDataset series;
	CategoryPlot plot;
	

	public DataPlotting () {
	}


	public void graph () {

		//		DefaultStatisticalCategoryDataset bar = new DefaultStatisticalCategoryDataset ();  
		//		bar.add(10.0, 2.4, "A", "2559"); // example data
		//		bar.add(15.0, 4.4, "A", "2623");
		//		bar.add(13.0, 2.1, "A", "2685");
		//		bar.add(7.0, 1.3, "A", "2930");

		bar = new DefaultStatisticalCategoryDataset (); 
		
	

		int [] mean = {1, 2, 3, 4, 8};
		double [] sd = {0.1, 0.2, 0.3, 0.4, 0.5};
		int avg = 0;
		double stdv = 0.0;
		String cat = "";
		String title = "";
		//Object series = "";
		
		
		for (int i = 0; i < mean.length; i++) {
			avg = mean [i];
			stdv = sd [i];
			cat = "A";
			title = "A pillar";
			bar.add (avg, stdv, cat, title);
		}

//		for (int j = 0; j < mean.length; j++) {
//			bar.add (avg, stdv, cat, title);
//		}
//	}

	plot = new CategoryPlot(
			bar,
			new CategoryAxis("PillarID"),
			new NumberAxis("Average Force (pN)"),
			new StatisticalBarRenderer());


	plot.setOrientation(PlotOrientation.VERTICAL);
	plot.setBackgroundPaint(Color.LIGHT_GRAY);

	JFreeChart chart = new JFreeChart("Pillar Data",JFreeChart.DEFAULT_TITLE_FONT,plot,true);
	//This is for the title

	plot = chart.getCategoryPlot(); // This is for faffing with the bar colours.
	BarRenderer renderer = (BarRenderer) plot.getRenderer();
	renderer.setSeriesPaint(0, Color.RED.darker());
	renderer.setSeriesPaint(1, Color.GREEN.darker());
	renderer.setSeriesPaint(2, Color.BLUE.darker());
	renderer.setSeriesPaint(3, Color.MAGENTA.darker());
	chart.removeLegend();
	// renderer.setSeriesItemLabelsVisible(0, true);

	try {

		ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);

	} catch (Exception e) {

		System.out.println("Problem occurred creating chart.jska " + e.getMessage());
	}

	System.out.println("I did well");
}	
}