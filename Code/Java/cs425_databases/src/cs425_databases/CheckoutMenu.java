package cs425_databases;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CheckoutMenu {

	private JFrame fCheckout;
	private JComboBox<String> existingCards;
	private JEditorPane moneyDetails;
	
	private JTextField tStreet;
	private JTextField tCity;
	private JTextField tState;
	private JTextField tZipcode;
	
	private String userInfo [] = {"","",""};
	private double checkoutInfo[] = {0.0,0.0,0.0,0.0,0.0};
	
	private ArrayList<ArrayList<Object>> cards;
	
	// Constructor
	public CheckoutMenu(String uInfo[], double inputInfo []) {
		userInfo = uInfo;
		checkoutInfo = inputInfo;
		initialize();
	}
	
	// Initializer
	private void initialize() {
		
		fCheckout = new JFrame();
		fCheckout.setTitle("https://www.store.com/checkout?id=19201");
		fCheckout.getContentPane().setBackground(Color.DARK_GRAY);
		fCheckout.setBounds(100, 100, 898, 455);
		fCheckout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fCheckout.getContentPane().setLayout(null);
		
		JLabel label = new JLabel();
		label.setForeground(Color.WHITE);
		label.setText("Payment Information");
		label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label.setBounds(83, 27, 436, 37);
		fCheckout.getContentPane().add(label);
		
		JLabel label_1 = new JLabel();
		label_1.setForeground(Color.WHITE);
		label_1.setText("Shipping Address");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_1.setBounds(83, 167, 153, 53);
		fCheckout.getContentPane().add(label_1);
		
		JLabel lblBuildingstreet = new JLabel();
		lblBuildingstreet.setForeground(Color.WHITE);
		lblBuildingstreet.setText("Building/Street");
		lblBuildingstreet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBuildingstreet.setBounds(100, 224, 131, 22);
		fCheckout.getContentPane().add(lblBuildingstreet);
		
		tStreet = new JTextField();
		tStreet.setBounds(258, 224, 317, 26);
		fCheckout.getContentPane().add(tStreet);
		
		JLabel lblCity = new JLabel();
		lblCity.setForeground(Color.WHITE);
		lblCity.setText("City");
		lblCity.setFont(new Font("Tahoma", lblCity.getFont().getStyle(), 16));
		lblCity.setBounds(100, 258, 44, 22);
		fCheckout.getContentPane().add(lblCity);
		
		tCity = new JTextField();
		tCity.setBounds(258, 258, 317, 26);
		fCheckout.getContentPane().add(tCity);
		
		JLabel lblState = new JLabel();
		lblState.setForeground(Color.WHITE);
		lblState.setText("State");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblState.setBounds(100, 292, 67, 22);
		fCheckout.getContentPane().add(lblState);
		
		tState = new JTextField();
		tState.setEditable(false);
		tState.setText(userInfo[2]);
		tState.setBounds(258, 292, 317, 26);
		fCheckout.getContentPane().add(tState);
		
		JButton bOrder = new JButton("Place Order");
		bOrder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				placeOrder();
			}
		});
		bOrder.setForeground(new Color(255, 0, 0));
		bOrder.setBackground(Color.WHITE);
		bOrder.setBounds(705, 295, 117, 29);
		fCheckout.getContentPane().add(bOrder);
		
		moneyDetails = new JEditorPane();
		moneyDetails.setForeground(new Color(0, 0, 128));
		moneyDetails.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
		moneyDetails.setBackground(new Color(192, 192, 192));
		moneyDetails.setText("\n Subtotal                           $" + 0.0 + "\n\n Delivery Fee                     $" + 0.0 + "\n\n Tax                                   $" + 0.0 + "\n\n Total                                $" + 0.0);
		moneyDetails.setBounds(652, 149, 227, 181);
		moneyDetails.setEditable(false);
		fCheckout.getContentPane().add(moneyDetails);
		
		JLabel label_6 = new JLabel();
		label_6.setForeground(Color.WHITE);
		label_6.setText("Order summary");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 18));
		label_6.setBounds(652, 100, 171, 37);
		fCheckout.getContentPane().add(label_6);
		
		existingCards = new JComboBox<String>();
		existingCards.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(existingCards.getItemCount() >0)
					displayCardDetails();
			}
		});
		existingCards.addItem("Select A Card");
		existingCards.setSelectedIndex(0);
		existingCards.setBounds(195, 73, 143, 27);
		fCheckout.getContentPane().add(existingCards);
		
		JTextField cNumber = new JTextField();
		JTextField cMonth = new JTextField();
		JTextField cYear = new JTextField();
		JTextField cStreet = new JTextField();
		JTextField cCity = new JTextField();
		JComboBox<Integer> cZipcode = new JComboBox<Integer>();
		cZipcode.setBounds(150, 88, 130, 26);
		for (int i = 46001; i < 47998; i++) {
			cZipcode.addItem(new Integer(i));
		}
		for (int i = 60001; i < 63000; i++) {
			cZipcode.addItem(new Integer(i));
		}
		
		Object [] fields = {
				"Card Number:", cNumber,
				"Expiration Month:", cMonth,
				"Expiration Year:", cYear,
				"Building/Street:", cStreet,
				"City:", cCity,
				"Zipcode:", cZipcode
		};
		
		JButton bAddCard = new JButton("Add New Card");
		bAddCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cNumber.setEditable(true);
				JOptionPane.showConfirmDialog(null, fields, "Enter your card and address details", JOptionPane.CANCEL_OPTION);
				createNewCard(cNumber.getText(), cMonth.getText(), cYear.getText(), cStreet.getText(), cCity.getText(), cZipcode.getSelectedItem().toString());
				cNumber.setText("");
				cMonth.setText("");
				cYear.setText("");
				cStreet.setText("");
				cCity.setText("");
				cZipcode.setSelectedIndex(0);
			}
		});
		bAddCard.setForeground(Color.BLACK);
		bAddCard.setBackground(Color.WHITE);
		bAddCard.setBounds(345, 76, 156, 25);
		fCheckout.getContentPane().add(bAddCard);
		
		JLabel lblZipCode = new JLabel();
		lblZipCode.setText("Zip Code");
		lblZipCode.setForeground(Color.WHITE);
		lblZipCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblZipCode.setBounds(100, 325, 131, 22);
		fCheckout.getContentPane().add(lblZipCode);
		
		tZipcode = new JTextField();
		tZipcode.setEditable(false);
		tZipcode.setText(userInfo[1]);
		tZipcode.setBounds(258, 325, 317, 26);
		fCheckout.getContentPane().add(tZipcode);
		
		JLabel lblUseExistingCard = new JLabel("Use Card");
		lblUseExistingCard.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblUseExistingCard.setForeground(Color.WHITE);
		lblUseExistingCard.setBounds(100, 76, 72, 18);
		fCheckout.getContentPane().add(lblUseExistingCard);
		
		JButton bModify = new JButton("Modify Cart");
		bModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modifyCart();
			}
		});
		bModify.setForeground(new Color(30, 144, 255));
		bModify.setBackground(Color.WHITE);
		bModify.setBounds(130, 378, 156, 29);
		fCheckout.getContentPane().add(bModify);
		
		JButton bContinue = new JButton("Continue Shopping");
		bContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				continueShopping();
			}
		});
		bContinue.setForeground(new Color(30, 144, 255));
		bContinue.setBackground(new Color(30, 144, 255));
		bContinue.setBounds(373, 378, 156, 29);
		fCheckout.getContentPane().add(bContinue);
		
		JButton bCancel = new JButton("Cancel Order");
		bCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cancelOrder();
			}
		});
		bCancel.setForeground(new Color(30, 144, 255));
		bCancel.setBackground(new Color(30, 144, 255));
		bCancel.setBounds(625, 378, 156, 29);
		fCheckout.getContentPane().add(bCancel);
		
		JButton btnEditCard = new JButton("Edit Selected Card");
		btnEditCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Object card [] = null;
				
				for(int i = 1; i < cards.size(); i++) {
					if(cards.get(i).get(1).toString().equals(existingCards.getSelectedItem().toString())) {
						card = cards.get(i).toArray();
					}
				}
				
				if (existingCards.getSelectedItem().toString().equals("Select A Card")) {
					_message("No Card Selected!", "Please select a valid card!", 1);
				}
				else {
					cNumber.setEditable(false);
					cNumber.setText(card[1].toString());
					cMonth.setText(card[2].toString());
					cYear.setText(card[3].toString());
					cStreet.setText(card[4].toString());
					cCity.setText(card[5].toString());
					if (Integer.parseInt(card[7].toString())/10000 == 4)
						cZipcode.setSelectedIndex(Integer.parseInt(card[7].toString())-46001);
					else
						cZipcode.setSelectedIndex(1997 + Integer.parseInt(card[7].toString())-60001);
					
					JOptionPane.showConfirmDialog(null, fields, "Enter your card and address details", JOptionPane.CANCEL_OPTION);
					editCard(cNumber.getText(), cMonth.getText(), cYear.getText(), cStreet.getText(), cCity.getText(), cZipcode.getSelectedItem().toString());
				}
				cNumber.setText("");
				cMonth.setText("");
				cYear.setText("");
				cStreet.setText("");
				cCity.setText("");
				cZipcode.setSelectedIndex(0);
				
			}
		});
		btnEditCard.setForeground(Color.BLACK);
		btnEditCard.setBackground(Color.WHITE);
		btnEditCard.setBounds(345, 109, 156, 25);
		fCheckout.getContentPane().add(btnEditCard);
		
		JButton btnDeleteCard = new JButton("Delete Selected Card");
		btnDeleteCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (existingCards.getSelectedItem().toString().equals("Select A Card"))
					_message("No Card Selected!", "Please select a valid card!", 1);
				deleteCard(existingCards.getSelectedItem().toString());
			}
		});
		btnDeleteCard.setForeground(Color.BLACK);
		btnDeleteCard.setBackground(Color.WHITE);
		btnDeleteCard.setBounds(345, 143, 156, 25);
		fCheckout.getContentPane().add(btnDeleteCard);
		
		displayCheckoutInfo();
		getCards();	
		
		fCheckout.setVisible(true);
	}
	
	// Function to Check if String is Numeric
	public boolean _isNumeric(String str)  
	{  
	  try  
	  {  
	    int d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	} 
	
	// Function to Display Message in pop-up window
	private void _message(String title, String message, int type) {
		Object[] options = {"OK"};
		if (type == 1)
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		else if (type == 2)
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		else
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	// Function to Calculate Money Variables
	private void displayCheckoutInfo() {
		moneyDetails.setText("\n Subtotal                           "
				+ "$" + checkoutInfo[0] + "\n\n Delivery Fee                     "
						+ "$" + checkoutInfo[1] + "\n\n Tax                                   "
								+ "$" + checkoutInfo[2] + "\n\n Total                                "
										+ "$" + checkoutInfo[3]);
	}
	
	// Function to Get Credit Card and Associated Information from Database
	private void getCards() {
		
		cards = SQLInterface.sqlExecute("select * from cinfo where cid = '" + userInfo[0]+"';");
		for(int i = 1; i < cards.size(); i++) {
			existingCards.addItem(cards.get(i).get(1).toString());
		}
		
	}
	
	// Function to Display Credit Cards and Associated Information
	private void displayCardDetails() {
		Object card [] = null;
		
		if (existingCards.getSelectedItem().toString().equals("Select A Card")) {
			tStreet.setText("");
			tCity.setText("");
		}
		else {
			
			for(int i = 1; i < cards.size(); i++) {
				if(cards.get(i).get(1).toString().equals(existingCards.getSelectedItem().toString())) {
					card = cards.get(i).toArray();
				}
			}
			
			tStreet.setText(card[4].toString());
			tCity.setText(card[5].toString());
			tState.setText(card[6].toString());
			tZipcode.setText(card[7].toString());
		}
		
	}
	
	// Function to Create a new Credit Card and Associated Information
	private void createNewCard(String cNumber, String cMonth, String cYear, String cStreet, String cCity, String cZipcode) {
		
		String cState = "";
		if (Integer.parseInt(userInfo[1])/10000 == 4)
			cState = "IN";
		else
			cState = "IL";
		
		if(checkDetails(cNumber, cMonth, cYear, cStreet, cCity)) {
			
			if(SQLInterface.sqlUpdate("insert into cinfo values ('"+userInfo[0]+"', '"+cNumber+"', '"+cMonth+"', '"+cYear+"', '"+cStreet+"', '"+cCity+"', '"+cState+"', '"+cZipcode+"');") == 0)
				_message("Failed!", "Unable to add your card. Please try again later.", 1);
			else {
				_message("Success!", "Your card was successfully added!", 0);
				existingCards.removeAllItems();
				existingCards.addItem("Select A Card");
				getCards();
			}
		}
		else 
			_message("Invalid Details!", "Please enter valid information!", 1);
		
	}
	
	// Function to Check entered credit card details
	private boolean checkDetails(String cNumber, String cMonth, String cYear, String cStreet, String cCity) {
		boolean temp = true;
		
		for(int i = 0; i < cNumber.length(); i++) {
			if(!Character.isDigit(cNumber.charAt(i)))
				temp = false;
		}
		if(cNumber.length() != 16 || cNumber.charAt(0) == '0')
			temp = false;
		if(cMonth.length() != 2 || Integer.parseInt(cMonth) <= 0 || Integer.parseInt(cMonth) > 12) 
			temp = false;
		if(cYear.length() != 4 || Integer.parseInt(cYear) <= 0) 
			temp = false;
		if(cStreet.equals("")) 
			temp = false;
		if(cCity.equals(""))
			temp = false;

		return temp;
	}
	
	// Function to Edit a Credit Card and Associated Information
	private void editCard(String cNumber, String cMonth, String cYear, String cStreet, String cCity, String cZipcode) {
		String cState = "";
		if (Integer.parseInt(userInfo[1])/10000 == 4)
			cState = "IN";
		else
			cState = "IL";
				
		if(checkDetails(cNumber, cMonth, cYear, cStreet, cCity)) {
			if(SQLInterface.sqlUpdate("update cinfo set cid = '"+userInfo[0]+"',  month = '"+cMonth+"', year = '"+cYear+"', street = '"+cStreet+"', city = '"+cCity+"', state = '"+cState+"', zip = '"+cZipcode+"' where cid = '"+userInfo[0]+"' and cnumber = '"+cNumber+"';") == 0)
				_message("Failed!", "Unable to update your card details. Please try again later.", 1);
			else {
				_message("Success!", "Your card details were successfully updated!", 0);
				existingCards.removeAllItems();
				existingCards.addItem("Select A Card");
				getCards();
			}
		}
		else 
			_message("Invalid Details!", "Please enter valid information!", 1);
	}
	
	// Function to Delete a Credit Card and Associated Information
	private void deleteCard(String cnumber) {
		if(SQLInterface.sqlUpdate("delete from cinfo where cid = '"+userInfo[0]+"' and cnumber = '"+cnumber+"';") == 0)
			_message("Failed!", "Unable to delete card. Please try again later.", 1);
		else
			_message("Success!", "Your card was successfully deleted!", 0);
		existingCards.removeAllItems();
		existingCards.addItem("Select A Card");
		getCards();
	}
	
	// Function to return to the Cart Menu
	private void modifyCart() {
		fCheckout.setVisible(false);
		new CartMenu(userInfo);
	}
	
	// Function to Return to the Shopping Menu
	private void continueShopping() {
		fCheckout.setVisible(false);
		new Grocery_store(userInfo);
	}
	
	// Function to Cancel Order
	private void cancelOrder() {
		if(SQLInterface.sqlUpdate("delete from cart where cid = '"+userInfo[0]+"';") == 0)
			_message("Failed!", "Unable to cancel your order. Please try again later.", 1);
		else {
			_message("Success!", "Your cart was successfully emptied!", 0);
		}
		
		fCheckout.setVisible(false);
		new Grocery_store(userInfo);
		
	}
	
	// Function to Check if entered information is valid
	private boolean checkInfo() {
		if (tStreet.getText().equals("") || tCity.getText().equals("") || existingCards.getSelectedItem().toString().equals("Select A Card") || (int)checkoutInfo[4] == 0)
			return false;
		else
			return true;
	}
	
	// Function to Place Order
	private void placeOrder() {
		
		int oid = 0;
		int update = 0;
		String timestamp = "";
		String address = tStreet.getText() + ", " + tCity.getText() + ", " + tState.getText() + " " + tZipcode.getText() +".";
		String message = "Thank you! Your order has been placed!\n"+
				(int)checkoutInfo[4] + " items will be delivered to the following address:\n" + address;
		
		if(checkInfo()) {
			ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select max(oid) from orders where cid = '"+userInfo[0]+"';");
			if (data.get(1).get(0) == null) 
				oid = (((Integer.parseInt(userInfo[0])%10000) + 90000) * 10) + 1;
			else
				oid = Integer.parseInt(data.get(1).get(0).toString()) + 1;
			
			data = SQLInterface.sqlExecute("select now();");
			timestamp = data.get(1).get(0).toString();
			
			data = SQLInterface.sqlExecute("select * from cart where cid = '" + userInfo[0]+"';");
			update = SQLInterface.sqlUpdate("update customer set balance = balance + '"+checkoutInfo[3]+"' where cid = '"+userInfo[0]+"';");
			for(int i = 1; i < data.size(); i++) {
				update = SQLInterface.sqlUpdate("insert into orders values ('"+oid+"', '"+userInfo[0]+"', '"+data.get(i).get(2)+"', '"+data.get(i).get(3)+"', '"+existingCards.getSelectedItem().toString()+"', 'issued', '"+timestamp+"');");
			}
			if(update == 0)
				_message("Failed!", "Unable to place your order. Please try again later.", 1);
			else {
				_message("Success!", message, 0);
				cancelOrder();
			}
		}
		else
			_message("Incorrect Details!!", "Please enter correct information in all fields!", 0);
	
	}
}
