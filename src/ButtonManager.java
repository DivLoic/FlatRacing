import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

class Button{
	
	private String text;
	private int x;
	private int y;
	private boolean option;
	public boolean selected;
	private Color color;
	
	public Button(String txt, int X, int Y, boolean selec, Color color ){
		this.option = true;
		this.text = txt;
		this.x = X;
		this.y = Y;
		this.selected = selec;
		this.color = color;
	}
	
	public void draw(Graphics2D g) {
		if(option){
			g.fillRoundRect(x, y, 110, 40, 10, 10);
		 if(selected){
			g.setColor(this.color);//new Color(176,95,35)
		}else {
			g.setColor(new Color(73,73,73));
		}
		g.fillRoundRect(x+4, y+4, 102, 32, 5, 5);
		g.setColor(Color.WHITE);
		g.drawString(this.text, x+30, y+25);
		g.setColor(Parameters.DEFAULT_COLOR);
		g.setStroke(new BasicStroke(3.2f));
	}
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
	private boolean localGame = true;
	
	public ButtonManager(){
		b1 = new Button ("Jouer", 200, 150, true,new Color(176,95,35));
		b2 = new Button ("Réseaux", 350, 150, false,new Color(176,95,35));
	}
	
	public void reButtonManager(){
		step2 = false;
		localGame = true;
		b1 = new Button ("Jouer", 200, 150, true,new Color(176,95,35));
		b2 = new Button ("Réseaux", 350, 150, false,new Color(176,95,35));
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
		b1 = new Button("1 Players", 200, 150, true, new Color(176,95,35));
		b2 = new Button ("2 Players", 350, 150, false, new Color(176,95,35));
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
		
		if(Game.joystick.getMove(KeyEvent.VK_RIGHT)  ){
			buttonSwitch(true);
		}
		if(Game.joystick.getMove(KeyEvent.VK_LEFT)){
			buttonSwitch(false);
		}
		if(Game.joystick.getMove(KeyEvent.VK_ESCAPE) && step2){
			Game.joystick.addKey(KeyEvent.VK_ESCAPE);
			reButtonManager();
		}
		
		if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && !step2){
			slideToplay();
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && !step2){//Selection réseaux
			Game.joystick.addKey(KeyEvent.VK_ENTER);
			slideToNework();
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && step2 && localGame){
			Game.mode = 2;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && step2 && localGame){
			Game.mode = 1;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b2.selected && step2 && !localGame){//
			Game.joystick.addKey(KeyEvent.VK_ENTER);
			Game.mode = 4;
		}else if(Game.joystick.getMove(KeyEvent.VK_ENTER) && b1.selected && step2 &&  !localGame){//
			Game.joystick.addKey(KeyEvent.VK_ENTER);
			IpWindow win = new IpWindow();
			Thread threadIp = new Thread(win);
			threadIp.start();
			menu.networkRequest = true;
		}
		
	}
}
