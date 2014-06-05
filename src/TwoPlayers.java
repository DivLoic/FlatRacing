import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class TwoPlayers extends JPanel {
	
	Tunnel tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD);
	MeteorShawer allMeteors = new MeteorShawer(); 
	Ship ship1 = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT});
	Ship ship2 = new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(147,76,147), 20, new int[]{KeyEvent.VK_Z, KeyEvent.VK_D,  KeyEvent.VK_S, KeyEvent.VK_Q});

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Parameters.BACKGROUND_COLOR);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Parameters.DEFAULT_COLOR);
		
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
		


		g.setColor(Parameters.BACKGROUND_COLOR);
		g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), Parameters.INFORMATIONS_MAX_HEIGHT); // Supprimer le léger dépassement du bord du tunnel sur les informations
		g.setColor(Parameters.DEFAULT_COLOR);
		
		allMeteors.controller(tunnel, g2d);
		ship1.controller(tunnel, ship2, g2d, allMeteors ,true);
		ship2.controller(tunnel, ship1, g2d, allMeteors ,false);
		
		Game.mainClock++;

		tunnel.controller(g2d);

	}
	
}
