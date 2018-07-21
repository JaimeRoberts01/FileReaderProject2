import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;


@SuppressWarnings("serial")
public class GUIClass extends JFrame implements ActionListener, ChangeListener {

	/*Instance variables*/
	private JPanel Panel1, Panel2, Panel3, Panel4;
	private JLabel Label1, Label2, Label3, Label4, Label5, Label6, Label7;
	private JComboBox<String> JB1;
	private JTextField TF1, TF2, TF3, TF4, TF5, TF6, TF7;
	private JSlider JS1;
	private JFileChooser JFC;
	private JButton Button1, Button2, Button3, Button4, Button5;

	private ReportFrame ReportFrame;
	private ReportFrame2 ReportFrame2;
	private Processing Process;
	//private FileManager FileManager;

	private ArrayList <String> fileLine;


	/*Getters and Setters*/
	public ArrayList<String> getFileLine() {return fileLine;}
	public void setFileLine(ArrayList<String> fileLine) {this.fileLine = fileLine;}


	/*Constructor*/
	public GUIClass ()  {

		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle ("Jemma's Test GUI");
		setSize (700, 300);
		setLocation (1500,0);
		setResizable (false);
		setLayout (new GridLayout (4,1));
		GUIComponents ();
	}


	/*Basic GUI setup*/
	public void GUIComponents () {

		Panel1 = new JPanel ();
		Panel1.setBackground (Color.lightGray);
		add (Panel1);

		Label1 = new JLabel ("Pixel to nm:");
		Panel1.add (Label1);
		Label1.setEnabled (true);

		TF1 = new JTextField (4);
		TF1.addActionListener (this);
		Panel1.add(TF1);
		TF1.setEnabled(true);

		Label2 = new JLabel ("Substrate (E):");
		Panel1.add (Label2);
		Label2.setEnabled (true);

		TF2 = new JTextField (4);
		TF2.addActionListener (this);
		Panel1.add(TF2);
		TF2.setEnabled(true);

		Label3 = new JLabel ("Pillar Diameter (µm):");
		Panel1.add (Label3);
		Label3.setEnabled (true);

		TF3 = new JTextField (4);
		TF3.addActionListener (this);
		Panel1.add(TF3);
		TF3.setEnabled(true);

		Label4 = new JLabel ("Pillar Length (µm):");
		Panel1.add (Label4);
		Label4.setEnabled (true);

		TF4 = new JTextField (4);
		TF4.addActionListener (this);
		Panel1.add(TF4);
		TF4.setEnabled(true);

		Button1 = new JButton ("Calculate Force");
		Button1.setPreferredSize(new Dimension(125,25));
		Button1.addActionListener (this);
		Panel1.add (Button1);
		Button1.setEnabled (true);

		Button3 = new JButton ("Save Data");
		Button3.setPreferredSize(new Dimension(125,25));
		Button3.addActionListener (this);
		Panel1.add (Button3);
		Button3.setEnabled (true);


		Panel2 = new JPanel ();
		Panel2.setBackground (Color.lightGray);
		add (Panel2);

		Label5 = new JLabel ("Get data by:");
		Panel2.add (Label5);
		Label5.setEnabled (true);

		String [] comboBox = {"Frame", "Pillar"};
		JB1 = new JComboBox <String>(comboBox);
		JB1.addActionListener (this);
		Panel2.add(JB1);
		JB1.setEnabled(true);

		Label7 = new JLabel ("ID:");
		Panel2.add (Label7);
		Label7.setEnabled (true);

		TF6 = new JTextField (4);
		TF6.addActionListener (this);
		Panel2.add(TF6);
		TF6.setEnabled(true);

		Button2 = new JButton ("Get Data");
		Button2.setPreferredSize(new Dimension(125,25));
		Button2.addActionListener (this);
		Panel2.add (Button2);
		Button2.setEnabled (true);

		Button5 = new JButton ("All Frames");
		Button5.setPreferredSize(new Dimension(125,25));
		Button5.addActionListener (this);
		Panel2.add (Button5);
		Button5.setEnabled (true);


		Panel3 = new JPanel ();
		Panel3.setBackground (Color.lightGray);
		add (Panel3);

		Label6 = new JLabel ("File:");
		Panel3.add(Label6);
		Label6.setEnabled(true);

		TF5 = new JTextField(43);
		TF5.addActionListener (this);
		Panel3.add(TF5);
		TF5.setEnabled(true);

		Button4 = new JButton ("Browse");
		Button4.setPreferredSize(new Dimension(125,25));
		Button4.addActionListener (this);
		Panel3.add (Button4);
		Button4.setEnabled (true);


		Panel4 = new JPanel ();
		Panel4.setBackground (Color.lightGray);
		add(Panel4);

		JS1 = new JSlider (JSlider.HORIZONTAL); // There's no point in doing this but I want a reference of it anyway
		JS1.addChangeListener(this);
		JS1.setMaximum(100);
		JS1.setMinimum(0);
		JS1.setValue(1);
		JS1.setPreferredSize(new Dimension(500, 50));
		Panel4.add(JS1);
		JS1.setEnabled(true);

		TF7 = new JTextField(4);
		TF7.addActionListener (this);
		TF7.setHorizontalAlignment((int) TextField.CENTER_ALIGNMENT);
		TF7.setText(Integer.toString(1));
		TF7.setEditable(false);
		Panel4.add(TF7);
		TF7.setEnabled(true);
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
	public void fileWriter (String fileName) {

		FileWriter writer = null;
		String file = null;

		try {

			try {

				file = fileName;
				writer = new FileWriter (file);	
				writer.write(Process.outputFile());
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

		/*This is the 'Calculate Forces' button and calls methods for calculating the forces.*/
		if (e.getSource() == Button1) { 
			System.out.println("We definitely hit button 1");
			int conversion = Integer.parseInt(TF1.getText().trim());
			double youngsModulous = Double.parseDouble(TF2.getText().trim());
			double pillarD = Double.parseDouble(TF3.getText().trim());
			double pillarL = Double.parseDouble(TF4.getText().trim());
			Process.nanoMeters(conversion);
			Process.forces(youngsModulous, pillarD, pillarL);
			Process.newDataArray();
			System.out.println("And if you're seeing this, we did something!");
		}

		/**/
		if (e.getSource() == Button2) {

			System.out.println("We definitely hit button 2");
			//			displayOutput();
			//			int ID = Integer.parseInt(TF6.getText().trim());

			//			if (JB1.getSelectedIndex() == 0 ) {	
			//				Process.byFrame (ID);
			//			}
			//
			//			else if (JB1.getSelectedIndex() == 1) {
			//				Process.byPillar (ID);
			//			}
			System.out.println("And if you're seeing this, we did something!");
		}

		/*This is the 'Save' button. It calls the fileReader and passes it a string for the file name.If
		 a file with this name exists, the user is given the option to change the name or overwrite.*/
		if (e.getSource() == Button3) { // save Button

			System.out.println("We definitely hit button 3");	// Not sure what I'm doing with this one

			JFC = new JFileChooser();
			int saveVal = JFC.showSaveDialog(null);

			if (saveVal == JFileChooser.APPROVE_OPTION) {

				File savedFile = JFC.getSelectedFile();

				if (savedFile.exists()) {
					int response = JOptionPane.showConfirmDialog (null, "FILE ALREADY EXISTS. REPLACE?", 
							"Select an Option...", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						String fileName = savedFile.toString();
						fileWriter (fileName);
					}

					if (response == JOptionPane.NO_OPTION) {
						response = JOptionPane.CLOSED_OPTION;
					}
				}

				else {
					String fileName1 = savedFile.toString();
					fileWriter (fileName1);
					System.out.println("Here is the filename: " + fileName1);
				}

			}
			System.out.println("And if you're seeing this, we did something!");	
		}

		/*This is the 'Browse' button and enables the user to open a file. A string is passed to the
		 texfield so the user can see which file has been opened.*/
		if (e.getSource() == Button4) {
			JFC = new JFileChooser ();
			JFC.setMultiSelectionEnabled(false);
			int openVal = JFC.showDialog(null, "Select");

			if (openVal == JFileChooser.APPROVE_OPTION) {
				File selectedFile =	JFC.getSelectedFile();
				TF5.setText(selectedFile.toString());
				String fileName = TF5.getText();

				System.out.println("THIS IS TF5;" + TF5.getText());
				fileReader(fileName);
				//				String fileName = TF5.getText();
				//				System.out.println("THIS IS FILEZ" + fileName);
				//				FileManager = new FileManager ();
				//				FileManager.fileReader (fileName);
			}

		}

		
		/*This is the 'All Frames' button and is used to call a method for separating out the data
		 for individual pillars across the different frames.*/
		if (e.getSource() == Button5) {

			System.out.println("We definitely hit button AllFrame");
			displayOutput();
			Process.getNumberOfUniqueFrames();
			Process.getNumberOfUniquePillars ();
			Process.allFrames();
			System.out.println("And if you're seeing this, we did something!");
		}
	}


	
	@Override /*State change method for the slider - this will go if I can't find anything to do with it.*/
	public void stateChanged(ChangeEvent e) {

		if (e.getSource() == JS1) {
			if (JS1.getValueIsAdjusting()) {
				int slider = JS1.getValue();
				TF7.setText(Integer.toString(slider));
			}	
		}
	}
}