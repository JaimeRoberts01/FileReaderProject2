import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportTable extends JFrame implements ActionListener {
	
	
	/*Instance variables*/
	private JTable displayTable;
	private JButton Button1, Button2;
	private JFrame frame;
	
	private Processing Process;
	private OutputData OutputData;


	/*Constructor*/	
	public ReportTable (Processing Process) {		
	}
	
	/**This method formats the JTable
	 * @ param columnNames - column header
	 * @ param output - row data.
	 */

	public void JTable (String [] columnNames, Object [][] output) {

		displayTable = new JTable (output, columnNames) {	

			public boolean isCellEditable (int output, int columnNames) {
				return false;
			}			
		};

		displayTable.setFont (new Font ("SansSerif", Font.PLAIN, 12));	
		displayTable.setBorder(BorderFactory.createLineBorder(Color.black));
		displayTable.setBorder (new EmptyBorder (10,10,10,10));
		displayTable.setFillsViewportHeight(true);
		displayTable.setGridColor(Color.lightGray);
		displayTable.getTableHeader().setFont(new Font ("SansSerif", Font.BOLD, 12));

		JScrollPane scroll = new JScrollPane (displayTable);
		scroll.setPreferredSize(new Dimension (600, 345));
		add (scroll);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer (); 
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT); // Column data right.
		displayTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		displayTable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		displayTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		displayTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		displayTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

		displayTable.setColumnSelectionAllowed(true);
		displayTable.setRowSelectionAllowed(true);
		
		frameLayout(scroll);
	}


	/**This method lays out the frame*/

	public void frameLayout (JScrollPane scroll) {

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Forces Data");
		frame.setSize(610, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

		frame.add(scroll);

		Button1 = new JButton("Save");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button1.setOpaque(true);
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Button1.setEnabled(true);
		frame.add (Button1);

		Button2 = new JButton("Close");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button2.setOpaque(true);
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Button2.setEnabled(true);
		frame.add (Button2);	
	}


	/**FileChooser - save location*/

	public void fileChooser () {

		JFileChooser JFC = new JFileChooser();
		String fileName = null;
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


	/**The method sends fileName to the the FileWriter in the FileManager class. 
	 * The FileWriter deals with a number of output files so an identifier is 
	 * passed to the method identifying which dataset has been sent for saving.
	 * @param fileName - name of the file to be saved.
	 */

	public void fileWriter (String fileName) {

		String identifier = "Data";
		FileManager FileManager = new FileManager (Process, null, OutputData);
		FileManager.fileWriter(identifier, fileName);	
	}


	/**ActionPerformed methods for the individual buttons*/

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) {

			fileChooser();
		}

		if (e.getSource() == Button2) {

			frame.dispose();
		}
	}
}