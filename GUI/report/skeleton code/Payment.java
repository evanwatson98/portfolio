import java.util.*;

public class Payment {
	
	Customer newCustomer;
	//Stores the amount of tickets 
	
	int tickets;
	
	//This arrayList should be created in our driver
	//ArrayList<Customer> customerInformation = ArrayList<>();
	
	public Payment(Customer newCustomer, int tickets) {
		newCustomer = newCustomer;
		tickets = tickets;
		
	}
	
	public void newPayment() {
		//will receive how many tickets a user will want and add a new ticket object in the array once they have confirmed the cash/credit payment
	}
	
	//If the user decides they no longer want a smaller or larger amount of tickets they can remove them
	public void removeTicket() {}
	
	public String orderSummary() {}
	
	public String receipt() {} 
	
	//If the user pays in credit 
	public void creditCollection() {}
	
	//We need to find out what time the ticket is for so we can charge them apropriately for matanes, etc. 
	public String getTicketOptions() {}
	
	
	public static void main(String[] arg) {}
	//Implement a scanner that will prompt the user with payment method, Movie name, type of ticket and the amount of tickets
	//orderSummary will be printed
	//Prompt the User if they confirm the order summary
	//Once the user is finished and have confirmed order summary we will print the receipt
}
