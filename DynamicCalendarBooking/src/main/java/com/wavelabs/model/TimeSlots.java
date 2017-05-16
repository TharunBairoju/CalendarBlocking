package com.wavelabs.model;

import java.sql.Time;

public class TimeSlots {
	private int id;
	private Time from_time;
	private Status status;
	private SlotInfo slot;
	private Receiver receiver;
	

	public SlotInfo getSlot() {
		return slot;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setSlot(SlotInfo slot) {
		this.slot = slot;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getFrom_time() {
		return from_time;
	}

	public void setFrom_time(Time from_time) {
		this.from_time = from_time;
	}
}
