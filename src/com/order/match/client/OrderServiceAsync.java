package com.order.match.client;


import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>OrderService</code>.
 */
public interface OrderServiceAsync {

	void getOrders(AsyncCallback<List<Order>> callback)throws IllegalArgumentException;
	void sendBuyDetails(String volume, String price,
			AsyncCallback<String> asyncCallback); 
	
	void sendSellDetails(String volume, String price,
			AsyncCallback<String> asyncCallback); 
}
