import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;


public class FileManager {


	private ArrayList <String> fileLine;
	private ReportFrame ReportFrame;
	private Processing Process;


	public FileManager() {
	}


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
	public void fileWriter (String fileName, String output) {

		FileWriter writer = null;
		String file = null;
		try {

			try {
				file = fileName;
				writer = new FileWriter (file);

				if (fileName.equals("jemma.csv")) {

					writer.write(output);
				}
				
				else if (fileName.equals("lainey.csv")) {

					writer.write(output);
				}

				else if (fileName.equals("tanya.csv")) {

					writer.write(output);
				}
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