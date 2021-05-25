package proiectPip;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
public class gui extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private static final boolean True = false;
	private JFrame frame;
	private JTextField txtTextAdnotare;
	private JPanel panel;
	private Button button_1;
	private JComboBox<String> comboBox;
	private JTree tree;
	private JTree tree_1;
	private JButton btnNewButton;
	private Canvas canvas;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public gui() {
		initialize();
	}

	private void initialize() {
		//frame
		frame = new JFrame();
		frame.setBounds(100, 100, 1249, 798);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		//panel superior, pt  butoane, textfield, comboBox
		
		panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.light"));
		panel.setBounds(10, 11, 1213, 91);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		//Buton adnotare
		button_1 = new Button("Confirma adnotare");
		button_1.setActionCommand("Confirma adnotare");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String adnotare=txtTextAdnotare.getText();
				System.out.println(adnotare);
			}
		});
		button_1.setBounds(10, 10, 118, 29);
		panel.add(button_1);
		
		//comboBox
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String culoare = String.valueOf(comboBox.getSelectedItem());
				System.out.println(culoare);
			}
		});
		comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Green", "Red", "Blue", "Yellow"}));
		comboBox.setBounds(375, 10, 160, 29);
		panel.add(comboBox);
		
		//textfield
		txtTextAdnotare = new JTextField();
		txtTextAdnotare.setToolTipText("");
		txtTextAdnotare.setBounds(173, 10, 160, 29);
		panel.add(txtTextAdnotare);
		txtTextAdnotare.setColumns(10);
		
		Button button_2 = new Button("Incarcare imagine");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
			}
		});
		button_2.setBounds(10, 52, 118, 29);
		panel.add(button_2);
		
		//Jtree so i can see the folder
		String path = "";
		try {
			path = new File(".").getCanonicalPath();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		tree_1 = new JTree();
		tree_1.setBounds(0, 113, 253, 627);
		frame.getContentPane().add(tree_1);
		tree_1.setModel(new MyTree(new File(path)));
		tree_1.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree_1.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree_1.getLastSelectedPathComponent();

			    if (node == null)
			    	return;
			    //Nothing is selected.
			    

			    Object nodeInfo = node.getUserObject();
			    if (node.isLeaf()) {
			        System.out.println(node);
			        
			    } 
				
			}
		});
		
		
		canvas = new ImageCanvas("Imagini/2.jpg");
		canvas.setBounds(259, 108, 974, 641);
		frame.getContentPane().add(canvas);
		
	
		
		
	
		
		
		
		//doamne ajuta
		//DrawImageOnCavas game = new DrawImageOnCavas("draw image", 500, 1500);
 	   //	game.start();
			
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	
	public void valueChanged(TreeSelectionEvent e) {
		   
	}
	
	public class ImageCanvas extends Canvas {

        private BufferedImage img;

        public ImageCanvas(String path) {
            try {
                img = ImageIO.read(new File(path));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return img == null ? new Dimension(200, 200) : new Dimension(img.getWidth(), img.getHeight());
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            if (img != null) {
                int x = (getWidth() - img.getWidth()) / 2;
                int y = (getHeight() - img.getHeight()) / 2;
                g.drawImage(img, x, y, this);
            }
        }
	}
}
