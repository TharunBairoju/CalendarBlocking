package com.wavelabs.service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import com.wavelabs.dao.ProviderDao;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;

public class ProviderrService {
	static ProviderDao providerdao = new ProviderDao();
	static Logger logger = Logger.getLogger(ProviderrService.class);

	public static Provider persistProvider(Provider provider) {
		logger.info("persisting going on");
		return providerdao.persistProvider(provider);
	}

	/**
	 * This method will gets the doctor based on the id
	 * 
	 * @param id
	 * @return
	 */
	public static Provider getProviderById(int id) {
		logger.info("getting provider going on");
		return providerdao.getProvider(id);
	}

	/**
	 * This method will persist the slots which is created by doctor
	 * 
	 * @param id
	 * @param fromdate
	 * @param todate
	 * @param fromtime
	 * @param totime
	 * @param personsPerSlot
	 * @return
	 */
	public static List<SlotInfo> providerSlots(int id, Date fromdate, Date todate, Time fromtime, Time totime,
			int slotduration) {
		logger.info("creation of availability is going on");
		Provider provider = getProviderById(id);
		return SlotInfoService.createSlots(provider, fromdate, todate, fromtime, totime, slotduration);
	}

	/**
	 * This method will gets the all the doctors in the DB
	 * 
	 * @return
	 */
	public static List<Provider> getAllProviders() {
		logger.info("getting all providers going on");
		return providerdao.getProvidersList();
	}

	/**
	 * This method will get the all the timeslots of a provider
	 * 
	 * @param id
	 * @return
	 */
	public static List<TimeSlots> getTimeslotList(int id) {
		Provider provider = getProviderById(id);
		return TimeSlotService.getTimeSlotList(provider);
	}

	/**
	 * This method will gets the Doctor based on the id
	 * 
	 * @param id
	 * @return
	 */
	public static List<TimeSlots> getBookedData(int id) {
		logger.info("getting slotdata going on");
		List<TimeSlots>list=TimeSlotService.getBookedSlotData(getProviderById(id));
		return list.stream().filter(timeslot->timeslot.getStatus()==Status.booked).collect(Collectors.toList());
	}

	/**
	 * This will gets the all the booked slot data to the doctor
	 * 
	 * @param id
	 * @return
	 */
	public static String getBookedInformation(int id) {
		List<TimeSlots> timeslot = getBookedData(id);
		logger.info("getting slotdata is ready");
		String slotdata = "--------------------------------BOOKED SLOT INFORMATION--------------------------------------\n";
		for (TimeSlots slot : timeslot) {
				slotdata = slotdata + "\tTimings:  " + slot.getFrom_time()
						+ "\tAttendeeName:  " + slot.getReceiver().getName() + "\tPhonenumber:"
						+ slot.getReceiver().getPhone_number() + "\n";
		}
		return slotdata;
	}

	public static String createAvailability(String id) {
		return TimeSlotService.createAvailability(id);
	}
}
