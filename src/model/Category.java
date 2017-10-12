package model;

import java.util.Collections;
import java.util.List;

public class Category {

	private long categoryID;
	private String name;
	private TransactionType type;
	private List<Transaction> transactions;
	private List<Budget> budgets;
	private List<PlannedPayment> plannedPayments;
	
	public Category(String name, TransactionType type, List<Transaction> transactions, List<Budget> budgets, List<PlannedPayment> plannedPayments) {
		this.name = name;
		this.type = type;
		this.transactions = transactions;
		this.budgets = budgets;
		this.plannedPayments = plannedPayments;
	}

	public String getName() {
		return name;
	}

	public TransactionType getType() {
		return type;
	}
	
	public long getCategoryId() {
		return categoryID;
	}
	
	public List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}
	
	public List<Budget> getBudgets() {
		return Collections.unmodifiableList(budgets);
	}
	
	public List<PlannedPayment> getPlannedPayments() {
		return Collections.unmodifiableList(plannedPayments);
	}
	
	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}
}
