package com.order.match.shared;


public class FieldVerifier {


	public static boolean isNotANumber(String number) {
		if (number == null || number.isEmpty() || number.length()>5 || number.equalsIgnoreCase("0")) {
			return true;
		}
		
		return !number.matches("[0-9]*");
	}
}
