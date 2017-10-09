package model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {

	private int userID;
	private String name;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private Set<Account> accounts;
	private Set<OwnCategory> ownCategories;
	
	public User(int userID, String name, String password, String email, String firstName, String lastName) {
		this.userID = userID;
		this.name = name;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		
		this.accounts = new HashSet<>();
		this.ownCategories = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
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
	
	
	
}
