# MSc_Project_0806849

A program for processing PillarTracker data. This program operates on PillarTracker csv files to calculate traction forces and sort the data into datasets by Frame ID or Pillar ID. 

Instructions for installing Fiji can be found at https://fiji.sc/ and how to install and use PillarTracker at https://github.com/scottreen/PillarTracker. Instructions on how to use the processing software in this repo
can be found below:

1.	Loading a file: Use the Browse button to search the file directory for the correct file. The software only accepts csv files and the file path will appear in the text area beside the button; if it does not, the wrong file has been used. The Calculate Force button will become active at this point. 

2.	Input values: Use the textboxes to input values for converting deflection in pixels to deflection in pico-newtons (Pixel to nm), and for calculating forces using the Young’s modulus of the pillar material (Substrate MPa), pillar diameter (Pillar Diameter (µm)) and pillar length (Pillar Length (µm)). 

3.	Calculate force: Use the Calculate Force button to calculate pico-newton forces. A table will appear showing forces for all pillars across the timestack. The table can be saved to file using the Save button. Most of the other buttons should become active at this point. 

4.	View Statistics: To view the average force and standard deviation of all the data, use the View Statistics button. The data can be saved to file using the Save button. 

5.	Viewing the dataset: The data can be viewed as a line chart, scatter plot or bar chart. Select one from the dropdown list and use the Plot Data button to view the graph. 

6.	Filtering values: The scatter plot and bar chart both have the ability to filter out values at the lower end of the scale. Simply type in a value e.g. 120000 to remove data entries below this value. Do not use a decimal point or comma to separate the digits e.g. 120000 not 120,000 or 120.000.

7.	Saving graphs: All graphs can be saved to file using the Save button. They can also be copy/pasted or simply closed using the Close button.

8.	Get Data by Frame: To view pillar forces at a particular timepoint, select a frame number using the slider. The value will change in the textbox next to the slider. Use the button Get Data to view a table of the values. The table has a Plot button, which will display a scatter plot of this data.

9.	Get Data by Pillar: To view pillar forces across time, enter a pillar ID into the textbox. Use the Get Data button to view a table of the values. The table has a Plot button, which will display a line chart of the data.

10.	Compare pillars: To view the data for more than one pillar on the same graph, use the Multi-Pillar button. This will open a new input box to enter the ID of the pillars; use a space to separate the values e.g. 2559 2623 2685. Use the Enter button to enter values or Cancel to cancel.

11.	Multi-Pillar: A table will appear showing the average forces and standard deviation for the pillars entered into the input box. The Plot button will display a line chart of the pillar forces over time. The Bar Chart button on the line chart panel will display a bar chart of the average forces.

12.	Get All Data: This button will display a table of pillar forces organised by pillar rather than by frame. This is the data used to generate the line chart of the overall data and serves as a means of accessing this data. 

Test data and example output data can be found in the All_Input_and_Results folder.
