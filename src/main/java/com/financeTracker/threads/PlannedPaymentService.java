package com.financeTracker.threads;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.db.PlannedPaymentDAO;

@Component
public class PlannedPaymentService extends Thread {
	@Autowired
	private PlannedPaymentDAO plannedPaymentDAO;
	
	public PlannedPaymentService() {
		//this.initialize();
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				List<PlannedPayment> allPlannedPayments = plannedPaymentDAO.getAllPlannedPayments();
				for (PlannedPayment plannedPayment : allPlannedPayments) {
					//foreach pp -> new thread
					PlannedPaymentThread ppt = new PlannedPaymentThread(plannedPayment);
					System.out.println("Startring ppt");
					ppt.setDaemon(true);
					ppt.start();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(24*60*60*1000); //24h*60min*60s*1000ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
	
	private void initialize() {
		while (true) {
			try {
				List<PlannedPayment> allPlannedPayments = plannedPaymentDAO.getAllPlannedPayments();
				for (PlannedPayment plannedPayment : allPlannedPayments) {
					//foreach pp -> new thread
					PlannedPaymentThread ppt = new PlannedPaymentThread(plannedPayment);
					System.out.println("Startring ppt");
					ppt.setDaemon(true);
					ppt.start();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(24*60*60*1000); //24h*60min*60s*1000ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}
}
