package com.wavelabs.dao;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.sun.mail.smtp.SMTPTransport;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class CommunicationDao {
	Logger logger = Logger.getLogger(CommunicationDao.class);

	public void sendSMS(String phonenumber, String text) {
		String ACCOUNT_SID = "ACa4f4c33001223961d981631e99c41c61";
		String AUTH_TOKEN = "0f323353aa3a4d3fabe40f6ae32453ca";
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message.creator(new PhoneNumber(phonenumber), // to
				new PhoneNumber("+18187947024"), // from
				text).create();
		logger.info("Done with SMS sending");
	}

	/**
	 * Send email using GMail SMTP server.
	 *
	 * @param username
	 *            GMail username
	 * @param password
	 *            GMail password
	 * @param recipientEmail
	 *            TO recipient
	 * @param title
	 *            title of the message
	 * @param message
	 *            message to be sent
	 * @throws AddressException
	 *             if the email address parse failed
	 * @throws MessagingException
	 *             if the connection is dead or not in the connected state or if
	 *             the message is not a MimeMessage
	 */
	public static void Send(final String username, final String password, String recipientEmail, String title,
			String message) throws AddressException, MessagingException {
		SendMail(username, password, recipientEmail, "", title, message);
	}

	/**
	 * Send email using GMail SMTP server.
	 *
	 * @param username
	 *            GMail username
	 * @param password
	 *            GMail password
	 * @param recipientEmail
	 *            TO recipient
	 * @param ccEmail
	 *            CC recipient. Can be empty if there is no CC recipient
	 * @param title
	 *            title of the message
	 * @param message
	 *            message to be sent
	 * @throws AddressException
	 *             if the email address parse failed
	 * @throws MessagingException
	 *             if the connection is dead or not in the connected state or if
	 *             the message is not a MimeMessage
	 */
	@SuppressWarnings("restriction")
	public static void SendMail(final String username, final String password, String recipientEmail, String ccEmail,
			String title, String message) throws AddressException, MessagingException {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		// Get a Properties object
		Properties props = System.getProperties();
		props.setProperty("mail.smtps.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.socketFactory.port", "587");
		props.setProperty("mail.smtps.auth", "true");

		/*
		 * If set to false, the QUIT command is sent and the connection is
		 * immediately closed. If set to true (the default), causes the
		 * transport to wait for the response to the QUIT command.
		 * 
		 * ref :
		 * http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/
		 * package-summary.html
		 * http://forum.java.sun.com/thread.jspa?threadID=5205249 smtpsend.java
		 * - demo program from javamail
		 */
		props.put("mail.smtps.quitwait", "false");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		// -- Create a new message --
		final MimeMessage msg = new MimeMessage(session);

		// -- Set the FROM and TO fields --
		msg.setFrom(new InternetAddress(username + "@gmail.com"));
		msg.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(recipientEmail, false));

		if (ccEmail.length() > 0) {
			msg.setRecipients(javax.mail.Message.RecipientType.CC, InternetAddress.parse(ccEmail, false));
		}

		msg.setSubject(title);
		msg.setText(message, "utf-8");
		msg.setSentDate(new Date());

		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

		t.connect("smtp.gmail.com", username, password);
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
	}

	public void sendMail(String mailid, String msg) {
		try {
			Send("tharun.it1205", "9666253931", mailid, "Appointment booking", msg);
			logger.info("Done with sending Mail");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
