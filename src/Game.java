import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame {

	public static Keyboard joystick = new Keyboard();
	public static boolean skipMenu = false ;
	public static int choiceMenu;
	public static int mainClock = 0;
	public static int gameDuration = 180;


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
		Menu selectMenu = new Menu();
		this.getContentPane().add(selectMenu);
		this.setContentPane(selectMenu);
		this.setVisible(true);
		

		while(!skipMenu) {
			long startTime = System.currentTimeMillis();
			selectMenu.repaint();
			long endTime = System.currentTimeMillis();
			long processTime = endTime - startTime > 1000/60 ? 1000/60 : endTime - startTime;
			
			try {
				Thread.sleep((1000/60) - processTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}		

	private void launchOnePlayer() {
		OnePlayer subGame = new OnePlayer();
		
		this.getContentPane().add(subGame);
		this.setContentPane(subGame);
		this.setVisible(true);
		while(true) {
			long startTime = System.currentTimeMillis();
			subGame.repaint();
			long endTime = System.currentTimeMillis();
			long processTime = endTime - startTime > 1000/60 ? 1000/60 : endTime - startTime;
		
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
			
			if(mainClock % 60 == 0) {
				gameDuration--;
			}
			
			mainClock++;
			
			try {
				Thread.sleep((1000/60) - processTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		Game FlatRacing = new Game(Parameters.WINDOW_MAX_WIDTH, Parameters.WINDOW_MAX_HEIGHT);
		
		FlatRacing.lauchMenu();
		switch(choiceMenu){
			case 0:
				FlatRacing.launchOnePlayer();
			break;
			case 1:
				FlatRacing.launchTwoPlayers();
			break;
			default:
				// ˆsuivre
			break;
		}
	
		
	}



}