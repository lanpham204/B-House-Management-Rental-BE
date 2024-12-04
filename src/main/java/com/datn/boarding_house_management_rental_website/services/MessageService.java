package com.datn.boarding_house_management_rental_website.services;

public interface MessageService {
	
	void Producer(String senderName, String receiverName);
	
	void Consumer(String senderName, String receiverName);
	

}
