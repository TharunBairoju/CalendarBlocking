package com.wavelabs.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.wavelabs.model.Password;
import com.wavelabs.util.SessionUtil;

public class SignupAndLogin {
	public static String signupUser(String email, String password) {
		try {
			Password pwd = new Password();
			pwd.setEmail(email);
			pwd.setPassword(password);
			Session session = SessionUtil.getSession();
			session.save(pwd);
			session.beginTransaction().commit();
			System.out.println("signup success");
			return "sucess";
		} catch (Exception e) {
			System.out.println("signup fail");
			return "fail";
		}
	}

	public static Password getUser(String email) {
		Password user = null;
		try {
			Criteria criteria = SessionUtil.getSession().createCriteria(Password.class);
			criteria.add(Restrictions.eq("email", email));
			user = (Password) criteria.uniqueResult();
			return user;
		} catch (Exception e) {
			return user;
		}
	}

	public static boolean passwordValidation(Password pwd, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (PasswordValidation.validatePassword(password, pwd.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	public static String userValidation(String email, String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		Password user = getUser(email);
		if (passwordValidation(user, password)) {
			return "Login success";
		} else {
			return "Login fail";
		}
	}

	public static String getUser(String email, String pwd) {
		Password user = null;
		try {
			Criteria criteria = SessionUtil.getSession().createCriteria(Password.class);
			criteria.add(Restrictions.eq("email", email));
			criteria.add(Restrictions.eq("password", pwd));
			user = (Password) criteria.uniqueResult();
			if (user == null) {
				return "success";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			return "something went wrong";
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		  /*String encrypt = PasswordEncryption.generatePwdHash("tharun");
		  System.out.println(encrypt); 
		  signupUser("tharun.it05@gmail.com", encrypt);*/
		 
		String encrypt = PasswordEncryption.generatePwdHash("tharun");
		String result = getUser("tharun.it05@gmail.com", encrypt);
		System.out.println(result);
	}
}
