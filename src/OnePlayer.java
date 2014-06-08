import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class OnePlayer extends FlatPanel {
	
	private Tunnel tunnel;
	private Ship ship;
	
	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73),Parameters.MIN_THRESHOLD);
		this.ship = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT});
		this.whithBreak = true;
	}

	@Override
	public void personalController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		tunnel.controller(g2d);
		//p.spread();
		//ship1.controller(tunnel,ship1,g2d);
	}
	
}
