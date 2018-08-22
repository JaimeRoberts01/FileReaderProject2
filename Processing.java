import java.util.*;


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
	public double[] getNanometers() {return nanometers;}
	public double[] getPicoNewtons() {return picoNewtons;}
	

	/**This method breaks the ArrayList<String> into a 2D array using "," as the delimiter. 
	 * It starts by removing lines that contain the word "NaN"/"Frame Index", which aren't 
	 * processed. @author jemmanatasharoberts.
	 */

	public Object [][] data (ArrayList<String> fileLine) {

		if (fileLine.isEmpty()) {
			System.out.println("Something is wrong");
		}

		for (int i = 0; i < 2; i++) {

			for (int j =0; j<fileLine.size(); j++) {

				if (fileLine.get(j).contains("NaN") ||  fileLine.get(j).contains("Frame Index")) {

					fileLine.remove(j);
				}
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


	/**This method converts pixels into nanometres for the deflection values.
	 * @return an array of deflection values in nanometres.
	 */

	public double [] nanoMeters (int conversion) {	

		int columns = data.length;
		nanometers = new double [columns];

		for (int i= 0; i<this.data.length; i++) {

			double nm =  Double.parseDouble((String) data [i][6]) * conversion;
			nanometers [i] = nm;
		}

		return nanometers;
	}


	/**This method calculates the picoNewton forces from all the values provided.
	 * @return an array of forces in piconewtons.
	 */

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


	/**This method creates a new array with the added nanometres and picoNewton values.
	 * @ returns an array containing all data for the pillars.*/

	public Object [][] newDataArray () {

		int columns = 9;
		int rows = data.length;

		newData = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {

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
		return newData;
	}
}