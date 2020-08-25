package cs425_databases;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class Login {

	private JFrame fLogin;
	
	private String userInfo[] = {"", "", ""};
	
	private int user = 0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.fLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor
	public Login() {
		initialize();
	}

	// Initializer
	private void initialize() {
		
		fLogin = new JFrame();
		fLogin.getContentPane().setBackground(Color.BLACK);
		fLogin.setTitle("http://www.store.com/login.aspx?src=common");
		fLogin.setBounds(100, 100, 350, 214);
		fLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fLogin.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(65, 37, 74, 16);
		fLogin.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(65, 65, 74, 16);
		fLogin.getContentPane().add(lblPassword);
		
		JLabel lblZipcode = new JLabel("Zipcode:");
		lblZipcode.setForeground(Color.WHITE);
		lblZipcode.setBounds(65, 93, 74, 16);
		fLogin.getContentPane().add(lblZipcode);
		
		JTextField tUsername = new JTextField();
		tUsername.setBounds(150, 32, 130, 26);
		fLogin.getContentPane().add(tUsername);
		tUsername.setColumns(10);
		
		JComboBox<Integer> cZipcode = new JComboBox<Integer>();
		cZipcode.setBounds(150, 88, 130, 26);
		for (int i = 46001; i < 47998; i++) {
			cZipcode.addItem(new Integer(i));
		}
		for (int i = 60001; i < 63000; i++) {
			cZipcode.addItem(new Integer(i));
		}
		fLogin.getContentPane().add(cZipcode);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(150, 60, 130, 26);
		fLogin.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				userInfo[1] = cZipcode.getSelectedItem().toString();
				char temp[] = passwordField.getPassword();
				String password = "";
				for(int i=0; i < temp.length; i++) {
					password += temp[i];
				}
				
				if (checkCredentials(tUsername.getText(), password)) {
					login();	
				}
				else {
					tUsername.setText("");
					passwordField.setText("");
					_message("Invalid Login!", "Please enter a valid username, password!", 1);
				}
					
			}
		});
		btnLogin.setBounds(189, 126, 95, 29);
		fLogin.getContentPane().add(btnLogin);
		
		JTextField name = new JTextField();
		JTextField pass = new JTextField();
		JTextField fname = new JTextField();
		JTextField lname = new JTextField();
		
		Object [] fields = {
				"First Name:", fname,
				"Last Name:", lname,
				"Username:", name, 
				"Password:", pass
		};
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBounds(58, 126, 117, 29);
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showConfirmDialog(null, fields, "Create a New Account", JOptionPane.CANCEL_OPTION);
				if(createAccount(fname.getText(), lname.getText(), name.getText(), pass.getText()))
					_message("Success!", "Your account was created! Please login with the username and password you entered.", 0);
				else
					_message("Failed!","Account creation failed! Please enter valid details!", 1);
			}
		});
		fLogin.getContentPane().add(btnSignUp);		
		
		fLogin.setVisible(true);
		
	}
	
	// Function to show messages in pop-up window
	private void _message(String title, String message, int type) {
		Object[] options = {"OK"};
		if (type == 1)
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		else if (type == 2)
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		else
			JOptionPane.showOptionDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	// Function to check username and password
	private boolean checkCredentials(String uname, String upass) {
		ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select uid from users where uname = '"+ uname + "' and upass = '" + upass + "';");

		if (data.size() == 1) 
			return false;
		else {
			userInfo[0] = data.get(1).get(0).toString();
			user = Integer.parseInt(userInfo[0])/10000;
			return true;
		}
		
	}
	
	// Function to create a new customer account
	private boolean createAccount(String fname, String lname, String name, String pass) {
		
		int update1 = 0;
		int update2 = 0;
		int id = 10001;
		
		if(fname.equals("") || lname.equals("") || name.equals("") || pass.equals(""))
			return false;
		else {
			ArrayList<ArrayList<Object>> data = SQLInterface.sqlExecute("select max(uid) "
					+ "from users where uid like '1%';");
			if (data.get(1).get(0) != null) {
				id = Integer.parseInt(data.get(1).get(0).toString()) + 1;
			}
			
			
			try {
				update1 = SQLInterface.sqlUpdate("insert into users values ('" + id + "', '" + 
						name + "', '" + pass +"');");
				update2 = SQLInterface.sqlUpdate("insert into customer values ('" + id + "', '" + 
						fname + "', '" + lname +"', '0.0');");
			}
			catch (Exception e) {
				
			}
			
			if(update1 == 0 || update2 == 0)
				return false;
			else
				return true;
		}
	}
	
	// Function to login to customer or staff depending on username and password
	private void login() {
		if (user == 1) {
			fLogin.setVisible(false);
			new Grocery_store(userInfo);
		}
		else if (user == 2) {
			fLogin.setVisible(false);
			new Grocery_store_2(userInfo);
		}
		else {
			_message("Invalid Login!", "Please enter a valid username and password!", 1);
		}
				
	}
	
}
