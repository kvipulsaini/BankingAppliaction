package App;

import java.awt.EventQueue;
import java.awt.Desktop;
import java.sql.*;
import java.util.concurrent.atomic.AtomicLong;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class BankingCurd {

	private JFrame frame;
	private JTextField txtfname;
	private JTextField txtlname;
	private JTextField txtemail;
	private JTextField txtmobile;
	private JTextField txtaadhar;
	private JTextField txtaddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankingCurd window = new BankingCurd();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BankingCurd() {
		initialize();
		Connect();

	}

	Connection con;
	Statement stmt;
	private JTextField txt_email;

	public void Connect() {
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// step2 create the connection object
			con = DriverManager.getConnection("jdbc:oracle:thin:system/Vipul123$@localhost:1521:ORCL");

			// step3 create the statement object
			stmt = con.createStatement();

			// step4 execute query
			ResultSet rs = stmt.executeQuery("select * from users");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

			// step5 close the connection object
			// con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1100, 703);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Standard Charted Bank");
		lblNewLabel.setBounds(433, 10, 369, 69);
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 45));
		frame.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(10, 76, 458, 511);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("First Name");
		lblNewLabel_1.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1.setBounds(22, 59, 132, 43);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Last Name");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(22, 138, 123, 43);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Email Id.");
		lblNewLabel_1_2.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_2.setBounds(22, 208, 123, 43);
		panel.add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_3 = new JLabel("Mobile No.");
		lblNewLabel_1_3.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_3.setBounds(22, 281, 132, 43);
		panel.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("Aadhar No.");
		lblNewLabel_1_4.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_4.setBounds(22, 362, 132, 43);
		panel.add(lblNewLabel_1_4);

		JLabel lblNewLabel_1_5 = new JLabel("Address:");
		lblNewLabel_1_5.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_5.setBounds(31, 438, 123, 43);
		panel.add(lblNewLabel_1_5);

		txtfname = new JTextField();
		txtfname.setBounds(201, 59, 216, 37);
		panel.add(txtfname);
		txtfname.setColumns(10);

		txtlname = new JTextField();
		txtlname.setColumns(10);
		txtlname.setBounds(201, 144, 216, 37);
		panel.add(txtlname);

		txtemail = new JTextField();
		txtemail.setColumns(10);
		txtemail.setBounds(201, 214, 216, 37);
		panel.add(txtemail);

		txtmobile = new JTextField();
		txtmobile.setColumns(10);
		txtmobile.setBounds(201, 290, 216, 37);
		panel.add(txtmobile);

		txtaadhar = new JTextField();
		txtaadhar.setColumns(10);
		txtaadhar.setBounds(201, 362, 216, 37);
		panel.add(txtaadhar);

		txtaddress = new JTextField();
		txtaddress.setColumns(10);
		txtaddress.setBounds(201, 429, 216, 74);
		panel.add(txtaddress);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(31, 597, 113, 45);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String fname, lname, email, mobile, aadhar, address;
				long account;

				fname = txtfname.getText();
				lname = txtlname.getText();
				email = txtemail.getText();
				account = uniqueCurrentTimeMS();
				mobile = txtmobile.getText();
				aadhar = txtaadhar.getText();
				address = txtaddress.getText();

				try {
					String query = "insert into users (FirstName,LastName,Email,AccountNo,Mobile,Aadhar,Address) values (?,?,?,?,?,?,?)";
					PreparedStatement sql = con.prepareStatement(query);

					sql.setString(1, fname);
					sql.setString(2, lname);
					sql.setString(3, email);
					sql.setLong(4, account);
					sql.setString(5, mobile);
					sql.setString(6, aadhar);
					sql.setString(7, address);
					System.out.println(sql);
					sql.executeQuery();
					JOptionPane.showMessageDialog(null, "Your Account Number is " + uniqueCurrentTimeMS());
					System.out.println("Account Number is: " + uniqueCurrentTimeMS());
				}

				catch (Exception e1) {
					e1.printStackTrace();

				}

			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 25));
		frame.getContentPane().add(btnSave);

		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(196, 597, 113, 45);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// step5 close the connection object
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 25));
		frame.getContentPane().add(btnExit);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(347, 597, 113, 45);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtfname.setText("");
				txtlname.setText("");
				txtemail.setText("");
				txtmobile.setText("");
				txtaadhar.setText("");
				txtaddress.setText("");
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 25));
		frame.getContentPane().add(btnClear);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(613, 193, 388, 242);
		panel_1.setBorder(
				new TitledBorder(null, "User Login", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1_6 = new JLabel("Email Id");
		lblNewLabel_1_6.setBounds(134, 21, 156, 34);
		lblNewLabel_1_6.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		panel_1.add(lblNewLabel_1_6);

		txt_email = new JTextField();
		txt_email.setColumns(10);
		txt_email.setBounds(99, 63, 216, 37);
		panel_1.add(txt_email);

		JLabel lblNewLabel_1_6_1 = new JLabel("Password");
		lblNewLabel_1_6_1.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_6_1.setBounds(134, 110, 156, 34);
		panel_1.add(lblNewLabel_1_6_1);

		txt_password = new JPasswordField();
		txt_password.setBounds(99, 154, 216, 34);
		panel_1.add(txt_password);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String username1 = txt_email.getText();
				String[] username = new String[] { username1 };
				String password = txt_password.getText();

				try {
					Statement stmt = con.createStatement();
					String sql = "select * from users where email = '" + txt_email.getText() + "' and aadhar = '"
							+ txt_password.getText().toString() + "'";
					ResultSet rs = stmt.executeQuery(sql);
					if (rs.next()) {
						txt_email.setText(null);
						txt_password.setText(null);
						System.out.println(username[0] + "Login Page");
						con.close();
						User_dash.ShowDialog(username);

					} else {
						JOptionPane.showMessageDialog(null, "       INVALID LOGIN", "ERROR", JOptionPane.ERROR_MESSAGE);
						txt_email.setText(null);
						txt_password.setText(null);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

			}
		});
		btnLogin.setBounds(873, 445, 113, 45);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 25));
		frame.getContentPane().add(btnLogin);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_email.setText(null);
				txt_password.setText(null);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnReset.setBounds(623, 445, 113, 45);
		frame.getContentPane().add(btnReset);
	}

	private static final AtomicLong LAST_TIME_MS = new AtomicLong();
	private JPasswordField txt_password;

	public static long uniqueCurrentTimeMS() {
		long now = System.currentTimeMillis();
		while (true) {
			long lastTime = LAST_TIME_MS.get();
			if (lastTime >= now)
				now = lastTime + 1;
			if (LAST_TIME_MS.compareAndSet(lastTime, now))
				return now;
		}
	}
}
