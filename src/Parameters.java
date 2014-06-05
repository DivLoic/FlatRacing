import java.awt.Color;


public class Parameters {
	
	public static final int SCREEN_MAX_WIDTH = 700;
	public static final int SCREEN_MAX_HEIGHT = 300;
	
	public static final int INFORMATIONS_MAX_HEIGHT = 80;
	
	public static final int WINDOW_HEADER_HEIGHT = 22;
	public static final int WINDOW_MAX_WIDTH = SCREEN_MAX_WIDTH;
	public static final int WINDOW_MAX_HEIGHT = SCREEN_MAX_HEIGHT + WINDOW_HEADER_HEIGHT + INFORMATIONS_MAX_HEIGHT;
	
	public static final int MIN_THRESHOLD = (SCREEN_MAX_HEIGHT/3);
	public static final int MAX_THRESHOLD = SCREEN_MAX_HEIGHT;
	
	public static final double DT = 1;
	
	public static final Color DEFAULT_COLOR = Color.BLACK;
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	public static final double METEOR_SIZE = 7;
	
	public static final double METEOR_MAX_SPEED = 4.5;
	public static final double METEOR_PROBA = 8;

	
	
	
	public static final double REMAINS_SIZE = 5;
	public static final int REMAINS_PER_IMPACT = 20;
	

	
}
