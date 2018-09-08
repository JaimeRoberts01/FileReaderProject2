import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;


/**Defines a GUI that displays TextFields to enable 
 * input values and buttons to access the results. 
 */

@SuppressWarnings("serial")
public class GUIClass extends JFrame implements ActionListener, ChangeListener {


	/*Instance variables*/
	private JPanel panel1, panel2, panel3, panel4, panel5;
	private JLabel label1, label2,label3, label4, label5, label6, label7, label8, label9;
	private JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7;
	private JSlider js1;
	private JComboBox <String> jcb1;
	private JButton button1, button2, button3, button4, button5, button6, button7, button8;

	private DataArray DataArray;
	private DataProcessing DataProcessing;
	
	private ArrayList <String> fileLine;


	/*Constructor*/
	public GUIClass ()  {

		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setFont(new Font ("SansSerif", Font.PLAIN, 14));
		setTitle ("PillarTracker File Reader");
		setSize (709, 300);
		setLocationRelativeTo(null);
		setResizable (false);
		setLayout (new GridLayout (5,1));
		GUIComponents ();
	}


	/**Basic GUI setup*/

	public void GUIComponents () {

		/*panel1*/
		panel1 = new JPanel ();
		panel1.setBorder(new EmptyBorder (12,5,12,6));
		add (panel1);

		label1 = new JLabel ("File:");
		label1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel1.add(label1);

		tf1 = new JTextField(44);
		tf1.addActionListener (this);
		tf1.setBorder(BorderFactory.createLineBorder(Color.black));
		tf1.setPreferredSize(new Dimension(43,23));
		tf1.setEditable(false);
		panel1.add(tf1);
		tf1.setEnabled(true);

		button1 = new JButton ("Browse");
		button1.setPreferredSize(new Dimension(125,23));
		button1.setOpaque(true);
		button1.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button1.setBorder(BorderFactory.createLineBorder(Color.black));
		button1.addActionListener (this);
		panel1.add (button1);
		button1.setEnabled (true);


		/*panel2*/
		panel2 = new JPanel ();
		panel2.setBorder(new EmptyBorder (12,6,12,6));
		add (panel2);

		label2 = new JLabel ("Pixel to nm:");
		label2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel2.add (label2);

		tf2 = new JTextField (4);
		tf2.addActionListener (this);
		tf2.setBorder(BorderFactory.createLineBorder(Color.black));
		tf2.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		tf2.setPreferredSize(new Dimension(4,23));
		panel2.add(tf2);
		tf2.setEnabled(true);

		label3 = new JLabel ("Substrate (MPa):");
		label3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel2.add (label3);

		tf3 = new JTextField (4);
		tf3.addActionListener (this);
		tf3.setBorder(BorderFactory.createLineBorder(Color.black));
		tf3.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		tf3.setPreferredSize(new Dimension(4,23));
		panel2.add(tf3);
		tf3.setEnabled(true);

		label4 = new JLabel ("Pillar Diameter (µm):");
		label4.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel2.add (label4);

		tf4 = new JTextField (4);
		tf4.addActionListener (this);
		tf4.setBorder(BorderFactory.createLineBorder(Color.black));
		tf4.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		tf4.setPreferredSize(new Dimension(4,23));
		panel2.add(tf4);
		tf4.setEnabled(true);

		label5 = new JLabel ("Pillar Length (µm):");
		label5.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel2.add (label5);

		tf5 = new JTextField (4);
		tf5.addActionListener (this);
		tf5.setBorder(BorderFactory.createLineBorder(Color.black));
		tf5.setHorizontalAlignment((int) JTextField.CENTER_ALIGNMENT);
		tf5.setPreferredSize(new Dimension(4,23));
		panel2.add(tf5);
		tf5.setEnabled(true);


		/*panel3*/
		panel3 = new JPanel ();
		panel3.setBorder(new EmptyBorder (12,6,12,7));
		add (panel3);

		button2 = new JButton ("Calculate Force");
		button2.setPreferredSize(new Dimension(129,23));
		button2.setOpaque(true);
		button2.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button2.setBorder(BorderFactory.createLineBorder(Color.black));
		button2.addActionListener (this);
		panel3.add (button2);
		button2.setEnabled (false);

		button3 = new JButton ("View Statistics");
		button3.setPreferredSize(new Dimension(129,23));
		button3.setOpaque(true);
		button3.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button3.setBorder(BorderFactory.createLineBorder(Color.black));
		button3.addActionListener (this);
		panel3.add (button3);
		button3.setEnabled (false);

		JSeparator s1 = new JSeparator(SwingConstants.VERTICAL);
		s1.setPreferredSize(new Dimension(13,23));
		s1.setBackground(Color.DARK_GRAY);
		panel3.add(s1);

		s1 = new JSeparator(SwingConstants.VERTICAL);
		s1.setPreferredSize(new Dimension(12,23));
		s1.setBackground(Color.DARK_GRAY);
		panel3.add(s1);

		label6 = new JLabel (" View all data by:");
		label6.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel3.add (label6);

		String [] comboBox = {"Select Style", "Line Chart", "Scatter Plot", "Bar Chart"};
		jcb1 = new JComboBox <String> (comboBox);
		jcb1.addActionListener(this);
		jcb1.setPreferredSize(new Dimension(135,30));
		jcb1.setEditable(false);
		panel3.add(jcb1);
		jcb1.setEnabled(false);

		button4 = new JButton ("Plot Data");
		button4.setPreferredSize(new Dimension(129,23));
		button4.setOpaque(true);
		button4.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button4.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button4.setBorder(BorderFactory.createLineBorder(Color.black));
		button4.addActionListener (this);
		panel3.add (button4);
		button4.setEnabled (false);


		/*panel4*/
		panel4 = new JPanel ();
		panel4.setBorder(new EmptyBorder (12,6,12,7));
		add(panel4);

		label7 = new JLabel ("Get data by frame:");
		label7.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel4.add(label7);

		tf6 = new JTextField(5);
		tf6.addActionListener (this);
		tf6.setBorder(BorderFactory.createLineBorder(Color.black));
		tf6.setPreferredSize(new Dimension(5,23));
		tf6.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		tf6.setText(Integer.toString(1));
		tf6.setEditable(false);
		panel4.add(tf6);
		tf6.setEnabled(false);

		js1 = new JSlider (JSlider.HORIZONTAL);
		js1.addChangeListener(this);
		js1.setMinimum(1);
		js1.setValue(1);
		js1.setPreferredSize(new Dimension(360, 23));
		panel4.add(js1);
		js1.setEnabled(false);

		button5 = new JButton ("Get Data");
		button5.setPreferredSize(new Dimension(129,23));
		button5.setOpaque(true);
		button5.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button5.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button5.setBorder(BorderFactory.createLineBorder(Color.black));
		button5.addActionListener (this);
		panel4.add (button5);
		button5.setEnabled (false);


		/*panel5*/
		panel5 = new JPanel ();
		panel5.setBorder(new EmptyBorder (12,6,12,7));
		add(panel5);

		label8 = new JLabel ("Get data by pillar: ");
		label8.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel5.add (label8);

		tf7 = new JTextField (5);
		tf7.addActionListener (this);
		tf7.setBorder(BorderFactory.createLineBorder(Color.black));
		tf7.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		tf7.setPreferredSize(new Dimension(5,23));
		panel5.add(tf7);
		tf7.setEnabled(true);

		button6 = new JButton ("Get Data");
		button6.setPreferredSize(new Dimension(128,23));
		button6.setOpaque(true);
		button6.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button6.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button6.setBorder(BorderFactory.createLineBorder(Color.black));
		button6.addActionListener (this);
		panel5.add (button6);
		button6.setEnabled (false);

		button7 = new JButton ("Multi-Pillar");
		button7.setPreferredSize(new Dimension(128,23));
		button7.setOpaque(true);
		button7.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button7.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button7.setBorder(BorderFactory.createLineBorder(Color.black));
		button7.addActionListener (this);
		panel5.add (button7);
		button7.setEnabled (false);

		s1 = new JSeparator(SwingConstants.VERTICAL);
		s1.setPreferredSize(new Dimension(10,23));
		s1.setBackground(Color.DARK_GRAY);
		panel5.add(s1);

		label9 = new JLabel ("Get all data:");
		label9.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		panel5.add (label9);

		button8 = new JButton ("Get Data");
		button8.setPreferredSize(new Dimension(128,23));
		button8.setOpaque(true);
		button8.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		button8.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button8.setBorder(BorderFactory.createLineBorder(Color.black));
		button8.addActionListener (this);
		panel5.add (button8);
		button8.setEnabled (false);
	}


