package com.financeTracker.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskInitializer {
	@Autowired
	private ScheduledTasks tasks;

	@PostConstruct
	public void initialize() {
		tasks.testPrintHi();
	}
}
