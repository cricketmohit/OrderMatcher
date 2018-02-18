package com.order.match.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl  {


//	public String createUser(Order userToSave) {
//		Connection c = null;
//		Statement stmt = null;
//
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:ordermatch.db");
//			c.setAutoCommit(false);
//
//			stmt = c.createStatement();
//			if (userToSave.getUserID() != 0) {
//				User persistedUser = getUserById(userToSave.getUserID());
//				persistedUser.setUsername(persistedUser.getUsername());
//				persistedUser.setPassword(persistedUser.getPassword());
//				StringBuffer sql1 = new StringBuffer(
//						"UPDATE USER SET USERNAME = '");
//				sql1.append(persistedUser.getUsername())
//						.append("', PASSWORD = '")
//						.append(persistedUser.getPassword())
//						.append("' where USERID = ")
//						.append(userToSave.getUserID());
//				stmt.executeUpdate(sql1.toString());
//			} else {
//				StringBuffer sql = new StringBuffer(
//						"INSERT INTO USER (USERID,USERNAME,EMAIL,PASSWORD,LASTLOGIN)"
//								+ " VALUES ((SELECT MAX( USERID ) FROM USER) +1, '")
//						.append(userToSave.getUsername()).append("', '")
//						.append(userToSave.getEmail()).append("', '")
//						.append(userToSave.getPassword()).append("', NULL) ");
//
//				stmt.executeUpdate(sql.toString());
//			}
//			stmt.close();
//			c.commit();
//			c.close();
//		} catch (Exception e) {
//			throw new BaseException("APP-01-01", e.getMessage());
//		}
//		return "SUCCESS";
//	}
//
//	
//	public String login(User userToLogin) throws BaseException {
//		Connection c = null;
//		Statement stmt = null;
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
//			c.setAutoCommit(false);
//
//			StringBuffer query = new StringBuffer(
//					"SELECT * FROM USER where username = '");
//			query.append(userToLogin.getUsername()).append("'");
//			stmt = c.createStatement();
//			ResultSet rs = stmt.executeQuery(query.toString());
//			if (!rs.isBeforeFirst()) {
//				throw new BaseException("APP-02-02",
//						"User not available, please register");
//			}
//			while (rs.next()) {
//				String password = rs.getString("PASSWORD");
//				int userID = rs.getInt("USERID");
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date lastLogin = null;
//				try {
//					if (rs.getString("LASTLOGIN") != null) {
//						lastLogin = df.parse(rs.getString("LASTLOGIN"));
//					}
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
//
//				Calendar date = Calendar.getInstance();
//				long t = date.getTimeInMillis();
//				Date afterRemovingThirtyMins = new Date(t
//						- (30 * ONE_MINUTE_IN_MILLIS));
//
//				if (password.equals(userToLogin.getPassword())) {
//					if (lastLogin != null) {
//						long last = lastLogin.getTime();
//						lastLogin = new Date(last + (60 * ONE_MINUTE_IN_MILLIS));
//						if (lastLogin.after(afterRemovingThirtyMins)) {
//							throw new BaseException("APP-02-01",
//									"Already Logged In");
//						}
//					}
//					StringBuffer sql = new StringBuffer(
//							"UPDATE USER SET LASTLOGIN = datetime('now')");
//					sql.append(" where USERID = ").append(userID);
//					stmt.executeUpdate(sql.toString());
//					c.commit();
//					rs.close();
//					stmt.close();
//					c.close();
//					return "SUCCESS";
//				} else {
//					throw new BaseException("APP-02-01", "Incorrect Password");
//				}
//			}
//			rs.close();
//			stmt.close();
//			c.close();
//		} catch (Exception e) {
//
//			throw new BaseException("APP-02-01", e.getMessage());
//		}
//
//		return "SUCCESS";
//	}
//
//	
//	public String checkLoginStatus(int userId) throws BaseException {
//		Connection c = null;
//		Statement stmt = null;
//		String result = "FAILURE";
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
//			c.setAutoCommit(false);
//
//			StringBuffer query = new StringBuffer(
//					"SELECT * FROM USER where USERID = '");
//			query.append(userId).append("'");
//			stmt = c.createStatement();
//			ResultSet rs = stmt.executeQuery(query.toString());
//
//			while (rs.next()) {
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date lastLogin = null;
//				try {
//					if (rs.getString("LASTLOGIN") != null) {
//						lastLogin = df.parse(rs.getString("LASTLOGIN"));
//					}
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs
//
//				Calendar date = Calendar.getInstance();
//				long t = date.getTimeInMillis();
//				Date afterRemovingThirtyMins = new Date(t
//						- (30 * ONE_MINUTE_IN_MILLIS));
//
//				if (lastLogin != null) {
//					long last = lastLogin.getTime();
//					lastLogin = new Date(last + (60 * ONE_MINUTE_IN_MILLIS));
//					if (lastLogin.after(afterRemovingThirtyMins)) {
//						result = "SUCCESS";
//					}
//				}
//			}
//			rs.close();
//			stmt.close();
//			c.close();
//		} catch (Exception e) {
//			throw new BaseException("APP-02-01", e.getMessage());
//		}
//		return result;
//	}
//
//	
//	
//	
//	public User getUserById(int userId) throws BaseException {
//		Connection c = null;
//		Statement stmt = null;
//		String result = "FAILURE";
//		User user = new User();
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
//			c.setAutoCommit(false);
//
//			StringBuffer query = new StringBuffer(
//					"SELECT * FROM USER where USERID = '");
//			query.append(userId).append("'");
//			stmt = c.createStatement();
//			ResultSet rs = stmt.executeQuery(query.toString());
//			if (!rs.isBeforeFirst()) {
//				throw new BaseException("APP-02-02",
//						"User not available, please register");
//			}
//			while (rs.next()) {
//				user.setUserID(rs.getInt("USERID"));
//				user.setUsername(rs.getString("USERNAME"));
//				user.setPassword(rs.getString("PASSWORD"));
//				user.setE_mail(rs.getString("EMAIL"));
//			}
//			rs.close();
//			stmt.close();
//			c.close();
//		} catch (Exception e) {
//			throw new BaseException("APP-04-01", e.getMessage());
//		}
//		return user;
//	}
//
//	
//	public String createPost(Post post) throws BaseException {
//		Connection c = null;
//		Statement stmt = null;
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
//			c.setAutoCommit(false);
//			stmt = c.createStatement();
//			if (post.getPostID() != 0) {
//				Post persistedPost = getPostById(post.getPostID());
//				persistedPost.setBody(post.getBody());
//				persistedPost.setTitle(post.getTitle());
//				StringBuffer sql1 = new StringBuffer(
//						"UPDATE POST SET DATE = datetime('now'), BODY = '");
//				sql1.append(persistedPost.getBody()).append("', TITLE = '")
//						.append(persistedPost.getTitle())
//						.append("' where POSTID = ").append(post.getPostID());
//				stmt.executeUpdate(sql1.toString());
//			} else {
//				StringBuffer sql = new StringBuffer(
//						"INSERT INTO POST (POSTID,POSTUSERID,TITLE,BODY,DATE)"
//								+ " VALUES ((SELECT MAX( POSTID ) FROM POST) +1, '")
//						.append(post.getUserID()).append("', '")
//						.append(post.getTitle()).append("', '")
//						.append(post.getBody()).append("', datetime('now')) ");
//
//				stmt.executeUpdate(sql.toString());
//			}
//			stmt.close();
//			c.commit();
//			c.close();
//		} catch (Exception e) {
//			throw new BaseException("APP-03-01", e.getMessage());
//		}
//		return "SUCCESS";
//	}
//
//	
//	public String deletePost(int postID) throws BaseException {
//		Connection c = null;
//		Statement stmt = null;
//
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
//			c.setAutoCommit(false);
//			stmt = c.createStatement();
//			StringBuffer sql = new StringBuffer(
//					"Delete from POST where POSTID = '").append(postID).append(
//					"'");
//
//			stmt.executeUpdate(sql.toString());
//			stmt.close();
//			c.commit();
//			c.close();
//		} catch (Exception e) {
//			throw new BaseException("APP-01-01", e.getMessage());
//		}
//		return "SUCCESS";
//	}
//
//	
//	public Post searchPost(Post postId) throws BaseException {
//		Connection c = null;
//		Statement stmt = null;
//		String result = "FAILURE";
//		Post post = new Post();
//		try {
//			Class.forName("org.sqlite.JDBC");
//			c = DriverManager.getConnection("jdbc:sqlite:userpost.db");
//			c.setAutoCommit(false);
//
//			StringBuffer query = new StringBuffer(
//					"SELECT * FROM POST where TITLE LIKE '%");
//			query.append(postId.getPostID()).append("%'");
//			stmt = c.createStatement();
//			ResultSet rs = stmt.executeQuery(query.toString());
//			if (!rs.isBeforeFirst()) {
//				throw new BaseException("APP-02-02", "Post not available");
//			}
//			while (rs.next()) {
//				post.setUserID(rs.getInt("POSTUSERID"));
//				post.setTitle(rs.getString("TITLE"));
//				post.setBody(rs.getString("BODY"));
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				Date date = null;
//				try {
//					if (rs.getString("LASTLOGIN") != null) {
//						date = df.parse(rs.getString("DATE"));
//					}
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				post.setDate(date);
//			}
//			rs.close();
//			stmt.close();
//			c.close();
//		} catch (Exception e) {
//			throw new BaseException("APP-04-01", e.getMessage());
//		}
//		return post;
//	}
	
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
					return result+"\n"+"TRADE "+ sellQuantity+"@"+price;
				} else if(buy.getQuantity()==Integer.valueOf(sellQuantity)){
					removeQuantity(buy);
					return result+"\n"+"TRADE "+ sellQuantity+"@"+price;
				}else{
					sellQuantity=String.valueOf(Integer.valueOf(sellQuantity)-buy.getQuantity());
					removeQuantity(buy);
					result=result+"\n"+"TRADE "+ buy.getQuantity()+"@"+price;
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

	

	public String sendBuyDetails(String buyQuantity, String price) { 
		List<Orders> sellDetails = getSellDetails(price); 
		String result="";
		if(sellDetails.size()>0){
			
			for (Orders sell : sellDetails) {
				if(sell.getQuantity()>Integer.valueOf(buyQuantity)){
					sell.setQuantity(sell.getQuantity()-Integer.valueOf(buyQuantity));
					updateQuantity(sell);
					return result+"\n"+"TRADE "+ buyQuantity+"@"+sell.price;
				} else if(sell.getQuantity()==Integer.valueOf(buyQuantity)){
					removeQuantity(sell);
					return result+"\n"+"TRADE "+ buyQuantity+"@"+sell.price;
				}else{
					buyQuantity=String.valueOf(Integer.valueOf(buyQuantity)-sell.getQuantity());
					removeQuantity(sell);
					result=result+"\n"+"TRADE "+ sell.getQuantity()+"@"+sell.price;
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
