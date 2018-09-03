import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;

	
/**A class that defines a bar-graph of the data for 
 * the whole dataset and MultiPillar functionality.
 */

public class BarGraph implements ActionListener {


	/*Instance Variables*/
	private CategoryPlot plot;
	private JFreeChart barChart;
	private Object [][] barChartArray;
	private DefaultStatisticalCategoryDataset bar;
	private JFrame frame;
	private JPanel panel;
	private JButton button1, button2, button3, button4;
	private JTextField tf1;

	private Processing2 Process2;


	/*Constructor*/
	public BarGraph (Processing2 Process2) {
		this.Process2 = Process2;
	}


	/**This method retrieves data from the AllFrames method and creates a dataset array. It uses
	 * pillar indices from Processing2 as well as average and standard deviation values.
	 */

	public void barChartData_AllData () { // All data

		barChartArray = new Object [Process2.getPillar().size()][3];

		try {

			for (int i = 0; i < Process2.getPillar().size(); i++) {
				barChartArray [i][0] = Process2.getPillar().get(i);
				barChartArray [i][1] = Process2.getMean().get(i);
				barChartArray [i][2] = Process2.getStandard_deviation().get(i);
			}

			bar = new DefaultStatisticalCategoryDataset (); 

			for (int i = 0; i < Process2.getPillar().size(); i++) { 

				double avg = (double) barChartArray [i][1]; 
				double stdv = (double) barChartArray [i][2]; 
				String cat = "Pillar";
				String title = Integer.toString((int) barChartArray[i][0]);

				bar.add (avg, stdv, cat, title);
			}
		}

		catch (NullPointerException NPE) {

			String message = "Invalid input";
			StringWriter stackTraceWriter = new StringWriter();
			NPE.printStackTrace(new PrintWriter(stackTraceWriter));
			String stackTrace = stackTraceWriter.toString();

			LogFile log = new LogFile ();
			log.writeToLog(message, stackTrace);
			// Calls the log file if there is an error.
		}

		String identifier = "AllData";
		createBarChart(identifier);
	}

	
	/**This method retrieves data from the Multipillar method and creates a dataset array. It uses
	 * pillar indices from Processing2 that are passed from MultipillarInput as well as average 
	 * and standard deviation values generated in Processing2.
	 */

	public void barChartData_Multipillar () { // MultiPillar

		barChartArray = new Object [Process2.getValues().length][3];

		try {

			for (int i = 0; i < Process2.getValues().length; i++) {
				barChartArray [i][0] = Process2.getValues() [i];
				barChartArray [i][1] = Process2.getMean().get(i);
				barChartArray [i][2] = Process2.getStandard_deviation().get(i);
			}

			bar = new DefaultStatisticalCategoryDataset (); 

			for (int i = 0; i < Process2.getValues().length; i++) { 
				double avg = (double) barChartArray [i][1];
				double stdv = (double) barChartArray [i][2];
				String cat = "Pillar"; // can have different categories.
				String title = (String) barChartArray [i][0];

				bar.add (avg, stdv, cat, title);
			}
		}

		catch (NullPointerException NPE) {

			String message = "Invalid input";
			StringWriter stackTraceWriter = new StringWriter();
			NPE.printStackTrace(new PrintWriter(stackTraceWriter));
			String stackTrace = stackTraceWriter.toString();

			LogFile log = new LogFile ();
			log.writeToLog(message, stackTrace);
			// Calls the log file if there is an error.
		}

		String identifier = "Multipillar";
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
		frame.setLayout(new BorderLayout());

		ChartPanel chartpanel = new ChartPanel(barChart);
		chartpanel.setBackground(Color.white);
		chartpanel.setPreferredSize(new Dimension(700,446));
		chartpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		chartpanel.setVisible(true);
		frame.add(chartpanel, BorderLayout.CENTER);

		panel = new JPanel ();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		frame.add(panel, BorderLayout.SOUTH);

		button1 = new JButton ("Save");
		button1.setPreferredSize(new Dimension(125,23));
		button1.setOpaque(true);
		button1.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button1.setBorder(BorderFactory.createLineBorder(Color.black));
		button1.addActionListener (this);
		panel.add(button1);

		button2 = new JButton ("Close");
		button2.setPreferredSize(new Dimension(125,23));
		button2.setOpaque(true);
		button2.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button2.setBorder(BorderFactory.createLineBorder(Color.black));
		button2.addActionListener (this);
		panel.add(button2);

		/*Different buttons depending on the source of the data*/
		if (identifier.equals("AllData")) {

			JSeparator S1 = new JSeparator(SwingConstants.VERTICAL);
			S1.setPreferredSize(new Dimension(10,23));
			S1.setBackground(Color.DARK_GRAY);
			panel.add(S1);

			JLabel L1 = new JLabel ("Set filter value:");
			L1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			panel.add(L1);

			tf1 = new JTextField (5);
			tf1.addActionListener (this);
			tf1.setBorder(BorderFactory.createLineBorder(Color.black));
			tf1.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
			tf1.setPreferredSize(new Dimension(5,23));
			tf1.setEnabled(true);
			panel.add(tf1);	

			button3 = new JButton ("Apply");
			button3.setPreferredSize(new Dimension(125,23));
			button3.setOpaque(true);
			button3.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
			button3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			button3.setBorder(BorderFactory.createLineBorder(Color.black));
			button3.addActionListener (this);
			panel.add(button3);

			button4 = new JButton ("Reset");
			button4.setPreferredSize(new Dimension(125,23));
			button4.setOpaque(true);
			button4.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
			button4.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			button4.setBorder(BorderFactory.createLineBorder(Color.black));
			button4.addActionListener (this);
			panel.add(button4);
		}
	}


	/**This method filters the dataset depending on force value. Notifiers update the bar-chart*/

	public void filterValues () {

		Object [][] barChartArray = new Object [this.barChartArray.length][3];

		int filterValue = 0; // The value below which datapoints are removed.

		try {

			if (tf1.getText().equals("")) {
				filterValue = 0;
			}

			else if (tf1.getText().contains(",")) {
				filterValue = Integer.parseInt(tf1.getText().replaceAll(",",""));
			}

			else {
				filterValue = Integer.parseInt(tf1.getText());		
			}

			for (int i = 0; i < this.barChartArray.length; i++) {

				double forces = (double) this.barChartArray [i][1];

				if (forces>filterValue) {

					barChartArray [i] = this.barChartArray [i]; 
				}
			}

			bar = new DefaultStatisticalCategoryDataset (); 

			for (int i = 0; i < this.barChartArray.length; i++) { 

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

			String message = "Invalid input";
			StringWriter stackTraceWriter = new StringWriter();
			NFE.printStackTrace(new PrintWriter(stackTraceWriter));
			String stackTrace = stackTraceWriter.toString();

			LogFile log = new LogFile ();
			log.writeToLog(message, stackTrace);
			// Calls the log file if there is an error.
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

			String message = "A problem occurred creating chart";

			LogFile log = new LogFile ();
			log.writeToLog(message, null);
			// Calls the log file if there is an error.
		}
	}


	/**ActionPerformed methods for the individual buttons*/

	@Override
	public void actionPerformed(ActionEvent e) {


		if (e.getSource() == button1) {

			fileChooser();
		}

		if (e.getSource() == button2) {

			frame.dispose();
		}

		if (e.getSource() == button3) {

			filterValues ();
		}

		if (e.getSource() == button4) {

			tf1.setText("");
			filterValues ();
		}
	}	
}
