package model.db;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Transaction;

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
		String query = "SELECT transaction_id, type, date, amount, account, category, own_category FROM finance_tracker.transactions";
		
		PreparedStatement statement = null;
		statement = DBManager.getInstance().getConnection().prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			int transactionId = result.getInt("transaction_id");
			String type = result.getString("type");
			LocalDateTime date = result.getTimestamp("date").toLocalDateTime();
			BigDecimal amount = result.getBigDecimal("amount");
			String account = result.getString("account");
			String category = result.getString("category");
			String ownCategory = result.getString("own_category");
			//Transaction t = new Transaction(transactionId, type, amount, account, category, ownCategory, date, tags);
			//transactions.add(t);
		}
		return transactions;
	}
}
