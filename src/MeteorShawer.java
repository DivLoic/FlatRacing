import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;



class Meteor {
	
	protected double x;
	protected double y;
	private double vx;
	protected double size;
	
	private boolean available = true;
	

	public Meteor(double posX, double posY, double speedX){
		this.x = posX;
		this.y = posY;
		this.vx = speedX;

	}
	
	public void left(){
		x = x - vx;
	}
	
	public Meteor(double posX, double posY, double speedX, double size){
		this.x = posX;
		this.y = posY;
		this.vx = speedX;
		this.size = size;
	}
	
	public void moveY(){
		
	}
	
	public void moveX(double d){
		x = x + d;

	}
	
	public void draw(Graphics2D g) {
		Ellipse2D.Double shape = new Ellipse2D.Double(this.x-this.size, this.y-this.size, this.size*2, this.size*2);
		g.setColor(new Color(73,73,73));//RED  
		g.fill(shape);
		g.setColor(Parameters.DEFAULT_COLOR);
		
		g.setStroke(new BasicStroke(3.2f));
		g.draw(shape);
	}
	
	public void launch(Tunnel tube) {
		this.available = false;
		this.size = Parameters.METEOR_SIZE;
		this.x = Parameters.SCREEN_MAX_WIDTH;
		this.y = Math.random()*((tube.bottom.y[699] + 10 ) - tube.top.y[699] - 10) + tube.top.y[690] -10;
		
	}
	

	//SURCHARGE DE LA METHODE
	public void launch(double x, double y) {
		this.available = false;
		this.x = x;
		this.y = y;
	}
	

	public boolean getAvailable(){
		return this.available;
	}
	
	public void stop() {
		this.available = true;
	}
	
	public boolean collision(Tunnel tunnel) {
		int l = (int)Math.round(this.x);
		if(this.y <= tunnel.top.y[l] + this.size) {
			return true;
		} else if(this.y >= tunnel.bottom.y[l] - this.size) {
			return true;
		} else {
			return false;
		}
	}
	
	
}


public class MeteorShawer {

	protected ArrayList<Meteor> meteorPool = new ArrayList<Meteor>() ;
	
	public MeteorShawer() {
		for(int i= 0; i< 10; i++){
			this.meteorPool.add(new Meteor(0,0, Parameters.METEOR_MAX_SPEED ,0));
		}
	}
	
	private void switchOn(Tunnel tube, int i){
		meteorPool.get(i).launch(tube);
	}
	
	protected void switchOff(int i){
		meteorPool.get(i).stop();
	}
	
	public void controller(Tunnel tube, Graphics2D g) {
		if(Math.random()*10 > Parameters.METEOR_PROBA && Game.mainClock % 30 == 0) {

			for(int k = 0; k < this.meteorPool.size(); k++){
				if(meteorPool.get(k).getAvailable() == true){
					switchOn(tube, k);
					break;
				}
			}
		}
		
		for(int i = 1; i < meteorPool.size(); i++ ) {
			if(meteorPool.get(i).getAvailable() == false && meteorPool.get(i).x > 1) {
		
				meteorPool.get(i).draw(g);
				meteorPool.get(i).moveX( tube.top.vx - 1);
				if(meteorPool.get(i).x > Parameters.METEOR_SIZE) {
					if( meteorPool.get(i).collision(tube)){
						switchOff(i);
					}
				
				}
			} else if(meteorPool.get(i).getAvailable() == false && meteorPool.get(i).x <= 1) {
				switchOff(i);
			}
		}
	}
	
}
