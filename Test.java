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


public class Test  implements ActionListener {


	private CategoryPlot plot;
	private JFreeChart barChart;
	private Object [][] barChartArray;
	private DefaultStatisticalCategoryDataset bar;

	private Processing2 Process2;

	private JButton Button1, Button2, Button3, Button4;
	private JTextField TF1;
	private JFrame frame;


	public Test (Processing2 Process2) {
		this.Process2 = Process2;
	}


	/**This method calls the allPillarAllFrames method and creates a new array using that data. 
	 * An ID is passed to the method so that it can be used to build the line graph.
	 */

	public void barChartData () { 

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

			double avg = 0;
			double stdv = 0.0;
			String cat = "";
			String title = "";


			for (int i = 0; i < rows; i++) { 

				avg = (double) barChartArray [i][1]; //newBarChartArray [i][1]; //barChartArray [i][1];
				stdv = (double) barChartArray [i][2]; //newBarChartArray [i][2]; //barChartArray [i][2];
				cat = "Pillar";
				title = Integer.toString((int) barChartArray[i][0]); //newBarChartArray[i][0]); //barChartArray[i][0]); //(String) barChartArray [i][0];

				bar.add (avg, stdv, cat, title);
			}
		}
		catch (NullPointerException NPE) {
			System.err.println("Invalid input - barchartdata");
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

		barChart = new JFreeChart("Average Forces",plot);
		barChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
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
		
		frame = new JFrame ("Test");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Average Forces Data - All Data");
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
		
		Button4 = new JButton ("Close");
		Button4.setPreferredSize(new Dimension(125,23));
		Button4.setOpaque(true);
		Button4.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button4.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button4.setBorder(BorderFactory.createLineBorder(Color.black));
		Button4.addActionListener (this);
		frame.add(Button4);
		
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
		
		Button2 = new JButton ("Reset");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setOpaque(true);
		Button2.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		frame.add(Button2);
	}

	
	public void filterValues () {

		int rows = barChartArray.length;
		int columns = 3;

		Object [][] barChartArray = new Object [rows][columns];

		int filterValue = 0;

		if (TF1.getText().equals("")) {
			filterValue = 0;
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
		System.out.println("NLC L: " + barChartArray.length);

		bar = new DefaultStatisticalCategoryDataset (); 

		double avg = 0;
		double stdv = 0.0;
		String cat = "";
		String title = "";


		for (int i = 0; i < rows; i++) { 

			if (barChartArray [i][1] != null) {
				avg = (double) barChartArray [i][1]; 
				stdv = (double) barChartArray [i][2]; 
				cat = "Pillar";
				title = Integer.toString((int) barChartArray[i][0]); 

				bar.add (avg, stdv, cat, title);
			}
		}

		plot.setDataset(bar);
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

		if (e.getSource()== Button1) {
			fileChooser();
		}
		if (e.getSource() == Button2) {

			TF1.setText("");
			filterValues ();

		}
		if (e.getSource() == Button3) {
			filterValues ();
		}
		if (e.getSource() == Button4) {
			frame.dispose ();
		}
	}	

}
