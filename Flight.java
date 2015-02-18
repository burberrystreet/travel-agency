import java.util.*;
import java.lang.*;
import java.io.*;

public class Flight {
	// static variables (independent of any one Flight object)
	public static ArrayList<Flight> flights = new ArrayList<Flight>();

	// attributes for each Flight
	private int fltNum;
	private Airport departLoc;
	private Airport arriveLoc;
	private int departTime;      // min after midnight
	private int arriveTime;      // min after midnight
	private double price;
	private int seats;
	private String airline;
	private int duration;        // in minutes, calculated

	// blank Flight constructor
	public Flight() {
		fltNum = 0;
		departLoc = null;
		arriveLoc = null;
		departTime = 0;
		arriveTime = 0;
		price = 0;
		seats = 0;
		airline = null;
		duration = 0;
	}

	// Flight constructor with most parameters specified
	public Flight(int f, Airport dL, Airport aL, int dT, int aT, double p, int s, String a){
		fltNum = f;
		departLoc = dL;
		arriveLoc = aL;
		departTime = dT;
		arriveTime = aT;
		price = p;
		seats = s;
		airline = a;
		Flight.calcDur(this);
	}

	//
	// Flight-specific get methods

	public int getFltNum() {
		return fltNum;
	}

	public Airport getDepLoc() {
		return departLoc;
	}

	public Airport getArrLoc() {
		return arriveLoc;
	}

	public int getDT() {
		return departTime;
	}

	public int getAT() {
		return arriveTime;
	}

	public double getPrice() {
		return price;
	}

	public int getSeats() {
		return seats;
	}

	public String getAirline() {
		return airline;
	}

	public int getDuration() {
		return duration;
	}

	//
	// Flight-specific set methods

	public void setFltNum(int a) {
		fltNum = a;
	}

	public void setDepLoc(Airport a) {
		departLoc = a;
	}

	public void setArrLoc(Airport a) {
		arriveLoc = a;
	}

	public void setDT(int a) {
		departTime = a;
	}

	public void setAT(int a) {
		arriveTime = a;
	}

	public void setPrice(double a) {
		price = a;
	}

	public void setSeats(int a) {
		seats = a;
	}

	public void setAirline(String a) {
		airline = a;
	}

	public void setDuration(int a) {
		duration = a;
	}

	// special case: duration is not given in the csv file and must be calculated
	// calcDur(Flight q) method sets q's duration method given existing eta and etd
	// this method was made static so that it could be used in a test driver class.
	public static void calcDur(Flight q) {  
		int a = q.getAT();
		int d = q.getDT();
		if (a>d) {
			q.setDuration(a-d);
		} else if (a<d) {
			q.setDuration((1440-d)+a);
		}
	}

	//
	// static get/set methods for ArrayList<Flight> flights

	public static ArrayList<Flight> getFlights() {
		return flights;
	}

	public static void setFlights() throws FileNotFoundException {  // "throws" required in case the file is not found
		Scanner s = new Scanner(new File("Flights.csv"));           // s loads the .csv file
		Scanner r = null;                                           
		while(s.hasNextLine()) {                                    // when s reaches the end of the file, the loop stops
			r = new Scanner(s.nextLine());                          // s reads a line, which is loaded into r
			r.useDelimiter(",");                                    // r will read the line in tokens separated by the ',' char
			int tempfltnm = 0;                                      // temporary variables are initialized
			String tempdepLoc = null;
			String temparrLoc = null;;
			int tempdepTime = 0;
			int temparrTime = 0;
			double tempPrice = 0;
			int tempSeats = 0;
			String tempAirline = null;

			while (r.hasNext()) {                                   // when r reaches the end of the line, the loop stops
				tempfltnm = r.nextInt();                            // r reads each token and assigns them to the temp variables
				tempdepLoc = r.next();
				temparrLoc = r.next();
				tempdepTime = r.nextInt();
				temparrTime = r.nextInt();
				tempPrice = r.nextDouble();
				tempSeats = r.nextInt();
				tempAirline = r.next();
			}                                                   // Flight constructor (OUTSIDE while loop, that took a while to figure out) 
			Flight addition = new Flight(tempfltnm, Airport.codeToAirport(tempdepLoc), Airport.codeToAirport(temparrLoc), tempdepTime, temparrTime, tempPrice, tempSeats, tempAirline);
			flights.add(addition);                                  // add the Flight to the ArrayList<>
		}
		s.close();                                                  // after s has no more lines to read, s closes
	}

