import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Ticket {

	String type, title, time, date;
	double price;
	int quantity;


	public Ticket(String title, String date, String time, String type, String quantity) {
		this.title = title;
		this.date = date;
		this.time = time;
		this.type = type;
		this.quantity = Integer.parseInt(quantity);

		this.price = Manager.ticketPrice * this.quantity;

		if (time.contains("am")) {
			this.price = this.price/2;
			if (this.type.equals("Child") || this.type.equals("Senior")) {
				this.price = this.price * (.75);
			}
		}

		this.price = Math.round(this.price * 100.0) / 100.0;



		System.out.println(this.toString());


		addToCart(this);
		AppDriver.tab2.updateUI();
	}

	public void addToCart(Ticket ticket) {
		ShoppingCart.addTicket(ticket);
	}


	public JPanel draw() {
		JPanel section = new JPanel();
		JLabel tix = new JLabel(this.quantity + "(" + this.type +  ") tickets to see " + this.title + " on " + this.date + " at " + this.time);
		JButton delete = new JButton("Delete this movie");
		delete.addActionListener((event) -> {
			ShoppingCart.cart.remove(this);
			section.getParent().remove(section);
		}); 


		section.add(tix); section.add(delete);
		return section;
	}



	public void viewTickets() {}
	public void setPrice(double price) {this.price = price;}
	public double getPrice() {return this.price;}
	public String getDate() { return this.date;}
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getTime() { return time; }
	public void setTime(String time) { this.time = time; }
	public int getQuantity() {return this.quantity;}
	public String toString() {
		return "[" + this.title + ", " + this.date + ", " + this.quantity + ", " + this.price + ", " + this.type + "]";
	}
}