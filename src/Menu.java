
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.*;


public class Menu extends JPanel {
	
	public boolean networkRequest = false;
	Tunnel tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), 200);
	ButtonManager jbm = new ButtonManager();
	@Override
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
		jbm.controller(g2d, this);

	}
	

	
}
