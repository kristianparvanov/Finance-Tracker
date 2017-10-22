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
import java.util.Set;

import model.Account;
import model.Budget;
import model.Category;
import model.Tag;
import model.Transaction;
import model.TransactionType;

public class BudgetDAO {
	private static BudgetDAO instance;
	private static final Connection CONNECTION = DBManager.getInstance().getConnection();
	
	private BudgetDAO() {
	}
	
	public synchronized static BudgetDAO getInstance() {
		if (instance == null) {
			instance = new BudgetDAO();
		}
		return instance;
	}
	
	public synchronized void getAllBudgets() throws SQLException {
		String query = "SELECT budget_id, name, initial_amount, amount, from_date, to_date, account_id, category_id FROM finance_tracker.budgets";
		
		PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(query);
		
		ResultSet result = statement.executeQuery();
		
		while(result.next()) {
			long budgetId = result.getLong("budget_id");
			String name = result.getString("name");
			BigDecimal initialAmount = result.getBigDecimal("initial_amount");
			BigDecimal amount = result.getBigDecimal("amount");
			LocalDateTime fromDate = result.getTimestamp("from_date").toLocalDateTime();
			LocalDateTime toDate = result.getTimestamp("to_date").toLocalDateTime();
			int accountId = result.getInt("account_id");
			int categoryId = result.getInt("category_id");
			HashSet<Tag> tags = TagDAO.getInstance().getTagsByBudgetId(budgetId);
			
			Set<Transaction> transactions = BudgetsHasTransactionsDAO.getInstance().getAllTransactionsByBudgetId(budgetId);
			Budget budget = new Budget(budgetId, name, initialAmount, amount, fromDate, toDate, accountId, categoryId, tags, transactions);
			
			System.out.println(budget);
		}
	}
	
	public synchronized List<Budget> getAllBudgetsByAccountId(long accountId) throws SQLException {
		String query = "SELECT budget_id, name, initial_amount, amount, from_date, to_date, category_id FROM finance_tracker.budgets WHERE account_id = ?";
		
		PreparedStatement statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setLong(1, accountId);
		
		List<Budget> budgets = new ArrayList<Budget>();

		ResultSet result = statement.executeQuery();
		
		while(result.next()) {
			long budgetId = result.getLong("budget_id");
			String name = result.getString("name");
			BigDecimal initialAmount = result.getBigDecimal("initial_amount");
			BigDecimal amount = result.getBigDecimal("amount");
			LocalDateTime fromDate = result.getTimestamp("from_date").toLocalDateTime();
			LocalDateTime toDate = result.getTimestamp("to_date").toLocalDateTime();
			long categoryId = result.getLong("category_id");
			HashSet<Tag> tags = TagDAO.getInstance().getTagsByBudgetId(budgetId);
			Set<Transaction> transactions = BudgetsHasTransactionsDAO.getInstance().getAllTransactionsByBudgetId(budgetId);
			
			Budget budget = new Budget(budgetId, name, initialAmount, amount, fromDate, toDate, accountId, categoryId, tags, transactions);
			
			budgets.add(budget);
		}
		
		return budgets;
	}
	
	public synchronized List<Budget> getAllBudgetsByCategoryId(long categoryId) throws SQLException {
		String sql = "SELECT budget_id, name, initial_amount, amount, from_date, to_date, account_id FROM finance_tracker.budgets WHERE category_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, categoryId);
		
		List<Budget> budgets = new ArrayList<Budget>();
		
		ResultSet res = ps.executeQuery();
		
		while(res.next()) {
			long budgetId = res.getLong("budget_id");
			String name = res.getString("name");
			BigDecimal initialAmount = res.getBigDecimal("initial_amount");
			BigDecimal amount = res.getBigDecimal("amount");
			LocalDateTime fromDate = res.getTimestamp("from_date").toLocalDateTime();
			LocalDateTime toDate = res.getTimestamp("to_date").toLocalDateTime();
			long accountId = res.getLong("account_id");
			HashSet<Tag> tags = TagDAO.getInstance().getTagsByBudgetId(budgetId);
			Set<Transaction> transactions = BudgetsHasTransactionsDAO.getInstance().getAllTransactionsByBudgetId(budgetId);
			
			Budget budget = new Budget(budgetId, name, initialAmount, amount, fromDate, toDate, accountId, categoryId, tags, transactions);
			
			budgets.add(budget);
		}
		
		return budgets;
	}
	
