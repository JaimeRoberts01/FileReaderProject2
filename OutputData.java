import java.util.Arrays;

public class OutputData {
	
	private Processing2 Process2;
	private Processing Process;
	private ReportFrame2 ReportFrame2;
	private ReportFrame ReportFrame;
	
	
	public OutputData (Processing2 Process2, Processing Process) {
		this.Process2 = Process2;
		this.Process = Process;
		
	}
	
	
/**This method builds a string for the ReportFrame.*/ 
	
	public String outputString () { 

		StringBuilder SB = new StringBuilder();
		
		String header_upper = (String.format("%s %11s %11s %15s %19s %16s %13s", "Frame", "Pillar", "dx", "dy", "Deflection", "Deflection", "Force")+"\n");
		String header_lower = (String.format("%s %10s %11s %15s %17s %16s %15s", "Index", "Index", " ", " ", "(px)", "(nm)", "(pN)")+"\n");
		String bar = "--------------------------------------------------------------------------------------------------";
		
		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");
		
		for (int i = 0; i <Process.getNewData().length; i++) {

			SB.append(String.format("%3s",Process.getNewData()[i][0]) + "\t" + String.format("%8.4s", Process.getNewData() [i][1])  + "\t"  + String.format("%8.7s", Process.getNewData() [i][4]) + "\t" 
			+ String.format("%8.7s", Process.getNewData()[i][5]) + "\t"  + String.format("%8.7s", Process.getNewData() [i][6]) + "\t" + String.format("%9.7s",Process.getNewData() [i][7]) 
			+ "\t" + String.format("%9.8s", Process.getNewData() [i][8]) + "\n");
		}

		String output = SB.toString();
		ReportFrame = new ReportFrame (Process);
		ReportFrame.reportFormatter(output);
		return output;
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

	public String stringByFrame (int ID) { 

		StringBuilder SB = new StringBuilder();
		String header_upper = (String.format("%9s %15s %15s", "Frame", "Pillar", "Force")+"\n");
		String header_lower = (String.format("%9s %15s %15s", "Index", "Index", "(pN)")+"\n");
		String bar = "---------------------------------------------";
		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");
		String body = "";

		for (int i=0; i<Process2.getDataByFrame().size(); i++) {

			body += (String.format("%7s", ID) + "\t" + String.format("%17.8s", Process2.getPillar().get(i)) + "\t" + String.format("%11.8s", Process2.getDataByFrame().get(i)) + "\n");
		}

		SB.append(body);

		String output = SB.toString();
		System.out.println("output: " + "\n" + output);
		String identifier = "Frame Data";
		System.out.println("identifier : " + "\n" + identifier);
		ReportFrame2 = new ReportFrame2 (Process2, identifier, ID);
		ReportFrame2.reportFormatter(output);
		return output;
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

	public String stringByPillar (int ID) {

		StringBuilder SB = new StringBuilder();
		String header_upper = (String.format("%9s %15s %15s", "Frame", "Pillar", "Force")+"\n");
		String header_lower = (String.format("%9s %15s %15s", "Index", "Index", "(pN)")+"\n");
		String bar = "---------------------------------------------";
		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");

		String body = "";

		for (int i=0; i<Process2.getDataByPillar().size(); i++) {

			body += (String.format("%7s", Process2.getPillarFrame().get(i)) + "\t" + String.format("%17.8s", ID) + "\t" + String.format("%11.8s", Process2.getDataByPillar().get(i)) + "\n");
		}

		SB.append(body);
		SB.append("\n");

		for (int j=0; j<Process2.getMean().size(); j++) {
			SB.append("Average Force (pN): " + String.format("%1.8s", Process2.getMean().get(j)) + "\n");
			SB.append("Standard deviation: " + String.format("%1.8s",Process2.getStandard_deviation().get(j)) + "\n");
		}

		String output = SB.toString();
		String identifier = "Pillar Data";
		ReportFrame2 = new ReportFrame2 (Process2, identifier, ID);
		ReportFrame2.reportFormatter(output);

		return output;
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

	public String StringMultipillar () {

		StringBuilder SB = new StringBuilder();

		String header_upper = (String.format("%9s %17s %13s", "Pillar", "Average Force", "Standard")+"\n");
		String header_lower = (String.format("%9s %13s %18s", "Index", "(pN)", "Deviation")+"\n");
		String bar = "---------------------------------------------";

		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");

		String body = "";

		for (int i=0; i<Process2.getValues().length; i++) {
			body += (String.format("%9s", Process2.getValues() [i]) + "\t" + String.format("%9.8s", Process2.getMean().get(i)) + "\t" + String.format("%9.8s", Process2.getStandard_deviation().get(i)) + "\n");
		}	

		SB.append(body);
		String output = SB.toString();

		String identifier = "Multipillar Data";
		ReportFrame2 = new ReportFrame2 (Process2, identifier, 0);
		ReportFrame2.reportFormatter(output);

		return output;
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

	public String stringStatistics () { 

		StringBuilder SB = new StringBuilder();

		String header_upper = (String.format("%9s %17s %13s", "Pillar", "Average Force", "Standard")+"\n");
		String header_lower = (String.format("%9s %13s %18s", "Index", "(pN)", "Deviation")+"\n");
		String bar = "---------------------------------------------";

		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");

		for (int i = 0; i <Process2.getPillar().size(); i++) {

			SB.append(String.format("%9s",Process2.getPillar().get(i)) + "\t" + String.format("%9.8s",Process2.getMean().get(i)) + "\t" 
					+ String.format("%9.8s",Process2.getStandard_deviation().get(i)) + "\n");
		}

		String output = SB.toString();
		
		String identifier = "Statistical Data";
		ReportFrame2 = new ReportFrame2 (Process2, identifier, 0);
		ReportFrame2.reportFormatter(output);
		return output;
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
}