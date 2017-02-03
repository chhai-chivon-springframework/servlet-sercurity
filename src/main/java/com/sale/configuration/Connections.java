package com.sale.configuration;

import java.sql.Connection;
import java.sql.DriverManager;

public final class Connections {	
	private static Connection con = null;
	private static String url ;
	
	private static void loadConnection () throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		url = "jdbc:postgresql://localhost:5432/sale_db";
	}
	public static Connection open() {
		try {
			if(con == null) {
				loadConnection();
				System.out.println("connection is open..!");
				return con = DriverManager.getConnection(url,"postgres","12345");
			}else return con;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void close() {
		try {
			if(con != null){
				con.close();
				System.out.println("connection is close...!");
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
