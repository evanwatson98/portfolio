import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ShoppingCart {

	public static ArrayList<Ticket> cart = new ArrayList<Ticket>();
	public ShoppingCart() {
		cart.add(new Ticket("title", "date", "time", "quan", "type"));
	}



	public static void addTicket(Ticket t) {cart.add(t);}
	public static void printReciept() throws IOException {
		// This function creates a new html file with all the contents of the add then opens the file
		// to open we may have use CGI

		try {
			Deals();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scanner in = new Scanner(new File("receipt.html"));
		PrintWriter out = new PrintWriter("yourReceipt.html");

		out.print(receiptStarter(in));

		in.close();
		out.close();

		File htmlFile = new File("yourReceipt.html");
		Desktop.getDesktop().browse(htmlFile.toURI());
	}


	private static String receiptStarter(Scanner in) {
		String content = "";
		int lineCounter = 1;
		while (in.hasNextLine()) {
			String line = in.nextLine() + "\n";
			if (lineCounter == 33) {
				content += items();
			}
			content += line;
			lineCounter++;
		}
		return content;
	}

	private static String items() {
		String items = "";
		int itemCount = 0;
		double sum = 0;
		for (Ticket i : cart) {
			itemCount++;
			String item = "<tr> \n";
			item += "<td>" + itemCount + "</td> \n";
			item += "<td><b>" + i.getTitle() + "</b></td> \n";
			item += "<td>" + i.getTime() + ", " + i.getDate() + ", " + i.getQuantity() +" tickets</td>";
			item += "<td>" + i.getType() + "</td> \n";
			item += "<td>$" + i.getPrice() + "</td> \n";
			item += "</tr> \n";

			sum += i.price;
			items += item;
		}
		sum = Math.round(sum * 100.0) / 100.0;
		items += "<tr><td></td><td></td><td></td><td></td><td><b>Total</b>: " + sum + "</td></tr>";
		return items;
	}


	public static void remove(Ticket t) { cart.remove(t); }

	public static ArrayList<Ticket> getShoppingCart() {
		return cart;
	}


	static int dayOfWeek = 0;

	public static void Deals() throws ParseException {

		int kidCounter = 0;
		int adultCounter = 0;

		for(Ticket t : ShoppingCart.getShoppingCart()) {

			if(t.getType().equals("Adult")) {
				adultCounter++;
			}
			if(t.getType().equals("Child")) {
				kidCounter++;
			}

			String sDate = t.getDate();
			Calendar c = Calendar.getInstance();
			SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
			Date date = format.parse(sDate); 
			c.setTime(date);
			dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

			//if wednesday, senior matinee tickets are free
			if(dayOfWeek == 4 && t.getType().equals("Senior") && t.getTime().contains("am")) {
				t.setPrice(0);
			}


			//if Saturday, kids matinee tickets are buy one get one free
			if(dayOfWeek == 7) {
				if(t.getType().equals("Child") && t.getTime().contains("am") && (kidCounter%2 == 0)) {
					t.setPrice(0);
				}
			}

		}

		if(adultCounter >= 2) {
			for(Ticket cT : ShoppingCart.getShoppingCart()) {
				if (cT.getType().equals("Child")) {
					cT.setPrice(cT.getPrice() * .5); 
				}
			}
		}
	}






}