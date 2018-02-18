package com.order.match.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class OrderDaoImpl  {
	static{

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
	public List<String> getOrders() { 
		Connection c = null;
		Statement stmt = null;
		List<String> orderList = new ArrayList<String>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
			c.setAutoCommit(false);
			
			StringBuffer query = new StringBuffer(
					"SELECT * FROM ORDERS where PRICE > 0 order by datetime");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				String order="";
				if(rs.getString("ORDERS").equalsIgnoreCase("0")){
					 order="BUY "+rs.getString("QUANTITY")+"@"+rs.getString("PRICE");	
				}else{
					 order="SELL "+rs.getString("QUANTITY")+"@"+rs.getString("PRICE"); 
				}
				 
				orderList.add(order);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderList;
	}
	public String sendSellDetails(String sellQuantity, String price) { 
		List<Orders> buyDetails = getBuyDetails(price); 
		String result="";
		if(buyDetails.size()>0){
			for (Orders buy : buyDetails) {
				if(buy.getQuantity()>Integer.valueOf(sellQuantity)){
					buy.setQuantity(buy.getQuantity()-Integer.valueOf(sellQuantity));
					updateQuantity(buy);
					return result+"TRADE "+ sellQuantity+"@"+price+"\n";
				} else if(buy.getQuantity()==Integer.valueOf(sellQuantity)){
					removeQuantity(buy);
					return result+"TRADE "+ sellQuantity+"@"+price+"\n";
				}else{
					sellQuantity=String.valueOf(Integer.valueOf(sellQuantity)-buy.getQuantity());
					removeQuantity(buy);
					result=result+"TRADE "+ buy.getQuantity()+"@"+price+"\n";
				}
			}
			insertSellDetails(sellQuantity,price);
			return result;
		}else{
			insertSellDetails(sellQuantity,price);
			return result;
		}
		
		
	}
	public List<Orders> getBuyDetails(String sellPrice) { 
		Connection c = null;
		Statement stmt = null;
		List<Orders> buyList = new ArrayList<Orders>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
			c.setAutoCommit(false);
			
			StringBuffer query = new StringBuffer(
					"SELECT * FROM ORDERS where ORDERS = '0' and PRICE >='");
			query.append(sellPrice).append("' order by PRICE , datetime");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				Orders buy = new Orders();
				buy.setId(rs.getInt("ID"));
				buy.setOrders(rs.getInt("ORDERS"));
				buy.setQuantity(rs.getInt("QUANTITY"));
				buy.setPrice(rs.getInt("PRICE"));
				buyList.add(buy);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyList;
	}
	public void insertSellDetails(String volume, String price) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
	
				StringBuffer sql = new StringBuffer(
						"INSERT INTO ORDERS (ID,ORDERS,QUANTITY,PRICE,DATETIME)"
								+ " VALUES ((SELECT MAX( ID ) FROM ORDERS) +1, '1','")
						.append(volume).append("', '")
						.append(price).append("', datetime('now'))");
					stmt.executeUpdate(sql.toString());
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}

	

	public String sendbuyDetails(String buyQuantity, String price) { 
		List<Orders> sellDetails = getSellDetails(price); 
		String result="";
		if(sellDetails.size()>0){
			for (Orders sell : sellDetails) {
				if(sell.getQuantity()>Integer.valueOf(buyQuantity)){
					sell.setQuantity(sell.getQuantity()-Integer.valueOf(buyQuantity));
					updateQuantity(sell);
					return result+"TRADE "+ buyQuantity+"@"+sell.price+"\n";
				} else if(sell.getQuantity()==Integer.valueOf(buyQuantity)){
					removeQuantity(sell);
					return result+"TRADE "+ buyQuantity+"@"+sell.price+"\n";
				}else{
					buyQuantity=String.valueOf(Integer.valueOf(buyQuantity)-sell.getQuantity());
					removeQuantity(sell);
					result=result+"TRADE "+ sell.getQuantity()+"@"+sell.price+"\n";
				}
			}
			insertBuyDetails(buyQuantity,price);
			return result;
		}else{
			insertBuyDetails(buyQuantity,price);
			return result;
		}
		
	}
	public void removeQuantity(Orders order) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
	
			StringBuffer sql = new StringBuffer(
					"DELETE FROM ORDERS where ID = ").append(order.getId());
						

				stmt.executeUpdate(sql.toString());
	
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void updateQuantity(Orders order) {
		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
	
			StringBuffer sql = new StringBuffer(
					"UPDATE ORDERS SET QUANTITY = ");
			sql.append(order.getQuantity()).append(" where ID = ").append(order.getId());
						

				stmt.executeUpdate(sql.toString());
	
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void insertBuyDetails(String volume, String price) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
			c.setAutoCommit(false);

			stmt = c.createStatement();
	
				StringBuffer sql = new StringBuffer(
						"INSERT INTO ORDERS (ID,ORDERS,QUANTITY,PRICE,DATETIME)"
								+ " VALUES ((SELECT MAX( ID ) FROM ORDERS) +1, '0','")
						.append(volume).append("', '")
						.append(price).append("', datetime('now'))");
						

				stmt.executeUpdate(sql.toString());
	
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
	}

	public List<Orders> getSellDetails(String buyPrice) { 
		Connection c = null;
		Statement stmt = null;
		List<Orders> sellList = new ArrayList<Orders>();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
			c.setAutoCommit(false);
			StringBuffer query = new StringBuffer(
					"SELECT * FROM ORDERS where ORDERS = '1' and PRICE <='");
			query.append(buyPrice).append("' order by PRICE, datetime");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(query.toString());
			while (rs.next()) {
				Orders sell = new Orders();
				sell.setId(rs.getInt("ID"));
				sell.setOrders(rs.getInt("ORDERS"));
				sell.setQuantity(rs.getInt("QUANTITY"));
				sell.setPrice(rs.getInt("PRICE"));
				sellList.add(sell);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sellList;
	}
	
	

}
