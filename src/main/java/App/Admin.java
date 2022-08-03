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

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Admin {

	private JFrame frame;
	private JTable table;
	private JTable table_3;
	private JTextField txtuid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin window = new Admin();
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
	public Admin() {
		initialize();
		Connect();
		table_load();
	}

	Connection con;
	Statement stmt;

	ResultSet rs;

	public void Connect() {
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// step2 create the connection object
			con = DriverManager.getConnection("jdbc:oracle:thin:system/Vipul123$@localhost:1521:ORCL");
			con.setAutoCommit(true);

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
		frame.setBounds(100, 100, 1042, 682);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Standard Charted Bank");
		lblNewLabel.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 45));
		lblNewLabel.setBounds(344, 10, 369, 69);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 135, 959, 290);
		frame.getContentPane().add(scrollPane);

		table_3 = new JTable();
		scrollPane.setViewportView(table_3);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnExit.setBounds(83, 573, 146, 45);
		frame.getContentPane().add(btnExit);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String bid;
				bid = txtuid.getText();

				try {

					if (bid == null) {
						JOptionPane.showMessageDialog(null, "No Record found!!!!!!!");
					} else {
						PreparedStatement sql = con
								.prepareStatement("delete from account_transaction where Userid = ?");
						PreparedStatement sql1 = con.prepareStatement("delete from users where id = ? ");
						sql.setString(1, bid);
						sql1.setString(1, bid);
						sql.executeUpdate();
						sql1.executeUpdate();
						JOptionPane.showMessageDialog(null, "Record Deleted!!!!!!!");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();

				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnDelete.setBounds(305, 573, 146, 45);
		frame.getContentPane().add(btnDelete);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnUpdate.setBounds(555, 573, 146, 45);
		frame.getContentPane().add(btnUpdate);

		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BankingCurd.main(null);
			}
		});
		btnHome.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnHome.setBounds(813, 573, 146, 45);
		frame.getContentPane().add(btnHome);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(355, 435, 324, 128);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1_2 = new JLabel("User Id.");
		lblNewLabel_1_2.setBounds(133, 20, 99, 34);
		lblNewLabel_1_2.setFont(new Font("Segoe UI Historic", Font.BOLD, 25));
		panel.add(lblNewLabel_1_2);

		txtuid = new JTextField();
		txtuid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String id = txtuid.getText();
					PreparedStatement sql = con.prepareStatement(
							"select FirstName,LastName,Email,AccountNo,Mobile,Aadhar,Address from users where id = ?");
					sql.setString(1, id);
					ResultSet rs = sql.executeQuery();

					if (rs.next() == true) {
						String FirstName = rs.getString(1);
						String LastName = rs.getString(2);
						String Email = rs.getString(3);
						String AccountNo = rs.getString(4);
						String Mobile = rs.getString(5);
						String Aadhar = rs.getString(6);
						String Address = rs.getString(7);

					} else {
						JOptionPane.showMessageDialog(null, "No Such record found!!!!!!!");
					}
				} catch (SQLException ex) {
					ex.printStackTrace();

				}

			}
		});
		txtuid.setColumns(10);
		txtuid.setBounds(68, 64, 216, 37);
		panel.add(txtuid);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table_load();
			}
		});
		btnLoad.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnLoad.setBounds(83, 479, 146, 45);
		frame.getContentPane().add(btnLoad);

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnLogin.setBounds(813, 479, 146, 45);
		frame.getContentPane().add(btnLogin);

	}

	public void table_load() {
		try {
			PreparedStatement sql = con.prepareStatement("select * from users");
			rs = sql.executeQuery();
			table_3.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
