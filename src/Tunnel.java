import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

class PieceOfTunnel {
	
	private double x;
	private double y;
	
	private double width;
	private double height;
	
	private double vx;
	
	private boolean available;
	
	public PieceOfTunnel(double x, double y, double width, double height, double vx, boolean available) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.vx = vx;
		
		this.available = available;
	}
	
	
	public double getX() {
		return this.x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
	public boolean move() {
		this.x += this.vx * Parameters.DT;

		if(this.x < 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void print() {
		StdDraw.filledRectangle(this.x + this.width/2, this.y + this.height/2, this.width/2, this.height/2);
	}
	
}




public class Tunnel {
	
	private int maxSize;
	
	private ArrayList<PieceOfTunnel> bottomPieces, topPieces;
	
	private double bottomPointsOldSummit, topPointsOldSummit;
	private double[] bottomPointsTargetPoint, topPointsTargetPoint;
	
	private int counterPeriod;
	
	private Color color;
	
	public Tunnel(int maxSize, double initSummit, double vx, Color color) {
		this.maxSize = maxSize;
		
		this.bottomPieces = new ArrayList<PieceOfTunnel>();
		this.topPieces = new ArrayList<PieceOfTunnel>();
		
		this.bottomPointsOldSummit = this.topPointsOldSummit = initSummit;
		this.bottomPointsTargetPoint = new double[]{1, initSummit};
		this.topPointsTargetPoint = new double[]{1, Parameters.SCREEN_HEIGHT - initSummit};
		
		this.counterPeriod = 0;
		
		this.color = color;
		
		for(int i = 0 ; i < this.maxSize ; i++) {
			bottomPieces.add(new PieceOfTunnel(i, 0, 1, initSummit, -vx, true));
			topPieces.add(new PieceOfTunnel(i, Parameters.SCREEN_HEIGHT-initSummit, 1, initSummit, -vx, true));
		}
	}
	
	public void print() {
		StdDraw.setPenColor(this.color);
		
		for(int i = 0 ; i < this.maxSize ; i++) {
			if(!this.bottomPieces.get(i).isAvailable() && !this.topPieces.get(i).isAvailable()) {
				this.bottomPieces.get(i).print();
				this.topPieces.get(i).print();
			}
		}
		
		StdDraw.setPenColor();
	}
	
	public void move() {
		for(int i = 0 ; i < this.maxSize ; i++) {
			this.bottomPieces.get(i).setAvailable(this.bottomPieces.get(i).move());
			this.topPieces.get(i).setAvailable(this.topPieces.get(i).move());
		}
	}
	
	public void arrange() {
		int counterAvailable = 0;
		
		for(int i = 0 ; i < this.maxSize ; i++) {
			if(this.bottomPieces.get(i).isAvailable() && this.topPieces.get(i).isAvailable()) {
				this.bottomPieces.get(i).setAvailable(false);
				this.topPieces.get(i).setAvailable(false);
				
				counterAvailable++;
			} else {
				break;
			}
		}
		
		
		
		List<PieceOfTunnel> bottomNewPieces = this.bottomPieces.subList(0, counterAvailable);
		List<PieceOfTunnel> topNewPieces = this.topPieces.subList(0, counterAvailable);
		
		this.bottomPieces.subList(0, counterAvailable).clear();
		this.topPieces.subList(0, counterAvailable).clear();
		
		
		
		for(int i = 0 ; i < counterAvailable ; i++) {
			double[] buildPieces = this.buildPieces();

			bottomNewPieces.get(i).setX(this.maxSize - counterAvailable + i);
			bottomNewPieces.get(i).setHeight(buildPieces[0]);

			topNewPieces.get(i).setX(this.maxSize - counterAvailable + i);
			topNewPieces.get(i).setY(buildPieces[1]);
			topNewPieces.get(i).setHeight(Parameters.SCREEN_HEIGHT - topNewPieces.get(i).getY());
			
			this.bottomPieces.add(bottomNewPieces.get(i));
			this.topPieces.add(topNewPieces.get(i));
		}
	}
	
	public double[][] randomPoints() {
		int randomX = Utilities.randomIntFromInterval(Parameters.SCREEN_WIDTH / 6, Parameters.SCREEN_WIDTH / 2);
		
		int randomMasterY = Utilities.randomIntFromInterval(0, Parameters.SCREEN_HEIGHT - Parameters.MIN_THRESHOLD);
		int randomY = Utilities.randomIntFromInterval(randomMasterY + Parameters.MIN_THRESHOLD, Math.min(Parameters.SCREEN_HEIGHT, randomMasterY + Parameters.MAX_THRESHOLD));
		
		double[][] points = {{randomX, randomMasterY}, {randomX, randomY}};
		return points;
	}
	
	public double sinusTunnel(double oldSummit, double[] targetPoint, int t) {
		double targetPointX = targetPoint[0];
		double targetPointY = targetPoint[1];

		if(oldSummit <= targetPointY) {
			return (targetPointY - oldSummit) * 0.5 * (Math.sin(Math.PI / targetPointX * t - Math.PI/2) + 1) + oldSummit;
		} else {
			return (oldSummit - targetPointY) * 0.5 * (Math.sin(Math.PI / targetPointX * t + Math.PI/2) + 1) + targetPoint[1];
		}
	}
	
	public double[] buildPieces() {
		double bottomValue = this.sinusTunnel(this.bottomPointsOldSummit, this.bottomPointsTargetPoint, this.counterPeriod);
		double topValue = this.sinusTunnel(this.topPointsOldSummit, this.topPointsTargetPoint, this.counterPeriod);
		
		if(this.counterPeriod < this.bottomPointsTargetPoint[0] && this.counterPeriod < this.topPointsTargetPoint[0]) {
			this.counterPeriod++;
		} else {
			this.bottomPointsOldSummit = this.bottomPointsTargetPoint[1];
			this.topPointsOldSummit = this.topPointsTargetPoint[1];

			double[][] newTargetPoints = this.randomPoints();
			this.bottomPointsTargetPoint = newTargetPoints[0];
			this.topPointsTargetPoint = newTargetPoints[1];

			this.counterPeriod = 0;
		}
		
		double[] values = {bottomValue, topValue};

		return values;
	}
	
	public void controller() {
		this.print();
		this.move();
		this.arrange();
	}
	
}
