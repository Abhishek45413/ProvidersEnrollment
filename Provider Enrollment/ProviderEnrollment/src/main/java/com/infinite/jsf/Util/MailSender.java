package com.infinite.jsf.Util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	public static String sendInfo(String toEmail, String subject, String htmlContent) {
		String from = "infinitehealthsure@gmail.com";
		String host = "smtp.gmail.com";

		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "xrascqydsfthxttk");
			}
		});

		session.setDebug(true);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);

			message.setContent(htmlContent, "text/html");

			Transport.send(message);
			return "Mail Sent Successfully...";
		} catch (MessagingException mex) {
			mex.printStackTrace();
			return "Error: " + mex.getMessage();
		}
	}
}
