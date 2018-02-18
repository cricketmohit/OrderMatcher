package com.order.match.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("order")
public interface OrderService extends RemoteService {
	List<Order> getOrders() throws IllegalArgumentException;
	String sendBuyDetails(String volume, String price) throws IllegalArgumentException;
	String sendSellDetails(String volume, String price) throws IllegalArgumentException;
}
