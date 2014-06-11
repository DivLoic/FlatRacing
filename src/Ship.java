import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;


public class Ship {
	
	private double x;
	private double y;
	
	private double vx;
	private double vy;
	private double vxMax;
	private double vyMax;
	
	public double dy, dx;
	
	private double ax;
	private double ay;
	
	private double frictX;
	private double frictY;
	
	private double r;
	private Color color;
	
	protected int lives;
	protected int score;

	private boolean invincibility;
	private int invincibilityClock;
	
	private int counterCollisionTunnel;
	
	private int up;
	private int right;
	private int down;
	private int left;
	private boolean isWinner = false;
	
	private ArrayList<Trace> flash = new ArrayList<Trace>() ;
	private PoolRemains remains = new PoolRemains(); 
	
	
	protected boolean informationsLeftOrRight;
	private int informationsY;
	
	
	
	public Ship(double x, double y , double vx, double vy, double vxMax, double vyMax, double ax, double ay, double frictX, double frictY, double r, Color color, int lives, int[] keytab, boolean informationsLeftOrRight, int informationsY) {
		this.x = x;
		this.y = y;
		
		this.vx = vx;
		this.vy = vy;
		this.vxMax = vxMax;
		this.vyMax = vyMax;
		
		
		this.ax = ax;
		this.ay = ay;
		
		this.frictX = frictX;
		this.frictY = frictY;
		
		this.r = r;
		this.color = color;
		
		this.lives = lives;
		this.score = 0;
		
		this.invincibility = false;
		this.invincibilityClock = 10;
		
		this.counterCollisionTunnel = 0;
		


		this.up = keytab[0];
		this.right = keytab[1];
		this.down = keytab[2];
		this.left = keytab[3];
		
		for(int code : keytab ){

			Game.joystick.addKey(code);
		}
		
		for(int i= 0; i< 20; i++){
			this.flash.add(new Trace());
		}
		
		this.informationsLeftOrRight = informationsLeftOrRight;
		this.informationsY = informationsY;
		
	}
	
	
	
	private void drive() {
		if(Game.joystick.getMove(right)) {//RIGHT
			if(this.vx + this.ax * Parameters.DT < this.vxMax) {
				this.vx += this.ax * Parameters.DT;
			} else {
				this.vx = this.vxMax;
			}

			timeToTrace();
		}
		
		if(Game.joystick.getMove(left)) {//LEFT
			if(this.vx - this.ax * Parameters.DT > -this.vxMax) {
				this.vx -= this.ax * Parameters.DT;
			} else {
				this.vx = -this.vxMax;
			}

			timeToTrace();
		}
		
		if(Game.joystick.getMove(down)) {// DOWN
			if(this.vy + this.ay * Parameters.DT < this.vyMax) {
				this.vy += this.ay * Parameters.DT;
			} else {
				this.vy = this.vyMax;
			}
			timeToTrace();
		}
		
		if(Game.joystick.getMove(up)) {// UP
			if(this.vy - this.ay * Parameters.DT > -this.vyMax) {
				this.vy -= this.ay * Parameters.DT;
			} else {
				this.vy = -this.vyMax;
			}
			timeToTrace();
		}
	}
	
	private void move() {
		if(this.vx < 0) {
			if(this.x + this.vx * Parameters.DT < this.r) {
				this.x = this.r;
			} else {
				this.x += this.vx * Parameters.DT;
			}
		} else {
			if(this.x + this.vx * Parameters.DT > Parameters.SCREEN_MAX_WIDTH - this.r) {
				this.x = Parameters.SCREEN_MAX_WIDTH - this.r;
			} else {
				this.x += this.vx * Parameters.DT;
			}
		}

		if(this.vy < 0)
		{
			if(this.y + this.vy * Parameters.DT < this.r) {
				this.y = this.r;
			} else {
				this.y += this.vy * Parameters.DT;
			}
		} else {
			if(this.y + this.vy * Parameters.DT > Parameters.SCREEN_MAX_HEIGHT - this.r) {
				this.y = Parameters.SCREEN_MAX_HEIGHT - this.r;
			} else {
				this.y += this.vy * Parameters.DT;
			}
		}
		
		this.vx *= this.frictX;
		this.vy *= this.frictY;
	}
	
