package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Transaction {
	private long transactionId;
	private TransactionType type;
	private BigDecimal amount;
	private long account;
	private long category;
	private LocalDateTime date;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public Transaction(TransactionType type, BigDecimal amount, long account, long category, LocalDateTime date, HashSet<Tag> tags) {
		this.type = type;
		this.amount = amount;
		this.account = account;
		this.category = category;
		this.date = date;
		this.tags = tags;
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
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public long getAccount() {
		return account;
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
