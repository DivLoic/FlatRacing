import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Button extends JButton implements MouseListener
{
	private String name;
	
	public Button(String str){
		super(str);
		this.name = str;
	}
	

	public void paintComponent(Graphics g){
		    Graphics2D g2d = (Graphics2D)g;
		    g2d.setColor(Color.BLACK);
		    g2d.fillRoundRect(0, 0, this.getWidth(),this.getHeight() ,15,15);
		    g.setColor(Color.WHITE);
		    g2d.fillRoundRect(7, 7, this.getWidth()-15, this.getHeight()-15, 10, 10);
		    g2d.setColor(Color.BLACK);
		    g2d.drawString(this.name, this.getWidth() / 2 - (this.getWidth()/ 2 /4), (this.getHeight() / 2) + 5);
		  }
	

	public void mouseClicked(MouseEvent e) {
		//?????
	}
	
	public void mouseEntered(MouseEvent e) {
		//Silence is golden -- Citation Wordpress =D
	}
	
	public void mouseExited(MouseEvent e)  {
		//Silence is golden
	}
	
	public void mousePressed(MouseEvent e) {
		//Silence is golden
	}
	
	public void mouseReleased(MouseEvent e)  {
		//Silence is golden
	}
}