	private void collisionTunnel(Tunnel tunnel) {
		if(this.x >= 0 && this.x < Parameters.SCREEN_MAX_WIDTH) {
			int l = (int)Math.round(this.x);
			int m = (int)Math.round(this.x - this.r);
			int n = (int)Math.round(this.x + this.r);
			
			if(this.y <= tunnel.top.y[l] + this.r) {
				double a = (tunnel.top.y[n-1] - tunnel.top.y[m]) / (n-m);
	
				this.vy = -tunnel.top.vx;
				this.vx = tunnel.top.vx * a;
	
				this.y = tunnel.top.y[l] + this.r;
				
				if(!this.invincibility) {
					this.lives--;
					this.invincibility = true;
					
					this.counterCollisionTunnel++;
					
					if(this.counterCollisionTunnel % 5 == 0) {
						if(tunnel.top.vx <= -3) {
							tunnel.top.vx++;
							tunnel.bottom.vx++;
						}
						
						this.counterCollisionTunnel = 0;
					}
						
				}
			} else if(this.y >= tunnel.bottom.y[l] - this.r) {
				double a = (tunnel.bottom.y[n-1] - tunnel.bottom.y[m]) / (n-m);
	
				this.vy = tunnel.bottom.vx;
				this.vx = -tunnel.bottom.vx * a;
	
				this.y = tunnel.bottom.y[l] - this.r;
				
				if(!this.invincibility) {
					this.lives--;
					this.invincibility = true;
					
					this.counterCollisionTunnel++;
					
					if(this.counterCollisionTunnel % 5 == 0) {
						if(tunnel.bottom.vx <= -3) {
							tunnel.top.vx++;
							tunnel.bottom.vx++;
						}
						
						this.counterCollisionTunnel = 0;
					}
				}
			}
		}
	}
	
	private void collisionShip(Ship ship) {
		if(Utilities.distanceTwoPoints(this.x, this.y, ship.x, ship.y) <= this.r + ship.r) {
			double k = Math.abs(Utilities.distanceTwoPoints(this.x, this.y, ship.x, ship.y) - (this.r + ship.r));

			if(Math.abs(this.vx) >= 0.01) {
				double dx = this.vx / Math.sqrt(this.vx * this.vx + this.vy * this.vy);
				this.x = this.x - (k * dx * 1.5);
			}
			
			if(Math.abs(this.vy) >= 0.01) {
				double dy = this.vy / Math.sqrt(this.vx * this.vx + this.vy * this.vy);
				this.y = this.y - (k * dy * 1.5);
			}

			double shipVx = ship.vx;
			double shipVy = ship.vy;

			ship.vx = this.vx;
			ship.vy = this.vy;

			this.vx = shipVx;
			this.vy = shipVy;
		}
	}
	
	
	private void collisionMeteor(MeteorShawer meteors){
		for(int i = 1; i < meteors.meteorPool.size(); i++ ) {
			if( meteors.meteorPool.get(i).getAvailable() == false) {
				if(Utilities.distanceTwoPoints(this.x, this.y, meteors.meteorPool.get(i).x, meteors.meteorPool.get(i).y) <= this.r + meteors.meteorPool.get(i).size) {
					@SuppressWarnings("unused")
					double k = Math.abs(Utilities.distanceTwoPoints(this.x, this.y, meteors.meteorPool.get(i).x, meteors.meteorPool.get(i).y) - (this.r + meteors.meteorPool.get(i).size));
					
					if(!this.invincibility) {
						this.lives--;
						this.invincibility = true;
					}
						
					meteors.switchOff(i);
					this.remains.launch(this.x, this.y);
					this.vx = -Parameters.METEOR_MAX_SPEED/8 * meteors.meteorPool.size();
					
				}
			}
		}
		
	}
	
	
	protected void scoreCalculator(Graphics2D g) {
		Font myFont = new Font("Arial", Font.BOLD, 16);
		g.setFont(myFont);
		
		FontMetrics fm = g.getFontMetrics(myFont);
		int lengthStringScore = fm.stringWidth("Score : ");
		int lengthMyScore = fm.stringWidth("" + this.score + "");
		
		g.setColor(Parameters.DEFAULT_COLOR);
		
		if(this.informationsLeftOrRight) {
			g.drawString("Score : ", 10, Parameters.SCREEN_MAX_HEIGHT + this.informationsY + 25);
			g.setColor(this.color);
			g.drawString("" + this.score + "", 10 +lengthStringScore, Parameters.SCREEN_MAX_HEIGHT + this.informationsY + 25);
		} else {
			g.drawString("Score : ", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyScore-lengthStringScore, Parameters.SCREEN_MAX_HEIGHT + this.informationsY + 25);
			g.setColor(this.color);
			g.drawString("" + this.score + "", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyScore, Parameters.SCREEN_MAX_HEIGHT + this.informationsY + 25);
		}
		
		g.setColor(Parameters.DEFAULT_COLOR);
		
		if(Game.mainClock % 20 == 0) {
			if(this.x > 0){
			this.score += (int)(10 * (10 - ((Math.log((Parameters.SCREEN_MAX_WIDTH - this.x)) / Math.log(Parameters.SCREEN_MAX_WIDTH) * 10))));
			}
		}
	}

