import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class NetPlayers extends FlatPanel{
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public static double [][] tunnelup;
	public static double [][] tunneldown;
	
	private Ship ship1;
	private Ship shipdistant;
	
	private TunnelNetwork tunnelNet;

	private Thread t;

	private Thread tup;
	private Thread tdown;
	
	public NetPlayers(ObjectOutputStream o, ObjectInputStream i, boolean isfirst) {
		this.out = o;
		this.in = i ;
		
		if (isfirst){
			this.ship1 = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT}, true, 25);
			this.shipdistant = new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(147,76,147), 20, new int[]{0, 0,  0, 0}, false, 25);
			} else {
			this.ship1 = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8,  new Color(147,76,147), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT}, true, 25);
			this.shipdistant = new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8,new Color(176,95,35), 20, new int[]{0, 0,  0, 0}, false, 25);
			}
		
		tup = new Thread(new PlayerUpLink(out, ship1, isfirst));
		tdown = new Thread(new PlayerDownLink(in ,shipdistant, !isfirst));
		
		tup.start();
		tdown.start();
	}
	
	@Override
	public void personalController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		this.ship1.print(g2d);
		this.ship1.collisionTunnel(tunnelNet);
		this.shipdistant.collisionTunnel(tunnelNet);
		//this.shipdistant.controllerEffect(tunnelNet, g2d ,false);
		this.shipdistant.print(g2d);
		
		this.tunnelNet.controller(g2d);

	}

	@Override
	public void endingController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnelNet = new TunnelNetwork(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD, this.tunnelup, this.tunneldown); 
		this.whithBreak = false;
	}
	
	

}

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
