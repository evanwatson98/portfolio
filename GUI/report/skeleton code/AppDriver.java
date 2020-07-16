import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AppDriver extends JPanel implements ActionListener {
	
	ImageIcon ic = new ImageIcon("avengers.jpg");
	JButton m1 = new JButton("Avengers Endgame", ic);
	
	public AppDriver() {
		this.setFocusable(true);
		this.setSize(MainWindow.WIDTH, 200);
		
		JButton genre = new JButton("Genre");
		
		//ArrayList<JButton> movies = addMovies();
		//for (JButton i : movies) { this.add(i); }
//		
		this.add(genre);
		genre.setSize(200, 150);

		setVisible(true);
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == m1) {
			this.remove(m1);
		}
		repaint();
		// TODO Auto-generated method stub
		
	}
	
	
	/**
	 * addMovies()
	 * This function should Scan the movie database, create new Movies for all, and create a Jbutton from it
	 * Then, it returns the ArrayList of JButtons to the constructor so that all the buttons can be added to the screen
	 */	
	public ArrayList<JButton> addMovies() {
		//ArrayList<JButton> movies = new ArrayList<JButton>();
		return null;	
	}

}
