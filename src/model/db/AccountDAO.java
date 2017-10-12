package model.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.User;

public class AccountDAO {
	
	private static AccountDAO instance;
//	private static final HashMap<String, ArrayList<Account>> ALL_ACCOUNTS = new HashMap<>(); 
	private static final List<Account> ALL_ACCOUNTS = new ArrayList<>(); 
	
	private AccountDAO() {
		//getAllAccounts();
	}
	
	public synchronized static AccountDAO getInstance() {
		if (instance == null) {
			instance = new AccountDAO();
		}
		return instance;
	}
	
	// TODO
//	private void getAllAccounts() {
//		// TODO Auto-generated method stub
//		
//	}
	
	public synchronized void insertAccount(Account acc) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, ammount, user_id) "
														+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, acc.getName());
		ps.setBigDecimal(2, acc.getAmount());
		ps.setInt(3, (int)acc.getUserId());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		acc.setAccaountID(rs.getLong(1));
		
		ALL_ACCOUNTS.add(acc);
	}
	
	public synchronized void deleteAccount(int accountId) throws SQLException {
		String sql = "DELETE FROM accounts WHERE account_id = ?";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, accountId);
		ps.executeUpdate();
		
		for (Account account : ALL_ACCOUNTS) {
			if (account.getAccaountId() == accountId) {
				ALL_ACCOUNTS.remove(account);
				
				return;
			}
		}
	}
	
	public Account getAccountByAccountId(int accountId) throws SQLException {
		String query = "SELECT account_id, name, amount, user_id FROM finance_tracker.accounts WHERE finance_tracker.accounts.account_id = ?";
		
		PreparedStatement statement = null;
		Account account = null;
		statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setInt(1, accountId);
		ResultSet result = statement.executeQuery();
		String name = result.getString("name");
		BigDecimal amount = result.getBigDecimal("amount");
		int userId = result.getInt("user_id");
		User user = UserDAO.getInstance().getUserByUserId(userId);
		account = new Account(name, amount, user);
		return account;
	}
}
