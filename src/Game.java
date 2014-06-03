import javax.swing.JFrame;

import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements ActionListener{
	
	public static Hashtable<String, Boolean> joystick1 = new Hashtable<String ,Boolean>();
	public static Hashtable<String, Boolean> joystick2 = new Hashtable<String ,Boolean>();

	public Game(int width, int height) {
		this.setTitle("FlatRacing");
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		joystick1.put("left", false);
		joystick1.put("right", false);
		joystick1.put("up", false);
		joystick1.put("down", false);
		
		this.addKeyListener(new KeyListener() {
    		public void keyTyped (KeyEvent e){
    			
    		}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				switch(arg0.getKeyCode()){
				case 38:
					joystick1.put("up", true);
					break;
				case 40:
					joystick1.put("down", true);
					break;
				case 37:
					joystick1.put("left", true);
					break;
				case 39:
					joystick1.put("right", true);
					break;
				default:
					break;
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				switch(arg0.getKeyCode()){
				case 38:
					joystick1.put("up", false);
					break;
				case 40:
					joystick1.put("down", false);
					break;
				case 37:
					joystick1.put("left", false);
					break;
				case 39:
					joystick1.put("right", false);
					break;
				default:
					break;
				}
			}
		});
    		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
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
		
		Game FlatRacing = new Game(Parameters.SCREEN_MAX_WIDTH, Parameters.SCREEN_MAX_HEIGHT + Parameters.SCREEN_HEADER_HEIGHT);
		
		FlatRacing.launchTwoPlayers();
		
	}

}
