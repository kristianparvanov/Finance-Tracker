package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Tag;
import model.Transaction;
import model.TransactionType;
import model.db.AccountDAO;
import model.db.TransactionDAO;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Transaction> trans = null;
		try {
			trans = TransactionDAO.getInstance().getAllTransactionsByAccountId(Integer.valueOf(request.getParameter("id")));
			
		} catch (SQLException e) {
			System.out.println("neshto katastrofalno se slu4i");
			e.printStackTrace();
		}
		for (Transaction transaction : trans) {
			response.getWriter().append("<h4>" + transaction.toString() + "</h4>");
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		//response.sendRedirect("result.html");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		String type = request.getParameter("type");
		String amount = request.getParameter("amount");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		String description = request.getParameter("description");
		
		HashSet<Tag> tagsSet = new HashSet<>();
		if (!tags.isEmpty()) {
			String[] tagNames = tags.split(" ");
			for (String tag : tagNames) {
				tagsSet.add(new Tag(tag, userId));
			}
		}
		
		Transaction t = new Transaction(TransactionType.valueOf(type), description, BigDecimal.valueOf(Double.valueOf(amount)), Long.parseLong(account), Long.parseLong(category), LocalDateTime.now(), tagsSet);
		try {
			Account acc = AccountDAO.getInstance().getAccountByAccountId(Long.parseLong(account));
			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
			BigDecimal oldValue = AccountDAO.getInstance().getAmountByAccountId((int)acc.getAccaountId());
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
		//request.setAttribute("id", Long.parseLong(account));
		//doGet(request, response);
	}

}
