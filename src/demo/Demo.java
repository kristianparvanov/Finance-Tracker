package demo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.Account;
import model.Budget;
import model.Category;
import model.OwnCategory;
import model.PlannedPayment;
import model.Tag;
import model.Transaction;
import model.TransactionType;
import model.User;
import model.db.AccountDAO;
import model.db.BudgetDAO;
import model.db.CategoryDAO;
import model.db.DBManager;
import model.db.PlannedPaymentDAO;
import model.db.TransactionDAO;
import model.db.UserDAO;

public class Demo {
	public static void main(String[] args) {
		Connection connection = null;
		connection = DBManager.getInstance().getConnection();
		System.out.println("Working");
	
		User u1 = new User("blago", "123", "wad", "bl", "n");
		u1.setUserId(1);
		
		Account a1 = new Account("bank", BigDecimal.valueOf(5000), (int)u1.getUserId());
		a1.setAccaountID(1);
		Account a2 = new Account("cred", BigDecimal.valueOf(421414.0000), (int)u1.getUserId());
		a1.setAccaountID(2);
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		List<Budget> budgets = new ArrayList<Budget>();
		List<PlannedPayment> plannedPayments = new ArrayList<PlannedPayment>();
		
		HashSet<Tag> tags = new HashSet<Tag>();
		Tag kolata = new Tag("kolata");
		//kolata.setTagId(7);
		tags.add(kolata);
		Tag brat = new Tag("brat");
		//kolata.setTagId(8);
		tags.add(brat);
		
		Category c1 = new Category("cafe", TransactionType.EXPENCE, transactions, budgets, plannedPayments);
		c1.setCategoryID(1);
		OwnCategory oc1 = new OwnCategory("sth", TransactionType.EXPENCE, u1.getUserId(), transactions, budgets, plannedPayments);
		oc1.setOwnCategoryId(2);
		
		Transaction t1 = new Transaction(TransactionType.INCOME, BigDecimal.valueOf(7000), a1.getAccaountId(), c1.getCategoryId(), oc1.getOwnCategoryId(), LocalDateTime.now(), tags);
		PlannedPayment p1 = new PlannedPayment("CARS", TransactionType.EXPENCE, LocalDateTime.of(2017, 11, 12, 15, 37, 25), BigDecimal.valueOf(250), "nosa4a", a1.getAccaountId(), c1.getCategoryId(), oc1.getOwnCategoryId(), tags);
		Budget b1 = new Budget("gazovata", BigDecimal.valueOf(200), LocalDateTime.of(2017, 10, 12, 15, 37, 25), LocalDateTime.of(2017, 10, 14, 15, 37, 25), a1.getAccaountId(), c1.getCategoryId(), oc1.getOwnCategoryId(), tags);
		
		try {
			//UserDAO.getInstance().insertUser(new User("Kristian", "123", "kris@40", "Kristian", "purvanov"));
			//!!System.out.println(UserDAO.getInstance().isValidLogin("Kristian", "123"));
			
			//transactions
			//TransactionDAO.getInstance();
			TransactionDAO.getInstance().insertTransaction(t1);
			//TransactionDAO.getInstance().updateTransaction(t1);
//			Transaction t6 = new Transaction(TransactionType.INCOME, BigDecimal.valueOf(2500), a1.getAccaountId(), c1.getCategoryId(), oc1.getOwnCategoryId(), LocalDateTime.now(), tags);
//			t6.setTransactionId(6);
//			TransactionDAO.getInstance().deleteTransaction(t6);
			
			//planned
			//PlannedPaymentDAO.getInstance().getAllPlannedPayments();
			PlannedPaymentDAO.getInstance().insertPlannedPayment(p1);
			
			//budget
			//BudgetDAO.getInstance().getAllBudgets();
			BudgetDAO.getInstance().insertBudget(b1);
			
			//account
			//AccountDAO.getInstance().insertAccount(new Account("Debit card", BigDecimal.valueOf(25000), u1));
			
			//category
			//CategoryDAO.getInstance().insertCategory(new Category("kurvi", TransactionType.EXPENCE, transactions, budgets, plannedPayments));
			
			
			
//			System.out.println(AccountDAO.getInstance().getAllAccountsByUserId((int)u1.getUserId()));
//			System.out.println(AccountDAO.getInstance().getAccountByAccountId((int)u1.getUserId()));
//			AccountDAO.getInstance().makeTransferToOtherAccount(a2, a1, BigDecimal.valueOf(414));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
