/////////////////////////////////////////////////////////////////////////////////
//	
//	C212 Final Project Movie Theater App
//	Group Members: Camisa Vines, Rebecca Myers, Evan Watson, John Ruszkowski
//	Date Last Modifed: 4/26/19 at 5:20PM
//
/////////////////////////////////////////////////////////////////////////////////
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow extends JFrame {

    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) { ex.printStackTrace();
        } catch (IllegalAccessException ex) { ex.printStackTrace();
        } catch (InstantiationException ex) { ex.printStackTrace();
        } catch (ClassNotFoundException ex) { ex.printStackTrace(); }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


    private static void createAndShowGUI() {

    	// CREATE ALL THE MOVIE OBJECTS
    	try { createMovieObjects(); } catch (Exception ignore) {}

        //Create and set up the window.
        JFrame frame = new JFrame("Movie Application");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        AppDriver demo = new AppDriver();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

	public static void createMovieObjects() throws FileNotFoundException {
		Scanner in = new Scanner(new File("movie.txt"));

		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] movie = line.split(",");
			new Movie(movie[0], movie[1], movie[2], movie[3], movie[4]);
		}
	}

}
