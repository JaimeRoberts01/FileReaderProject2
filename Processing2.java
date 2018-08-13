import java.util.*;


public class Processing2 {


	/*Instance variables*/
	private ArrayList <Double> mean, standard_deviation; 
	private ArrayList <Object> dataByPillar, outputDataByPillar, dataByFrame, outputDataByFrame, multipillar;
	private ArrayList <String> dataByPillarFrame;
	private ArrayList <Integer> pillar, frame;	

	//private FileManager FileManager;
	//private Processing Process;
	private ReportFrame3 ReportFrame3;
//	private ReportFrame4 ReportFrame4;
//	private ReportFrame5 ReportFrame5;
	private MultipillarInput MultipillarInput;

	
	/*Constructor*/
	Processing2 (MultipillarInput MultipillarInput) {	
		this.MultipillarInput = MultipillarInput;
	}


	/*Getters & Setters*/
	public ArrayList<Double> getMean() {return mean;}
	public void setMean(ArrayList<Double> mean) {this.mean = mean;}
	public ArrayList<Double> getStandard_deviation() {return standard_deviation;}
	public void setStandard_deviation(ArrayList<Double> standard_deviation) {this.standard_deviation = standard_deviation;}
	public ArrayList<Integer> getPillar() {return pillar;}
	public void setPillar(ArrayList<Integer> pillar) {this.pillar = pillar;}
	public ArrayList<String> getDataByPillarFrame() {return dataByPillarFrame;}
	public void setDataByPillarFrame(ArrayList<String> dataByPillarFrame) {this.dataByPillarFrame = dataByPillarFrame;}


	/*This method creates a list of the pillars in the newData array. It is used for initialising
	the first pillar value and sets up allFrames so that it can collect data for specific pillars.*/
	public ArrayList <Integer> getPillars (Object [][] newData) {

		pillar = new ArrayList <Integer> ();

		for (int i = 0; i<newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]); 

