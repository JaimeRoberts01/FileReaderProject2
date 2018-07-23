import java.util.*;


public class Processing {
	
	/*Instance variables for the arrays.*/
	private Object [][] data, newData;
	private double [] nanometers, picoNewtons;
	private int frames, uniquePillars; 
	//private GUIClass GUI;

//	public Processing (int conversion, double youngsModulous, double pillarD, double pillarL) {	
//
//	}
	public Processing () {	

	}


	/*Getters and Setters*/

	public Object[][] getData() {return data;}
	public void setData(Object[][] data) {this.data = data;}
	public Object[][] getNewData() {return newData;}
	public void setNewData(Object[][] newData) {this.newData = newData;}
	public int getFrames() {return frames;}
	public void setFrames(int frames) {this.frames = frames;}
	public int getUniquePillars() {return uniquePillars;}
	public void setUniquePillars(int uniquePillars) {this.uniquePillars = uniquePillars;}


	/*This method breaks the ArrayList<String> into a 2D array using "," as the delimiter. It 
	starts by removing lines that contain the word "NaN"/"Frame Index", which aren't processed*/
 
	public Object [][] data (ArrayList<String> fileLine) { 

		for (int i = 0; i < fileLine.size(); i++) {

			if (fileLine.get(i).contains("NaN") || fileLine.get(i).contains("Frame Index")) {
				fileLine.remove(i);
			}
		}

		int rows = fileLine.size();
		int columns = 7;

		data = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {

			data [i] = fileLine.get(i).split(",");
			System.out.println(Arrays.toString(data[i]));
		}
		System.out.println ("Data length: " + data.length);
		return data;
	}

	
	/*This method converts pixels into nanometres for the deflection values.*/
	public double [] nanoMeters (int conversion) {

		int columns = data.length;
		nanometers = new double [columns];

		for (int i= 0; i<this.data.length; i++) {

			double nm =  Double.parseDouble((String) data [i][6]) * conversion;
			nanometers [i] = nm;
		}

		return nanometers;
	}

	
	/*This method calculates the picoNewton forces from all the values provided .*/
	public double [] forces (double youngsModulous, double pillarD, double pillarL) {

		int columns = data.length;
		picoNewtons = new double [columns];

		double constant = (double) 3/64;
		double E = youngsModulous; //double E = 2.0 for PDMS;
		double pi = Math.PI;
		double diameter = pillarD; //double diameter = 0.5 for the 500 nm pillars;
		double length = pillarL; //double length = 1.3 for the 500 nm pillars;

		for (int i=0; i<data.length; i++) {
	
			double picoMeters = (double) nanometers [i] *1000;
			double picoForces = (constant * pi *E * (Math.pow(diameter, 4)/Math.pow(length, 3))*picoMeters);
			picoNewtons [i] = picoForces;
		}

		return picoNewtons;
	}


	/*This method creates a new array with the added nanometres and picoNewton values.*/
	public Object [][] newDataArray () {

		int columns = 9;
		int rows = data.length;
		
		newData = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {
					
			newData [i][0] = data [i][0]; //frame
			newData [i][1] = data [i][1]; // pillar
			newData [i][2] = data [i][2]; // x
			newData [i][3] = data [i][3]; // y
			newData [i][4] = data [i][4]; // dx
			newData [i][5] = data [i][5]; // dy
			newData [i][6] = data [i][6]; // deflection
			newData [i][7] = nanometers [i];
			newData [i][8] = picoNewtons [i];

			System.out.println("Here is newData: " + Arrays.toString (newData[i]));
		}

		return data;
	}

	
	/*This method cycles through the frames and finds the highest number of frames in the dataset
	 e.g. There are usually 91 frames with 5535 sets of pillar data in each frame.*/
	public int getNumberOfUniqueFrames () {

		frames = Integer.parseInt((String) data [0][0]);

		for (int i = 0; i< data.length; i++) {

			if (Integer.parseInt((String) data [i][0]) > frames) {

				frames = Integer.parseInt((String) data [i][0]); 
			}
		}

		System.out.println("Here are the number of frames: " + frames);
		return frames;
	}


	/*This method cycles through the pillars to find how many pillars there are in a frame. This
	 one needs more work - I can't remember what I wanted it for.*/
	public int getNumberOfUniquePillars () {

		uniquePillars = 0;
		int pillars = 0; // there will never be a pillar 0.

		for (int i = 0; i< data.length; i++) {

			if (Integer.parseInt((String) data [i][1]) > pillars) {

				pillars = Integer.parseInt((String) data [i][1]);
				uniquePillars ++;	
			}
		}

		System.out.println("Here are the number of unique pillars: " + uniquePillars);
		return uniquePillars;
	}


