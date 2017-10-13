package model;

import java.math.BigDecimal;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Account {

	private long accaountID;
	private String name;
	private BigDecimal amount;
	private int user;
	private List<Transaction> transactions;
	private List<Budget> budgets;
	private List<PlannedPayment> plannedPayments;
	
	
	public Account(String name, BigDecimal amount, int user, List<Transaction> transactions, List<Budget> budgets, List<PlannedPayment> plannedPayments) {
		this.name = name;
		this.amount = amount;
		this.user = user;
		this.transactions = transactions;
		this.budgets = budgets;
		this.plannedPayments = plannedPayments;
	}
	
	public Account(String name, BigDecimal amount, int user) {
		this(name, amount, user, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}

	public String getName() {
		return name;
	}

	public BigDecimal getAmount() {
		return amount;
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
	
	public long getAccaountId() {
		return accaountID;
	}

	public long getUserId() {
		return user;
	}

	public void setAccaountID(long accaountID) {
		this.accaountID = accaountID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accaountID ^ (accaountID >>> 32));
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((transactions == null) ? 0 : transactions.hashCode());
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
		Account other = (Account) obj;
		if (accaountID != other.accaountID)
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (transactions == null) {
			if (other.transactions != null)
				return false;
		} else if (!transactions.equals(other.transactions))
			return false;
		return true;
	}

}
