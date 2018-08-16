import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class MultipillarInput extends JFrame implements ActionListener{


	/*Instance Variables*/
	private JTextArea displayFile;
	private JButton Button1, Button2;
	private Object [] values;

	private Processing Process;
	private Processing2 Process2;
	private BarGraph BarGraph;
	//private LineChart LineChart;
	private LineChartTest LineChartTest;


	/*Getters & Setters*/
	public Object[] getValues() {return values;}
	public void setValues(Object[] values) {this.values = values;}

	public MultipillarInput () {
		
	}

	/*Constructor*/
	public MultipillarInput (Processing Process) {	

		this.Process = Process;
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("Multipillar Data");
		setLocation (1500, 675);
		setSize (400, 400);
		setVisible (true);
		setResizable (false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		frameComponents ();
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


	@Override /*ActionPerformed methods for the individual buttons*/
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) {

			this.dispose();
			multipillarValues ();		
		}

		if (e.getSource() == Button2) {

			this.dispose();
		}
	}


	/*This method gets the pillar values from the MultiPillar TextArea and sends the
		 values to a method in Processing2 for AVG and SD across all frames*/
	public Object []  multipillarValues () {

		values = displayFile.getText().split(", ");
		Process2 = new Processing2 (this);
		Process2.getPillars(Process.getNewData());
		Process2.multiPillar(Process.getNewData());
		
		int ID = 0;
		
		for (Object o: values) { ID = Integer.parseInt((String) o); System.out.println(o); System.out.println ("ID: " + ID);}
		
		BarGraph = new BarGraph (Process2);
		BarGraph.barChartData();
		
		LineChartTest = new LineChartTest (Process2);
		LineChartTest.lineChartData();
		
		return values;
	}
}