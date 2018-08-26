import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;


public class BarGraph implements ActionListener {

	
	/*Instance Variables*/
	private CategoryPlot plot;
	private JFreeChart barChart;
	private Object [][] barChartArray;
	private DefaultStatisticalCategoryDataset bar;
	private JFrame frame;
	private JButton Button1, Button2, Button3, Button4;
	private JTextField TF1;

	private Processing2 Process2;


	/*Constructor*/
	public BarGraph (Processing2 Process2) {
		this.Process2 = Process2;
	}


	/**This method retrieves data from the Multipillar method and creates a dataset array. It uses
	 * pillar indices from Processing2 that are passed from MultipillarInput as well as average 
	 * and standard deviation values generated in Processing2.
	 */

	public void barChartData_Multipillar () { 

		int rows = Process2.getValues().length;
		int columns = 3;

		barChartArray = new Object [rows][columns];

		try {

			for (int i = 0; i < rows; i++) {
				barChartArray [i][0] = Process2.getValues() [i];
				barChartArray [i][1] = Process2.getMean().get(i);
				barChartArray [i][2] = Process2.getStandard_deviation().get(i);
			}

			bar = new DefaultStatisticalCategoryDataset (); 

			for (int i = 0; i < rows; i++) { 
				double avg = (double) barChartArray [i][1];
				double stdv = (double) barChartArray [i][2];
				String cat = "Pillar";
				String title = (String) barChartArray [i][0];

				bar.add (avg, stdv, cat, title);
			}
		}
		
		catch (NullPointerException NPE) {
			System.err.println("Invalid input");
			NPE.printStackTrace();
		}

		String identifier = "Multipillar";
		createBarChart(identifier);
	}


	/**This method retrieves data from the AllFrames method and creates a dataset array. It uses
	 * pillar indices from Processing2 as well as average and standard deviation values.
	 */
	
	public void barChartData_AllData () {

		int rows = Process2.getPillar().size();	
		int columns = 3;

		barChartArray = new Object [rows][columns];

		try {

			for (int i = 0; i < rows; i++) {
				barChartArray [i][0] = Process2.getPillar().get(i);
				barChartArray [i][1] = Process2.getMean().get(i);
				barChartArray [i][2] = Process2.getStandard_deviation().get(i);
			}

			bar = new DefaultStatisticalCategoryDataset (); 

			for (int i = 0; i < rows; i++) { 

				double avg = (double) barChartArray [i][1]; 
				double stdv = (double) barChartArray [i][2]; 
				String cat = "Pillar";
				String title = Integer.toString((int) barChartArray[i][0]);

				bar.add (avg, stdv, cat, title);
			}
		}
		
		catch (NullPointerException NPE) {
			System.err.println("Invalid input - barchartdata");
			NPE.printStackTrace();
		}

		String identifier = "AllData";
		createBarChart(identifier);
	}


	/**This method creates the barchart. It uses the ID to run through the array in scatterPlotData and 
	 * pulls out the data for a particular pillar. This is then plotted on a formatted graph plot. A
	 * String identifier is passed to identify the source of the data.
	 */

