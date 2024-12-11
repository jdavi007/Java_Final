/*
 * CSC 2220 - Programming In Java
 * Final Exam
 * Jacob Davis, Dylan Dray, Kaylee Strope
 * 
 * Note: 
 * If using Eclipse, may need to place .csv files into the res folder.
 * 
 * If not on Eclipse, .csv files need to be stored in the same folder as the project
 * or may need to input the entire file path.
 * 
 * We included the sample .csv file as well as the resulting output file in the res folder.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FlightPathController
{
	public static void main(String[] args) 
	{
		String inputFile; // For user input
		ArrayList<List<String>> flightRecords = new ArrayList<>(); // ArrayList of String lists to hold flight info read from file
		ArrayList<FlightPath> flightPaths = new ArrayList<>(); // ArrayList to store flight path objects
		ArrayList<String> firstAPcodes = new ArrayList<>(); // ArrayList to store unique airport codes
		ArrayList<String> firstAPtimes = new ArrayList<>(); // ArrayList to store time of first appearance of airport codes from firstAPcodes
		Boolean fileLoaded = false; // Boolean used to track if flight path data was successfully collected from the input file
		
		
		
		// Getting filename
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the name of the flight path file: ");
		inputFile = s.nextLine();
		s.close();
		
		
		
		// Reading from file
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(inputFile)); // Creating BufferedReader using the file name provided by the user
			String line;
			
			while((line = br.readLine()) != null) // Checking for EOF
			{
				String[] values = line.split(",");// Splitting CSV line by commas and creating a String list
				
				if(values.length > 4) // Accounts for file header
				{
					flightRecords.add(Arrays.asList(values)); // Adding the list to the ArrayList
				}	
			}
			
			br.close(); // Close BufferedReader
			fileLoaded = true;
		}
		catch (IOException e) 
		{
			System.out.println("Error: There was an issue retriving information from the file."); // Output error message
		}
		
		
		
		if(fileLoaded) // Check for invalid file
		{
			// Creating flight path objects
			for(List<String> record : flightRecords) 
			{
				FlightPath flightPath = new FlightPath(record); // Create filePath object
				//flightPath.print(); // Test print
				flightPaths.add(flightPath); // Store in collection
			}
		
		
			
			// Finding first appearances of Airport Codes & their accompanying time stamps
			for(FlightPath path : flightPaths) // Cycle through flight path objects
			{	
				if(!path.getAPcode().equals("N/A")) // Check for flight path with no airport code, i.e. Surface and Near-Surface
				{
					if(!firstAPcodes.contains(path.getAPcode()))  // If first appearance of code...
					{
						firstAPcodes.add(path.getAPcode()); // Add to list of first appearance codes
						firstAPtimes.add(path.getTime()); // Add time to list of first appearance times
					}
				}
			}
		
		
		
			// Adding first appearance time stamps to flight paths with repeated airport codes
			for(FlightPath path : flightPaths) // Cycle through flight path objects
			{
				if(!path.getAPcode().equals("N/A")) // Check for flight path with no airport code, i.e. Surface and Near-Surface
				{
					if(firstAPtimes.contains(path.getTime())) // If first appearance...
					{
						path.setFirstAppearanceFlag(true); // Set first appearance flag
					}
					else // If not first appearance...
					{
						for(int i = 0; i < firstAPcodes.size(); i++) // Cycle through firstAPcodes
						{
							if(path.getAPcode().equals(firstAPcodes.get(i))) // Find index of correct code
							{
								path.setFirstAppearance(firstAPtimes.get(i)); // Set time of first appearance based on index of the corresponding code
							}
						}
					}
				}
			}
		
		
		
			// Test print
			/*
			for(FlightPath path : flightPaths) 
			{
				path.print();
			}
			 */
		
		
		
			// Creating new filename
			String outFile = inputFile.substring(0, inputFile.length()-4) + "_flightPath.csv";
		
		
		
			// Writing to output file
			try
			{
				FileWriter writer = new FileWriter(outFile); // Create file writer
			
				for(FlightPath path : flightPaths) // Cycle through File Path objects
				{
					writer.write(path.toCSVstring()); // Write path info to file
				}
				
				writer.close(); // Close FileWriter
				
				System.out.printf("Flight path data for %s was written to %s%n", inputFile, outFile); // Output confirmation message
			} 
			catch (IOException e) 
			{
				System.out.println("Error: There was an issue generating flight path information."); // Output error message
			}
		}
	}
}
