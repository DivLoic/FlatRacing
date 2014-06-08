
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


@SuppressWarnings("serial")
public class Menu extends FlatPanel {
	
	private Tunnel tunnel;
	private ButtonManager jbm;
	
	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), 200);
		this.jbm = new ButtonManager();
		this.whithBreak = false;
	}
	
	@Override
	public void personalController(Graphics2D g) {
		// TODO Auto-generated method stub
		tunnel.controller(g);
		jbm.controller(g);
	}

	@Override
	public void endingController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}
	
	public void basicPaint(Graphics g){
		super.basicPaint(g);
		g.setColor(new Color(73,73,73));
		g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, Parameters.SCREEN_MAX_WIDTH, Parameters.INFORMATIONS_MAX_HEIGHT);
		g.setColor(Parameters.DEFAULT_COLOR);
		
	}
	
}
