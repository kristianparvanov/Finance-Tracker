package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PlannedPayment {
	private int plannedPaymentId;
	private String name;
	private LocalDateTime fromDate;
	private LocalDateTime toDate;
	private BigDecimal amount;
	private Account account;
	private Category category;
	private OwnCategory ownCategory;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public PlannedPayment(int plannedPaymentId, String name, LocalDateTime fromDate, LocalDateTime toDate,
			BigDecimal amount, Account account, Category category, OwnCategory ownCategory) {
		this.plannedPaymentId = plannedPaymentId;
		this.name = name;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.amount = amount;
		this.account = account;
		this.category = category;
		this.ownCategory = ownCategory;
	}
	
	public String getName() {
		return name;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public OwnCategory getOwnCategory() {
		return ownCategory;
	}
	
	public Account getAccount() {
		return account;
	}
	
	public BigDecimal getAmount() {
		return amount;
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
