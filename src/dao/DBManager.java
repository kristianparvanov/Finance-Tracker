package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/finance_tracker";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root"; 
    private Connection conn;

    private DBManager() {
        try {
            Class.forName(DBManager.JDBC_DRIVER);
            this.conn = DriverManager.getConnection(DBManager.DB_URL, DBManager.USERNAME, DBManager.PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load database driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Unable to connect to database: " + e.getMessage());
        }
    }

    public static DBManager getInstance() {
        if (DBManager.instance == null) {
            synchronized (DBManager.class) {
                if (DBManager.instance == null) {
                    DBManager.instance = new DBManager();
                }
            }
        }
        return DBManager.instance;
    }

    public Connection getConnection() {
        return this.conn;	
    }

    public void closeConnection() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}