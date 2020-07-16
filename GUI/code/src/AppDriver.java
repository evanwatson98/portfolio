import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;

public class AppDriver {

	JPanel tab1 = customerView();
	static JPanel tab2 = shopping();
	JPanel tab3 = admin();

	// MAIN FUNCTION THAT IS CREATING THE TABS AND SENDING THEM BACK TO THE MAINWINDOW
	public void addComponentToPane(Container pane) {
	    JTabbedPane tabbedPane = new JTabbedPane();
	    tabbedPane.setFont( new Font( "Serif", Font.PLAIN, 25 ) );

	    tabbedPane.addTab("Main Window", customerView());
	    tabbedPane.addTab("Shopping Cart", tab2);
	    tabbedPane.addTab("Manager Mode", admin());

	    pane.add(tabbedPane, BorderLayout.CENTER);
	}


	/////////////////////////////////////////////////////////////
	// LANDING PAGE TAB
	/////////////////////////////////////////////////////////////

	public JPanel customerView() {

		ArrayList<Movie> movies = Movie.ALL_MOVIES;

		// CREATE THE PANELS
	    JPanel card1 = new JPanel();
	    JPanel movSection = new JPanel(); movSection.setLayout(new GridLayout(Movie.ALL_MOVIES.size()/4 + 1, 4));
	    JPanel searchSection = new JPanel(); searchSection.setLayout(new GridLayout(2, 1));

	    // BUTTON ALLOWS USER TO SEE ALL MOVIES
	    JButton allMoviesButton = new JButton("All");
	    //allMoviesButton.setBackground(Color.MAGENTA);
	    allMoviesButton.addActionListener((event) -> {
	    	movSection.removeAll();
	    	for (Movie i : movies) { movSection.add(i.draw()); }
	    	movSection.updateUI();

	    }); 
	    searchSection.add(allMoviesButton);


	    // BUTTONS AND ACTIONS FOR EACH GENRE PRESENT
	    for (String i : Movie.genreSort.keySet()) {
	    	JButton b = new JButton(i);
	    	//b.setBackground(Color.orange);
	    	b.addActionListener((event) -> {
	    		movSection.removeAll();
	    		for (Movie j : Movie.genreSort.get(i)) {
	    			movSection.add(j.draw());
	    		}
	    		movSection.updateUI();
	    	});
	    	searchSection.add(b);
	    }


	    // BUTTONS AND ACTIONS FOR EACH RATING PRESENT
	    for (String i : Movie.ratingSort.keySet()) {
	    	JButton b = new JButton(i);
	    	//b.setBackground(Color.YELLOW);
	    	b.addActionListener((event) -> {
	    		movSection.removeAll();
	    		for (Movie j : Movie.ratingSort.get(i)) {
	    			movSection.add(j.draw());
	    		}
	    		movSection.updateUI();
	    	});
	    	searchSection.add(b);
	    }



	    for (Movie i : movies) { movSection.add(i.draw()); }
	    card1.add(searchSection);
	    card1.add(movSection);

		return card1;
	}



	/////////////////////////////////////////////////////////////
	// SHOPPING CART TAB
	/////////////////////////////////////////////////////////////

	public static JPanel shopping() {

		JPanel card2 = new JPanel();
		JPanel cartAndPayment = new JPanel(); cartAndPayment.setLayout(new GridLayout(1, 2));
		JPanel cartSection = new JPanel(); cartSection.setLayout(new GridLayout(ShoppingCart.getShoppingCart().size(), 1));
		JPanel paymentSection = new JPanel(); 

		for (Ticket i : ShoppingCart.getShoppingCart()) {
			cartSection.add(i.draw());
		}
		cartAndPayment.add(cartSection);


		Payment p = new Payment();
		paymentSection.add(p.draw());



		// update the shopping cart and confirmation button
		JButton updateCart = new JButton("Update Shopping Cart");
		updateCart.addActionListener((event) -> {
			cartSection.removeAll();
			for (Ticket i : ShoppingCart.getShoppingCart()) {
				cartSection.add(i.draw());
			}
			cartSection.updateUI();
		});
		card2.add(updateCart);




	    cartAndPayment.add(paymentSection);
	    card2.add(cartAndPayment);




		return card2;
	}








	/////////////////////////////////////////////////////////////
	// ADMIN TAB
	/////////////////////////////////////////////////////////////

	int attempts = 0;
	public JPanel admin() {


		JPanel card3 = new JPanel();
	    JButton ok = new JButton("Enter");
	    JPasswordField password = new JPasswordField("Password", 30);
	    card3.add(password);
	    card3.add(ok);

	    ok.addActionListener((event) -> {
	    	attempts++;
	    	if (attempts >= 3) {
	    		System.exit(0);
	    	}

	    	if (password.getText().equals(Manager.PASSWORD)) {
	    		card3.remove(ok); card3.remove(password);
	    		System.out.println("section added");

	    		ArrayList<Movie> movies = new ArrayList<Movie>();
	    		movies = Movie.ALL_MOVIES;

	    		for (Movie i : movies) {
	    	    	card3.add(i.drawAdmin());
    	    	}

	    		JPanel addSection = new JPanel();
	    		addSection.setSize(200, 100);
	    		addSection.setLayout(new GridLayout(3,1));
	    		addSection.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));

	    		JTextField title = new JTextField("Movie Title,Genre,Year,Runtime,Rating", 50);

	    		JButton add = new JButton("Add");
	    		addSection.add(title); addSection.add(add);

	    		add.setBackground(Color.green);
	    		add.addActionListener((source) -> {
	    			Manager m = new Manager();
	    			String[] movie = title.getText().split(",");
	    			Movie temp = new Movie(movie[0], movie[1], movie[2], movie[3], movie[4]);
	    			m.addMovies(movie[0], movie[1], movie[2], movie[3], movie[4]);
	    		});

	    		card3.add(addSection);
	    	}
	    });

		return card3;

	}










} 
  
