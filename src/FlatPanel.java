import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public abstract class FlatPanel extends JPanel{
	public boolean whithBreak = false;
	public abstract void buildElements();
	public abstract void personalController(Graphics2D g2d );
	public FlatPanel() {
		this.buildElements();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Parameters.BACKGROUND_COLOR);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Parameters.DEFAULT_COLOR);
		
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
		this.personalController(g2d);
		
	}
	
	public void pause(){
		
	}
	
	
}
