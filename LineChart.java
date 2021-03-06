import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;


/**A class that defines a line-chart of the data for the whole 
 * dataset, getByPillar and MultiPillar functionality.
 */

public class LineChart implements ActionListener {


	/*Instance Variables*/
	private JFreeChart lineChart;
	private XYPlot plot;
	private Object [][] lineChartArray;
	private JFrame frame;
	private JPanel panel;
	private JButton button1, button2, button3;

	private DataProcessing DataProcessing;


	/*Constructor*/
	public LineChart (DataProcessing DataProcessing) {
		this.DataProcessing = DataProcessing;	
	}


	/**This method retrieves data from the allPillarsAllFrames method and creates a dataset array.
	 * IDs are created from the pillar ArrayList and turned into an ID array for tracking all the
	 * individual pillar indexes when creating XYSeries data.
	 */

	public void lineChartData_AllData () { // All data

		lineChartArray = new Object [DataProcessing.getDataByPillarFrame().size()][3]; 
		int [] ID = null;

		try {

			for (int i = 0; i < DataProcessing.getDataByPillarFrame().size(); i++) {

				lineChartArray [i] = (DataProcessing.getDataByPillarFrame().get(i)).toString().split(","); 
			}	

			ID = new int [DataProcessing.getPillar().size()];

			for (int i = 0; i< DataProcessing.getPillar().size(); i++) {

				ID [i] = DataProcessing.getPillar().get(i);
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
		createLineChart (ID, identifier);
	}


	/**This method retrieves data from the byPillar method and creates a dataset array. An ID is
	 * passed from Processing2 and turned into an ID array for tracking the pillar index across
	 * the individual frames.
	 * @param ID - the pillar index.
	 */

	public void lineChartData_byPillar (int pillarID) { //getByPillar

		lineChartArray = new Object [DataProcessing.getOutputDataByPillar().size()][3]; 
		int [] ID = null;

		try {

			for (int i = 0; i < DataProcessing.getOutputDataByPillar().size(); i++) {

				lineChartArray [i] = (DataProcessing.getOutputDataByPillar().get(i)).toString().split(",");
			}	
			
			ID = new int [1];

			for (int i = 0; i < ID.length; i++) { 

				ID [i] = pillarID;	
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

		String identifier = "ByPillar";
		createLineChart (ID, identifier);
	}


	/**This method retrieves data for the Multipillar method and creates a dataset array.
	 * IDs are passed to it from MultipillarInput in the form of an ID array for tracking
	 * all the individual pillar indexes when creating XYSeries data.
	 * @param IDs - the pillar indices.
	 */

	public void lineChartData_Multipillar (Object[] IDs) { // Multipillar

		lineChartArray = new Object [DataProcessing.getOutputMultipillar().size()][3]; 
		int [] ID = null;

		try {

			for (int i = 0; i < DataProcessing.getOutputMultipillar().size(); i++) {

				lineChartArray [i] = (DataProcessing.getOutputMultipillar().get(i)).toString().split(",");
			}

			ID = new int [IDs.length];

			for (int i = 0; i < IDs.length; i++) { 

				ID [i] = Integer.parseInt((String) IDs [i]);	
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
		createLineChart (ID, identifier);	
	}


	/**This method creates the series data from the lineChart array
	 * @param ID - the pillar index.
	 * @param identifier - identifies the source of the data.
	 */

	public void createLineChart (int [] ID, String identifier) {

		XYSeriesCollection dataset = new XYSeriesCollection ();

		for (int i = 0; i< ID.length; i++) {

			XYSeries series = new XYSeries (ID[i]);
			int inputID = ID[i];

			for (int j =0; j< lineChartArray.length; j++) {

				int pillar = Integer.parseInt((String) lineChartArray [j][1]);

				if (inputID == pillar) {

					int x = Integer.parseInt((String) lineChartArray [j][0]); // frame
					double y = Double.parseDouble((String) lineChartArray [j][2]); // force
					series.add(x, y);
				}
			}

			dataset.addSeries(series);
		}

		formatLineChart(ID, dataset, identifier);
	}


	/**This method formats the LineChart.
	 * @param ID - pillar indices.
	 * @param dataset - the data.
	 * @param identifier - the source of the data.
	 */

	public void formatLineChart (int[] ID, XYSeriesCollection dataset, String identifier) {

		/*Different plot formatting depending on the source of the data*/
		if (identifier.equals("Multipillar")) {

			lineChart = ChartFactory.createXYLineChart("Forces Over Time", "Frame ID", "Force (pN)", dataset);
			lineChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
			plot =  lineChart.getXYPlot();
			plot.setBackgroundPaint(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		}

		else if (identifier.equals("ByPillar")) {

			String pillarID = Arrays.toString(ID);
			pillarID = pillarID.replace("[", ""); pillarID = pillarID.replace("]","");
			lineChart = ChartFactory.createXYLineChart("Forces Over Time: Pillar " + pillarID, "Frame ID", "Force (pN)", dataset);
			lineChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
			lineChart.removeLegend();
			plot =  lineChart.getXYPlot();
			plot.setBackgroundPaint(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		}

		else if (identifier.equals("AllData")) {

			lineChart = ChartFactory.createXYLineChart("Forces Over Time", "Frame ID", "Force (pN)", dataset);
			lineChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
			lineChart.removeLegend();
			plot =  lineChart.getXYPlot();
			plot.setBackgroundPaint(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		}

		/*Y axis formatting*/
		Axis axisY = plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

		/*X axis formatting*/
		NumberAxis axisX = (NumberAxis) plot.getDomainAxis();
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);
		axisX.setLowerBound(0.0);
		axisX.setAutoTickUnitSelection(true);

		/*Series formatting*/
		XYLineAndShapeRenderer render = (XYLineAndShapeRenderer) plot.getRenderer();		
		render.setSeriesPaint(0, new Color (5, 5, 140));
		render.setSeriesPaint(1, new Color (209, 16, 196));
		render.setSeriesPaint(2, new Color(204, 0, 0));
		render.setSeriesPaint(3, new Color(9, 224, 2));
		render.setSeriesPaint(4, new Color(249, 245, 7));

		render.setSeriesOutlineStroke(0, new BasicStroke(1));
		render.setSeriesOutlineStroke(1, new BasicStroke(1));
		render.setSeriesOutlineStroke(2, new BasicStroke(1));
		render.setSeriesOutlineStroke(3, new BasicStroke(1));
		render.setSeriesOutlineStroke(4, new BasicStroke(1));

		frameLayout (ID, identifier);
	}


	/**This method lays out the graph frame
	 * @param ID - the pillar indices
	 * @param identifier - the source of the data.*/

	public void frameLayout (int [] ID, String identifier) {

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Linechart Data - Forces Over Time");
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());

		ChartPanel chartpanel = new ChartPanel(lineChart);
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

		if (identifier.equals("Multipillar")) {

			button3 = new JButton ("Bar Chart");
			button3.setPreferredSize(new Dimension(125,23));
			button3.setOpaque(true);
			button3.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
			button3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			button3.setBorder(BorderFactory.createLineBorder(Color.black));
			button3.addActionListener (this);
			panel.add(button3);	
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

			ChartUtilities.saveChartAsJPEG(new File(fileName), lineChart, 1200, 800);
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

			BarChart BarChart = new BarChart (DataProcessing);
			BarChart.barChartData_Multipillar ();
		}
	}	
}