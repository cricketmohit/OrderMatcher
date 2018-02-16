package com.order.match.client;

import java.io.Serializable;

public class Order implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public String buy;

public String sell;
public String getBuy() {
	return buy;
}
public void setBuy(String buy) {
	this.buy = buy;
}
public String getSell() {
	return sell;
}
public void setSell(String sell) {
	this.sell = sell;
}
}
