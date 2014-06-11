import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;


class Remain {

	
	private double x;
	private double y;
	
	private double x2;
	private double y2;
	
	private double vx;
	private double vy;
	
	
	private boolean available = true;
	
	public Remain(){
		this.x = 0;
		this.y = 0;
		this.x2 = 0;
		this.y2 = 0;

		
	}
	
	public void explose(double x, double y){
		double s,z;
		if(Math.random() < 0.5) {
			s = 1;
		} else {
			s = -1;
		}
		if(Math.random() < 0.5) {
			z = 1;
		} else {
			z = -1;
		}
		this.vx =  s * Math.random()*((20-10)+10);
		this.vy =  z *Math.random()*((20-10)+10);
		this.available = false;
		this.x = x ;
		this.x2 = x;
		this.y = y ;
		this.y2 = y;


	}
	
	public void draw(Graphics2D g){
		g.setStroke(new BasicStroke(2.9f));
		//g.setColor(new Color(73,73,73));
		Path2D.Double border = new Path2D.Double();
		border.moveTo(this.x, this.y);
		
		border.lineTo(this.x2, this.y2);
		
		g.draw(border);
		g.setColor(Parameters.DEFAULT_COLOR);
	}
	
	public void spread() {
		if(Utilities.distanceTwoPoints(this.x, this.y, this.x2, this.y2) <= Parameters.REMAINS_SIZE) {
			this.x = this.x + vx;
			this.y = this.y +vy;
			this.x2 = this.x2 + vx/2;
			this.y2 = this.y2 + vy/2;
		} else {
			this.x = this.x + vx;
			this.y = this.y +vy;
			this.x2 = this.x2 + vx;
			this.y2 = this.y2 + vy;
		}
	}
	
	public void stop(){
		x = x2 = y = y2 = 0;
		vy = vx = 0 ;
		available = true;
		
	}
	
	public boolean getAvailable() {
		return this.available;
	}
	
	public boolean isOutSide(){
		if(this.x2 > Parameters.SCREEN_MAX_WIDTH){
			return true;
		} else if (this.x2 < 1) {
			return true;
		}
		
		if(this.y2 > Parameters.SCREEN_MAX_HEIGHT) {
			return true;
		} else if (this.y2 < 1) {
			return true;
		}
		
		return false;
	}
	
	
}

public class PoolRemains {
	
	private ArrayList<Remain> rem = new ArrayList<Remain>() ;
	
	public PoolRemains(){
		for(int i= 0; i< 60; i++){
			this.rem.add(new Remain());
		}
	}
	
	public void launch(double x ,double y) {
		int q = 0;
		for(int i = 0; i < rem.size(); i++){
			if(rem.get(i).getAvailable()){
				rem.get(i).explose(x,y);
				q++;
			}
			if(q >= Parameters.REMAINS_PER_IMPACT){
				break;
			}
		}
	}
	
	public void controller(Graphics2D g) {
		for(int i = 0; i < rem.size(); i++) {
			if(rem.get(i).isOutSide()){
				rem.get(i).stop();
			}
			if(!rem.get(i).getAvailable()){
				rem.get(i).draw(g);
				rem.get(i).spread();
			}
		}
		
	}
	
}
