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
	private long account;
	private long category;
	private long ownCategory;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public Budget(String name, BigDecimal amount, LocalDateTime fromDate, LocalDateTime toDate,
			long account, long category, long ownCategory, HashSet<Tag> tags) {
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
	
	public long getAccount() {
		return account;
	}
	
	public long getCategory() {
		return category;
	}
	
	public long getOwnCategory() {
		return ownCategory;
	}
	
	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	@Override
	public String toString() {
		return "Budget [budgetId=" + budgetId + ", name=" + name + ", amount=" + amount + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", account=" + account + ", category=" + category + ", ownCategory="
				+ ownCategory + ", tags=" + tags + "]\n";
	}
}
