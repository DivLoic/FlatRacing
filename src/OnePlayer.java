import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;




@SuppressWarnings("serial")
public class OnePlayer extends FlatPanel {
	
	private Tunnel tunnel;
	
	private MeteorShawer allMeteors;
	
	private Ship[] allShips;
	
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD);
		this.allMeteors = new MeteorShawer(); 
		
		this.allShips = new Ship[]{
				new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT}, true, 25)
		};
		
		this.whithBreak = true;
	}

	@Override
	public void personalController(Graphics2D g2d) {
		// TODO Auto-generated method stub
			Game.mainClock++;
			
			this.allMeteors.controller(tunnel, g2d);
			
			for(int i = 0, n = this.allShips.length ; i < n ; i++) {
				this.allShips[i].controller(tunnel, allShips, allMeteors, g2d, true);
			}
			
			tunnel.controller(g2d);
			
			Graphics g = (Graphics) g2d;
			g.setColor(Parameters.BACKGROUND_COLOR);
			g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le lŽger dŽpassement du bord du tunnel sur les informations
			g.setColor(Parameters.DEFAULT_COLOR);

			if( this.allShips[0].lives <= 0 ) {
				this.gameOver = true;
			}
	}

	@Override
	public void endingController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		tunnel.print(g2d);

		for(int i = 0, n = this.allShips.length ; i < n ; i++) {
			this.allShips[i].print(g2d);
			this.allShips[i].checkLives(g2d);
			this.allShips[i].scoreCalculator(g2d);
		}
		printOver(g2d);
		 
		Graphics g = (Graphics) g2d;
		g.setColor(Parameters.BACKGROUND_COLOR);
		g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le lŽger dŽpassement du bord du tunnel sur les informations
		g.setColor(Parameters.DEFAULT_COLOR);
	}
	
	private void printOver(Graphics2D g) {

		if(this.gameOver) {
			Font myFont = new Font("Arial", Font.BOLD, 16);
			g.setFont(myFont);
			
			FontMetrics fm = g.getFontMetrics(myFont);
			int lengthStringTime = fm.stringWidth("GAME OVER");
			g.drawString("GAME OVER", Parameters.SCREEN_MAX_WIDTH/2 - lengthStringTime/2, Parameters.SCREEN_MAX_HEIGHT + 37);
		}
			
	}
	
}
