import java.awt.Color;


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
	}
	
	public void drive() {
		if(StdDraw.isKeyPressed(this.keyRight)) {
			if(this.vx + this.ax * Parameters.DT < this.vxMax) {
				this.vx += this.ax * Parameters.DT;
			} else {
				this.vx = this.vxMax;
			}
		}
		
		if(StdDraw.isKeyPressed(this.keyLeft)) {
			if(this.vx - this.ax * Parameters.DT > -this.vxMax) {
				this.vx -= this.ax * Parameters.DT;
			} else {
				this.vx = -this.vxMax;
			}
		}
		
		if(StdDraw.isKeyPressed(this.keyUp)) {
			if(this.vy + this.ay * Parameters.DT < this.vyMax) {
				this.vy += this.ay * Parameters.DT;
			} else {
				this.vy = this.vyMax;
			}
		}
		
		if(StdDraw.isKeyPressed(this.keyDown)) {
			if(this.vy - this.ay * Parameters.DT > -this.vyMax) {
				this.vy -= this.ay * Parameters.DT;
			} else {
				this.vy = -this.vyMax;
			}
		}
	}
	
	public void move() {
		this.x += this.vx * Parameters.DT;
		this.y += this.vy * Parameters.DT;
		
		this.vx *= this.frictX;
		this.vy *= this.frictY;
	}
	
	public void print() {
		StdDraw.setPenColor(this.color);
		StdDraw.filledCircle(this.x, this.y, this.r);
		StdDraw.setPenColor();
	}
	
	public void controller() {
		this.drive();
		this.move();
		this.print();
	}
	
}
