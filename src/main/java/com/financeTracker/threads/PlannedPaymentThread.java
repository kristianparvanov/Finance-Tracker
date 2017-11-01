package com.financeTracker.threads;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.financeTracker.model.Account;
import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.Tag;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.PlannedPaymentDAO;
import com.financeTracker.model.db.TransactionDAO;

public class PlannedPaymentThread extends Thread {
	PlannedPayment plannedPayment;
	private volatile boolean running = true;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private PlannedPaymentDAO plannedPaymentDAO;

	public PlannedPaymentThread(PlannedPayment plannedPayment) {
		this.plannedPayment = plannedPayment;
	}
	
	@Override
	public void run() {
		while (running) {
			Period diff = Period.between(LocalDate.now(), this.plannedPayment.getFromDate().toLocalDate());
			try {
				Thread.sleep(diff.getDays()*24*60*60*1000); //days*24h*60m*60s*1000ms
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				Account acc = accountDAO.getAccountByAccountId(this.plannedPayment.getAccount());
				BigDecimal newValue = this.plannedPayment.getAmount();
				BigDecimal oldValue = accountDAO.getAmountByAccountId(acc.getAccountId());
				Transaction t = null;
				if (this.plannedPayment.getPaymentType().equals(TransactionType.EXPENCE)) {
					accountDAO.updateAccountAmount(acc, oldValue.subtract(newValue));
					t = new Transaction(TransactionType.EXPENCE, "Planned Payment Expense", this.plannedPayment.getAmount(), acc.getAccountId(), this.plannedPayment.getCategory(), LocalDateTime.now(), (HashSet<Tag>) this.plannedPayment.getTags());
				} else 
				if (this.plannedPayment.getPaymentType().equals(TransactionType.INCOME)) {
					accountDAO.updateAccountAmount(acc, oldValue.add(newValue));
					t = new Transaction(TransactionType.INCOME, "Planned Payment Income", this.plannedPayment.getAmount(), acc.getAccountId(), this.plannedPayment.getCategory(), LocalDateTime.now(), (HashSet<Tag>) this.plannedPayment.getTags());
				}
				transactionDAO.insertTransaction(t);
				
				plannedPaymentDAO.deletePlannedPayment(this.plannedPayment.getPlannedPaymentId());
				running = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
