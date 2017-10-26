package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.financeTracker.model.Account;
import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.PlannedPaymentDAO;

@Component
public class CronSchedule {
	//@Scheduled(cron = "0 0 6 * * *")
	//@Scheduled(cron = "0 0 21 * * *")
	@Scheduled(cron = "*0 03 21 * * *")
	public void testPrintHi() {
		for (int i = 0; i < 17; i++) {
			System.out.println("HI");
		}
		try {
			List<PlannedPayment> allPlannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPayments();
			for (PlannedPayment plannedPayment : allPlannedPayments) {
				if (LocalDateTime.now().isAfter(plannedPayment.getFromDate())) {
					Account acc = AccountDAO.getInstance().getAccountByAccountId(plannedPayment.getAccount());
					BigDecimal newValue = plannedPayment.getAmount();
					BigDecimal oldValue = AccountDAO.getInstance().getAmountByAccountId(acc.getAccountId());
					if (plannedPayment.getPaymentType().equals(TransactionType.EXPENCE)) {
						AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.subtract(newValue)));
					} else 
					if (plannedPayment.getPaymentType().equals(TransactionType.INCOME)) {
						AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.add(newValue)));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}