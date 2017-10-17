package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Budget {
	private long budgetId;
	private String name;
	private BigDecimal amount;
	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private long accountId;
	private long categoryId;
	private Set<Tag> tags = new HashSet<Tag>();
	private Set<Transaction> transactions = new HashSet<Transaction>();
	
	public Budget(long budgetId, String name, BigDecimal amount, LocalDateTime fromDate, LocalDateTime toDate,
			long accountId, long categoryId, Set<Tag> tags, Set<Transaction> transactions) {
		this(name, amount, fromDate, toDate, accountId, categoryId, tags, transactions);
		
		this.budgetId = budgetId;
	}
	
	public Budget(long budgetId, String name, BigDecimal amount, LocalDateTime fromDate, LocalDateTime toDate,
			long accountId, long categoryId, Set<Tag> tags) {
		this(name, amount, fromDate, toDate, accountId, categoryId, tags, new HashSet<>());
		
		this.budgetId = budgetId;
	}

	public Budget(String name, BigDecimal amount, LocalDateTime fromDate, LocalDateTime toDate,
			long accountId, long categoryId, Set<Tag> tags, Set<Transaction> transactions) {
		this.name = name;
		this.amount = amount;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.accountId = accountId;
		this.categoryId = categoryId;
		this.tags = tags;
		this.transactions = transactions;
	}
	
	public long getBudgetId() {
		return budgetId;
	}
	
	public void setBudgetId(long budgetId) {
		this.budgetId = budgetId;
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public LocalDateTime getFromDate() {
		return fromDate;
	}
	
	public LocalDateTime getToDate() {
		return toDate;
	}
	
	public long getAccountId() {
		return accountId;
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	
	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}
	
	public Set<Transaction> getTransactions() {
		return Collections.unmodifiableSet(transactions);
	}
	
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Budget [budgetId=" + budgetId + ", name=" + name + ", amount=" + amount + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", accountId=" + accountId + ", categoryId=" + categoryId + ", tags=" + tags + "]\n";
	}
}
