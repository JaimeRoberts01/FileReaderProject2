import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame extends JFrame implements ActionListener {


	/**Instance variables*/

	private JTextArea displayFile;
	private JButton Button1, Button2;
	private JFileChooser JFC;
	private Processing Process;
	private FileManager FileManager;


	/**Constructor*/

	public ReportFrame (Processing Process) {	

		this.Process = Process;
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("Forces Data");
		setLocation (1500, 675);
		setSize (825, 400);
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


	/**This method formats a display screen for the newData array. Note that the data
	 * is a tidier version of the actual output data and is for viewing purposes only.
	 */

	public void reportFormatter () {

		displayFile.append (Process.outputString());	
	}


	/**FileChooser allows files to be saved in a particular directory and with a give name.
	 * The fileName is passed to the FileWriter method for saving the data.
	 */

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

	
	/**The method send the fileName to the the FileWriter in the FileManager class. The 
	 * FileWriter deals with a number of output files so an identifier is passed to the
	 * method identifying which dataset has been sent for saving.
	 */

	public void fileWriter (String fileName) {

		String identifier = "Data";
		FileManager = new FileManager (Process, null);
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