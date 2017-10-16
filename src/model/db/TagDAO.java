package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import model.Budget;
import model.PlannedPayment;
import model.Tag;
import model.Transaction;

public class TagDAO {
	private static TagDAO instance;
	private static final Connection CONNECTION = DBManager.getInstance().getConnection();
	
	private TagDAO() {}
	
	public synchronized static TagDAO getInstance() {
		if (instance == null) {
			instance = new TagDAO();
		}
		return instance;
	}
	
	public Tag getTagByTagId(long tagId) throws SQLException {
		String query = "SELECT name, user_id FROM finance_tracker.tags WHERE finance_tracker.tags.tag_id = ?";
		Tag tag = null;
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, tagId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			String name = result.getString("name");
			long userId = result.getLong("user_id");
			tag = new Tag(tagId, name, userId);
		}
		return tag;
	}
	
	public synchronized void insertTagToTags(Tag tag, long userId) throws SQLException {
		String query = "INSERT INTO finance_tracker.tags (name, user_id) VALUES (?, ?)";
		PreparedStatement statement = CONNECTION.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, tag.getName());
		statement.setLong(2, userId);
		statement.executeUpdate();
		
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		tag.setTagId(resultSet.getLong(1));
	}
	
	public HashSet<Tag> getTagsByTransactionId(long transactionId) throws SQLException {
		HashSet<Tag> tags = new HashSet<Tag>();
		String query = "SELECT tag_id, transaction_id FROM finance_tracker.transactions_has_tags WHERE transaction_id = ?";
		
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, transactionId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			long tagId = result.getLong("tag_id");
			Tag tag = getTagByTagId(tagId);
			tags.add(tag);
		}
		return tags;
	}
	
	public void insertTagToTransaction(Transaction t, Tag tag) throws SQLException {
		String query = "INSERT INTO finance_tracker.transactions_has_tags (transaction_id, tag_id) VALUES (?, ?)";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, t.getTransactionId());
		statement.setLong(2, tag.getTagId());
		statement.executeUpdate();
	}

	public HashSet<Tag> getTagsByBudgetId(long budgetId) throws SQLException {
		HashSet<Tag> tags = new HashSet<Tag>();
		String query = "SELECT budget_id, tag_id FROM finance_tracker.budgets_has_tags WHERE budget_id = ?";
		
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, budgetId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			long tagId = result.getLong("tag_id");
			Tag tag = getTagByTagId(tagId);
			tags.add(tag);
		}
		return tags;
	}
	
	public synchronized void insertTagToBudget(Budget b, Tag tag) throws SQLException {
		String query = "INSERT INTO finance_tracker.budgets_has_tags (budget_id, tag_id) VALUES (?, ?)";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, b.getBudgetId());
		statement.setLong(2, tag.getTagId());
		statement.executeUpdate();
	}

	public HashSet<Tag> getTagsByPlannedPaymentId(long plannedPaymentId) throws SQLException {
		HashSet<Tag> tags = new HashSet<Tag>();
		String query = "SELECT planned_payment_id, tag_id FROM finance_tracker.planned_payments_has_tags WHERE planned_payment_id = ?";
		
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, plannedPaymentId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			long tagId = result.getLong("tag_id");
			Tag tag = getTagByTagId(tagId);
			tags.add(tag);
		}
		return tags;
	}
	
	public void insertTagToPlannedPayment(PlannedPayment p, Tag tag) throws SQLException {
		String query = "INSERT INTO finance_tracker.planned_payments_has_tags (planned_payment_id, tag_id) VALUES (?, ?)";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, p.getPlannedPaymentId());
		statement.setLong(2, tag.getTagId());
		statement.executeUpdate();
		
	}

	public Set<Tag> getAllTagsByUserId(long userId) throws SQLException {
		String query = "SELECT tag_id, name FROM tags WHERE user_id = ?;";
		
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, userId);
		
		ResultSet result = statement.executeQuery();
		
		Set<Tag> tags = new HashSet<>();
		
		while(result.next()) {
			long tagId = result.getLong("tag_id");
			Tag tag = getTagByTagId(tagId);
			
			tags.add(tag);
		}
		
		return tags;
	}
}
