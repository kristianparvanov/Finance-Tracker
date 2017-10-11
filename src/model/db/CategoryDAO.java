package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Category;
import model.TransactionType;

public class CategoryDAO {

	private static CategoryDAO instance;
	//private static final ArrayList<Category> ALL_CATEGORIES = new ArrayList<>();

	private CategoryDAO(){}

	public static synchronized CategoryDAO getInstance() {
		if (instance == null) {
			instance = new CategoryDAO();
		}
		
		return instance;
	}
	
	
	public synchronized void insertCategory(Category c) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO categories (name, type) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, c.getName());
		ps.setString(2, c.getType().toString());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		c.setCategoryID(rs.getLong(1));
		
		//ALL_CATEGORIES.add(c);
	}
	
	public synchronized Category getCategoryByCategoryId(int categoryId) throws SQLException {
		String sql = "SELECT name, type FROM categories WHERE category_id = ?";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, categoryId);
		
		ResultSet rs = ps.executeQuery();
		
		String name = rs.getString("name");
		TransactionType type = TransactionType.valueOf(rs.getString("type"));
		
		Category category = null;
		category = new Category(name, type);
		
		return category;
	}

}