	public void createBarChart (String identifier) {

		plot = new CategoryPlot(
				bar,
				new CategoryAxis("PillarID"),
				new NumberAxis("Average Force (pN)"),
				new StatisticalBarRenderer());

		barChart = new JFreeChart("Average Forces",plot);
		barChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
		barChart.removeLegend();
		barChart.setBackgroundPaint(Color.white);

		/*Plot appearance*/
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setBackgroundPaint(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		plot = barChart.getCategoryPlot(); 

		/*Y axis formatting*/
		NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

		/*X axis formatting*/
		CategoryAxis axisX = plot.getDomainAxis();
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);

		/*Series formatting*/
		StatisticalBarRenderer renderer = (StatisticalBarRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		renderer.setSeriesPaint(0, gradientpaint);
		renderer.setDrawBarOutline(true);
		renderer.setSeriesOutlinePaint(0, Color.black);
		renderer.setSeriesOutlineStroke(0, new BasicStroke(1));
		renderer.setErrorIndicatorPaint(Color.black);
		renderer.setMaximumBarWidth(10);

		frameLayout(identifier);
	}


	/**This method lays out the graph frame
	 * @param identifier - the source of the data.*/

	public void frameLayout (String identifier) {

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Barchart Data - Average Forces");
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

		ChartPanel chartpanel = new ChartPanel(barChart);
		chartpanel.setBackground(Color.white);
		chartpanel.setPreferredSize(new Dimension(700,446));
		chartpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		chartpanel.setVisible(true);
		frame.add(chartpanel);

		Button1 = new JButton ("Save");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setOpaque(true);
		Button1.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		frame.add(Button1);

		Button2 = new JButton ("Close");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setOpaque(true);
		Button2.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		frame.add(Button2);

		/*Different buttons depending on the source of the data*/
		if (identifier.contentEquals("AllData")) {
			
			JSeparator S1 = new JSeparator(SwingConstants.VERTICAL);
			S1.setPreferredSize(new Dimension(10,23));
			S1.setBackground(Color.DARK_GRAY);
			frame.add(S1);

			JLabel L1 = new JLabel ("Set filter value:");
			L1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			frame.add(L1);

			TF1 = new JTextField (5);
			TF1.addActionListener (this);
			TF1.setBorder(BorderFactory.createLineBorder(Color.black));
			TF1.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
			TF1.setPreferredSize(new Dimension(5,23));
			TF1.setEnabled(true);
			frame.add(TF1);	

			Button3 = new JButton ("Apply");
			Button3.setPreferredSize(new Dimension(125,23));
			Button3.setOpaque(true);
			Button3.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
			Button3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			Button3.setBorder(BorderFactory.createLineBorder(Color.black));
			Button3.addActionListener (this);
			frame.add(Button3);

			Button4 = new JButton ("Reset");
			Button4.setPreferredSize(new Dimension(125,23));
			Button4.setOpaque(true);
			Button4.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
			Button4.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			Button4.setBorder(BorderFactory.createLineBorder(Color.black));
			Button4.addActionListener (this);
			frame.add(Button4);
		}
	}


	/**This method filters the dataset depending on force value. Notifiers update the bar-chart*/
	
	public void filterValues () {

		int rows = barChartArray.length;
		int columns = 3;

		Object [][] barChartArray = new Object [rows][columns];

		int filterValue = 0;
		
		try {
			
		if (TF1.getText().equals("")) {
			filterValue = 0;
		}
		
		else if (TF1.getText().contains(",")) {
			filterValue = Integer.parseInt(TF1.getText().replaceAll(",",""));
		}
		
		else {
			filterValue = Integer.parseInt(TF1.getText());		
		}

		for (int i = 0; i < rows; i++) {

			double forces = (double) this.barChartArray [i][1];

			if (forces>filterValue) {

				barChartArray [i] = this.barChartArray [i]; 
				System.out.println("NLC: " + Arrays.toString(barChartArray[i]));
			}
		}

		bar = new DefaultStatisticalCategoryDataset (); 

		for (int i = 0; i < rows; i++) { 

			if (barChartArray [i][1] != null) {
				double avg = (double) barChartArray [i][1]; 
				double stdv = (double) barChartArray [i][2]; 
				String cat = "Pillar";
				String title = Integer.toString((int) barChartArray[i][0]); 

				bar.add (avg, stdv, cat, title);
			}
		}

		plot.setDataset(bar);
		}
		
		catch (NumberFormatException NFE) {
			System.err.println("Invalid input");
			//NFE.printStackTrace();
		}
	}


	/**FileChooser - save location*/

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


	/**This method saves the graph to file
	 * @param fileName - the name of the file.
	 */

	public void saveChart (String fileName) {

		try {

			ChartUtilities.saveChartAsJPEG(new File(fileName), barChart, 1200, 800);
		} 

		catch (Exception e) {	

			System.out.println("Problem occurred creating chart.jska ");
			e.printStackTrace();
		}
	}


	/**ActionPerformed methods for the individual buttons*/

	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getSource() == Button1) {
			
			fileChooser();
		}

		if (e.getSource() == Button2) {
			
			frame.dispose();
		}

		if (e.getSource() == Button3) {
			
			filterValues ();
		}

		if (e.getSource() == Button4) {
			
			TF1.setText("");
			filterValues ();
		}
	}	
}