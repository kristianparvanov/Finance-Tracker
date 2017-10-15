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
	private long account;
	private long category;
	private long ownCategory;
	private HashSet<Tag> tags = new HashSet<Tag>();
	
	public PlannedPayment(String name, TransactionType paymentType, LocalDateTime fromDate, BigDecimal amount,
			String description, long account, long category, long ownCategory, HashSet<Tag> tags) {
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
		return "PlannedPayment [plannedPaymentId=" + plannedPaymentId + ", name=" + name + ", paymentType="
				+ paymentType + ", fromDate=" + fromDate + ", amount=" + amount + ", description=" + description
				+ ", account=" + account + ", category=" + category + ", ownCategory=" + ownCategory + ", tags=" + tags
				+ "]\n";
	}
}