	public void finalScore(Graphics2D g, boolean leftOrRight){ 
		Font myFont = new Font("Arial", Font.BOLD, 16);
		g.setFont(myFont);
		
		FontMetrics fm = g.getFontMetrics(myFont);
		int lengthStringScore = fm.stringWidth("Score : ");
		int lengthMyScore = fm.stringWidth("" + this.score + " + " + this.lives + " x10");
		
		g.setColor(Parameters.DEFAULT_COLOR);
		
		if(leftOrRight) {
			g.drawString("Score : ", 10, Parameters.SCREEN_MAX_HEIGHT + 50);
			g.setColor(this.color);
			g.drawString("" + this.score + " + " + this.lives + " x10", 10 +lengthStringScore, Parameters.SCREEN_MAX_HEIGHT + 50);
		} else {
			g.drawString("Score : ", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyScore-lengthStringScore, Parameters.SCREEN_MAX_HEIGHT + 50);
			g.setColor(this.color);
			g.drawString("" + this.score + " + " + this.lives + " x10", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyScore, Parameters.SCREEN_MAX_HEIGHT + 50);
		}
		
		g.setColor(Parameters.DEFAULT_COLOR);
		
		if(Game.mainClock % 20 == 0) {
			this.score += (int)(10 * (11 - ((Math.log((Parameters.SCREEN_MAX_WIDTH - this.x)) / Math.log(Parameters.SCREEN_MAX_WIDTH) * 10))));
		}
		myFont = new Font("Arial", Font.BOLD, 16);
		g.setFont(myFont);
		
		fm = g.getFontMetrics(myFont);
		if(!this.isWinner){
			int lengthStringLives = fm.stringWidth("Lives : ");
			int lengthMyLives = fm.stringWidth(" LOOSE ");	
			g.setColor(Parameters.DEFAULT_COLOR);
			
			if(leftOrRight) {
				g.drawString("You : ", 10, Parameters.SCREEN_MAX_HEIGHT + 25);
				g.setColor(this.color);
				g.drawString("LOOSE", 10 +lengthStringLives, Parameters.SCREEN_MAX_HEIGHT + 25);
			} else {
				g.drawString("You : ", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyLives-lengthStringLives, Parameters.SCREEN_MAX_HEIGHT + 25);
				g.setColor(this.color);
				g.drawString("LOOSE", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyLives, Parameters.SCREEN_MAX_HEIGHT + 25);
			}
			
			g.setColor(Parameters.DEFAULT_COLOR);
		} else {
			int lengthStringLives = fm.stringWidth("Lives : ");
			int lengthMyLives = fm.stringWidth(" LOOSE ");	
			g.setColor(Parameters.DEFAULT_COLOR);
			
			if(leftOrRight) {
				g.drawString("You : ", 10, Parameters.SCREEN_MAX_HEIGHT + 25);
				g.setColor(this.color);
				g.drawString(" WIN ", 10 +lengthStringLives, Parameters.SCREEN_MAX_HEIGHT + 25);
			} else {
				g.drawString("You : ", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyLives-lengthStringLives, Parameters.SCREEN_MAX_HEIGHT + 25);
				g.setColor(this.color);
				g.drawString(" WIN ", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyLives, Parameters.SCREEN_MAX_HEIGHT + 25);
			}
			
		}
		
		
		
	}
	
	
	
