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


public class LineChart implements ActionListener {


	/**Instance Variables*/

	private Processing2 Process2;
	private JFreeChart lineChart;
	private XYPlot plot;
	private Object [][] lineChartArray;
	private JFrame frame;
	private JButton Button1, Button2, Button3;


	/**Constructor*/

	public LineChart (Processing2 Process2) {
		this.Process2 = Process2;	
	}

	
	/**This method retrieves data for the Multipillar method and creates a dataset array.
	 * IDs are passed to it from MultipillarInput in the form of an ID array for tracking
	 * all the individual pillar indexes when creating XYSeries data.
	 */
	
	public void lineChartData_Multipillar (Object[] IDs) { 


		int rows = Process2.getOutputMultipillar().size();
		int columns = 3;

		lineChartArray = new Object [rows][columns]; 
		int [] ID = null;
		
		try {

		for (int i = 0; i < rows; i++) {

			lineChartArray [i] = (Process2.getOutputMultipillar().get(i)).toString().split(",");
			System.out.println("LineChart2: " + Arrays.toString(lineChartArray[i]));
		}

		ID = new int [IDs.length];

		for (int i = 0; i < IDs.length; i++) { 

			ID [i] = Integer.parseInt((String) IDs [i]);	
		}
		
		}
		catch (NullPointerException NPE) {
			System.err.println("Invalid input");
		}

		String identifier = "Multipillar";
		createLineChart (ID, identifier);	
	}
	
	
	/**This method retrieves data from the allPillarsAllFrames method and creates a dataset array.
	 * IDs are created from the pillar ArrayList and turned into an ID array for tracking all the
	 * individual pillar indexes when creating XYSeries data.
	 */
	
	public void lineChartData_AllData () { 

		int rows = Process2.getDataByPillarFrame().size();
		int columns = 3;

		lineChartArray = new Object [rows][columns]; 
		int [] ID = null;

		try {

			for (int i = 0; i < rows; i++) {

				lineChartArray [i] = (Process2.getDataByPillarFrame().get(i)).toString().split(","); 
				System.out.println(Arrays.toString(lineChartArray[i]));
			}	

			ID = new int [Process2.getPillar().size()];

			for (int i = 0; i< Process2.getPillar().size(); i++) {
				ID [i] = Process2.getPillar().get(i);
				System.out.println("APAF ID: " + ID [i]);
			}
		}

		catch (NullPointerException NPE) {
			System.err.println("Invalid input");
		}

		String identifier = "AllData";
		createLineChart (ID, identifier);
	}
	
	
	/**This method retrieves data from the byPillar method and creates a dataset array. An ID is
	 * passed from Processing2 and turned into an ID array for tracking the pillar index across
	 * the individual frames.
	 */
	
	public void lineChartData_byPillar (int pillarID) { 

		int rows = Process2.getOutputDataByPillar().size();
		int columns = 3;

		lineChartArray = new Object [rows][columns]; 
		int [] ID = null;

		try {

			for (int i = 0; i < rows; i++) {

				lineChartArray [i] = (Process2.getOutputDataByPillar().get(i)).toString().split(",");
				System.out.println(Arrays.toString(lineChartArray[i]));
			}	

			ID = new int [1];

			for (int i = 0; i < ID.length; i++) { 
				
				ID [i] = pillarID;	
				System.out.println ("pillar ID: " + ID [i]);
			}
			
		}
		catch (NullPointerException NPE) {
			System.err.println("Invalid input");
		}

		String identifier = "ByPillar";
		createLineChart (ID, identifier);
	}


	/**This method creates the series data from the lineChart array */
	
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
		
	
	/**This method formats the LineChart.*/
	
	public void formatLineChart (int[] ID, XYSeriesCollection dataset, String identifier) {
		
		if (identifier.equals("Multipillar") || identifier.equals("ByPillar")) {

			lineChart = ChartFactory.createXYLineChart("Forces Over Time", "Frame ID", "Force (pN)", dataset);
			lineChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
			plot =  lineChart.getXYPlot();
		}

		else if (identifier.equals("AllData")) {
			lineChart = ChartFactory.createXYLineChart("Forces Over Time", "Frame ID", "Force (pN)", dataset);
			lineChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
			lineChart.removeLegend();
			plot =  lineChart.getXYPlot();
		}
		
		
		Axis axisY = plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

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


	/**This method lays out the graph frame*/

	public void frameLayout (int [] ID, String identifier) {

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Linechart Data - Forces Over Time");
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

		ChartPanel chartpanel = new ChartPanel(lineChart);
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
		
		if (identifier.equals("Multipillar")) {
			
			Button3 = new JButton ("Bar Chart");
			Button3.setPreferredSize(new Dimension(125,23));
			Button3.setOpaque(true);
			Button3.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
			Button3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
			Button3.setBorder(BorderFactory.createLineBorder(Color.black));
			Button3.addActionListener (this);
			frame.add(Button3);	
		}
		}


	/**FileChooser allows files to be saved in a particular directory and with a given name.
	 * The fileName is passed to the saveChart method for saving the linechart as an image.
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

			ChartUtilities.saveChartAsJPEG(new File(fileName), lineChart, 1200, 800);
		} 

		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well");
	}


	/**ActionPerformed method for the save button*/

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) {
			fileChooser();
		}
		
		if (e.getSource() == Button2) {
			frame.dispose();
		}
		if (e.getSource() == Button3) {
			BarGraph BarGraph = new BarGraph (Process2);
			BarGraph.barChartData_Multipillar ();
		}
	}	
}