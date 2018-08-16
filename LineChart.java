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


	/**Constructor*/
	
	public LineChart (Processing2 Process2) {
		this.Process2 = Process2;
	}


	/**This method calls the allPillarAllFrames method and creates a new array using that data. 
	 * An ID is passed to the method so that it can be used to build the line graph.
	 */

	public void lineChartData (int ID) { // getByPillar

		int rows = Process2.getOutputDataByPillar().size();
		int columns = 3;

		lineChartArray = new Object [rows][columns]; 

		for (int i = 0; i < rows; i++) {

			lineChartArray [i] = (Process2.getOutputDataByPillar().get(i)).toString().split(",");
			System.out.println(Arrays.toString(lineChartArray[i]));
		}

		createLineChart (ID);
	}


	/**This method creates the line graph. It uses the ID to run through the array in lineGraphData
	 * and pull out the data for a particular pillar. This is then plotted on a formatted graph plot
	 * that can be saved to file.
	 */

	public void createLineChart (int ID) {
		
		XYSeriesCollection dataset = new XYSeriesCollection ();
		XYSeries series = new XYSeries ("Pillars");

		double y = 0.0;
		int x = 0;

		for (int i =0; i< lineChartArray.length; i++) {

			int pillar = Integer.parseInt((String) lineChartArray [i][1]);

			if (ID == pillar) {

				y = Double.parseDouble((String) lineChartArray [i][2]); // force
				x = Integer.parseInt((String) lineChartArray [i][0]); // frame
				series.add(x, y);
			}
		}
		
		dataset.addSeries(series);

		lineChart = ChartFactory.createXYLineChart("Pillar " + ID + ": Forces over time", "Frame ID", "Force (pN)", dataset);
		plot = (XYPlot) lineChart.getPlot();
		lineChart.removeLegend();

		Axis axisY = plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

		NumberAxis axisX = (NumberAxis) plot.getDomainAxis();
		axisX.setTickUnit(new NumberTickUnit(5));
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);

		XYLineAndShapeRenderer render = (XYLineAndShapeRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		render.setSeriesPaint(0, gradientpaint);
		render.setSeriesOutlineStroke(0, new BasicStroke(1));

		frameLayout (ID);
	}

	
	/**This method lays out the graph frame*/
	
	public void frameLayout (int ID) {

		ChartFrame frame = new ChartFrame("Pillar: " + ID, lineChart);

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

		this.fileChooser();
	}	
}