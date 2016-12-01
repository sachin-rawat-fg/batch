package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import configuration.Settings;

public class ConnectionDB {

	private static ConnectionDB obj = new ConnectionDB();
	Connection conn = null;
	private ConnectionDB(){
		Settings set = Settings.getInstance();
		try {
			Class.forName(set.getDBClass());
    	     conn = DriverManager.getConnection(set.getDBUrl(),set.getDBUsername(),"");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public static ConnectionDB getInstance( ) {
	      return obj;
	   }
	public Connection getDBConnection()
	{
		return conn;
	}

}
