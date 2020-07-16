
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainWindow extends JFrame {
	
	public MainWindow() {
		super();
		setLayout(new FlowLayout());
		setTitle("Movie Application");
//		setSize(2000, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBackground(Color.black);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		this.setUndecorated(true);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		JFrame ap = new MainWindow();
		AppDriver ad = new AppDriver();
		ap.add(ad);
        ap.setVisible(true);
	}





}
