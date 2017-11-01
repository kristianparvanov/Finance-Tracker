package com.financeTracker.threads;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.db.PlannedPaymentDAO;

@Component
public class PlannedPaymentManager {
	@Autowired
	private PlannedPaymentDAO plannedPaymentDAO;
	
	@Async
	public void testPrintHi() {
		while (true) {
			try {
				List<PlannedPayment> allPlannedPayments = plannedPaymentDAO.getAllPlannedPayments();
				for (PlannedPayment plannedPayment : allPlannedPayments) {
					//foreach pp -> new thread
					PlannedPaymentThread ppt = new PlannedPaymentThread(plannedPayment);
					ppt.start();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

