import java.util.*;


/**Class to define JTable data and output data.*/

public class OutputData {

	/*Instance variables*/
	private Processing2 Process2;
	private Processing Process;


	/*Constructor*/
	public OutputData (Processing2 Process2, Processing Process) {
		this.Process2 = Process2;
		this.Process = Process;		
	}


	/**This method builds a ReportTablestring for all data in the newData array. Note, 
	 * it is a tidier version of the output file used for viewing the data only.
	 */ 

	public void outputString () { 

		Object [][] data = new Object [Process.getNewData().length][5];

		for (int i = 0; i <Process.getNewData().length; i++) {

			int frame = Integer.parseInt((String) Process.getNewData() [i][0]);
			int pillar = Integer.parseInt((String) Process.getNewData() [i][1]);
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
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for the whole data
	 * @returns output - an output string for file.
	 */

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



	/**This method builds a ReportTable string for a chosen frame in the newData array. 
	 * Note, it is a tidier version of the output file used for viewing the data only.
	 * @param ID - the frame index.
	 */ 

	public void stringByFrame (int ID) { 

		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};

		Object [][] data = new Object [Process2.getDataByFrame().size()][3];

		for (int i = 0; i < Process2.getDataByFrame().size(); i++) {

			int frame = ID;
			int pillar = Process2.getPillar().get(i);;
			double forces = (double) Process2.getDataByFrame().get(i);

			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", forces);
		}

		String identifier = "Frame Data";
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, ID);	
		ReportTable2.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}	


	/**This method builds a csv output file for frame data
	 * @returns output - an output string for file.
	 */

	public String outputByFrame () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<Process2.getOutputDataByFrame().size(); i++) {
			SB.append(Process2.getOutputDataByFrame().get(i) + "\n");
		}

		String output = SB.toString();
		return output;
	}


	/**This method builds a ReportTable string for a chosen pillar in the newData array. 
	 * Note, it is a tidier version of the output file used for viewing the data only.
	 * @param ID - the pillar index.
	 */

	public void stringByPillar (int ID) { // ID is the pillar

		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};

		Object [][] data = new Object [Process2.getForce().size()][3];
		
		for (int i = 0; i < Process2.getForce().size(); i++) {

			int frame = Integer.parseInt ((String) Process2.getPillarFrame().get(i));
			int pillar = ID;
			double forces = (double) Process2.getForce().get(i);

			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", forces);
		}

		String identifier = "Pillar Data";
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, ID);	
		ReportTable2.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for pillar data
	 * @returns output - an output string for file.
	 */

	public String outputByPillar () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<Process2.getOutputDataByPillar().size(); i++) {
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


	/**This method builds a ReportTable string for multiple pillars in the newData array. 
	 * Note, it is a tidier version of the output file used for viewing the data only.
	 */

	public void StringMultipillar () {

		String [] column = {"Pillar Index (ID)", "Average Force (pN)", "Standard Deviation"};

		Object [][] data = new Object [Process2.getValues().length][3];

		for (int i = 0; i < Process2.getValues().length; i++) {

			int pillar =  Integer.parseInt((String) Process2.getValues() [i]); 
			double forces = Process2.getMean().get(i);
			double standard_deviation = Process2.getStandard_deviation().get(i);

			data [i][0] = pillar;
			data [i][1] = String.format("%.2f", forces);
			data [i][2] = String.format("%.2f", standard_deviation);
		}

		String identifier = "Multipillar Data";
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, 0);	
		ReportTable2.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for multipillar data
	 * @returns output - an output string for file.
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


	/**This method builds a ReportTable string of the average pillar force and standard deviation for 
	 * the whole data. Note, it is a tidier version of the output file used for viewing the data only.
	 */

	public void stringStatistics () { 

		String [] column = {"Pillar Index (ID)", "Average Force (pN)", "Standard Deviation"};

		Object [][] data = new Object [Process2.getPillar().size()][3];

		for (int i = 0; i < Process2.getPillar().size(); i++) {

			int pillar = Process2.getPillar().get(i);
			double forces = Process2.getMean().get(i);
			double standard_deviation = Process2.getStandard_deviation().get(i);

			data [i][0] = pillar;
			data [i][1] = String.format("%.2f", forces);
			data [i][2] = String.format("%.2f", standard_deviation);
		}

		String identifier = "Statistical Data";
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, 0);	
		ReportTable2.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for the statistics
	 * @returns output - an output string for file.
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

	
	/**This method builds a ReportTable string for the whole data that has been organised by frame rather
	 * than by pillar. Note, it is a tidier version of the output file used for viewing the data only.
	 */
	
	public void stringAllData () {

		Object [][] allData = new Object [Process2.getDataByPillarFrame().size()][3];

		for (int i = 0; i< Process2.getDataByPillarFrame().size(); i++) {
			
			allData [i] = Process2.getDataByPillarFrame().get(i).toString().split(",");
		}

		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};

		Object [][] data = new Object [Process2.getDataByPillarFrame().size()][3];

		for (int i = 0; i < Process2.getDataByPillarFrame().size(); i++) {

			int frame = Integer.parseInt((String)allData [i][0]);
			int pillar = Integer.parseInt((String)allData [i][1]);
			double forces = Double.parseDouble((String)allData [i][2]);

			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", forces);
		}

		String identifier = "All Data";
		ReportTable2 ReportTable2 = new ReportTable2 (Process2, identifier, 0);	
		ReportTable2.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for the whole 
	 * data organised by frame rather than by pillar
	 * @returns output - an output string for file.
	 */
	
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