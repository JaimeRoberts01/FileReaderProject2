import java.util.*;


/**Defines a class that manipulates the data in the data array*/

public class DataProcessing {
	

	/*Instance variables*/
	private ArrayList <Double> mean, standard_deviation, force; 
	private ArrayList <Object> dataByFrame, outputDataByFrame;
	private ArrayList <Object> pillarFrame, dataByPillarFrame; 
	private ArrayList <Object> outputDataByPillar, outputMultipillar; 
	private ArrayList <Integer> pillar, frame;	
	private Object [] values; 
	
	private MultipillarInput MultipillarInput;
	
	/*Default constructor*/
	DataProcessing () {
	}
	

	/*Constructor*/
	DataProcessing (MultipillarInput MultipillarInput) {	
		this.MultipillarInput = MultipillarInput;
	}


	/*Getters*/
	public ArrayList<Double> getMean() {return mean;}
	public ArrayList<Double> getStandard_deviation() {return standard_deviation;}
	public ArrayList<Integer> getPillar() {return pillar;}
	public ArrayList<Object> getDataByPillarFrame() {return dataByPillarFrame;}
	public ArrayList<Object> getOutputDataByPillar() {return outputDataByPillar;}
	public ArrayList<Object> getOutputDataByFrame() {return outputDataByFrame;}
	public ArrayList<Object> getOutputMultipillar() {return outputMultipillar;}
	public Object[] getValues() {return values;}
	public ArrayList<Integer> getFrame() {return frame;}
	public ArrayList<Object> getDataByFrame() {return dataByFrame;}
	public ArrayList<Object> getPillarFrame() {return pillarFrame;}
	public ArrayList<Double> getForce() {return force;}


	/**This method creates a list of the pillars in the newData array. 
	 * It is used for initialising the first pillar value and sets up 
	 * allFrames so that it can collect data for specific pillars.
	 * @param data - the data array.
	 * @return pillar - an arrayList of pillar IDs for the whole data.
	 */

	public ArrayList <Integer> getPillars (Object [][] data) {

		pillar = new ArrayList <Integer> (); // An ArrayList of pillar IDs.

		for (int i = 0; i<data.length; i++) {

			int pillarID = Integer.parseInt((String) data [i][1]); 

			if (!pillar.contains(pillarID)) {
				
				pillar.add(pillarID);
			}
		}

		Collections.sort(pillar); // Some data is missing for each subset of data.
		return pillar;
	}


	/** This method creates a list of the frames in the newData array.
	 * It is used to set the slider for the getByFrame functionality.
	 * @param data - the data array.
	 * @return frame - an ArrayList of frame IDs for the whole data.
	 */

	public ArrayList <Integer> getFrames (Object [][] data) {

		frame = new ArrayList <Integer> (); // An ArrayList of frame IDs.

		for (int i = 0; i<data.length; i++) {
 
			int frameID = Integer.parseInt((String) data [i][0]); 

			if (!frame.contains(frameID)) {
				
				frame.add(frameID);
			}
		}

		Collections.sort(frame); // Some data is missing for each subset of data.
		return frame;
	}


	/**This method loops through the pillar ArrayList identifying pillars of the same 
	 * ID from all the frames in the dataset. Pillars of the same ID are added to a 
	 * temporary ArrayList, which is used to calculate averages and standard deviation.
	 * @param newData - the newData array.
	 */

	public void allFrames (Object [][] newData) {
		
		mean = new ArrayList<Double> (); // ArrayList of averages.
		standard_deviation = new ArrayList<Double> (); // ArrayList of standard deviation.
		force = new ArrayList <Double> (); // ArrayList of forces.

		for (int j = 0; j < pillar.size(); j++) {

			int pillarArray = pillar.get(j);
			
			for (int k = 0; k < newData.length; k++) {

				int pillarIndex = Integer.parseInt((String) newData [k][1]);

				if (pillarIndex == pillarArray) {

					force.add ((double) newData [k][8]);	
				}
			}

			statistics (force); // Calls the statistics methods and gives it the forces.
			force.clear();
		}
	}	


	/**This method generates a list of FrameID, PillarID and Forces relating all pillars across a single Frame
	 * @param newData - the newData array.
	 * @param ID - the ID of the frame.
	 */

	public void dataByFrame (Object [][] newData, int ID) {

		dataByFrame = new ArrayList <Object>(); // ArrayList of forces.
		outputDataByFrame = new ArrayList <Object>(); // ArrayList of values for output.

		for (int i = 0; i< newData.length; i++) {

			int frameID = Integer.parseInt((String) newData [i][0]);

			if (frameID == ID) {

				outputDataByFrame.add(newData [i][0] + "," + newData [i][1] + "," + newData [i][8]);	
				dataByFrame.add(newData [i][8]);
			}	
		}

		DataOutput DataOutput = new DataOutput (this, null);
		DataOutput.stringByFrame (ID);
		// Calls the method for loading values to the JTable.	
	}


