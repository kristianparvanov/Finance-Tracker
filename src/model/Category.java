package model;

public class Category {

	private long categoryID;
	private String name;
	private TransactionType type;
	
	public Category(String name, TransactionType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public TransactionType getType() {
		return type;
	}
	
	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}
}
