import java.awt.Color;
import java.awt.event.KeyEvent;


public class Game {

	public static void main(String[] args) {
		
		StdDraw.setCanvasSize(Parameters.SCREEN_MAX_WIDTH, Parameters.SCREEN_MAX_HEIGHT);
		StdDraw.setXscale(0, Parameters.SCREEN_MAX_WIDTH);
		StdDraw.setYscale(0, Parameters.SCREEN_MAX_HEIGHT);
		
		Tunnel tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -5, (Parameters.SCREEN_MAX_HEIGHT - Parameters.MAX_THRESHOLD) / 2, Color.BLACK, Color.BLACK);
		Ship player = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 15, 15, 0.6, 0.6, 0.93, 0.93, 10, Color.BLACK, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
		
		while(true) {
			StdDraw.clear();
			
			tunnel.controller();
			player.controller();
			
			StdDraw.show(1000/60);
		}

	}

}
