package com.wavelabs.dao;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.service.SlotInfoService;
import com.wavelabs.service.TimeSlotService;
import com.wavelabs.util.SessionUtil;

public class SlotInfoDao {
	static Session session = SessionUtil.getSession();
	private static Logger logger = Logger.getLogger(SlotInfoDao.class);

	/**
	 * this will persist the single slot with particular doctor
	 * 
	 * @param date
	 * @param from_time
	 * @param to_time
	 * @param no_of_time_slots
	 * @param no_of_booked
	 * @param status
	 * @param provider
	 * @return
	 */
	public SlotInfo addSlot(Date date, Time from_time, Time to_time, int no_of_time_slots, int no_of_booked,
			Provider provider) {
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setFrom_time(from_time);
		slotinfo.setTo_time(to_time);
		slotinfo.setProvider(provider);
		slotinfo.setDate(date);
		session.save(slotinfo);
		session.beginTransaction().commit();
		return slotinfo;
	}

	/**
	 * this will persist the Slots
	 * 
	 * @param slotinfo
	 * @return
	 */
	public SlotInfo persistSlotInfo(SlotInfo slotinfo) {
		try {
			session.save(slotinfo);
			session.beginTransaction().commit();
			logger.info("Done with persisting the slotinfo");
		} catch (Exception e) {
			logger.warn("Something went wrong in persisting the slotinfo");
			logger.info("Reason:" + e.getMessage());
		}

		return slotinfo;
	}

	/**
	 * This method will fetch the all the slots data in the form of list
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SlotInfo> getAllSlotDetails() {
		try {
			Criteria criteria = session.createCriteria(SlotInfo.class);
			List<SlotInfo> slotList = criteria.list();
			logger.info("Done with getting slotinfo list");
			return slotList;
		} catch (Exception e) {
			List<SlotInfo> slotlist = new ArrayList<>();
			logger.warn("Something went wrong while getting slotinfo with criteria");
			logger.info("Reason " + e.getMessage());
			return slotlist;
		}
	}

	/**
	 * This method will persist the all the slots with particular doctor
	 * 
	 * @param name
	 * @param phno
	 * @param date1
	 * @param date2
	 * @param slotinfo
	 * @return
	 */
	public List<SlotInfo> persistSlots(Date date1, Date date2, SlotInfo slotinfo, Time startTime, Time endTime,
			int slotDuration) {
		List<SlotInfo> listOfSlots = new ArrayList<SlotInfo>();
		System.out.println("before1");
		try {
			System.out.println("start slot creation");
			List<Time>timingslist=TimeSlotService.getTimings(startTime, endTime, slotDuration);
			List<Date> datesList = SlotInfoService.getListOfDates(date1, date2);
			for (int i = 0; i < datesList.size(); i++) {
				slotinfo.setDate(datesList.get(i));
				session.save(slotinfo);
				session.flush();
				listOfSlots.add(slotinfo);
				TimeSlotService.createTimeSlots(slotinfo, timingslist);
				session.clear();
			}
			session.beginTransaction().commit();
			System.out.println("slot and timeslot creation done");
		} catch (Exception e) {
			logger.warn("Something went wrong in the slot creation");
			logger.info("Reason :" + e.getMessage());
			List<SlotInfo> slotlist = new ArrayList<SlotInfo>();
			return slotlist;
		}
		return listOfSlots;
	}	
}
