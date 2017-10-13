package model;

import java.util.Collections;
import java.util.List;

public class OwnCategory {

	private long ownCategoryId;
	private String name;
	private TransactionType type;
	private long userID;
	private List<Transaction> transactions;
	private List<Budget> budgets;
	private List<PlannedPayment> plannedPayments;
	
	public OwnCategory(long ownCategoryId, String name, long userID, TransactionType type, List<Transaction> transactions, List<Budget> budgets, List<PlannedPayment> plannedPayments) {
		this(name, type, userID, transactions, budgets, plannedPayments);
		
		this.ownCategoryId = ownCategoryId;
	}
	
	public OwnCategory(String name, TransactionType type, long user, List<Transaction> transactions, List<Budget> budgets, List<PlannedPayment> plannedPayments) {
		this.name = name;
		this.type = type;
		this.userID = user;
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
		result = prime * result + ((budgets == null) ? 0 : budgets.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (ownCategoryId ^ (ownCategoryId >>> 32));
		result = prime * result + ((plannedPayments == null) ? 0 : plannedPayments.hashCode());
		result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (int) (userID ^ (userID >>> 32));
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
		if (budgets == null) {
			if (other.budgets != null)
				return false;
		} else if (!budgets.equals(other.budgets))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownCategoryId != other.ownCategoryId)
			return false;
		if (plannedPayments == null) {
			if (other.plannedPayments != null)
				return false;
		} else if (!plannedPayments.equals(other.plannedPayments))
			return false;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		if (type != other.type)
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}
}
