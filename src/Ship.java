import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.*;

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
	
	private int keyRight;
	private int keyLeft;
	private int keyUp;
	private int keyDown;
	
	private Hashtable<String, Boolean> joystick; 
	
	
	public Ship(double x, double y , double vx, double vy, double vxMax, double vyMax, double ax, double ay, double frictX, double frictY, double r, Color color, int keyRight, int keyLeft, int keyUp, int keyDown) {
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
		
		this.keyRight = keyRight;
		this.keyLeft = keyLeft;
		this.keyUp = keyUp;
		this.keyDown = keyDown;
		
		this.joystick = Game.joystick1;
	}
	
	
	
	private void drive() {
		if(this.joystick.get("right")) {
			if(this.vx + this.ax * Parameters.DT < this.vxMax) {
				this.vx += this.ax * Parameters.DT;
			} else {
				this.vx = this.vxMax;
			}
		}
		
		if(this.joystick.get("left")) {
			if(this.vx - this.ax * Parameters.DT > -this.vxMax) {
				this.vx -= this.ax * Parameters.DT;
			} else {
				this.vx = -this.vxMax;
			}
		}
		
		if(this.joystick.get("down")) {
			if(this.vy + this.ay * Parameters.DT < this.vyMax) {
				this.vy += this.ay * Parameters.DT;
			} else {
				this.vy = this.vyMax;
			}
		}
		
		if(this.joystick.get("up")) {
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
	
	private void print(Graphics2D g) {
		Ellipse2D.Double shape = new Ellipse2D.Double(this.x-this.r, this.y-this.r, this.r*2, this.r*2);
		
		g.setColor(this.color);
		g.fill(shape);
		g.setColor(Parameters.DEFAULT_COLOR);
	}
	
	public void controller(Graphics2D g) {
		this.drive();
		this.move();
		this.print(g);
	}
	
}
