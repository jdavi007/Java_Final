/*
 * CSC 2220 - Programming In Java
 * Final Exam
 * Jacob Davis, Dylan Dray, Kaylee Strope
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
		}
		catch (IOException e) 
		{
			System.out.println("Error: There was an issue retriving information from the file.\n");
		}
		
		
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
			if(!path.getAPcode().equals("N/A")) 
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
			if(!path.getAPcode().equals("N/A")) 
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
		
		
		
		// TODO: Create new csv filename by removing '.csv' from inputFile variable and adding 'flightPath.csv'
		// TODO: Output to file by cycling through FlightPath objects and using toCSVstring() method
	}

}
