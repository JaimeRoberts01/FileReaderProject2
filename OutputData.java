import java.util.*;


public class OutputData {
	
	private Processing2 Process2;
	private Processing Process;
	
	
	public OutputData (Processing2 Process2, Processing Process) {
		this.Process2 = Process2;
		this.Process = Process;		
	}
	
	
/**This method builds a string for the ReportFrame.*/ 
	
	public void outputString () { 
		
		int columns = 5;
		int rows= Process.getNewData().length;
		
		Object [][] data = new Object [rows][columns];
		
		for (int i = 0; i <Process.getNewData().length; i++) {
			
			int frame = Integer.parseInt((String)Process.getNewData() [i][0]);
			int pillar = Integer.parseInt((String)Process.getNewData() [i][1]);
			double px = Double.parseDouble((String) Process.getNewData() [i][6]);
			double nm = (double) Process.getNewData() [i][7];
			double pn = (double) Process.getNewData() [i][8];
			
			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", px);
			data [i][3] = String.format("%.2f", nm);
			data [i][4] = String.format("%.2f", pn);		
		}
		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Deflection (px)", "Deflection (nm)", "Force (pN)"};

		ReportTable ReportTable = new ReportTable (Process);	
		ReportTable.JTable(column, data);
	}


	/**This method builds a string for the output file, which is another csv file.*/
	
