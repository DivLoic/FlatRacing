import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class ButtonManager {
	
	private Button b1;
	private Button b2;
	private boolean step2 = false;
	private boolean localGame = true;
	
	public static IpAdress IpWindow;
	public ButtonManager(){
		b1 = new Button ("Jouer", 200, 150, true,new Color(176,95,35));
		b2 = new Button ("Réseaux", 350, 150, false,new Color(176,95,35));
	}
	
	private void buttonSwitch(boolean b) {
		if(b){
			b1.release();
			b2.press();
		} else {
			b2.release();
			b1.press();
		}
	}
	
	
	private void slideToplay(){
		step2 = true;
		b1 = new Button("1 Players", 200, 150, true, new Color(176,95,35));
		b2 = new Button ("2 Players", 350, 150, false, new Color(176,95,35));
		Game.joystick.addKey(KeyEvent.VK_ENTER);
	}
	
	private void slideToNework(){
		localGame = false;
		step2 = true;
		b1 = new Button("Player", 200, 150, true,  new Color(147,76,147));
		b2 = new Button ("Server", 350, 150, false,  new Color(147,76,147));
		Game.joystick.addKey(KeyEvent.VK_ENTER);
	}
	
	
	
	public void controller(Graphics2D g , Menu menu){
		b1.controller(g);
		b2.controller(g);
		
		if(Game.joystick.getMove(KeyEvent.VK_RIGHT)  ){//On choisit le réseaux
			buttonSwitch(true);
		}
		if(Game.joystick.getMove(KeyEvent.VK_LEFT)){//On choisit de jouer
			buttonSwitch(false);
		}
		if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && !step2){//Selection local
			slideToplay();
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && !step2){//Selection réseaux
			Game.joystick.addKey(KeyEvent.VK_ENTER);
			slideToNework();
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && step2 && localGame){//
			Game.skipMenu = true;
			Game.choiceMenu = 1;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && step2 &&  localGame){//
			Game.skipMenu = true;
			Game.choiceMenu = 0;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && step2 && !localGame){//
			Game.joystick.addKey(KeyEvent.VK_ENTER);
			Game.skipMenu = true;
			Game.choiceMenu = 3;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && step2 &&  !localGame){//
			Game.joystick.addKey(KeyEvent.VK_ENTER);
			IpWindow = new IpAdress();
			Thread threadIp = new Thread(IpWindow);
			threadIp.start();
			menu.networkRequest =true;
		}
	}
}
