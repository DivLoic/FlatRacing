import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


public class ButtonManager {
	
	private Button b1;
	private Button b2;
	private boolean step2 = false;
	
	public ButtonManager(){
		b1 = new Button ("Jouer", 200, 150, true);
		b2 = new Button ("RŽseaux", 350, 150, false);
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
		b1 = new Button("1 Joueur", 200, 150, true);
		b2 = new Button ("2 Joueurs", 350, 150, false);
		Game.joystick.addKey(KeyEvent.VK_ENTER);
	}
	
	private void slideTonework(){
		
	}
	
	
	public void controller(Graphics2D g){
		b1.controller(g);
		b2.controller(g);
		
		if(Game.joystick.getMove(KeyEvent.VK_RIGHT)  ){
			buttonSwitch(true);
		}
		if(Game.joystick.getMove(KeyEvent.VK_LEFT)){
			buttonSwitch(false);
		}
		if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && !step2){
			slideToplay();
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && step2){
			Game.skipMenu = true;
			Game.choiceMenu = 1;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && step2){
			Game.skipMenu = true;
			Game.choiceMenu = 0;
		}
		
	}
}
