
import java.awt.Color;
import java.awt.Graphics2D;

public class Menu extends FlatPanel {
	
	private Tunnel tunnel;
	private ButtonManager jbm;
	
	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), 200);
		this.jbm = new ButtonManager();
	}
	
	@Override
	public void personalController(Graphics2D g) {
		// TODO Auto-generated method stub
		tunnel.controller(g);
		jbm.controller(g);
	}
	
}
