import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class TwoPlayers extends JPanel {



	

	Tunnel tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, (Parameters.SCREEN_MAX_HEIGHT - Parameters.MAX_THRESHOLD) / 2, Color.BLACK, Color.BLACK);
	//respecter l ordre des touches UP RIGHT DOWN LEFT
	Ship player = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 15, 15, 0.6, 0.6, 0.93, 0.93, 8, Color.BLACK, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT});
	Ship player2 = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 15, 15, 0.6, 0.6, 0.93, 0.93, 8, Color.RED, new int[]{KeyEvent.VK_Z, KeyEvent.VK_D,  KeyEvent.VK_S, KeyEvent.VK_Q});
	Ship player3 = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 15, 15, 0.6, 0.6, 0.93, 0.93, 8, Color.GREEN,new int[]{KeyEvent.VK_I, KeyEvent.VK_L,  KeyEvent.VK_K, KeyEvent.VK_J});

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Parameters.DEFAULT_COLOR);
		
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
		tunnel.controller(g2d);


		player.controller(tunnel, g2d);
		player2.controller(tunnel,g2d);
		player3.controller(tunnel, g2d);

	}
	
}
