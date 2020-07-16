
public class Ticket {
	
	private String type, title, time;
	private double PRICE, price;

	
	public Ticket(String title, String time, String type) {
		this.title = title;
		this.time = time;
		this.type = type;
		this.price = PRICE;
		
		if (type.equals("Child Matinee") || type.equals("Senior Matinee")) { this.price = price * (3/4) * (1/2); }
		else if (type.equals("Child Primetime") || type.equals("Senior Primetime")) { this.price = price * (3/4); }
		else if (type.equals("Adult Matinee")) { this.price = price * (1/2); }

	}
	
	public void purchaseTickets() {}
	public void viewTickets() {}
	public void setPrice(int price) {PRICE = price;}
	public double getPrice() {return PRICE;}
}