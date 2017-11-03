package com.financeTracker.threads;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.db.PlannedPaymentDAO;

@Component
public class PlannedPaymentService {
	

    @Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	private PlannedPaymentDAO plannedPaymentDAO;
	
	@PostConstruct
	public void init() {
		System.out.println("Payment service started");
		try {
			List<PlannedPayment> allPlannedPayments = plannedPaymentDAO.getAllPlannedPayments();
			for (PlannedPayment plannedPayment : allPlannedPayments) {
				//foreach pp -> new thread
				initPaymentThread(plannedPayment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void initPaymentThread(PlannedPayment p) {
		PlannedPaymentThread runnable = applicationContext.getBean(PlannedPaymentThread.class);
		runnable.setPlannedPayment(p);
		Thread t = new Thread(runnable);
		System.out.println("Startring ppt");
		t.setDaemon(true);
		t.start();
	}
}
