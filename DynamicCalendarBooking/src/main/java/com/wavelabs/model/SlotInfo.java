package com.wavelabs.model;

import java.sql.Time;
import java.util.Date;

public class SlotInfo {
	private int id;
	private Date date;
	private Time from_time;
	private Time to_time;
	private Provider provider;

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getFrom_time() {
		return from_time;
	}

	public void setFrom_time(Time from_time) {
		this.from_time = from_time;
	}

	public Time getTo_time() {
		return to_time;
	}

	public void setTo_time(Time to_time) {
		this.to_time = to_time;
	}
	
}
