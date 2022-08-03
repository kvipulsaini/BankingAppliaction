package App;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class User_dash {

	private JFrame frame;
	private JTextField txtWithdraw;
	private JTextField txtDeposite;
	String user;
	String userName;
	int userId;
	float debitamt;
	String s1;
	static String name;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User_dash window = new User_dash(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public static void ShowDialog(String[] username) {
		User_dash window = new User_dash(username);
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public User_dash(String[] username) {
		initialize();
		Connect();
		balance();
		table_load();
		System.out.println(username[0]);
		user = username[0];
		user_data(username[0]);
		user_Id(username[0]);

	}

	Connection con;
	Statement stmt;

	ResultSet rs;
	private JTable table;
	private JTextField txtBalance;
	private JTextField txtname;

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
		frame.setBounds(100, 100, 1051, 597);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Standard Charted Bank");
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 45));
		lblNewLabel.setBounds(344, 10, 369, 69);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Hi,");
		lblNewLabel_1.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1.setBounds(442, 73, 40, 43);
		frame.getContentPane().add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Withdrawal", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(69, 379, 324, 128);
		frame.getContentPane().add(panel);

		JLabel lblNewLabel_1_2 = new JLabel("Withdraw amount");
		lblNewLabel_1_2.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_2.setBounds(68, 20, 216, 34);
		panel.add(lblNewLabel_1_2);

		txtWithdraw = new JTextField();
		txtWithdraw.setColumns(10);
		txtWithdraw.setBounds(68, 64, 216, 37);
		panel.add(txtWithdraw);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Deposit",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(579, 379, 324, 128);
		frame.getContentPane().add(panel_1);

		JLabel lblNewLabel_1_2_1 = new JLabel("Deposit amount");
		lblNewLabel_1_2_1.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_2_1.setBounds(68, 20, 216, 34);
		panel_1.add(lblNewLabel_1_2_1);

		txtDeposite = new JTextField();
		txtDeposite.setColumns(10);
		txtDeposite.setBounds(68, 64, 216, 37);
		panel_1.add(txtDeposite);

		JButton btnwithdraw = new JButton("Submit");
		btnwithdraw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String money = txtWithdraw.getText();
				float withdraw = Float.parseFloat(money);

				if (debitamt <= 0) {
					JOptionPane.showMessageDialog(null, "Your balance is ₹ " + debitamt + " Withdrawn ");
				} else {
					try {
						String query = "INSERT INTO account_transaction (UserId,DebitAmt,CreditAmt,TranDate) VALUES ("+userId+","
								+ withdraw + ",0.00,CURRENT_TIMESTAMP)";
						PreparedStatement sql = con.prepareStatement(query);
						sql.executeQuery();
						balance();
						JOptionPane.showMessageDialog(null, "Your amount ₹ " + withdraw + " Withdrawn ");
						System.out.println("Account Balance is: ");

					} catch (Exception e2) {
						// throw e2.printStackTrace();
						e2.printStackTrace();
					}
				}
				txtWithdraw.setText(null);
			}
		});
		btnwithdraw.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnwithdraw.setBounds(246, 517, 136, 33);
		frame.getContentPane().add(btnwithdraw);

		JButton btndeposit = new JButton("Submit");
		btndeposit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String money = txtDeposite.getText();
				float deposite = Float.parseFloat(money);
				try {
					stmt = con.createStatement();
					String query = "INSERT INTO account_transaction (UserId,DebitAmt,CreditAmt,TranDate) VALUES ("+userId+",0.00,"
							+ deposite + ",CURRENT_TIMESTAMP)";

					PreparedStatement sql = con.prepareStatement(query);

					// sql.setLong(1, userId);
					// sql.setNString(1, withdraw);

					System.out.println(sql);
					stmt.executeUpdate(query);
					// ResultSet rs = stmt.executeQuery(query);
					balance();
					JOptionPane.showMessageDialog(null, "Your amount ₹ " + deposite + " Deposited ");
					System.out.println("Account Balance is: ");
				}

				catch (Exception e1) {
					e1.printStackTrace();

				}
			}
		});
		btndeposit.setFont(new Font("Tahoma", Font.BOLD, 25));
		btndeposit.setBounds(745, 517, 146, 33);
		frame.getContentPane().add(btndeposit);

		JButton btnExit = new JButton("Exit");
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
		btnExit.setBounds(848, 34, 146, 45);
		frame.getContentPane().add(btnExit);

		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BankingCurd.main(null);
			}
		});
		btnHome.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnHome.setBounds(35, 34, 146, 45);
		frame.getContentPane().add(btnHome);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(35, 147, 959, 226);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtWithdraw.setText(null);
			}
		});
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnReset.setBounds(79, 517, 136, 33);
		frame.getContentPane().add(btnReset);

		JButton btnReset_1 = new JButton("Reset");
		btnReset_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtDeposite.setText(null);
			}
		});
		btnReset_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnReset_1.setBounds(589, 517, 136, 33);
		frame.getContentPane().add(btnReset_1);

		JLabel lblNewLabel_1_1 = new JLabel("Balance :");
		lblNewLabel_1_1.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		lblNewLabel_1_1.setBounds(35, 104, 111, 43);
		frame.getContentPane().add(lblNewLabel_1_1);

		txtBalance = new JTextField();
		txtBalance.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtBalance.setEditable(false);
		txtBalance.setColumns(10);
		txtBalance.setBounds(143, 109, 167, 37);
		frame.getContentPane().add(txtBalance);

		txtname = new JTextField();
		txtname.setText("<dynamic>");
		txtname.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtname.setEditable(false);
		txtname.setColumns(10);
		txtname.setBounds(475, 78, 167, 37);
		frame.getContentPane().add(txtname);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_load();

			}
		});
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnLoad.setBounds(915, 114, 79, 33);
		frame.getContentPane().add(btnLoad);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				balance();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(816, 113, 87, 33);
		frame.getContentPane().add(btnUpdate);
	}

	public void table_load() {
		try {
			// String[] username = username[0];
			PreparedStatement sql = con.prepareStatement(
					"SELECT DebitAmt,CreditAmt,TranDate  From account_transaction a  Join users u ON a.UserId = u.Id where u.email='"
							+ user + "'");

			ResultSet rs = sql.executeQuery();
			// while(rs.next())
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void balance() {
		// txtBalance.setText("12.96");

		System.out.println("balnce function " + user);
		// System.out.println("balnce function "+userId);

		try {
			PreparedStatement sql = con.prepareStatement(
					"select sum(creditamt)-sum(debitamt) from  account_transaction group by UserId having userid="+userId+"");

			ResultSet rs = sql.executeQuery();
			while (rs.next())

				debitamt = rs.getFloat(1);
			System.out.println("Balance amount " + debitamt);
			s1 = String.valueOf(debitamt);
			txtBalance.setText(s1);
			// table_1.setModel(DbUtils.resultSetToTableModel(bal));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void user_data(String username) {

		try {

			// step3 create the statement object
			stmt = con.createStatement();

			// step4 execute query
			ResultSet rs = stmt.executeQuery("Select * from users where email = '" + username + "'");

			while (rs.next())
				name = rs.getString(2);

			System.out.println(name);
			txtname.setText(name);

		} catch (Exception e) {
			System.out.println(e);

		}
	}
	
	public void user_Id(String username) {

		try {

			// step3 create the statement object
			stmt = con.createStatement();

			// step4 execute query
			ResultSet rs = stmt.executeQuery("Select * from users where email = '" + username + "'");

			while (rs.next())
				userId = rs.getInt(1);

			System.out.println(userId);
			

		} catch (Exception e) {
			System.out.println(e);

		}
	}
}
