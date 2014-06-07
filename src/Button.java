
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class Button{
	
	private String text;
	private int posX;
	private int posY;
	private boolean option;
	public boolean selected;
	private Color color;
	public Button(boolean leftArrow){
		
	}
	
	public Button(String txt, int X, int Y, boolean selec, Color color ){
		this.option = true;
		this.text = txt;
		this.posX = X;
		this.posY = Y;
		this.selected = selec;
		this.color = color;
	}
	
	public void draw(Graphics2D g) {
		if(option){
				g.fillRoundRect(posX, posY, 110, 40, 10, 10);
			 if(selected){
				g.setColor(this.color);//new Color(176,95,35)
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
