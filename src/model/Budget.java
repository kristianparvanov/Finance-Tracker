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
	private Account account;
	private Category category;
	private OwnCategory ownCategory;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public Budget(String name, BigDecimal amount, LocalDateTime fromDate, LocalDateTime toDate,
			Account account, Category category, OwnCategory ownCategory, HashSet<Tag> tags) {
		this.name = name;
		this.amount = amount;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.account = account;
		this.category = category;
		this.ownCategory = ownCategory;
		this.tags = tags;
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
	
	public LocalDateTime getFromDate() {
		return fromDate;
	}
	
	public LocalDateTime getToDate() {
		return toDate;
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
	
	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}
}
