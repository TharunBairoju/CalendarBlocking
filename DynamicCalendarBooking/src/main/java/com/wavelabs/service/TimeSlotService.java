package com.wavelabs.service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.wavelabs.dao.TimeSlotDao;
import com.wavelabs.model.Bookings;
import com.wavelabs.model.Ids;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.model.TimeSlots;

public class TimeSlotService {
	static TimeSlotDao timeslotdao = new TimeSlotDao();
	static Logger logger = Logger.getLogger(TimeSlotService.class);

	/**
	 * This method will persist the timeslots
	 * 
	 * @param slotinfo
	 * @param timings
	 */
	public static void createTimeSlots(SlotInfo slotinfo, List<Time> timings) {
		logger.info("creation of timeslots going on");
		timeslotdao.persistTimeslots(slotinfo, timings);
	}

	public static int getNoOfSlots(Time startTime, Time endTime, int slotDuration) {
		logger.info("finding no of timeslots based start, end time and slotduration");
		int slotinMilli = slotDuration * 60 * 1000;
		int count = 0;
		while (startTime.getTime() < endTime.getTime()) {
			startTime = new Time(startTime.getTime() + slotinMilli);
			count++;
		}
		logger.info("no of timeslots for a slot " + count);
		return count;
	}

	/**
	 * This method will creates the slot timings based on the persons per slot
	 * which is given by the provider and returns the list of timings
	 * 
	 * @param startTime
	 * @param endTime
	 * @param slotDuration
	 * @return
	 */

	public static List<Time> getTimings(Time startTime, Time endTime, int slotDuration) {
		logger.info("getting timeslot timings list based on the start and end time slotduration");
		List<Time> timeslotList = new ArrayList<Time>();
		int noOfslots = TimeSlotService.getNoOfSlots(startTime, endTime, slotDuration);
		System.out.println(noOfslots);
		long durationtime = slotDuration * 60 * 1000;
		int count = 0;
		timeslotList.add(startTime);
		while (startTime.getTime() < endTime.getTime()) {
			if (count < noOfslots) {
				startTime = new Time(startTime.getTime() + durationtime);
				timeslotList.add(startTime);
				count++;
			}
		}
		logger.info("done with getting timings of the timeslots");
		return timeslotList;
	}

	/**
	 * This will gives the timeslot based on the timeslot id
	 * 
	 * @param id
	 * @return
	 */
	public static TimeSlots getTimeSlotById(int id) {
		logger.info("getting timeslot by id going on");
		return timeslotdao.getTimeslotById(id);
	}

	/**
	 * This method will gives the all the available timeslots in the table
	 * 
	 * @return
	 */
	public static List<TimeSlots> getAllAvailabletimes() {
		return timeslotdao.getAllAvailableTimeSlots();
	}

	/**
	 * This method will gives the all the booked timeslots in the table
	 * 
	 * @return
	 */
	public static List<TimeSlots> getAllBookedtimes() {
		return timeslotdao.getAllBookedTimeSlots();
	}

	/**
	 * This method will gives the all the available timeslots based on the
	 * slotid
	 * 
	 * @param slotid
	 * @return
	 */
	public static List<TimeSlots> getAllAvailableTimesPerSlot(int slotid) {
		logger.info("getting available times of a slot is going on");
		return timeslotdao.getAvaialbleTimeSlotsPerSlot(slotid);
	}

	/**
	 * This method will gives the all the booked timeslots based on the slotid
	 * 
	 * @param slotid
	 * @return
	 */
	public static List<TimeSlots> getAllBookedTimesPerSlot(int slotid) {
		logger.info("getting booked times of a slot is going on");
		return timeslotdao.getBookedTimeSlotsPerSlot(slotid);
	}

	/**
	 * This method will books the timeslot for the consultant with particular
	 * timeslot
	 * 
	 * @param receiverid
	 * @param timeslotid
	 * @return
	 */
	public static String bookTimeSlot(int receiverid, String ids) {
		logger.info("Booking a timeslot is going on");
		return timeslotdao.updateTimeslot(ReceiverService.getReceiverById(receiverid), getIds(ids));
	}

	/**
	 * This method will gets the all the timeslots in the table
	 * 
	 * @return
	 */
	public static List<TimeSlots> getAllTimeSlots() {
		return timeslotdao.getAllTimeSlots();
	}

	/**
	 * This method will update the timeslot into booked
	 * 
	 * @param timeslotid
	 * @return
	 */

	public static String confirmBooking(Ids[] ids) {
		logger.info("confirming booking is going on");
		return timeslotdao.confirmBooking(ids);
	}

	/**
	 * This method will make the timeslot as available
	 * 
	 * @param timeslotid
	 * @return
	 */
	public static String cancelBooking(Ids[] ids) {
		logger.info("cancelling booking is going on");
		return timeslotdao.cancelBooking(ids);
	}

	public static int[] getIds(String ids) {
		String[] stringids = ids.split(",");
		int[] intids = new int[stringids.length];
		for (int i = 0; i < stringids.length; i++) {
			intids[i] = Integer.parseInt(stringids[i]);
		}
		return intids;
	}

	public static String createAvailability(String id) {
		return timeslotdao.createAvailability(getIds(id));
	}

	public static List<TimeSlots> getTimeSlotList(int[] ids) {
		return timeslotdao.getTimeslots(ids);
	}

	public static List<TimeSlots> getTimeSlotList(Provider provider) {
		return timeslotdao.getTimeslotsList(provider);
	}

	public static List<TimeSlots> getTimeSlotsByProvider(int id) {
		return timeslotdao.getTimeslotsList(ProviderrService.getProviderById(id));
	}

	public static List<TimeSlots> getBookedSlotData(Provider provider) {
		return timeslotdao.getTimeslotsList(provider);
	}
	public static List<TimeSlots> getBookingInfo(Provider provider){
		return timeslotdao.getTimeslotsBookingInfo(provider);
	}

	public static String modifyBooking(String ids) {
		return timeslotdao.modifyBookings(ids);
	}

	public static String declineBooking(String ids) {
		return timeslotdao.declineBookings(ids);
	}

	public static List<Bookings> getBookings(int id) {
		Provider provider=ProviderrService.getProviderById(id);
		return BookingService.getBookings(provider);
	}

	


}
