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


public class LineChart3 implements ActionListener {


	/**Instance Variables*/

	private Processing2 Process2;
	private JFreeChart lineChart;
	private XYPlot plot;
	private Object [][] lineChartArray;
	private JFrame frame;
	private JButton Button1, Button2, Button3, Button4;
	private JTextField TF1;
	private int [] ID;


	/**Constructor*/

	public LineChart3 (Processing2 Process2) {
		this.Process2 = Process2;
	}

	
	public void lineChartData (Object[] values) { // all data 
		
		int rows = Process2.getDataByPillarFrame().size();
		int columns = 3;
		
		ID = null;

		lineChartArray = new Object [rows][columns]; 

		for (int i = 0; i < rows; i++) {

			lineChartArray [i] = (Process2.getDataByPillarFrame().get(i)).toString().split(",");
			System.out.println(Arrays.toString(lineChartArray[i]));
		}	
		
		ID = new int [values.length];

		for (int i = 0; i < values.length; i++) { // creating a new array of the pillar index

			ID [i] = (int) values [i];	
		}
		createLineChart (ID);
	}


	public void createLineChart (int [] ID) {

		XYSeriesCollection dataset = new XYSeriesCollection ();

		double y = 0.0;
		int x = 0;

		for (int i = 0; i< ID.length; i++) {

			XYSeries series = new XYSeries (ID[i]);
			int inputID = ID[i];

			for (int j =0; j< lineChartArray.length; j++) {

				int pillar = Integer.parseInt((String) lineChartArray [j][1]);

				if (inputID == pillar) {
					x = Integer.parseInt((String) lineChartArray [j][0]); // frame
					y = Double.parseDouble((String) lineChartArray [j][2]); // force
					series.add(x, y);
				}
			}

			dataset.addSeries(series);
		}

		lineChart = ChartFactory.createXYLineChart("Forces Over Time", "Frame ID", "Force (pN)", dataset);
		lineChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
		plot =  lineChart.getXYPlot();
		
		
		Axis axisY = plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

		NumberAxis axisX = (NumberAxis) plot.getDomainAxis();
		//axisX.setTickUnit(new NumberTickUnit(5));
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
				
		render.setSeriesPaint(0, new Color (5,5,140));
		render.setSeriesPaint(1, new Color (209,16,196));
		render.setSeriesPaint(2, new Color(204, 0, 0));
		render.setSeriesPaint(3, new Color(9, 224, 2));
		render.setSeriesPaint(4, new Color(249, 245, 7));
		
		render.setSeriesOutlineStroke(0, new BasicStroke(1));
		render.setSeriesOutlineStroke(1, new BasicStroke(1));
		render.setSeriesOutlineStroke(2, new BasicStroke(1));
		render.setSeriesOutlineStroke(3, new BasicStroke(1));
		render.setSeriesOutlineStroke(4, new BasicStroke(1));
		
		

		frameLayout (ID);
	}


	/**This method lays out the graph frame*/

	public void frameLayout (int [] ID) {

		//ChartFrame frame = new ChartFrame("Multipillar Data", lineChart);

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Forces Over Time - Multipillar Data");
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

	// --- WORK IN PROGRESS ----- //
	public void filterValues (int [] ID) { //this doesn't work

		int rows = this.lineChartArray.length;
		int columns = 3;

		Object [][] lineChartArray = new Object [rows][columns];

		int filterValue = 0;

		if (TF1.getText().equals("")) {
			filterValue = 0;
		}
		
		else {
			
			filterValue = Integer.parseInt(TF1.getText());
		}

		for (int i = 0; i < rows; i++) {

			double forces = Double.parseDouble((String) this.lineChartArray [i][2]);
			

			if (forces>filterValue) {

				lineChartArray [i] = this.lineChartArray [i]; 
				System.out.println("NLC: " + Arrays.toString(lineChartArray [i]));
				}	
			}

		System.out.println("NLC L: " + lineChartArray.length);

		XYSeriesCollection dataset = new XYSeriesCollection (); 
		
		for (int i = 0; i< ID.length; i++) {
			
			XYSeries series = new XYSeries (ID[i]);
			int inputID = ID[i];
		
		for (int j = 0; j < rows; j++) { 
			
			int pillar = 0;
			
			if (lineChartArray [j][1] != null) {
			
			pillar = Integer.parseInt((String) lineChartArray [j][1]);
			}

			if (inputID == pillar) {

				int x = Integer.parseInt((String) lineChartArray [j][0]);
				double y =  Double.parseDouble((String) lineChartArray [j][2]);
				series.add (x,y);
			}
		}
		dataset.addSeries(series);
		}
		plot.setDataset(dataset);
	}
	
	
	
//	XYSeriesCollection dataset = new XYSeriesCollection ();
//
//	double y = 0.0;
//	int x = 0;
//
//	for (int i = 0; i< ID.length; i++) {
//
//		XYSeries series = new XYSeries (ID[i]);
//		int inputID = ID[i];
//
//		for (int j =0; j< lineChartArray.length; j++) {
//
//			int pillar = Integer.parseInt((String) lineChartArray [j][1]);
//
//			if (inputID == pillar) {
//				x = Integer.parseInt((String) lineChartArray [j][0]); // frame
//				y = Double.parseDouble((String) lineChartArray [j][2]); // force
//				series.add(x, y);
//			}
//		}
//
//		dataset.addSeries(series);
//	}
	
	// ----- WORK IN PROGRESS -----//

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
			filterValues (ID);
			
		}
		
		if (e.getSource() == Button4) {
			TF1.setText("");
			filterValues (ID);
		}
	}
}