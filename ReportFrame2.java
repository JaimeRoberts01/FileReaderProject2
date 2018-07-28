import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class ReportFrame2 extends JFrame {

	private JTextArea displayFile;

	private Processing Process;

	/*Constructor*/
	public ReportFrame2 (Processing Process) {	
		
		this.Process = Process;
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("Output File");
		setLocation (1500, 675);
		setSize (825, 400);
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
		displayFile.setEditable (false);;
		JScrollPane scroll = new JScrollPane (displayFile);
		add (scroll);
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
}