	/** JFileChooser allows the open a file from a location in the directory;
	 * the fileName is passed the FileReader. Only csv files are permitable.
	 */

	public void fileSelection () {

		JFileChooser JFC = new JFileChooser ();
		JFC.setMultiSelectionEnabled(false);
		File selectedFile = null;
		int openVal = JFC.showDialog(null, "Select");

		if (openVal == JFileChooser.APPROVE_OPTION) {

			selectedFile = JFC.getSelectedFile();
			String fileName = selectedFile.toString();
			tf1.setText(fileName);

			if (fileName.contains(".csv")) {

				fileReader (fileName);
			}

			else {

				JOptionPane.showMessageDialog (null, "INVALID FILETYPE \n .csv files only", "ERROR", JOptionPane.ERROR_MESSAGE);
				tf1.setText(""); // The program is designed for csv files.
			}
		}

		else if (openVal == JFileChooser.CANCEL_OPTION) {

			return;
		}
	}


	/**FileReader opens the file and reads the data within 
	 * it then stores the data line by line in an array.
	 * @param fileName - the name of the file.
	 */

	public void fileReader (String fileName) { 

		FileReader reader = null;
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;

		DataArray = new DataArray();

		try {

			file = fileName;
			fileLine = new ArrayList<String>();

			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) !=null) {
				fileLine.add (line);
			}

