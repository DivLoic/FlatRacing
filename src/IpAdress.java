

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class IpAdress extends JFrame implements Runnable{
	
	private JPanel container = new JPanel();
	private JTextField jtf = new JTextField("000,000,000,000");
	private static JLabel label = new JLabel("Exemple");
	private JButton b = new JButton ("Connection");  
	private String regex = ",";
	private int[] result = new int[4];
	private boolean parsed = true;
	
	
	public IpAdress() {
		this.setTitle("Indiquez l'adresse Ip du serveur");
	    this.setSize(500, 100);
	    Toolkit.getDefaultToolkit().getScreenSize();
	    this.setLocation(100, 200);
	    //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    container.setBackground(Color.white);
	    container.setLayout(new BorderLayout());
	    JPanel top = new JPanel();
	    Font police = new Font("Arial", Font.BOLD, 14);
	    jtf.setFont(police);
	    jtf.setPreferredSize(new Dimension(150, 30));
	    jtf.setForeground(new Color(176,95,35));
	    top.add(label);
	    top.add(jtf);
	    container.add(top, BorderLayout.NORTH);
	    b.addActionListener(new BoutonListener());
	    container.add(b);
	    this.setContentPane(container);
	    this.setVisible(true);

	}
	
	 class BoutonListener implements ActionListener{
		    public void actionPerformed(ActionEvent e) {
		    	String Adrs = jtf.getText();
		    	String byteAdrs[] = Adrs.split(regex);
		    	
		    	for(int x = 0; x < byteAdrs.length; x++) {
		    		try{
		    		result[x] =  Integer.parseInt(byteAdrs[x]);
		    		} catch(NumberFormatException w) {
		    			reminder();
		    			w.printStackTrace();
		    			
		    		}
		    		
		    	}
		    	if(byteAdrs.length != 4){
		    		reminder();
		    	}
		    	if(parsed) {
		    		Thread tCo = new Thread(new Connection(result));
		    		tCo.start();
		    		setLabel("Waiting for connection ...");
		    	}
		    	
		    }
	 }
	 
	public void reminder() {// A revoir
		parsed = false;
		jtf.setText("000,000,000,000");
		label.setForeground(new Color(147,76,147));
		//label.setForeground(Color.BLACK);	
		 b.addActionListener(new BoutonListener());
	}
	
	public static void setLabel(String str){
		label.setText(str);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			
		}
	}
}
