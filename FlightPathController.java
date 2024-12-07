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
	// 1 - Preparation - done
	//Ask the user for name of the file. Assume the file will be in the same directory as your program
	
	// 2 - Read - done
	// Read file one line at a time
	// Line format: Time, Latitude, Longitude, Location, Knots, MPH, Elevation, Rate of Elevation, Reporting Facility
	// Read & store (in a class' object) the Time, Latitude, Longitude, Reporting Facility (only the airport code, a 4-character sequence)
	// Store in a collection of your choice (from chapter 16 or 20)
	// Your class should have those four attributes stored and have 2 additional attributes for the things you will look up
	// One all data is read, close the file
	
	// 3 - Processing - TODO
	// Use latitude and longitude to determine location
	// Record the location (town name, state/province name)
	// When querying the coordinates, you can either use a comma that separates latitude and longitude, or just have a space between them
	// Would recommend for the airport code, to search google maps (API?) again with the term of "airport"
	// For instance, if you see KTRI on a line, you should ask Google Maps/API to look up the location of the 4-letter code
	// Save time and go back through your collection, find other entries that match the airport code, and add that information into those objects in the collection
	
	// 4 - Output - basically done (will be very quick)
	// Create a filename that matched the one put in for perusal but amend the end of the name of the file to include the term "flightPath"
	// Print out an object's attributes one at a time. Make sure to separate your objects one per line
	// Each line should look like: Time, Latitude, Longitude, Current Location, Airport Code, Name & Locale of the airport (airport name/location)
	// After you're done, close the new .csv file
	// Utilize the same CSV format when saving the new file
	
	
	public static void main(String[] args) 
	{
		String inputFile; // For user input
		ArrayList<List<String>> flightRecords = new ArrayList<>(); // ArrayList of String lists to hold flight info read from file
		
		// Getting filename
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the name of the flight path file: ");
		inputFile = s.nextLine();
		
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
		
		
		// TODO: With FileWriter open:
		// (Need to figure out Google stuff before worrying about file output)
		// Cycle through records, create objects, write info to file
		for(List<String> record : flightRecords) 
		{
			FlightPath flightPath = new FlightPath(record); // Create filePath object
			//flightPath.print(); // Print for testing
			
			// TODO: Write info to file
		}
		// TODO: Close FileWriter
	}

}