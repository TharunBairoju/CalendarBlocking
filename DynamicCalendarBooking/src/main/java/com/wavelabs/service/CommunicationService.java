package com.wavelabs.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.wavelabs.dao.CommunicationDao;
import com.wavelabs.model.Receiver;
import com.wavelabs.model.Status;
import com.wavelabs.model.Provider;

public class CommunicationService {
	static CommunicationDao commdao = new CommunicationDao();
	static Logger logger = Logger.getLogger(CommunicationService.class);
	public static Receiver receiver;
	public static Provider provider;
	public static String status;
	public static String times;

	public CommunicationService(Receiver receiver, Provider provider, String status, String times) {
		CommunicationService.receiver = receiver;
		CommunicationService.provider = provider;
		CommunicationService.status = status;
		CommunicationService.times = times;
	}

	public static void sendInfo(Receiver receiver, Provider provider, String status, String times) {
		String message = null;
		if (status == Status.booked.toString()) {
			message = CommunicationService.getSuccessMessage(receiver, provider, times);
			System.out.println(message);
		}
		if (status == Status.available.toString()) {
			message = CommunicationService.getFailureMessage(receiver, provider, times);
		}
		if (status == Status.process.toString()) {
			message = CommunicationService.getProcessMessage(receiver, provider, times);
		}
		if (status == Status.cancel.toString()) {
			message = CommunicationService.getFailureMessage(receiver, provider, times);
		}
		if (status == Status.decline.toString()) {
			message = CommunicationService.getDeclineMessage(receiver, provider, times);
		}
		String phonenumber = "+91" + receiver.getPhone_number();
		String email = receiver.getEmail();
		try {
			commdao.sendSMS(phonenumber, message);
			commdao.sendMail(email, message);
			logger.info("SMS and Mail sending done");
		} catch (Exception e) {
			logger.info("something went wrong while sending messages & mails");
		}
	}

	public static String getSuccessMessage(Receiver consultant, Provider provider, String times) {
		String message = "Congratulations\nHi " + consultant.getName().toUpperCase()
				+ "\nBooking confirmed with the provider \nMr." + provider.getName() + "\nTimings: " + times
				+ "\nPlease be on time";
		System.out.println("message generated");
		logger.info("Done with success message creation");
		return message;

	}

	public static String getFailureMessage(Receiver consultant, Provider provider, String times) {
		String message = "Sorry for this..\nHi " + consultant.getName().toUpperCase()
				+ "\nBooking request is cancelled with the provider \nMr." + provider.getName() + "\nTimings: " + times
				+ "\nPlease kindly book an another timeslots";
		logger.info("done with failure message creation");
		return message;
	}

	public static String getProcessMessage(Receiver receiver, Provider provider, String times) {
		String message = "Congratulations\nHi " + receiver.getName().toUpperCase()
				+ "\nBooking request is under process.. with the provider \nMr." + provider.getName() + "\nTimings: "
				+ times + "\nwill get back to you with update...";
		logger.info("done with process message creation");
		return message;
	}

	public static String getDeclineMessage(Receiver consultant, Provider provider, String times) {
		String message = "Sorry for this..\nHi " + consultant.getName().toUpperCase()
				+ "\nBooking request is decline with the provider \nMr." + provider.getName() + "\nTimings: " + times
				+ "\nPlease kindly book an another timeslots";
		logger.info("done with failure message creation");
		return message;
	}

	public static void sendData(String rName, String phno, String email, String pName, String times, String status) {
		String message = null;
		if (status.equals(Status.booked.toString())) {
			message = CommunicationService.getSuccessMessage(rName, pName, times);
			System.out.println(message);
		}
		if (status.equals(Status.available.toString())) {
			message = CommunicationService.getFailureMessage(rName, pName, times);
		}
		if (status.equals(Status.process.toString())) {
			message = CommunicationService.getProcessMessage(rName, pName, times);
		}
		if (status.equals(Status.cancel.toString())) {
			message = CommunicationService.getFailureMessage(rName, pName, times);
		}
		if (status.equals(Status.decline.toString())) {
			message = CommunicationService.getDeclineMessage(rName, pName, times);
		}
		String phonenumber = "+91" + phno;
		try {
			commdao.sendSMS(phonenumber, message);
			commdao.sendMail(email, message);
			logger.info("SMS and Mail sending done");
		} catch (Exception e) {
			logger.info("something went wrong while sending messages & mails");
		}
	}

	public static void send(List<String> msglist) {
		String rName = "";
		String phno = "";
		String email = "";
		String pName = "";
		String time = "";
		String status = "";
		for (String msgdata : msglist) {
			String[] msgMetaData = msgdata.split(",");
			rName = msgMetaData[0];
			phno = msgMetaData[1];
			email = msgMetaData[2];
			pName = msgMetaData[3];
			time = msgMetaData[5];
			status = msgMetaData[4];
			logger.info(rName);
			logger.info(phno);
			logger.info(email);
			logger.info(pName);
			logger.info(time);
			logger.info(status);
			sendData(rName, phno, email, pName, time, status);
		}
	}

	public static String getSuccessMessage(String rName, String pName, String times) {
		String message = "Congratulations\nHi " + rName.toUpperCase() + "\nBooking confirmed with the provider \nMr."
				+ pName + "\nTimings: " + times + "\nPlease be on time";
		System.out.println("message generated");
		logger.info("Done with success message creation");
		return message;

	}

	public static String getFailureMessage(String rName, String pName, String times) {
		String message = "Sorry for this..\nHi " + rName.toUpperCase()
				+ "\nBooking request is cancelled with the provider \nMr." + pName + "\nTimings: " + times
				+ "\nPlease kindly book an another timeslots";
		logger.info("done with failure message creation");
		return message;
	}

	public static String getProcessMessage(String rName, String pName, String times) {
		String message = "Congratulations\nHi " + rName.toUpperCase()
				+ "\nBooking request is under process.. with the provider \nMr." + pName + "\nTimings: " + times
				+ "\nwill get back to you with update...";
		logger.info("done with process message creation");
		return message;
	}

	public static String getDeclineMessage(String rName, String pName, String times) {
		String message = "Sorry for this..\nHi " + rName.toUpperCase()
				+ "\nBooking request is decline with the provider \nMr." + pName + "\nTimings: " + times
				+ "\nPlease kindly book an another timeslots";
		logger.info("done with failure message creation");
		return message;
	}

}
