package com.wavelabs.service;

import java.util.List;

import org.hibernate.Session;

import com.wavelabs.dao.BookingsDao;
import com.wavelabs.model.Bookings;
import com.wavelabs.model.Provider;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.util.SessionUtil;

public class BookingService {
	static Session session = SessionUtil.getSession();
	static BookingsDao bookingsdao=new BookingsDao();

	public static Bookings getBooking(int receiver_id, int timeslot_id) {
		return bookingsdao.getBooking(receiver_id,timeslot_id);
	}
	public static Bookings getBookingByStatus(int receiver_id, int timeslot_id) {
		return bookingsdao.getBookingByStatus(receiver_id,timeslot_id);
	}

	public static List<String> cancelForRemainingReceivers(List<TimeSlots> timeslotlist) {
		return bookingsdao.cancelBookingForRemaining(timeslotlist);
	}
	public static List<Bookings> getBookings(TimeSlots timeslot){
		return bookingsdao.getBookingByTimeslot(timeslot);
	}
	public static Bookings getBookedBookings(TimeSlots timeSlots) {
		return bookingsdao.getBookedBookings(timeSlots);
	}
	public static List<Bookings> getBookings(Provider provider) {
		return bookingsdao.getBookings(provider);
	}
	

}
