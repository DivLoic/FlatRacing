import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class TwoPlayers extends FlatPanel {
	
	private Tunnel tunnel ;
	private MeteorShawer allMeteors;
	
	private Ship[] allShips;
		

	private void printTime(int duration, Graphics2D g) {
		int min = duration / 60;
		int sec = duration % 60;
		
		String secStr = "" + sec;

		if(sec < 10) {
			secStr = "0" + sec;
		}

		String time = "[ " + min + ":" + secStr + " ]";
		
		Font myFont = new Font("Arial", Font.BOLD, 16);
		g.setFont(myFont);
		
		if(!this.gameOver) {
			FontMetrics fm = g.getFontMetrics(myFont);
			int lengthStringTime = fm.stringWidth(time);
			g.drawString(time, Parameters.SCREEN_MAX_WIDTH/2 - lengthStringTime/2, Parameters.SCREEN_MAX_HEIGHT + 37);
		} else {
			FontMetrics fm = g.getFontMetrics(myFont);
			int lengthStringTime = fm.stringWidth("GAME OVER");
			g.drawString("GAME OVER", Parameters.SCREEN_MAX_WIDTH/2 - lengthStringTime/2, Parameters.SCREEN_MAX_HEIGHT + 37);
		}
	}

	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD);
		this.allMeteors = new MeteorShawer(); 
		
		this.allShips = new Ship[]{
				new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT}, true, 25),
				new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(147,76,147), 20, new int[]{KeyEvent.VK_Z, KeyEvent.VK_D,  KeyEvent.VK_S, KeyEvent.VK_Q}, false, 25),
				//new Ship(200, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(127,16,147), 20, new int[]{KeyEvent.VK_U, KeyEvent.VK_K,  KeyEvent.VK_H, KeyEvent.VK_J}, true, 70)
		};
		
		this.whithBreak = true;
	}

	@Override
	public void personalController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		if(Game.mainClock % 60 == 0) {
			this.gameDuration--;
		}
			Game.mainClock++;
			
			this.allMeteors.controller(tunnel, g2d);
			
			for(int i = 0, n = this.allShips.length ; i < n ; i++) {
				this.allShips[i].controller(tunnel, allShips, allMeteors, g2d, true);
			}
			
			tunnel.controller(g2d);
			
			this.printTime(this.gameDuration, g2d);
			
			Graphics g = (Graphics) g2d;
			g.setColor(Parameters.BACKGROUND_COLOR);
			g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le l�ger d�passement du bord du tunnel sur les informations
			g.setColor(Parameters.DEFAULT_COLOR);

			if( this.gameDuration <= 0 || this.ship1.lives <= 0 || this.ship2.lives <= 0 ) {
				this.gameOver = true;
				if(this.gameDuration <= 0){
					if(this.ship1.lives * this.ship1.lives*this.ship1.score > this.ship2.lives * this.ship2.lives*this.ship2.score){
						this.ship1.getVictory();
					} else {
						this.ship2.getVictory();
					}
				} else {
					if(this.ship1.lives == 0){
						this.ship2.getVictory();
					} else {
						this.ship1.getVictory();
					}
				}
			}
	}

	@Override
	public void endingController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		tunnel.print(g2d);
		this.ship1.print(g2d);
		this.ship2.print(g2d);
		
		this.ship1.finalScore(g2d, true, ship2);
		this.ship2.finalScore(g2d, false, ship2);
		
		printTime(this.gameDuration, g2d);
		
		Graphics g = (Graphics) g2d;
		g.setColor(Parameters.BACKGROUND_COLOR);
		g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le l�ger d�passement du bord du tunnel sur les informations
		g.setColor(Parameters.DEFAULT_COLOR);
	}
	
	
	
}
