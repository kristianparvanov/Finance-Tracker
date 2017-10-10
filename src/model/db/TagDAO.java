package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Tag;

public class TagDAO {
	private static TagDAO instance;
	
	public synchronized static TagDAO getInstance() {
		if (instance == null) {
			instance = new TagDAO();
		}
		return instance;
	}
	
	public Tag getTagByTagId(int tagId) throws SQLException {
		String query = "SELECT tag_id, name FROM finance_tracker.tags WHERE finance_tracker.tags.tag_id = ?";
		
		PreparedStatement statement = null;
		Tag tag = null;
		statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setInt(1, tagId);
		ResultSet result = statement.executeQuery();
		int id = result.getInt("tag_id");
		String name = result.getString("name");
		tag = new Tag(id, name);
		return tag;
	}
	
	public List<Tag> getTagsByTransactionId(int transactionId) throws SQLException {
		List<Tag> tags = new ArrayList<Tag>();
		String query = "SELECT tag_id, transaction_id FROM finance_tracker.transactions_has_tags WHERE transaction_id = ?";
		
		PreparedStatement statement = null;
		statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setInt(1, transactionId);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			int tagId = result.getInt("tag_id");
			Tag tag = getTagByTagId(tagId);
			tags.add(tag);
		}
		return tags;
	}
}
