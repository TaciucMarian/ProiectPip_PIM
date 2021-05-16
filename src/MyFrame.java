import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class MyFrame extends JFrame implements ActionListener{


	JButton button;
	JLabel label;
	
	MyFrame(){		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		
		button = new JButton("Alege culoarea:");
		button.addActionListener(this);
		
		label = new JLabel();
		label.setBackground(Color.MAGENTA);
		label.setOpaque(true);
		
		label.setText("text");
		label.setFont(new Font("",Font.CENTER_BASELINE,150));
		
		this.add(button);
		this.add(label);
		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		if (e.getSource()==button){
			
			//partea de color chooser care ne intereseaza
			
			JColorChooser colorChooser = new JColorChooser();
			
			Color color =JColorChooser.showDialog(null, "Alege o culoare", Color.white); // showDialog(component,title,initialColor)
			
			label.setForeground(color); // schimba culoarea textului(foreground) cu cel mai recent color selectat din ColorChooser
			//label.setBackground(color); 
		}
		
		
	}
}