	/**This method generates a list of FrameID, PillarID and Forces relating to a single pillar across all the frames
	 * @param newData - the newData array.
	 * @param ID - the ID of the pillar.
	 */

	public void dataByPillar (Object [][] newData, int ID) { 

		mean = new ArrayList<Double> (); // ArrayList of averages.
		standard_deviation = new ArrayList<Double> (); // ArrayList of standard deviations.
		
		pillarFrame = new ArrayList <Object> (); // ArrayList of frames associated with a pillar.
		outputDataByPillar = new ArrayList <Object> (); // ArrayList of values for output.

		force = new ArrayList <Double> (); // ArrayList of forces.

		for (int i = 0; i< newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]);

			if (ID == pillarID) {

				force.add((double) newData [i][8]);
				outputDataByPillar.add(newData [i][0] + "," + newData [i][1] + "," + newData [i][8]);
				pillarFrame.add(newData [i][0]);
			}	
		}

		if (force.isEmpty()) { // Assumes there was no pillar with the entered ID.

			String message = "Invalid user input - check input variable integer: " + ID;
			
			LogFile log = new LogFile ();
			log.writeToLog(message, null);
			// Calls the log file if there is an error.
			return;
		}

		else {

			statistics (force); // Calls the statistics methods and gives it the forces.
			
			DataOutput DataOutput = new DataOutput (this, null);
			DataOutput.stringByPillar (ID);
			// Calls the method for loading values to the JTable.	
			force.clear();
		}
	}


	/**This method is for the multipillar functionality and is similar to the allFrames
	 * method but does not use the pillar ArrayList for ordering the pillars.
	 * @param newData - the newData array.
	 */

	public void multiPillar (Object [][] newData) {

		mean = new ArrayList<Double> (); // ArrayList of averages.
		standard_deviation = new ArrayList<Double> (); // ArrayList of standard deviations.
		
		force = new ArrayList <Double> (); // ArrayList of forces.
		outputMultipillar = new ArrayList <Object> (); // ArrayList of values for output.
		
		values = new Object [MultipillarInput.getID().length]; // An array of IDs

		for (int i =0; i< MultipillarInput.getID().length; i++) {
			
			values = MultipillarInput.getID();
		}
		
		for (int i = 0; i < values.length; i++) {

			int value = Integer.parseInt((String) values [i]);

			for (int j =0; j< newData.length; j++) {

				if (value == Integer.parseInt ((String) newData [j][1])) {

					force.add((Double) newData [j][8]);
					outputMultipillar.add(newData [j][0] + "," + newData [j][1] + "," + newData [j][8]); 
				}
			}	
			
			statistics(force); // Calls the statistics methods and gives it the forces.
		}

		DataOutput DataOutput = new DataOutput (this, null);
		DataOutput.StringMultipillar ();
		// Calls the method for loading values to the JTable.	
		force.clear();
	}
		

	/**This method calculates the average and standard 
	 * deviation of the forceNewton values in the data.
	 * @param force - an ArrayList of forces.
	 */

	public void statistics (ArrayList<Double> force) {

		double average = 0.0;
		double sd = 0.0;

		for (double avg : force) {

			average += avg;			
		}

		average = average/force.size();
		mean.add(average);

		for (double stdev : force) {

			sd += Math.pow((stdev-average), 2);			
		}

		sd = Math.sqrt(sd/force.size());
		standard_deviation.add(sd);
	}


	/**This method collects all the data for all pillars across all the frames. 
	 * It is used for the LineChart plotting pillar forces across time.
	 * @param newData - the newData array.
	 * @return databyPillarFrame - an ArrayList of values.
	 */

	public ArrayList <Object> allPillarsAllFrames (Object [][] newData) {

		dataByPillarFrame = new ArrayList <Object> (); // ArrayList of values for output.

		for (int i = 0; i< pillar.size(); i++) {
			
			int pillarArray = pillar.get(i);

			for (int j = 0; j< newData.length; j++) {

				int pillarIndex = Integer.parseInt((String) newData [j][1]);

				if (pillarIndex == pillarArray) {

					dataByPillarFrame.add (newData [j][0] + "," + newData [j][1] + "," +  newData [j][8]);
				}
			}
		}
		
		return dataByPillarFrame;
	}	
}