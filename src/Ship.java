import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Ship {
	
	private double x;
	private double y;
	
	private double vx;
	private double vy;
	private double vxMax;
	private double vyMax;
	
	private double ax;
	private double ay;
	
	private double frictX;
	private double frictY;
	
	private double r;
	private Color color;
	
	private int lives;
	private int score;

	private boolean invincibility;
	private int invincibilityClock;
	
	private int[] keytab; 
	
	
	public Ship(double x, double y , double vx, double vy, double vxMax, double vyMax, double ax, double ay, double frictX, double frictY, double r, Color color, int lives, int[] keytab) {
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
		
		this.keytab = keytab;
		
		for(int code : keytab) {
			Game.joystick.addKey(code);
		}
	}
	
	
	
	private void drive() {
		if(Game.joystick.getMove(keytab[1])) {//RIGHT
			if(this.vx + this.ax * Parameters.DT < this.vxMax) {
				this.vx += this.ax * Parameters.DT;
			} else {
				this.vx = this.vxMax;
			}
		}
		
		if(Game.joystick.getMove(keytab[3])) {//LEFT
			if(this.vx - this.ax * Parameters.DT > -this.vxMax) {
				this.vx -= this.ax * Parameters.DT;
			} else {
				this.vx = -this.vxMax;
			}
		}
		
		if(Game.joystick.getMove(keytab[2])) {// DOWN
			if(this.vy + this.ay * Parameters.DT < this.vyMax) {
				this.vy += this.ay * Parameters.DT;
			} else {
				this.vy = this.vyMax;
			}
		}
		
		if(Game.joystick.getMove(keytab[0])) {// UP
			if(this.vy - this.ay * Parameters.DT > -this.vyMax) {
				this.vy -= this.ay * Parameters.DT;
			} else {
				this.vy = -this.vyMax;
			}
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
			}
		} else if(this.y >= tunnel.bottom.y[l] - this.r) {
			double a = (tunnel.bottom.y[n-1] - tunnel.bottom.y[m]) / (n-m);

			this.vy = tunnel.bottom.vx;
			this.vx = -tunnel.bottom.vx * a;

			this.y = tunnel.bottom.y[l] - this.r;
			
			if(!this.invincibility) {
				this.lives--;
				this.invincibility = true;
			}
		}
	}
	
	private void collisionShip(Ship ship) {
		if(Utilities.distanceTwoPoints(this.x, this.y, ship.x, ship.y) <= this.r + ship.r) {
			double k = Math.abs(Utilities.distanceTwoPoints(this.x, this.y, ship.x, ship.y) - (this.r + ship.r));

			if(Math.abs(this.vx) >= 0.00001)
			{
				double dx = this.vx / Math.sqrt(this.vx * this.vx + this.vy * this.vy);
				this.x = this.x - (k * dx * 1.7);
			}
			
			if(Math.abs(this.vy) >= 0.00001)
			{
				double dy = this.vy / Math.sqrt(this.vx * this.vx + this.vy * this.vy);
				this.y = this.y - (k * dy * 1.7);
			}

			double shipVx = ship.vx;
			double shipVy = ship.vy;

			ship.vx = this.vx;
			ship.vy = this.vy;

			this.vx = shipVx;
			this.vy = shipVy;
		}
	}
	
	private void scoreCalculator() {
		if(Game.mainClock % 20 == 0) {
			this.score += (int)(10 * (11 - ((Math.log((Parameters.SCREEN_MAX_WIDTH - this.x)) / Math.log(Parameters.SCREEN_MAX_WIDTH) * 10))));
		}
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
	
	private void print(Graphics2D g) {
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
	
	public void controller(Tunnel tunnel, Ship ship, Graphics2D g) {
		this.drive();
		this.move();
		this.collisionShip(ship);
		this.collisionTunnel(tunnel);
		this.checkInvincibility();
		this.scoreCalculator();
		this.print(g);
	}
	
}
