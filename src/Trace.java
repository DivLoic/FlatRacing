import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;


public class Trace {
	
	public double blackX, blackY, blackSize, whiteX, whiteY, whiteSize;
	private final double sizeInit = 7;
	public boolean available;
	
	public Trace(){
		blackSize = sizeInit;
		whiteSize = sizeInit;
		blackX = 0;
		blackY = 0;
		whiteX = 0;
		whiteY = 0;
		available= true;
	}
	
	public boolean getAvailable(){
		return this.available;
	}
	public void setter(double x, double y,double dx, double dy){
		available = false;
		blackX = x;
		blackY = y;
		whiteX = x;// +dx;
		whiteY = y;// +dy;
	}
	
	public void stop(){
		available = true;
		blackSize = sizeInit;
		whiteSize = sizeInit;
		blackX = 0;
		blackY = 0;
		whiteX = 0;
		whiteY = 0;
	}
	
	public void draw(Graphics2D g){
		if(available == false){
			Ellipse2D.Double shape = new Ellipse2D.Double(this.blackX-this.blackSize, this.blackY-this.blackSize, this.blackSize*2, this.blackSize*2);
			g.setColor(Color.BLACK);
			g.fill(shape);
		}
	}
	
	public void grow(){
		blackSize = blackSize - 0.3;
	}

}
