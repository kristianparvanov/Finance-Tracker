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
import model.Category;
import model.OwnCategory;
import model.PlannedPayment;
import model.Tag;
import model.TransactionType;

public class PlannedPaymentDAO {
	private static PlannedPaymentDAO instance;
	private static final HashMap<String , PlannedPayment> ALL_PLANNED_PAYMENTS = new HashMap<>();
	private static final Connection CONNECTION = DBManager.getInstance().getConnection();
	
	private PlannedPaymentDAO() {
		//getAllPlannedPayments()??
	}
	
	public synchronized static PlannedPaymentDAO getInstance() {
		if (instance == null) {
			instance = new PlannedPaymentDAO();
		}
		return instance;
	}
	
	public synchronized void getAllPlannedPayments() throws SQLException {
		if (!ALL_PLANNED_PAYMENTS.isEmpty()) {
			return;
		}
		String query = "SELECT planned_payment_id, name, type, from_date, amount, description, account_id, category_id, own_category_id FROM finance_tracker.planned_payments";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
			long plannedPaymentId = result.getLong("planned_payment_id");
			String name = result.getString("name");
			String type = result.getString("type");
			TransactionType paymentType = TransactionType.valueOf(type);
			LocalDateTime fromDate = result.getTimestamp("from_date").toLocalDateTime();
			BigDecimal amount = result.getBigDecimal("amount");
			String description = result.getString("description");
			int accountId = result.getInt("account_id");
			//Account account = AccountDAO.getInstance().getAccountByAccountId(accountId);
			int categoryId = result.getInt("category_id");
			//Category category = CategoryDAO.getInstance().getCategoryByCategoryId(categoryId);
			int ownCategoryId = result.getInt("own_category_id");
			//OwnCategory ownCategory = OwnCategoryDAO.getInstance().getOwnCategoryByOwnCategoryId(ownCategoryId);
			HashSet<Tag> tags = TagDAO.getInstance().getTagsByPlannedPaymentId(plannedPaymentId);
			PlannedPayment payment = new PlannedPayment(name, paymentType, fromDate, amount, description, accountId, categoryId, ownCategoryId, tags);
			payment.setPlannedPaymentId(plannedPaymentId);
			ALL_PLANNED_PAYMENTS.put(name, payment);
			
			System.out.println(payment);
		}
	}
	
	public synchronized List<PlannedPayment> getAllPlannedPaymentsByAccountId(int accountId) {
		List<PlannedPayment> payments = new ArrayList<PlannedPayment>();
		for (PlannedPayment payment : ALL_PLANNED_PAYMENTS.values()) {
			if (payment.getAccount() == accountId) {
				payments.add(payment);
			}
		}
		return payments;
	}
	
	public synchronized List<PlannedPayment> getAllPlannedPaymentsByCategoryId(int categoryId) {
		List<PlannedPayment> payments = new ArrayList<PlannedPayment>();
		for (PlannedPayment payment : ALL_PLANNED_PAYMENTS.values()) {
			if (payment.getCategory() == categoryId) {
				payments.add(payment);
			}
		}
		return payments;
	}
	
	public synchronized List<PlannedPayment> getAllPlannedPaymentsByOwnCategoryId(int ownCategoryId) {
		List<PlannedPayment> payments = new ArrayList<PlannedPayment>();
		for (PlannedPayment payment : ALL_PLANNED_PAYMENTS.values()) {
			if (payment.getOwnCategory() == ownCategoryId) {
				payments.add(payment);
			}
		}
		return payments;
	}
	
	public synchronized void insertPlannedPayment(PlannedPayment p) throws SQLException {
		String query = "INSERT INTO finance_tracker.planned_payments (name, type, from_date, amount, description, account_id, category_id, own_category_id) VALUES (?, ?, STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), ?, ?, ?, ?, ?)";
		PreparedStatement statement = CONNECTION.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, p.getName());
		statement.setString(2, p.getPaymentType().toString());
		statement.setTimestamp(3, Timestamp.valueOf(p.getFromDate().withNano(0)));
		statement.setBigDecimal(4, p.getAmount());
		statement.setString(5, p.getDescription());
		statement.setLong(6, p.getAccount());
		statement.setLong(7, p.getCategory());
		statement.setLong(8, p.getOwnCategory());
		statement.executeUpdate();
		
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		p.setPlannedPaymentId(resultSet.getLong(1));
		
		ALL_PLANNED_PAYMENTS.put(p.getName(), p);
	}
	
	public synchronized void updatePlannedPayment(PlannedPayment p) throws SQLException {
		String query = "UPDATE finance_tracker.planned_payments SET name = ?, type = ?, from_date = STR_TO_DATE(?, '%Y-%m-%d %H:%i:%s'), amount = ?, description = ?, account_id = ?, category_id = ?, own_category_id = ? WHERE planned_payment_id = ?";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setString(1, p.getName());
		statement.setString(2, p.getPaymentType().toString());
		statement.setTimestamp(3, Timestamp.valueOf(p.getFromDate().withNano(0)));
		statement.setBigDecimal(4, p.getAmount());
		statement.setString(5, p.getDescription());
		statement.setLong(6, p.getAccount());
		statement.setLong(7, p.getCategory());
		statement.setLong(8, p.getOwnCategory());
		statement.setLong(9, p.getPlannedPaymentId());
		statement.executeUpdate();
		
		ALL_PLANNED_PAYMENTS.put(p.getName(), p);
	}
	
	public synchronized void deletePlannedPayment(PlannedPayment p) throws SQLException {
		String query = "DELETE FROM finance_tracker.planned_payments WHERE planned_payment_id = ?";
		PreparedStatement statement = CONNECTION.prepareStatement(query);
		statement.setLong(1, p.getPlannedPaymentId());
		statement.executeUpdate();
		
		ALL_PLANNED_PAYMENTS.remove(p.getName());
	}
	
	public synchronized void removeTransaction(PlannedPayment p) {
		ALL_PLANNED_PAYMENTS.remove(p.getName());
	}
}
