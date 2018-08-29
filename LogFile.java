import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public class LogFile {

	
	/*Instance Variables*/
	private JTextArea display;
	private JFrame frame;
	private JScrollPane scroll;

	
	/*Constructor*/
	public LogFile () {
	}


	/**This method lays out the log frame*/
	
	public void logComponents () {

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("----- Error Report -----");
		frame.setSize(750, 300);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());

		display = new JTextArea ();
		display.setLineWrap (true);
		display.setWrapStyleWord (true);
		display.setFont (new Font ("SansSerif", Font.ITALIC, 12));	
		display.setBorder(BorderFactory.createLineBorder(Color.black));
		display.setBorder (new EmptyBorder (10,10,10,10));
		display.setEditable (false);
		
		scroll = new JScrollPane (display);
		scroll.setPreferredSize(new Dimension (750, 300));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.add (scroll, BorderLayout.CENTER);
	}

	
	/**This method appends the message and printstack to the display 
	 * @param message - the error message
	 * @param stackTrace - the printstackTrace
	 */
	
	public void writeToLog (String message, String stackTrace) {

		logComponents ();
		
		display.append(message + "\n\n");
		display.append(stackTrace);
		display.setCaretPosition(0);
	}
}