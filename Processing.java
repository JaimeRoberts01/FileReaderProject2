import java.util.*;


public class Processing {

	private ArrayList<String> frame, pillar;
	private ArrayList<Double> x, y, dx, dy, deflection, nanometers, picoNewtons;
	private String [][] data;
	private Object [][] newData;
	private int rows;
	private double frameAverage, trajectoryAverage; //, frameStandardDeviation, trajectoryStandardDeviation;
	private GUIClass inputData;


	public Processing () {	

	}


	/*Getters and Setters*/

	public ArrayList<String> getFrame() {return frame;}
	public void setFrame(ArrayList<String> frame) {this.frame = frame;}
//	public ArrayList<String> getTrajectory() {return trajectory;}
//	public void setTrajectory(ArrayList<String> trajectory) {this.trajectory = trajectory;}
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


	public Processing (ArrayList<String> fileLine) {

		rows = fileLine.size();
		int columns = 7;

		System.out.println("We have found size: " + rows);

		data = new String [rows][columns];

		frame = new ArrayList<String>();
		pillar = new ArrayList<String>();
		x = new ArrayList<Double>();
		y = new ArrayList<Double>();
//		x_raw = new ArrayList<Double>();
//		y_raw = new ArrayList<Double>();
		dx = new ArrayList<Double>();
		dy = new ArrayList<Double>();
		deflection = new ArrayList<Double>();



		for (int i = 0; i < rows; i++) {
			data [i] = fileLine.get(i).split(",");
			frame.add (data [i][0]);
			pillar.add (data [i][1]);
			x.add (Double.parseDouble(data [i][2]));
			y.add (Double.parseDouble(data [i][3]));
//			x_raw.add (Double.parseDouble(data [i][4]));
//			y_raw.add (Double.parseDouble(data [i][5]));
			dx.add (Double.parseDouble(data [i][4]));
			dy.add (Double.parseDouble(data [i][5]));
			deflection.add (Double.parseDouble(data [i][6]));
		}

	}

	//		for (String s : trajectory) {
	//		System.out.println("tragic " + s);
	//		}

	//System.out.println("frame: " + frame + "\n" + "trajectory: " + trajectory + "\n" + "dx: " + dx + "\n" + "dy: " + dy);


	public ArrayList<Double> nanoMeters (double conversion) { // This method should calculate nanometers

		
		//System.out.println("THIS IS CONVERSION: " + inputData.getConversion());
		nanometers = new ArrayList <Double>();

		for (int i=0; i<rows;i++) {

			//double nm = deflection.get(i) * 73;
			double nm = deflection.get(i) * conversion; // this doesn't work
			nanometers.add(nm);
		}
		//System.out.println(nanometers);
		return nanometers;
	}


	public ArrayList<Double> forces (double youngsModulous, double pillarD, double pillarL) {

		picoNewtons = new ArrayList <Double>();

		double constant = (double) 3/64;
		//double E = 2.0;
		double E = youngsModulous;
		double pi = Math.PI;
		//double diameter = 0.5;
		double diameter = pillarD;
		//double length = 1.3;
		double length = pillarL;

		for (int i=0; i<rows; i++) {

			double picoMeters = nanometers.get(i)*1000;
			double picoForces = (constant * pi *E * (Math.pow(diameter, 4)/Math.pow(length, 3))*picoMeters);
			picoNewtons.add(picoForces);
			System.out.println(String.format("%.10f", picoNewtons.get(i)));
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
//			newData [i][4] = x_raw.get(i);
//			newData [i][5] = y_raw.get(i);
			newData [i][4] = dx.get(i);
			newData [i][5] = dy.get(i);
			newData [i][6] = deflection.get(i);
			newData [i][7] = nanometers.get(i);	
			newData [i][8] = picoNewtons.get(i); 
			System.out.println("Here is newData: " + Arrays.toString(newData[i]));
		}

		return newData;
	}

	
	public void byFrame () { //This will need checks for non-existent frames

		ArrayList <Double> byFrame = new ArrayList<Double>();

		for (int i = 0; i<rows; i++) {

			String frameID = (String) newData[i][0];

			if (frameID.equals("1")) {
				double something = (double) newData [i][8];
				byFrame.add(something);
				//System.out.println("Here is the row for Frame: " + Arrays.toString(newData[i]));
				System.out.println("Here is picoNewtons for Frame: " + (newData[i][8]));
			}
		}

		System.out.println("Here is byframe: " + byFrame);
		for (double d : byFrame) {
			frameAverage += d;
		}

		frameAverage = frameAverage/byFrame.size();
		System.out.println("Here is byframe average: " + frameAverage);
	}


	public void byTrajectory () { //This will need checks for non-existent trajectories

		ArrayList <Double> byPillar = new ArrayList<Double>();

		for (int i = 0; i<rows; i++) {

			String pillarID = (String) newData[i][1];

			if (pillarID.equals("2")) {
				double something = (double) newData[i][8];
				byPillar.add(something);
				//System.out.println("Here is the row for Trajectory: " + Arrays.toString(newData[i]));
				System.out.println("Here is picoNewtons for Trajectory: " + (newData[i][8]));
			}
		}

		System.out.println("Here is byTrajectory: " + byPillar);
		for (double d: byPillar){
			trajectoryAverage += d; 	
		}

		trajectoryAverage = trajectoryAverage/byPillar.size();
		System.out.println("Here is byTrajecotry average: " + trajectoryAverage);
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