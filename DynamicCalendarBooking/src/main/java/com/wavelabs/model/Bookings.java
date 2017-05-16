package com.wavelabs.model;

public class Bookings {
	private int id;
	private TimeSlots timeslot;
	private Receiver receiver;
	private Status status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TimeSlots getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(TimeSlots timeslot) {
		this.timeslot = timeslot;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
