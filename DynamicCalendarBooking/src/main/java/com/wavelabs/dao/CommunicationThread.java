package com.wavelabs.dao;

import java.util.List;

import com.wavelabs.service.CommunicationService;

public class CommunicationThread implements Runnable {
	List<String> msglist;

	public CommunicationThread(List<String> commlist) {
		// TODO Auto-generated constructor stub
		this.msglist=commlist;
	}

	@Override
	public void run() {
		CommunicationService.send(msglist);
	}
}