	public String outputFile () {

		String header = ("Frame Index" + "," + "Pillar Index" + "," + "x" + "," + "y" + ","  + "dx" + ","
				+ "dy" + "," + "Deflection (px)" + "," + "Deflection (nm)"+ "," + "Forces (pN)" + ",");
		String body = "";

		for (int i =0; i<Process.getNewData().length;i++) {

			body += Arrays.toString(Process.getNewData()[i]) +"\n";
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String output = SB.toString();
		output = output.replace("[", "");
		output = output.replace("]", "");
		return output;
	}
	
	
	/**This method creates a String output for the dataByFrame method and displays
	 * it it in a ReportFrame. Note, it is a tidier version of the output file used 
	 * for viewing the data only.
	 */

	public void stringByFrame (int ID) { // ID is for the frame number
		
		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};

		int columns = 3;
		int rows= Process2.getDataByFrame().size();
		
		Object [][] data = new Object [rows][columns];
		
		for (int i = 0; i < Process2.getPillar().size(); i++) {
			
		int frame = ID;
		int pillar = Process2.getPillar().get(i);;
		double forces = (double) Process2.getDataByFrame().get(i);
		
		data [i][0] = frame;
		data [i][1] = pillar;
		data [i][2] = String.format("%.2f", forces);
		}
		
		String identifier = "Frame Data";
		System.out.println("identifier : " + "\n" + identifier);
		
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, ID);	
		ReportTable2.JTable(column, data);
	}	


	/**This method creates a String output for the dataByFrame method and is 
	 * called by the FileWriter. It is formatted as csv compatible.
	 */

	public String outputByFrame () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<Process2.getDataByFrame().size(); i++) {
			SB.append(Process2.getOutputDataByFrame().get(i) + "\n");
		}

		String output = SB.toString();
		System.out.println("output: " + "\n" + output);
		return output;
	}


	/**This method creates a string from the dataByPillar method and displays it 
	 * in a ReportFrame. Note, it is a tidier version of the output file used for 
	 * viewing the data only.
	 */

	public void stringByPillar (int ID) { // ID is the pillar

		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};

		int columns = 3;
		int rows= Process2.getDataByPillar().size();
		
		Object [][] data = new Object [rows][columns];
		
		for (int i = 0; i < Process2.getDataByPillar().size(); i++) {
			
		int frame = Integer.parseInt ((String) Process2.getPillarFrame().get(i));
		int pillar = ID;
		double forces = (double) Process2.getDataByPillar().get(i);
		
		data [i][0] = frame;
		data [i][1] = pillar;
		data [i][2] = String.format("%.2f", forces);
		}
		
		String identifier = "Pillar Data";
		System.out.println("identifier : " + "\n" + identifier);
		
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, ID);	
		ReportTable2.JTable(column, data);
	}


	/**This method creates a String output for the dataByPillar method and is 
	 * called by the FileWriter. It is formatted as csv compatible.
	 */

	public String outputByPillar () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<Process2.getDataByPillar().size(); i++) {
			SB.append(Process2.getOutputDataByPillar().get(i) + "\n");
		}

		SB.append("\n");

		for (int j=0; j<Process2.getMean().size(); j++) {
			SB.append("AVG Force (pN): " + "," + " ," + Process2.getMean().get(j) + "\n");
			SB.append("SD: " + "," + " ," + Process2.getStandard_deviation().get(j) + "\n");
		}

		String output = SB.toString();
		return output;
	}


	/**This method builds a String for the multipillar method and displays it in a ReportFrame. 
	 * Note, it is a tidier version of the output file used for viewing the data only.
	 */

	public void StringMultipillar () {
		
		
		String [] column = {"Pillar Index (ID)", "Average Force (pN)", "Standard Deviation"};

		int columns = 3;
		int rows= Process2.getValues().length;
		
		Object [][] data = new Object [rows][columns];
		
		for (int i = 0; i < Process2.getValues().length; i++) {
			
		int pillar =  Integer.parseInt((String) Process2.getValues() [i]); 
		double forces = Process2.getMean().get(i);
		double standard_deviation = Process2.getStandard_deviation().get(i);
		
		data [i][0] = pillar;
		data [i][1] = String.format("%.2f", forces);
		data [i][2] = String.format("%.2f", standard_deviation);
		}
		
		String identifier = "Pillar Data";
		System.out.println("identifier : " + "\n" + identifier);
		
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, 0);	
		ReportTable2.JTable(column, data);
	}


	/**This method creates a String output for the multipillar method and is called
	 * by the FileWriter. It is formatted as csv compatible.
	 */

	public String outputMultipillar () {

		StringBuilder SB = new StringBuilder();
		SB.append("Pillar Index" + "," + "AVG Force (pN)" + "," + "SD" + "\n");

		for (int i=0; i<Process2.getValues().length; i++) {
			SB.append(Process2.getValues() [i] + "," + Process2.getMean().get(i) + "," + Process2.getStandard_deviation().get(i) + "\n");
		}	

		String output = SB.toString();
		return output;
	}


	/**This method builds a String output for the average and standard deviation and displays it in 
	 * a ReportFrame. Note, it is a tidier version of the output file used for viewing the data only.
	 */

	public void stringStatistics () { 
		
		String [] column = {"Pillar Index (ID)", "Average Force (pN)", "Standard Deviation"};

		int columns = 3;
		int rows= Process2.getPillar().size();
		
		Object [][] data = new Object [rows][columns];
		
		for (int i = 0; i < Process2.getPillar().size(); i++) {
			
		int pillar = Process2.getPillar().get(i);
		double forces = Process2.getMean().get(i);
		double standard_deviation = Process2.getStandard_deviation().get(i);
		
		data [i][0] = pillar;
		data [i][1] = String.format("%.2f", forces);
		data [i][2] = String.format("%.2f", standard_deviation);
		}
		
		String identifier = "Pillar Data";
		System.out.println("identifier : " + "\n" + identifier);
		
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, 0);	
		ReportTable2.JTable(column, data);
	}


	/**This method creates a String output file for the average and standard deviation and
	 * is called by the FileWriter. It is formatted as csv compatible. 
	 */

	public String outputStatistics () {

		String header = ("Pillar Index" + "," + "Average Force (pN)" + "," + "Stndev" + ",");
		String body = "";

		for (int i =0; i<Process2.getPillar().size();i++) {

			body += (Process2.getPillar().get(i) + "," + Process2.getMean().get(i) + "," + Process2.getStandard_deviation().get(i) + "\n");
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		return outputFile;
	}
	
	public void stringAllData () {
		
		int columns = 3;
		int rows = Process2.getDataByPillarFrame().size();

		Object [][] allData = new Object [rows][columns];
		
		for (int i = 0; i< rows; i++) {
			allData [i] = Process2.getDataByPillarFrame().get(i).split(",");
		}
	
		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};

		Object [][] data = new Object [rows][columns];
		
		int frame = 0;
		int pillar = 0;
		double forces = 0.0;
		
		for (int i = 0; i < Process2.getDataByPillarFrame().size(); i++) {
			
		frame = Integer.parseInt((String)allData [i][0]);
		pillar = Integer.parseInt((String)allData [i][1]);
		forces = Double.parseDouble((String)allData [i][2]);
		
		data [i][0] = frame;
		data [i][1] = pillar;
		data [i][2] = String.format("%.2f", forces);
		}
	
		String identifier = "All Data";
		System.out.println("identifier : " + "\n" + identifier);
		
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, 0);	
		ReportTable2.JTable(column, data);
	}
	
	
	public String outputAllData () {
		
		String header = ("Frame Index (ID)" + "," + "Pillar Index (ID)" + "," + "Forces (pN)" + ",");
		String body = "";

		for (int i =0; i<Process2.getDataByPillarFrame().size();i++) {

			body += Process2.getDataByPillarFrame().get(i) + "\n";
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		return outputFile;
	}
}