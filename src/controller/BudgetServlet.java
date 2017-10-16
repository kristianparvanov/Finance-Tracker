package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Budget;
import model.Tag;
import model.db.BudgetDAO;

@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Budget> budgets = null;
//		Long accountId = (Long) request.getAttribute("id");
		try {
			budgets = BudgetDAO.getInstance().getAllBudgetsByAccountId(Integer.valueOf(request.getParameter("id")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Budget budget : budgets) {
			response.getWriter().append("<h4>" + budget.toString() + "</h4>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String amount = request.getParameter("amount");
		String from = request.getParameter("from");
		String to = request.getParameter("to");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		
		Set<Tag> tagsSet = new HashSet<>();
		if (!tags.isEmpty()) {
			String[] tagNames = tags.split(" ");
			for (String tag : tagNames) {
				tagsSet.add(new Tag(tag));
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
