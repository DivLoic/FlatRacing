import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.swing.JPanel;

class PlayerUpLink implements Runnable {
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Ship ShipLocal;
	private boolean first;
	public PlayerUpLink(ObjectOutputStream o, Ship local, boolean status ){
		this.out = o;
		ShipLocal = local;
		first = status;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			ShipLocal.sendNetPos(out, first);
			
			try {
				Thread.sleep(1000/100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

class PlayerDownLink implements Runnable {
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Ship ShipLocal;
	private Ship Shipdistant;
	private boolean first;
	public PlayerDownLink(ObjectInputStream i,Ship distant, boolean status){
		this.in = i ;
		Shipdistant = distant;
		first = status;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			Shipdistant.getNetPos(in, first);
		}
	}
	
}


public class NetPlayer extends JPanel {

	ObjectOutputStream out;
	ObjectInputStream in;
	
	public static double [][] tunnelup;
	public static double [][] tunneldown;
	
	
	private Thread t;
	TunnelNetwork tunnelNet = new TunnelNetwork(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD, this.tunnelup, this.tunneldown); 
	Tunnel tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD);
	MeteorShawer allMeteors = new MeteorShawer(); 
	public Ship ship1 ;
	public Ship shipdistant ;
	private Thread tup;
	private Thread tdown;
	
	public NetPlayer(ObjectOutputStream o, ObjectInputStream i, boolean isfirst) {
		this.out = o;
		this.in = i ;
		
		if (isfirst){
			ship1 = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT});
			shipdistant = new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(147,76,147), 20, new int[]{0, 0,  0, 0});	
		} else {
			ship1 = new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(147,76,147), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT});
			shipdistant = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{0, 0,  0, 0});	
		}
	
		
		tup = new Thread(new PlayerUpLink(out, ship1, isfirst));
		tdown = new Thread(new PlayerDownLink(in ,shipdistant, !isfirst));
		
		tup.start();
		tdown.start();
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
		
		
		
		ship1.controller(tunnelNet, shipdistant, g2d, allMeteors ,true);
		shipdistant.collisionTunnel(tunnelNet);
		shipdistant.controllerEffect(tunnel, g2d ,false);
		shipdistant.print(g2d);
		
		Game.mainClock++;

		tunnelNet.controller(g2d);
		
		g.setColor(Parameters.BACKGROUND_COLOR);
		g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le léger dépassement du bord du tunnel sur les informations
		g.setColor(Parameters.DEFAULT_COLOR);
	}
}

