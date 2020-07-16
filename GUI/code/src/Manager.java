import java.io.*;
import javax.swing.JPanel;

public class Manager {

	static final String PASSWORD = "c212_MovieApp";
	public static double ticketPrice = 5.0;



	public JPanel displayMovies() {
		System.out.println("in maganer");
		return null;
	}


	public void addMovies(String title, String genre, String year, String rating, String runtime) {

	  System.out.println("adding to movie.txt");

	  try (
			  FileWriter fw = new FileWriter("movie.txt", true);
			  BufferedWriter bw = new BufferedWriter(fw);
			  PrintWriter out = new PrintWriter(bw)
					  )
	  { out.println(title + ", " + genre + ", " + year + ", " + runtime + ", " +rating); } 

	  catch (IOException e) { e.printStackTrace(); }

	}

}