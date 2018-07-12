package java0630;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.net.aso.n;

public class DataBaseUtil {
	
	public static void main(String args[]) {
		DataBaseUtil util = new DataBaseUtil();
		Connection connection = util.getConn();
		System.out.println(connection);
	}
	
	public Connection getConn(){
		Properties properties = new Properties();
		String driver = null;
		String url = null;
		String username = null;
		String passwd = null;
		try {
			properties.load(DataBaseUtil.class.getClassLoader().getResourceAsStream("DataBase.property"));
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			passwd = properties.getProperty("password");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, passwd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Connection getConn(String url, String username, String passwd) {
		
		return null;
	}

}
