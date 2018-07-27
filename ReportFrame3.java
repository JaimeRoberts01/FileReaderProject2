import java.awt.*;
//import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame3 extends JFrame implements ActionListener{

	
	private Container contentPane;
	private JTextArea displayFile;
	private JButton Button1, Button2;
	
	private String [] values;
	
	
	private Processing Process;
	private Processing2 Process2;
	
	public ReportFrame3 () {	
		

		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("MultiPillar");
		setLocation (1500, 675);
		setSize (400, 300);
		setVisible (true);
		setResizable (false);
		setLayout(new SpringLayout());
		frameComponents ();
	}
	

	public void frameComponents () {
					
		contentPane = this.getContentPane();
		SpringLayout layout = new SpringLayout ();
		contentPane.setLayout(layout);
		contentPane.setBackground(Color.lightGray);
		
		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("Courier", Font.PLAIN, 14));	
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		displayFile.setBorder(BorderFactory.createLineBorder(Color.black));
		displayFile.setPreferredSize(new Dimension(396,233));
		displayFile.setEditable (true);
		contentPane.add(displayFile);
		
		Button1 = new JButton("OK");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button1.setOpaque(true);
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Button1.setEnabled(true);
		contentPane.add(Button1);
		
		Button2 = new JButton("Cancel");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setFont(new Font ("Consolas", Font.PLAIN, 14));
		Button2.setOpaque(true);
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Button2.setEnabled(true);
		contentPane.add(Button2);
		
		SpringLayout.Constraints displayFileCons = layout.getConstraints(displayFile);
		displayFileCons.setX(Spring.constant(2));
		displayFileCons.setY(Spring.constant(2));
		SpringLayout.Constraints Button1Cons = layout.getConstraints(Button1);
		Button1Cons.setX(Spring.sum(Spring.constant(63), displayFileCons.getConstraint(SpringLayout.NORTH)));
		Button1Cons.setY(Spring.constant(245));
		SpringLayout.Constraints Button2Cons = layout.getConstraints(Button2);
		Button2Cons.setX(Spring.sum(Spring.constant(208), displayFileCons.getConstraint(SpringLayout.NORTH)));
		Button2Cons.setY(Spring.constant(245));
	}
	
	

	public String[] getValues() {return values;}
	public void setValues(String[] values) {this.values = values;}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == Button1) {
			
			this.dispose();
			reportFormatter ();
			someMethod();
			
		}
		
		if (e.getSource() == Button2) {
			
			this.dispose();
		}
	}
	
	
	public String []  reportFormatter () {
		System.out.println("We are in ReportFrame3");
		
		values = displayFile.getText().split(", ");
		
		for (String s: values) {System.out.println(s);}
		return values;
	}
	
	
	public void someMethod () {
		
		for (int i = 0; i < values.length; i++) {
//			Process2 = new Processing2 ();
//			Process2.allFrames(Process.getNewData(), values);
			
		
	}
	}
}// class bracket