	public synchronized void insertBudget(Budget b) throws SQLException {		
		CONNECTION.setAutoCommit(false);
		
		try {
			String sql = "INSERT INTO budgets (name, amount, from_date, to_date, account_id, category_id) VALUES (?, ?, STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), ?, ?)";
			
			PreparedStatement ps = CONNECTION.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, b.getName());
			ps.setBigDecimal(2, b.getAmount());
			ps.setTimestamp(3, Timestamp.valueOf(b.getFromDate().withNano(0)));
			ps.setTimestamp(4, Timestamp.valueOf(b.getToDate().withNano(0)));
			ps.setLong(5, b.getAccountId());
			ps.setLong(6, b.getCategoryId());
			ps.executeUpdate();
			
			ResultSet resultSet = ps.getGeneratedKeys();
			resultSet.next();
			
			b.setBudgetId(resultSet.getLong(1));
			
			if (TransactionDAO.getInstance().existsTransaction(b.getFromDate(), b.getToDate(), b.getCategoryId(), b.getAccountId())) {
				Set<Transaction> transactions = TransactionDAO.getInstance().getAllTransactionsForBudget(b.getFromDate(), b.getToDate(), b.getCategoryId(), b.getAccountId());
			
				for (Transaction transaction : transactions) {
					BudgetsHasTransactionsDAO.getInstance().insertTransactionBudget(b.getBudgetId(), transaction.getTransactionId());
				}
				
				b.setTransactions(transactions);
			}
			
			for (Tag tag : b.getTags()) {
				TagDAO.getInstance().insertTagToTags(tag, tag.getUserId());
				TagDAO.getInstance().insertTagToBudget(b, tag);
			}
		} catch(SQLException e) {
			CONNECTION.rollback();
			
			throw new SQLException();
		} finally {
			CONNECTION.setAutoCommit(true);
		}
	}
	
	public synchronized void updateBudget(Budget b) throws SQLException {
		String sql = "UPDATE finance_tracker.budgets SET name = ?, initial_amount = ?, amount = ?, from_date = STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), to_date = STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), account_id = ?, category_id = ? WHERE budget_id = ?";
		
		PreparedStatement ps = CONNECTION.prepareStatement(sql);
		ps.setString(1, b.getName());
		ps.setBigDecimal(2, b.getInitialAmount());
		ps.setBigDecimal(3, b.getAmount());
		ps.setTimestamp(4, Timestamp.valueOf(b.getFromDate().withNano(0)));
		ps.setTimestamp(5, Timestamp.valueOf(b.getToDate().withNano(0)));
		ps.setLong(6, b.getAccountId());
		ps.setLong(7, b.getCategoryId());
		ps.setLong(8, b.getBudgetId());
		ps.executeUpdate();
	}
	
	public synchronized void deleteBudget(Budget b) throws SQLException {
		for (Transaction t : b.getTransactions()) {
			BudgetsHasTransactionsDAO.getInstance().deleteTransactionBudgetByTransactionId(t.getTransactionId());
		}
		
		String query = "DELETE FROM finance_tracker.budgets WHERE budget_id = ?";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, b.getBudgetId());
		statement.executeUpdate();
	}
	
	public synchronized boolean existsBudget(LocalDateTime date, long categoryId, long accountId) throws SQLException {
		String sql = "SELECT from_date, to_date, account_id, category_id FROM budgets WHERE category_id = ? AND account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, categoryId);
		ps.setLong(2, accountId);
		
		ResultSet res = ps.executeQuery();
		
		while (res.next()) {
			LocalDateTime fromDate = res.getTimestamp("from_date").toLocalDateTime();
			LocalDateTime toDate = res.getTimestamp("to_date").toLocalDateTime();
			
			if (isBetweenTwoDates(date, fromDate, toDate)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Set<Budget> getAllBudgetsByDateCategoryAndAccount(LocalDateTime date, long categoryId, long accountId) throws SQLException {
		String sql = "SELECT budget_id, name, initial_amount, amount, from_date, to_date, account_id, category_id FROM budgets WHERE category_id = ? AND account_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, categoryId);
		ps.setLong(2, categoryId);
		
		ResultSet res = ps.executeQuery();
		Set<Budget> budgets = new HashSet<>();
		
		while (res.next()) {
			long budgetId = res.getLong("budget_id");
			String name = res.getString("name");
			BigDecimal initialAmount = res.getBigDecimal("initial_amount");
			BigDecimal amount = res.getBigDecimal("amount");
			LocalDateTime fromDate = res.getTimestamp("from_date").toLocalDateTime();
			LocalDateTime toDate = res.getTimestamp("to_date").toLocalDateTime();
			Set<Tag> tags = TagDAO.getInstance().getTagsByBudgetId(budgetId);
			Set<Transaction> transactions = BudgetsHasTransactionsDAO.getInstance().getAllTransactionsByBudgetId(budgetId);
			
			Budget b = new Budget(budgetId, name, initialAmount, amount, fromDate, toDate, accountId, categoryId, tags, transactions);
			
			if (isBetweenTwoDates(date, fromDate, toDate)) {
				budgets.add(b);
			}
		}
		
		return budgets;
	}
	
	private boolean isBetweenTwoDates(LocalDateTime date, LocalDateTime from, LocalDateTime to) {
		return !date.isBefore(from) && !date.isAfter(to);
	}

	public Set<Budget> getAllBudgetsByUserId(long userId) throws SQLException {
		String sql = "SELECT b.budget_id, b.name, b.initial_amount, b.amount, b.from_date, b.to_date, b.account_id, b.category_id FROM budgets b JOIN accounts a ON a.account_id = b.account_id AND user_id = ?;";
		
		PreparedStatement ps = DBManager.getInstance().getConnection().prepareStatement(sql);
		ps.setLong(1, userId);
		
		ResultSet res = ps.executeQuery();
		Set<Budget> budgets = new HashSet<>();
		
		while(res.next()) {
			long budgetId = res.getLong("budget_id");
			String name = res.getString("name");
			BigDecimal initialAmount = res.getBigDecimal("initial_amount");
			BigDecimal amount = res.getBigDecimal("amount");
			LocalDateTime fromDate = res.getTimestamp("from_date").toLocalDateTime();
			LocalDateTime toDate = res.getTimestamp("to_date").toLocalDateTime();
			long accountId = res.getLong("account_id");
			long categoryId = res.getLong("category_id");
			Set<Tag> tags = TagDAO.getInstance().getTagsByBudgetId(budgetId);
			Set<Transaction> transactions = BudgetsHasTransactionsDAO.getInstance().getAllTransactionsByBudgetId(budgetId);
			
			Budget b = new Budget(budgetId, name, initialAmount, amount, fromDate, toDate, accountId, categoryId, tags, transactions);
			
			budgets.add(b);
		}
		
		return budgets;
	}
}
