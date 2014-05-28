import java.awt.Color;
import java.awt.event.KeyEvent;


class PieceOfTunnel {
	
	public int size;
	public int vx;

	public double[] x;
	public double[] y;

	public double oldSummit;
	public double[] targetPoint;
	
	public Color color;
	
	public PieceOfTunnel(int size, int vx, double initSummit, double ground, Color color) {
		this.size = size + 1;
		this.vx = vx;
		
		this.x = new double[this.size+2];
		this.y = new double[this.size+2];
		
		this.oldSummit = initSummit;
		this.targetPoint = new double[]{1, initSummit};
		
		this.color = color;
		
		for(int i = 0 ; i < this.size ; i ++) {
			this.x[i] = i;
			this.y[i] = initSummit;
		}
		
		this.x[this.size] = this.x[this.size-1];
		this.y[this.size] = ground;
		
		this.x[this.size+1] = this.x[0];
		this.y[this.size+1] = ground;
	}
	
	
	
	public void print() {
		StdDraw.setPenColor(this.color);
		StdDraw.filledPolygon(this.x, this.y);
		StdDraw.setPenColor();
	}
	
	public void move() {
		for(int i = 0 ; i < this.size ; i++) {
			this.x[i] += this.vx * Parameters.DT;
		}
	}
	
}





public class Tunnel {
	
	public PieceOfTunnel bottom;
	public PieceOfTunnel top;
	
	public int counterPeriod;
	
	public Tunnel(int size, int vx, double initSummit, Color bottomColor, Color topColor) {
		this.bottom = new PieceOfTunnel(size, vx, initSummit, 0, bottomColor);
		this.top = new PieceOfTunnel(size, vx, Parameters.SCREEN_MAX_HEIGHT-initSummit, Parameters.SCREEN_MAX_HEIGHT, topColor);
		
		this.counterPeriod = 0;
	}
	
	
	
	public void print() {
		this.bottom.print();
		this.top.print();
	}
	
	public void move() {
		this.bottom.move();
		this.top.move();
	}
	
	public void arrange() {
		int counterHidden = 0;
		
		for(int i = 0 ; i < this.bottom.size ; i++) {
			if(this.bottom.x[i] < 0) {
				counterHidden++;
			}
		}
		
		for(int i = 0 ; i < this.bottom.size - counterHidden ; i++) {
			this.bottom.x[i] = this.bottom.x[i+counterHidden];
			this.bottom.y[i] = this.bottom.y[i+counterHidden];

			this.top.x[i] = this.top.x[i+counterHidden];
			this.top.y[i] = this.top.y[i+counterHidden];
		}
		
		for(int i = this.bottom.size - counterHidden ; i < this.bottom.size ; i++) {
			double[] heights = this.buildHeights();

			this.bottom.x[i] = i;
			this.bottom.y[i] = heights[0];

			this.top.x[i] = i;
			this.top.y[i] = heights[1];
		}
	}
	
	public double[][] randomPoints() {
		int randomX = Utilities.randomIntFromInterval(Parameters.SCREEN_MAX_WIDTH / 6, Parameters.SCREEN_MAX_WIDTH / 2);
		
		int randomMasterY = Utilities.randomIntFromInterval(0, Parameters.SCREEN_MAX_HEIGHT - Parameters.MIN_THRESHOLD);
		int randomY = Utilities.randomIntFromInterval(randomMasterY + Parameters.MIN_THRESHOLD, Math.min(Parameters.SCREEN_MAX_HEIGHT, randomMasterY + Parameters.MAX_THRESHOLD));
		
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
		double bottomValue = this.sinusTechnology(this.bottom.oldSummit, this.bottom.targetPoint, this.counterPeriod);
		double topValue = this.sinusTechnology(this.top.oldSummit, this.top.targetPoint, this.counterPeriod);
		
		if(this.counterPeriod < this.bottom.targetPoint[0]) {
			this.counterPeriod++;
		} else {
			this.bottom.oldSummit = this.bottom.targetPoint[1];
			this.top.oldSummit = this.top.targetPoint[1];

			double[][] newTargetPoints = this.randomPoints();
			this.bottom.targetPoint = newTargetPoints[0];
			this.top.targetPoint = newTargetPoints[1];

			this.counterPeriod = 0;
		}
		
		double[] values = {bottomValue, topValue};

		return values;
	}
	
	public void controller() {
		this.print();
		this.move();
		this.arrange();
		
		if(StdDraw.isKeyPressed(KeyEvent.VK_X))
		{
			this.top.vx--;
			this.bottom.vx--;
		}

		if(StdDraw.isKeyPressed(KeyEvent.VK_W))
		{
			if(this.top.vx < 0)
			{
				this.top.vx++;
				this.bottom.vx++;
			}
		}
	}
}
