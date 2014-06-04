
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Button{
	
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
			if(selected){
				GradientPaint gp = new GradientPaint(posX, posY, new Color(147,76,147), posX+100, posY+30, new Color(176,95,35), true);
				g.setPaint(gp);
				g.fillRoundRect(posX, posY, 100, 30, 10, 10);
			}else {
				g.fillRoundRect(posX, posY, 100, 30, 10, 10);
			}
			g.setColor(Color.WHITE);
			g.fillRoundRect(posX+5, posY+5, 90, 20, 10, 10);
			g.setColor(Parameters.DEFAULT_COLOR);
			g.drawString(this.text, posX+25, posY+20);
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
