package com.wavelabs.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.wavelabs.model.Bookings;
import com.wavelabs.model.Provider;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.service.CommunicationService;
import com.wavelabs.service.ProviderrService;
import com.wavelabs.util.SessionUtil;

public class BookingsDao {
	static Session session = SessionUtil.getSession();

	public Bookings getBooking(int receiver_id, int timeslot_id) {
		Bookings booking=null;
		try{
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("receiver.id", receiver_id));
			criteria.add(Restrictions.eq("timeslot.id", timeslot_id));
			criteria.add(Restrictions.eq("status", Status.process));
			booking = (Bookings) criteria.uniqueResult();			
			return booking;
		}catch (Exception e) {
			return booking;
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<Bookings> getBookingByTimeslot(TimeSlots timeslot) {
		List<Bookings> bookingslist=new ArrayList<Bookings>();
		try {
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("timeslot.id", timeslot.getId()));
			criteria.add(Restrictions.eq("status", Status.process));
			bookingslist = criteria.list();
			return bookingslist;
		} catch (Exception e) {
			return bookingslist;
		}
	}

	public List<String> cancelBookingForRemaining(List<TimeSlots> timeslotlist) {
		List<String>msgData=new ArrayList<String>();
		List<CommunicationService> commlist=new ArrayList<CommunicationService>();
		for (TimeSlots timeslot : timeslotlist) {
			List<Bookings> bookinglist = getBookingByTimeslot(timeslot);
			for (Bookings bookings : bookinglist) {
				bookings.setStatus(Status.cancel);
				session.update(bookings);
				session.flush();
				/*CommunicationService.sendInfo(bookings.getReceiver(), timeslot.getSlot().getProvider(),
						Status.cancel.toString(), timeslot.getFrom_time().toString());*/
				commlist.add(new CommunicationService(bookings.getReceiver(), timeslot.getSlot().getProvider(),
						Status.cancel.toString(), timeslot.getFrom_time().toString()));
				msgData.add(bookings.getReceiver().getName()+","+bookings.getReceiver().getPhone_number()+","+bookings.getReceiver().getEmail()+","+timeslot.getSlot().getProvider().getName()+","+Status.cancel.toString()+","+timeslot.getFrom_time().toString());
			}
			session.beginTransaction().commit();
		}
		/*CommunicationThread cthread=new CommunicationThread(msgData);
		Thread thread = new Thread(cthread);
		thread.start();*/
		return msgData;
	}
	public Bookings getBookingByStatus(int receiver_id, int timeslot_id) {
		Bookings booking=null;
		try{
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("receiver.id", receiver_id));
			criteria.add(Restrictions.eq("timeslot.id", timeslot_id));
			criteria.add(Restrictions.or(Restrictions.eq("status", Status.process),Restrictions.eq("status", Status.booked)));
			booking = (Bookings) criteria.uniqueResult();			
			return booking;
		}catch (Exception e) {
			return booking;
		}
		
	}

	public Bookings getBookedBookings(TimeSlots timeSlots) {
		Bookings booking=null;
		try{
			Criteria criteria = session.createCriteria(Bookings.class);
			criteria.add(Restrictions.eq("timeslot.id", timeSlots.getId()));
			criteria.add(Restrictions.eq("status", Status.booked));
			booking = (Bookings) criteria.uniqueResult();			
			return booking;
		}catch (Exception e) {
			return booking;
		}
	}

	public List<Bookings> getBookings(Provider provider) {
	System.out.println("getting bookings");
		Criteria criteria = session.createCriteria(Bookings.class);
		criteria.createAlias("timeslot", "timeslot");
		criteria.createAlias("timeslot.slot", "slot");
		criteria.createAlias("slot.provider", "provider");
		criteria.add(Restrictions.eq("provider.id", provider.getId()));
		criteria.add(Restrictions.eq("status", Status.process));
		@SuppressWarnings("unchecked")
		List<Bookings> bookings = criteria.list();
		return bookings;
	}
	public static void main(String[] args) {
		Provider provider=ProviderrService.getProviderById(1);
		System.out.println(provider);
		Criteria criteria = session.createCriteria(Bookings.class);
		criteria.createAlias("timeslot", "timeslot");
		criteria.createAlias("timeslot.slot", "slot");
		criteria.createAlias("slot.provider", "provider");
		criteria.add(Restrictions.eq("provider.id", provider.getId()));
		criteria.add(Restrictions.eq("status", Status.process));
		@SuppressWarnings("unchecked")
		List<Bookings> bookings = criteria.list();
		System.out.println(bookings);
	}
}
