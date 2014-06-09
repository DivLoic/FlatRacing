

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends FlatPanel{

	public static double[] Gamer1 = new double[3];
	public static double[] Gamer2 = new double[3];
	
	public static boolean starter1 = false;
	public static boolean starter2 = false;
	
	protected static double[][] pointsTop;
	protected static double[][] pointsBottom;
	
    public static boolean firstplayer =true;
	private ServerSocket server;
	private Thread t1, t2;
	

	private int port = Parameters.FIRST_PORT;
	private boolean serverSetted = false;
	private String host;
	
	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		while(!serverSetted){
			try{
				buildTunnelElements();
				server = new ServerSocket(port,0);
				serverSetted = true;
				System.out.println("Radar en place");
				InetAddress ht = InetAddress.getLocalHost();
				host = ht.getHostAddress();
			}catch(IOException e){
				serverSetted = false;
				port++;
				e.printStackTrace();
				
			}
		}
		
		t1 = new Thread(new acceptClient(server, Gamer1,Gamer2,firstplayer));
		t1.start();
		
		t2 = new Thread(new acceptClient(server,Gamer2,Gamer1,firstplayer));
		t2.start();
	}
	
public void buildTunnelElements() {
		
		int nbPoints = 500;

		this.pointsTop = new double[nbPoints][2];
		this.pointsBottom = new double[nbPoints][2];
		
		TunnelNetwork.initPoints(pointsTop, pointsBottom);
}

	@Override
	public void personalController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.setColor(new Color(73,73,73));
		g2d.getFontMetrics(new Font("Arial", Font.BOLD, 16));
		g2d.drawString(host, 200,200);
		g2d.setColor(Parameters.DEFAULT_COLOR);
		
		//tunnel.controller(g2d);
	}

	@Override
	public void endingController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}
	
	  public static void ready(){
          if(starter1 == true){
              starter2 = true;
          }else {
              starter1 = true;
          }
      }

}

class acceptClient implements Runnable {
	
	private ServerSocket server;
	private Socket socket;
	private Thread t;
        
        private ObjectOutputStream out;
        private ObjectInputStream in;
        
	private double[] local;
    private double[] distant;
	private boolean first;
        
//-------------------------------------------------    
	
	
public acceptClient(ServerSocket s, double[] local ,double[] distant,boolean first){
		this.server = s;
		this.local = local;
        this.distant = distant;
		this.first = first;
            
                                
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
			try {
				socket = server.accept();
			    this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());
                in.readBoolean();
                out.writeObject(Server.pointsTop);
                out.reset();
                in.readBoolean();
                out.writeObject(Server.pointsBottom);
                in.readBoolean();
                Server.ready();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

                        while(!Thread.currentThread().isInterrupted()){
                            if(Server.starter1 && Server.starter2){
                                Thread.currentThread().interrupt();
                            } else {
                             
                            }
                        }
                        
                        System.out.print("EEEEEEEEEEEEEEEEEEEEEEEEEEE");
            try {
                if(Server.firstplayer){
                	out.writeBoolean(Server.firstplayer);
                	out.flush();
                	Server.firstplayer = false;
                }else{
                	out.writeBoolean(Server.firstplayer);
                	out.flush();
                	 
                }
            } catch (IOException ex) {
                Logger.getLogger(acceptClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            Thread up = new Thread(new UpLink(out, true));
            Thread up2 = new Thread(new UpLink(out, false));
            Thread down = new Thread(new DownLink(in,Server.firstplayer));
            
            up.start();
            //up2.start();
            down.start();                
           
	}
}


//-------------------------------------------------

class DownLink implements Runnable {

	private ObjectInputStream in;
	private boolean first;
	private double[] acc = new double[3];
	
	public DownLink(ObjectInputStream i, boolean firstPlayer){
		this.in = i;
		this.first = Server.firstplayer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			while(true){
				
				try {
					acc = (double[])in.readObject();
					if(acc[0] == 1){
						Server.Gamer1 = acc;
					} else {
						Server.Gamer2 = acc;
					}
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
				}
			}
	
	}
}

//-------------------------------------------------



class UpLink implements Runnable {

	private ObjectOutputStream out;
	private boolean first;
	
	public UpLink(ObjectOutputStream o, boolean firstPlayer){
		this.out = o;
		this.first = firstPlayer;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			while(true){
				try {

						out.writeObject(Server.Gamer1);
		                out.reset();

						out.writeObject(Server.Gamer2);
	                    out.reset();

                    
				} catch (IOException e) {
						// TODO Auto-generated catch block
						System.err.println("Encre n¡2");
				}
			}
	}	
}
