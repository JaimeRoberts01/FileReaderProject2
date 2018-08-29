import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


/**Class to define a window for entering multiple pillar IDs*/

@SuppressWarnings("serial")
public class MultipillarInput extends JFrame implements ActionListener{


	/*Instance Variables*/
	private JTextArea displayFile;
	private JButton button1, button2;
	private Object [] ID;

	private Processing Process;
	private Processing2 Process2;


	/* Default Constructor*/
	public MultipillarInput () {
	}


	/*Constructor*/
	public MultipillarInput (Processing Process, Processing2 Process2) {	

		this.Process = Process;
		this.Process2 = Process2;

		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("Multipillar Data");
		setLocationRelativeTo(null);
		setSize (400, 400);
		setVisible (true);
		setResizable (false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
	}


	/*Getters*/
	public Object[] getID() {return ID;}
	
	
	/**GUI layout*/
	
	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("SansSerif", Font.PLAIN, 14));	
		displayFile.setBorder(BorderFactory.createLineBorder(Color.black));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		JScrollPane scroll = new JScrollPane (displayFile);
		scroll.setPreferredSize(new Dimension (400, 345));
		add (scroll);

		button1 = new JButton("OK");
		button1.setPreferredSize(new Dimension(125,23));
		button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button1.setOpaque(true);
		button1.setBorder(BorderFactory.createLineBorder(Color.black));
		button1.addActionListener (this);
		button1.setEnabled(true);
		add (button1);

		button2 = new JButton("Cancel");
		button2.setPreferredSize(new Dimension(125,23));
		button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button2.setOpaque(true);
		button2.setBorder(BorderFactory.createLineBorder(Color.black));
		button2.addActionListener (this);
		button2.setEnabled(true);
		add (button2);
	}


	/**ActionPerformed methods for the individual buttons*/

	@Override 
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button1) {

			dispose();
			multipillarValues ();
		}

		if (e.getSource() == button2) {

			dispose();
		}
	}


	/**This method gets the pillar values from the MultiPillar TextArea and sends the values 
	 * to a method in Processing2 to obtain mean and standard deviation across all frames
	 * @return ID - pillar indices.
	 */

	public Object []  multipillarValues () {

		if (displayFile.getText().trim().equals("")) {

			String message = "Invalid input - no valid pillar indices";				
			LogFile log = new LogFile ();
			log.writeToLog(message, null);

			return null;
		}

		try {

			ID = displayFile.getText().trim().split(" ");

			Process2.getPillars(Process.getData());

			for (int j = 0 ; j<ID.length; j++) {

				int value = Integer.parseInt((String)ID [j]);

				for (int i =0; i<Process2.getPillar().size(); i++) {

					if (!Process2.getPillar().contains(value)) {

						String message = "Invalid pillar index - check values: " + Arrays.toString(ID);

						LogFile log = new LogFile ();
						log.writeToLog(message, null);
						// Calls the log file if there is an error.
						return null;
					}
				}
			}

			Process2 = new Processing2 (this);
			Process2.getPillars(Process.getData());
			Process2.multiPillar(Process.getNewData());
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

		return ID;
	}
}