	//
	// optimization methods return airports with min/max/targeted attributes

	public static Flight minDur(ArrayList<Flight> lf) { 
		Flight minDur = new Flight();
		int min = 200;
		for (Flight a : lf) {
			int temp = a.getDuration();
			if (temp < min) {
				min = a.getDuration();
				minDur = a;
			}
		}
		return minDur;
	}

	//
	// print methods allow user to view data in console

	public static ArrayList<String> flListSimpleToString(ArrayList<Flight> alf) {
		ArrayList<String> strs = new ArrayList<String>();
		strs.add(" FLT# | DL  | AL  | DUR  | STS | AIRLINE");
		strs.add("----------------------------------------");
		for (Flight a : alf) {
			String num = Integer.toString(a.getFltNum());
			while (num.length()<4) {
				num = " " + num;
			}
			Airport dep = a.getDepLoc();
			String depname = dep.getName();
			Airport arr = a.getArrLoc();
			String arrname = arr.getName();
			String dur = Integer.toString(a.getDuration());
			while (dur.length()<4) {
				dur = " " + dur;
			}
			int sts = a.getSeats();
			String airline = a.getAirline();
			strs.add(num + "  | " + depname + " | " + arrname + " | " + dur + " | " + sts + " | " + airline);
			strs.add("----------------------------------------");

		}
		strs.add("----------------------------------------");
		return strs;
	}

	public static ArrayList<String> flAllSimpleToString() {
		ArrayList<String> strs = new ArrayList<String>();
		strs.add(" FLT# | DL  | AL  | DUR  | STS | AIRLINE");
		strs.add("----------------------------------------");
		for (Flight a : flights) {
			String num = Integer.toString(a.getFltNum());
			while (num.length()<4) {
				num = " " + num;
			}
			Airport dep = a.getDepLoc();
			String depname = dep.getName();
			Airport arr = a.getArrLoc();
			String arrname = arr.getName();
			String dur = Integer.toString(a.getDuration());
			while (dur.length()<4) {
				dur = " " + dur;
			}
			int sts = a.getSeats();
			String airline = a.getAirline();
			strs.add(num + "  | " + depname + " | " + arrname + " | " + dur + " | " + sts + " | " + airline);
			strs.add("----------------------------------------");
		}
		strs.add("----------------------------------------");
		return strs;
	}

	public static ArrayList<String> flOneSimpleToString(Flight a) {
		ArrayList<String> strs = new ArrayList<String>();
		strs.add(" FLT# | DL  | AL  | DUR  | STS | AIRLINE");
		strs.add("----------------------------------------");
		String num = Integer.toString(a.getFltNum());
		while (num.length()<4) {
			num = " " + num;
		}
		Airport dep = a.getDepLoc();
		String depname = dep.getName();
		Airport arr = a.getArrLoc();
		String arrname = arr.getName();
		String dur = Integer.toString(a.getDuration());
		while (dur.length()<4) {
			dur = " " + dur;
		}
		int sts = a.getSeats();
		String airline = a.getAirline();
		strs.add(num + "  | " + depname + " | " + arrname + " | " + dur + " | " + sts + " | " + airline);
		strs.add("----------------------------------------");
		strs.add("----------------------------------------");
		return strs;
	}

	//
	// ArrayList<Flight> constructors use the main flight list to build subsets of flights

	// Blank ArrayList<Flight> constructor
	public static ArrayList<Flight> newFlightList() {
		ArrayList<Flight> newList = new ArrayList<>();
		return newList;
	}

	// ArrayList<Flight> constructors with an ArrayList<Flight> to search through and one parameter
	public static ArrayList<Flight> depFrom(ArrayList<Flight> alf, Airport a) {
		ArrayList<Flight> depFrom = Flight.newFlightList();
		for (Flight fl : alf) {
			if (fl.getDepLoc() == a) {
				depFrom.add(fl);
			}
		}
		return depFrom;
	}

	public static ArrayList<Flight> arrTo(ArrayList<Flight> alf, Airport a) {
		ArrayList<Flight> arrTo = Flight.newFlightList();
		for (Flight fl : alf) {
			if (fl.getArrLoc() == a) {
				arrTo.add(fl);
			}
		}
		return arrTo;
	}

	//
	// misc methods

	// used to quickly verify all flights were added to the main list from the csv
	public static int getFlightsSize() { 
		return flights.size();
	}
}
