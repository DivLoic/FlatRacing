
public class Utilities {
	
	public static int randomIntFromInterval(int min, int max) {
		return (int)Math.round(Math.random() * (max-min+1) + min);
	}
	
	public static double distanceTwoPoints(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
}
