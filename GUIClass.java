import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import javax.swing.event.*;


@SuppressWarnings("serial")
public class GUIClass extends JFrame implements ActionListener, ChangeListener {


	/*Instance variables*/
	private JPanel Panel1, Panel2, Panel3, Panel4, Panel5;
	private JLabel Label1, Label2, Label3, Label4, Label5, Label6, Label7, Label8;
	private JSeparator S1;
	private JTextField TF1, TF2, TF3, TF4, TF5, TF6, TF7;

	private JSlider JS1;
	private JFileChooser JFC;
	private JButton Button1, Button2, Button3, Button4, Button5, Button6;

	private ReportFrame ReportFrame;
	private ReportFrame2 ReportFrame2;
	private Processing Process;
	private Processing2 Process2;
	private DataPlotting DataPlotting;
	//private FileManager FileManager;

	private ArrayList <String> fileLine;


	/*Getters and Setters*/
	public ArrayList<String> getFileLine() {return fileLine;}
	public void setFileLine(ArrayList<String> fileLine) {this.fileLine = fileLine;}


	/*Constructor*/
	public GUIClass ()  {

		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setFont(new Font ("Consolas", Font.PLAIN, 14));
		setTitle ("PillarTracker File Reader");
		setSize (700, 300);
		setLocation (1500,0);
		setResizable (false);
		setLayout (new GridLayout (5,1));
		GUIComponents ();
	}


	/*Basic GUI setup*/
	public void GUIComponents () {

		/*Panel1 contains the JFileChooser for selecting files*/
		Panel1 = new JPanel ();
		Panel1.setBackground (Color.lightGray);
		Panel1.setBorder(new EmptyBorder (12,4,12,4));
		add (Panel1);

		Label1 = new JLabel ("File:");
		Label1.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel1.add(Label1);

		TF1 = new JTextField(43);
		TF1.addActionListener (this);
		TF1.setBorder(BorderFactory.createLineBorder(Color.black));
		TF1.setPreferredSize(new Dimension(43,23));
		TF1.setEditable(false);
		Panel1.add(TF1);
		TF1.setEnabled(true);

		Button1 = new JButton ("Browse");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button1.setOpaque(true);
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Panel1.add (Button1);
		Button1.setEnabled (true);


		/*Panel2 contains the JTextFields for calculating force.*/
		Panel2 = new JPanel ();
		Panel2.setBackground (Color.lightGray);
		Panel2.setBorder(new EmptyBorder (12,6,12,6));
		add (Panel2);

		Label2 = new JLabel ("Pixel to nm:");
		Label2.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel2.add (Label2);

		TF2 = new JTextField (4);
		TF2.addActionListener (this);
		TF2.setBorder(BorderFactory.createLineBorder(Color.black));
		TF2.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF2.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF2);
		TF2.setEnabled(true);

		Label3 = new JLabel ("Substrate (E):");
		Label3.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel2.add (Label3);

