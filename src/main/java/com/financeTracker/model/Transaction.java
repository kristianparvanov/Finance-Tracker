package com.financeTracker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Transaction {
	private long transactionId;
	private TransactionType type;
	private String description;
	private BigDecimal amount;
	private long account;
	private String categoryName;
	private long category;
	private LocalDateTime date;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public Transaction(long transactionId, TransactionType type, String description, BigDecimal amount, long account, long category,
			LocalDateTime date, HashSet<Tag> tags) {
		this(type, description, amount,account, category, date, tags);
		
		this.transactionId = transactionId;
	}

	public Transaction(TransactionType type, String description, BigDecimal amount, long account, long category, LocalDateTime date, HashSet<Tag> tags) {
		this.type = type;
		this.description = description;
		this.amount = amount;
		this.account = account;
		this.category = category;
		this.date = date;
		this.tags = tags;
	}
	
	public Transaction(TransactionType type, BigDecimal amount, LocalDateTime date) {
		this.type = type;
		this.amount = amount;
		this.date = date;
	}
	
	public long getTransactionId() {
		return transactionId;
	}
	
	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public TransactionType getType() {
		return type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public long getAccount() {
		return account;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public long getCategory() {
		return category;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", type=" + type + ", amount=" + amount + ", account="
				+ account + ", category=" + category + ", date=" + date + ", tags="
				+ tags + "]\n";
	}
}
