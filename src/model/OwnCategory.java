package model;

import java.util.Collections;
import java.util.List;

public class OwnCategory {

	private long ownCategoryId;
	private String name;
	private TransactionType type;
	private User user;
	private List<Transaction> transactions;
	private List<Budget> budgets;
	private List<PlannedPayment> plannedPayments;
	
	public OwnCategory(long ownCategoryId, String name, User user, TransactionType type, List<Transaction> transactions, List<Budget> budgets, List<PlannedPayment> plannedPayments) {
		this(name, type, user, transactions, budgets, plannedPayments);
		
		this.ownCategoryId = ownCategoryId;
	}
	
	public OwnCategory(String name, TransactionType type, User user, List<Transaction> transactions, List<Budget> budgets, List<PlannedPayment> plannedPayments) {
		this.name = name;
		this.type = type;
		this.user = user;
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
	
	public long getOwnCategoryId() {
		return ownCategoryId;
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
	
	public void setOwnCategoryId(long ownCategoryId) {
		this.ownCategoryId = ownCategoryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (ownCategoryId ^ (ownCategoryId >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OwnCategory other = (OwnCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownCategoryId != other.ownCategoryId)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
