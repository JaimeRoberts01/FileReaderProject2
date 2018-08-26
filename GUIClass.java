import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class GUIClass extends JFrame implements ActionListener, ChangeListener {


	/*Instance variables*/
	private JPanel Panel1, Panel2, Panel3, Panel4, Panel5;
	private JLabel Label1, Label2, Label3, Label4, Label5, Label6, Label7, Label8, Label9;
	private JTextField TF1, TF2, TF3, TF4, TF5, TF6, TF7;
	private JSlider JS1;
	private JComboBox <String> JCB1;
	private JButton Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8;

	private Processing Process;
	private Processing2 Process2;
	@SuppressWarnings("unused")
	private FileManager FileManager;
	
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

		/*Panel1*/
		Panel1 = new JPanel ();
		Panel1.setBorder(new EmptyBorder (12,5,12,6));
		add (Panel1);

		Label1 = new JLabel ("File:");
		Label1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel1.add(Label1);

		TF1 = new JTextField(44);
		TF1.addActionListener (this);
		TF1.setBorder(BorderFactory.createLineBorder(Color.black));
		TF1.setPreferredSize(new Dimension(43,23));
		TF1.setEditable(false);
		Panel1.add(TF1);
		TF1.setEnabled(true);

		Button1 = new JButton ("Browse");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setOpaque(true);
		Button1.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Panel1.add (Button1);
		Button1.setEnabled (true);


		/*Panel2*/
		Panel2 = new JPanel ();
		Panel2.setBorder(new EmptyBorder (12,6,12,6));
		add (Panel2);

		Label2 = new JLabel ("Pixel to nm:");
		Label2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel2.add (Label2);

		TF2 = new JTextField (4);
		TF2.addActionListener (this);
		TF2.setBorder(BorderFactory.createLineBorder(Color.black));
		TF2.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF2.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF2);
		TF2.setEnabled(true);

		Label3 = new JLabel ("Substrate (MPa):");
		Label3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel2.add (Label3);

		TF3 = new JTextField (4);
		TF3.addActionListener (this);
		TF3.setBorder(BorderFactory.createLineBorder(Color.black));
		TF3.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF3.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF3);
		TF3.setEnabled(true);

		Label4 = new JLabel ("Pillar Diameter (µm):");
		Label4.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel2.add (Label4);

		TF4 = new JTextField (4);
		TF4.addActionListener (this);
		TF4.setBorder(BorderFactory.createLineBorder(Color.black));
		TF4.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF4.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF4);
		TF4.setEnabled(true);

		Label5 = new JLabel ("Pillar Length (µm):");
		Label5.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel2.add (Label5);

		TF5 = new JTextField (4);
		TF5.addActionListener (this);
		TF5.setBorder(BorderFactory.createLineBorder(Color.black));
		TF5.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF5.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF5);
		TF5.setEnabled(true);


		/*Panel3*/
		Panel3 = new JPanel ();
		Panel3.setBorder(new EmptyBorder (12,6,12,7));
		add (Panel3);

		Button2 = new JButton ("Calculate Force");
		Button2.setPreferredSize(new Dimension(129,23));
		Button2.setOpaque(true);
		Button2.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Panel3.add (Button2);
		Button2.setEnabled (false);

		Button3 = new JButton ("View Statistics");
		Button3.setPreferredSize(new Dimension(129,23));
		Button3.setOpaque(true);
		Button3.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button3.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button3.setBorder(BorderFactory.createLineBorder(Color.black));
		Button3.addActionListener (this);
		Panel3.add (Button3);
		Button3.setEnabled (false);

		JSeparator S1 = new JSeparator(SwingConstants.VERTICAL);
		S1.setPreferredSize(new Dimension(13,23));
		S1.setBackground(Color.DARK_GRAY);
		Panel3.add(S1);

		S1 = new JSeparator(SwingConstants.VERTICAL);
		S1.setPreferredSize(new Dimension(12,23));
		S1.setBackground(Color.DARK_GRAY);
		Panel3.add(S1);

		Label6 = new JLabel (" View all data by:");
		Label6.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel3.add (Label6);

		String [] comboBox = {"Select Style", "Line Chart", "Scatter Plot", "Bar Chart"};
		JCB1 = new JComboBox <String> (comboBox);
		JCB1.addActionListener(this);
		JCB1.setPreferredSize(new Dimension(135,30));
		JCB1.setEditable(false);
		Panel3.add(JCB1);
		JCB1.setEnabled(false);

		Button4 = new JButton ("Plot Data");
		Button4.setPreferredSize(new Dimension(129,23));
		Button4.setOpaque(true);
		Button4.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button4.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button4.setBorder(BorderFactory.createLineBorder(Color.black));
		Button4.addActionListener (this);
		Panel3.add (Button4);
		Button4.setEnabled (false);


		/*Panel4*/
		Panel4 = new JPanel ();
		Panel4.setBorder(new EmptyBorder (12,6,12,7));
		add(Panel4);

		Label7 = new JLabel ("Get data by frame:");
		Label7.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel4.add(Label7);

		TF6 = new JTextField(5);
		TF6.addActionListener (this);
		TF6.setBorder(BorderFactory.createLineBorder(Color.black));
		TF6.setPreferredSize(new Dimension(5,23));
		TF6.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF6.setText(Integer.toString(1));
		TF6.setEditable(false);
		Panel4.add(TF6);
		TF6.setEnabled(false);

		JS1 = new JSlider (JSlider.HORIZONTAL);
		JS1.addChangeListener(this);
		JS1.setMinimum(1);
		JS1.setValue(1);
		JS1.setPreferredSize(new Dimension(360, 23));
		Panel4.add(JS1);
		JS1.setEnabled(false);

		Button5 = new JButton ("Get Data");
		Button5.setPreferredSize(new Dimension(129,23));
		Button5.setOpaque(true);
		Button5.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button5.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button5.setBorder(BorderFactory.createLineBorder(Color.black));
		Button5.addActionListener (this);
		Panel4.add (Button5);
		Button5.setEnabled (false);


		/*Panel5*/
		Panel5 = new JPanel ();
		Panel5.setBorder(new EmptyBorder (12,6,12,7));
		add(Panel5);

		Label8 = new JLabel ("Get data by pillar: ");
		Label8.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel5.add (Label8);

		TF7 = new JTextField (5);
		TF7.addActionListener (this);
		TF7.setBorder(BorderFactory.createLineBorder(Color.black));
		TF7.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF7.setPreferredSize(new Dimension(5,23));
		Panel5.add(TF7);
		TF7.setEnabled(true);

		Button6 = new JButton ("Get Data");
		Button6.setPreferredSize(new Dimension(128,23));
		Button6.setOpaque(true);
		Button6.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button6.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button6.setBorder(BorderFactory.createLineBorder(Color.black));
		Button6.addActionListener (this);
		Panel5.add (Button6);
		Button6.setEnabled (false);

		Button7 = new JButton ("Multi-Pillar");
		Button7.setPreferredSize(new Dimension(128,23));
		Button7.setOpaque(true);
		Button7.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button7.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button7.setBorder(BorderFactory.createLineBorder(Color.black));
		Button7.addActionListener (this);
		Panel5.add (Button7);
		Button7.setEnabled (false);

		S1 = new JSeparator(SwingConstants.VERTICAL);
		S1.setPreferredSize(new Dimension(10,23));
		S1.setBackground(Color.DARK_GRAY);
		Panel5.add(S1);

		Label9 = new JLabel ("Get all data:");
		Label9.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Panel5.add (Label9);

		Button8 = new JButton ("Get Data");
		Button8.setPreferredSize(new Dimension(128,23));
		Button8.setOpaque(true);
		Button8.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button8.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button8.setBorder(BorderFactory.createLineBorder(Color.black));
		Button8.addActionListener (this);
		Panel5.add (Button8);
		Button8.setEnabled (false);
	}


	/** JFileChooser allows the open a file from a location in the directory;
	 * the fileName is passed the FileReader. Only csv files are permittable.
	 */

	public void fileSelection () {

		JFileChooser JFC = new JFileChooser ();
		JFC.setMultiSelectionEnabled(false);
		File selectedFile = null;
		int openVal = JFC.showDialog(null, "Select");

		if (openVal == JFileChooser.APPROVE_OPTION) {

			selectedFile = JFC.getSelectedFile();
			String fileName = selectedFile.toString();
			TF1.setText(fileName);

			if (fileName.contains(".csv")) {

				fileReader (fileName);
			}

			else {

				JOptionPane.showMessageDialog (null, "INVALID FILETYPE \n .csv files only", "ERROR", JOptionPane.ERROR_MESSAGE);
				TF1.setText("");
			}
		}

		else if (openVal == JFileChooser.CANCEL_OPTION) {

			return;
		}
	}


	/**FileReader opens the file and reads the data within it storing the 
	 * data line by line in an array.This should be moved to its own class 
	 * but there are problems when this happens.
	 */

	public void fileReader (String fileName) { 

		FileReader reader = null;
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;

		Process = new Processing();

		try {

			file = fileName;
			fileLine = new ArrayList<String>();

			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) !=null) {
				fileLine.add (line);
			}

			Process.data(fileLine);

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
		if (e.getSource() == Button1) {

			//			FileManager = new FileManager ();
			//			FileManager.fileSelection ();
			//			
			//			String fileName = FileManager.getFileName();
			//			if (fileName != null) {
			//				TF1.setText(fileName);
			//				Button2.setEnabled(true);
			//			}
			//			else {
			//				TF1.setText("");
			//			}
			if (TF2 != null || TF3 != null || TF4 != null || TF5 != null || TF7 != null) {

				reset ();
			}

			fileSelection ();

			Process2 = new Processing2 ();
			Process2.getFrames(Process.getData());
			int max = Process2.getFrame().size();
			JS1.setMaximum(max);
			JS1.setEnabled(true);

			Button2.setEnabled(true);
		}


		/*Calculate Forces*/
		if (e.getSource() == Button2) { 

			calculateForces();

			if (TF2 != null && TF3 != null && TF4 != null && TF5 != null) {

				Button3.setEnabled(true);
				JCB1.setEnabled(true);
				TF6.setEnabled(true);
				Button5.setEnabled(true);
				Button6.setEnabled(true);
				Button7.setEnabled(true);
				Button8.setEnabled(true);
			}
		}


		/*View Statistics*/
		if (e.getSource() == Button3) {

			try {

				Process2 = new Processing2 ();
				Process2.getPillars(Process.getData());
				Process2.getFrames(Process.getData());
				Process2.allFrames(Process.getNewData());

				OutputData OutputData = new OutputData (Process2, null);
				OutputData.stringStatistics();				
			}

			catch (NullPointerException NPE) {
				System.out.println("No valid data for statistical analysis");
				NPE.printStackTrace();
			}			
		}


		/*ComboBox*/
		if (e.getSource () == JCB1) {

			if (JCB1.getSelectedIndex() == 1 || JCB1.getSelectedIndex() == 2 || JCB1.getSelectedIndex() == 3) {

				Button4.setEnabled(true);
			}

			else {

				Button4.setEnabled(false);
			}	
		}


		/*Plot Data*/
		if (e.getSource() == Button4) { 

			if (JCB1.getSelectedIndex() == 1) {

				Process2 = new Processing2 ();
				Process2.getPillars(Process.getData());
				Process2.allFrames(Process.getNewData());
				Process2.allPillarsAllFrames(Process.getNewData());
				LineChart LineChart = new LineChart (Process2);
				LineChart.lineChartData_AllData();
			}

			if (JCB1.getSelectedIndex() == 2) {

				Process2 = new Processing2 ();
				Process2.getPillars(Process.getData());
				Process2.allFrames(Process.getNewData());
				ScatterPlot ScatterPlot = new ScatterPlot (Process2);
				ScatterPlot.scatterPlotData_AllFrames ();			
			}

			if (JCB1.getSelectedIndex() == 3) {

				Process2 = new Processing2 ();
				Process2.getPillars(Process.getData());
				Process2.allFrames(Process.getNewData());
				BarGraph BarGraph = new BarGraph (Process2); 
				BarGraph.barChartData_AllData();
			}
		}


		/*Get Data - Frame*/
		if (e.getSource() == Button5) {

			try {

				int ID = Integer.parseInt(TF6.getText().trim());
				Process2 = new Processing2 ();
				Process2.getPillars(Process.getData());
				Process2.getFrames(Process.getData());
				Process2.dataByFrame(Process.getNewData(), ID);
			}

			catch (NullPointerException NPE) {
				System.err.println("Invalid input");
				NPE.printStackTrace();
			}
		}


		/*Get Data - Pillar*/
		if (e.getSource() == Button6) {

			try {

				int ID = Integer.parseInt(TF7.getText().trim());

				if (ID == 0) {

					System.err.println("Value contains zero");			
				}

				else {

					Process2 = new Processing2 ();
					Process2.getPillars(Process.getData());
					Process2.getFrames(Process.getData());
					Process2.dataByPillar(Process.getNewData(), ID);
				}
			}

			catch (NumberFormatException NFE) {
				System.err.println("Invalid input");
				NFE.printStackTrace();
			}

			catch (NullPointerException NPE) {
				System.err.println("Invalid input");
				NPE.printStackTrace();
			}
		}


		/*MultiPillar*/
		if (e.getSource() == Button7) {

			MultipillarInput MultipillarInput = new MultipillarInput (Process, Process2);
			MultipillarInput.frameComponents();
		}


		/*Get Data - All Data*/
		if (e.getSource() == Button8) {

			Process2 = new Processing2 ();
			Process2.getPillars(Process.getData());
			Process2.allPillarsAllFrames(Process.getNewData());
			OutputData OutputData = new OutputData (Process2, null);
			OutputData.stringAllData();
		}
	}


	/**State change method for the slider.*/

	@Override 
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() == JS1) {

			if (JS1.getValueIsAdjusting()) {
				int slider = JS1.getValue();
				TF6.setText(Integer.toString(slider));
			}	
		}
	}


	/**This method retrieves input data from the TextFields for converting to forces*/

	public void calculateForces () {

		try {

			int conversion = Integer.parseInt(TF2.getText().trim());
			double youngsM = Double.parseDouble(TF3.getText().trim());
			double pillarD = Double.parseDouble(TF4.getText().trim());
			double pillarL = Double.parseDouble(TF5.getText().trim());

			if (conversion == 0 || youngsM == 0 || pillarD == 0 || pillarL == 0) {

				System.err.println("Value contains zero");			
			}

			else {

				Process.nanoMeters(conversion);
				Process.forces(youngsM, pillarD, pillarL);
				Process.newDataArray();
			}
		}

		catch (NumberFormatException NFE) {
			System.err.println("Invalid input");
			NFE.printStackTrace();
		}

		catch (NullPointerException NPE) {
			System.err.println("Invalid input");
			NPE.printStackTrace();
		}
	}


	public void reset () {

		TF2.setText(""); 
		TF3.setText("");
		TF4.setText("");
		TF5.setText("");
		TF7.setText("");

		JS1.setValue(1);

		Button2.setEnabled(false);
		Button3.setEnabled(false);
		Button4.setEnabled(false);
		Button5.setEnabled(false);
		Button6.setEnabled(false);
		Button7.setEnabled(false);
		Button8.setEnabled(false);
	}
}