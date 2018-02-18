package com.order.match.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.order.match.client.Order;
import com.order.match.client.OrderService;
import com.order.match.dao.OrderDaoImpl;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class OrderServiceImpl extends RemoteServiceServlet implements
		OrderService {
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	public List<Order> getOrders() throws IllegalArgumentException {
		List<Order> resultList = new ArrayList<Order>();
		OrderDaoImpl impl = new OrderDaoImpl();
		List<String> strList = impl.getOrders();
//		strList.add("Buy 50@5");
//		strList.add("Buy 60@15");
//		strList.add("Buy 545349");
//		strList.add("Sell 20@50");
//		strList.add("Buy 50@5");
		List<String> buyList = new ArrayList<String>();
		List<String> sellList = new ArrayList<String>();
		
	
		for (String string : strList) {
			if(string.contains("BUY")){
				buyList.add(string);
			} else{
				sellList.add(string);
			}
		}
		Iterator<String> iterator = buyList.iterator(); 
		Iterator<String> iterator2 = sellList.iterator(); 
		while(iterator.hasNext() || iterator2.hasNext()){
			Order order = new Order();
			if(iterator.hasNext()){
				order.setBuy(iterator.next());
			}
			if(iterator2.hasNext()){
				order.setSell(iterator2.next());
			}
			resultList.add(order);
		}
		
	
//		for (String buyStr : buyList) {
//			
//		}
//		Order order = new Order();
//		order.setBuy("Buy 50@5");
//		order.setSell("Sell 20@50");
//		resultList.add(order);
//		order = new Order();
//		order.setBuy("Buy 54534985734985738947");
//		order.setSell("");
//		resultList.add(order);
//		resultList.add(order);

		return resultList;
	}
//	public List<String> getSellOrders() throws IllegalArgumentException {
//		List<String> resultList = new ArrayList<String>();
//		resultList.add("Sell 50@5");
//		resultList.add("Sell 150@5");
//		resultList.add("Sell 150@5");
//		resultList.add("Sell 150@5453498573498573");
//
//		return resultList;
//	}

	public String sendBuyDetails(String volume, String price)
			throws IllegalArgumentException {
		OrderDaoImpl impl = new OrderDaoImpl();
		return impl.sendBuyDetails(volume,price);
	}

	public String sendSellDetails(String volume, String price)
			throws IllegalArgumentException {
		OrderDaoImpl impl = new OrderDaoImpl();
		return impl.sendSellDetails(volume,price); 
	}
}
