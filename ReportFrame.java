import java.awt.*;


import javax.swing.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame extends JFrame {

	
	/*Instance variables*/
	private JTextArea displayFile;


	/*Constructor*/
	public ReportFrame () {		

		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("File Contents");
		setLocation (1500, 330);
		setSize (700, 330);
		setVisible (true);
		setResizable (false); 
		frameComponents ();
	}

	/*GUI layout*/
	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("Courier", Font.PLAIN, 14));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		displayFile.setEditable (false);
		JScrollPane scroll = new JScrollPane (displayFile);
		add (scroll);
	}


	/*This method prints a copy of the input file into a reader*/
	public void reportFormatter (String l) {
		
		displayFile.append (l + "\n");	
	}	
}