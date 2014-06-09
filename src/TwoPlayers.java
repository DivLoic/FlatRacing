import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class TwoPlayers extends FlatPanel {
	
	private Tunnel tunnel ;
	private MeteorShawer allMeteors;
	
	private Ship[] allShips;
		

	

	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -3, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD);
		this.allMeteors = new MeteorShawer(); 
		
		this.allShips = new Ship[]{
				new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT}, true, 25),
				new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(147,76,147), 20, new int[]{KeyEvent.VK_Z, KeyEvent.VK_D,  KeyEvent.VK_S, KeyEvent.VK_Q}, false, 25),
				//new Ship(200, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(15,169,240), 20, new int[]{KeyEvent.VK_U, KeyEvent.VK_K,  KeyEvent.VK_J, KeyEvent.VK_H}, true, 70)
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
			g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le lŽger dŽpassement du bord du tunnel sur les informations
			g.setColor(Parameters.DEFAULT_COLOR);

			if( this.gameDuration <= 0 || this.allShips[0].lives <= 0 || this.allShips[1].lives <= 0 ) {
				this.gameOver = true;
				if(this.gameDuration <= 0){
					findWinner(allShips);
				} else {
					giveUp(allShips);
				}
			}
	}
	
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
			g.setColor(Color.BLACK);
			FontMetrics fm = g.getFontMetrics(myFont);
			int lengthStringTime = fm.stringWidth("GAME OVER");
			g.drawString("GAME OVER", Parameters.SCREEN_MAX_WIDTH/2 - lengthStringTime/2, Parameters.SCREEN_MAX_HEIGHT + 37);
		}
	}
	
	private void findWinner(Ship[] tabShip){
		int indice =0;
		double maxscore = 0;
		for(int i=0; i < tabShip.length; i++){
			if(tabShip[i].score + tabShip[i].lives * 10 > maxscore ){
				maxscore = tabShip[i].score + tabShip[i].lives * 10 ;
				indice = i;
			}
		}
		tabShip[indice].getVictory();
	}
	
	private void giveUp(Ship[] tabShip){
		int indice =0;
		for(int i=0; i < tabShip.length; i++){
			if(tabShip[i].lives  <= 0){
				
			} else {
				tabShip[i].getVictory();
			}
		}
	}

	@Override
	public void endingController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		tunnel.print(g2d);
		
		
		for(int i = 0, n = this.allShips.length ; i < n ; i++) {
			this.allShips[i].print(g2d);
			this.allShips[i].finalScore(g2d, this.allShips[i].informationsLeftOrRight);
		}
		
		printTime(this.gameDuration, g2d);
		
		Graphics g = (Graphics) g2d;
		g.setColor(Parameters.BACKGROUND_COLOR);
		g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le lŽger dŽpassement du bord du tunnel sur les informations
		g.setColor(Parameters.DEFAULT_COLOR);
	}

	
	
}
