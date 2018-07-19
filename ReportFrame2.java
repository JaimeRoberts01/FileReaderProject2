import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class ReportFrame2 extends JFrame {

	private JTextArea displayFile;
	private Processing Process;

	public ReportFrame2 (Processing Process) {	// For testing the output data without having to search for it in the file system - consider changing to a log.
		
		this.Process = Process;
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("Output File");
		setLocation (1500, 675);
		setSize (825, 400);
		setVisible (true);
		//setResizable (false);
		frameComponents ();
	}


	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("Courier", Font.PLAIN, 14));	
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		displayFile.setEditable (false);
		add (displayFile);

		//scrollPane = new JScrollPane (displayFile);
		//scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.setPreferredSize (new Dimension (200, 200));
		//add (scrollPane);
	}


	public void reportFormatter () {

		String header = (String.format("%s %11s %11s %15s %19s %17s %15s", "frame", "pillar", "dx", "dy", "deflection", "nanometers", "picoNewtons")+"\n");
		String bar = "----------------------------------------------------------------------------------------------------";
		displayFile.append(header);
		displayFile.append (bar+ "\n\n");
		displayFile.append (Process.outputString());
	}
}
