import java.util.*;


public class Processing2 {


	/*Instance variables*/
	private ArrayList <Double> mean, standard_deviation; 
	private ArrayList <Object> dataByPillar, dataByFrame, multipillar;
	private ArrayList <Integer> pillar;

	private FileManager FileManager;
	private DataPlotting DataPlotting;

	/*Constructor*/
	Processing2 () {		
	}

	
	

	public ArrayList<Double> getMean() {return mean;}
	public void setMean(ArrayList<Double> mean) {this.mean = mean;}
	public ArrayList<Double> getStandard_deviation() {return standard_deviation;}
	public void setStandard_deviation(ArrayList<Double> standard_deviation) {this.standard_deviation = standard_deviation;}
	public ArrayList<Integer> getPillar() {return pillar;}
	public void setPillar(ArrayList<Integer> pillar) {this.pillar = pillar;}




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

		for (int i = 0; i< newData.length; i++) {

			int frameID = Integer.parseInt((String) newData [i][0]);

			if (frameID == ID) {

				Object forces = newData [i][0] + "," + newData [i][1] + "," + newData [i][8];
				dataByFrame.add(forces);	
			}	
		}

		for (int j =0; j< dataByFrame.size(); j++) {System.out.println("byFrame: " + dataByFrame.get(j));}
		outputStringbyFrame ();
		return dataByFrame;
	}


	/*This method will generate a list of FrameID, PillarID and Forces relating to a single pillar across
	all the frames*/
	public ArrayList <Object> dataByPillar (Object [][] newData, int ID) { 

		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		dataByPillar = new ArrayList <Object> ();
		ArrayList <Double> pico = new ArrayList <Double> ();

		for (int i = 0; i< newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]);

			if (pillarID == ID) {

				pico.add((double) newData [i][8]);
				Object forces = newData [i][0] + "," + newData [i][1] + "," + newData [i][8];
				dataByPillar.add(forces);
			}	
		}

		for (int j =0; j< dataByPillar.size(); j++) {System.out.println("bypillar: " + dataByPillar.get(j));}
		statistics(pico);
		outputStringbyPillar();
		return dataByPillar;
	}


	/*This method is for the multipillar functionality and is similar to the allFrames
	 method but does not use the pillar ArrayList for ordering the pillars*/
	public void multiPillar (Object [][] newData, String [] values) {

		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		ArrayList <Double> pico = new ArrayList <Double> ();
		multipillar = new ArrayList <Object> ();
		int value = 0;

		for (int i = 0; i < values.length; i++) {
			value = Integer.parseInt(values [i]);
			multipillar.add(value);

			for (int j =0; j< newData.length; j++) {

				if (value == Integer.parseInt ((String) newData [j][1])) {
					pico.add((Double) newData [j][8]);
					//Object forces = newData [j][0] + "," + newData [j][1] + "," + newData [j][8];

				}
			}
			for (double d: pico) {System.out.println("pico2 : " + d);}
			statistics(pico);
			outputStringMultipillar (values);
			pico.clear();
		}

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
		DataPlotting = new DataPlotting (this);

		for (double stdev : pico) {

			sd += Math.pow((stdev-average), 2);			
		}

		sd = Math.sqrt(sd/pico.size());
		standard_deviation.add(sd);
	}


	/*This method creates a string from the dataByFrame method and sends it to the FileWriter.*/
	public String outputStringbyFrame () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<dataByFrame.size(); i++) {
			SB.append(dataByFrame.get(i) + "\n");
		}

		String output = SB.toString();
		System.out.println("output: " + "\n" + output);
		String identifier = "dataByFrame";
		FileManager = new FileManager ();
		FileManager.fileWriter(identifier, output);
		return output;
	}


	/*This method creates a string from the dataByPillar method and sends it to the FileWriter.*/
	public String outputStringbyPillar () {

		StringBuilder SB = new StringBuilder();
		SB.append("Frame Index" + "," + "Pillar Index" + "," + "Force (pN)" + "\n");

		for (int i=0; i<dataByPillar.size(); i++) {
			SB.append(dataByPillar.get(i) + "\n");
		}

		SB.append("\n");

		for (int j=0; j<mean.size(); j++) {
			SB.append("AVG Force (pN): " + "," + " ," + mean.get(j) + "\n");
			SB.append("SD: " + "," + " ," + standard_deviation.get(j) + "\n");
		}

		String output = SB.toString();
		String identifier = "dataByPillar";
		FileManager = new FileManager ();
		FileManager.fileWriter(identifier, output);	

		return output;
	}


	/*This method builds a string for the multipillar method and sends it to the FileWriter*/
	public String outputStringMultipillar (String[] values) {

		StringBuilder SB = new StringBuilder();
		SB.append("Pillar Index" + "," + "AVG Force (pN)" + "," + "SD" + "\n");

		for (int i=0; i<multipillar.size(); i++) {
			SB.append(values [i] + "," + mean.get(i) + " ," + standard_deviation.get(i) + "\n");
		}	

		String output = SB.toString();
		String identifier = "multipillar";
		FileManager = new FileManager ();
		FileManager.fileWriter(identifier, output);
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