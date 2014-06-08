

import javax.swing.*;

public class Launcher {

		FlatPanel panel;
		JFrame frame;
		int mode;
		public Launcher(JFrame frame, FlatPanel Panel, int mode ){
			this.panel = Panel;
			this.frame = frame;
			this.mode = mode;
			Game.gameBreak = false;
			frame.getContentPane().add(panel);
			frame.setContentPane(panel);
			frame.setVisible(true);
			}
		
		public void loop(){
			
			if(!panel.whithBreak) {
				while(Game.mode == this.mode ) {
					this.action();
				}
			} else {
				while(Game.mode == this.mode ) {
				 if(!Game.gameBreak ){
					this.action();
				 	}
				}
			}
			
			
		}
		
		public void action() {
			long startTime = System.currentTimeMillis();
			panel.repaint();
			long endTime = System.currentTimeMillis();
			long processTime = endTime - startTime > 1000/60 ? 1000/60 : endTime - startTime;
			
			try {
				Thread.sleep((1000/60) - processTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
}
