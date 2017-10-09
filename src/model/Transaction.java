package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Transaction {
	private int transactionId;
	private TransactionType type;
	private BigDecimal amount;
	private Account account;
	private Category category;
	private OwnCategory ownCategory;
	private LocalDateTime date;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public Transaction(int transactionId, TransactionType type, BigDecimal amount, Account account, Category category,
			OwnCategory ownCategory, LocalDateTime date, HashSet<Tag> tags) {
		this.transactionId = transactionId;
		this.type = type;
		this.amount = amount;
		this.account = account;
		this.category = category;
		this.ownCategory = ownCategory;
		this.date = date;
		this.tags = tags;
	}

	public TransactionType getType() {
		return type;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public OwnCategory getOwnCategory() {
		return ownCategory;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}
	
	public void addTag(Tag t) {
		if (t != null) {
			this.tags.add(t);
		} else {
			//TODO exception in tags/budget?
		}
	}
	
	public void removeTag(Tag t) {
		if (t != null) {
			this.tags.remove(t);
		} else {
			//TODO exception in tags/budget?
		}
	}
}
