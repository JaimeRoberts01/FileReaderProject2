import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class MultipillarInput extends JFrame implements ActionListener{


	/*Instance Variables*/
	private JTextArea displayFile;
	private JButton Button1, Button2;
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


	/*GUI layout*/
	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("Courier", Font.PLAIN, 14));	
		displayFile.setBorder(BorderFactory.createLineBorder(Color.black));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		JScrollPane scroll = new JScrollPane (displayFile);
		scroll.setPreferredSize(new Dimension (400, 345));
		add (scroll);

		Button1 = new JButton("OK");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button1.setOpaque(true);
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Button1.setEnabled(true);
		add (Button1);

		Button2 = new JButton("Cancel");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button2.setOpaque(true);
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Button2.setEnabled(true);
		add (Button2);
	}


	/**ActionPerformed methods for the individual buttons*/
	
	@Override 
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) {

			this.dispose();
			multipillarValues ();
		}

		if (e.getSource() == Button2) {

			this.dispose();
		}
	}

	/**Getters*/
	
	public Object[] getID() {return ID;}
	
	
	/**This method gets the pillar values from the MultiPillar TextArea and sends the values 
	 * to a method in Processing2 to obtain mean and standard deviation across all frames
	 */
	
	public Object []  multipillarValues () {

			if (displayFile.getText().trim().equals("")) {
				
				System.err.println("Ivalid input - no valid pillar indices");
				return null;
			}
			
			ID = displayFile.getText().trim().split(" ");

			Process2 = new Processing2 (this);
			Process2.getPillars(Process.getData());
			Process2.multiPillar(Process.getNewData());
			
			return ID;
	}
}