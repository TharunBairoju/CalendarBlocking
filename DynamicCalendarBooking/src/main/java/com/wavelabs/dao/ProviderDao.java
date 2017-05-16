package com.wavelabs.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.wavelabs.model.Provider;
import com.wavelabs.util.SessionUtil;

/**
 * this is an provider DAO class which is for persist the data
 * 
 * @author tharunkumarb
 *
 */

public class ProviderDao {
	Session session = SessionUtil.getSession();
	Criteria criteria = session.createCriteria(Provider.class);
	Logger logger = Logger.getLogger(ProviderDao.class);

	/**
	 * This method will persist the doctor based on the input data
	 * 
	 * @param name
	 * @param hospital_name
	 * @param hospital_address
	 * @param phone_number
	 * @param email
	 * @param password
	 * @param specialization
	 * @return
	 */
	public Provider addProvider(String name, String address, long phone_number, String email, String password) {
		Provider provider = new Provider();
		provider.setName(name);
		provider.setAddress(address);
		provider.setPhone_number(phone_number);
		provider.setEmail(email);
		provider.setPassword(password);
		session.save(provider);
		session.beginTransaction().commit();
		return provider;
	}

	/**
	 * This method gets the doctors based on id of the doctor
	 * 
	 * @param id
	 * @return Doctor
	 */
	public Provider getProvider(int id) {
		logger.info("Getting provider based on the id");
		return (Provider) session.get(Provider.class, id);
	}

	/**
	 * this method will gets the all the doctors in the form of list
	 * 
	 * @return
	 */
	public List<Provider> getProvidersList() {
		@SuppressWarnings("unchecked")
		List<Provider> providerList = criteria.list();
		logger.info("Done with getting provider list");
		return providerList;
	}

	public Provider persistProvider(Provider provider) {
		session.save(provider);
		session.flush();
		session.beginTransaction().commit();
		logger.info("Done with persisting the provider");
		return provider;
	}

}
