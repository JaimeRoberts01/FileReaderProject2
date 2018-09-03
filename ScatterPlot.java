import java.io.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;


/**A class that defines a scatter-plot of the data for 
 * the whole dataset and the getByFrame functionality.
 */

public class ScatterPlot implements ActionListener {


	/*Instance Variables*/
	private Object [][] scatterPlotArray;
	private JFreeChart scatterPlot;
	private XYSeriesCollection dataset;
	private XYSeries series;
	private XYPlot plot;
	private JFrame frame;
	private JPanel panel;
	private JButton button1, button2, button3, button4;
	private JTextField tf1;

	private DataProcessing DataProcessing;


	/*Constructor*/
	public ScatterPlot (DataProcessing DataProcessing) {
		this.DataProcessing = DataProcessing;
	}


	/**This method retrieves data from the AllFrames method and 
	 * creates a dataset array of the whole data
	 */

	public void scatterPlotData_AllFrames () { // All data

		scatterPlotArray = new Object [DataProcessing.getMean().size()][3]; 
		int [] ID = null;

		try {

			ID = new int [DataProcessing.getPillar().size()];

			for (int i = 0; i< DataProcessing.getPillar().size(); i++) {
				ID [i] = DataProcessing.getPillar().get(i);
			}

			for (int i = 0; i < DataProcessing.getMean().size(); i++) {

				scatterPlotArray [i][0] = ID [i]; 
				scatterPlotArray [i][1] = DataProcessing.getMean().get(i);
				scatterPlotArray [i][2] = DataProcessing.getStandard_deviation().get(i);
			}
		}

		catch (NullPointerException NPE) {

			String message = "Invalid input";
			StringWriter stackTraceWriter = new StringWriter();
			NPE.printStackTrace(new PrintWriter(stackTraceWriter));
			String stackTrace = stackTraceWriter.toString();

			LogFile log = new LogFile ();
			log.writeToLog(message, stackTrace);
		}

		String identifier = "AllData";
		createScatterPlot (ID, identifier);
	}
	
	
	/**This method retrieves data from the byFrame method and creates a dataset array of the frame 
	 * data. An ID is passed from Processing2 and turned into an ID array for tracking the frame.
	 * @param ID - the frame index.
	 */

	public void scatterPlotData_byFrame (int frameID) { // getByFrame

		scatterPlotArray = new Object [DataProcessing.getOutputDataByFrame().size()][3]; 
		int [] ID = null;

		for (int i = 0; i < DataProcessing.getOutputDataByFrame().size(); i++) {

			scatterPlotArray [i] = (DataProcessing.getOutputDataByFrame().get(i)).toString().split(",");
		}

		ID = new int [1];

		for (int i = 0; i < ID.length; i++) { 

			ID [i] = frameID;	
		}

		String identifier = "ByFrame";
		createScatterPlot (ID, identifier);
	}


	/**This method creates the scatter-plot. It uses the ID to run through the array in scatterPlotData
	 * and pulls out the data for a particular pillar. This is then plotted on a formatted graph plot.
	 * @param ID - the ID of the pillar or frame.
	 * @param identifier - the source of the data e.g. allFrame or byFrame. 
	 */

	public void createScatterPlot (int[] ID, String identifier) {

		dataset = new XYSeriesCollection ();
		series = new XYSeries ("Pillars");

		for (int i =0; i< scatterPlotArray.length; i++ ) {

			if (identifier.equals("ByFrame")) {
				int x = Integer.parseInt((String) scatterPlotArray [i][1]);
				double y = Double.parseDouble((String) scatterPlotArray [i][2]); 
				series.add (x,y);
			}

			else if (identifier.equals("AllData")) {
				int x = (int) scatterPlotArray [i][0];
				double y = (double) scatterPlotArray [i][1]; 
				series.add (x,y);
			}
		}

		dataset.addSeries(series);
		formatScatterPlot (ID, dataset, identifier);
	}


	/**This method formats the scatter-plot*/

	public void formatScatterPlot (int[] ID, XYSeriesCollection dataset, String identifier) {		

		if (identifier.equals("ByFrame")) {

			String frameID = Arrays.toString(ID);
			frameID = frameID.replace("[", ""); frameID = frameID.replace("]","");
			scatterPlot = ChartFactory.createScatterPlot("Pillar Forces: Frame " + frameID, "Pillar ID", "Force (pN)", dataset);
		}

		else {

			scatterPlot = ChartFactory.createScatterPlot("Pillar Forces", "Pillar ID", "Force (pN)", dataset);
		}

		/*Plot appearance*/
		scatterPlot.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
		scatterPlot.setBackgroundPaint(Color.white);
		scatterPlot.removeLegend();
		plot = (XYPlot) scatterPlot.getPlot();
		plot.setBackgroundPaint(Color.getHSBColor(0.0f, 0.0f, 0.90f));

		/*Y axis formatting*/
		NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f)); 

		/*X axis formatting*/
		ValueAxis axisX = plot.getDomainAxis();
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);
		axisX = scatterPlot.getXYPlot().getDomainAxis();
		DecimalFormat format = new DecimalFormat("####");
		((NumberAxis) axisX).setNumberFormatOverride(format);
		axisX.setAutoTickUnitSelection(true);

		/*Series formatting*/
		XYLineAndShapeRenderer render = (XYLineAndShapeRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		render.setSeriesPaint(0, gradientpaint);

		frameLayout(ID, identifier);
	}


	/**This method lays out the graph frame
	 * @param ID - the pillar indices.
	 * @param identifier - the source of the data.
	 */

	public void frameLayout (int[] ID, String identifier) {

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Scatterplot Data - Pillar Forces");
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());

		ChartPanel chartpanel = new ChartPanel(scatterPlot);
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
		if (identifier.contentEquals("AllData")) {

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
	
	
	/**This method filters the dataset depending on force value. Notifiers update the scatter-plot*/

	public void filterValues () {

		Object [][] scatterPlotArray = new Object [this.scatterPlotArray.length][3];

		int filterValue = 0; // The value below which datapoints are removed.

		if (tf1.getText().equals("")) {
			filterValue = 0;
		}

		else {

			filterValue = Integer.parseInt(tf1.getText());
		}

		for (int i = 0; i < this.scatterPlotArray.length; i++) {

			double forces = (double) this.scatterPlotArray [i][1];

			if (forces>filterValue) {

				scatterPlotArray [i] = this.scatterPlotArray [i]; 
			}	
		}

		dataset = new XYSeriesCollection (); 
		series = new XYSeries ("Pillars");

		for (int i = 0; i < this.scatterPlotArray.length; i++) { 

			if (scatterPlotArray [i][1] != null) {

				int x = (int) scatterPlotArray [i][0];
				double y =  (double) scatterPlotArray [i][1];
				series.add (x,y);
			}
		}

		dataset.addSeries(series);
		plot.setDataset(dataset);
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
					savePlot (fileName);
				}

				else if (response == JOptionPane.NO_OPTION) {
					response = JOptionPane.CLOSED_OPTION;
				}
			}

			else {
				fileName = savedFile.toString();
				savePlot (fileName);	
			}
		}
	}


	/**This method saves the graph to file
	 * @param fileName - the name of the file.*/

	public void savePlot (String fileName) {

		try {

			ChartUtilities.saveChartAsJPEG(new File(fileName), scatterPlot, 1200, 800);
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