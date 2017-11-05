package com.financeTracker.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class PlannedPayment {
	private long plannedPaymentId;
	
	@NotNull
	@Size(min = 2, max = 30)
	@NotEmpty
	private String name;
	
	private TransactionType paymentType;
	private LocalDateTime fromDate;
	
	@NotNull
	@Min(1)
	@Max((long) 999999999.9999)
	private BigDecimal amount;
	
	private String description;
	private long account;
	private String categoryName;
	private long category;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public PlannedPayment(String name, TransactionType paymentType, LocalDateTime fromDate, BigDecimal amount,
			String description, long account, long category, HashSet<Tag> tags) {
		this.name = name;
		this.paymentType = paymentType;
		this.fromDate = fromDate;
		this.amount = amount;
		this.description = description;
		this.account = account;
		this.category = category;
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
	
	
	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	@Override
	public String toString() {
		return "PlannedPayment [plannedPaymentId=" + plannedPaymentId + ", name=" + name + ", paymentType="
				+ paymentType + ", fromDate=" + fromDate + ", amount=" + amount + ", description=" + description
				+ ", account=" + account + ", category=" + category + ", tags=" + tags
				+ "]\n";
	}
}
