package App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BankingApplication {

	public static void main(String args[]) {
		Connect();
		BankAccount obj1 = new BankAccount("XYZ", "BA0001");
		obj1.showMenu();
		
	}

	public static void Connect() {
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// step2 create the connection object
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:system/Vipul123$@localhost:1521:ORCL");

			// step3 create the statement object
			Statement stmt = con.createStatement();

			// step4 execute query
			ResultSet rs = stmt.executeQuery("select * from persons");
			while (rs.next())
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

			// step5 close the connection object
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

class BankAccount {
	int balance;
	int previousTransaction;
	String customerName;
	String customerId;

	BankAccount(String cname, String cid) {
		customerName = cname;
		customerId = cid;

	}

	void deposit(int amount) {
		if (amount != 0) {
			balance = balance + amount;

			previousTransaction = amount;
		}
	}

	void withdraw(int amount) {
		if (amount != 0) {
			balance = balance - amount;
			previousTransaction = -amount;
		}
	}

	void getPreviousTransaction() {
		if (previousTransaction > 0) {
			System.out.println("Deposited:" + previousTransaction);
		} else if (previousTransaction < 0) {
			System.out.println("withdrawn: " + Math.abs(previousTransaction));
		} else {
			System.out.println("No Transaction Occured");
		}
	}
	
	void CreateCustomer() {
		System.out.println("Please Enter the Following Details: ");
		
	}

	void showMenu() {
		char option = '\0';

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome " + customerName);
		System.out.println("Your ID is " + customerId);
		System.out.println("\n");
		System.out.println("A. Check Balance");
		System.out.println("B. Deposit");
		System.out.println("C. Withdraw");
		System.out.println("D. Previous transactions");
		System.out.println("E. Exit");
		System.out.println("F. Create New Customer");
		do {
			System.out.println("====================================================================================");
			System.out.println("Enter an option");
			System.out.println("====================================================================================");
			option = scanner.next().charAt(0);
			System.out.println("\n");

			switch (option) {
			case 'A':
				System.out.println("-----------------------------");
				System.out.println("Balance = " + balance);
				System.out.println("-----------------------------");
				System.out.println("\n");
				break;

			case 'B':
				System.out.println("-----------------------------");
				System.out.println("Enter an amount to deposite: ");
				System.out.println("-----------------------------");
				int amount = scanner.nextInt();
				deposit(amount);
				System.out.println("\n");
				break;

			case 'C':
				System.out.println("-----------------------------");
				System.out.println("Enter an amount to withdraw: ");
				System.out.println("-----------------------------");
				int amount2 = scanner.nextInt();
				withdraw(amount2);
				System.out.println("\n");
				break;

			case 'D':
				System.out.println("-----------------------------");
				getPreviousTransaction();
				System.out.println("-----------------------------");
				System.out.println("\n");
				break;

			case 'E':
				System.out.println("*****************************");

				break;
				

			case 'F':
				System.out.println("-----------------------------");
				CreateCustomer();
				System.out.println("-----------------------------");
				System.out.println("\n");
				break;	

			default:
				System.out.println("Invalid Option!!. Please try again");
				break;
			}
		} while (option != 'E');
		System.out.println("ThankYou For Banking with Us.");
	}
}