package com.wavelabs.dao;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;

import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.util.SessionUtil;

public class DaoChecking {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ParseException {
		ProviderDao doctor = new ProviderDao();
		Provider provider = doctor.addProvider("shankar", " plot8 jublihills, hitechcity", 966253254, "tharun.it05@gmail.com",
				"tharun");
		System.out.println("@@@@@@@@@@@@@ provider created");

		SlotInfoDao slotinfo = new SlotInfoDao();
		DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		Date date1=(Date)format.parse("17/04/2017");
		
		SlotInfo slot = slotinfo.addSlot(date1, new Time(8, 00, 00), new Time(12, 00, 00),1, 0, provider);
		System.out.println("@@@@@@@@@@@@@@@@ slot created");
		
		TimeSlots timeslot=new TimeSlots();
		timeslot.setFrom_time(new Time(8, 00, 00));
		timeslot.setSlot(slot);
		timeslot.setStatus(Status.available);
		Session session=SessionUtil.getSession();
		session.save(timeslot);
		session.flush();
		session.beginTransaction().commit();
		session.close();
	}
}
