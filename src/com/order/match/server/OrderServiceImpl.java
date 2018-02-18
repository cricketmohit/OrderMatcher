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

	public List<Order> getOrders() throws IllegalArgumentException {
		List<Order> resultList = new ArrayList<Order>();
		OrderDaoImpl impl = new OrderDaoImpl();
		List<String> strList = impl.getOrders();
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

		return resultList;
	}


	public String sendBuyDetails(String volume, String price)
			throws IllegalArgumentException {
		OrderDaoImpl impl = new OrderDaoImpl();
		return impl.sendbuyDetails(volume,price);
	}

	public String sendSellDetails(String volume, String price)
			throws IllegalArgumentException {
		OrderDaoImpl impl = new OrderDaoImpl();
		return impl.sendSellDetails(volume,price); 
	}
}
