import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


public class LineChart implements ActionListener {

	
	/**Instance Variables*/

	private Processing2 Process2;
	private Processing Process;
	private JFreeChart lineChart;
	private CategoryPlot plot;
	private Object [][] lineChartArray;


	/**Constructor*/
	
	public LineChart (Processing2 Process2, Processing Process) {
		this.Process2 = Process2;
		this.Process = Process;
	}


	/**This method calls the allPillarAllFrames method and creates a new array using that data. 
	 * An ID is passed to the method so that it can be used to build the line graph.
	 */

	public void lineGraphData (int ID) {

		Process2.allPillarsAllFrames(Process.getNewData());

		int rows = Process2.getDataByPillarFrame().size();
		int columns = 3;

		lineChartArray = new Object [rows][columns]; 

		for (int i = 0; i < rows; i++) {

			lineChartArray [i] = (Process2.getDataByPillarFrame().get(i)).split(",");
			System.out.println(Arrays.toString(lineChartArray[i]));
		}

		createLineGraph (ID);
	}

	
	/**This method creates the line graph. It uses the ID to run through the array in lineGraphData
	 * and pull out the data for a particular pillar. This is then plotted on a formatted graph plot
	 * that can be saved to file.
	 */

	public void createLineGraph (int ID) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		double y = 0.0;
		String c = "";
		String x = "";

		for (int i =0; i< lineChartArray.length; i++) {

			int pillar = Integer.parseInt((String) lineChartArray [i][1]);

			if (ID == pillar) {

				y = Double.parseDouble((String) lineChartArray [i][2]); // force
				c = (String) lineChartArray [i][1]; // pillar
				x = (String) lineChartArray [i][0]; // frame
				dataset.addValue(y, c, x);
			}
		}

		lineChart = ChartFactory.createLineChart("Pillar: " + ID + " Forces over time", "Frame ID", "Force (pN)", dataset);
		plot =  lineChart.getCategoryPlot();
		lineChart.removeLegend();

		Axis axisY = plot.getRangeAxis();
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

		LineAndShapeRenderer render = new LineAndShapeRenderer ();
		render.setSeriesFillPaint(0, Color.black);

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
					saveGraph (fileName);
				}

				else if (response == JOptionPane.NO_OPTION) {
					response = JOptionPane.CLOSED_OPTION;
				}
			}

			else {
				fileName = savedFile.toString();
				saveGraph (fileName);	
			}
		}
	}


	/**This method saves the graph to file*/

	public void saveGraph (String fileName) {

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