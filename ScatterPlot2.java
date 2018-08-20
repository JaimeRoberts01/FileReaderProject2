import java.io.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;


public class ScatterPlot2 implements ActionListener {
	
	
	/**Instance Variables*/
	
	private Object [][] scatterPlotArray;
	private JFreeChart scatterPlot;
	private XYSeriesCollection dataset;
	private XYSeries series;
	
	private JFrame frame;
	private JButton Button1, Button2, Button3, Button4;
	private JTextField TF1;
	private XYPlot plot;

	private Processing2 Process2;
	
	
	/**Constructor*/
	
	public ScatterPlot2 (Processing2 Process2) {
		this.Process2 = Process2;
	}
	
	
	/**This method calls the allPillarAllFrames method and creates a new array using that data. 
	 * An ID is passed to the method so that it can be used to build the line graph.
	 */
	
	public void scatterPlotData (Object[] values) {
		
		System.out.println("We are here");
		
		int rows = Process2.getMean().size();
		int columns = 3;

		int [] ID = null;
		
		
		ID = new int [values.length];
		
		
		for (int i = 0; i < values.length; i++) { // creating a new array of the pillar index
			ID [i] = (int) values [i];	
		}
		System.out.println("ID: " + Arrays.toString(ID));
		
		
		scatterPlotArray = new Object [rows][columns]; 
		System.out.println("We are here1A");
		for (int i = 0; i < rows; i++) {

			System.out.println("We are here2");
			scatterPlotArray [i][0] = ID [i]; //Process2.getValues2() [i];
			scatterPlotArray [i][1] = Process2.getMean().get(i);
			scatterPlotArray [i][2] = Process2.getStandard_deviation().get(i);
			
			System.out.println("Scatterplot2: " + scatterPlotArray[i][0]);
			System.out.println("Scatterplot2: " + Arrays.toString(scatterPlotArray[i]));
		}
		System.out.println("We are here3");
		
		createScatterPlot (ID);
	}
	
	
	/**This method creates the scatter-plot. It uses the ID to run through the array in scatterPlotData
	 * and pulls out the data for a particular pillar. This is then plotted on a formatted graph plot
	 * that can be saved to file.
	 */
	
	public void createScatterPlot (int [] ID) {
				
		dataset = new XYSeriesCollection ();
		
//		for (int i = 0; i<ID.length; i++) {
//			XYSeries series = new XYSeries (ID [i]);
//			int inputID = ID[i];
		
		series = new XYSeries ("Pillars");

		for (int j =0; j< scatterPlotArray.length; j++ ) {
			
//			int pillar = (int) scatterPlotArray [j][0]; //Integer.parseInt((String) scatterPlotArray [j][0]);
//			
//			if (inputID == pillar) {

			int x = (int) scatterPlotArray [j][0];//Integer.parseInt((String) scatterPlotArray [i][0]); // pillar
			double y =  (double) scatterPlotArray [j][1]; // force
			series.add (x,y);
		}
		//}
		dataset.addSeries(series);
		//}

		scatterPlot = ChartFactory.createScatterPlot("Pillar Forces", "Pillar ID", "Force (pN)", dataset);
		scatterPlot.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
		scatterPlot.setBackgroundPaint(Color.white);
		scatterPlot.removeLegend();
	
		plot = (XYPlot) scatterPlot.getPlot();
		plot.setBackgroundPaint(Color.lightGray);

		NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f)); 

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

		XYLineAndShapeRenderer render = (XYLineAndShapeRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		render.setSeriesPaint(0, gradientpaint);
		GeneralPath cross = new GeneralPath();
		cross.moveTo(-1.0f, -3.0f);
		cross.lineTo(1.0f, -3.0f);
		cross.lineTo(1.0f, -1.0f);
		cross.lineTo(3.0f, -1.0f);
		cross.lineTo(3.0f, 1.0f);
		cross.lineTo(1.0f, 1.0f);
		cross.lineTo(1.0f, 3.0f);
		cross.lineTo(-1.0f, 3.0f);
		cross.lineTo(-1.0f, 1.0f);
		cross.lineTo(-3.0f, 1.0f);
		cross.lineTo(-3.0f, -1.0f);
		cross.lineTo(-1.0f, -1.0f);
		cross.closePath();
		render.setSeriesShape(0, cross);
		
		frameLayout(ID);
	}
	
	
	/**This method lays out the graph frame*/
	
	public void frameLayout (int [] ID) {
				
		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Frame: ");// + ID);
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

		ChartPanel chartpanel = new ChartPanel(scatterPlot);
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
	
	// ----- WORK IN PROGRESS ----- //
	
	public void filterValues () {

		int rows = this.scatterPlotArray.length;
		int columns = 3;

		Object [][] scatterPlotArray = new Object [rows][columns];

		int filterValue = 0;

		if (TF1.getText().equals("")) {
			filterValue = 0;
		}
		
		else {
			
			filterValue = Integer.parseInt(TF1.getText());
		}

		for (int i = 0; i < rows; i++) {

			double forces = (double) this.scatterPlotArray [i][1];

			if (forces>filterValue) {

				scatterPlotArray [i] = this.scatterPlotArray [i]; 
				System.out.println("NLC: " + Arrays.toString(scatterPlotArray [i]));
				}	
			}

		System.out.println("NLC L: " + scatterPlotArray.length);

		dataset = new XYSeriesCollection (); 
		series = new XYSeries ("Pillars");
		
		for (int i = 0; i < rows; i++) { 

			if (scatterPlotArray [i][1] != null) {

				int x = (int) scatterPlotArray [i][0];
				double y =  (double) scatterPlotArray [i][1];

				series.add (x,y);
			}
		}
		dataset.addSeries(series);

		plot.setDataset(dataset);
	}
	
	// ----- WORK IN PROGRESS ----- //
	
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
	
	
	/**This method saves the graph to file*/
	
	public void savePlot (String fileName) {
		
		try {

			ChartUtilities.saveChartAsJPEG(new File(fileName), scatterPlot, 1200, 800);
		} 

		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well2");
		
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
			filterValues ();
			
		}
		
		if (e.getSource() == Button4) {
			TF1.setText("");
			filterValues ();
		}
	}
}
