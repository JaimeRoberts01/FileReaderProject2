import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;

import java.awt.Color;
import java.io.File;
import org.jfree.chart.ChartFactory;
//import org.jfree.data.category.CategoryDataset; 
//import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.chart.plot.*;


public class DataPlotting {
	
	public DataPlotting () {
	}
	
	public void graph () {
	 // Create a simple pie chart

//    DefaultPieDataset pieDataset = new DefaultPieDataset();
//    pieDataset.setValue("A", new Integer(75));
//    pieDataset.setValue("B", new Integer(10));
//    pieDataset.setValue("C", new Integer(10));
//    pieDataset.setValue("D", new Integer(5));	
		
//		// create a bar chart
//		
//	    DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
//	    barDataset.addValue(new Integer(75), "A", "");
//	    barDataset.addValue(new Integer(10), "B", "");
//	    barDataset.setValue(new Integer(10), "C", "");
//	    barDataset.setValue(new Integer(5), "D", "");
//
//     JFreeChart chart = ChartFactory.createBarChart
//    		 ("CSC408 Mark Distribution", // Title
//           "Category", "Percentage", // Axis labels
//           barDataset, // Dataset
//           PlotOrientation.VERTICAL, // Orientation
//           true, // Show legend
//           true, // Use tooltips
//           false // Configure chart to generate URLs?
//    );
//     
//    CategoryPlot plot = chart.getCategoryPlot();
//    BarRenderer renderer = (BarRenderer) plot.getRenderer();
//    renderer.setSeriesPaint(0, Color.RED.darker());
//    renderer.setSeriesPaint(1, Color.GREEN.darker());
//    renderer.setSeriesPaint(2, Color.BLUE.brighter());
//    renderer.setSeriesPaint(3, Color.MAGENTA.brighter());
    
    
	// Statistical bar chart - not quite right.
    DefaultStatisticalCategoryDataset bar = new DefaultStatisticalCategoryDataset ();  
    bar.add(10.0, 2.4, "A", "2559"); // example data
    bar.add(15.0, 4.4, "A", "2623");
    bar.add(13.0, 2.1, "A", "2685");
    bar.add(7.0, 1.3, "A", "2930");
    // mean, sd, series, dunno doesn't match with graph so isn't pillar id
    // got it. Set them all to A puts them in the same category but they end up the same colour
    
    CategoryPlot plot = new CategoryPlot(
    		bar,
    		new CategoryAxis("PillarID"),
    		new NumberAxis("Average Force (pN)"),
    		new StatisticalBarRenderer());
    
    		plot.setOrientation(PlotOrientation.VERTICAL);
    		plot.setBackgroundPaint(Color.LIGHT_GRAY);
    	
    		
    		 JFreeChart chart = new JFreeChart("Pillar Data",JFreeChart.DEFAULT_TITLE_FONT,plot,true);
    		 //This is for the title
    		 
    		 
    		 plot = chart.getCategoryPlot();
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
