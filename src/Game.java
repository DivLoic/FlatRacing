import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

@SuppressWarnings("serial")
public class Game extends JFrame {

	public static Keyboard joystick = new Keyboard();
	public static int mode;
	public static boolean gameBreak = false;
	public static int mainClock = 0;

	public static ObjectInputStream in;
	public static ObjectOutputStream out;

	public Game(int width, int height) {
		this.setTitle("FlatRacing");
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.addKeyListener(new KeyListener() {
    		public void keyTyped (KeyEvent e) {
    			
    		}

			public void keyPressed(KeyEvent arg0) {
				joystick.press(arg0);
				
			}

			public void keyReleased(KeyEvent arg0) {
				joystick.release(arg0);
			}
		});
    	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	public static void main(String[] args) {

		Game FlatRacing = new Game(Parameters.WINDOW_MAX_WIDTH, Parameters.WINDOW_MAX_HEIGHT);
		
		while(true) {
			switch(mode){
				case 0:
					Launcher menu = new Launcher(FlatRacing, new Menu(),0);
					menu.loop();
				case 1:
					Launcher onePlayer = new Launcher(FlatRacing, new OnePlayer(),1);
					onePlayer.loop();
					break;
				case 2:
					Launcher twoPlayers = new Launcher(FlatRacing, new TwoPlayers(),2);
					twoPlayers.loop();
					break;
				case 3:
					Launcher netPlayers = new Launcher(FlatRacing, new NetPlayers(out, in, Connection.firstPlayer),3);
					netPlayers.loop();
					break;
				case 4:
					Launcher server = new Launcher(FlatRacing, new Server(),4);
					server.loop();
					break;
				default:
					Launcher menudefault = new Launcher(FlatRacing, new Menu(),0);
					menudefault.loop();
				break;
			}
		}
	}



}