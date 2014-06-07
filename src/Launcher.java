import javax.swing.*;

public class Launcher {

		JPanel panel;
		JFrame frame;
		int mode;
		public Launcher(JFrame frame, JPanel Panel, int mode ){
			this.panel = Panel;
			this.frame = frame;
			this.mode = mode;
			frame.getContentPane().add(panel);
			frame.setContentPane(panel);
			frame.setVisible(true);
			}
		public void loop(){
			
			while(Game.mode == this.mode) {
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
}
