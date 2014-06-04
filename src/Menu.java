import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JPanel {
	
	Tunnel tunnel = new Tunnel(Parameters.SCREEN_MAX_WIDTH, -4, 25, new Color(73,73,73), new Color(73,73,73));
	private JPanel activeLine;
	
	public Menu() {
		
		activeLine = buildGrid();
		this.add(activeLine);
		this.setAlignmentY(CENTER_ALIGNMENT);
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Parameters.DEFAULT_COLOR);
		
		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		
		tunnel.controller(g2d);

	}
	
	private JPanel buildGrid() {
		
		JPanel grid = new JPanel();
	    grid.setPreferredSize(new Dimension(400, 70));
	    grid.setBackground(new Color(1f,0f,0f,0 ));
	   
	    
	    GridLayout gr = new GridLayout(1,3);
	    gr.setHgap(10);
	    gr.setVgap(60);
	    
	    grid.setLayout(gr);
	    
	    grid.add(BuildLine());
	    grid.add(BuildLine());
	    grid.add(BuildLine());
	   
	    
	    return grid;
	
	}
	
	private Button BuildLine() {
		JPanel linePan = new JPanel();
		linePan.setPreferredSize(new Dimension(600, 200));
		linePan.setBackground(new Color((float)0.4,(float)0.6,0,1));
		linePan.setLayout(new BoxLayout(linePan, BoxLayout.X_AXIS));
		
		Button b1 = new Button("Jouer");
		b1.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										System.out.println("Hello");
									}
								}
							);
		Button b2 = new Button("Réseaux");
		b2.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										System.out.print("Hollé");
									}
								}
							);
		Button b3 = new Button("Quitter");
		b3.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										System.out.println("Holla");
									}
								}
							);
		linePan.add(b1);
		linePan.add(b2);
		linePan.add(b3);
		return b1;
		
	}
	
	
}
