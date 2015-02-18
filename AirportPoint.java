import java.awt.geom.Point2D;
import java.util.ArrayList;

public class AirportPoint extends Point2D.Double {
	public static ArrayList<AirportPoint> airportpoints = new ArrayList<AirportPoint>();
	private static String name;
	private static Airport airport;
	
	public AirportPoint(String name, double x, double y){
		super(x,y);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Airport getAirport() {
		return airport;
	}
	
	public static void popPoints() {
		for (Airport s : Airport.getAirports()) {
			AirportPoint t = null;
			if (s.getLgt() < -130) {
				if (s.getLat() > 60) {
					t = new AirportPoint(s.getName(), 100.0, 75.0);
				} else {
				    t = new AirportPoint(s.getName(), (double) (1000 - ((s.getLgt()*-1)-100)*15), (double) (600 - (s.getLat() - 20)*15));
			    }
			} else {
				t = new AirportPoint(s.getName(), (double) (1000 - ((s.getLgt()*-1)-80)*15), (double) (600 - (s.getLat() - 20)*15));
			}
			airportpoints.add(t);
		}
	}
	public static ArrayList<AirportPoint> returnPoints() {
		return AirportPoint.airportpoints;

	}
}
