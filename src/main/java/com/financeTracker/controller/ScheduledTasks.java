package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.financeTracker.model.Account;
import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.PlannedPaymentDAO;

@Component
public class ScheduledTasks {
	@Autowired
	private PlannedPaymentDAO plannedPaymentDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Async
	public void testPrintHi() {
		while (true) {
			try {
				List<PlannedPayment> allPlannedPayments = plannedPaymentDAO.getAllPlannedPayments();
				for (PlannedPayment plannedPayment : allPlannedPayments) {
					if (LocalDateTime.now().isAfter(plannedPayment.getFromDate())) {
						Account acc = accountDAO.getAccountByAccountId(plannedPayment.getAccount());
						BigDecimal newValue = plannedPayment.getAmount();
						BigDecimal oldValue = accountDAO.getAmountByAccountId(acc.getAccountId());
						if (plannedPayment.getPaymentType().equals(TransactionType.EXPENCE)) {
							accountDAO.updateAccountAmount(acc, (oldValue.subtract(newValue)));
						} else 
						if (plannedPayment.getPaymentType().equals(TransactionType.INCOME)) {
							accountDAO.updateAccountAmount(acc, (oldValue.add(newValue)));
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("4s--------------------------------------------------------------------------");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

