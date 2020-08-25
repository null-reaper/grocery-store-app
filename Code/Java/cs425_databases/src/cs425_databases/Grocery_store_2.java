package cs425_databases;

import java.awt.Image;

import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Grocery_store_2 {

	private JTable table;
	private JTable cTable;
	private JTable sTable;
	private JTable spTable;
	private JTable oTable;
	private JScrollPane pMain;
	private JFrame fStore2;
	
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
	
	private int num_items;
	
	private String userInfo [] = {"","","",""};
	
	private ArrayList<ArrayList<Object>> products;
	private ArrayList<ArrayList<Object>> customers;
	private ArrayList<ArrayList<Object>> suppliers;
	private ArrayList<ArrayList<Object>> supply;
	private ArrayList<ArrayList<Object>> orders;

	// Constructor
	public Grocery_store_2(String userInfo []) {
		this.userInfo = userInfo;
		num_items = 0;
		initialize();
	}
		
	// Initializer
	public void initialize() {
		fStore2 = new JFrame();
		fStore2.getContentPane().setBackground(Color.BLACK);
		fStore2.setForeground(Color.BLACK);
		fStore2.setBackground(Color.BLACK);
		fStore2.setTitle("https://www.store.com/home?user=staff");
		fStore2.setBounds(100, 100, 814, 617);
		fStore2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fStore2.getContentPane().setLayout(null);
		
		Image hero_image = new ImageIcon(this.getClass().getResource("/hero.png")).getImage();
		ImageIcon hero = new ImageIcon(hero_image.getScaledInstance(815, 200, Image.SCALE_SMOOTH));
		
		Image icon_image = new ImageIcon(this.getClass().getResource("/store.png")).getImage();
		ImageIcon icon = new ImageIcon(icon_image.getScaledInstance(120, 140, Image.SCALE_SMOOTH));
		
		JLabel str_icn = new JLabel("");
		str_icn.setBounds(21, 0, 128, 144);
		str_icn.setIcon(icon);
		fStore2.getContentPane().add(str_icn);
		
		input = new JTextField();
		input.setColumns(10);
		input.setBounds(157, 97, 565, 26);
		fStore2.getContentPane().add(input);
		
		JButton bSearch = new JButton("Search");
		bSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				search(input.getText());
			}
		});
		bSearch.setBounds(734, 97, 78, 29);
		fStore2.getContentPane().add(bSearch);
		
		JButton bLogout = new JButton("Log Out");
		bLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logout();
			}
		});
		bLogout.setBounds(710, 70, 102, 29);
		fStore2.getContentPane().add(bLogout);
		
		JButton bInfo = new JButton("Account Info");
		bInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				displayAccountInfo();
			}
		});
		bInfo.setBounds(572, 70, 139, 29);
		fStore2.getContentPane().add(bInfo);
		
		JButton bHome = new JButton("Home");
		bHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				goHome();
			}
		});
		bHome.setBounds(154, 70, 117, 29);
		fStore2.getContentPane().add(bHome);
		
		JButton bCustomers = new JButton("Check Customers");
		bCustomers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkCustomers();
			}
		});
		bCustomers.setBounds(269, 70, 155, 29);
		fStore2.getContentPane().add(bCustomers);
		
		JButton bOrders = new JButton("Manage Orders");
		bOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				manageOrders();
			}
		});
		bOrders.setBounds(7, 510, 147, 29);
		fStore2.getContentPane().add(bOrders);
		
		pMain = new JScrollPane();
		pMain.setBounds(161, 135, 643, 409);
		fStore2.getContentPane().add(pMain);
		
		JLabel lblFilterBy = new JLabel("Filter By");
		lblFilterBy.setForeground(Color.WHITE);
		lblFilterBy.setBounds(15, 350, 61, 16);
		fStore2.getContentPane().add(lblFilterBy);
		
		rbr = new JRadioButton("Relevance");
		rbr.setForeground(Color.WHITE);
		rbr.setBounds(7, 380, 94, 23);
		fStore2.getContentPane().add(rbr);
		
		rbplh = new JRadioButton("Price: Low to High");
		rbplh.setForeground(Color.WHITE);
		rbplh.setBounds(7, 410, 147, 23);
		fStore2.getContentPane().add(rbplh);
		
		rbphl = new JRadioButton("Price: High to Low");
		rbphl.setForeground(Color.WHITE);
		rbphl.setBounds(7, 440, 149, 23);
		fStore2.getContentPane().add(rbphl);
		
		ButtonGroup filters = new ButtonGroup();
		filters.add(rbr);
		filters.add(rbphl);
		filters.add(rbplh);
		
		JLabel lblShopByDepartment = new JLabel("Search By Department");
		lblShopByDepartment.setForeground(Color.WHITE);
		lblShopByDepartment.setBounds(15, 153, 139, 34);
		fStore2.getContentPane().add(lblShopByDepartment);
		
		cMeat = new JCheckBox("Meat & Seafood");
		cMeat.setForeground(Color.WHITE);
		cMeat.setBounds(7, 190, 133, 23);
		fStore2.getContentPane().add(cMeat);
		
		cFruits = new JCheckBox("Fruits & Vegetables");
		cFruits.setForeground(Color.WHITE);
		cFruits.setBounds(7, 215, 155, 23);
		fStore2.getContentPane().add(cFruits);
		
		cBeverages = new JCheckBox("Beverages");
		cBeverages.setForeground(Color.WHITE);
		cBeverages.setBounds(7, 240, 128, 23);
		fStore2.getContentPane().add(cBeverages);
		
		cSnacks = new JCheckBox("Snacks & Candy");
		cSnacks.setForeground(Color.WHITE);
		cSnacks.setBounds(7, 265, 142, 23);
		fStore2.getContentPane().add(cSnacks);
		
		cFrozen = new JCheckBox("Frozen Foods");
		cFrozen.setForeground(Color.WHITE);
		cFrozen.setBounds(7, 290, 128, 23);
		fStore2.getContentPane().add(cFrozen);
		
		JButton btnCommit = new JButton("Commit Changes");
		btnCommit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editProducts();
			}
		});
		btnCommit.setBounds(154, 556, 658, 29);
		fStore2.getContentPane().add(btnCommit);
		
		JButton btnCreateNewProduct = new JButton("Create New Product");
		btnCreateNewProduct.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				createNewProduct();
			}
		});
		btnCreateNewProduct.setBounds(7, 480, 147, 26);
		fStore2.getContentPane().add(btnCreateNewProduct);
		
		JButton btnSeller = new JButton("Check Suppliers");
		btnSeller.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				checkSuppliers();
			}
		});
		btnSeller.setBounds(425, 70, 147, 29);
		fStore2.getContentPane().add(btnSeller);
		
		JLabel lhero = new JLabel("");
		lhero.setBounds(0, -48, 829, 172);
		lhero.setIcon(hero);
		fStore2.getContentPane().add(lhero);
		
		createProductView();
		createCustomerView();
		createSupplierView();
		createSupplyView();
		createOrdersView();
		goHome();
		
		fStore2.setVisible(true);
	}
	
	// Function to check if String is Numeric
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
	
	// Function to check if String is Double
	public boolean _isDouble(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
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
	
	// Function to Display Table in pop-up window
	private void _message(String title, JTable t) {
		JOptionPane.showConfirmDialog(null, new JScrollPane(t), title, JOptionPane.OK_CANCEL_OPTION);
	}
	
	// Function to Image from Source Folder
	private Icon _getImage(String filename) {
		Image icon_image = new ImageIcon(this.getClass().getResource("/" + filename)).getImage();
		return new ImageIcon(icon_image.getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	}
	
	// Function to Round off Floating-Point Numbers
	public double _round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	// Function to Create Products' Table
	private void createProductView() {
		String[] columnNames = {"Picture", "Name", "Size", "Price", "More Info", "State", "Availability", "Add Stock", "Delete"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, true, true, true, true, false, true, true, true
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
        table.getColumnModel().getColumn(1).setPreferredWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(50);
        table.getColumnModel().getColumn(3).setPreferredWidth(40);
        table.getColumnModel().getColumn(4).setPreferredWidth(60);
        table.getColumnModel().getColumn(5).setPreferredWidth(20);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
        table.getColumnModel().getColumn(7).setPreferredWidth(50);
        table.getColumnModel().getColumn(8).setPreferredWidth(30);
        
        ShowMoreInfo moreInfo = new ShowMoreInfo(table, 4);
        CheckAvailable check = new CheckAvailable(table, 6);
        AddStock add = new AddStock(table, 7);
        DeleteProduct delete = new DeleteProduct(table, 8);
        pMain.setViewportView(table);
	}
	
	// Function to Create Customers' Table
	private void createCustomerView() {
		String[] columnNames = {"Customer ID", "First Name", "Last Name", "Balance"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
            
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        cTable = new JTable(model);
        cTable.setPreferredScrollableViewportSize(cTable.getPreferredSize());
        
        cTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        cTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        cTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        cTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        
	}
	
	// Function to Create Suppliers' Table
	private void createSupplierView() {
		String[] columnNames = {"Supplier ID", "First Name", "Last Name", "Street", "City", "State", "Zipcode", "Products Sold"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
            
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        sTable = new JTable(model);
        sTable.setPreferredScrollableViewportSize(sTable.getPreferredSize());
        
        sTable.setRowHeight(50);
        sTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        sTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        sTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        sTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        sTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        sTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        sTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        sTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        
        SupplierProducts supply = new SupplierProducts(sTable, 7);
	}
	
	// Function to Create Supply Table
	private void createSupplyView() {
		String[] columnNames = {"Image", "Product Name", "Selling Price"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
            
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        spTable = new JTable(model);
        spTable.setPreferredScrollableViewportSize(spTable.getPreferredSize());
        
        spTable.setRowHeight(50);
        spTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        spTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        spTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        
	}
	
	// Function to Create Orders' Table
	private void createOrdersView() {
		
		String[] columnNames = {"Order No.", "CustomerID", "Product", "Qty", "Street", "City", "State", "Zipcode", "Status", "Time Stamp", "Action"};
        Object[][] data = {};

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
        	boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false, false, false, true
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
        oTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(3).setPreferredWidth(30);
        oTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(5).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(9).setPreferredWidth(80);
        oTable.getColumnModel().getColumn(10).setPreferredWidth(120);
        
        Order order = new Order(oTable, 10);
        
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
			rowData.add(products.get(i).get(6));
			rowData.add("Check");
			rowData.add("+");
			rowData.add("-");
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < num_items; i++) {
			dm.addRow(temp.get(i).toArray());
		}
	
	}
	
	// Function to Check If Constraints are satisfied on entered values
	private boolean checkValues() {
		boolean temp = true;
		for(int i = 0; i < table.getModel().getRowCount(); i++) {
			if(!_isDouble(table.getModel().getValueAt(i, 2).toString()) || !_isDouble(table.getModel().getValueAt(i, 3).toString()))
				temp = false;
		}
		return temp;
	}
	
	// Function to Edit Products in the Database
	private void editProducts() {
		if(checkValues()) {
			
			int update = 0;
			
			for(int i = 1; i < products.size(); i++) {
			update = SQLInterface.sqlUpdate("update product set pname = '"+table.getModel().getValueAt(i-1, 1).toString()+"', size = '"+table.getModel().getValueAt(i-1, 2).toString()+"' where pid = '"+products.get(i).get(0).toString()+"';");
			update = SQLInterface.sqlUpdate("update pprice set price = '"+table.getModel().getValueAt(i-1, 3).toString()+"' where pid = '"+products.get(i).get(0).toString()+"' and state = '"+table.getModel().getValueAt(i-1, 5).toString()+"';");

			}
			if(update == 0)
				_message("Failed!", "Unable to update products. Please try again later.", 1);
			else {
				_message("Success!", "Items were successfully updated!", 0);
			}
		}
		else
			_message("Invalid Details!!", "Please enter valid information in all fields!", 0);
	
		goHome();
	}
	
	// Function to Display Customers
	private void displayCustomers() {
		
		customers = SQLInterface.sqlExecute("select *  from customer;");
		
		DefaultTableModel dm = (DefaultTableModel) cTable.getModel();
		
		int rowCount = dm.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		
		ArrayList<ArrayList<Object>> temp = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> rowData = new ArrayList<Object>();
		
		int cnum = customers.size() - 1;
		
		for(int i = 1; i < customers.size(); i++) {
			rowData = new ArrayList<Object>();
			rowData.add(customers.get(i).get(0));
			rowData.add(customers.get(i).get(1));
			rowData.add(customers.get(i).get(2));
			rowData.add("$" + customers.get(i).get(3));
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < cnum; i++) {
			dm.addRow(temp.get(i).toArray());
		}
	
	}

	// Function to Display Suppliers
	private void displaySuppliers() {
		
		suppliers = SQLInterface.sqlExecute("select *  from supplier;");
		
		DefaultTableModel dm = (DefaultTableModel) sTable.getModel();
		
		int rowCount = dm.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		
		ArrayList<ArrayList<Object>> temp = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> rowData = new ArrayList<Object>();
		
		int snum = suppliers.size() - 1;
		
		for(int i = 1; i < suppliers.size(); i++) {
			rowData = new ArrayList<Object>();
			rowData.add(suppliers.get(i).get(0));
			rowData.add(suppliers.get(i).get(1));
			rowData.add(suppliers.get(i).get(2));
			rowData.add(suppliers.get(i).get(3));
			rowData.add(suppliers.get(i).get(4));
			rowData.add(suppliers.get(i).get(5));
			rowData.add(suppliers.get(i).get(6));
			rowData.add("Show");
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < snum; i++) {
			dm.addRow(temp.get(i).toArray());
		}
	
	}
	
	// Function to Display Orders
	private void displayOrders() { 
		
		orders = SQLInterface.sqlExecute("select * from sorder;");
		
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
			rowData.add(orders.get(i).get(1));
			rowData.add(orders.get(i).get(2));
			rowData.add(orders.get(i).get(3));
			rowData.add(orders.get(i).get(4));
			rowData.add(orders.get(i).get(5));
			rowData.add(orders.get(i).get(6));
			rowData.add(orders.get(i).get(7));
			rowData.add(orders.get(i).get(8));
			rowData.add(orders.get(i).get(9));
			if (orders.get(i).get(8).toString().equals("issued"))
				rowData.add("Complete Order");
			else
				rowData.add("None");
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < num; i++) {
			dm.addRow(temp.get(i).toArray());
		}
	}
	
	// Function to Display Products Info
	public void displayProductInfo(int pnum) {
		
		JTextField pinfo = new JTextField();
		pinfo.setText(products.get(pnum+1).get(4).toString());
		
		Object [] fields = {
				"Nutrition Content:    ", pinfo
				
		};
		JOptionPane.showConfirmDialog(null, fields, "Edit Additional Information", JOptionPane.CANCEL_OPTION);
		saveProductInfo(pnum, pinfo.getText());
	}
	
	// Function to Save Product Info in Database
	public void saveProductInfo(int pnum, String info) {
		if(SQLInterface.sqlUpdate("update product set info = '"+info+"' where pid = '"+products.get(pnum+1).get(0).toString()+"';") == 0)
			_message("Failed!", "Unable to update product info. Please try again later.", 1);
		else
			_message("Success!", "Product info was modified successfully!", 0);
		products = SQLInterface.sqlExecute("select * from cproduct;");
		goHome();
	}
	
	// Function to Create New Product
	private void createNewProduct() {
		JTextField pName = new JTextField();
		JComboBox<Double> size = new JComboBox<Double>();
		double i = 0.1;
		while(i < 10.0) {
			size.addItem(_round(i,2));
			i += 0.1;
		}
		JComboBox<String> category = new JComboBox<String>();
		category.addItem("Meat & Seafood");
		category.addItem("Fruits & Vegetables");
		category.addItem("Beverages");
		category.addItem("Snacks & Candy");
		category.addItem("Frozen Foods");
		JTextField info = new JTextField();
		JComboBox<String> state = new JComboBox<String>();
		state.addItem("IL");
		state.addItem("IN");
		JComboBox<Double> price = new JComboBox<Double>();
		i = 0.1;
		while(i < 100.0) {
			price.addItem(_round(i,2));
			i += 0.1;
		}
		
		Object [] fields = {
				"Product Name:", pName,
				"Size (in cu. ft.):", size,
				"Category:", category,
				"Additional Info:", info,
				"State:", state,
				"Price (in $):", price
				
		
		};
		
		JOptionPane.showConfirmDialog(null, fields, "Create a New Product", JOptionPane.CANCEL_OPTION);
		
		String dept = "";
		if(category.getSelectedItem().toString().equals("Meat & Seafood"))
			dept = "meats";
		else if(category.getSelectedItem().toString().equals("Fruits & Vegetables"))
			dept = "fruits";
		else if(category.getSelectedItem().toString().equals("Beverages"))
			dept = "beverages";
		else if(category.getSelectedItem().toString().equals("Snacks & Candy"))
			dept = "snacks";
		else if(category.getSelectedItem().toString().equals("Frozen Foods"))
			dept = "frozen";
		else
			dept = "meats";
			
		if(addProductToDatabase(pName.getText(), size.getSelectedItem().toString(), dept, info.getText(),state.getSelectedItem().toString(), price.getSelectedItem().toString()))
			_message("Success!", "The product was successfully created!", 0);
		else
			_message("Failed!", "The product was not created. Please try again later.", 1);
		
		goHome();
	}
		
	// Function to Add New Product to Database
	private boolean addProductToDatabase(String pname, String size, String category, String info, String state, String price) {
		int update1 = 0;
		int update2 = 0;
		int id = 30001;
		
		if(pname.equals("") || info.equals(""))
			return false;
		else {
			ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select max(pid) from product;");
			if (data.get(1).get(0) != null) {
				id = Integer.parseInt(data.get(1).get(0).toString()) + 1;
			}
		
			try {
				update1 = SQLInterface.sqlUpdate("insert into product values ('"+id+"', '"+pname+"', '"+size+"', '"+category+"', '"+info+"', 'default.png');");
				update2 = SQLInterface.sqlUpdate("insert into pprice values ('"+id+"', '"+state+"', '"+price+"');");
			}
			catch (Exception e) {
				return false;
			}
			
			if(update1 == 0 || update2 == 0)
				return false;
			else
				return true;
			}
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
	
	// Function to Get Tags from Entered Text
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
			String code = "select * from cproduct";
			if (tags.size()!=0 || categories.size()!=0) {
				code +=  " where (";
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
	
	// Function to arrange Products by ID as in Database
	private void goHome() {
		products = SQLInterface.sqlExecute("select * from cproduct order by pid;");
		displayProducts();
	}
	
	// Function to Log Out and go to the Main Screen
	private void logout() {
		fStore2.setVisible(false);
		new Login();
	}
	
	// Function to Get Customer Data
	private void checkCustomers() {
		displayCustomers();
		_message("Check Customers", cTable);
	}

	// Function to Get Supplier Data
	private void checkSuppliers() {
		displaySuppliers();
		_message("Check Suppliers", sTable);
	}
	
	// Function to Manage Orders
	private void manageOrders() {
			displayOrders();
			_message("Manage Orders", oTable);
	}
	
	// Function to Get Staff Details fromthe Database
	private ArrayList<ArrayList<Object>> getStaffDetails() {
		return SQLInterface.sqlExecute("select * from users, staff where uid = sid and sid = '" + userInfo[0]+"';");
	}
	
	// Function to Display Account Details
	private void displayAccountInfo() {
		
		ArrayList<ArrayList<Object>> data = getStaffDetails();
		
		JTextField fname = new JTextField();
		fname.setText(data.get(1).get(4).toString());
		JTextField lname = new JTextField();
		lname.setText(data.get(1).get(5).toString());
		JTextField password = new JTextField();
		password.setText(data.get(1).get(2).toString());
		JTextField street = new JTextField();
		street.setText(data.get(1).get(6).toString());
		JTextField city = new JTextField();
		city.setText(data.get(1).get(7).toString());
		
		
		Object [] fields = {
				"First Name:", fname,
				"Last Name:", lname,
				"Username: " + data.get(1).get(1).toString(),
				"Password:", password,
				"Salary: $" + data.get(1).get(10).toString(),
				"Job Title: " + data.get(1).get(11).toString().toUpperCase(),
				"Street: ", street,
				"City: ", city,
				"State: " + data.get(1).get(8).toString(),
				"Zipcode: " + data.get(1).get(9).toString()
		};
		JOptionPane.showConfirmDialog(null, fields, "Edit Customer Details", JOptionPane.CANCEL_OPTION);
		
		updateStaffInfo(fname.getText(), lname.getText(), password.getText(), street.getText(), city.getText());
		
	}
	
	// Function to Update Account Details in the Database
	private void updateStaffInfo(String fname, String lname, String password, String street, String city) {
		
		if(SQLInterface.sqlUpdate("update users set upass = '" + password + "' where uid = '" + userInfo[0] + "';") == 0 ||
				SQLInterface.sqlUpdate("update staff set fname = '" + fname + "', lname = '"+ lname +"',  street = '"+street+"', city = '"+city+"' where sid = '" + userInfo[0] + "';") == 0)
			_message("Failed!", "Account update was unsuccessful. Please try again later.", 1);
		else
			_message("Success", "Your account details have been saved.", 0);
	}
	
	// Function to Delete Product from the Database
	private void deleteProductFromDatabase(int row) {
		String pid = products.get(row+1).get(0).toString();
		int update = 0;
		
		SQLInterface.sqlUpdate("delete from sells where pid = '"+pid+"';");
		SQLInterface.sqlUpdate("delete from stock where pid = '"+pid+"';");
		SQLInterface.sqlUpdate("delete from pprice where pid = '"+pid+"';");
		update = SQLInterface.sqlUpdate("delete from product where pid = '"+pid+"';");
		
		if(update == 0)
			_message("Failed!", "Product was not deleted successfully. Please try again later.", 1);
		else
			_message("Success!!", "Product was deleted successfully!", 0);
		
		goHome();
	}
	
	// Function to Add Stock
	private void addStock(int row) {
		String pid = products.get(row+1).get(0).toString();
		
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select wid from warehouse;"); 
		
		JComboBox<String> warehouse = new JComboBox<String>();
		for(int i = 1; i < data.size(); i++) {
			warehouse.addItem(data.get(i).get(0).toString());
		}
		JComboBox<Integer> qty = new JComboBox<Integer>();
		for(int i = 0; i < 1000; i++) {
			qty.addItem(i);
		}
		
		Object [] fields = {
				"Warehouse:", warehouse,
				"Qty:", qty	
		};
		JOptionPane.showConfirmDialog(null, fields, "Add Stock to Warehouse", JOptionPane.CANCEL_OPTION);
		
		addStockToDatabase(pid, warehouse.getSelectedItem().toString(), qty.getSelectedItem().toString());
			
	}
	
	// Function to Add Stock to the Database
	private void addStockToDatabase(String pid, String wid, String qty) {
		String capacity = "";
		
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select qty,size from product natural join stock where wid = '"+wid+"';");
		double total= 0.0;
		for(int i = 1; i<data.size(); i++) {
			total += Double.parseDouble(data.get(i).get(0).toString()) * Double.parseDouble(data.get(i).get(1).toString());
		}
		total += Double.parseDouble(SQLInterface.sqlExecute("select size from product where pid = '"+pid+"';").get(1).get(0).toString())*Double.parseDouble(qty);
		
		capacity = SQLInterface.sqlExecute("select capacity from warehouse where wid = '"+wid+"';").get(1).get(0).toString();
		
		if(!qty.equals("0")) {
			if(total > Double.parseDouble(capacity))
				_message("No Space!", "Stock cannot be added! Warehouse capacity exceeded!", 1);
			else {
				if(SQLInterface.sqlUpdate("update stock set qty = qty + "+qty+" where pid = '"+pid+"' and wid = '"+wid+"';") == 0) {
					if(SQLInterface.sqlUpdate("insert into stock values ('"+pid+"', '"+wid+"', '"+qty+"');") == 0)
						_message("Failed!", "Stocks update failed. Please try again later!", 1);
					else
						_message("Success!", "Stocks were updated successfully!", 0);
				}
				else
					_message("Success!", "Stocks were updated successfully!", 0);
			}
		}
		else 
			_message("Stock Update Unsuccessful!", "Please enter a quantity greater than 0!", 1);
	}
	
	// Function to Check Availability of Products
	private void checkAvailable(int row) {

		String pid = products.get(row+1).get(0).toString();
		
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select * from warehouse;"); 
		
		JComboBox<String> warehouse = new JComboBox<String>();
		for(int i = 1; i < data.size(); i++) {
			warehouse.addItem(data.get(i).get(0).toString());
		}
		JLabel qty = new JLabel();
		qty.setText(getQty(pid, warehouse.getSelectedItem().toString()));
		JLabel address = new JLabel();
		address.setText(getAddress(pid, warehouse.getSelectedItem().toString()));
		warehouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				qty.setText(getQty(pid, warehouse.getSelectedItem().toString()));
				address.setText(getAddress(pid, warehouse.getSelectedItem().toString()));
			}
		});
		
		Object [] fields = {
				"Warehouse:", warehouse,
				"Address: ", address, " ",
				"Quantity: ", qty
				
		};
		JOptionPane.showConfirmDialog(null, fields, "Check Availability", JOptionPane.CANCEL_OPTION);
	}
	
	// Function to Get Quantity of a Product in a Warehouse
	private String getQty(String pid, String warehouse) {
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select wid, qty from stock where pid = '"+pid+"';"); 
		for(int i = 1; i < data.size(); i++) {
			if(warehouse.equals(data.get(i).get(0).toString())) {
				return data.get(i).get(1).toString();
			}	
		}
		return "0";
	}
	
	// Function to Get Address of a Warehouse
	private String getAddress(String pid, String warehouse) {
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select * from warehouse where wid = '"+warehouse+"';"); 
		for(int i = 1; i < data.size(); i++) {
			if(warehouse.equals(data.get(i).get(0).toString())) {
				return data.get(i).get(1).toString()+", "+data.get(i).get(2).toString()+", "+data.get(i).get(3).toString()+" "+data.get(i).get(4).toString();
			}	
		}
		return "...";
	}
	
	// Function to Complete Orders
	private void completeOrder(int row) {
		String oid = orders.get(row+1).get(0).toString();
		String pid = orders.get(row+1).get(2).toString();
		String qtyr = orders.get(row+1).get(3).toString();
		String cnumber = orders.get(row+1).get(4).toString();
		String zip = "" + orders.get(row+1).get(7).toString().charAt(0);
		
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select wid from warehouse where zip like '"+zip+"%';");
		
		JComboBox<String> warehouse = new JComboBox<String>();
		for(int i = 1; i < data.size(); i++) {
			warehouse.addItem(data.get(i).get(0).toString());
		}
		JLabel qty = new JLabel();
		qty.setText(getQty(pid, warehouse.getSelectedItem().toString()));
		warehouse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				qty.setText(getQty(pid, warehouse.getSelectedItem().toString()));
			}
		});
		
		Object [] fields = {
				"Quantity Required:", qtyr,
				"Warehouse:", warehouse,
				"Quantity Available: ", qty,
		};
		int option = JOptionPane.showConfirmDialog(null, fields, "Complete Order", JOptionPane.CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			if(Integer.parseInt(qtyr) > Integer.parseInt(qty.getText()))
				_message("Failed!", "Order cannot be completed! Not enough quantity in stock!", 1);
			else {
				if(Integer.parseInt(qtyr) == Integer.parseInt(qty.getText()))
					doOrder(oid, pid, qtyr, warehouse.getSelectedItem().toString(), 1);
				else
					doOrder(oid, pid, qtyr, warehouse.getSelectedItem().toString(), 0);
			}
		}
	}
	
	// Function to Reflect the Order in the Database
	private void doOrder(String oid, String pid, String qty, String wid, int condition) {
		int update1 = 0;
		int update2 = 0;
		
		if(condition == 0)
			update1 = SQLInterface.sqlUpdate("update stock set qty = qty - "+qty+" where pid = '"+pid+"' and wid = '"+wid+"';");
		else
			update1 = SQLInterface.sqlUpdate("delete from stock where pid = '"+pid+"' and wid = '"+wid+"';");
		update2 = SQLInterface.sqlUpdate("update orders set status = 'sent' where oid = '"+oid+"';");
		
		if(update1 == 0 || update2 == 0)
			_message("Failed!", "Order could not be completed successfully. Please try again later.", 1);
		else {
			_message("Success!", "Order was completed successfully.", 0);	
			displayOrders();
		}
		
	}
	
	// Function to Display the List of Products sold a Suppliers
	private void showSupply(int row) {
		String ssid = suppliers.get(row+1).get(0).toString();
		
		supply = SQLInterface.sqlExecute("select image, pname, price from sells natural join product where ssid = '"+ssid+"';");
		
		
		DefaultTableModel dm = (DefaultTableModel) spTable.getModel();
		
		int rowCount = dm.getRowCount();
		
		for (int i = rowCount - 1; i >= 0; i--) {
		    dm.removeRow(i);
		}
		
		ArrayList<ArrayList<Object>> temp = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> rowData = new ArrayList<Object>();
		
		int ssnum = supply.size() - 1;
		
		for(int i = 1; i < supply.size(); i++) {
			rowData = new ArrayList<Object>();
			rowData.add(_getImage(supply.get(i).get(0).toString()));
			rowData.add(supply.get(i).get(1));
			rowData.add(supply.get(i).get(2));
			temp.add(rowData);
		}
	
		for(int i = 0 ; i < ssnum; i++) {
			dm.addRow(temp.get(i).toArray());
		}
		
		JComboBox<String> warehouse = new JComboBox<String>();
		for(int i = 1; i < supply.size(); i++) {
			warehouse.addItem(supply.get(i).get(0).toString());
		}
		
		_message("Products Sold by Selected Supplier", spTable);
	}
	
	// Class for Buttons in Tables
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
	
	// Class for Buttons in Tables
	class DeleteProduct extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public DeleteProduct(JTable table, int column) {
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
			deleteProductFromDatabase(table.getSelectedRow());
		}
	
	}
	
	// Class for Buttons in Tables
	class AddStock extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public AddStock(JTable table, int column) {
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
			addStock(table.getSelectedRow());
		}
	
	}
	
	// Class for Buttons in Tables
	class CheckAvailable extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public CheckAvailable(JTable table, int column) {
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
			checkAvailable(table.getSelectedRow());
		}
	
	}
	
	// Class for Buttons in Tables
	class Order extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public Order(JTable table, int column) {
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
			if(oTable.getModel().getValueAt(table.getSelectedRow(), 8).toString().equals("issued"))
				completeOrder(table.getSelectedRow());
		}
	
	}
	
	// Class for Buttons in Tables
	class SupplierProducts extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
		JTable table;
		JButton renderButton;
		JButton editButton;
		String text;

		public SupplierProducts(JTable table, int column) {
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
			showSupply(table.getSelectedRow());
		}
	
	}
}
