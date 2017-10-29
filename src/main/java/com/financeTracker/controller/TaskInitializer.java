package com.financeTracker.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskInitializer implements Runnable {
	@Autowired
	private ScheduledTasks tasks;

	@PostConstruct
	public void initialize() {
		// tasks.testPrintHi();
	}
	

	@Override
	public void run() {
		System.out.println("Invoking an asynchronous method. " + Thread.currentThread().getName());
		//tuka nadolu dava null na  ScheduledTasks tasks
		tasks.testPrintHi();
		
	}
}