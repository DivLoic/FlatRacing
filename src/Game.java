import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class Game extends JFrame {

	public static Keyboard joystick = new Keyboard();
	public static int mode;
	public static boolean gameBreak = false;
	public static int mainClock = 0;



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
					Launcher Menu = new Launcher(FlatRacing, new Menu(),0);
					Menu.loop();
				case 1:
					Launcher OnePlayer = new Launcher(FlatRacing, new OnePlayer(),1);
					OnePlayer.loop();
				case 2:
					Launcher TwoPlayers = new Launcher(FlatRacing, new TwoPlayers(),2);
					TwoPlayers.loop();
				case 3:
	
				default:
					Launcher Menudefault = new Launcher(FlatRacing, new Menu(),0);
					Menudefault.loop();
				break;
			}
		}
	}



}