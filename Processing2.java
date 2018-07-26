import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Processing2 {

	private ArrayList <Double> mean, standard_deviation;
	private ArrayList <Integer> pillar;
	
	//private Processing Process;


	Processing2 () {		
	}

	
	public void allFrames (Object [][] newData) {

		pillar = new ArrayList <Integer> ();
		mean = new ArrayList<Double> ();
		standard_deviation = new ArrayList<Double> ();

		for (int i = 0; i<newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]); 

			if (!pillar.contains(pillarID)) {
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

		for (double m : mean) {System.out.println("mean: " + m);}
		for (double s : standard_deviation) {System.out.println("stndev: " + s);}
		this.dataByframe(newData);
		//this.dataByPillar(newData);
		
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
	public Object [][] dataByframe (Object [][] newData) {
			
		int ID = 1;
		System.out.println("We are in dataByFrame");

//		int columns = 9;
		int columns = 3;
		int rows = newData.length;
		
		Object [][] byFrame = new Object [rows][columns];

		for (int i = 0; i< newData.length; i++) {

			int frameID = Integer.parseInt((String) newData [i][0]);

			if (frameID == ID) {

				byFrame [i][0] = newData [i][0];
				byFrame [i][1] = newData [i][1];
				byFrame [i][2] = newData [i][8];
//				byFrame [i][3] = newData [i][3];
//				byFrame [i][4] = newData [i][4];
//				byFrame [i][5] = newData [i][5];
//				byFrame [i][6] = newData [i][6];
//				byFrame [i][7] = newData [i][7];
//				byFrame [i][8] = newData [i][8];
					
			}	
		}
		for (int j =0; j< byFrame.length; j++) {System.out.println("byFrame: " + Arrays.toString (byFrame [j]));}
		return byFrame;
	}


	public Object [][] dataByPillar (Object [][] newData) { // this will have to be an arraylist because I don't know the size

		int ID = 2559;
		System.out.println("We are in dataByPillar");

//		int columns = 9;
		int columns = 3;
		int rows = newData.length;
		
		Object [][] byPillar = new Object [rows][columns];

		for (int i = 0; i< newData.length; i++) {

			int pillarID = Integer.parseInt((String) newData [i][1]);

			if (pillarID == ID) {

				byPillar [i][0] = newData [i][0];
				byPillar [i][1] = newData [i][1];
				byPillar [i][2] = newData [i][8];
//				byPillar [i][3] = newData [i][3];
//				byPillar [i][4] = newData [i][4];
//				byPillar [i][5] = newData [i][5];
//				byPillar [i][6] = newData [i][6];
//				byPillar [i][7] = newData [i][7];
//				byPillar [i][8] = newData [i][8];
					
			}	
		}
		for (int j =0; j< byPillar.length; j++) {System.out.println("bypillar: " + Arrays.toString (byPillar [j]));}
//		System.out.println("bypillar: " + Arrays.deepToString (byPillar));
		return byPillar;

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
