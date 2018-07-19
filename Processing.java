import java.util.*;


public class Processing {

	private ArrayList<Integer> frame, pillar;
	private ArrayList<Double> x, y, dx, dy, deflection, nanometers, picoNewtons;
	private String [][] data;
	private Object [][] newData;
	private int rows, frames, uniquePillars;
	private double frameAverage, pillarAverage; 


	public Processing () {	

	}


	/*Getters and Setters*/

	public ArrayList<Integer> getFrame() {return frame;}
	public void setFrame(ArrayList<Integer> frame) {this.frame = frame;}
	public ArrayList<Integer> getPillar() {return pillar;}
	public void setPillar(ArrayList<Integer> pillar) {this.pillar = pillar;}
	public ArrayList<Double> getDx() {return dx;}
	public void setDx(ArrayList<Double> dx) {this.dx = dx;}
	public ArrayList<Double> getDy() {return dy;}
	public void setDy(ArrayList<Double> dy) {this.dy = dy;}
	public ArrayList<Double> getDeflection() {return deflection;}
	public void setDeflection(ArrayList<Double> deflection) {this.deflection = deflection;}
	public ArrayList<Double> getNanometers() {return nanometers;}
	public void setNanometers(ArrayList<Double> nanometers) {this.nanometers = nanometers;}
	public Object[][] getNewData() {return newData;}
	public void setNewData(Object[][] newData) {this.newData = newData;}
	public int getFrames() {return frames;}
	public void setFrames(int frames) {this.frames = frames;}
	public int getUniquePillars() {return uniquePillars;}
	public void setUniquePillars(int uniquePillars) {this.uniquePillars = uniquePillars;}


	public Processing (ArrayList<String> fileLine) { // We need to deal with the NaN issue.

		rows = fileLine.size();
		int columns = 7;

		System.out.println("We have found size: " + rows);

		data = new String [rows][columns];

		frame = new ArrayList<Integer>();
		pillar = new ArrayList<Integer>();
		x = new ArrayList<Double>();
		y = new ArrayList<Double>();
		dx = new ArrayList<Double>();
		dy = new ArrayList<Double>();
		deflection = new ArrayList<Double>();

		for (int i = 0; i < rows; i++) {

			data [i] = fileLine.get(i).split(",");

			frame.add (Integer.parseInt(data [i][0]));
			pillar.add (Integer.parseInt(data [i][1]));
			x.add (Double.parseDouble(data [i][2]));
			y.add (Double.parseDouble(data [i][3]));
			dx.add (Double.parseDouble(data [i][4]));
			dy.add (Double.parseDouble(data [i][5]));
			deflection.add (Double.parseDouble(data [i][6]));
		}
	}


	public ArrayList<Double> nanoMeters (double conversion) { 

		nanometers = new ArrayList <Double>();

		for (int i= 0; i<rows; i++) {

			double nm = deflection.get(i) * conversion;
			nanometers.add(nm);
		}

		//System.out.println(nanometers);
		return nanometers;
	}


	public ArrayList<Double> forces (double youngsModulous, double pillarD, double pillarL) {

		picoNewtons = new ArrayList <Double>();

		double constant = (double) 3/64;
		double E = youngsModulous; //double E = 2.0 for PDMS;
		double pi = Math.PI;
		double diameter = pillarD; //double diameter = 0.5 for the 500 nm pillars;
		double length = pillarL; //double length = 1.3 for the 500 nm pillars;

		for (int i=0; i<rows; i++) {

			double picoMeters = nanometers.get(i)*1000;
			double picoForces = (constant * pi *E * (Math.pow(diameter, 4)/Math.pow(length, 3))*picoMeters);
			picoNewtons.add(picoForces);
			//System.out.println(String.format("%.10f", picoNewtons.get(i)));
		}

		return picoNewtons;
	}


	public Object [][] newDataArray () {

		int columns = 9;
		newData = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {

			newData [i][0] = frame.get(i);
			newData [i][1] = pillar.get(i);
			newData [i][2] = x.get(i);
			newData [i][3] = y.get(i);
			newData [i][4] = dx.get(i);
			newData [i][5] = dy.get(i);
			newData [i][6] = deflection.get(i);
			newData [i][7] = nanometers.get(i);	
			newData [i][8] = picoNewtons.get(i); 
			//System.out.println("Here is newData: " + Arrays.toString(newData[i]));
		}

		return newData;
	}