			if (!pillar.contains(pillarID)) {
				pillar.add(pillarID);
			}
		}

		Collections.sort(pillar);

		for (int i : pillar) {System.out.println("pillar: " + i);}
		return pillar;
	}


	public ArrayList <Integer> getFrames (Object [][] newData) {

		frame = new ArrayList <Integer> ();

		for (int i = 0; i<newData.length; i++) {

			int frameID = Integer.parseInt((String) newData [i][0]); 

			if (!frame.contains(frameID)) {
				frame.add(frameID);
			}
		}

		Collections.sort(frame);

		for (int i : frame) {System.out.println("frame: " + i);}
		return frame;
	}


	/*This method loops through the pillar ArrayList identifying pillars of the same ID
	 from all the frames in the dataset. Pillars of the same ID are added to a temporary
	 ArrayList, which is used for averages and standard deviation.*/
	public void allFrames (Object [][] newData) {

		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		ArrayList <Double> pico = new ArrayList <Double> ();

		for (int j = 0; j < pillar.size(); j++) {

			int pillarArray = pillar.get(j);
			System.out.println("pillarArray: " + pillarArray);

			for (int k = 0; k < newData.length; k++) {

				int pillarIndex = Integer.parseInt((String) newData [k][1]);

				if (pillarIndex == pillarArray) {

					pico.add ((double) newData [k][8]);
				}
			}

			System.out.println("pico: " + pico);
			statistics (pico);
			pico.clear();
		}

		for (double m : mean) {System.out.println("mean: " + m);}
		for (double s : standard_deviation) {System.out.println("stndev: " + s);}
	}	


	/*This method will generate a list of FrameID, PillarID and Forces relating all pillars across
	 across a single Frame */
	public ArrayList <Object> dataByFrame (Object [][] newData, int ID) {

		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		dataByFrame = new ArrayList <Object>();
		outputDataByFrame = new ArrayList <Object>();

		for (int i = 0; i< newData.length; i++) {

			int frameID = Integer.parseInt((String) newData [i][0]);

			if (frameID == ID) {

				Object forces = newData [i][0] + "," + newData [i][1] + "," + newData [i][8];
				outputDataByFrame.add(forces);	
				dataByFrame.add((double) newData [i][8]);
			}	
		}

		for (int j =0; j< dataByFrame.size(); j++) {System.out.println("byFrame: " + dataByFrame.get(j));}
		stringByFrame (ID);
		return dataByFrame;
	}


	/*This method will generate a list of FrameID, PillarID and Forces relating to a single pillar across
	all the frames*/
	public ArrayList <Object> dataByPillar (Object [][] newData, int ID) { 

		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		dataByPillar = new ArrayList <Object> ();
		outputDataByPillar = new ArrayList <Object> ();
		ArrayList <Double> pico = new ArrayList <Double> ();

		for (int i = 0; i< newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]);
			

			if (ID == pillarID) {

				pico.add((double) newData [i][8]);
				Object forces = newData [i][0] + "," + newData [i][1] + "," + newData [i][8];
				outputDataByPillar.add(forces);
				dataByPillar.add((double) newData [i][8]);
			}	
		}

		for (int j =0; j< dataByPillar.size(); j++) {System.out.println("bypillar: " + dataByPillar.get(j));}
		statistics(pico);
		stringByPillar(ID);
		pico.clear();
		return dataByPillar;
	}


	/*This method is for the multipillar functionality and is similar to the allFrames
	 method but does not use the pillar ArrayList for ordering the pillars*/
	public void multiPillar (Object [][] newData) {

		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		ArrayList <Double> pico = new ArrayList <Double> ();
		multipillar = new ArrayList <Object> ();

		int index = MultipillarInput.getValues().length;
		String [] values = new String [index];

		for (int i =0; i< MultipillarInput.getValues().length; i++) {
			values = MultipillarInput.getValues();
		}

		int value = 0;

		for (int i = 0; i < values.length; i++) {

			value = Integer.parseInt(values [i]);
			multipillar.add(value);

			for (int j =0; j< newData.length; j++) {

				if (value == Integer.parseInt ((String) newData [j][1])) {
					pico.add((Double) newData [j][8]);
				}
			}

			for (double d: pico) {System.out.println("pico2 : " + d);}
			statistics(pico);
			pico.clear();
		}

		StringMultipillar ();

		for (double m : mean) {System.out.println("mean: " + m);}
		for (double s : standard_deviation) {System.out.println("stndev: " + s);}
	}


	/*This method calculates the average and standard deviation of the picoNewton values in the data.*/
	public void statistics (ArrayList<Double> pico) {

		double average = 0.0;
		double sd = 0.0;

		for (double avg : pico) {

			average += avg;			
		}

		average = average/pico.size();
		mean.add(average);

		for (double stdev : pico) {

			sd += Math.pow((stdev-average), 2);			
		}

		sd = Math.sqrt(sd/pico.size());
		standard_deviation.add(sd);
	}


	public ArrayList<String> allDataAllFrames (Object [][] newData) {

		dataByPillarFrame = new ArrayList <String> ();

		for (int i = 0; i< pillar.size(); i++) {

			int pillarArray = pillar.get(i);
			System.out.println("pillarArray: " + pillarArray);


			for (int j = 0; j< newData.length; j++) {

				int pillarIndex = Integer.parseInt((String) newData [j][1]);

				if (pillarIndex == pillarArray) {

					dataByPillarFrame.add (newData [j][0] + "," + newData [j][1] + "," +  newData [j][8]);
				}
			}
		}

		for (Object O : dataByPillarFrame) {System.out.println("dataByPillarFrame: " + O);}
		System.out.println("DBPF L:" + dataByPillarFrame.size());
		return dataByPillarFrame;
	}

	
	// This is all output-related methods. Consider removing to an output class to tidy Processing2 //
	
	
	public String stringByFrame (int ID) { // needs altering but that has to do with the method.
		
		StringBuilder SB = new StringBuilder();
		String header_upper = (String.format("%9s %15s %15s", "Frame", "Pillar", "Force")+"\n");
		String header_lower = (String.format("%9s %15s %15s", "Index", "Index", "(pN)")+"\n");
		String bar = "---------------------------------------------";
		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");
		String body = "";
		
		for (int i=0; i<dataByFrame.size(); i++) {
			
			body += (String.format("%7s", ID) + "\t" + String.format("%17.8s", pillar.get(i)) + "\t" + String.format("%11.8s", dataByFrame.get(i)) + "\n");
		}
		
		SB.append(body);
		
		String output = SB.toString();
		System.out.println("output: " + "\n" + output);
		String identifier = "Frame Data";
		System.out.println("identifier : " + "\n" + identifier);
		ReportFrame3 = new ReportFrame3 (this, identifier);
		ReportFrame3.reportFormatter(output);
		return output;
	}
	
	
	/*This method creates a string from the dataByFrame method and sends it to the FileWriter.*/
	public String outputByFrame () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<dataByFrame.size(); i++) {
			SB.append(outputDataByFrame.get(i) + "\n");
		}

		String output = SB.toString();
		System.out.println("output: " + "\n" + output);
		return output;
	}


	/*This method creates a string from the dataByPillar method and sends it to the FileWriter.*/
	public String stringByPillar (int ID) { // This needs changing but it's the method

		StringBuilder SB = new StringBuilder();
		String header_upper = (String.format("%9s %15s %15s", "Frame", "Pillar", "Force")+"\n");
		String header_lower = (String.format("%9s %15s %15s", "Index", "Index", "(pN)")+"\n");
		String bar = "---------------------------------------------";
		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");

		String body = "";
		
		for (int i=0; i<dataByPillar.size(); i++) {
			
			body += (String.format("%7s", frame.get(i)) + "\t" + String.format("%17.8s", ID) + "\t" + String.format("%11.8s", dataByPillar.get(i)) + "\n");
		}
		
		SB.append(body);
		SB.append("\n");

		for (int j=0; j<mean.size(); j++) {
			SB.append("Average Force (pN): " + String.format("%1.8s", mean.get(j)) + "\n");
			SB.append("Standard deviation: " + String.format("%1.8s",standard_deviation.get(j)) + "\n");
		}

		String output = SB.toString();
		String identifier = "Pillar Data";
		ReportFrame3 = new ReportFrame3 (this, identifier);
		ReportFrame3.reportFormatter(output);

		return output;
	}
	
	
	public String outputByPillar () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");
	
		for (int i=0; i<dataByPillar.size(); i++) {
			SB.append(outputDataByPillar.get(i) + "\n");
		}

		SB.append("\n");

		for (int j=0; j<mean.size(); j++) {
			SB.append("AVG Force (pN): " + "," + " ," + mean.get(j) + "\n");
			SB.append("SD: " + "," + " ," + standard_deviation.get(j) + "\n");
		}

		String output = SB.toString();
		return output;
	}
	

	/*This method builds a string for the multipillar method and sends it to the FileWriter*/
	//public String outputStringMultipillar (String[] values) {
	public String StringMultipillar () {

		String [] values = MultipillarInput.getValues();

		StringBuilder SB = new StringBuilder();

		String header_upper = (String.format("%9s %17s %13s", "Pillar", "Average Force", "Standard")+"\n");
		String header_lower = (String.format("%9s %13s %18s", "Index", "(pN)", "Deviation")+"\n");
		String bar = "---------------------------------------------";

		SB.append(header_upper);
		SB.append(header_lower);
		SB.append (bar+ "\n\n");

		String body = "";

		for (int i=0; i<multipillar.size(); i++) {
			body += (String.format("%9s", values [i]) + "\t" + String.format("%9.8s", mean.get(i)) + "\t" + String.format("%9.8s", standard_deviation.get(i)) + "\n");
		}	

		SB.append(body);
		String output = SB.toString();

		String identifier = "Multipillar Data";
		ReportFrame3 = new ReportFrame3 (this, identifier);
		ReportFrame3.reportFormatter(output);

		return output;
	}

	
	public String outputMultipillar () {

		String [] values = MultipillarInput.getValues();

		StringBuilder SB = new StringBuilder();
		SB.append("Pillar Index" + "," + "AVG Force (pN)" + "," + "SD" + "\n");

		for (int i=0; i<multipillar.size(); i++) {
			SB.append(values [i] + "," + mean.get(i) + "," + standard_deviation.get(i) + "\n");
		}	

		String output = SB.toString();
		return output;
	}


	/*This method builds a string for the ReportFrameX.*/
	public String outputString () { 

		StringBuilder SB = new StringBuilder();
		for (int i = 0; i <pillar.size(); i++) {

			SB.append(String.format("%9s",pillar.get(i)) + "\t" + String.format("%9.8s",mean.get(i)) + "\t" 
					+ String.format("%9.8s",standard_deviation.get(i)) + "\n");
		}

		String output = SB.toString();	
		return output;
	}


	/*This method creates an output file for the average and standard deviation of pillars from AllFrames*/
	public String outputFile () {

		String header = ("Pillar Index" + "," + "Average Force (pN)" + "," + "Stndev" + ",");
		String body = "";

		for (int i =0; i<pillar.size();i++) {

			body += (pillar.get(i) + "," + mean.get(i) + "," + standard_deviation.get(i) + "\n");
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		return outputFile;
	}
}