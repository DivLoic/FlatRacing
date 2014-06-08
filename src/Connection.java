

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Connection implements Runnable{
	private Socket socket;
	public Thread t;
	private static byte[] ip = new byte[4];
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private int port = Parameters.FIRST_PORT;
	private boolean rightPort = false;
	public static boolean firstPlayer;

	public Connection(int[] tab ) {
		for(int i = 0; i < tab.length; i++){
			ip[i] = (byte)(tab[i]);
		}

	}
	
	private void slidePort(){
		port++;
		rightPort = false;
				
	}
	
	private void skipConnection(){
		Game.skipMenu = true;
		Game.choiceMenu = 2;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!rightPort){
			try{
				System.out.print("\nDemande de connexion"+ port);
				InetAddress host = InetAddress.getByAddress(ip);
				socket = new Socket(host,port);
			
				
				rightPort = true;
				
			}catch(UnknownHostException e){
				e.printStackTrace();
			}catch(IOException e){
				System.err.println("Error2");
				slidePort();
				
			}
			
		}
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());;
			in = new ObjectInputStream(socket.getInputStream());;
			System.out.print("\nConnection Etablit");
			out.writeBoolean(true);
			out.reset();
			NetPlayer.tunnelup = (double[][]) in.readObject();
			out.writeBoolean(true);
			out.reset();
			NetPlayer.tunneldown = (double[][]) in.readObject();
			IpAdress.setLabel("waiting for ennemy...");
			out.writeBoolean(true);
			out.reset();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		try {
			firstPlayer = in.readBoolean();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.err.println("Extinction du server ");
		}
		Game.out = out;
		Game.in = in; 
		skipConnection();
		
			
		}
	}

