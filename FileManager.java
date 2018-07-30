import java.io.*; 
import java.util.*;
import javax.swing.*;


public class FileManager {


	/*Instance variables*/
	private ArrayList <String> fileLine;
	private ReportFrame ReportFrame;
	private Processing Process;
	//private GUIClass GUI;


	/*Constructor*/
	public FileManager() {
		
	}

	/*FileReader for reading in the files - this doesn't yet work.*/
	public void fileReader (String fileName) { 

		FileReader reader = null;	
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;
		ReportFrame = new ReportFrame ();

		try {

			file = fileName;
			System.out.println ("WE'RE IN FM: " + file);
			fileLine = new ArrayList<String>();

			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) !=null) {
				fileLine.add (line);
			}

			for (String s : fileLine) {		
				ReportFrame.reportFormatter(s);	
			}

			Process = new Processing ();
			Process.data(fileLine);
			
			reader.close();	
			bufferedReader.close();
		}

		catch (IOException IOE) {

			JOptionPane.showMessageDialog (null, "FILE NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
			IOE.printStackTrace();	
		}

		catch (InputMismatchException IME) {

			JOptionPane.showMessageDialog (null, "INVALID FILE", "ERROR", JOptionPane.ERROR_MESSAGE);
			IME.printStackTrace();	
		}	
	}


	/*FileWriter writes the getByFrame, getByPillar and MultiPillar data to file*/
	public void fileWriter (String identifier, String output) {

		String rootFileName = GUIClass.getRootFileName();	
		System.out.println("RFN: " + rootFileName);
		String fileName = null;
		FileWriter writer = null;

		if (identifier.equals("dataByFrame")) {

			fileName = rootFileName + "_dataByFrame.csv";
		}

		else if (identifier.equals("dataByPillar")) {

			fileName = rootFileName + "_dataByPillar.csv";
		}

		else if (identifier.equals("multipillar")) {

			fileName = rootFileName + "_multipillar.csv";
		}

		try {

			try {

				writer = new FileWriter (fileName);
				writer.write(output);
			}

			finally {

				writer.close();	
			}
		}

		catch (IOException IOE) {

			IOE.printStackTrace();	
		}
	}
}