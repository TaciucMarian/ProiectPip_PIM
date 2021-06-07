package proiectPip;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.swing.tree.TreeSelectionModel;

import java.io.FileOutputStream;
import java.io.FileWriter;

public class gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField txtTextAdnotare;
	private JPanel panel;
	private Button button_1;
	private JComboBox<String> comboBox;
	private JTree tree_1;
	private Canvas canvas;
	String adnotare = new String();
	public HashMap<String, Color> map = new HashMap<String, Color>();

	public static boolean pictat = false;

	public void exportImage(String imageName) {
		BufferedImage image = new BufferedImage(canvas.getWidth(),
				canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = image.createGraphics();
		canvas.paint(graphics);
		canvas.update(graphics);
		try {
			System.out.println("Exporting image: " + imageName);
			FileOutputStream out = new FileOutputStream("Imagini_adnotate/"
					+ imageName);
			ImageIO.write(image, "png", out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//graphics.dispose();
	}

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
		// frame
		frame = new JFrame();
		frame.setBounds(100, 100, 1249, 798);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		// panel superior, pt butoane, textfield, comboBox

		panel = new JPanel();
		panel.setBackground(UIManager.getColor("Button.light"));
		panel.setBounds(10, 11, 1213, 91);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Buton adnotare
		button_1 = new Button("Confirma adnotare");
		button_1.setActionCommand("Confirma adnotare");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adnotare = txtTextAdnotare.getText();
				System.out.println(adnotare);
				exportImage("" + tree_1.getLastSelectedPathComponent());
			}
		});
		button_1.setBounds(10, 10, 118, 29);
		panel.add(button_1);

		// comboBox
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String culoare = String.valueOf(comboBox.getSelectedItem());
				System.out.println(culoare);
			}
		});
		comboBox.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {
				"Green", "Red", "Blue", "Yellow" }));
		comboBox.setBounds(375, 10, 160, 29);
		panel.add(comboBox);

		// textfield
		txtTextAdnotare = new JTextField();
		txtTextAdnotare.setToolTipText("");
		txtTextAdnotare.setBounds(173, 10, 160, 29);
		panel.add(txtTextAdnotare);
		txtTextAdnotare.setColumns(10);
		map.put("Red", Color.RED);
		map.put("Green", Color.GREEN);
		map.put("Blue", Color.BLUE);
		map.put("Yellow", Color.YELLOW);

		class ImageCanvas extends Canvas {
			private BufferedImage img;
			int x1, y1, x2, y2;

			public ImageCanvas(String path) {
				try {
					img = ImageIO.read(new File(path));
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						canvas.update(getGraphics());
						int x = e.getX();
						int y = e.getY();
						System.out.println(x + "," + y);
						setStartPoint(e.getX(), e.getY());
					}

					@Override
					public void mouseDragged(MouseEvent e) {
						canvas.update(getGraphics());
						int x = e.getX();
						int y = e.getY();
						System.out.println(x + "," + y);
						setEndPoint(e.getX(), e.getY());
						canvas.paint(getGraphics());
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						canvas.update(getGraphics());
						int x = e.getX();
						int y = e.getY();
						System.out.println(x + "," + y);
						setEndPoint(e.getX(), e.getY());
						canvas.paint(getGraphics());
					}
				});
			}

			public void setStartPoint(int x, int y) {
				x1 = (x);
				y1 = (y);
			}

			public void setEndPoint(int x, int y) {
				x2 = (x);
				y2 = (y);
			}

			public void drawPerfectRect(Graphics g, int x, int y, int x2, int y2) {
				int px = Math.min(x, x2);
				int py = Math.min(y, y2);
				int pw = Math.abs(x - x2);
				int ph = Math.abs(y - y2);
				g.drawRect(px, py, pw, ph);
			}

			@Override
			public Dimension getPreferredSize() {
				return img == null ? new Dimension(200, 200) : new Dimension(
						img.getWidth(), img.getHeight());
			}

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				String filePath = "ExportTXT.txt";
				File file = new File(filePath);
				if (img != null) {
					try {
						String color;
						color = comboBox.getSelectedItem().toString();
						int x = (getWidth() - img.getWidth()) / 2;
						int y = (getHeight() - img.getHeight()) / 2;
						g.drawImage(img, x, y, this);
						g.setColor(map.get(color));
						drawPerfectRect(g, x1, y1, x2, y2);
						g.drawString(adnotare, x2 - (x2 - x1) / 2, y2 + 15);
						String str;
						FileWriter fw = new FileWriter(file);
						BufferedWriter bw = new BufferedWriter(fw);
						str = "Coordonatele adnotarii sunt: x1 =" + x1
								+ " y1 =" + y1 + "\tx2 =" + x2 + " y2 =" + y2
								+ ", adnotare: " + adnotare;
						bw.write(str);
						bw.newLine();
						bw.close();
						fw.close();
						;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		JButton buton_export = new JButton("Exporta adnotari");
		buton_export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Graphics g = null;			
				if (e.getSource() == buton_export) {
					paint(g);
				}
			}
		});
		buton_export.setBounds(565, 10, 166, 29);
		panel.add(buton_export);

		// Jtree so i can see the folder
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
		tree_1.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree_1.addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				String legatura;
				Object o = tree_1.getLastSelectedPathComponent();
				String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
				Pattern p = Pattern.compile(regex);
				System.out.println(o);
				legatura = "Imagini/" + o;
				System.out.println(legatura);
				Matcher m = p.matcher(legatura);
				if (m.matches() && pictat == false) {
					System.out.println("match");
					canvas = new ImageCanvas(legatura);
					canvas.setBounds(259, 108, 974, 641);
					frame.getContentPane().add(canvas);
					pictat = true;

				}

				else if (m.matches() && pictat == true) {
					try {
						canvas.getGraphics().dispose();
						canvas.getGraphics().drawImage(
								ImageIO.read(new File(legatura)), 0, 0,
								canvas.getWidth(), canvas.getHeight(), null);

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
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
}