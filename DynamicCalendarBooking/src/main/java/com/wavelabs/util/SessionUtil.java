package com.wavelabs.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * This contains the creation of Configuration, and building SessionFactory and
 * Sessions
 * 
 * @author tharunkumarb
 *
 */
public class SessionUtil {

	private static Configuration cfg = null;
	private static SessionFactory factory = null;
	private static Session session = null;
	private static int count = 0;

	/**
	 * It gives the SessionFactory object only once. If it is closed then it
	 * will never gives a new SessionFactory
	 * 
	 * @return
	 */

	public static SessionFactory getSessionFactory() {
		if (count == 0)
			buildSessionFactory();
		return factory;
	}

	/**
	 * In this method schema is exported
	 */
	@SuppressWarnings("deprecation")
	private static void buildSessionFactory() {
		cfg = new Configuration();
		cfg.configure();
		factory = cfg.buildSessionFactory();
		session = factory.openSession();
		count++;
	}

	/**
	 * It gives the Session object. If it is closed then it will gives a new
	 * Session
	 * 
	 * @return
	 */
	public static Session getSession() {
		if (count == 0) {
			buildSessionFactory();
			count++;
		}
		if (!session.isOpen()) {
			session = factory.openSession();
		}
		return session;
	}

	/**
	 * In gives the Configuration object only once means schema exported only once
	 * @return
	 */
	public static Configuration getConfiguration() {

		if (count == 0) {
			buildSessionFactory();
			count++;
		}

		return cfg;

	}

}