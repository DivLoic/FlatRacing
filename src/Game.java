import javax.swing.JFrame;


public class Game extends JFrame {
	
	public Game(int width, int height) {
		this.setTitle("FlatRacing");
		this.setSize(width, height);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	private void launchTwoPlayers() {
		TwoPlayers subGame = new TwoPlayers();
		this.setContentPane(subGame);
		this.setVisible(true);
		
		while(true) {
			long startTime = System.currentTimeMillis();
			subGame.repaint();
			long endTime = System.currentTimeMillis();
			long processTime = endTime - startTime > 1000/60 ? 1000/60 : endTime - startTime;
			
			try {
				Thread.sleep((1000/60) - processTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		
		Game FlatRacing = new Game(Parameters.SCREEN_MAX_WIDTH, Parameters.SCREEN_MAX_HEIGHT);
		
		FlatRacing.launchTwoPlayers();
		
	}

}
