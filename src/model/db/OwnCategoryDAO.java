package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Budget;
import model.OwnCategory;
import model.PlannedPayment;
import model.Transaction;
import model.TransactionType;
import model.User;

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
		PreparedStatement ps = con.prepareStatement("INSERT INTO own_categories (name, type) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, îc.getName());
		ps.setString(2, îc.getType().toString());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		îc.setOwnCategoryId(rs.getLong(1));
	}
	
	public synchronized Set<OwnCategory> getAllOwnCategoriesByUserId(int userId) throws SQLException {
		Set<OwnCategory> ownCategories = new HashSet<>();
		
		String sql = "SELECT own_category_id, name, type FROM accounts WHERE user_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, userId);
		
		ResultSet res = ps.executeQuery();
		
		while(res.next()) {
			int ownCategoryId = res.getInt("own_category_id");
			String name = res.getString("name");
			TransactionType type = TransactionType.valueOf(res.getString("type"));
			List<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsByOwnCategoryId(ownCategoryId);
			List<Budget> budgets = BudgetDAO.getInstance().getAllBudgetsByOwnCategoryId(ownCategoryId);
			List<PlannedPayment> plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPaymentsByOwnCategoryId(ownCategoryId);
			User user = UserDAO.getInstance().getUserByUserId(userId);
			
			OwnCategory oc = new OwnCategory(ownCategoryId, name, user, type, transactions, budgets, plannedPayments);
			
			ownCategories.add(oc);
		}
		
		return ownCategories;
	}
	
	
	public synchronized OwnCategory getOwnCategoryByOwnCategoryId(int ownCategoryId) throws SQLException {
		String sql = "SELECT name, type, user_id FROM own_categories WHERE own_category_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setInt(1, + ownCategoryId);
		
		ResultSet res = ps.executeQuery();
		
		String name = res.getString("name");
		TransactionType type = TransactionType.valueOf(res.getString("type"));
		int userId = res.getInt("user_id");
		List<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsByOwnCategoryId(ownCategoryId);
		List<Budget> budgets = BudgetDAO.getInstance().getAllBudgetsByOwnCategoryId(ownCategoryId);
		List<PlannedPayment> plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPaymentsByOwnCategoryId(ownCategoryId);
		User user = UserDAO.getInstance().getUserByUserId(userId);
		
		OwnCategory oc = new OwnCategory(ownCategoryId, name, user, type, transactions, budgets, plannedPayments);
		
		return oc;
	}

}