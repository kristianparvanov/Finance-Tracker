package model;

public class OwnCategory {

	private long ownCategoryId;
	private String name;
	private TransactionType type;
	
	public OwnCategory(String name, TransactionType type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public TransactionType getType() {
		return type;
	}
	
	public void setOwnCategoryId(long ownCategoryId) {
		this.ownCategoryId = ownCategoryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (ownCategoryId ^ (ownCategoryId >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OwnCategory other = (OwnCategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ownCategoryId != other.ownCategoryId)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
