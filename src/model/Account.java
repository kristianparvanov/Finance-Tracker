package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {

	private String name;
	private BigDecimal amount;
	private List<Transaction> transactions;
	
	public Account(String name, BigDecimal amount) {
		this.name = name;
		this.amount = amount;
		
		transactions = new ArrayList<>();
	}
}
