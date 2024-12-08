import java.util.List;

public class FlightPath
{
	// Member data
	private String time; // Time pulled from file
	private String latitude; // Latitude pulled from file
	private String longitude; // Longitude pulled from file
	private String airport; // Airport info pulled from .csv file
	private StringBuilder airportCode = new StringBuilder(); // Empty StringBuilder to get only airport code
	private String location = ""; // Town & State/Province needed from Google Maps query
	private String airportName = ""; // needed from Google Maps query
	private String airportLocation = ""; // needed from Google Maps query
	
	// Constructor
	public FlightPath(List<String> record) 
	{
		time = record.get(0);
		latitude = record.get(1);
		longitude = record.get(2);
		airport = record.get(record.size() - 1);
		
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
		
		fetchLocation(); // Fetch location from GMaps
		fetchAirport(); // Fetch airport name & location from GMaps
	}// end Constructor
	
	
	// function to send latitude and longitude to Google API to retrieve flight location
	private void fetchLocation() {
		try {
			// put the API call to Google Maps once found
			location = "Town, State"; // Example location
		} catch (Exception e) {
			System.out.println("Unable to retrieve location from Google Maps API.");
			location = "unknown";
		}
	}
	// function to send airport code to Google API to get airport name & location
	private void fetchAirport() {
		try {
			if (!airportCode.toString().equals("N/A")) // If airport code is available
			{
				// put the API call to Google Maps once found
				airportName = "Airport Name"; // Example airport name
				airportLocation = "city, country"; // Example airport location
			}
			else {
			airportName = "unknown";
			airportLocation = "unknown";
			}
		} catch (Exception e) {
			System.out.println("Unable to retrieve airport information from Google Maps API.");
			airportName = "unknown";
			airportLocation = "unknown";
			}
	}
	public String toCSVstring() { // Return a string in CSV format(separated by commas)
		return String.format("%s,%s,%s,%s,%s,%s,%s", time, latitude, longitude, location, airportCode.toString(), airportName, airportLocation);
	}
	public void print() // TODO: Create string to send to file instead of printing
	{
		System.out.printf("Time: %s\tLatitude: %s\tLongitude: %s\tCurrent Location: %s\tAirport Code: %s\tAirport Name: %s\tAirport Locale: %s%n",
				time, latitude, longitude, location, airportCode.toString(), airportName, airportLocation);
	}
}
