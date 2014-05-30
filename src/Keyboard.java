import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;


public class Keyboard extends KeyAdapter {
	
	public Hashtable<Integer, Boolean> keyStatus = new Hashtable<Integer, Boolean>();
	
	public Keyboard() {
		this.keyStatus.put(KeyEvent.VK_RIGHT, false);
		this.keyStatus.put(KeyEvent.VK_LEFT, false);
		this.keyStatus.put(KeyEvent.VK_UP, false);
		this.keyStatus.put(KeyEvent.VK_DOWN, false);
		
		this.keyStatus.put(KeyEvent.VK_D, false);
		this.keyStatus.put(KeyEvent.VK_Q, false);
		this.keyStatus.put(KeyEvent.VK_Z, false);
		this.keyStatus.put(KeyEvent.VK_S, false);
	}
	
	
	
	public boolean isKeyPressed(int keyCode) {
		return this.keyStatus.get(keyCode);
	}

}
