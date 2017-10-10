package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.codec.digest.DigestUtils;

import model.User;

public class UserDAO {

	private static UserDAO instance;
	
	private UserDAO(){}
	
	public static synchronized UserDAO getInstance() {
		if (instance == null) {
			instance = new UserDAO();
		}
		
		return instance;
	}
	
	public void insertUser(User u) throws SQLException {
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password, email, first_name, last_name) "
														+ "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		
		ps.setString(1, u.getUserName());
		ps.setString(2, DigestUtils.sha512Hex(u.getUserName()));
		ps.setString(3, u.getEmail());
		ps.setString(4, u.getFirstName());
		ps.setString(5, u.getLastName());
		ps.executeUpdate();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		
		u.setUserId(rs.getLong(1));
	}
	
	public User getUserByUserId(int userId) throws SQLException {
		String query = "SELECT user_id, username, password, email, first_name, last_name FROM finance_tracker.users WHERE finance_tracker.users.user_id = ?";
		
		PreparedStatement statement = null;
		User user = null;
		statement = DBManager.getInstance().getConnection().prepareStatement(query);
		statement.setInt(1, userId);
		ResultSet result = statement.executeQuery();
		String userName = result.getString("username");
		String password = result.getString("password");
		String email = result.getString("email");
		String firstName = result.getString("first_name");
		String lastName = result.getString("last_name");
		user = new User(userName, password, email, firstName, lastName);
		return user;
	}
}
