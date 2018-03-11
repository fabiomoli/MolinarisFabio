package it.polito.tdpProvaFinale.baionettaUpdater.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private final static String jdbcURL = "jdbc:mysql://localhost/baionetta?user=fabio&password=  ";

	private static Connection myConn = null;

	public static Connection getConnection() {

		if (myConn != null)
			return myConn;

		Connection c = null;
		try {
			c = DriverManager.getConnection(jdbcURL);
		} catch (SQLException e) {
			System.err.println("Error MySQL Connection");
		}

		myConn = c;
		return c;
	}
}
