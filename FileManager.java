import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;


public class FileManager {
	
	//private GUIClass GUI;
	private Processing Process;
	private ReportFrame ReportFrame;
	
	
	

	private ArrayList <String> fileLine;
	
	
	public FileManager() {
		
	}
	
	
	public void fileReader (String fileName) { // This should be moved to an IO class. 

		FileReader reader = null;	
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;
		ReportFrame = new ReportFrame ();
		//Process = new Processing ();

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
			
			//Process = new Processing (fileLine);
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

}
