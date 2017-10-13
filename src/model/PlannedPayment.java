package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PlannedPayment {
	private long plannedPaymentId;
	private String name;
	private TransactionType paymentType;
	private LocalDateTime fromDate;
	private BigDecimal amount;
	private String description;
	private Account account;
	private Category category;
	private OwnCategory ownCategory;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public PlannedPayment(String name, TransactionType paymentType, LocalDateTime fromDate, BigDecimal amount,
			String description, Account account, Category category, OwnCategory ownCategory, HashSet<Tag> tags) {
		this.name = name;
		this.paymentType = paymentType;
		this.fromDate = fromDate;
		this.amount = amount;
		this.description = description;
		this.account = account;
		this.category = category;
		this.ownCategory = ownCategory;
		this.tags = tags;
	}

	public long getPlannedPaymentId() {
		return plannedPaymentId;
	}
	
	public void setPlannedPaymentId(long plannedPaymentId) {
		this.plannedPaymentId = plannedPaymentId;
	}
	
	public String getName() {
		return name;
	}
	
	public TransactionType getPaymentType() {
		return paymentType;
	}
	
	public LocalDateTime getFromDate() {
		return fromDate;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public String getDescription() {
		return description;
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
