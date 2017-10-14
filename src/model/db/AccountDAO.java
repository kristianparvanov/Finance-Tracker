package model.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Account;
import model.Budget;
import model.PlannedPayment;
import model.Transaction;
import model.User;

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
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		acc.setAccaountID(rs.getLong(1));
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
	
	public synchronized Set<Account> getAllAccountsByUserId(int userId) throws SQLException {
		Set<Account> accounts = new HashSet<>();
		
		String sql = "SELECT account_id, name, amount FROM accounts WHERE user_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, userId);
		
		ResultSet res = ps.executeQuery();
		
		while(res.next()) {
			int accountId = res.getInt("account_id");
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
		String sql = "SELECT account_id, name, amount, user_id FROM accounts WHERE accounts.account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, accountId);
		
		ResultSet res = ps.executeQuery();
		res.next();
		
		Long accaountID = res.getLong("account_id");
		String name = res.getString("name");
		BigDecimal amount = res.getBigDecimal("amount");
		int userId = res.getInt("user_id");
		List<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsByAccountId(accountId);
		List<Budget> budgets = BudgetDAO.getInstance().getAllBudgetsByAccountId(accountId);
		List<PlannedPayment> plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPaymentsByAccountId(accountId);
		
		Account acc = new Account(name, amount, userId, transactions, budgets, plannedPayments);
		acc.setAccaountID(accaountID);
		return acc;
	}
	
	public synchronized void updateAccountAmount(Account acc, BigDecimal newAmount) throws SQLException {
		String sql = "UPDATE accounts SET amount = ? WHERE account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setBigDecimal(1, newAmount);
		ps.setLong(2, acc.getAccaountId());
		ps.executeUpdate();
	}
	
	public synchronized void makeTransferToOtherAccount(Account currentAcc, Account otherAcc, BigDecimal amount) throws SQLException {
		updateAccountAmount(currentAcc, currentAcc.getAmount().subtract(amount));
		updateAccountAmount(otherAcc, otherAcc.getAmount().add(amount));
	}
	
	public synchronized BigDecimal getAmountByAccountId(int accountId) throws SQLException {
		String sql = "SELECT amount FROM accounts WHERE account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, accountId);

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
}
