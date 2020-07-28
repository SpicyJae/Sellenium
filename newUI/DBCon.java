package fleetup.selenium.newUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.openqa.selenium.WebDriver;

/* 
 * This is for database. 
 * 1) Connection 
 * 2) SQL
 */
public class DBCon {

	String dburl = "jdbc:oracle:thin:@fleetup-stress-121216.cqzjqrssuycy.us-west-2.rds.amazonaws.com:1521:STRS1212";
	String dbClass = "oracle.jdbc.driver.OracleDriver";
	Connection con;
	Statement stmt;

	/*
	 * Construction
	 */
	public DBCon() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ConnectDB();
	}

	/*
	 * DB connect
	 */
	public void ConnectDB()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Class.forName(dbClass).newInstance();
		con = DriverManager.getConnection(dburl, "truelite", "4ADYZff3");
		stmt = (Statement) con.createStatement();
	}

	/*
	 * Role Type = '2' : Client admin Role Type = '3' : Observer
	 * 
	 * But i select account which language is english
	 */
	public String GetAvailAccount(int role_type) throws SQLException {
		String sql = "SELECT * FROM( SELECT * FROM user_info where role_type='" + role_type
				+ "' and password='912ec803b2ce49e4a541068d495ab570' and ACCT_ID IN (SELECT ACCT_ID FROM ACCOUNT WHERE LANGUAGE='1') ORDER BY dbms_random.value ) WHERE rownum = 1";
		ResultSet rs = stmt.executeQuery(sql);
		String user_id_result = "";
		while (rs.next()) {
			user_id_result = rs.getString("USER_ID");
		}
		return user_id_result;
	}
}
