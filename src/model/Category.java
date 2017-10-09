package model;

public class Category {

	private int categoryID;
	private String name;
	private TransactionType type;
	
	public Category(int categoryID, String name, TransactionType type) {
		this.categoryID = categoryID;
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public TransactionType getType() {
		return type;
	}
	
	
}
