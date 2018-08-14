import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;


import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame3 extends JFrame implements ActionListener {


	private JTextArea displayFile;
	private JButton Button1, Button2, Button3;
	private JFileChooser JFC;
	private FileManager FileManager;
	private Processing Process;
	private Processing2 Process2;
	private LineChart LineChart;
	
	private String identifier;
	private int ID;


	/*Constructor*/
	public ReportFrame3 (Processing2 Process2, String identifier, int ID) {	

		this.Process2 = Process2;
		this.identifier = identifier;
		this.ID = ID;
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle (identifier + " " + ID);
		setLocation (1500, 675);
		setSize (400, 400);
		setVisible (true);
		setResizable (false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		frameComponents ();
	}


	/*GUI layout*/
	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("Courier", Font.PLAIN, 14));	
		displayFile.setBorder(BorderFactory.createLineBorder(Color.black));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		displayFile.setEditable (false);
		JScrollPane scroll = new JScrollPane (displayFile);
		scroll.setPreferredSize(new Dimension (400, 345));
		add (scroll);

		Button1 = new JButton("Save");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button1.setOpaque(true);
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Button1.setEnabled(true);
		add(Button1);

		Button2 = new JButton("Close");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button2.setOpaque(true);
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Button2.setEnabled(true);
		add(Button2);
		
		Button3 = new JButton("Graph");
		Button3.setPreferredSize(new Dimension(125,23));
		Button3.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button3.setOpaque(true);
		Button3.setBorder(BorderFactory.createLineBorder(Color.black));
		Button3.addActionListener (this);
		Button3.setEnabled(true);
		add(Button3);
	}


	/*This method formats a display screen for the newData array values (except x and y).*/
	public void reportFormatter (String output) {	
		
			displayFile.append(output);
	}


	/*FileChooser allows files to be saved in a particular directory and with a give name.*/
	public void fileChooser () {

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
					fileWriter (fileName);
				}

				else if (response == JOptionPane.NO_OPTION) {
					response = JOptionPane.CLOSED_OPTION;
				}
			}

			else {
				fileName = savedFile.toString();
				fileWriter (fileName);	
			}
		}
	}


	/*FileWriter writes text to a file. The output file is formatted differently from that
	 displayed on the ReportFrame screen.*/
	public void fileWriter (String fileName) {

		
		FileManager = new FileManager (null, Process2);
		FileManager.fileWriter(identifier, fileName);	
	}


	@Override /*ActionPerformed methods for the individual buttons*/
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) {
   
			this.fileChooser();

		}

		if (e.getSource() == Button2) {

			this.dispose();
		}
		
		if (e.getSource() == Button3) {

			LineChart = new LineChart (Process2, Process);
			LineChart.lineGraphData(ID);
		}
	}
}