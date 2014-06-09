import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

class Button{
	
	private String text;
	private int posX;
	private int posY;
	private boolean option;
	public boolean selected;
	
	public Button(boolean leftArrow){
		
	}
	
	public Button(String txt, int X, int Y, boolean selec){
		this.option = true;
		this.text = txt;
		this.posX = X;
		this.posY = Y;
		this.selected = selec;
	}
	
	public void draw(Graphics2D g) {
		if(option){
				g.fillRoundRect(posX, posY, 110, 40, 10, 10);
			if(selected){
				g.setColor(new Color(176,95,35));
			}else {
				g.setColor(new Color(73,73,73));
			}
			g.fillRoundRect(posX+4, posY+4, 102, 32, 5, 5);
			g.setColor(Color.WHITE);
			g.drawString(this.text, posX+30, posY+25);
			g.setColor(Parameters.DEFAULT_COLOR);
			g.setStroke(new BasicStroke(3.2f));
		}
	}
	
	public void callback(){
		
	}
	
	public void release(){
		this.selected = false;
	}
	
	public void press(){
		this.selected = true;
	}
	public void controller(Graphics2D g){
		this.draw(g);
	}
	
	
}


public class ButtonManager {
	
	private Button b1;
	private Button b2;
	private boolean step2 = false;
	
	public ButtonManager(){
		this.b1 = new Button ("Jouer", 200, 150, true);
		this.b2 = new Button ("RŽseaux", 350, 150, false);
	}
	
	public void reButtonManager(){
		this.b1 = new Button ("Jouer", 200, 150, true);
		this.b2 = new Button ("RŽseaux", 350, 150, false);
	}
	
	
	private void buttonSwitch(boolean b) {
		if(b){
			this.b1.release();
			this.b2.press();
		} else {
			this.b2.release();
			this.b1.press();
		}
	}
	
	private void slideToplay(){
		Game.joystick.addKey(KeyEvent.VK_ENTER);
		step2 = true;
		b1 = new Button("1 Joueur", 200, 150, true);
		b2 = new Button ("2 Joueurs", 350, 150, false);
		
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
		if(Game.joystick.getMove(KeyEvent.VK_ESCAPE) && step2){
			Game.joystick.addKey(KeyEvent.VK_ESCAPE);
			step2 = false;
			reButtonManager();
		}
		if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && !step2){
			slideToplay();
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && step2){
			Game.mode = 2;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && step2){
			Game.mode = 1;
		}
		
	}
}