		TF3 = new JTextField (4);
		TF3.addActionListener (this);
		TF3.setBorder(BorderFactory.createLineBorder(Color.black));
		TF3.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF3.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF3);
		TF3.setEnabled(true);

		Label4 = new JLabel (" Pillar Diameter (µm):");
		Label4.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel2.add (Label4);

		TF4 = new JTextField (4);
		TF4.addActionListener (this);
		TF4.setBorder(BorderFactory.createLineBorder(Color.black));
		TF4.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF4.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF4);
		TF4.setEnabled(true);

		Label5 = new JLabel (" Pillar Length (µm):");
		Label5.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel2.add (Label5);

		TF5 = new JTextField (4);
		TF5.addActionListener (this);
		TF5.setBorder(BorderFactory.createLineBorder(Color.black));
		TF5.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF5.setPreferredSize(new Dimension(4,23));
		Panel2.add(TF5);
		TF5.setEnabled(true);


		/*Panel3 contains buttons for calculating force and saving.*/
		Panel3 = new JPanel ();
		Panel3.setBackground (Color.lightGray);
		Panel3.setBorder(new EmptyBorder (12,6,12,7));
		add (Panel3);

		Button2 = new JButton ("Calculate Force");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button2.setOpaque(true);
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Panel3.add (Button2);
		Button2.setEnabled (true);

		Button3 = new JButton ("Save Data");
		Button3.setPreferredSize(new Dimension(125,23));
		Button3.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button3.setOpaque(true);
		Button3.setBorder(BorderFactory.createLineBorder(Color.black));
		Button3.addActionListener (this);
		Panel3.add (Button3);
		Button3.setEnabled (true);


		/*Panel4 contains sliders for obtaining all pillar data for a
		 given frame (selected by the slider).*/
		Panel4 = new JPanel ();
		Panel4.setBackground (Color.lightGray);
		Panel4.setBorder(new EmptyBorder (12,6,12,7));
		add(Panel4);

		Label6 = new JLabel ("Get data by frame:");
		Label6.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel4.add(Label6);

		JS1 = new JSlider (JSlider.HORIZONTAL);
		JS1.addChangeListener(this);
		JS1.setMaximum(91);
		JS1.setMinimum(1);
		JS1.setValue(1);
		JS1.setPreferredSize(new Dimension(496, 23));
		Panel4.add(JS1);
		JS1.setEnabled(true);

		TF6 = new JTextField(4);
		TF6.addActionListener (this);
		TF6.setBorder(BorderFactory.createLineBorder(Color.black));
		TF6.setPreferredSize(new Dimension(4,23));
		TF6.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF6.setText(Integer.toString(1));
		TF6.setEditable(false);
		Panel4.add(TF6);
		TF6.setEnabled(true);


		/*Panel5 contains the JTextfields and buttons for obtaining pillar
		 data for a particular pillar.*/
		Panel5 = new JPanel ();
		Panel5.setBackground (Color.lightGray);
		Panel5.setBorder(new EmptyBorder (12,6,12,7));
		add(Panel5);

		Label7 = new JLabel ("Get data by pillar:");
		Label7.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel5.add (Label7);

		TF7 = new JTextField (5);
		TF7.addActionListener (this);
		TF7.setBorder(BorderFactory.createLineBorder(Color.black));
		TF7.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF7.setPreferredSize(new Dimension(4,23));
		Panel5.add(TF7);
		TF7.setEnabled(true);

		Button4 = new JButton ("Add");
		Button4.setPreferredSize(new Dimension(125,23));
		Button4.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button4.setOpaque(true);
		Button4.setBorder(BorderFactory.createLineBorder(Color.black));
		Button4.addActionListener (this);
		Panel5.add (Button4);
		Button4.setEnabled (true);

		Button5 = new JButton ("Get Data");
		Button5.setPreferredSize(new Dimension(125,23));
		Button5.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button5.setOpaque(true);
		Button5.setBorder(BorderFactory.createLineBorder(Color.black));
		Button5.addActionListener (this);
		Panel5.add (Button5);
		Button5.setEnabled (true);

		S1 = new JSeparator(SwingConstants.VERTICAL);
		S1.setPreferredSize(new Dimension(10,23));
		S1.setBackground(Color.DARK_GRAY);
		Panel5.add(S1);

		Label8 = new JLabel ("Get all data:");
		Label8.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Panel5.add (Label8);

		Button6 = new JButton ("All Frames");
		Button6.setPreferredSize(new Dimension(127,23));
		Button6.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button6.setOpaque(true);
		Button6.setBorder(BorderFactory.createLineBorder(Color.black));
		Button6.addActionListener (this);
		Panel5.add (Button6);
		Button6.setEnabled (true);
	}


	/*FileReader opens the file and reads the data within it storing the data line by line in an array.
	 This should be moved to its own class but there are problems when this happens.*/
	public void fileReader (String fileName) { 

		FileReader reader = null;
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;
		ReportFrame = new ReportFrame ();
		Process = new Processing();

		try {

			file = fileName;
			fileLine = new ArrayList<String>();

			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) !=null) {
				fileLine.add (line);
			}

			for (String s : fileLine) {
				ReportFrame.reportFormatter(s);	
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


	/*FileWriter saves the data to a new file. This should be in its own class but there are problems
	 when attempting this.*/
	public void fileWriter (String fileName, int dataset) {

		FileWriter writer = null;
		String file = null;

		try {

			try {
				file = fileName;
				writer = new FileWriter (file);
				
				if (dataset == 0) {	
					writer.write(Process.outputFile());
				}
				else if (dataset ==1) {
					writer.write(Process2.outputFile2());	
				}
			}

			finally {

				writer.close();	
			}
		}

		catch (IOException IOE) {

			IOE.printStackTrace();	
		}
	}


	/*This is for displaying the output data in the report frame. It is for testing the output data
	 is correct and will be deleted at the end - it cannot cope with the whole file.*/
	public void displayOutput () {

		ReportFrame2 = new ReportFrame2 (Process);	
		ReportFrame2.reportFormatter();
	}


	@Override /*ActionPerformed methods for the individual buttons*/
	public void actionPerformed(ActionEvent e) {

		/*This is the 'Browse' button and enables the user to open a file. A string is passed to the
		 texfield so the user can see which file has been opened.*/
		if (e.getSource() == Button1) {
			JFC = new JFileChooser ();
			JFC.setMultiSelectionEnabled(false);
			int openVal = JFC.showDialog(null, "Select");

			if (openVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile =	JFC.getSelectedFile();
				TF1.setText(selectedFile.toString());
				String fileName = TF1.getText();

				System.out.println("THIS IS TF1;" + TF1.getText());
				fileReader(fileName);
				//				String fileName = TF5.getText();
				//				System.out.println("THIS IS FILEZ" + fileName);
				//				FileManager = new FileManager ();
				//				FileManager.fileReader (fileName);
			}
		}


		/*This is the 'Calculate Forces' button and calls methods for calculating the forces.*/
		if (e.getSource() == Button2) { 
			System.out.println("We definitely hit button 2");
			int conversion = Integer.parseInt(TF2.getText().trim());
			double youngsM = Double.parseDouble(TF3.getText().trim());
			double pillarD = Double.parseDouble(TF4.getText().trim());
			double pillarL = Double.parseDouble(TF5.getText().trim());
			Process.nanoMeters(conversion);
			Process.forces(youngsM, pillarD, pillarL);
			Process.newDataArray();
			System.out.println("And if you're seeing this, we did something!");
		}


		/*This is the 'Save' button. It calls the fileReader and passes it a string for the file name.If
		 a file with this name exists, the user is given the option to change the name or overwrite.*/
		if (e.getSource() == Button3) { 

			System.out.println("We definitely hit button 3");	
			

			Object [] options = {"Force Data", "Stats Data"};
			String option = (String) JOptionPane.showInputDialog (null, "Choose which data to save", 
					"Select an Option...", JOptionPane.PLAIN_MESSAGE, null, options, "Force Data");
			int dataset = 0;
			if (option == options [0] ) {
				dataset = 0;
			}
			else if (option == options [1] ) {
				dataset = 1;
			}

			JFC = new JFileChooser();
			String fileName = "";
			int saveVal = JFC.showSaveDialog(null);

			if (saveVal == JFileChooser.APPROVE_OPTION) {

				File savedFile = JFC.getSelectedFile();

				if (savedFile.exists()) {
					int response = JOptionPane.showConfirmDialog (null, "FILE ALREADY EXISTS. REPLACE?", 
							"Select an Option...", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						fileName = savedFile.toString();
						fileWriter (fileName, dataset);
					}

					if (response == JOptionPane.NO_OPTION) {
						response = JOptionPane.CLOSED_OPTION;
					}
				}

				else {
					fileName = savedFile.toString();
					fileWriter (fileName, dataset);
					System.out.println("Here is the filename: " + fileName);
				}

			}
			System.out.println("And if you're seeing this, we did something!");	
		}


		/*Add*/
		if (e.getSource() == Button4) {

			System.out.println("We definitely hit button 4");
			DataPlotting = new DataPlotting ();
			DataPlotting.graph();
			System.out.println("And if you're seeing this, we did something!");
		}


		/*Get Data*/
		if (e.getSource() == Button5) {

			System.out.println("We definitely hit button 5");
			int ID = Integer.parseInt(TF6.getText().trim());
			Process2 = new Processing2 ();
			Process2.dataByframe(null);
			System.out.println("And if you're seeing this, we did something!");
		}


		/*This is the 'All Frames' button and is used to call a method for separating out the data
		 for individual pillars across the different frames.*/
		if (e.getSource() == Button6) {

			System.out.println("We definitely hit button AllFrame");
			displayOutput();
			Process2 = new Processing2 ();
			Process2.allFrames(Process.getNewData());
			System.out.println("And if you're seeing this, we did something!");
		}
	}


	@Override /*State change method for the slider - this will go if I can't find anything to do with it.*/
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() == JS1) {
			
			if (JS1.getValueIsAdjusting()) {
				int slider = JS1.getValue();
				TF6.setText(Integer.toString(slider));
			}	
		}
	}
}