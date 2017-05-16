package com.wavelabs.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.wavelabs.model.Receiver;
import com.wavelabs.util.SessionUtil;

/**
 * In this dao class all the operation related to Consultant will be done based
 * on the input parameters
 * 
 * @author tharunkumarb
 *
 */
public class ReceiverDao {
	Session session = SessionUtil.getSession();
	Logger logger = Logger.getLogger(ReceiverDao.class);

	/**
	 * This method will persist the consultant based on the input parameter
	 * 
	 * @param name
	 * @param email
	 * @param phone_number
	 * @param address
	 * @param password
	 * @return
	 */
	public Receiver addReceiver(String name, String email, long phone_number, String address, String password) {
		Receiver consultant = new Receiver();
		consultant.setName(name);
		consultant.setEmail(email);
		consultant.setPhone_number(phone_number);
		consultant.setAddress(address);
		consultant.setPassword(password);
		session.save(consultant);
		session.beginTransaction().commit();
		return consultant;
	}

	/**
	 * This method will gets the all the consultants in the form of list
	 * 
	 * @return
	 */
	public List<Receiver> getAllReceiver() {
		@SuppressWarnings("unchecked")
		List<Receiver> list = session.createCriteria(Receiver.class).list();
		logger.info("Done with getting receviers list");
		return list;
	}

	/**
	 * This will persist the consultant
	 * 
	 * @param receiver
	 * @return
	 */
	public Receiver persistReceiver(Receiver receiver) {
		session.save(receiver);
		session.beginTransaction().commit();
		logger.info("Done with persisting the receiver");
		return receiver;
	}

	/**
	 * This will get the Consultant by its id
	 * 
	 * @param id
	 * @return
	 */
	public Receiver getReceiverById(int id) {
		Receiver consult = (Receiver) session.get(Receiver.class, id);
		logger.info("Done with getting receiver by id");
		return consult;
	}
}
