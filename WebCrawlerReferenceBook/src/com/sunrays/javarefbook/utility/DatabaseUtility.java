package com.sunrays.javarefbook.utility;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Database Utility class is user to get database connection
 * 
 * @author Sunil Sahu
 * 
 */

public class DatabaseUtility {

	static {
		// Load Driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Loading Error...");
		}
	}

	public static Connection openConnection() throws Exception {
		// Create Connection
		Connection conn = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/javarefwebcrawler",
						"root", "root");
		return conn;
	}

	public static void closeConnection(Connection conn) throws Exception {
		// Close Connection
		try {
			conn.close();
		} catch (Exception e) {
		}
	}

}
