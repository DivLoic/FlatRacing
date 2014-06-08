import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;


class PieceOfTunnel {
	
	public int size;
	public int vx;

	public double[] x;
	public double[] y;

	public double oldSummit;
	public double[] targetPoint;
	
	public double ground;
	
	public Color color;
	
	public PieceOfTunnel(int size, int vx, double initSummit, double ground, Color color) {
		this.size = size + 1;
		this.vx = vx;
		
		this.x = new double[this.size];
		this.y = new double[this.size];
		
		this.oldSummit = initSummit;
		this.targetPoint = new double[]{150, initSummit};
		
		this.ground = ground;
		
		this.color = color;
		
		for(int i = 0 ; i < this.size ; i ++) {
			this.x[i] = i;
			this.y[i] = initSummit;
		}
	}
	
	
	
	public void print(Graphics2D g) {
		Path2D.Double polygon = new Path2D.Double();
		polygon.moveTo(this.x[0], this.y[0]);
		
		for(int i = 1 ; i < this.size ; i++) {
			polygon.lineTo(this.x[i], this.y[i]);
		}
		
		polygon.lineTo(this.x[this.size-1], this.ground);
		polygon.lineTo(this.x[0], this.ground);
		polygon.lineTo(this.x[0], this.y[0]);
		polygon.closePath();
		
		g.setColor(this.color);
		g.fill(polygon);
		g.setColor(Parameters.DEFAULT_COLOR);
		
		g.setStroke(new BasicStroke(3.6f));
		Path2D.Double border = new Path2D.Double();
		border.moveTo(this.x[0], this.y[0]);
		
		for(int i = 1 ; i < this.size ; i++) {
			border.lineTo(this.x[i], this.y[i]);
		}

		g.draw(border);
	}
	
	public void move() {
		for(int i = 0 ; i < this.size ; i++) {
			this.x[i] += this.vx * Parameters.DT;
		}
		
		if(Game.mainClock % (60 * 20) == 0) {
			if(this.vx >= -6) {
				this.vx--;
			}
		}
	}
	
}





public class Tunnel {
	
	public PieceOfTunnel top;
	public PieceOfTunnel bottom;
	
	public int counterPeriod;
	public int minThreshold;
	
	public Tunnel(int size, int vx, double initSummit, Color topColor, Color bottomColor, int minThreshold) {
		this.top = new PieceOfTunnel(size, vx, initSummit, 0, topColor);
		this.bottom = new PieceOfTunnel(size, vx, Parameters.SCREEN_MAX_HEIGHT-initSummit, Parameters.SCREEN_MAX_HEIGHT, bottomColor);
		
		this.counterPeriod = 0;
		this.minThreshold = minThreshold;
	}
	
	
	
	public void print(Graphics2D g) {
		this.top.print(g);
		this.bottom.print(g);
	}
	
	public void move() {
		this.top.move();
		this.bottom.move();
	}
	
	public void arrange() {
		int counterHidden = 0;
		
		for(int i = 0 ; i < this.top.size ; i++) {
			if(this.top.x[i] < 0) {
				counterHidden++;
			}
		}
		
		for(int i = 0 ; i < this.top.size - counterHidden ; i++) {
			this.top.x[i] = this.top.x[i+counterHidden];
			this.top.y[i] = this.top.y[i+counterHidden];

			this.bottom.x[i] = this.bottom.x[i+counterHidden];
			this.bottom.y[i] = this.bottom.y[i+counterHidden];
		}
		
		for(int i = this.top.size - counterHidden ; i < this.top.size ; i++) {
			double[] heights = this.buildHeights();

			this.top.x[i] = i;
			this.top.y[i] = heights[0];

			this.bottom.x[i] = i;
			this.bottom.y[i] = heights[1];
		}
	}
	
	public double[][] randomPoints() {
		int randomX = Utilities.randomIntFromInterval(Parameters.SCREEN_MAX_WIDTH / 6, Parameters.SCREEN_MAX_WIDTH / 2);
		
		int randomMasterY = Utilities.randomIntFromInterval(0, Parameters.SCREEN_MAX_HEIGHT - this.minThreshold );
		int randomY = Utilities.randomIntFromInterval(randomMasterY + this.minThreshold , Math.min(Parameters.SCREEN_MAX_HEIGHT, randomMasterY + Parameters.MAX_THRESHOLD));
		
		double[][] points = {{randomX, randomMasterY}, {randomX, randomY}};
		return points;
	}
	
	public double sinusTechnology(double oldSummit, double[] targetPoint, int t) {
		double targetPointX = targetPoint[0];
		double targetPointY = targetPoint[1];

		if(oldSummit <= targetPointY) {
			return (targetPointY - oldSummit) * 0.5 * (Math.sin(Math.PI / targetPointX * t - Math.PI/2) + 1) + oldSummit;
		} else {
			return (oldSummit - targetPointY) * 0.5 * (Math.sin(Math.PI / targetPointX * t + Math.PI/2) + 1) + targetPoint[1];
		}
	}
	
	public double[] buildHeights() {
		double topValue = this.sinusTechnology(this.top.oldSummit, this.top.targetPoint, this.counterPeriod);
		double bottomValue = this.sinusTechnology(this.bottom.oldSummit, this.bottom.targetPoint, this.counterPeriod);
		
		if(this.counterPeriod < this.top.targetPoint[0]) {
			this.counterPeriod++;
		} else {
			this.top.oldSummit = this.top.targetPoint[1];
			this.bottom.oldSummit = this.bottom.targetPoint[1];

			double[][] newTargetPoints = this.randomPoints();
			this.top.targetPoint = newTargetPoints[0];
			this.bottom.targetPoint = newTargetPoints[1];

			this.counterPeriod = 0;
		}
		
		double[] values = {topValue, bottomValue};

		return values;
	}
	
	public void controller(Graphics2D g) {
		this.print(g);
		this.move();
		this.arrange();
	}
}
