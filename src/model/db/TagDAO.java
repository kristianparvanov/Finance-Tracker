package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import model.Tag;

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
	
	public Tag getTagByTagId(int tagId) throws SQLException {
		String query = "SELECT name FROM finance_tracker.tags WHERE finance_tracker.tags.tag_id = ?";
		Tag tag = null;
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setInt(1, tagId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			String name = result.getString("name");
			tag = new Tag(tagId, name);
		}
		return tag;
	}
	
	public HashSet<Tag> getTagsByTransactionId(long transactionId) throws SQLException {
		HashSet<Tag> tags = new HashSet<Tag>();
		String query = "SELECT tag_id, transaction_id FROM finance_tracker.transactions_has_tags WHERE transaction_id = ?";
		
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, transactionId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			int tagId = result.getInt("tag_id");
			Tag tag = getTagByTagId(tagId);
			tags.add(tag);
		}
		return tags;
	}

	public HashSet<Tag> getTagsByBudgetId(long budgetId) throws SQLException {
		HashSet<Tag> tags = new HashSet<Tag>();
		String query = "SELECT budget_id, tag_id FROM finance_tracker.budgets_has_tags WHERE budget_id = ?";
		
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, budgetId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			int tagId = result.getInt("tag_id");
			Tag tag = getTagByTagId(tagId);
			tags.add(tag);
		}
		return tags;
	}

	public HashSet<Tag> getTagsByPlannedPaymentId(long plannedPaymentId) throws SQLException {
		HashSet<Tag> tags = new HashSet<Tag>();
		String query = "SELECT budget_id, tag_id FROM finance_tracker.planned_payments_has_tags WHERE planned_payment_id = ?";
		
		PreparedStatement statement = null;
		statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, plannedPaymentId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			int tagId = result.getInt("tag_id");
			Tag tag = getTagByTagId(tagId);
			tags.add(tag);
		}
		return tags;
	}
}
