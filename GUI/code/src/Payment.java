//import java.awt.GridLayout;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import javax.swing.ButtonGroup;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JRadioButton;
//import javax.swing.JTextField;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;
import java.text.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Payment {

	static int dayOfWeek = 0;

	public static JPanel draw() {

		JPanel section = new JPanel();
		JPanel paymentInfo = new JPanel();
		paymentInfo.setLayout(new GridLayout(4, 1));

		JButton cash = new JButton("cash");
		JButton credit = new JButton("credit");

		cash.addActionListener((event) -> {
			paymentInfo.removeAll();

			JButton confirm = new JButton("Confirm Purchase");
		    confirm.addActionListener((event2) -> {

				try { ShoppingCart.printReciept();} catch (IOException e) { e.printStackTrace(); }
			});

		    paymentInfo.add(confirm);
			paymentInfo.updateUI();

		});

		credit.addActionListener((event) -> {
			paymentInfo.removeAll();
			JTextField cardNumber = new JTextField("Enter your card number", 40);
			JTextField exp = new JTextField("Card Exp. (MM/YY)", 20);
			JTextField ccvText = new JTextField("ccv", 20);

			paymentInfo.add(cardNumber);
			paymentInfo.add(exp);
			paymentInfo.add(ccvText);


			JButton confirm = new JButton("Confirm Purchase");
		    confirm.addActionListener((event2) -> {

		    	if ((cardNumber.getText().length() == 16) && (exp.getText().length() == 5) && (ccvText.getText().length() == 3)) {
		    	try { ShoppingCart.printReciept();} catch (IOException e) { e.printStackTrace(); }
		    	} else {
		    		JFrame frame = new JFrame();
		    		JOptionPane.showMessageDialog(frame, "Invalid credit card infomation. Try again");
		    	}


			});

		    paymentInfo.add(confirm);

			paymentInfo.updateUI();
		});




		section.add(cash); section.add(credit);
		section.add(paymentInfo);
		return section;
	}



}