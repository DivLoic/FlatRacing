

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;

public class Game extends JFrame {


		public static Keyboard joystick = new Keyboard();
		public static boolean skipMenu = false ;
		public static int choiceMenu;
		public static int mainClock = 0;
		
		


		public Game(int width, int height) {
			this.setTitle("FlatRacing");
			this.setSize(width, height);
			this.setResizable(false);
			this.setLocationRelativeTo(null);

			this.addKeyListener(new KeyListener() {
	    		public void keyTyped (KeyEvent e) {}

				public void keyPressed(KeyEvent arg0) {
					joystick.press(arg0);
				}

				public void keyReleased(KeyEvent arg0) {
					joystick.release(arg0);
				}
			});
	    	
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		private void lauchMenu(){

			while(!skipMenu) {

				try {
					Thread.sleep((1000/60) - processTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		

		private void launchOnePlayer() {
			OnePlayer subGame = new OnePlayer();
			
			while(true) {
			
				try {
					Thread.sleep((1000/60) - processTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void launchServer(){
			while() {
				mainClock++;
				try {
					Thread.sleep((1000/60) - processTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void launchTwoPlayers() {
			TwoPlayers subGame = new TwoPlayers();
			this.setContentPane(subGame);
			this.setVisible(true);

			while(true) {
				long startTime = System.currentTimeMillis();
				subGame.repaint();
				long endTime = System.currentTimeMillis();
				long processTime = endTime - startTime > 1000/60 ? 1000/60 : endTime - startTime;
				mainClock++;
				try {
					Thread.sleep((1000/60) - processTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		
		private void launchNet(ObjectOutputStream o , ObjectInputStream i ) {
			
			}
		}
		public static void main(String[] args) {

			Game FlatRacing = new Game(Parameters.WINDOW_MAX_WIDTH, Parameters.WINDOW_MAX_HEIGHT);
			while(true){
			
			switch(choiceMenu){
				case 0:
					FlatRacing.lauchMenu();
				break;
				case 1:
					FlatRacing.launchOnePlayer();
				break;
				case 2:
					FlatRacing.launchTwoPlayers();
				break;
				case 3:
					FlatRacing.launchNet(out, in);
				break;
				case 4:
					FlatRacing.launchServer();
					break;
				default:

				break;
			}
		
		}	
	}




}
