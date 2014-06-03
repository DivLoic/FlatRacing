import javax.swing.JFrame;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame {
	
	public static  Keyboard joystick = new Keyboard(); 
	

	public Game(int width, int height) {
		this.setTitle("FlatRacing");
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.addKeyListener(new KeyListener() {
    		public void keyTyped (KeyEvent e){
    			
    		}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				joystick.press(arg0);
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				joystick.release(arg0);
			}
		});
    		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
			
			try {
				Thread.sleep((1000/60) - processTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
		Game FlatRacing = new Game(Parameters.SCREEN_MAX_WIDTH, Parameters.SCREEN_MAX_HEIGHT);
		
		FlatRacing.launchTwoPlayers();
		
	}

}
