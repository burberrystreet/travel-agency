import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class GTA {

	//main method
	public static void main(String[] args) throws FileNotFoundException {
		Airport.setAirports();
		Flight.setFlights();
		System.out.println("Welcome to GTA!");
		GTA.tier1selection();
	}

	//
	//static navigation methods

	public static void tier1selection() throws FileNotFoundException {

		Scanner t1 = new Scanner(System.in);
		System.out.println("Enter 1 for all airports.");
		System.out.println("Enter 2 for all flights.");
		System.out.println("Enter 3 for a particular airport.");
		int input = t1.nextInt();
		if (input == 1) {
			GTA.tier2airports();
		} else if (input == 2) {
			GTA.tier2flights();
		} else if (input == 3) {
			GTA.tier2oneairport();
		} else {
			char cont = GTA.incorrectSelection(t1);
			while (cont == 'Y') {
				GTA.tier1selection();
			}
		}
	}

	public static void tier2airports() throws FileNotFoundException {
		Scanner t2a = new Scanner(System.in);
		System.out.println("Enter 1 to see all airports.");
		System.out.println("Enter 2 to see the airport with the shortest delay.");
		System.out.println("Enter 3 to choose one airport.");
		System.out.println("Enter 4 to see the map.");
		System.out.println("Enter 0 to go back to the main menu.");
		int input = t2a.nextInt();
		if (input == 1) {
			for (String s : Airport.apAllSimpleToString()) {
				System.out.println(s);
			}
			GTA.tier2airports();
		} else if (input == 2) {
			Airport min = Airport.minDelay(Airport.getAirports());
			for (String s : Airport.apOneSimpletoString(min)) {
				System.out.println(s);
			}
			GTA.tier2airports();
		} else if (input == 3) {
			GTA.tier2oneairport();
		} else if (input == 4) {
			GTA.renderMap();
		} else if (input == 0) {
			GTA.tier1selection();
		} else {
			char cont = GTA.incorrectSelection(t2a);
			while (cont == 'Y') {
				GTA.tier2airports();
			}
		}
	}

	public static void tier2flights() throws FileNotFoundException {
		Scanner t2f = new Scanner(System.in);
		System.out.println("Enter 1 to see all flights.");
		System.out.println("Enter 2 to see the flight with the shortest duration.");
		System.out.println("Enter 0 to go back to the main menu.");
		int input = t2f.nextInt();
		if (input == 1) {
			for (String s : Flight.flAllSimpleToString()) {
				System.out.println(s);
			}
			GTA.tier2flights();
		} else if (input == 2) {
			for (String s : Flight.flOneSimpleToString(Flight.minDur(Flight.getFlights()))) {
				System.out.println(s);
			}
			GTA.tier2flights();
		} else if (input == 0) {
			GTA.tier1selection();
		} else {
			char cont = GTA.incorrectSelection(t2f);
			while (cont == 'Y') {
				GTA.tier2flights();
			}
		}
	}

	public static void tier2oneairport() throws FileNotFoundException {
		Scanner t2o = new Scanner(System.in);
		System.out.println("What airport would you like to select?");
		Airport one = GTA.selectAirport();
		System.out.println("Enter 1 to see the flights from this Airport.");
		System.out.println("Enter 2 to see the flights to this Airport.");
		System.out.println("Enter 3 to add a second airport.");
		System.out.println("Enter 0 to go back to the main menu.");
		int input = t2o.nextInt();
		if (input == 1) {
			ArrayList<Flight> depFrom = Flight.depFrom(Flight.getFlights(), one);
			for (String s : Flight.flListSimpleToString(depFrom)) {
				System.out.println(s);
			}
			GTA.optimize(depFrom);
		} else if (input == 2) {
			ArrayList<Flight> arrTo = Flight.arrTo(Flight.getFlights(), one);
			for (String s : Flight.flListSimpleToString(arrTo)) {
				System.out.println(s);
			}
			GTA.optimize(arrTo);
		} else if (input == 3) {
			GTA.tier3secondairport();
		} else if (input == 0) {
			GTA.tier1selection();
		} else {
			char cont = GTA.incorrectSelection(t2o);
			while (cont == 'Y') {
				GTA.tier2oneairport();
			}
		}

	}

	public static void tier3secondairport() throws FileNotFoundException {
		System.out.println("Sorry, under construction.");
		GTA.tier2oneairport();
	}

	public static char incorrectSelection(Scanner s) {
		System.out.println("Incorrect selection. Try again?");
		String cont = s.next();
		cont.toUpperCase();
		return cont.charAt(0);
	}

	public static Airport selectAirport() {
		Scanner select = new Scanner(System.in);
		System.out.println("Type the 3-letter code of the desired airport.");
		String inputCode = select.next();
		if (inputCode.length() > 3) {
			inputCode = inputCode.substring(0, 2);
		} else if (inputCode.length() < 3) {
			char cont = GTA.incorrectSelection(select);
			while (cont == 'Y') {
				GTA.selectAirport();
			}
		}
		return Airport.codeToAirport(inputCode);
	}

	//
	//static optimization methods

	public static void optimize(ArrayList<Flight> lf) throws FileNotFoundException {
		Scanner o = new Scanner(System.in);
		System.out.println("How would you like to optimize this list?");
		System.out.println("Enter 1 to see the shortest flight.");
		System.out.println("Enter 2 to see the cheapest flight.");
		System.out.println("Enter 0 to go back to airport menu.");
		int input = o.nextInt();
		if (input == 1) {
			Flight shortest = GTA.optimizeTime(lf);
			for (String s : Flight.flOneSimpleToString(shortest)) {
				System.out.println(s);
			}
			GTA.optimize(lf);
		} else if (input == 2) {
			Flight cheapest = GTA.optimizePrice(lf);
			for (String s : Flight.flOneSimpleToString(cheapest)) {
				System.out.println(s);
			}
			GTA.optimize(lf);
		} else if (input == 0) {
			GTA.tier2oneairport();
		} else {
			char cont = GTA.incorrectSelection(o);
			while (cont == 'Y') {
				GTA.optimize(lf);
			}
		}
	}

	public static Flight optimizeTime(ArrayList<Flight> lf) {
		Flight shortest = new Flight();
		int shortestTime = 1800;
		for (Flight a : lf) {
			if (a.getDuration() < shortestTime) {
				shortestTime = a.getDuration();
				shortest = a;
			}
		}
		return shortest;
	}

	public static Flight optimizePrice(ArrayList<Flight> lf) {
		Flight cheapest = new Flight();
		double bestPrice = 200.0;
		for (Flight a : lf) {
			if (a.getPrice() < bestPrice) {
				bestPrice = a.getPrice();
				cheapest = a;
			}
		}
		return cheapest;
	}

	public static void renderMap() {
	    JFrame f = new JFrame("Map");
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    AirMap map = new AirMap();
	    f.add(map);
	    f.setVisible(true);
	}
	
}
