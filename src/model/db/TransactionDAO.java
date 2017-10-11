package model.db;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Category;
import model.OwnCategory;
import model.Tag;
import model.Transaction;
import model.TransactionType;

public class TransactionDAO {
	private static TransactionDAO instance;
	
	public synchronized static TransactionDAO getInstance() {
		if (instance == null) {
			instance = new TransactionDAO();
		}
		return instance;
	}
	
	public List<Transaction> getAllTransactions() throws SQLException {
		List<Transaction> transactions = new ArrayList<Transaction>();
		String query = "SELECT transaction_id, type, date, amount, account_id, category_id, own_category_id FROM finance_tracker.transactions";
		
		PreparedStatement statement = null;
		statement = DBManager.getInstance().getConnection().prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			int transactionId = result.getInt("transaction_id");
			String type = result.getString("type");
			TransactionType transactionType = 
			LocalDateTime date = result.getTimestamp("date").toLocalDateTime();
			BigDecimal amount = result.getBigDecimal("amount");
			int accountId = result.getInt("account_id");
			Account account = AccountDAO.getInstance().getAccountByAccountId(accountId);
			int categoryId = result.getInt("category_id");
			Category category = CategoryDAO.getInstance().getCategoryByCategoryId(categoryId);
			int ownCategoryId = result.getInt("own_category_id");
			OwnCategory ownCategory = OwnCategoryDAO.getInstance().getOwnCategoryByOwnCategoryId(ownCategoryId);
			List<Tag> tags = TagDAO.getInstance().getTagsByTransactionId(transactionId);
			Transaction t = new Transaction(transactionId, type, amount, account, category, ownCategory, date, tags);
			transactions.add(t);
		}
		return transactions;
	}
}
