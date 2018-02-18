package com.order.match.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SQLiteDAO {

	   public static void main( String args[] ) {
	      Connection c = null;
	      Statement stmt = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         String sql = "CREATE TABLE ORDERS" +
	                        "(ID INT PRIMARY KEY     NOT NULL," +
	                        " ORDERS INT    NOT NULL, " + 
	                        " QUANTITY        INT NOT NULL, " + 
	                        " PRICE         INT NOT NULL, " +
	                        "DATETIME TEXT   )"; 
	         

	        
	         stmt.executeUpdate(sql);
	         
	         StringBuffer sqlBuff = new StringBuffer(
						"INSERT INTO ORDERS (ID,ORDERS,QUANTITY,PRICE,DATETIME)"
								+ " VALUES (1, '")
						.append("0").append("', '")
						.append("0").append("', '")
						.append("0").append("', datetime('now')) ");

				stmt.executeUpdate(sqlBuff.toString());
				
			
	         stmt.close();
	         c.close();
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Tables created successfully");
	   }
	}
