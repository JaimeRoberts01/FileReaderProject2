import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;


import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame2 extends JFrame implements ActionListener {


	/**Instance Variables*/
	
	private JTextArea displayFile;
	private JButton Button1, Button2;
	
	private String identifier;
	@SuppressWarnings("unused")
	private int ID;
	
	private FileManager FileManager;
	private Processing2 Process2;
	private OutputData OutputData;


	/**Constructor*/
	
	public ReportFrame2 (Processing2 Process2, String identifier, int ID) {	

		this.Process2 = Process2;
		this.identifier = identifier;
		this.ID = ID;
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		
		if (ID == 0) {
			setTitle  (identifier); 
		}
		else {
			setTitle (identifier + " " + ID);
		}
		
		setLocationRelativeTo(null);
		setSize (400, 400);
		setVisible (true);
		setResizable (false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		frameComponents ();
	}


	/**GUI layout*/
	
	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("SansSerif", Font.PLAIN, 14));	
		displayFile.setBorder(BorderFactory.createLineBorder(Color.black));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		displayFile.setEditable (false);
		JScrollPane scroll = new JScrollPane (displayFile);
		scroll.setPreferredSize(new Dimension (400, 345));
		add (scroll);

		Button1 = new JButton("Save");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button1.setOpaque(true);
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Button1.setEnabled(true);
		add(Button1);

		Button2 = new JButton("Close");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button2.setOpaque(true);
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Button2.setEnabled(true);
		add(Button2);
	}


	/**This method formats a display screen for the getBy data including. Note that the data
	 * is a tidier version of the actual output data and is for viewing purposes only.
	 */
	
	public void reportFormatter (String output) {
		
			displayFile.append(output);
	}


	/**FileChooser allows files to be saved in a particular directory and with a give name.
	 * The fileName is passed to the FileWriter method for saving the data.
	 */
	
	public void fileChooser () {

		JFileChooser JFC = new JFileChooser();
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
	

	/**The method send the fileName to the the FileWriter in the FileManager class. The 
	 * FileWriter deals with a number of output files so an identifier is passed to the
	 * method identifying which dataset has been sent for saving.
	 */
	
	public void fileWriter (String fileName) {

		
		FileManager = new FileManager (null, Process2, OutputData);
		FileManager.fileWriter(identifier, fileName);	
	}


	/**ActionPerformed methods for the individual buttons*/
	
	@Override 
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) {
   
			this.fileChooser();

		}

		if (e.getSource() == Button2) {

			this.dispose();
		}
	}
}