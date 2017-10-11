package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Budget {
	private int budgetId;
	private String name;
	private BigDecimal amount;
	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private Account account;
	private Category category;
	private OwnCategory ownCategory;
	//tags Ì:Ì??
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public Budget(String name, BigDecimal amount, LocalDateTime fromDate, LocalDateTime toDate,
			Account account, Category category, OwnCategory ownCategory) {
		this.name = name;
		this.amount = amount;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.account = account;
		this.category = category;
		this.ownCategory = ownCategory;
	}
	
	public String getName() {
		return name;
	}
	
	public BigDecimal getAmount() {
		return amount;
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
	
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
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
