package model;

import java.math.BigDecimal;

public class Transaction {
	private int transactionId;
	private TransactionType type;
	private BigDecimal amount;
	private Account account;
	private Category category;
	private OwnCategory ownCategory;
}
