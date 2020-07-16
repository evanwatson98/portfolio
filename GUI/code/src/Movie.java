import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;


public class Movie {

	public String title, genre, rating, year, runtime;
	public Map<String, ArrayList<String>> theater;

	// SORTED ARRAYLISTS OF ALL THE MOVIES
	public static ArrayList<Movie> ALL_MOVIES = new ArrayList<Movie>();
	public static Map<String, ArrayList<Movie>> genreSort = new HashMap<String, ArrayList<Movie>>();	
	public static Map<String, ArrayList<Movie>> ratingSort = new HashMap<String, ArrayList<Movie>>();



	public Movie(String title, String genre, String year, String runtime, String rating) {
		this.title = title;
		this.genre = genre;
		this.year = year;
		this.runtime = runtime;
		this.rating = rating;
		this.theater = new HashMap<String, ArrayList<String>>();

		ArrayList<String> times = new ArrayList<String>();
		times.add("9:00am");times.add("11:00am"); times.add("1:00pm"); times.add("3:00pm");
		times.add("5:00pm"); times.add("7:00pm"); times.add("9:00pm"); times.add("10:00pm");

		for (int i = 1; i < 20; i++) {
			this.theater.put("5-" + i + "-2019", times);
		}

		ALL_MOVIES.add(this);
		genreSort(this);
		ratingSort(this);

	}




	// DRAWING THAT APPEARS ON THE LANDING PAGE
	public JPanel draw() {

		JPanel section = new JPanel();
		section.setSize(100, 200);
		section.setLayout(new GridLayout(4,1));
		section.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));

		//TITLE AND MOVIE DETAILS
		JLabel title = new JLabel(this.title);
		title.setFont(new Font("Serif", Font.BOLD, 20));
		title.setForeground(Color.red);
		section.add(title);
		JLabel details = new JLabel(this.genre + "," + this.runtime + "m," + this.rating);
	    details.setFont(new Font("Serif", Font.PLAIN, 14));
	    section.add(details);

	    // GET TICKET DEATILS
		JPanel ticketSection = new JPanel();
		ticketSection.setLayout(new GridLayout(2,2));
			// SET UP THE DEATILS IN EACH BOX
			int c = 0;
		    String[] dates = new String[this.theater.keySet().size()];
		    for(String i : this.theater.keySet()) { dates[c] = i; c++; }
		    c = 0;
		    String[] times = new String[this.theater.get(dates[0]).size()];
		    for (String i : this.theater.get(dates[0])) { times[c] = i; c++; }
		    String[] num = {"1", "2", "3", "4", "5"};
		    String[] type = { "Adult", "Senior", "Child"}; 
	    // INPUT THE INFORMATION IN THE COMBO BOXES
	    JComboBox<String> dateBox = new JComboBox<String>(dates);
	    JComboBox<String> timeBox = new JComboBox<String>(times);
	    JComboBox<String> quanBox = new JComboBox<String>(num);
	    JComboBox<String> typeBox = new JComboBox<String>(type);
	    ticketSection.add(dateBox); ticketSection.add(timeBox);
	    ticketSection.add(quanBox); ticketSection.add(typeBox);
	    section.add(ticketSection);



	    //CREATE THE ADD TO CART BUTTON
	    JButton addToCart = new JButton("Add to Shopping Cart");
	    addToCart.setBackground(Color.red); addToCart.setForeground(Color.WHITE);
	    addToCart.addActionListener((event) -> {
		    String THEDATE = dateBox.getSelectedItem().toString();
		    String THETIME = timeBox.getSelectedItem().toString();
		    String THETYPE = typeBox.getSelectedItem().toString();
		    String THEQUAN = quanBox.getSelectedItem().toString();
		    new Ticket(this.title, THEDATE, THETIME, THETYPE, THEQUAN);

	    });
	    section.add(addToCart);


	    return section;

	}



	// DRAWING THAT APPEARS ON THE ADMIN TAB
	public JPanel drawAdmin() {

		JPanel section = new JPanel();
		section.setSize(100, 200);
		section.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));


		JPanel buttonAndText = new JPanel();
		buttonAndText.setLayout(new GridLayout(2, 1));
		JTextField title = new JTextField(this.toString(), 50);
		JButton edit = new JButton("Edit");
		buttonAndText.add(title); buttonAndText.add(edit);
		edit.addActionListener((event) -> {
			String[] movieUpdate = title.getText().split(",");
			this.setTitle(movieUpdate[0]);
			this.setGenre(movieUpdate[1]);
			this.setYear(movieUpdate[2]);
			this.setRuntime(movieUpdate[3]);
			this.setRating(movieUpdate[4]);
			System.out.println(movieUpdate);
			try {updateAllMovies();} catch (FileNotFoundException e) {e.printStackTrace(); }
		});

		section.add(buttonAndText);

		return section;

	}






	// EDITS THE MOVIE DATABASE FILE 'MOVIE.TXT'
	public void updateAllMovies() throws FileNotFoundException {
		PrintWriter out = new PrintWriter(new File("movie.txt"));
		for (Movie i: ALL_MOVIES) {
			System.out.println(i.toString());
			out.println(i.toString());
		}

		out.close(); // it will not work without this line
	}





	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





	public void genreSort(Movie m) {
		// Sorts based on genre
		if (genreSort.keySet().contains(m.genre)) {
			genreSort.get(m.genre).add(m);
		} else {
			ArrayList<Movie> temp = new ArrayList<Movie>();
			temp.add(m);
			genreSort.put(m.genre, temp);
		}
	}

	public void ratingSort(Movie m) {
		// Sorts based on rating
		if (ratingSort.keySet().contains(m.rating)) {
			ratingSort.get(m.rating).add(m);
		} else {
			ArrayList<Movie> temp = new ArrayList<Movie>();
			temp.add(m);
			ratingSort.put(m.rating, temp);
		}
	}

	/** Getters and Setters */

	public static Map getRatingSort() {return ratingSort;}
	public static Map getGenreSort() {return genreSort;}
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getGenre() { return genre; }
	public void setGenre(String genre) { this.genre = genre; }
	public String getRating() { return rating; }
	public void setRating(String rating) { this.rating = rating; }
	public String getYear() { return year; }
	public void setYear(String year) { this.year = year; }
	public String getRuntime() { return runtime; }
	public void setRuntime(String runtime) { this.runtime = runtime; }
	public String toString() {
		return this.title + "," + this.genre + "," + this.year + "," + this.runtime + "," + this.rating;
	}




}