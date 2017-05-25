package it.polito.tdpProvaFinale.laBaionettaReader.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/baionetta";
	static private DBConnect instance = null;
	static private DataSource ds_unpooled;
	static private DataSource ds_pooled;

	private DBConnect() {
		instance = this;
		try {
			ds_unpooled = DataSources.unpooledDataSource(jdbcUrl, "root", "root");
			ds_pooled = DataSources.pooledDataSource( ds_unpooled );
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("Errore di connessione al database");
			}
	}

	public static DBConnect getInstance() {
		if (instance == null)
			return new DBConnect();
		else {
			return instance;
		}
	}



	public Connection getConnection() {
		try {

			Connection conn = ds_pooled.getConnection();
			return conn;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al database");
		}
	}
}