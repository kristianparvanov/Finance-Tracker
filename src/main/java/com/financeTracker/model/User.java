package com.financeTracker.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

public class User {

	private long userId;
	private String username;
	private byte[] password;
	private String email;
	private String firstName;
	private String lastName;
	private Set<Account> accounts;
	private Set<Category> ownCategories;
	private Set<Tag> tags;
	
	public User(String username, String password, String email, String firstName, String lastName, Set<Account> accounts, Set<Category> ownCategories, Set<Tag> tags) {
		this.username = username;
		this.password = DigestUtils.sha512(password);
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accounts = accounts;
		this.ownCategories = ownCategories;
		this.tags = tags;
	}
	
	public User(String username, String password, String email, String firstName, String lastName) {
		this(username, password, email, firstName, lastName, new HashSet<>(), new HashSet<>(), new HashSet<>());
	}


	public long getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return username;
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

	public Set<Category> getOwnCategories() {
		return Collections.unmodifiableSet(ownCategories);
	}

	public Set<Tag> getTags() {
		return Collections.unmodifiableSet(tags);
	}

	public void setUserId(long userID) {
		this.userId = userID;
	}
	
}
