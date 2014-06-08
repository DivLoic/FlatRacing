import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class TwoPlayers extends FlatPanel {
	
	private Tunnel tunnel ;
	private MeteorShawer allMeteors;
	private Ship ship1;
	private Ship ship2;
		

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
		
		FontMetrics fm = g.getFontMetrics(myFont);
		int lengthStringTime = fm.stringWidth(time);
		
		g.drawString(time, Parameters.SCREEN_MAX_WIDTH/2 - lengthStringTime/2, Parameters.SCREEN_MAX_HEIGHT + 37);
	}

	@Override
	public void buildElements() {
		// TODO Auto-generated method stub
		this.tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73), Parameters.MIN_THRESHOLD);
		this.allMeteors = new MeteorShawer(); 
		this.ship1 = new Ship(50, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(176,95,35), 20, new int[]{KeyEvent.VK_UP, KeyEvent.VK_RIGHT,  KeyEvent.VK_DOWN, KeyEvent.VK_LEFT});
		this.ship2 = new Ship(150, Parameters.SCREEN_MAX_HEIGHT/2, 0, 0, 5, 5, 0.5, 0.5, 0.93, 0.93, 8, new Color(147,76,147), 20, new int[]{KeyEvent.VK_Z, KeyEvent.VK_D,  KeyEvent.VK_S, KeyEvent.VK_Q});
		this.whithBreak = true;
	}

	@Override
	public void personalController(Graphics2D g2d) {
		// TODO Auto-generated method stub
		if(Game.mainClock % 60 == 0) {
			Game.gameDuration--;
		}
			Game.mainClock++;
			
			this.allMeteors.controller(tunnel, g2d);
			this.ship1.controller(tunnel, ship2, g2d, allMeteors ,true);
			this.ship2.controller(tunnel, ship1, g2d, allMeteors ,false);
			tunnel.controller(g2d);
			
			this.printTime(Game.gameDuration, g2d);
			
			Graphics g = (Graphics) g2d;
			g.setColor(Parameters.BACKGROUND_COLOR);
			g.fillRect(0, Parameters.SCREEN_MAX_HEIGHT, this.getWidth(), 10); // Supprimer le lŽger dŽpassement du bord du tunnel sur les informations
			g.setColor(Parameters.DEFAULT_COLOR);


	}
	
}
