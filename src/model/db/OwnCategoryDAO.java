package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Category;
import model.OwnCategory;
import model.TransactionType;

public class OwnCategoryDAO {

	private static OwnCategoryDAO instance;
	//private static final ArrayList<OwnCategory> ALL_OWN_CATEGORIES = new ArrayList<>();

	private OwnCategoryDAO(){}

	public static synchronized OwnCategoryDAO getInstance() {
		if (instance == null) {
			instance = new OwnCategoryDAO();
		}
		
		return instance;
	}
	
	
	public synchronized void insertCategory(OwnCategory îc) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO own_categories (name, type) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, îc.getName());
		ps.setString(2, îc.getType().toString());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		îc.setOwnCategoryId(rs.getLong(1));
		
		//ALL_OWN_CATEGORIES.add(c);
	}
	
	public synchronized OwnCategory getOwnCategoryByOwnCategoryId(int ownCategoryId) throws SQLException {
		String sql = "SELECT name, type FROM own_categories WHERE own_category_id = ?";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, + ownCategoryId);
		
		ResultSet rs = ps.executeQuery();
		
		String name = rs.getString("name");
		TransactionType type = TransactionType.valueOf(rs.getString("type"));
		
		OwnCategory ownCategory = null;
		ownCategory = new OwnCategory(name, type);
		
		return ownCategory;
	}

}