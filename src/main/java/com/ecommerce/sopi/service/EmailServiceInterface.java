package com.ecommerce.sopi.service;

public interface EmailServiceInterface {
	void sendHtmlMessage(String to, String subject, String htmlBody); // Gửi một đoạn Email message theo định dạng HTML
}
