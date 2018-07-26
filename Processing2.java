import java.util.ArrayList;
import java.util.Collections;

public class Processing2 {

	private ArrayList <Double> mean, standard_deviation;
	private ArrayList <Integer> pillar;


	Processing2 () {		
	}

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
//		this.dataByframe(newData, 0);
//		this.dataByPillar(newData, 0);	
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
		System.out.println("Average: " + average);

		for (double stdev : pico) {

			sd += Math.pow((stdev-average), 2);			
		}
		
		sd = Math.sqrt(sd/pico.size());
		standard_deviation.add(sd);
		System.out.println("SD: " + sd);
	}
	
	
	/*This method will generate a list of Frame ID, PillarID and Forces relating to a particular Frame */
	public ArrayList <Object> dataByFrame (Object [][] newData, int ID) {
			
		//int ID = 1;
		System.out.println("We are in dataByFrame");
		
		ArrayList <Object> dataByFrame = new ArrayList <Object>();

		for (int i = 0; i< newData.length; i++) {

			int frameID = Integer.parseInt((String) newData [i][0]);

			if (frameID == ID) {

				Object forces = newData [i][0] + "," + newData [i][1] + "," + newData [i][8];
				dataByFrame.add(forces);
					
			}	
		}
		for (int j =0; j< dataByFrame.size(); j++) {System.out.println("byFrame: " + dataByFrame.get(j));}
		return dataByFrame;
	}


	public ArrayList <Object> dataByPillar (Object [][] newData, int ID) { 
		
		//int ID = 2563;
		System.out.println("We are in dataByPillar");
		
		ArrayList <Object> dataByPillar = new ArrayList <Object> ();

		for (int i = 0; i< newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]);

			if (pillarID == ID) {

				Object forces = newData [i][0] + "," + newData [i][1] + "," + newData [i][8];
				dataByPillar.add(forces);
					
			}	
		}
		for (int j =0; j< dataByPillar.size(); j++) {System.out.println("bypillar: " + dataByPillar.get(j));}
		return dataByPillar;
	}

	
	/*This method creates an output file for the average and standard deviation of pillars from AllFrames*/
	public String outputFile2 () {

		String header = ("pillar" + "," + "average" + "," + "stndev" + ",");
		String body = "";

		for (int i =0; i<pillar.size();i++) {

			body += (pillar.get(i) + "," + mean.get(i) + "," + standard_deviation.get(i) + "\n");
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		System.out.println(outputFile);
		return outputFile;
	}
}
