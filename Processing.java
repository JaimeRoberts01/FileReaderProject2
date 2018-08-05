import java.util.*;


public class Processing {

	/*Instance variables for the arrays.*/
	private Object [][] data, newData;
	private double [] nanometers, picoNewtons;
	//	private GUIClass GUI;
	//	private FileManager FileManager;


	/*Constructor*/
	public Processing () {


	}


	/*Getters and Setters*/
	//public Object[][] getData() {return data;}
	//public void setData(Object[][] data) {this.data = data;}
	public Object[][] getNewData() {return newData;}
	public void setNewData(Object[][] newData) {this.newData = newData;}
	public double[] getNanometers() {return nanometers;}
	public void setNanometers(double[] nanometers) {this.nanometers = nanometers;}
	public double[] getPicoNewtons() {return picoNewtons;}
	public void setPicoNewtons(double[] picoNewtons) {this.picoNewtons = picoNewtons;}


	/*This method breaks the ArrayList<String> into a 2D array using "," as the delimiter. It 
	starts by removing lines that contain the word "NaN"/"Frame Index", which aren't processed*/
	public Object [][] data (ArrayList<String> fileLine) { 

		//		for (int i = 0; i < 2; i++) {
		//
		//			for (int j =0; j<fileLine.size(); j++) {
		//				
		//			if (fileLine.get(j).contains("NaN") ||  fileLine.get(j).contains("Frame Index")) {
		//
		//				fileLine.remove(j);
		//			}
		//		}
		//		}


		for (int i =0; i<fileLine.size(); i++) {

			if (fileLine.get(i).contains("NaN") ||  fileLine.get(i).contains("Frame Index")) {

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
	public double [] forces (double youngsM, double pillarD, double pillarL) {

		int columns = data.length;
		picoNewtons = new double [columns];

		double constant = (double) 3/64;
		double E = youngsM; //double E = 2.0 for PDMS;
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

			//	System.out.println("Here is newData: " + Arrays.toString (newData[i]));
		}

		return newData;
	}


	/*This method builds a string for the ReportFrame2.*/
	public String outputString () { 

		StringBuilder SB = new StringBuilder();
		
		for (int i = 0; i <newData.length; i++) {

			SB.append(String.format("%3s",newData[i][0]) + "\t" + String.format("%8.4s", newData [i][1])  + "\t"  + String.format("%8.7s", newData [i][4]) + "\t" 
			+ String.format("%8.7s", newData[i][5]) + "\t"  + String.format("%8.7s", newData [i][6]) + "\t" + String.format("%9.7s", newData [i][7]) 
			+ "\t" + String.format("%9.8s", newData [i][8]) + "\n");
		}

		String output = SB.toString();	
		return output;
	}


	/*This method builds a string for the output file, which is another CSV file.*/
	public String outputFile () {

		String header = ("Frame Index" + "," + "Pillar Index" + "," + "x" + "," + "y" + ","  + "dx" + ","
				+ "dy" + "," + "Deflection (px)" + "," + "Deflection (nm)"+ "," + "Forces (pN)" + ",");
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