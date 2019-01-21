package db;

import java.sql.Connection;


import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Connexao {
	
	public static final Connection getConexao() {
		Connection conn = null;
		
		 try {	
			   Class.forName("org.sqlite.JDBC").newInstance();
	           String database = "db\\database.db";
	            String url = "jdbc:sqlite:";
	            url = url + database;
	           
	            conn = DriverManager.getConnection(url);
	            return conn;
	            
	        } catch (Exception e) {
	        	JOptionPane.showMessageDialog(null, "Erro no banco :"+e);
	        }
		return conn;
	}
}
