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
import model.Budget;
import model.Category;
import model.OwnCategory;
import model.Tag;

public class BudgetDAO {
	private static BudgetDAO instance;
	private static final HashMap<String , Budget> ALL_BUDGETS = new HashMap<>();
	private static final Connection CONNECTION = DBManager.getInstance().getConnection();
	
	private BudgetDAO() {
		//getAllBudgets();??
	}
	
	public synchronized static BudgetDAO getInstance() {
		if (instance == null) {
			instance = new BudgetDAO();
		}
		return instance;
	}
	
	private synchronized void getAllBudgets() throws SQLException {
		if (!ALL_BUDGETS.isEmpty()) {
			return;
		}
		String sql = "SELECT budget_id, name, amount, from_date, to_date, account_id, category_id, own_category_id FROM finance_tracker.budgets";
		PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(sql);
		ResultSet result = statement.executeQuery();
		while(result.next()) {
			long budgetId = result.getLong("budget_id");
			String name = result.getString("name");
			BigDecimal amount = result.getBigDecimal("amount");
			LocalDateTime fromDate = result.getTimestamp("from_date").toLocalDateTime();
			LocalDateTime toDate = result.getTimestamp("to_date").toLocalDateTime();
			int accountId = result.getInt("account_id");
			Account account = AccountDAO.getInstance().getAccountByAccountId(accountId);
			int categoryId = result.getInt("category_id");
			Category category = CategoryDAO.getInstance().getCategoryByCategoryId(categoryId);
			int ownCategoryId = result.getInt("own_category_id");
			OwnCategory ownCategory = OwnCategoryDAO.getInstance().getOwnCategoryByOwnCategoryId(ownCategoryId);
			HashSet<Tag> tags = TagDAO.getInstance().getTagsByBudgetId(budgetId);
			Budget budget = new Budget(name, amount, fromDate, toDate, account, category, ownCategory, tags);
			budget.setBudgetId(budgetId);
			
			ALL_BUDGETS.put(name, budget);
		}
	}
	
	public synchronized List<Budget> getAllBudgetsByAccountId(int accountId) {
		List<Budget> budgets = new ArrayList<Budget>();
		for (Budget budget : ALL_BUDGETS.values()) {
			if (budget.getAccount().getAccaountId() == accountId) {
				budgets.add(budget);
			}
		}
		return budgets;
	}
	
	public synchronized List<Budget> getAllBudgetsByCategoryId(int categoryId) {
		List<Budget> budgets = new ArrayList<Budget>();
		for (Budget budget : ALL_BUDGETS.values()) {
			if (budget.getCategory().getCategoryId() == categoryId) {
				budgets.add(budget);
			}
		}
		return budgets;
	}
	
	public synchronized List<Budget> getAllBudgetsByOwnCategoryId(int ownCategoryId) {
		List<Budget> budgets = new ArrayList<Budget>();
		for (Budget budget : ALL_BUDGETS.values()) {
			if (budget.getOwnCategory().getOwnCategoryId() == ownCategoryId) {
				budgets.add(budget);
			}
		}
		return budgets;
	}
	
	public synchronized void insertBudget(Budget b) throws SQLException {
		String query = "INSERT INTO finance_tracker.budgets (name, amount, from_date, to_date, account_id, category_id, own_category_id) VALUES (?, ?, STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s'), STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s'), ?, ?, ?)";
		PreparedStatement statement = CONNECTION.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, b.getName());
		statement.setBigDecimal(2, b.getAmount());
		statement.setTimestamp(3, Timestamp.valueOf(b.getFromDate()));
		statement.setTimestamp(4, Timestamp.valueOf(b.getToDate()));
		statement.setLong(5, b.getAccount().getAccaountId());
		statement.setLong(6, b.getCategory().getCategoryId());
		statement.setLong(7, b.getOwnCategory().getOwnCategoryId());
		statement.executeUpdate();
		
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		b.setBudgetId(resultSet.getLong(1));
		
		ALL_BUDGETS.put(b.getName(), b);
	}
	
	public synchronized void updateBudget(Budget b) throws SQLException {
		String query = "UPDATE finance_tracker.budgets SET name = ?, amount = ?, from_date = STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s'), to_date = STR_TO_DATE('?', '%Y-%m-%d %H:%i:%s'), account_id = ?, category_id = ?, own_category_id = ?) WHERE budget_id = ?";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setString(1, b.getName());
		statement.setBigDecimal(2, b.getAmount());
		statement.setTimestamp(3, Timestamp.valueOf(b.getFromDate()));
		statement.setTimestamp(4, Timestamp.valueOf(b.getToDate()));
		statement.setLong(4, b.getAccount().getAccaountId());
		statement.setLong(5, b.getCategory().getCategoryId());
		statement.setLong(6, b.getOwnCategory().getOwnCategoryId());
		statement.setLong(7, b.getBudgetId());
		statement.executeUpdate();
		
		ALL_BUDGETS.put(b.getName(), b);
	}
	
	public synchronized void deleteBudget(Budget b) throws SQLException {
		String query = "DELETE FROM finance_tracker.budgets WHERE budget_id = ?";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, b.getBudgetId());
		statement.executeUpdate();
		
		ALL_BUDGETS.remove(b.getName(), b);
	}
	
	public synchronized void removeBudget(Budget b) {
		ALL_BUDGETS.remove(b.getName(), b);
	}
}
