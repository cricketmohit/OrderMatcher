package com.order.match.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.order.match.client.Order;
import com.order.match.client.OrderService;

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
		Order order = new Order();
		order.setBuy("Buy 50@5");
		order.setSell("Sell 20@50");
		resultList.add(order);
		order = new Order();
		order.setBuy("Buy 54534985734985738947");
		order.setSell("");
		resultList.add(order);
		resultList.add(order);

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
		// TODO Auto-generated method stub
		return "Order matched !!!";
	}

	public String sendSellDetails(String volume, String price)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return "Order matched !!";
	}
}
