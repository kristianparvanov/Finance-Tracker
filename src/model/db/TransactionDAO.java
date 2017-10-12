package model.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import model.Account;
import model.Category;
import model.OwnCategory;
import model.Tag;
import model.Transaction;
import model.TransactionType;

public class TransactionDAO {
	private static TransactionDAO instance;
	private static final HashMap<TransactionType, ArrayList<Transaction>> ALL_TRANSACTIONS = new HashMap<>();
	private static final Connection CONNECTION = DBManager.getInstance().getConnection();
	
	private TransactionDAO() {
		ALL_TRANSACTIONS.put(TransactionType.EXPENCE, new ArrayList<>());
		ALL_TRANSACTIONS.put(TransactionType.INCOME, new ArrayList<>());
	}
	
	public synchronized static TransactionDAO getInstance() {
		if (instance == null) {
			instance = new TransactionDAO();
		}
		return instance;
	}
	
	public synchronized void getAllTransactions() throws SQLException {
		if (!ALL_TRANSACTIONS.isEmpty()) {
			return;
		}
		
		String query = "SELECT transaction_id, type, date, amount, account_id, category_id, own_category_id FROM finance_tracker.transactions";
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			long transactionId = result.getInt("transaction_id");
			String type = result.getString("type");
			TransactionType transactionType = TransactionType.valueOf(type);
			LocalDateTime date = result.getTimestamp("date").toLocalDateTime();
			BigDecimal amount = result.getBigDecimal("amount");
			int accountId = result.getInt("account_id");
			Account account = AccountDAO.getInstance().getAccountByAccountId(accountId);
			int categoryId = result.getInt("category_id");
			Category category = CategoryDAO.getInstance().getCategoryByCategoryId(categoryId);
			int ownCategoryId = result.getInt("own_category_id");
			OwnCategory ownCategory = OwnCategoryDAO.getInstance().getOwnCategoryByOwnCategoryId(ownCategoryId);
			HashSet<Tag> tags = TagDAO.getInstance().getTagsByTransactionId(transactionId);
			Transaction t = new Transaction(transactionType, amount, account, category, ownCategory, date, tags);
			t.setTransactionId(transactionId);
			ALL_TRANSACTIONS.get(t.getType()).add(t);
		}
	}
	
//	public synchronized void getAllTransactions() throws SQLException {
//		if (!ALL_TRANSACTIONS.isEmpty()) {
//			return;
//		}
//		
//		String query = "SELECT transaction_id, type, date, amount, account_id, category_id, own_category_id FROM finance_tracker.transactions";
//		PreparedStatement statement = null;
//		statement = CONNECTION.prepareStatement(query);
//		
//		ResultSet result = statement.executeQuery();
//		while (result.next()) {
//			long transactionId = result.getInt("transaction_id");
//			String type = result.getString("type");
//			TransactionType transactionType = TransactionType.valueOf(type);
//			LocalDateTime date = result.getTimestamp("date").toLocalDateTime();
//			BigDecimal amount = result.getBigDecimal("amount");
//			int accountId = result.getInt("account_id");
//			Account account = AccountDAO.getInstance().getAccountByAccountId(accountId);
//			int categoryId = result.getInt("category_id");
//			Category category = CategoryDAO.getInstance().getCategoryByCategoryId(categoryId);
//			int ownCategoryId = result.getInt("own_category_id");
//			OwnCategory ownCategory = OwnCategoryDAO.getInstance().getOwnCategoryByOwnCategoryId(ownCategoryId);
//			HashSet<Tag> tags = TagDAO.getInstance().getTagsByTransactionId(transactionId);
//			Transaction t = new Transaction(transactionType, amount, account, category, ownCategory, date, tags);
//			t.setTransactionId(transactionId);
//			ALL_TRANSACTIONS.get(t.getType()).add(t);
//		}
//	}
//	
	public synchronized List<Transaction> getAllTransactionsByAccountId(int accountId) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		for (ArrayList<Transaction> transactionTypes : ALL_TRANSACTIONS.values()) {
			for (Transaction transaction : transactionTypes) {
				if (transaction.getAccount().getAccaountId() == accountId) {
					transactions.add(transaction);
				}
			}
		}
		return transactions;
	}

	public synchronized void insertTransaction(Transaction t) throws SQLException {
		String query = "INSERT INTO finance_tracker.transactions (type, date, amount, account_id, category_id, own_category_id) VALUES (?, STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s'), ?, ?, ?, ?)";
		PreparedStatement statement = CONNECTION.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, t.getType().toString());
		statement.setTimestamp(2, Timestamp.valueOf(t.getDate()));
		statement.setBigDecimal(3, t.getAmount());
		statement.setLong(4, t.getAccount().getAccaountId());
		statement.setLong(5, t.getCategory().getCategoryId());
		statement.setLong(6, t.getOwnCategory().getOwnCategoryId());
		statement.executeUpdate();
		
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		t.setTransactionId(resultSet.getLong(1));
		
		ALL_TRANSACTIONS.get(t.getType()).add(t);
	}
	
	public synchronized void updateTransaction(Transaction t) throws SQLException {
		String query = "UPDATE finance_tracker.transactions SET type = ?, date = STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s'), amount = ?, account_id = ?, category_id = ?, own_category_id = ?) WHERE transaction_id = ?";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setString(1, t.getType().toString());
		statement.setTimestamp(2, Timestamp.valueOf(t.getDate()));
		statement.setBigDecimal(3, t.getAmount());
		statement.setLong(4, t.getAccount().getAccaountId());
		statement.setLong(5, t.getCategory().getCategoryId());
		statement.setLong(6, t.getOwnCategory().getOwnCategoryId());
		statement.setLong(7, t.getTransactionId());
		statement.executeUpdate();
		
		ALL_TRANSACTIONS.get(t.getType()).add(t);
	}
	
	public synchronized void deleteTransaction(Transaction t) throws SQLException {
		String query = "DELETE FROM finance_tracker.transactions WHERE transaction_id = ?";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, t.getTransactionId());
		statement.executeUpdate();
		
		ALL_TRANSACTIONS.get(t.getType()).remove(t);
	}
	
	public synchronized void removeTransaction(Transaction t) {
		ALL_TRANSACTIONS.get(t.getType()).remove(t);
	}
}
