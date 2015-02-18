import java.util.*;
import java.lang.*;
import java.io.*;

public class Airport {
	// static variables (independent of any one Airport object)
	public static ArrayList<Airport> airports = new ArrayList<Airport>();

	// attributes for each Airport
	private String name;
	private double lat;
	private double lgt;
	private int delay; //minutes
	private String city;

	// blank Airport constructor
	public Airport() {
		name = null;
		lat = 0;
		lgt = 0;
		delay = 0;
		city = null;
	}

	// Airport constructor with all parameters specified
	public Airport(String name, double lat, double lgt, int delay, String city){
		this.name = name;
		this.lat = lat;
		this.lgt = lgt;
		this.delay = delay; 
		this.city = city;
	}

	//
	// Airport-specific get methods

	public String getName(){
		return name;
	}

	public double getLat(){
		return lat;
	}

	public double getLgt(){
		return lgt;
	}

	public int getDelay() {
		return delay;
	}

	public String getCity() {
		return city;
	}

	//
	// Airport-specific set methods

	public void setName(String name) {
		this.name = name;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLgt(double lgt) {
		this.lgt = lgt;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public void setCity(String city) {
		this.city = city;
	}

	//
	// static get/set methods for ArrayList<Airport> airports

	public static ArrayList<Airport> getAirports() {
		return airports;
	}

	public static void setAirports() throws FileNotFoundException {      // "throws" required in case the file is not found
		Scanner s = new Scanner(new File("Airports.csv"));               // s loads the .csv file
		Scanner r = null;                                                // r is as yet undefined
		while(s.hasNextLine()) {                                         // when s reaches the end of the file, the loop stops
			r = new Scanner(s.nextLine());                               // s reads a line, which is loaded into r. note that s advances to next line
			r.useDelimiter(",");                                         // r will read the line in tokens separated by the ',' char
			Airport temp = new Airport();                                // a temporary Airport object is created (values irrelevant, we'll set them later)
			while(r.hasNext()) {                                         // when r reaches the end of the line, the loop stops
				temp.setName(r.next());                                  // r reads the next token and assigns it to name
				temp.setLat(r.nextDouble());                             // "" assigns to lat (note nextDouble method needed to avoid casting String to double)
				temp.setLgt(r.nextDouble());                             // "" assigns to lgt ("")
				temp.setDelay(r.nextInt());                              // "" assigns to delay (nextInt method avoids casting String to int)
				temp.setCity(r.next());                                  // "" temp's city
			}                                                            // now temp is one of the airports from the .csv file
			airports.add(temp);                                          // temp is added to the list of airports
		}                                                                // the loop repeats until all airports are added (temp is recycled for each iteration)
		s.close();                                                       // after s has no more lines to read, s closes.
	}

	//
	// optimization methods return airports with min/max/targeted attributes

	public static Airport minDelay(ArrayList<Airport> l) {
		Airport minDelay = new Airport();
		int tempdelay = 1000;
		for (Airport temparpt : l) {
			if (temparpt.getDelay()<tempdelay) {
				minDelay = temparpt;
			}
		}
		return minDelay;
	}

	//
	// toString methods allow user to view data in console

	public static ArrayList<String> apListSimpleToString(ArrayList<Airport> l) {
		ArrayList<String> strs = new ArrayList<String>();
		strs.add("NM  | CITY");
		strs.add("----------");
		for (Airport a : l) {
			String nm = a.getName();
			String city = a.getCity();
			strs.add(nm + " | " + city);
			strs.add("----------");
		}
		strs.add("----------");
		return strs;
	}

	public static ArrayList<String> apAllSimpleToString() { 
		ArrayList<String> strs = new ArrayList<String>();
		strs.add("NM  | CITY");
		strs.add("----------");
		for (Airport a : airports) {
			String nm = a.getName();
			String city = a.getCity();
			strs.add(nm + " | " + city);
			strs.add("----------");
		}
		strs.add("----------");
		return strs;
	}

	public static ArrayList<String> apOneSimpletoString(Airport a) {
		ArrayList<String> strs = new ArrayList<String>();
		strs.add("NM  | CITY");
		strs.add("----------");
		String nm = a.getName();
		String city = a.getCity();
		strs.add(nm + " | " + city);
		strs.add("----------");
		return strs;
	}

	public static void printStrings(ArrayList<String> s) {
		for (String str : s) {
			System.out.println(str);
		}
	}

	//
	// misc methods

	// codeToAirport method receives a string and returns the airport with that name; returns null if no airport by that name
	public static Airport codeToAirport(String a) {
		Airport tempap = null;
		for (Airport k : airports) {
			String testnm = k.getName();
			if (a.equalsIgnoreCase(testnm)) {
				tempap = k;
				break;
			}
		}
		return tempap;
	}

	// used to quickly verify all airports were imported from the csv
	public static int getAirportsSize() { 
		return airports.size();
	}
}

