import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


/**Class to define a window for entering multiple pillar IDs*/

public class MultipillarInput implements ActionListener{


	/*Instance Variables*/
	private JTextArea displayFile;
	private JButton button1, button2;
	private JPanel panel;
	private JFrame frame;
	private Object [] ID;

	private DataArray DataArray;
	private DataProcessing DataProcessing;

	
	/* Default Constructor*/
	public MultipillarInput () {
	}


	/*Constructor*/
	public MultipillarInput (DataArray DataArray, DataProcessing DataProcessing) {	

		this.DataArray = DataArray;
		this.DataProcessing = DataProcessing;
	}


	/*Getters*/
	public Object[] getID() {return ID;}
	
	
	/**GUI layout*/
	
	public void frameComponents () {
		
		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle ("Multipillar Data");
		frame.setSize(400, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("SansSerif", Font.PLAIN, 14));	
		displayFile.setBorder(BorderFactory.createLineBorder(Color.black));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		JScrollPane scroll = new JScrollPane (displayFile);
		scroll.setPreferredSize(new Dimension (400, 345));
		frame.add (scroll, BorderLayout.CENTER);
		
		panel = new JPanel ();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		frame.add(panel, BorderLayout.SOUTH);

		button1 = new JButton("OK");
		button1.setPreferredSize(new Dimension(125,23));
		button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button1.setOpaque(true);
		button1.setBorder(BorderFactory.createLineBorder(Color.black));
		button1.addActionListener (this);
		button1.setEnabled(true);
		panel.add (button1);

		button2 = new JButton("Cancel");
		button2.setPreferredSize(new Dimension(125,23));
		button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		button2.setOpaque(true);
		button2.setBorder(BorderFactory.createLineBorder(Color.black));
		button2.addActionListener (this);
		button2.setEnabled(true);
		panel.add (button2);
	}


	/**ActionPerformed methods for the individual buttons*/

	@Override 
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button1) {

			frame.dispose();
			multipillarValues ();
		}

		if (e.getSource() == button2) {

			frame.dispose();
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

			DataProcessing.getPillars(DataArray.getData());

			for (int j = 0 ; j<ID.length; j++) {

				int value = Integer.parseInt((String)ID [j]);

				for (int i =0; i<DataProcessing.getPillar().size(); i++) {

					if (!DataProcessing.getPillar().contains(value)) {

						String message = "Invalid pillar index - check values: " + Arrays.toString(ID);

						LogFile log = new LogFile ();
						log.writeToLog(message, null);
						// Calls the log file if there is an error.
						return null;
					}
				}
			}

			DataProcessing = new DataProcessing (this);
			DataProcessing.getPillars(DataArray.getData());
			DataProcessing.multiPillar(DataArray.getDataArray());
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