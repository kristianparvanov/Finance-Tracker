package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Budget;
import model.Tag;
import model.Transaction;
import model.User;
import model.db.AccountDAO;
import model.db.BudgetDAO;

@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User u = (User) request.getSession().getAttribute("user");
		
		Set<Budget> budgets = null;
		BigDecimal percent = new BigDecimal(0.0);
		BigDecimal transactionsAmount = new BigDecimal(0.0);
		
		HashMap<Budget, BigDecimal> map = new HashMap<>();
		
		
		try {
			budgets = BudgetDAO.getInstance().getAllBudgetsByUserId(u.getUserId());
			
			for (Budget budget : budgets) {
				transactionsAmount = new BigDecimal(0.0);
				
				for (Transaction transaction : budget.getTransactions()) {
					transactionsAmount = transactionsAmount.add(transaction.getAmount());
				}
				transactionsAmount = BigDecimal.valueOf(45);
				percent = transactionsAmount.divide(budget.getAmount()).multiply(BigDecimal.valueOf(100));
				
				map.put(budget, percent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		request.getSession().setAttribute("budgets", map);
		
		request.getRequestDispatcher("budgets.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String amount = request.getParameter("amount");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		
		User u = (User) request.getSession().getAttribute("user");
		Set<Tag> tagsSet = new HashSet<>();
		if (!tags.isEmpty()) {
			String[] tagNames = tags.split(" ");
			for (String tag : tagNames) {
				tagsSet.add(new Tag(tag, u.getUserId()));
			}
		}
		
		//TODO TRANSACTIONS??????
		Budget b = new Budget(name, BigDecimal.valueOf(Double.valueOf(amount)), LocalDateTime.parse(from), LocalDateTime.parse(to), Long.parseLong(account), Long.parseLong(category), tagsSet, null);
		try {
			BudgetDAO.getInstance().insertBudget(b);
		} catch (SQLException e) {
			System.out.println("neshto katastrofalno se slu4i");
			e.printStackTrace();
		}
		//request.setAttribute("id", Long.parseLong(account));
		//doGet(request, response);
	}
}
