import java.util.*;


/**Defines a class that creates the data array*/

public class Processing {

	/*Instance variables for the arrays.*/
	private Object [][] data, newData;
	private double [] nanometers, picoNewtons;
	private OutputData OutputData;


	/*Constructor*/
	public Processing () {
	}

	
	/*Getters*/
	public Object[][] getData() {return data;}
	public Object[][] getNewData() {return newData;}
	

	/**This method breaks the ArrayList<String> into a 2D array using "," as the delimiter. It
	 * starts by removing lines containing the word "NaN"/"Frame Index", which aren't processed.
	 * @param fileLine - the ArrayList containing the lines of the file.
	 * @return data - an array containing the file data.
	 */

	public Object [][] data (ArrayList<String> fileLine) {

		if (fileLine.isEmpty()) {
			
			String message = "There was a problem with your file";			
			LogFile log = new LogFile ();
			log.writeToLog(message, null);
			// Calls the log file if there is an error.
		}

		for (int i = 0; i < 2; i++) { // runs twice owing to an error with 2 NaNs in a row

			for (int j =0; j<fileLine.size(); j++) {

				if (fileLine.get(j).contains("NaN") ||  fileLine.get(j).contains("Frame Index")) {

					fileLine.remove(j);
				}
			}
		}

		data = new Object [fileLine.size()][7];

		for (int i = 0; i < fileLine.size(); i++) {

			data [i] = fileLine.get(i).split(",");
		}
		
		return data;
	}


	/**This method converts pixels into nanometres for the deflection values.
	 * @param conversion - converts pixels to nanometres.
	 * @return an array of deflection values in nanometres.
	 */

	public double [] nanoMeters (int conversion) {	

		nanometers = new double [data.length];

		for (int i= 0; i<data.length; i++) {

			double nm =  Double.parseDouble((String) data [i][6]) * conversion;
			nanometers [i] = nm;
		}

		return nanometers;
	}


	/**This method calculates the picoNewton forces from all the values provided.
	 * @param youngsM - material stiffness parameter.
	 * @param pillarD - the pillar diameter parameter.
	 * @param pillarL - the pillar length parameter.
	 * @return an array of forces in piconewtons.
	 */

	public double [] forces (double youngsM, double pillarD, double pillarL) {

		picoNewtons = new double [data.length];

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


	/**This method creates a new array with the added nanometres and picoNewton values.
	 * @return an array containing all data for the pillars.
	 */

	public Object [][] newDataArray () {

		newData = new Object [data.length][9];

		for (int i = 0; i < data.length; i++) {

			newData [i][0] = data [i][0]; // frame
			newData [i][1] = data [i][1]; // pillar
			newData [i][2] = data [i][2]; // x
			newData [i][3] = data [i][3]; // y
			newData [i][4] = data [i][4]; // dx
			newData [i][5] = data [i][5]; // dy
			newData [i][6] = data [i][6]; // deflection
			newData [i][7] = nanometers [i];
			newData [i][8] = picoNewtons [i];
		}

		OutputData = new OutputData (null, this);
		OutputData.outputString();
		// Calls the method for loading values to the JTable.	
		
		return newData;
	}
}