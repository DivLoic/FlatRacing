
public class Utilities {
	
	public static int randomIntFromInterval(int min, int max) {
		return (int)Math.round(Math.random() * (max-min+1) + min);
	}
	
}
