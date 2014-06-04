
import java.awt.event.KeyEvent;
import java.util.Hashtable;


import javax.swing.JFrame;

public class Keyboard extends JFrame  {
	
	private Hashtable<Integer, Boolean> keys; 

	public Keyboard() {
		keys  = new Hashtable<Integer ,Boolean>();
		keys.put(KeyEvent.VK_LEFT, false);
		keys.put(KeyEvent.VK_RIGHT, false);
		keys.put(KeyEvent.VK_ENTER, false);
	}
	
	public void addKey(int asc) {
		this.keys.put(asc, false);
	}
	public boolean getMove(Integer i) {
		return keys.get(i);
	}
	
	public void press(KeyEvent event) {
		directionMapper(event,true);
				
	}

	public void release(KeyEvent event) {
		directionMapper(event,false);
	}
	
	public  void directionMapper(KeyEvent event, boolean status){
		
		for(Integer e : keys.keySet()){
			if(event.getKeyCode() == e) {
				keys.put(e, status);
			}
		}
		
	}

}
