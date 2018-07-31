import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame extends JFrame implements ActionListener {
	

	/*Instance variables*/
	private JTextArea displayFile;
	private JButton Button1, Button2;
	private JFileChooser JFC;
	private Processing Process;


	/*Constructor*/
	public ReportFrame (Processing Process) {	

		this.Process = Process;
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("Calculate Forces");
		setLocation (1500, 675);
		setSize (825, 400);
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
		scroll.setPreferredSize(new Dimension (825, 345));
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
	}


	/*This method formats a display screen for the newData array values (except x and y).*/
	public void reportFormatter () {

		String header_upper = (String.format("%s %11s %11s %15s %19s %16s %13s", "Frame", "Pillar", "dx", "dy", "Deflection", "Deflection", "Force")+"\n");
		String header_lower = (String.format("%s %10s %11s %15s %17s %16s %15s", "Index", "Index", " ", " ", "(px)", "(nm)", "(pN)")+"\n");
		String bar = "--------------------------------------------------------------------------------------------------";
		displayFile.append(header_upper);
		displayFile.append(header_lower);
		displayFile.append (bar+ "\n\n");
		displayFile.append (Process.outputString());	
	}


	/*FileWriter writes text to a file. The output file is formatted differently from that
	 displayed on the ReportFrame screen.*/
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


	@Override /*ActionPerformed methods for the individual buttons*/
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) {

			this.fileChooser();

		}

		if (e.getSource() == Button2) {

			this.dispose();
		}
	}
}