	protected void checkLives(Graphics2D g) {
		Font myFont = new Font("Arial", Font.BOLD, 16);
		g.setFont(myFont);
		
		FontMetrics fm = g.getFontMetrics(myFont);
		int lengthStringLives = fm.stringWidth("Lives : ");
		int lengthMyLives = fm.stringWidth("" + this.lives + "");
		
		g.setColor(Parameters.DEFAULT_COLOR);
		
		if(this.informationsLeftOrRight) {
			g.drawString("Lives : ", 10, Parameters.SCREEN_MAX_HEIGHT + this.informationsY);
			g.setColor(this.color);
			g.drawString("" + this.lives + "", 10 +lengthStringLives, Parameters.SCREEN_MAX_HEIGHT + this.informationsY);
		} else {
			g.drawString("Lives : ", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyLives-lengthStringLives, Parameters.SCREEN_MAX_HEIGHT + this.informationsY);
			g.setColor(this.color);
			g.drawString("" + this.lives + "", Parameters.SCREEN_MAX_WIDTH - 10 -lengthMyLives, Parameters.SCREEN_MAX_HEIGHT + this.informationsY);
		}
		
		g.setColor(Parameters.DEFAULT_COLOR);
	}
	
	private void checkInvincibility() {
		if(this.invincibility) {
			if(this.invincibilityClock % 180 == 0) {
				this.invincibility = false;
				this.invincibilityClock = 0;
			}

			this.invincibilityClock++;
		}
	}
	
	protected void print(Graphics2D g) {
		Ellipse2D.Double shape = new Ellipse2D.Double(this.x-this.r, this.y-this.r, this.r*2, this.r*2);
		
		if(!this.invincibility) {
			g.setColor(this.color);
		} else {
			if(this.invincibilityClock % 10 >= 0 && this.invincibilityClock % 10 <= 3) {
				g.setColor(Parameters.BACKGROUND_COLOR);
			} else {
				g.setColor(this.color);
			}
		}
		
		g.fill(shape);
		g.setColor(Parameters.DEFAULT_COLOR);
		
		g.setStroke(new BasicStroke(3.2f));
		g.draw(shape);
	}

	
	public void setTrace(){
		for(int i = 0; i < this.flash.size(); i++){
			if(flash.get(i).getAvailable() == true){
				flash.get(i).setter(x, y, dx, dy);
				break;
			}
		}
	}
	
	
	public void timeToTrace(){
		if(Game.mainClock % 3 == 0){
			this.setTrace();
		}else{
			
		}
		
	}
	
	

	public void getVictory(){
		this.isWinner = true;
	}
	
public void controller(Tunnel tunnel, Ship[] allShips, MeteorShawer meteors, Graphics2D g, boolean leftOrRight) {

		this.drive();
		this.move();
		
		for(int i = 0, n = allShips.length ; i < n ; i++) {
			if(allShips[i] != this) {
				this.collisionShip(allShips[i]);
			}
		}
			
		this.remains.controller(g);
		this.collisionMeteor(meteors);
		this.collisionTunnel(tunnel);
		this.checkInvincibility();
		this.scoreCalculator(g);
		this.checkLives(g);
		
		for(int i = 0; i < this.flash.size(); i++){
			if(this.flash.get(i).getAvailable() == false){
				this.flash.get(i).grow();
				this.flash.get(i).draw(g);
				if(this.flash.get(i).size <= 0.2){ //whiteSize
					this.flash.get(i).stop();
				}
			}
		}
		this.print(g);
	}
	
}


class Trace {
	
	public double x, y, size;
	private final double sizeInit = 7;
	public boolean available;
	
	public Trace(){
		this.size = sizeInit;
		this.x = 0;
		this.y = 0;
		available= true;
	}
	
	public boolean getAvailable(){
		return this.available;
	}
	public void setter(double x, double y,double dx, double dy){
		available = false;
		this.x = x;
		this.y = y;
	}
	
	public void stop(){
		available = true;
		size = sizeInit;

		x = 0;
		y = 0;
	}
	
	public void draw(Graphics2D g){
		if(available == false){
			Ellipse2D.Double shape = new Ellipse2D.Double(this.x-this.size, this.y-this.size, this.size*2, this.size*2);
			g.setColor(Color.BLACK);
			g.fill(shape);
		}
	}
	
	public void grow(){
		size = size - 0.3;
	}

}
