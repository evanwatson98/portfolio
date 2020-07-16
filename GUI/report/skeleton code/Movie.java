import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Movie {
	
	public String title, genre, rating;
	public int year, runtime;
	// public Map<String, Integer> showtimes = new HashMap<String, Integer>();
	public Set<Movie> ALL_MOVIES = new HashSet<Movie>();
	
	public static Map<String, Set<Movie>> genreSort = new HashMap<String, Set<Movie>>();	
	public Map<String, Set<Movie>> ratingSort = new HashMap<String, Set<Movie>>();

	
	
	public Movie(String title, String genre, int year, int runtime, String rating) {
		this.title = title;
		this.genre = genre;
		this.year = year;
		this.runtime = runtime;
		
		/**
		 * We need a better way to store theater <showTime, #ofSeatsAvailable>
		 */
		
		ALL_MOVIES.add(this);
		
		
		
		
		// Sorts based on genre
		if (genreSort.keySet().contains(this.genre)) {
			genreSort.get(this.genre).add(this);
		} else {
			Set<Movie> temp = new HashSet<Movie>();
			temp.add(this);
			genreSort.put(this.genre, temp);
		}
		
		
		
		//Sort for ratings here
		// And both the genre and the ratings sort may want to go in app Driver
		

		
	}
	
	public static void main(String[] args) {		
		Movie m = new Movie("title", "Action", 2019, 120, "PG");
		System.out.println(genreSort.isEmpty());
	}

	private String getTitle() { return title; }
	private void setTitle(String title) { this.title = title; }
	private String getGenre() { return genre; }
	private void setGenre(String genre) { this.genre = genre; }
	private String getRating() { return rating; }
	private void setRating(String rating) { this.rating = rating; }
	private int getYear() { return year; }
	private void setYear(int year) { this.year = year; }
	private int getRuntime() { return runtime; }
	private void setRuntime(int runtime) { this.runtime = runtime; }

	


}