	//	public void byFrame (int ID) { //This will need checks for non-existent frames. This should probably be in its own class.
	//
	//		ArrayList <Double> byFrame = new ArrayList<Double>();
	//
	//		for (int i = 0; i<rows; i++) {
	//
	//			int frameID = (int) newData [i][0];
	//			if (frameID == ID) {
	//				double something = (double) newData [i][8];
	//				byFrame.add(something);
	//				//System.out.println("Here is the row for Frame: " + Arrays.toString(newData[i]));
	//				//System.out.println("Here is picoNewtons for Frame: " + (newData[i][8]));
	//			}
	//		}
	//
	//		System.out.println("Here is byframe: " + byFrame);
	//		for (double d : byFrame) {
	//			frameAverage += d;
	//		}
	//
	//		frameAverage = frameAverage/byFrame.size();
	//		//System.out.println("Here is byframe average: " + frameAverage);
	//	}
	//
	//
	//	public void byPillar (int ID) { //This will need checks for non-existent trajectories. This should probably be in its own class with byFrame.
	//
	//		ArrayList <Double> byPillar = new ArrayList<Double>();
	//
	//		for (int i = 0; i<rows; i++) {
	//
	//			int pillarID = (int) newData[i][1];
	//			if (pillarID == ID) {
	//				double something = (double) newData[i][8];
	//				byPillar.add(something);
	//				//System.out.println("Here is the row for Trajectory: " + Arrays.toString(newData[i]));
	//				//System.out.println("Here is picoNewtons for Trajectory: " + (newData[i][8]));
	//			}
	//		}
	//
	//		System.out.println("Here is byPillar: " + byPillar);
	//		for (double d: byPillar){
	//			pillarAverage += d; 	
	//		}
	//
	//		pillarAverage = pillarAverage/byPillar.size();
	//		//System.out.println("Here is byPillar average: " + pillarAverage);
	//	}


//	public void allFrames () { // needs moving to its own class after it is sorted
//
//		for (int i = 0; i<uniquePillars; i++) {
//
//			double averageTest =0.0;
//			int pillarCounter = Integer.parseInt((String) newData [i][1]);
//			System.out.println("This is the value of pillarCounter: " + pillarCounter);
//
//			for (int j =0; j< newData.length; j++) {
//
//				int pillarID = Integer.parseInt((String) newData [j][1]);
//	
//				if (pillarID == pillarCounter) {
//					double something = (double) newData [j][8];
//					averageTest += (double) newData [j][8];
//					System.out.println("This is the force value matching the pillar: " + something);
//				}
//			}	
//
//			System.out.println("Averagetest: " + averageTest/frames);
//			System.out.println("This is the next value of pillarCounter: " + pillarCounter);
//		}
//	}
		

	public void allFrames () { // needs moving to its own class after it is sorted

		ArrayList <Integer> pillar = new ArrayList <Integer> ();

		for (int i = 0; i<newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]); //this is starting the iteration through the array
			boolean contains = pillar.contains(pillarID);
			
			if (contains != true) {
				pillar.add(pillarID);
			}
		}
		
		Collections.sort(pillar);
		
		for (int i : pillar) {System.out.println("pillar: " + i);}

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
	}	
	
	
	/*This method calculates the average and standard deviation of the picoNewton values in the data.*/
	public void statistics (ArrayList<Double> pico) {
		
		double average = 0.0;
		double sd = 0.0;
		
		for (double avg : pico) {
			average += avg;			
		}
		average = average/pico.size();
		System.out.println("Average: " + average);
		
		
		for (double stdev : pico) {
		
			sd += Math.pow((stdev-average), 2);			
		}
		sd = Math.sqrt(sd/pico.size());
		System.out.println("SD: " + sd);
	}


	/*This method builds a string for the ReportFrame2 to test proper output; it will be deleted eventually.*/
	public String outputString () { 

		StringBuilder SB = new StringBuilder();
		for (int i = 0; i <newData.length; i++) {
		
			SB.append(String.format("%3s",newData[i][0]) + "\t" + String.format("%8.4s", newData [i][1])  + "\t"  + String.format("%8.7s", newData [i][4]) + "\t" 
					+ String.format("%8.7s", newData[i][5]) + "\t"  + String.format("%8.7s", newData [i][6]) + "\t" + String.format("%10.7s", newData [i][7]) 
					+ "\t" + String.format("%10.8s", newData [i][8]) + "\n");
		}

		String output = SB.toString();	
		return output;
	}


	/*This method builds a string for the output file, which is another CSV file.*/
	public String outputFile () {

		String header = ("frame" + "," + "pillar" + "," + "x" + "," + "y" + ","  + "dx" + ","
				+ "dy" + "," + "deflection" + "," + "nanometers"+ "," + "picoNewtons" + ",");
		String body = "";

		for (int i =0; i<newData.length;i++) {
	
			body += Arrays.toString(newData[i]) +"\n";
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		outputFile = outputFile.replace("[", "");
		outputFile = outputFile.replace("]", "");

		return outputFile;
	}
}