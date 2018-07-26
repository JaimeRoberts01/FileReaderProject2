import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import java.io.File;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;


public class DataPlotting {
	
	public DataPlotting () {
	}
	
	public void graph () {
	 // Create a simple pie chart

    DefaultPieDataset pieDataset = new DefaultPieDataset();
    pieDataset.setValue("A", new Integer(75));
    pieDataset.setValue("B", new Integer(10));
    pieDataset.setValue("C", new Integer(10));
    pieDataset.setValue("D", new Integer(5));

     JFreeChart chart = ChartFactory.createPieChart
    ("CSC408 Mark Distribution", // Title
           pieDataset, // Dataset
           true, // Show legend
           true, // Use tooltips
           false // Configure chart to generate URLs?
    );

     try {

    ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 500, 300);

    } catch (Exception e) {

          

            System.out.println("Problem occurred creating chart.jska " + e.getMessage());

    }

          System.out.println("I did well");

    }

}
