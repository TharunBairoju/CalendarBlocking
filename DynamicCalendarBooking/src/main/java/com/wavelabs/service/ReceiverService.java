package com.wavelabs.service;

import org.apache.log4j.Logger;

import com.wavelabs.dao.ReceiverDao;
import com.wavelabs.model.Receiver;

public class ReceiverService {
	static ReceiverDao consultantdao = new ReceiverDao();
	static Logger logger=Logger.getLogger(Receiver.class);

	public static Receiver createReceiver(Receiver receiver) {
		logger.info("persisting receiver is going on");
		return consultantdao.persistReceiver(receiver);
	}

	public static Receiver getReceiverById(int id) {
		logger.info("getting receiver by id is going on");
		return consultantdao.getReceiverById(id);
	}
}
