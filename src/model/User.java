package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

public class User {

	private long userId;
	private String userName;
	private byte[] password;
	private String email;
	private String firstName;
	private String lastName;
	private Set<Account> accounts;
	private Set<OwnCategory> ownCategories;
	
	public User(String userName, String password, String email, String firstName, String lastName) {
		this.userName = userName;
		this.password = DigestUtils.sha512(password);
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		
		this.accounts = new HashSet<>();
		this.ownCategories = new HashSet<>();
	}

	public long getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public byte[] getPassword() {
		return password.clone();
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}

	public Set<OwnCategory> getOwnCategories() {
		return Collections.unmodifiableSet(ownCategories);
	}

	public void setUserId(long userID) {
		this.userId = userID;
	}
	
	
}
