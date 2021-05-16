package dtr.ui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MyPanel extends JPanel{
	
	BufferedImage img;
	String img1 ="/imagini/img1.jpg";
	String img2 = "/imagini/img2.jpg";
	boolean evenClick=false;
	
	public MyPanel(){
		setSize(600,400);
		setVisible(true);
		loadImage(img1);
		
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me){
				if(evenClick){
					loadImage(img1);
					evenClick=false;
				}
				else{
					loadImage(img2);
					evenClick=true;
				}
				
				updateUI();
			}
		});
	}
	
	private void loadImage(String str){
		try {
			img=ImageIO.read(MyPanel.class.getResource(str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, 0, 600, 400, this);
		
	}

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame frm = new JFrame();
				frm.setSize(600,400);
				frm.setVisible(true);
				frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frm.add(new MyPanel());
				
				
			}
		});

	}

}
