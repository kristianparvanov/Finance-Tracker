package com.financeTracker.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.financeTracker.model.Transaction;

public class BudgetsHasTransactionsDAO {

	private static BudgetsHasTransactionsDAO instance;
	
	private BudgetsHasTransactionsDAO(){}
	
	public static synchronized BudgetsHasTransactionsDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new BudgetsHasTransactionsDAO();
		}
		
		return instance;
	}
	
	public synchronized void insertTransactionBudget(long budgetId, long transactionId) throws SQLException {
		String sql = "INSERT INTO budgets_has_transactions (budget_id, transaction_id) VALUES (?, ?);";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, budgetId);
		ps.setLong(2, transactionId);
		ps.executeUpdate();
	}
	
	public synchronized void deleteTransactionBudgetByTransactionId(long transactionId) throws SQLException {
		String sql = "DELETE FROM budgets_has_transactions WHERE transaction_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1,  transactionId);
		ps.executeUpdate();
	}
	
	public synchronized void deleteTransactionBudgetByBudgetId(long budgetId) throws SQLException {
		String sql = "DELETE FROM budgets_has_transactions WHERE budget_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1,  budgetId);
		ps.executeUpdate();
	}

	public Set<Transaction> getAllTransactionsByBudgetId(long budgetId) throws SQLException {
		String sql = "SELECT transaction_id FROM budgets_has_transactions WHERE budget_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, budgetId);
		
		ResultSet res = ps.executeQuery();
		
		Set<Transaction> transactions = new HashSet<>();
		
		while(res.next()) {
			transactions.add(TransactionDAO.getInstance().getTransactionByTransactionId(res.getLong("transaction_id")));
		}
		
		return transactions;
	}
}
