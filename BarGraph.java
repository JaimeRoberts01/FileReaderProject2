import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Spring;
import javax.swing.SpringLayout;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;


public class BarGraph implements ActionListener {
	

	private CategoryPlot plot;
	private JFreeChart barChart;
	private Object [][] barChartArray;
	private DefaultStatisticalCategoryDataset bar;
	
	private Processing2 Process2;
	
	
	BarGraph (Processing2 Process2) {
		this.Process2 = Process2;
	}
	

	/**This method calls the allPillarAllFrames method and creates a new array using that data. 
	 * An ID is passed to the method so that it can be used to build the line graph.
	 */
	
	public void barChartData () { 
		
		int rows = Process2.getMultipillar().size();
		int columns = 3;

		barChartArray = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {
			barChartArray [i][0] = Process2.getMultipillar().get(i);
			barChartArray [i][1] = Process2.getMean().get(i);
			barChartArray [i][2] = Process2.getStandard_deviation().get(i);
		}

		bar = new DefaultStatisticalCategoryDataset (); 

		double avg = 0;
		double stdv = 0.0;
		String cat = "";
		String title = "";

		for (int i = 0; i < rows; i++) { 
			avg = (double) barChartArray [i][1];
			stdv = (double) barChartArray [i][2];
			cat = "Pillar";
			title = Integer.toString((int) barChartArray[i][0]); //(String) barChartArray [i][0];
			
			bar.add (avg, stdv, cat, title);
		}
		
		createBarChart();
	}
	
	
	/**This method creates the bar-chart. It uses the ID to run through the array in scatterPlotData
	 * and pulls out the data for a particular pillar. This is then plotted on a formatted graph plot
	 * that can be saved to file.
	 */
	
	public void createBarChart () {
		
		plot = new CategoryPlot(
				bar,
				new CategoryAxis("PillarID"),
				new NumberAxis("Average Force (pN)"),
				new StatisticalBarRenderer());

		barChart = new JFreeChart("Average Forces",JFreeChart.DEFAULT_TITLE_FONT,plot,true);
		barChart.removeLegend();
		barChart.setBackgroundPaint(Color.white);
		
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.lightGray);
		plot = barChart.getCategoryPlot(); 
		
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
		
		frameLayout();
	}
	
	
/**This method lays out the graph frame*/
	
	public void frameLayout () {

		ChartFrame frame = new ChartFrame("Multipillar Data", barChart);

		JButton Button1 = new JButton ("Save");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setOpaque(true);
		Button1.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		frame.add(Button1);
		
		SpringLayout layout = new SpringLayout ();
		frame.setLayout(layout);
		SpringLayout.Constraints button1Cons = layout.getConstraints(Button1);
		button1Cons.setX(Spring.constant(5));
		button1Cons.setY(Spring.constant(5));

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	
	/**FileChooser allows files to be saved in a particular directory and with a give name.
	 * The fileName is passed to the FileWriter method for saving the data.
	 */

	public void fileChooser () {

		JFileChooser JFC = new JFileChooser ();
		String fileName = "";
		int saveVal = JFC.showSaveDialog(null);

		if (saveVal == JFileChooser.APPROVE_OPTION) {

			File savedFile = JFC.getSelectedFile();

			if (savedFile.exists()) {
				int response = JOptionPane.showConfirmDialog (null, "FILE ALREADY EXISTS. REPLACE?", 
						"Select an Option...", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					fileName = savedFile.toString();
					saveChart (fileName);
				}

				else if (response == JOptionPane.NO_OPTION) {
					response = JOptionPane.CLOSED_OPTION;
				}
			}

			else {
				fileName = savedFile.toString();
				saveChart (fileName);	
			}
		}
	}
	
	
	/**This method saves the graph to file*/

	public void saveChart (String fileName) {

		try {

			ChartUtilities.saveChartAsJPEG(new File(fileName), barChart, 1200, 800);
		} 

		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well");
	}


	/**ActionPerformed method for the save button*/
	
	@Override
	public void actionPerformed(ActionEvent e) {

		this.fileChooser();
	}	

}
