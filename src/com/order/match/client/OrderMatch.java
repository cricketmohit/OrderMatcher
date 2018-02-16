package com.order.match.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.order.match.shared.FieldVerifier;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OrderMatch implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final OrderServiceAsync orderService = GWT
			.create(OrderService.class);
	
	private CellTable<Order> buyTable = new CellTable<Order>();
//	private CellTable<String> sellTable = new CellTable<String>();
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		final Button buyButton = new Button("BUY");
		final Button sellButton = new Button("SELL");
		final Label tradeLbl = new Label();
		final Label orderLbl = new Label();
		final TextBox volumeField = new TextBox();
		final TextBox priceField = new TextBox();

		final Label errorLabel = new Label();

		// We can add style names to widgets
		buyButton.addStyleName("sendButton");

		final TextColumn<Order> buyColumn = new TextColumn<Order>() {

			@Override
			public String getValue(Order object) {
				return object.buy;
			}
		};
		buyTable.setTableLayoutFixed(true);
		buyTable.setWidth("100%", true);
		buyTable.setColumnWidth(buyColumn, 50, Unit.PCT);
		
		buyColumn.setSortable(true);
		SafeHtml header = new SafeHtml() {
			
			public String asString() {
				// TODO Auto-generated method stub
				return "BuyOrder";
			}
		};
		
		buyTable.addColumn(buyColumn,header);
		buyTable.addColumnStyleName(0,"grey" );
		buyTable.addColumnStyleName(1,"grey" );	
		buyTable.setStyleName("grey");
//		buyTable.getRowElement(index).addClassName("redBackground");
		final TextColumn<Order> sellColumn = new TextColumn<Order>() {

			@Override
			public String getValue(Order object) {
				return object.sell;
			}
		};
		SafeHtml safe = new SafeHtml() {
			
			public String asString() {
				// TODO Auto-generated method stub
				return "Sell Orders";
			}
		};
		buyTable.setColumnWidth(sellColumn, 50, Unit.PCT);
		sellColumn.setSortable(true);
		buyTable.addColumn(sellColumn,safe);
		RootPanel.get("buyTable").add(buyTable);
//		RootPanel.get("sellTable").add(sellTable);
		RootPanel.get("orderLabelContainer").add(orderLbl);
		RootPanel.get("tradeLabelContainer").add(tradeLbl);

		RootPanel.get("volumeFieldContainer").add(volumeField);
		RootPanel.get("priceFieldContainer").add(priceField);
		RootPanel.get("buyButtonContainer").add(buyButton);
		RootPanel.get("sellButtonContainer").add(sellButton);

		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		volumeField.setFocus(true);
		volumeField.selectAll();
		getOrders();
//		getSellOrders();
		
		// Create the Table

		// Create a handler for the sellButton
		class SellHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendSellDetailsToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendSellDetailsToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendSellDetailsToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String volume = volumeField.getText();
				String price = priceField.getText();
				if (FieldVerifier.isNotANumber(volume)) {
					errorLabel
							.setText("Please enter a number greater than 0 and less than 99999");
					return;
				} else if (FieldVerifier.isNotANumber(price)) {
					errorLabel.setText("Price should be greater than 0");
					return;
				}

				// Then, we send the input to the server.

				orderService.sendSellDetails(volume, price,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user

							}

							public void onSuccess(String result) {
								orderLbl.setText("Order matched!!!");
								tradeLbl.setText("Mohit");
								// resultListStore = new ListStore<ResultRow>();
								// resultListStore.add(result);

							}
						});
			}
		}

		// Create a handler for the buyButton
		class BuyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendBuyDetailsToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendBuyDetailsToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendBuyDetailsToServer() {
				// First, we validate the input.
				errorLabel.setText("");
				String volume = volumeField.getText();
				String price = priceField.getText();
				if (FieldVerifier.isNotANumber(volume)) {
					errorLabel
							.setText("Please enter a number greater than 0 and less than 99999");
					return;
				} else if (FieldVerifier.isNotANumber(price)) {
					errorLabel.setText("Price should be greater than 0");
					return;
				}

				// Then, we send the input to the server.

				orderService.sendBuyDetails(volume, price,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user

							}

							public void onSuccess(String result) {
								orderLbl.setText("Order matched!!!");
								tradeLbl.setText("Trade");
								// resultListStore = new ListStore<ResultRow>();
								// resultListStore.add(result);
							}
						});
			}
		}

		// Add a handler to send the name to the server
		BuyHandler buyHandler = new BuyHandler();
		SellHandler sellHandler = new SellHandler();
		buyButton.addClickHandler(buyHandler);
		sellButton.addClickHandler(sellHandler);
	}
	
	public void getOrders(){
		orderService.getOrders(
				new AsyncCallback<List<Order>>() {
					public void onFailure(Throwable caught) {
						

					}

					public void onSuccess(List<Order> result) {

						buyTable.setRowCount(result.size());

			            ListDataProvider<Order> dataProvider = new ListDataProvider<Order>();
			            dataProvider.addDataDisplay(buyTable);

			            List<Order> list = dataProvider.getList();
			            for (Order order : result) {
			                list.add(order);
			            }

			            
						
					}
				});
	}
//	public void getSellOrders(){
//		orderService.getSellOrders(
//				new AsyncCallback<List<String>>() {
//					public void onFailure(Throwable caught) {
//						
//
//					}
//
//					public void onSuccess(List<String> result) {
//
//						sellTable.setRowCount(result.size());
//
//			            ListDataProvider<String> dataProvider = new ListDataProvider<String>();
//			            dataProvider.addDataDisplay(sellTable);
//
//			            List<String> list = dataProvider.getList();
//			            for (String String : result) {
//			                list.add(String);
//			            }
//
//			            ListHandler<String> listHandler = new ListHandler<String>(result);
//
//			            
//			            sellTable.addColumnSortHandler(listHandler);
//						
//					}
//				});
//	}
}
