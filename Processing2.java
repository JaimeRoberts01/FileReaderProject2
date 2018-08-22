import java.util.*;


public class Processing2 {
	

	/**Instance variables*/

	private ArrayList <Double> mean, standard_deviation; 
	private ArrayList <Object> dataByPillar, outputDataByPillar;
	private ArrayList <Object> dataByFrame, outputDataByFrame;
	private ArrayList <Object> pillarFrame, allFrames;
	private ArrayList <Object> multipillar, outputMultipillar;
	private Object [] values; 
	private ArrayList <String> dataByPillarFrame;
	private ArrayList <Integer> pillar, frame;	

	private MultipillarInput MultipillarInput;
	private LineChart LineChart;
	private ScatterPlot ScatterPlot;
	private OutputData OutputData;
	

	/**Default constructor*/

	Processing2 () {
	}
	

	/**Constructor*/

	Processing2 (MultipillarInput MultipillarInput) {	
		this.MultipillarInput = MultipillarInput;
	}


	/**Getters*/

	public ArrayList<Double> getMean() {return mean;}
	public ArrayList<Double> getStandard_deviation() {return standard_deviation;}
	public ArrayList<Integer> getPillar() {return pillar;}
	public ArrayList<String> getDataByPillarFrame() {return dataByPillarFrame;}
	public ArrayList<Object> getOutputDataByPillar() {return outputDataByPillar;}
	public ArrayList<Object> getOutputDataByFrame() {return outputDataByFrame;}
	public ArrayList<Object> getMultipillar() {return multipillar;}
	public ArrayList<Object> getOutputMultipillar() {return outputMultipillar;}
	public Object[] getValues() {return values;}
	public ArrayList<Object> getAllFrames() {return allFrames;}
	public ArrayList<Integer> getFrame() {return frame;}
	public ArrayList<Object> getDataByPillar() {return dataByPillar;}
	public ArrayList<Object> getDataByFrame() {return dataByFrame;}
	public ArrayList<Object> getPillarFrame() {return pillarFrame;}



	/**This method creates a list of the pillars in the newData array. 
	 * It is used for initialising the first pillar value and sets up 
	 * allFrames so that it can collect data for specific pillars.
	 */

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


	/** This method creates a list of the frames in the newData array.*/

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


	/**This method loops through the pillar ArrayList identifying pillars of the same ID from all the frames in the dataset. 
	 * Pillars of the same ID are added to a temporary ArrayList, which is used to calculate averages and standard deviation.
	 */

	public void allFrames (Object [][] newData) {
		
		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		allFrames = new ArrayList <Object> ();
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
				
		for (double m : mean) {System.out.println("mean P2: " + m);}
		for (double s : standard_deviation) {System.out.println("stndev P2: " + s);}
		
//		OutputData = new OutputData (this, null);
//		OutputData.stringStatistics();
	}	


	/**This method generates a list of FrameID, PillarID and Forces relating all pillars across a single Frame*/

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

		ScatterPlot = new ScatterPlot (this);
		ScatterPlot.scatterPlotData_byFrame (ID);

		OutputData = new OutputData (this, null);
		OutputData.stringByFrame (ID);
		return dataByFrame;
	}


	/**This method generates a list of FrameID, PillarID and Forces relating to a single pillar across all the frames*/

	public ArrayList <Object> dataByPillar (Object [][] newData, int ID) { 

		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		dataByPillar = new ArrayList <Object> ();
		pillarFrame = new ArrayList <Object> ();
		outputDataByPillar = new ArrayList <Object> ();

		ArrayList <Double> pico = new ArrayList <Double> ();

		for (int i = 0; i< newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]);

			if (ID == pillarID) {

				pico.add((double) newData [i][8]);
				Object forces = newData [i][0] + "," + newData [i][1] + "," + newData [i][8];
				Object frames = newData [i][0];
				outputDataByPillar.add(forces);
				pillarFrame.add(frames);
				System.out.println ("pillarFrame: " + pillarFrame);
				dataByPillar.add((double) newData [i][8]);
			}	
		}

		statistics (pico);

		if (pico.isEmpty()) {

			return null;
		}

		else {

			OutputData = new OutputData (this, null);
			OutputData.stringByPillar (ID);
			pico.clear ();
		}
		
		LineChart = new LineChart (this);
		LineChart.lineChartData_byPillar (ID);
	
		return dataByPillar;
	}


	/**This method is for the multipillar functionality and is similar to the allFrames
	 * method but does not use the pillar ArrayList for ordering the pillars.
	 */

	public void multiPillar (Object [][] newData) {


		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		ArrayList <Double> pico = new ArrayList <Double> ();

		outputMultipillar = new ArrayList <Object> ();

		values = new String [MultipillarInput.getID().length];

		for (int i =0; i< MultipillarInput.getID().length; i++) {
			values = MultipillarInput.getID();
		}

		for (int i = 0; i < values.length; i++) {

			int value = Integer.parseInt((String)values [i]);

			for (int j =0; j< newData.length; j++) {

				if (value == Integer.parseInt ((String) newData [j][1])) {

					pico.add((Double) newData [j][8]);
					Object output = newData [j][0] + "," + newData [j][1] + "," + newData [j][8];
					outputMultipillar.add(output); 
				}
			}	
			
			for (double d: pico) {System.out.println("pico2 : " + d);}
			
			statistics(pico);
			pico.clear();
		}

		OutputData = new OutputData (this, null);
		OutputData.StringMultipillar ();
		
		LineChart = new LineChart (this);
		LineChart.lineChartData_Multipillar(values);
		
		for (double m : mean) {System.out.println("mean: " + m);}
		for (double s : standard_deviation) {System.out.println("stndev: " + s);}
	}
	

	/**This method calculates the average and standard deviation of the picoNewton values in the data.*/

	public void statistics (ArrayList<Double> pico) {

//		if (pico.isEmpty()) { // If this goes in then I get out-of-bounds errors for avg and sd in the strings
//
//			System.out.println("Invalid pillar index - pico");
//			return;
//		}

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


	/**This method collects all the data for all pillars across all the frames. 
	 * It is used for the LineChart plotting pillar forces across time.
	 */

	public ArrayList<String> allPillarsAllFrames (Object [][] newData) {

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
		return dataByPillarFrame;
	}
}