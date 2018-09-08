import java.io.*; 
import java.util.*;
import javax.swing.*;


/**Class to operate file I/O*/

public class FileManager {


	/*Instance variables*/
	private DataArray DataArray;
	private DataProcessing DataProcessing;
	private DataOutput DataOutput;
	
	private ArrayList <String> fileLine;
	private String fileName;


	/*Default Constructor*/
	public FileManager () {
	}
		
	
	/*Constructor*/ 
	public FileManager(DataArray DataArray, DataProcessing DataProcessing, DataOutput DataOutput) {
		this.DataArray = DataArray;
		this.DataProcessing = DataProcessing;
		this.DataOutput = DataOutput;
	}

	
	/*Getters*/
	public String getFileName() {return fileName;}
	public void setFileName(String fileName) {this.fileName = fileName;}
	
	
	/**FileChooser - save location.*/
	
	public void fileSelection () { // This causes issues

		JFileChooser JFC = new JFileChooser ();
		JFC.setMultiSelectionEnabled(false);
		File selectedFile = null;
		int openVal = JFC.showDialog(null, "Select");

		if (openVal == JFileChooser.APPROVE_OPTION) {

			selectedFile =	JFC.getSelectedFile();
			fileName = selectedFile.toString();

			if (fileName.contains(".csv")) {

				fileReader(fileName);
			}

			else {

				System.out.println("Incorrect Filetype");
				JOptionPane.showMessageDialog (null, "INVALID FILETYPE \n .csv files only", "ERROR", JOptionPane.ERROR_MESSAGE);
				fileName = null;
			}
		}

		else if (openVal == JFileChooser.CANCEL_OPTION) {

			return;
		}
	}

	
	/**FileReader for reading in the csv files
	 *@param fileName - the name of the file.
	 */
	
	public void fileReader (String fileName) {

		FileReader reader = null;	
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;

		try {

			file = fileName;
			
			fileLine = new ArrayList<String>();

			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) !=null) {
				
				fileLine.add (line);
			}

			DataArray = new DataArray ();
			DataArray.data(fileLine);

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


	/**fileWriter for writing data to file.
	 * @param identifier - determines the writer.
	 * @param fileName - the name of the file.
	 */
	
	public void fileWriter (String identifier, String fileName) { // This works

		FileWriter writer = null;
		DataOutput = new DataOutput (DataProcessing, DataArray);

		try {

			try {

				writer = new FileWriter (fileName);
				
				if (identifier.equals("Force Data")) {
					writer.write(DataOutput.outputFile());
				}
				
				else if (identifier.equals("Statistical Data")) {
					writer.write(DataOutput.outputStatistics());
				}
				
				else if (identifier.equals("Frame Data")) {
					writer.write(DataOutput.outputByFrame());
				}

				else if (identifier.equals("Pillar Data")) {
					
					writer.write(DataOutput.outputByPillar());
				}

				else if (identifier.equals("Multipillar Data")) {
					writer.write(DataOutput.outputMultipillar());
				}
				
				else if (identifier.equals("All Data")) {
					writer.write(DataOutput.outputAllData());
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