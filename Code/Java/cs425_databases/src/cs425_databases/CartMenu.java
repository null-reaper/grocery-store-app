package cs425_databases;

import java.awt.Font;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CartMenu {

	private JFrame fCart;
	private JScrollPane pMain;
	private JTable table;
	private JEditorPane moneyDetails;
	private String userInfo [] = {"","",""};
	private ArrayList<ArrayList<Object>> cart;
	private double checkoutInfo[] = {0.0,0.0,0.0,0.0,0.0};
	
	private int num_items = 0;
	
	private boolean cartIsOK = true;
	
	// Constructor
	public CartMenu(String userInfo []) {
		this.userInfo = userInfo;
		initialize();
	}

	// Initializer
	private void initialize() {
		
		fCart = new JFrame();
		fCart.setTitle("https://www.store.com/cart?status=default");
		fCart.getContentPane().setBackground(Color.DARK_GRAY);
		fCart.setBackground(Color.BLACK);
		fCart.setBounds(100, 100, 898, 406);
		fCart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fCart.getContentPane().setLayout(null);
		
		JLabel top = new JLabel("    Your Cart");
		top.setForeground(Color.WHITE);
		top.setFont(new Font("Apple Braille", Font.PLAIN, 13));
		Image Images7=new ImageIcon (this.getClass().getResource("/shopping-cart-icon.png")).getImage();
		top.setIcon(new ImageIcon(Images7));
		top.setBounds(688, 20, 139, 48);
		fCart.getContentPane().add(top, BorderLayout.WEST);
		
		JButton bUpdate = new JButton("Update Cart");
		bUpdate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateCart();
			}
		});
		bUpdate.setForeground(new Color(30, 144, 255));
		bUpdate.setBackground(new Color(255, 255, 255));
		bUpdate.setBounds(705, 90, 117, 29);
		fCart.getContentPane().add(bUpdate);
		
		JButton bContinue = new JButton("Continue Shopping");
		bContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				continueShopping();
			}
		});
		bContinue.setForeground(new Color(30, 144, 255));
		bContinue.setBackground(new Color(30, 144, 255));
		bContinue.setBounds(687, 130, 156, 29);
		fCart.getContentPane().add(bContinue);
		
		JButton bCheckout = new JButton("Checkout");
		bCheckout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkout();
			}
		});
		bCheckout.setForeground(new Color(255, 0, 0));
		bCheckout.setBackground(Color.WHITE);
		bCheckout.setBounds(705, 325, 117, 29);
		fCart.getContentPane().add(bCheckout);
		
		moneyDetails = new JEditorPane();
		moneyDetails.setForeground(new Color(0, 0, 128));
		moneyDetails.setFont(new Font("Lucida Bright", Font.PLAIN, 13));
		moneyDetails.setBackground(new Color(192, 192, 192));
		moneyDetails.setText("\n Subtotal                           $0.0\n\n Delivery Fee                     $0.0\n\n Tax                                   $0.0\n\n Total                                $0.0");
		moneyDetails.setBounds(652, 179, 227, 181);
		moneyDetails.setEditable(false);
		fCart.getContentPane().add(moneyDetails);
		
		pMain = new JScrollPane();
        pMain.setBounds(30, 20, 590, 340);
        fCart.getContentPane().add(pMain);
		
		createCartView();
		displayCartItems();
		
		fCart.setVisible(true);
		
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
	
	// Function to Display Image from Source Folder
	private Icon _getImage(String filename) {
		Image icon_image = new ImageIcon(this.getClass().getResource("/" + filename)).getImage();
		return new ImageIcon(icon_image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	}
	
	// Function to Get Items from Cart in the Database
	public ArrayList<ArrayList<Object>> getCart() {
		return SQLInterface.sqlExecute("select * from cart natural join cproduct where cid = '" + userInfo[0]+"' and state = '"+userInfo[2]+"';");
	}
	
	// Function to Create Cart Table
	private void createCartView() {
		
		String[] columnNames = {"Picture", "Name", "Price", "Qty."};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
            
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        
        pMain.setViewportView(table);
    
	}
	
	// Function to Display Cart Items
	private void displayCartItems() {
		
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		
		int rowCount = dm.getRowCount();

		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		
		ArrayList<ArrayList<Object>> temp = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> rowData = new ArrayList<Object>();
		
		cart = getCart();
		
		num_items = cart.size() - 1;
		
		for(int i = 1; i < cart.size(); i++) {
			rowData = new ArrayList<Object>();
			rowData.add(_getImage(cart.get(i).get(8).toString()));
			rowData.add(cart.get(i).get(4));
			rowData.add(cart.get(i).get(10));
			rowData.add(cart.get(i).get(3));
			temp.add(rowData);
		}
		
		
		for(int i = 0 ; i < num_items; i++) {
			dm.addRow(temp.get(i).toArray());
		}
		
		createCheckoutInfo();
	}
	
	// Function to Update Cart Items in Database
	private void updateCart() {

		cartIsOK = true;
		String qty = "0";
		String num = "";
		int update = 0;
		
		for (int i = 0; i < num_items; i++) {
			qty = table.getModel().getValueAt(i, 3).toString(); 
			if (!_isNumeric(qty) || !(Integer.parseInt(qty) >= 0))
				cartIsOK = false;
		}
		
		if (cartIsOK) {
			
			for(int i = 0; i < num_items; i++) {
				num = table.getModel().getValueAt(i, 3).toString();
				if (Integer.parseInt(num) == 0) {
					update = SQLInterface.sqlUpdate("delete from cart where pid = '"+ cart.get(i+1).get(0).toString() +"';");
				}
				else {
					update = SQLInterface.sqlUpdate("update cart set qty = '"+num+"' where pid = '"+ cart.get(i+1).get(0).toString() +"';");
				}
			}
			
			if (update == 0)
				_message("Failed!", "Cart update was unsuccessful. Please try again later.", 1);
			else {
				displayCartItems();
				_message("Success!", "The items in your cart have been successfully updated!", 0);
			}
			
		}
		else {
			_message("Invalid Items!", "Some of the items in the cart have invalid quantities.", 1);
		}
		
	}
	
	// Function to return to the Shopping Menu
	private void continueShopping() {
		fCart.setVisible(false);
		new Grocery_store(userInfo);
	}
	
	// Function to go to the Checkout Menu
	private void checkout() {
		createCheckoutInfo();
		fCart.setVisible(false);
		new CheckoutMenu(userInfo, checkoutInfo);
	}
	
	// Function to Calculate Checkout Related Info
	private void createCheckoutInfo() {
		
		checkoutInfo[0] = 0.0;
		
		for(int i = 1; i < cart.size(); i++) {
			checkoutInfo[0] += Double.parseDouble(cart.get(i).get(10).toString()) * Double.parseDouble(cart.get(i).get(3).toString());
		}
		
		checkoutInfo[0] = ((int)(100*checkoutInfo[0]))/100.0;
		checkoutInfo[1] = (checkoutInfo[0]*0.05);
		checkoutInfo[1] = ((int)(100*checkoutInfo[1]))/100.0;
		checkoutInfo[2] = (checkoutInfo[0]*0.1);
		checkoutInfo[2] = ((int)(100*checkoutInfo[2]))/100.0;
		checkoutInfo[3] = checkoutInfo[0] + checkoutInfo[1] + checkoutInfo[2];
		checkoutInfo[3] = ((int)(100*checkoutInfo[3]))/100.0;
		checkoutInfo[4] = countItems();
		
		
		displayCheckoutInfo();
	}
	
	// Function to count number of unique items in the cart
	private double countItems() {
		return (double)(cart.size()-1);
	}
	
	// Function to Display Checkout Related Info
	private void displayCheckoutInfo() {
		moneyDetails.setText("\n Subtotal                           "
				+ "$" + checkoutInfo[0] + "\n\n Delivery Fee                     "
						+ "$" + checkoutInfo[1] + "\n\n Tax                                   "
								+ "$" + checkoutInfo[2] + "\n\n Total                                "
										+ "$" + checkoutInfo[3]);
	}
	
}
