package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Category;
import model.Tag;
import model.Transaction;
import model.TransactionType;
import model.User;
import model.db.AccountDAO;
import model.db.CategoryDAO;
import model.db.TransactionDAO;

@WebServlet("/addTransaction")
public class AddTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String amount = request.getParameter("amount");
		String[] tags = request.getParameterValues("tags");
		String description = request.getParameter("description");
		
		User u = (User) request.getSession().getAttribute("user");
		
		HashSet<Tag> tagsSet = new HashSet<>();
		for (String tagName : tags) {
			tagsSet.add(new Tag(tagName, u.getUserId()));
		}
		Account acc = null;
		try {
			acc = AccountDAO.getInstance().getAccountByAccountName(account);
			Category cat = CategoryDAO.getInstance().getCategoryByCategoryName(category);
			Transaction t = new Transaction(TransactionType.valueOf(type), description, BigDecimal.valueOf(Double.valueOf(amount)), acc.getAccountId(), cat.getCategoryId(), LocalDateTime.now(), tagsSet);
			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
			BigDecimal oldValue = AccountDAO.getInstance().getAmountByAccountId((int)acc.getAccountId());
			if (type.equals("EXPENCE")) {
				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.subtract(newValue)));
			} else 
			if (type.equals("INCOME")) {
				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.add(newValue)));
			}
			TransactionDAO.getInstance().insertTransaction(t);
		} catch (SQLException e) {
			System.out.println("neshto katastrofalno se slu4i");
			e.printStackTrace();
		}
		request.setAttribute("user", u);
		request.setAttribute("accountId", acc.getAccountId());
//		response.sendRedirect("transaction");
		
		request.getRequestDispatcher("transactions.jsp").forward(request, response);
	}
}
