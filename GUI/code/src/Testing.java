import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class Testing {

	@Test
	void test() {
		Movie m = new Movie("title", "genre", "year", "runtime", "rating");
		String output = m.toString();
		assertEquals("title, genre, year, runtime, rating", output);
		
		
		
			    
	    
	}

}
