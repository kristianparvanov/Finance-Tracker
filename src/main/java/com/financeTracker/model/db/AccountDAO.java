package com.financeTracker.model.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.financeTracker.model.Account;
import com.financeTracker.model.Budget;
import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.User;

public class AccountDAO {
	
	private static AccountDAO instance;
	
	private AccountDAO() {}
	
	public synchronized static AccountDAO getInstance() {
		if (instance == null) {
			instance = new AccountDAO();
		}
		return instance;
	}
	
	public synchronized void insertAccount(Account acc) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO accounts (name, amount, user_id) "
														+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, acc.getName());
		ps.setBigDecimal(2, acc.getAmount());
		ps.setInt(3, (int)acc.getUserId());
		ps.executeUpdate();
		
		ResultSet res = ps.getGeneratedKeys();
		res.next();
		
		acc.setAccaountID(res.getLong(1));
	}
	
	public synchronized void deleteAccount(int accountId) throws SQLException {
		String sql = "DELETE FROM accounts WHERE account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, accountId);
		ps.executeUpdate();
	}
	
	public synchronized long getAccountId(User user, String name) throws SQLException {
		String sql = "SELECT account_id FROM accounts WHERE user_id = ? and name = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, user.getUserId());
		ps.setString(2, name);
		
		ResultSet res = ps.executeQuery();
		res.next();
		
		return res.getLong("account_id");
	}
	
	public synchronized Set<Account> getAllAccountsByUserId(long userId) throws SQLException {
		Set<Account> accounts = new HashSet<>();
		
		String sql = "SELECT account_id, name, amount FROM accounts WHERE user_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, userId);
		
		ResultSet res = ps.executeQuery();
		
		while(res.next()) {
			long accountId = res.getInt("account_id");
			String name = res.getString("name");
			BigDecimal amount = new BigDecimal(res.getDouble("amount"));
			List<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsByAccountId(accountId);
			List<Budget> budgets = BudgetDAO.getInstance().getAllBudgetsByAccountId(accountId);
			List<PlannedPayment> plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPaymentsByAccountId(accountId);
			
			Account acc = new Account(name, amount, userId, transactions, budgets, plannedPayments);
			acc.setAccaountID(accountId);
			
			accounts.add(acc);

		}
		
		return accounts;
	}
	
	public synchronized Account getAccountByAccountId(long accountId) throws SQLException {
		String sql = "SELECT name, amount, user_id FROM accounts WHERE accounts.account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, accountId);
		
		ResultSet res = ps.executeQuery();
		res.next();
		
		String name = res.getString("name");
		BigDecimal amount = res.getBigDecimal("amount");
		int userId = res.getInt("user_id");
		List<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsByAccountId(accountId);
		List<Budget> budgets = BudgetDAO.getInstance().getAllBudgetsByAccountId(accountId);
		List<PlannedPayment> plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPaymentsByAccountId(accountId);
		
		Account acc = new Account(name, amount, userId, transactions, budgets, plannedPayments);
		acc.setAccaountID(accountId);
		
		return acc;
	}
	
	public synchronized void updateAccountAmount(Account acc, BigDecimal newAmount) throws SQLException {
		String sql = "UPDATE accounts SET amount = ? WHERE account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setBigDecimal(1, newAmount);
		ps.setLong(2, acc.getAccountId());
		ps.executeUpdate();
	}
	
	public synchronized void makeTransferToOtherAccount(Account currentAcc, Account otherAcc, BigDecimal amount) throws SQLException {
		DBManager.getInstance().getConnection().setAutoCommit(false);
		try {
			updateAccountAmount(currentAcc, currentAcc.getAmount().subtract(amount));
			updateAccountAmount(otherAcc, otherAcc.getAmount().add(amount));
			DBManager.getInstance().getConnection().commit();
		} catch (Exception e) {
			DBManager.getInstance().getConnection().rollback();
			throw new SQLException();
		} finally {
			DBManager.getInstance().getConnection().setAutoCommit(true);
		}
	}
	
	public synchronized BigDecimal getAmountByAccountId(long accountId) throws SQLException {
		String sql = "SELECT amount FROM accounts WHERE account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, accountId);

		ResultSet res = ps.executeQuery();
		res.next();
		
		return res.getBigDecimal("amount");
	}
	
	public synchronized boolean isValidAccount(User user, String name) throws SQLException {
		Set<Account> accounts = getAllAccountsByUserId((int)user.getUserId());
		
		for (Account acc : accounts) {
			if (acc.getName().equals(name)) {
				return false;
			}
		}
		
		return true;
	}

	public Account getAccountByAccountName(String accountName) throws SQLException {
		String query = "SELECT account_id, name, amount, user_id FROM finance_tracker.accounts WHERE accounts.name = ?";
		PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setString(1, accountName);
		
		ResultSet res = statement.executeQuery();
		res.next();
		
		long accountId = res.getLong("account_id");
		String name = res.getString("name");
		BigDecimal amount = res.getBigDecimal("amount");
		long userId = res.getLong("user_id");
		List<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsByAccountId(accountId);
		List<Budget> budgets = BudgetDAO.getInstance().getAllBudgetsByAccountId(accountId);
		List<PlannedPayment> plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPaymentsByAccountId(accountId);
		
		Account account = new Account(name, amount, userId, transactions, budgets, plannedPayments);
		account.setAccaountID(accountId);
		
		return account;
	}
	
	public String getAccountNameByAccountId(long accountId) throws SQLException {
		String query = "SELECT name FROM accounts WHERE accounts.account_id = ?";
		PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setLong(1, accountId);
		
		ResultSet res = statement.executeQuery();
		res.next();
		return res.getString("name");
	}
}