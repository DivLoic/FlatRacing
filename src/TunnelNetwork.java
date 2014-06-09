import java.awt.Color;


public class TunnelNetwork extends Tunnel {

	public double[][] pointsTop;
	public double[][] pointsBottom;

	public int counterPoints;
	
	public TunnelNetwork(int size, int vx, double initSummit, Color topColor, Color bottomColor, int minThreshold, double[][] pointsTop, double[][] pointsBottom) {
		super(size, vx, initSummit, topColor, bottomColor, minThreshold);
		
		this.pointsTop = pointsTop;
		this.pointsBottom = pointsBottom;

		this.counterPoints = 0;
	}
	
	
	public static double[][] randomPointsStatic() {
		int randomX = Utilities.randomIntFromInterval(Parameters.SCREEN_MAX_WIDTH / 6, Parameters.SCREEN_MAX_WIDTH / 2);
		
		int randomMasterY = Utilities.randomIntFromInterval(0, Parameters.SCREEN_MAX_HEIGHT - Parameters.MIN_THRESHOLD);
		int randomY = Utilities.randomIntFromInterval(randomMasterY + Parameters.MIN_THRESHOLD, Math.min(Parameters.SCREEN_MAX_HEIGHT, randomMasterY + Parameters.MAX_THRESHOLD));
		
		double[][] points = {{randomX, randomMasterY}, {randomX, randomY}};
		return points;
	}

	public static void initPoints(double[][] pointsTop, double[][] pointsBottom)
	{
		for(int i = 0, n = pointsTop.length ; i < n ; i++) {
			double[][] values = TunnelNetwork.randomPointsStatic();
			pointsTop[i] = values[0];
			pointsBottom[i] = values[1];
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

			double[][] newTargetPoints = {this.pointsTop[counterPoints], this.pointsBottom[counterPoints]};
			this.counterPoints++;

			this.top.targetPoint = newTargetPoints[0];
			this.bottom.targetPoint = newTargetPoints[1];

			this.counterPeriod = 0;
		}
		
		double[] values = {topValue, bottomValue};

		return values;
	}
	
}