package App;

import java.sql.*;

public class OracleCon {
	static String name;
	public static void main(String args[]) {
		try {
			// step1 load the driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// step2 create the connection object
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:system/Vipul123$@localhost:1521:ORCL");

			// step3 create the statement object
			Statement stmt = con.createStatement();

			// step4 execute query
			ResultSet rs = stmt.executeQuery("Select * from users where email ='kvipul.saini@gmail.com'");
			
			while (rs.next())
			 name = rs.getString(2);
			System.out.println(name);
				//System.out.println(rs.getFloat(1) + "  " );
				
				
			// step5 close the connection object
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}


