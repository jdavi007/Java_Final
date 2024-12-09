import java.util.List;

public class FlightPath
{
	// Member data
	private String time; // Time pulled from file
	private String latitude; // Latitude pulled from file
	private String longitude; // Longitude pulled from file
	private String airport; // Airport info pulled from .csv file
	private StringBuilder airportCode = new StringBuilder(); // Empty StringBuilder to get only airport code
	private Boolean firstAppearanceFlag = false;
	private String firstAppearance = "";
	
	
	
	// Constructor
	public FlightPath(List<String> record) 
	{
		time = record.get(0);
		latitude = record.get(1);
		longitude = record.get(2);
		airport = record.getLast(); // May need to use record.get(record.size() - 1) here. Either one or the other would not work for some of us
		
		// Getting airport code from airport string by searching for parentheses
		if(!airport.equals(" Surface and Near-Surface"))
		{
			int flag = 0; // Flag to track if parenthesis is found
			
			for(char c : airport.toCharArray())  // Cycle through characters in the string
			{
				switch(flag) 
				{
					case 0:
						if(c == '(') // Parenthesis found - start appending
						{
							flag = 1;
						}
						break;
					case 1:
						if(c == ')') // End of airport code
						{
							break;
						}
						else
						{
							airportCode.append(c);
						}
				}
				
				// May need to remove 'K' at the beginning of airportCode
			}
		}
		else 
		{
			airportCode.append("N/A"); // No airport code provided
		}
	}// end Constructor
	
	
	
	// Getter for airport code
	public String getAPcode() 
	{
		return airportCode.toString();
	}
	
	
	
	// Getter for timestamp
	public String getTime() 
	{
		return time;
	}
	
	
	
	// Setter for firstAppearanceFlag
	public void setFirstAppearanceFlag(Boolean b) 
	{
		firstAppearanceFlag = b;
	}
	
	
	
	// Setter for firstAppearance time stamp
	public void setFirstAppearance(String s) 
	{
		firstAppearance = s;
	}
	
	
	
	// Function to create a csv string for output to new csv file
	public String toCSVstring() 
	{
		if(firstAppearanceFlag || airportCode.toString().equals("N/A")) 
		{
			return String.format("%s,%s,%s,%s%n", time, latitude, longitude, airportCode.toString());
		}
		else 
		{
			return String.format("%s,%s,%s,%s,%s%n", time, latitude, longitude, airportCode.toString(), firstAppearance);
		}
	}
	
	
	
	// Function to print flight path data - used for testing
	public void print()
	{
		if(firstAppearanceFlag || airportCode.toString().equals("N/A")) 
		{
			System.out.printf("Time: %s\tLatitude: %s\tLongitude: %s\tAirport Code: %s%n",
				time, latitude, longitude, airportCode.toString());
		}
		else 
		{
			System.out.printf("Time: %s\tLatitude: %s\tLongitude: %s\tAirport Code: %s\tFirst Appearance: %s%n",
					time, latitude, longitude, airportCode.toString(), firstAppearance);
		}
	}
}
