package model.db;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.Map;

import model.Account;
import model.Transaction;
import model.User;

public class UserDAO {

	private static UserDAO instance;
	private static final Map<String, User> ALL_USERS = new HashMap<>();
	
	private UserDAO() throws SQLException{
		getAllUsers();
	}
	
	private void getAllUsers() throws SQLException {
		if (!ALL_USERS.isEmpty()) {
			return;
		}
		
		String sql = "SELECT user_id, username, password, email, first_name, last_name FROM users";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ResultSet res = ps.executeQuery();
		
		while(res.next()) {
			String userName = res.getString("username");
			String password = res.getString("password");
			String email = res.getString("email");
			String firstName = res.getString("firstName");
			String lastName = res.getString("last_name");
			
			User user = new User(userName, password, email, firstName, lastName);
			user.setUserId(res.getInt("user_id"));
			
			ALL_USERS.put(userName, user);
		}
	}

	public static synchronized UserDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new UserDAO();
		}
		
		return instance;
	}
	
	public synchronized void insertUser(User u) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, email, first_name, last_name) "
														+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, u.getUserName());
		ps.setString(2, DigestUtils.sha512Hex(u.getUserName()));
		ps.setString(3, u.getEmail());
		ps.setString(4, u.getFirstName());
		ps.setString(5, u.getLastName());
		ps.executeUpdate();
		
		ResultSet res = ps.getGeneratedKeys();
		res.next();
		
		u.setUserId(res.getLong(1));
		
		ALL_USERS.put(u.getUserName(), u);
	}
	
	public synchronized User getUserByUserId(int userId) {
		if (!ALL_USERS.isEmpty()) {
			for (User u : ALL_USERS.values()) {
				if (u.getUserId() == userId) {
					return u;
				}
			}
		}
		
		return null;
	}
	
	public synchronized int getUserId(String username) {		
		int id = 0;
		
		if (ALL_USERS.containsKey(username)) {
			id = (int)ALL_USERS.get(username).getUserId();
		}
		
		return id;
	}
	
	public synchronized User getUser(String username) {
		User user = null;
		
		if (ALL_USERS.containsKey(username)) {
			user = ALL_USERS.get(user);
		}
		
		return user;
	}
	
	public synchronized void deleteUser(String username) throws SQLException {
		String sql = "DELETE FROM users WHERE user_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setString(1,  username);
		ps.executeUpdate();
		
		ALL_USERS.remove(username);
	}
	
	public synchronized ArrayList<Account> getAllAccountsByUser(String username) throws SQLException {
		User user = ALL_USERS.get(username);
		
		ArrayList<Account> accounts = new ArrayList<>();
		
		String sql = "SELECT a.account_id, a.name, a.amount FROM accounts a JOIN users u ON a.user_id = u.user_id AND u.username = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setString(1, username);
		
		ResultSet res = ps.executeQuery();
		
		while(res.next()) {
			int accountId = res.getInt("account_id");
			String name = res.getString("name");
			BigDecimal amount = new BigDecimal(res.getDouble("amount"));
			List<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsByAccountId(accountId);
			
			Account acc = new Account(name, amount, user, transactions);
			acc.setAccaountID(accountId);
			
			accounts.add(acc);
		}
		
		return accounts;
	}
	
	public synchronized boolean isValidLogin(String username, String password) throws SQLException {
		byte[] hashedPassword = DigestUtils.sha512(DigestUtils.sha512Hex(DigestUtils.sha512(password)));
		
		getAllUsers();
		
		if (ALL_USERS.containsKey(username)) {
			User user = ALL_USERS.get(username);
			
			return MessageDigest.isEqual(hashedPassword, user.getPassword());
		}
		
		return false;
	}
}
