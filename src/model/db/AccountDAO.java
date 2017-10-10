package model.db;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.User;

public class AccountDAO {
	private static AccountDAO instance;
	
	public synchronized static AccountDAO getInstance() {
		if (instance == null) {
			instance = new AccountDAO();
		}
		return instance;
	}
	
	public Account getAccountByAccountId(int accountId) throws SQLException {
		String query = "SELECT account_id, name, amount, user_id FROM finance_tracker.accounts WHERE finance_tracker.accounts.account_id = ?";
		
		PreparedStatement statement = null;
		Account account = null;
		statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setInt(1, accountId);
		ResultSet result = statement.executeQuery();
		String name = result.getString("name");
		BigDecimal amount = result.getBigDecimal("amount");
		int userId = result.getInt("user_id");
		User user = UserDAO.getInstance().getUserByUserId(userId);
		account = new Account(name, amount, user);
		return account;
	}
}
