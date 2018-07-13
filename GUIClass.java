import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class GUIClass extends JFrame implements ActionListener {

	private JPanel Panel1, Panel2, Panel3;
	private JLabel Label1, Label2, Label3, Label4, Label5, Label6, Label7;
	private JComboBox<String> JB1;
	private JTextField TF1, TF2, TF3, TF4, TF5, TF6;
	private JTextArea JT1;
	private JFileChooser JFC;
	private JButton Button1, Button2, Button3, Button4;

	private ReportFrame ReportFrame;
	private ReportFrame2 ReportFrame2;
	private Processing Process;

	private ArrayList <String> fileLine;
	//private String ID;
	private double conversion, youngsModulous, pillarD, pillarL;

	public ArrayList<String> getFileLine() {return fileLine;}
	public void setFileLine(ArrayList<String> fileLine) {this.fileLine = fileLine;}
	public double getConversion() {return conversion;}
	public void setConversion(double conversion) {this.conversion = conversion;}
	public double getYoungsModulous() {return youngsModulous;}
	public void setYoungsModulous(double youngsModulous) {this.youngsModulous = youngsModulous;}
	public double getPillarD() {return pillarD;}
	public void setPillarD(double pillarD) {this.pillarD = pillarD;}
	public double getPillarL() {return pillarL;}
	public void setPillarL(double pillarL) {this.pillarL = pillarL;}
//	public String getID() {return ID;}
//	public void setID(String iD) {ID = iD;}
	
	
	public GUIClass ()  {

		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle ("Jemma's Test GUI");
		setSize (700, 300);
		setLocation (1500,0);
		setResizable (false);
		setLayout (new GridLayout (3,1));
		GUIComponents ();
	}


	public void GUIComponents () { // Basic GUI setup.

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

		JT1 = new JTextArea ();
		JT1.setSize(20, 100);
		JT1.setEditable(false);
		Panel2.add(JT1);
		JT1.setEnabled(true);

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
	}

	
	public void inputData () {
		
		conversion = Double.parseDouble(TF1.getText().trim());
		youngsModulous = Double.parseDouble(TF2.getText().trim());
		pillarD = Double.parseDouble(TF3.getText().trim());
		pillarL = Double.parseDouble(TF4.getText().trim());
		//ID = TF6.getText().trim();
		//System.out.println("HERE IS ID: " + ID);
	}


	public void fileReader () { // Get my file - eventually the file size will be too much for this at 9000 lines. 

		FileReader reader = null;
		FileWriter writer1 = null;
		BufferedReader bufferedReader = null;
		String file = "";//null;
		String line = "";//null;
		ReportFrame = new ReportFrame ();

		try {

			file = TF5.getText();
			fileLine = new ArrayList<String>();

			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) !=null) {
				fileLine.add (line);
			}

			writer1 = new FileWriter ("output2.csv"); //This shows that the extra bit appended on does not happen here.
			
			for (String FU : fileLine) {
			writer1.write(FU +"\n");
			System.out.println(FU);
			}
			writer1.close();

			
			
			
			
			
			for (String s : fileLine) {
				ReportFrame.reportFormatter(s);
				//System.out.println (s);
			}

			Process = new Processing (fileLine);	

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


	public void fileWriter () {

		FileWriter writer = null;	
		String file = null;

		try {

			try {

				file = "fileOut.csv";
				writer = new FileWriter (file);	
				writer.write(Process.outputFile());
			}

			finally {

				writer.close();	
			}
		}

		catch (IOException IOE) {

			JOptionPane.showMessageDialog (null, "FILE NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
			IOE.printStackTrace();	
		}
	}


	public void displayOutput () { // This is for the Output Display - testing for correct output.

		ReportFrame2 = new ReportFrame2 (Process);	
		ReportFrame2.reportFormatter();
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) { 
			System.out.println("We definitely hit button 1");
			inputData ();
			Process.nanoMeters(conversion);
			Process.forces(youngsModulous, pillarD, pillarL);
			Process.newDataArray();
			System.out.println("And if you're seeing this, we did something!");
		}

		if (e.getSource() == Button2) {
			
			System.out.println("We definitely hit button 2");
			displayOutput();
			String ID = TF6.getText().trim();
//			
			if (JB1.getSelectedIndex() == 0 ) {	
				Process.byFrame (ID);
			}
			
			else if (JB1.getSelectedIndex() == 1) {
				Process.byPillar (ID);
			}
			System.out.println("And if you're seeing this, we did something!");
		}

		if (e.getSource() == Button3) {
			System.out.println("We definitely hit button 3");	// Not sure what I'm doing with this one
//			JFC = new JFileChooser();
//			int saveVal = JFC.showSaveDialog(GUIClass.this);
//			
//			if (saveVal == JFileChooser.APPROVE_OPTION) {
//				//String savedFileName = TF5.getText();
//			}
			fileWriter ();
			System.out.println("And if you're seeing this, we did something!");	
		}

		if (e.getSource() == Button4) {
			JFC = new JFileChooser ();
			JFC.setMultiSelectionEnabled(false);
			int openVal = JFC.showDialog(GUIClass.this, "Select");
			
			if (openVal == JFileChooser.APPROVE_OPTION) {
				File selected =	JFC.getSelectedFile();
				TF5.setText(selected.toString());
				fileReader ();
			}
		}
	}
}