			DataArray.data(fileLine);

			reader.close();	
			bufferedReader.close();
		}

		catch (IOException IOE) {

			JOptionPane.showMessageDialog (null, "FILE NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
			IOE.printStackTrace();	
		}

		catch (InputMismatchException IME) {

			JOptionPane.showMessageDialog (null, "INVALID FILE", "ERROR", JOptionPane.ERROR_MESSAGE);
			IME.printStackTrace();	
		}	
	}


	/**ActionPerformed methods for the individual buttons*/

	@Override 
	public void actionPerformed(ActionEvent e) {


		/*Browse*/
		if (e.getSource() == button1) {

			if (tf2 != null || tf3 != null || tf4 != null || tf5 != null || tf7 != null) {

				reset ();
			}

			fileSelection (); // Calls the JFileChooser.

			DataProcessing = new DataProcessing ();
			DataProcessing.getFrames(DataArray.getData());
			
			int max = DataProcessing.getFrame().size(); // Gets the number of frames in the file.
			js1.setMaximum(max); // Sets the maximum value of the frame slider.
			js1.setEnabled(true);

			button2.setEnabled(true);
		}
		
		
		/*Calculate Forces*/
		if (e.getSource() == button2) { 

			calculateForces(); // Calls the method for calculating forces.

			if (tf2 != null && tf3 != null && tf4 != null && tf5 != null) {

				button3.setEnabled(true);
				jcb1.setEnabled(true);
				tf6.setEnabled(true);
				button5.setEnabled(true);
				button6.setEnabled(true);
				button7.setEnabled(true);
				button8.setEnabled(true);
				// Enables all the other buttons etc.
			}
		}


		/*View Statistics*/
		if (e.getSource() == button3) {

			try {

				DataProcessing = new DataProcessing ();
				DataProcessing.getPillars(DataArray.getData());
				DataProcessing.allFrames(DataArray.getDataArray());

				DataOutput DataOutput = new DataOutput (DataProcessing, null);
				DataOutput.stringStatistics(); 
				// Calls the method for loading values to the JTable.				
			}

			catch (NullPointerException NPE) {
				
				String message = "No valid data for statistical analysis";
				StringWriter stackTraceWriter = new StringWriter();
				NPE.printStackTrace(new PrintWriter(stackTraceWriter));
				String stackTrace = stackTraceWriter.toString();
				
				LogFile log = new LogFile ();
				log.writeToLog(message, stackTrace); 
				// Calls the log file if there is an error.
			}			
		}


		/*ComboBox*/
		if (e.getSource () == jcb1) {

			if (jcb1.getSelectedIndex() == 1 || jcb1.getSelectedIndex() == 2 || jcb1.getSelectedIndex() == 3) {

				button4.setEnabled(true);
			}

			else {

				button4.setEnabled(false);
			}	
		}


		/*Plot Data*/
		if (e.getSource() == button4) { 

			if (jcb1.getSelectedIndex() == 1) {

				DataProcessing = new DataProcessing ();
				DataProcessing.getPillars(DataArray.getData());
				DataProcessing.allPillarsAllFrames(DataArray.getDataArray());
				
				LineChart LineChart = new LineChart (DataProcessing);
				LineChart.lineChartData_AllData(); 
				// Calls the method for creating the line chart of all data.
			}

			if (jcb1.getSelectedIndex() == 2) {

				DataProcessing = new DataProcessing ();
				DataProcessing.getPillars(DataArray.getData());
				DataProcessing.allFrames(DataArray.getDataArray());
				
				ScatterPlot ScatterPlot = new ScatterPlot (DataProcessing);
				ScatterPlot.scatterPlotData_AllFrames (); 
				// Calls the method for creating the Scatter plot of all data.
			}

			if (jcb1.getSelectedIndex() == 3) {

				DataProcessing = new DataProcessing ();
				DataProcessing.getPillars(DataArray.getData());
				DataProcessing.allFrames(DataArray.getDataArray());
				
				BarChart BarChart = new BarChart (DataProcessing); 
				BarChart.barChartData_AllData(); 
				// Calls the method for creating the bar chart of all data.
			}
		}


		/*Get Data - Frame*/
		if (e.getSource() == button5) {

			try {

				int ID = Integer.parseInt(tf6.getText().trim());
				DataProcessing = new DataProcessing ();
				DataProcessing.getPillars(DataArray.getData());
				DataProcessing.getFrames(DataArray.getData());
				DataProcessing.dataByFrame(DataArray.getDataArray(), ID);
			}

			catch (NullPointerException NPE) {
				
				String message = "Invalid user input - check input variable integer.";
				StringWriter stackTraceWriter = new StringWriter();
				NPE.printStackTrace(new PrintWriter(stackTraceWriter));
				String stackTrace = stackTraceWriter.toString();
				
				LogFile log = new LogFile ();
				log.writeToLog(message, stackTrace);
				// Calls the log file if there is an error.
			}
		}


		/*Get Data - Pillar*/
		if (e.getSource() == button6) {

			try {

				int ID = Integer.parseInt(tf7.getText().trim());

				if (ID == 0) {

					String message = "Invalid user input - value contains zero.";
					LogFile log = new LogFile ();
					log.writeToLog(message, null);
					// Calls the log file if there is an error.
				}

				else {

					DataProcessing = new DataProcessing ();
					DataProcessing.getPillars(DataArray.getData());
					DataProcessing.dataByPillar(DataArray.getDataArray(), ID);
				}
			}

			catch (NumberFormatException NFE) {
				
				String message = "Invalid user input - check input variable integer.";
				StringWriter stackTraceWriter = new StringWriter();
				NFE.printStackTrace(new PrintWriter(stackTraceWriter));
				String stackTrace = stackTraceWriter.toString();
				
				LogFile log = new LogFile ();
				log.writeToLog(message, stackTrace);
				// Calls the log file if there is an error.
			}

			catch (NullPointerException NPE) {
				
				String message = "Invalid user input - check input variable integer.";
				StringWriter stackTraceWriter = new StringWriter();
				NPE.printStackTrace(new PrintWriter(stackTraceWriter));
				String stackTrace = stackTraceWriter.toString();
				
				LogFile log = new LogFile ();
				log.writeToLog(message, stackTrace);
				// Calls the log file if there is an error.
			}
		}


		/*MultiPillar*/
		if (e.getSource() == button7) {

			MultipillarInput MultipillarInput = new MultipillarInput (DataArray, DataProcessing);
			MultipillarInput.frameComponents(); // Calls the MultiPillar input box.
		}


		/*Get Data - All Data*/
		if (e.getSource() == button8) {

			DataProcessing = new DataProcessing ();
			DataProcessing.getPillars(DataArray.getData());
			DataProcessing.allPillarsAllFrames(DataArray.getDataArray());
			
			DataOutput DataOutput = new DataOutput (DataProcessing, null);
			DataOutput.stringAllData();
			// Calls the method for loading values to the JTable.	
		}
	}


	/**State change method for the slider.*/

	@Override 
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() == js1) {

			if (js1.getValueIsAdjusting()) {
				int slider = js1.getValue();
				tf6.setText(Integer.toString(slider)); // Value appears in TextField.
			}	
		}
	}


	/**This method retrieves input data from the Textfields for converting to forces*/

	public void calculateForces () {

		try {

			int conversion = Integer.parseInt(tf2.getText().trim());
			double youngsM = Double.parseDouble(tf3.getText().trim());
			double pillarD = Double.parseDouble(tf4.getText().trim());
			double pillarL = Double.parseDouble(tf5.getText().trim());

			if (conversion == 0 || youngsM == 0 || pillarD == 0 || pillarL == 0) {

				String message = "Invalid user input - value contains zero.";
				LogFile log = new LogFile ();
				log.writeToLog(message, null);
				// Calls the log file if there is an error.
			}

			else {

				DataArray.nanoMeters(conversion);
				DataArray.forces(youngsM, pillarD, pillarL);
				DataArray.newDataArray();
			}
		}

		catch (NumberFormatException NFE) {
			
			String message = "Invalid user input - check input variables integer, double, double, double.";
			StringWriter stackTraceWriter = new StringWriter();
			NFE.printStackTrace(new PrintWriter(stackTraceWriter));
			String stackTrace = stackTraceWriter.toString();
			
			LogFile log = new LogFile ();
			log.writeToLog(message, stackTrace);
			// Calls the log file if there is an error.
		}

		catch (NullPointerException NPE) {
			
			String message = "Invalid user input - check input variables integer, double, double, double.";
			StringWriter stackTraceWriter = new StringWriter();
			NPE.printStackTrace(new PrintWriter(stackTraceWriter));
			String stackTrace = stackTraceWriter.toString();
			
			LogFile log = new LogFile ();
			log.writeToLog(message, stackTrace);
			// Calls the log file if there is an error.
		}
	}


	/**This method resets the TextFields and buttons when a file is loaded*/
	
	public void reset () {

		tf2.setText(""); 
		tf3.setText("");
		tf4.setText("");
		tf5.setText("");
		tf7.setText("");

		js1.setValue(1);

		button2.setEnabled(false);
		button3.setEnabled(false);
		button4.setEnabled(false);
		button5.setEnabled(false);
		button6.setEnabled(false);
		button7.setEnabled(false);
		button8.setEnabled(false);
	}
}