	public int getNumberOfFrames () {

		frames = (int) newData [0][0];

		for (int i = 0; i< newData.length; i++) {

			if ((int) newData [i][0] > frames) {

				frames = (int) newData [i][0]; 
			}
		}

		System.out.println("Here are the number of frames: " + frames);
		return frames;
	}


	public int getNumberOfUniquePillars () {

		uniquePillars = 0;
		int pillars = 0; // there will never be a pillar 0.

		for (int i = 0; i< newData.length; i++) {

			if ((int) newData [i][1] > pillars) {

				pillars = (int) newData [i][1];
				uniquePillars ++;	
			}
		}

		System.out.println("Here are the number of unique pillars: " + uniquePillars);
		return uniquePillars;
	}


	public void byFrame (int ID) { //This will need checks for non-existent frames. This should probably be in its own class.

		ArrayList <Double> byFrame = new ArrayList<Double>();

		for (int i = 0; i<rows; i++) {

			int frameID = (int) newData [i][0];
			if (frameID == ID) {
				double something = (double) newData [i][8];
				byFrame.add(something);
				//System.out.println("Here is the row for Frame: " + Arrays.toString(newData[i]));
				//System.out.println("Here is picoNewtons for Frame: " + (newData[i][8]));
			}
		}

		System.out.println("Here is byframe: " + byFrame);
		for (double d : byFrame) {
			frameAverage += d;
		}

		frameAverage = frameAverage/byFrame.size();
		//System.out.println("Here is byframe average: " + frameAverage);
	}


	public void byPillar (int ID) { //This will need checks for non-existent trajectories. This should probably be in its own class with byFrame.

		ArrayList <Double> byPillar = new ArrayList<Double>();

		for (int i = 0; i<rows; i++) {

			int pillarID = (int) newData[i][1];
			if (pillarID == ID) {
				double something = (double) newData[i][8];
				byPillar.add(something);
				//System.out.println("Here is the row for Trajectory: " + Arrays.toString(newData[i]));
				//System.out.println("Here is picoNewtons for Trajectory: " + (newData[i][8]));
			}
		}

		System.out.println("Here is byPillar: " + byPillar);
		for (double d: byPillar){
			pillarAverage += d; 	
		}

		pillarAverage = pillarAverage/byPillar.size();
		//System.out.println("Here is byPillar average: " + pillarAverage);
	}


	public void allFrames () {



		for (int i = 0; i<uniquePillars; i++) {

			double averageTest =0.0;
			int pillarCounter = (int) newData [i][1];
			System.out.println("This is the value of pillarCounter: " + pillarCounter);

			for (int j =0; j< rows; j++) {


				int pillarID = (int) newData [j][1];
				//System.out.println("This is the value of pillarID: " + pillarID);

				if (pillarID == pillarCounter) {
					double something = (double) newData [j][8];
					averageTest += (double) newData [j][8];
					System.out.println("This is the force value matching the pillar: " + something);

				}
			}	

			System.out.println("Averagetest: " + averageTest/frames);
			System.out.println("This is the next value of pillarCounter: " + pillarCounter);
		}
	}


	public String outputString () { // For the reader.

		StringBuilder SB = new StringBuilder();
		for (int i = 0; i<rows; i++) {
			double x = dx.get(i);
			double y = dy.get(i);
			double deflect = deflection.get(i);
			double nm = nanometers.get(i);
			String dxVal = String.format("%.4f", x);
			String dyVal = String.format("%.4f", y);
			String deflectVal = String.format("%.3f", deflect);
			String nmVal = String.format("%.4f", nm);

			SB.append (String.format ("%3s", frame.get(i)) + "\t" + String.format("%8.4s", pillar.get(i)) + "\t" + String.format("%8.7s", dxVal) + "\t" 
					+ String.format("%8.7s", dyVal) + "\t" + String.format("%8.7s",deflectVal) + "\t" + String.format("%10.8s",  nmVal) 
					+ "\t" + String.format("%10.9s",  picoNewtons.get(i))+ "\n");
		}

		String output = SB.toString();
		return output;		
	}


	public String outputFile () { // For a CSV file.

		String header = ("frame" + "," + "pillar" + "," + "x" + "," + "y" + ","  + "dx" + ","
				+ "dy" + "," + "deflection" + "," + "nanometers"+ "," + "picoNewtons" + ",");
		String body = "";

		for (int i =0; i<rows;i++) {
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