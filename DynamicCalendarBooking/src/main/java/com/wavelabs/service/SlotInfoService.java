package com.wavelabs.service;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.wavelabs.dao.SlotInfoDao;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;

public class SlotInfoService {
	static SlotInfoDao slotinfodao = new SlotInfoDao();
	static Logger logger = Logger.getLogger(SlotInfoService.class);

	public static List<SlotInfo> createSlots(Provider provider,Date fromdate, Date todate, Time fromtime, Time totime,
			int slotDuration) {
		logger.info("creation of availability is going on");
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setFrom_time(fromtime);
		slotinfo.setTo_time(totime);
		slotinfo.setProvider(provider);
		return slotinfodao.persistSlots(fromdate, todate, slotinfo, fromtime, totime, slotDuration);
	}

	/**
	 * this method will give you the all the list of dates from between two
	 * dates in the form of list
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getListOfDates(Date startDate, Date endDate) {
		logger.info("getting list of dates from between two dates is going on");
		List<Date> dates = new ArrayList<Date>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(startDate);
		while (calendar.getTime().before(endDate)) {
			Date result = calendar.getTime();
			dates.add(result);
			calendar.add(Calendar.DATE, 1);
		}
		dates.add(endDate);
		logger.info("done with getting list of dates");
		return dates;
	}

	
	/**
	 * This method gives you the all the slot data
	 * 
	 * @return
	 */
	public static List<SlotInfo> getAllSlotData() {
		logger.info("getting slotdata is going on");
		return slotinfodao.getAllSlotDetails();
	}

	public static int getMonthNumber(String month) {
		int value = 0;
		switch (month) {
		case "January":
			return value;
		case "Febrary":
			return 1;
		case "March":
			return 2;
		case "April":
			return 3;
		case "May":
			return 4;
		case "June":
			return 5;
		case "July":
			return 6;
		case "August":
			return 7;
		case "September":
			return 8;
		case "Octomber":
			return 9;
		case "November":
			return 10;
		case "December":
			return 11;
		}
		return value;
	}

	public static Date getStartDate(String month) {
		System.out.println(SlotInfoService.getMonthNumber(month));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, SlotInfoService.getMonthNumber(month));
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		Date date = calendar.getTime();
		 DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
		 try {
			return	DATE_FORMAT.parse(DATE_FORMAT.format(date));
		} catch (ParseException e) {
			return null;
		}

	}

	public static Date getEndDate(String month) {
		System.out.println(SlotInfoService.getMonthNumber(month));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, SlotInfoService.getMonthNumber(month));
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		Date date = calendar.getTime();
		 DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
		 try {
			return DATE_FORMAT.parse(DATE_FORMAT.format(date));
		} catch (ParseException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(SlotInfoService.getStartDate("May"));
		System.out.println(SlotInfoService.getEndDate("May"));
	}

}
