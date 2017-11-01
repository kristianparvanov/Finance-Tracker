package com.financeTracker.threads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskInitializer extends Thread {
	@Autowired
	private PlannedPaymentService plannedPaymentService;
	
	@Autowired
	private EmailService emailService;
	
//	@PostConstruct
//	public void initialize() {
	
//	}

	@Override
	public void run() {
		System.out.println("Invoking an asynchronous methods. " + Thread.currentThread().getName());
		this.plannedPaymentService.setDaemon(true);
		this.plannedPaymentService.start();
		this.emailService.setDaemon(true);
		this.emailService.start();
	}
}
