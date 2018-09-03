import java.util.*;


/**Class to define JTable data and output data.*/

public class OutputData {

	/*Instance variables*/
	private DataProcessing DataProcessing;
	private DataArray DataArray;


	/*Constructor*/
	public OutputData (DataProcessing DataProcessing, DataArray DataArray) {
		this.DataProcessing = DataProcessing;
		this.DataArray = DataArray;		
	}


	/**This method builds a ReportTablestring for all data in the newData array. Note, 
	 * it is a tidier version of the output file used for viewing the data only.
	 */ 
	
	public void outputString () { 

		Object [][] data = new Object [DataArray.getDataArray().length][3];

		for (int i = 0; i <DataArray.getDataArray().length; i++) {

			int frame = Integer.parseInt((String) DataArray.getDataArray() [i][0]);
			int pillar = Integer.parseInt((String) DataArray.getDataArray() [i][1]);
			double pn = (double) DataArray.getDataArray() [i][8];

			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", pn);		
		}
		
		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};
		String identifier = "Force Data";
		ReportTable ReportTable = new ReportTable (DataArray, null, identifier, 0);	
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

		for (int i =0; i<DataArray.getDataArray().length;i++) {

			body += Arrays.toString(DataArray.getDataArray()[i]) +"\n";
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

		Object [][] data = new Object [DataProcessing.getDataByFrame().size()][3];

		for (int i = 0; i < DataProcessing.getDataByFrame().size(); i++) {

			int frame = ID;
			int pillar = DataProcessing.getPillar().get(i);;
			double forces = (double) DataProcessing.getDataByFrame().get(i);

			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", forces);
		}

		String identifier = "Frame Data";
		ReportTable ReportTable = new ReportTable (null, DataProcessing, identifier, ID);	
		ReportTable.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}	


	/**This method builds a csv output file for frame data
	 * @returns output - an output string for file.
	 */

	public String outputByFrame () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<DataProcessing.getOutputDataByFrame().size(); i++) {
			SB.append(DataProcessing.getOutputDataByFrame().get(i) + "\n");
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

		Object [][] data = new Object [DataProcessing.getForce().size()][3];
		
		for (int i = 0; i < DataProcessing.getForce().size(); i++) {

			int frame = Integer.parseInt ((String) DataProcessing.getPillarFrame().get(i));
			int pillar = ID;
			double forces = (double) DataProcessing.getForce().get(i);

			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", forces);
		}

		String identifier = "Pillar Data";
		ReportTable ReportTable = new ReportTable (null, DataProcessing, identifier, ID);	
		ReportTable.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for pillar data
	 * @returns output - an output string for file.
	 */

	public String outputByPillar () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<DataProcessing.getOutputDataByPillar().size(); i++) {
			SB.append(DataProcessing.getOutputDataByPillar().get(i) + "\n");
		}

		SB.append("\n");

		for (int j=0; j<DataProcessing.getMean().size(); j++) {
			SB.append("AVG Force (pN): " + "," + " ," + DataProcessing.getMean().get(j) + "\n");
			SB.append("SD: " + "," + " ," + DataProcessing.getStandard_deviation().get(j) + "\n");
		}

		String output = SB.toString();
		return output;
	}


	/**This method builds a ReportTable string for multiple pillars in the newData array. 
	 * Note, it is a tidier version of the output file used for viewing the data only.
	 */

	public void StringMultipillar () {

		String [] column = {"Pillar Index (ID)", "Average Force (pN)", "Standard Deviation"};

		Object [][] data = new Object [DataProcessing.getValues().length][3];

		for (int i = 0; i < DataProcessing.getValues().length; i++) {

			int pillar =  Integer.parseInt((String) DataProcessing.getValues() [i]); 
			double forces = DataProcessing.getMean().get(i);
			double standard_deviation = DataProcessing.getStandard_deviation().get(i);

			data [i][0] = pillar;
			data [i][1] = String.format("%.2f", forces);
			data [i][2] = String.format("%.2f", standard_deviation);
		}

		String identifier = "Multipillar Data";
		ReportTable ReportTable = new ReportTable (null, DataProcessing, identifier, 0);	
		ReportTable.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for multipillar data
	 * @returns output - an output string for file.
	 */

	public String outputMultipillar () {

		StringBuilder SB = new StringBuilder();
		SB.append("Pillar Index" + "," + "AVG Force (pN)" + "," + "SD" + "\n");

		for (int i=0; i<DataProcessing.getValues().length; i++) {
			SB.append(DataProcessing.getValues() [i] + "," + DataProcessing.getMean().get(i) + "," + DataProcessing.getStandard_deviation().get(i) + "\n");
		}	

		String output = SB.toString();
		return output;
	}


	/**This method builds a ReportTable string of the average pillar force and standard deviation for 
	 * the whole data. Note, it is a tidier version of the output file used for viewing the data only.
	 */

	public void stringStatistics () { 

		String [] column = {"Pillar Index (ID)", "Average Force (pN)", "Standard Deviation"};

		Object [][] data = new Object [DataProcessing.getPillar().size()][3];

		for (int i = 0; i < DataProcessing.getPillar().size(); i++) {

			int pillar = DataProcessing.getPillar().get(i);
			double forces = DataProcessing.getMean().get(i);
			double standard_deviation = DataProcessing.getStandard_deviation().get(i);

			data [i][0] = pillar;
			data [i][1] = String.format("%.2f", forces);
			data [i][2] = String.format("%.2f", standard_deviation);
		}

		String identifier = "Statistical Data";
		ReportTable ReportTable = new ReportTable (null, DataProcessing, identifier, 0);	
		ReportTable.JTable(column, data);
		// Calls the method for loading values to the JTable.	
	}


	/**This method builds a csv output file for the statistics
	 * @returns output - an output string for file.
	 */

	public String outputStatistics () {

		String header = ("Pillar Index" + "," + "Average Force (pN)" + "," + "Stndev" + ",");
		String body = "";

		for (int i =0; i<DataProcessing.getPillar().size();i++) {

			body += (DataProcessing.getPillar().get(i) + "," + DataProcessing.getMean().get(i) + "," + DataProcessing.getStandard_deviation().get(i) + "\n");
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

		Object [][] allData = new Object [DataProcessing.getDataByPillarFrame().size()][3];

		for (int i = 0; i< DataProcessing.getDataByPillarFrame().size(); i++) {
			
			allData [i] = DataProcessing.getDataByPillarFrame().get(i).toString().split(",");
		}

		String [] column = {"Frame Index (ID)", "Pillar Index (ID)", "Force (pN)"};

		Object [][] data = new Object [DataProcessing.getDataByPillarFrame().size()][3];

		for (int i = 0; i < DataProcessing.getDataByPillarFrame().size(); i++) {

			int frame = Integer.parseInt((String)allData [i][0]);
			int pillar = Integer.parseInt((String)allData [i][1]);
			double forces = Double.parseDouble((String)allData [i][2]);

			data [i][0] = frame;
			data [i][1] = pillar;
			data [i][2] = String.format("%.2f", forces);
		}

		String identifier = "All Data";
		ReportTable ReportTable2 = new ReportTable (null, DataProcessing, identifier, 0);	
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

		for (int i =0; i<DataProcessing.getDataByPillarFrame().size();i++) {

			body += DataProcessing.getDataByPillarFrame().get(i) + "\n";
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		return outputFile;
	}
}