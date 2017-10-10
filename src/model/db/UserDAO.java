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
}
