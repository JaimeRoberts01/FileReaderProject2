import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.TableView;

@SuppressWarnings("serial")
public class Demo extends JFrame implements ActionListener {

	private JTable displayTable;
	private Processing Process;
	private JButton Button1, Button2;
	private String [] columnNames;
	private Object [][] output;
	
	private Class [] columnClass;


	public Demo (Processing Process) {

		this.Process = Process;
		
		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("Forces Data");
		setLocationRelativeTo(null);
		setSize (825, 400);
		setVisible (true);
		setResizable (true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
		frameComponents ();
		
	}

	public void frameComponents() {

		//displayTable = new JTable ();
		
	}

	public void reportFormatter (String [] columnNames, Object [][] output) {
		
		this.output = output;
		this.columnNames = columnNames;
		
		displayTable = new JTable (output, columnNames) {	

		
		public boolean isCellEditable (int output, int columnNames) {
			return false;
		}
	};

	
	displayTable.setFont (new Font ("SansSerif", Font.PLAIN, 14));	
	displayTable.setBorder(BorderFactory.createLineBorder(Color.black));
	displayTable.setBorder (new EmptyBorder (10,10,10,10));
	displayTable.setFillsViewportHeight(true);
	displayTable.getTableHeader().setFont(new Font ("SansSerif", Font.BOLD, 14));
	JScrollPane scroll = new JScrollPane (displayTable);
	scroll.setPreferredSize(new Dimension (825, 345));
	add (scroll);
	
	//DefaultTableModel model = new DefaultTableModel ();
	
	
		Button1 = new JButton("Save");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button1.setOpaque(true);
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		Button1.setEnabled(true);
		add(Button1);

		Button2 = new JButton("Close");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button2.setOpaque(true);
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		Button2.setEnabled(true);
		add(Button2);
		
		//tableFormatter();
	
	}
	
	public void tableFormatter () {
		
		//displayTable = new JTable (output, columnNames) {
			
//			public boolean isCellEditable (int output, int columnNames) {
//				return false;
//			}
//			
//			public Component prepareRenderer(TableCellRenderer r, int output, int columnNames) {
//				Component c = super.prepareRenderer(r, output, columnNames);
//				
//				if (output % 2 == 0) {
//					
//					c.setBackground(Color.white);
//				}
//					
//				else {
//					
//					c.setBackground(Color.lightGray);	
//				}
//				return c;
//			}
//		};
		
		
		
		
	
		TableColumnModel cm = displayTable.getColumnModel();
		//cm.getColumn(0).setCellRenderer((displayTable.getDefaultRenderer(columnClass)));
		cm.getColumn(1).sizeWidthToFit();
		cm.getColumn(2).sizeWidthToFit();
		cm.getColumn(3).sizeWidthToFit();
		cm.getColumn(4).sizeWidthToFit();
		cm.getColumn(5).sizeWidthToFit();
		cm.getColumn(6).sizeWidthToFit();
		
		
		
//		m.getColumn(0).setCellRenderer(FormatRenderer.getDateTimeRenderer());
//		m.getColumn(0).setCellRenderer(cellRenderer);
//		m.getColumn(1).setCellRenderer(FormatRenderer.getTimeRenderer());
//		m.getColumn(2).setCellRenderer(NumberRenderer.getPercentRenderer());
//		m.getColumn(3).setCellRenderer(NumberRenderer.getCurrencyRenderer());

		
	}
	
	public void frame () {
		
	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
