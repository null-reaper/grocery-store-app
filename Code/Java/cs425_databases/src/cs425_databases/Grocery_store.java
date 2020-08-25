package cs425_databases;

import java.awt.Image;

import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Grocery_store {

	private JFrame fStore1;
	private JTable table;
	private JTable table2;
	private JTable oTable;
	private JScrollPane pMain;
	private JScrollPane offersPane;
	
	private JCheckBox cMeat;
	private JCheckBox cFruits;
	private JCheckBox cBeverages;
	private JCheckBox cSnacks;
	private JCheckBox cFrozen;
	
	private JRadioButton rbr;
	private JRadioButton rbplh;
	private JRadioButton rbphl;
	
	private JTextField input;
	private String departments[] = {"meats", "fruits", "beverages", "snacks", "frozen"};
	private String filter;
	private ArrayList<String> categories = new ArrayList<String>();
	private ArrayList<String> tags = new ArrayList<String>();
	
	private int num_items = 0;
	
	private String userInfo[] = {"","", ""};
	
	private ArrayList<ArrayList<Object>> products;
	private ArrayList<ArrayList<Object>> offers;
	private ArrayList<ArrayList<Object>> orders;

	// Constructor
	public Grocery_store(String userInfo []) {
		this.userInfo = userInfo;
		
		if (Integer.parseInt(userInfo[1])/10000 == 4)
			userInfo[2] = "IN";
		else
			userInfo[2] = "IL";
		initialize();
	}

	// Initializer
	private void initialize() {
		
		fStore1 = new JFrame();
		fStore1.getContentPane().setBackground(Color.BLACK);
		fStore1.setResizable(false);
		fStore1.setTitle("https://www.store.com/home?user=customer");
		fStore1.setBounds(100, 100, 833, 617);
		fStore1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fStore1.getContentPane().setLayout(null);
		
		Image icon_image = new ImageIcon(this.getClass().getResource("/store.png")).getImage();
		ImageIcon icon = new ImageIcon(icon_image.getScaledInstance(120, 140, Image.SCALE_SMOOTH));
		
		Image hero_image = new ImageIcon(this.getClass().getResource("/hero.png")).getImage();
		ImageIcon hero = new ImageIcon(hero_image.getScaledInstance(855, 200, Image.SCALE_SMOOTH));
		
		Image offers_image = new ImageIcon(this.getClass().getResource("/offers.png")).getImage();
		ImageIcon offers = new ImageIcon(offers_image.getScaledInstance(160, 60, Image.SCALE_SMOOTH));
		
		JButton btnAccountInfo = new JButton("Account Info");
		btnAccountInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				displayAccountInfo();
			}
		});
		btnAccountInfo.setBounds(614, 70, 117, 29);
		fStore1.getContentPane().add(btnAccountInfo);
		
		JButton btnCart = new JButton("View Cart");
		btnCart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goToCart();
			}
		});
		btnCart.setBounds(502, 70, 117, 29);
		fStore1.getContentPane().add(btnCart);
		
		input = new JTextField();
		input.setBounds(157, 97, 429, 26);
		input.setText("");
		fStore1.getContentPane().add(input);
		input.setColumns(10);
		
		JButton bSearch = new JButton("Search");
		bSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search(input.getText());
			}
		});
		bSearch.setBounds(590, 97, 78, 29);
		fStore1.getContentPane().add(bSearch);
		
		pMain = new JScrollPane();
		pMain.setBounds(161, 135, 500, 450);
		fStore1.getContentPane().add(pMain);
		
		JLabel lblShopByDepartment = new JLabel("Shop By Department");
		lblShopByDepartment.setForeground(Color.WHITE);
		lblShopByDepartment.setBounds(15, 153, 133, 34);
		fStore1.getContentPane().add(lblShopByDepartment);
		
		cMeat = new JCheckBox("Meat & Seafood");
		cMeat.setForeground(Color.WHITE);
		cMeat.setBounds(7, 190, 133, 23);
		fStore1.getContentPane().add(cMeat);
		
		cFruits = new JCheckBox("Fruits & Vegetables");
		cFruits.setForeground(Color.WHITE);
		cFruits.setBounds(7, 215, 155, 23);
		fStore1.getContentPane().add(cFruits);
		
		cBeverages = new JCheckBox("Beverages");
		cBeverages.setForeground(Color.WHITE);
		cBeverages.setBounds(7, 240, 128, 23);
		fStore1.getContentPane().add(cBeverages);
		
		cSnacks = new JCheckBox("Snacks & Candy");
		cSnacks.setForeground(Color.WHITE);
		cSnacks.setBounds(7, 265, 142, 23);
		fStore1.getContentPane().add(cSnacks);
		
		cFrozen = new JCheckBox("Frozen Foods");
		cFrozen.setForeground(Color.WHITE);
		cFrozen.setBounds(7, 290, 128, 23);
		fStore1.getContentPane().add(cFrozen);
		
		JLabel lblFilterBy = new JLabel("Filter By");
		lblFilterBy.setForeground(Color.WHITE);
		lblFilterBy.setBounds(15, 350, 61, 16);
		fStore1.getContentPane().add(lblFilterBy);
		
		rbr = new JRadioButton("Relevance");
		rbr.setForeground(Color.WHITE);
		rbr.setBounds(7, 380, 94, 23);
		rbr.setSelected(true);
		fStore1.getContentPane().add(rbr);
		
		rbplh = new JRadioButton("Price: Low to High");
		rbplh.setForeground(Color.WHITE);
		rbplh.setBounds(7, 410, 147, 23);
		fStore1.getContentPane().add(rbplh);
		
		rbphl = new JRadioButton("Price: High to Low");
		rbphl.setForeground(Color.WHITE);
		rbphl.setBounds(7, 440, 149, 23);
		fStore1.getContentPane().add(rbphl);
		
		ButtonGroup filters = new ButtonGroup();
		filters.add(rbr);
		filters.add(rbphl);
		filters.add(rbplh);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logout();
			}
		});
		btnLogOut.setBounds(725, 70, 90, 29);
		fStore1.getContentPane().add(btnLogOut);
		
		JButton btnHome = new JButton("Home");
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goHome();
			}
		});
		btnHome.setBounds(154, 70, 117, 29);
		fStore1.getContentPane().add(btnHome);
		
		JButton btnAbout = new JButton("Contact Us");
		btnAbout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				displayContactInfo();
			}
		});
		btnAbout.setBounds(265, 70, 122, 29);
		fStore1.getContentPane().add(btnAbout);
		
		
		JButton btnShopPastPurchases = new JButton("Your Orders");
		btnShopPastPurchases.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showOrders();
			}
		});
		btnShopPastPurchases.setBounds(381, 70, 126, 29);
		fStore1.getContentPane().add(btnShopPastPurchases);
		
		JLabel offs = new JLabel("");
		offs.setForeground(Color.WHITE);
		offs.setBounds(665, 105, 164, 66);
		offs.setIcon(offers);
		fStore1.getContentPane().add(offs);
		
		JLabel str_icn = new JLabel("");
		str_icn.setBounds(21, 0, 128, 144);
		str_icn.setIcon(icon);
		fStore1.getContentPane().add(str_icn);
		
		JLabel lhero = new JLabel("");
		lhero.setBounds(2, -48, 829, 172);
		lhero.setIcon(hero);
		fStore1.getContentPane().add(lhero);
		
		offersPane = new JScrollPane();
		offersPane.setBounds(674, 180, 146, 405);
		fStore1.getContentPane().add(offersPane);
		
		createProductView();
		createOffersView();
		createOrdersView();
		goHome();
		displayOffers();
		
		fStore1.setVisible(true);
		
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
	
	// Function to Display Messsage in pop-up window
	private void _message(String title, String message, int type) {
		Object[] options = {"OK"};
		if (type == 1)
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		else if (type == 2)
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		else
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	// Function to Display Table in pop-up window
	private void _message(String title, JTable t) {
		JOptionPane.showConfirmDialog(null, new JScrollPane(t), title, JOptionPane.OK_CANCEL_OPTION);
	}
	
	// Function to Get Image from Source Folder
	private Icon _getImage(String filename) {
		Image icon_image = new ImageIcon(this.getClass().getResource("/" + filename)).getImage();
		return new ImageIcon(icon_image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	}
	
	// Function to Create Products' Table
	private void createProductView() {
		String[] columnNames = {"Picture", "Name", "Size", "Price", "More Info.", "Qty.", "Buy"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, false, false, true, true, true
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
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(5).setPreferredWidth(20);
        table.getColumnModel().getColumn(5).setPreferredWidth(20);
        table.getColumnModel().getColumn(6).setPreferredWidth(10);
        
        ShowMoreInfo moreInfo = new ShowMoreInfo(table, 4);
        AddToCartButton addToCart = new AddToCartButton(table, 6);
        pMain.setViewportView(table);
	}
	
	// Function to Create Offers' Table
	private void createOffersView() {
		
		String[] columnNames = {"", "", ""};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
            
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        
        table2 = new JTable(model);
        table2.setPreferredScrollableViewportSize(table.getPreferredSize());
        table2.setTableHeader(null);
        
        table2.setRowHeight(50);
        table2.getColumnModel().getColumn(0).setPreferredWidth(50);
        table2.getColumnModel().getColumn(1).setPreferredWidth(30);
        table2.getColumnModel().getColumn(2).setPreferredWidth(20);
        
        AddOfferButton addToCart = new AddOfferButton(table2, 2);
        
        offersPane.setViewportView(table2);
        
        displayOffers();
	}
	
	// Function to Create Orders' Table
	private void createOrdersView() {
		
		String[] columnNames = {"Order No.", "Product", "Qty", "Credit Card Used", "Status", "Time Stamp", "Action"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
            
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        oTable = new JTable(model);
        oTable.setPreferredScrollableViewportSize(oTable.getPreferredSize());
        
        oTable.setRowHeight(50);
        oTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        oTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        oTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        oTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        oTable.getColumnModel().getColumn(4).setPreferredWidth(50);
        oTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        
        OrderAction action = new OrderAction(oTable, 6);
        
	}

	// Function to Display Products
	private void displayProducts() {
		
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		
		int rowCount = dm.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		
		ArrayList<ArrayList<Object>> temp = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> rowData = new ArrayList<Object>();
		
		num_items = products.size() - 1;
		
		for(int i = 1; i < products.size(); i++) {
			rowData = new ArrayList<Object>();
			rowData.add(_getImage(products.get(i).get(5).toString()));
			rowData.add(products.get(i).get(1));
			rowData.add(products.get(i).get(2));
			rowData.add(products.get(i).get(7));
			rowData.add("See More");
			rowData.add("0");
			rowData.add("+");
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < num_items; i++) {
			dm.addRow(temp.get(i).toArray());
		}
	
	}
	
	// Function to Display Offers
	private void displayOffers() { 
		
		offers = SQLInterface.sqlExecute("select * from cproduct where state = '" + userInfo[2] + "' order by price asc;");
		for(int i = 6; i < offers.size(); i++) {
			offers.remove(6);
		}
		
		DefaultTableModel dm = (DefaultTableModel) table2.getModel();
		
		int rowCount = dm.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		
		ArrayList<ArrayList<Object>> temp = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> rowData = new ArrayList<Object>();
		
		num_items = offers.size() - 1;
		
		for(int i = 1; i < offers.size(); i++) {
			rowData = new ArrayList<Object>();
			rowData.add(_getImage(offers.get(i).get(5).toString()));
			rowData.add(offers.get(i).get(7));
			rowData.add("+");
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < num_items; i++) {
			dm.addRow(temp.get(i).toArray());
		}
	}
	
	// Function to Display Orders
	private void displayOrders() { 
		
		orders = SQLInterface.sqlExecute("select * from orders where cid = '"+userInfo[0]+"';");
		
		DefaultTableModel dm = (DefaultTableModel) oTable.getModel();
		
		int rowCount = dm.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		
		ArrayList<ArrayList<Object>> temp = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> rowData = new ArrayList<Object>();
		
		int num = orders.size() - 1;
		
		for(int i = 1; i < orders.size(); i++) {
			rowData = new ArrayList<Object>();
			rowData.add(orders.get(i).get(0));
			rowData.add(orders.get(i).get(2));
			rowData.add(orders.get(i).get(3));
			rowData.add(orders.get(i).get(4));
			rowData.add(orders.get(i).get(5));
			rowData.add(orders.get(i).get(6));
			if(orders.get(i).get(5).toString().equals("issued"))
				rowData.add("Delete");
			else if (orders.get(i).get(5).toString().equals("sent"))
				rowData.add("Acknowledge");
			else
				rowData.add("None");
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < num; i++) {
			dm.addRow(temp.get(i).toArray());
		}
	}
	
	// Function to Display Product Info
	public void displayProductInfo(int pnum) {
		_message("Nutrition Content", products.get(pnum+1).get(4).toString(), 2);
	}
		
	// Function to Check Selected Departments
	private void _checkDepartments() {
		categories.clear();
		if (cMeat.isSelected())
			categories.add(" category = '" + departments[0] + "' ");
		if (cFruits.isSelected())
			categories.add(" category = '" + departments[1] + "' ");
		if (cBeverages.isSelected())
			categories.add(" category = '" + departments[2] + "' ");
		if (cSnacks.isSelected())
			categories.add(" category = '" + departments[3] + "' ");
		if (cFrozen.isSelected())
			categories.add(" category = '" + departments[4] + "' ");
	} 
	
	// Function to Check Selected Filter
	private void _checkFilters() {
		if (rbr.isSelected())
			filter = "pid";
		else if (rbplh.isSelected())
			filter = "price asc";
		else if (rbphl.isSelected())
			filter = "price desc";
		else
			filter = "pid";
	}
	
	// Function to Get Tags from Inputted Text
	private void _getTags(String text) {
		tags.clear();
		for(String x : (text.toLowerCase()).split(" ")){
			tags.add(" pname like '%" + x + "%' ");
		}
	}
	
	// Function to Search Products
	private void search(String searchText) {
		
		_checkDepartments();
		_checkFilters();
		_getTags(searchText);
		
		if (searchText.equals("")) {
			goHome();
		}
		else {
			
			String code = "select * from cproduct where state = '" + userInfo[2] + "' ";
			if (tags.size()!=0 || categories.size()!=0) {
				code +=  " and (";
				if (tags.size()==0 && categories.size()!=0) {
					for(int i = 0; i < categories.size(); i++) {
						if (i > 0)
							code += "or";
						code += categories.get(i);
					}
					code += ") order by " + filter + ";";
				}
				else if (tags.size()!=0 && categories.size()==0) {
					for(int i = 0; i < tags.size(); i++) {
						if (i > 0)
							code += "or";
						code += tags.get(i);
					}
					code += ") order by " + filter + ";";
				}
				else {
					for(int i = 0; i < categories.size(); i++) {
						if (i > 0)
							code += "or";
						code += categories.get(i);
					}
					code += ") and (";
					for(int i = 0; i < tags.size(); i++) {
						if (i > 0)
							code += "or";
						code += tags.get(i);
					}
					code += ") order by " + filter + ";";
				}
			}
			else {
				code += " order by " + filter + ";";
			}
			
			products = SQLInterface.sqlExecute(code);
			displayProducts();
		}
	}
	
	// Function to Order Products by ID as in Database
	private void goHome() {
		products = SQLInterface.sqlExecute("select * from cproduct where state = '" + userInfo[2] + "' order by pid;");
		displayProducts();
	}
	
	// Function to Display Contact Info
	private void displayContactInfo() {
		_message("Contact Us", "\nContact our customer service \nteam to provide a comment or \nask a question about your local \nstore or our corporate headquarters.\n\nCall us at 1888-999-8999.", 2);
	}
	
	// Function to Show Orders
	private void showOrders() {
		displayOrders();
		_message("Your past purchases", oTable);
	}
	
	// Function to get Customer Details from Database
	private ArrayList<ArrayList<Object>> getCustomerDetails() {
		return SQLInterface.sqlExecute("select * from users, customer where uid = cid and cid = '" + userInfo[0]+"';");
	
	}
	
	// Function to Display Account Info
	private void displayAccountInfo() {
		
		ArrayList<ArrayList<Object>> data = getCustomerDetails();
		
		JTextField fname = new JTextField();
		fname.setText(data.get(1).get(4).toString());
		JTextField lname = new JTextField();
		lname.setText(data.get(1).get(5).toString());
		JTextField password = new JTextField();
		password.setText(data.get(1).get(2).toString());
		
		Object [] fields = {
				"First Name:", fname,
				"Last Name:", lname,
				"Username: " + data.get(1).get(1).toString(),
				"Password:", password,
				"Account Balance: $" + data.get(1).get(6).toString()
				
		};
		JOptionPane.showConfirmDialog(null, fields, "Edit Customer Details", JOptionPane.CANCEL_OPTION);
		
		updateCustomerInfo(fname.getText(), lname.getText(),password.getText());
		
	}
	
	// Function to Update Customer Info in Database
	private void updateCustomerInfo(String fname, String lname, String password) {
		if(SQLInterface.sqlUpdate("update users set upass = '" + password + "' where uid = '" + userInfo[0] + "';") == 0 ||
				SQLInterface.sqlUpdate("update customer set fname = '" + fname + "', lname = '"+ 
						lname +"' where cid = '" + userInfo[0] + "';") == 0)
			_message("Failed!", "Account update was unsuccessful. Please try again later. "
					+ "(NOTE: Duplicate items not alowed.)", 1);
		else
			_message("Success", "Your account details have been saved.", 0);
	}
	
	// Function to Add Item to Cart in Database
	public void addToCart(String pid, String qty) {
		
		int cartId = (Integer.parseInt(userInfo[0])%10000) + 80000;
		
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select * from cart where cid = '" + userInfo[0] + "';");
		if(SQLInterface.sqlUpdate("insert into cart values ('"+cartId+"', '"+userInfo[0]+"', '"+pid+"', '"+qty+"');") == 0)
			_message("Failed!", "Item not added to cart. Please try again later.", 1);
		else
			_message("Success", qty + " item(s) were added to your cart.", 0);
			
		
	}
	
	// Function to Cancel Order
	public void cancelOrder(int pnum) {
		
		Object order [] = orders.get(pnum+1).toArray();
		
		if (order[5].toString().equals("received")) 
			_message("Can't Cancel Order!!", "Completed orders cannot be cancelled!", 1);
		else {
			if(SQLInterface.sqlUpdate("delete from orders where oid = '"+order[0].toString()+"'"
					+ " and cid = '"+userInfo[0]+"' and pid = '"+order[2].toString()+"';") == 0)
				_message("Failed!", "Order cannot be cancelled. Please try again later.", 1);
			else {
				displayOrders();
				_message("Success", "Your order was successfully cancelled.", 0);
			}
		}
	}
	
	// Function to Add Product to the Cart
	public void addProduct(int pnum) {
		
		String qty = table.getModel().getValueAt(pnum, 5).toString(); 
		if (_isNumeric(qty) && Integer.parseInt(qty) > 0) {
			addToCart(products.get(pnum+1).get(0).toString(), qty); 
		}
	}
	
	// Function to Add Offer to the Cart
	private void addOffer(int pnum) {
	
		JTextField qty = new JTextField();
		Object [] fields = {
				"Enter Quantity:", qty
		};
		JOptionPane.showConfirmDialog(null, fields, "Add Item to Cart.", JOptionPane.CANCEL_OPTION);
		
		
		if (_isNumeric(qty.getText()) && Integer.parseInt(qty.getText()) > 0) {
			addToCart(offers.get(pnum+1).get(0).toString(), qty.getText()); 
		}
	}
	
	// Function to Log Out and go to the Main Screen
	private void logout() {
		fStore1.setVisible(false);
		new Login();
	}
	
	// Function to go to Cart Menu
	private void goToCart() {
		fStore1.setVisible(false);
		new CartMenu(userInfo);
	}
	
	// Function to Acknowledge Order
	private void gotOrder(int row) {
		String oid = orders.get(row+1).get(0).toString();
		
		if(SQLInterface.sqlUpdate("update orders set status = 'received' where oid = '"+oid+"';") == 0)
			_message("Failed!", "Could update order status. Please try again later.", 1);
		else
			_message("Success!", "Order status was successfully updated", 0);
		
		displayOrders();
	}
	
	// Class for Buttons in table
	class ShowMoreInfo extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public ShowMoreInfo(JTable table, int column) {
			super();
			this.table = table;
			renderButton = new JButton();

			editButton = new JButton();
			editButton.setFocusPainted(false);
			editButton.addActionListener(this);

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer(this);
			columnModel.getColumn(column).setCellEditor(this);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			renderButton.setText( (value == null) ? "+" : value.toString() );
			return renderButton;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			text = (value == null) ? "+" : value.toString();
			editButton.setText(text);
			return editButton;
		}

		public Object getCellEditorValue()
		{
			return text;
		}

		public void actionPerformed(ActionEvent e)
		{
			fireEditingStopped();
			displayProductInfo(table.getSelectedRow());
			
		}
	}
	
	// Class for Buttons in table
	class AddToCartButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public AddToCartButton(JTable table, int column) {
			super();
			this.table = table;
			renderButton = new JButton();

			editButton = new JButton();
			editButton.setFocusPainted(false);
			editButton.addActionListener(this);

			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(column).setCellRenderer(this);
			columnModel.getColumn(column).setCellEditor(this);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		{
			renderButton.setText( (value == null) ? "+" : value.toString() );
			return renderButton;
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			text = (value == null) ? "+" : value.toString();
			editButton.setText(text);
			return editButton;
		}

		public Object getCellEditorValue()
		{
			return text;
		}

		public void actionPerformed(ActionEvent e)
		{
			fireEditingStopped();
			addProduct(table.getSelectedRow());
		}
		
	}
	
		// Class for Buttons in table
		class AddOfferButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
			JTable table;
			JButton renderButton;
			JButton editButton;
			String text;

			public AddOfferButton(JTable table, int column) {
				super();
				this.table = table;
				renderButton = new JButton();

				editButton = new JButton();
				editButton.setFocusPainted(false);
				editButton.addActionListener(this);

				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(column).setCellRenderer(this);
				columnModel.getColumn(column).setCellEditor(this);
			}

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				renderButton.setText( (value == null) ? "+" : value.toString() );
				return renderButton;
			}

			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				text = (value == null) ? "+" : value.toString();
				editButton.setText(text);
				return editButton;
			}

			public Object getCellEditorValue()
			{
				return text;
			}

			public void actionPerformed(ActionEvent e)
			{
				fireEditingStopped();
				addOffer(table.getSelectedRow());
			}
			
	}
		
		// Class for Buttons in table
		class OrderAction extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
			JTable table;
			JButton renderButton;
			JButton editButton;
			String text;

			public OrderAction(JTable table, int column) {
				super();
				this.table = table;
				renderButton = new JButton();

				editButton = new JButton();
				editButton.setFocusPainted(false);
				editButton.addActionListener(this);

				TableColumnModel columnModel = table.getColumnModel();
				columnModel.getColumn(column).setCellRenderer(this);
				columnModel.getColumn(column).setCellEditor(this);
			}

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				renderButton.setText( (value == null) ? "+" : value.toString() );
				return renderButton;
			}

			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
				text = (value == null) ? "+" : value.toString();
				editButton.setText(text);
				return editButton;
			}

			public Object getCellEditorValue()
			{
				return text;
			}

			public void actionPerformed(ActionEvent e)
			{
				fireEditingStopped();
				int row = table.getSelectedRow();
				if (orders.get(row+1).get(5).toString().equals("issued"))
					cancelOrder(row);
				else if (orders.get(row+1).get(5).toString().equals("sent"))
					gotOrder(row);
			}
			
		}
}
