package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
import model.db.TagDAO;
import model.db.TransactionDAO;

@WebServlet("/editTransaction")
public class EditTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long transactionId = Long.valueOf(request.getParameter("transactionId"));
		try {
			Transaction t = TransactionDAO.getInstance().getTransactionByTransactionId(transactionId);
			String type = t.getType().toString();
			String description = t.getDescription();
			BigDecimal amount = t.getAmount();
			String accountName = AccountDAO.getInstance().getAccountNameByAccountId(t.getAccount());
			String category = CategoryDAO.getInstance().getCategoryNameByCategoryId(t.getCategory());
			LocalDateTime date = t.getDate();
			Set<Tag> tags = TagDAO.getInstance().getTagsByTransactionId(transactionId);
			
			Set<String> tagNames = new HashSet<String>();
			for (Tag tag : tags) {
				tagNames.add(tag.getName());
			}
			
			request.getSession().setAttribute("editTransactionType", type);
			request.getSession().setAttribute("editTransactionDescription", description);
			request.getSession().setAttribute("editTransactionAmount", amount);
			request.getSession().setAttribute("editTransactionAccount", accountName);
			request.getSession().setAttribute("editTransactionCategory", category);
			request.getSession().setAttribute("editTransactionDate", date);
			request.getSession().setAttribute("editTransactionTags", tagNames);
			request.getSession().setAttribute("transactionId", transactionId);
		} catch (SQLException e) {
			System.out.println("Could not extract transaction from database");
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("editTransaction.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String amount = request.getParameter("amount");
		String date = request.getParameter("date");
		String[] tags = request.getParameterValues("tags");
		String description = request.getParameter("description");
		long transactionId = (long) request.getSession().getAttribute("transactionId");
		
		String[] inputDate = date.split("/");
		int month = Integer.valueOf(inputDate[0]);
		int dayOfMonth = Integer.valueOf(inputDate[1]);
		int year = Integer.valueOf(inputDate[2]);
		
		LocalDateTime newDate = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
		
		User u = (User) request.getSession().getAttribute("user");
		
		HashSet<Tag> tagsSet = new HashSet<>();
		if (tags != null) {
			for (String tagName : tags) {
				tagsSet.add(new Tag(tagName, u.getUserId()));
			}
		}
		
		Account acc = null;
		try {
			acc = AccountDAO.getInstance().getAccountByAccountName(account);
			Category cat = CategoryDAO.getInstance().getCategoryByCategoryName(category);
			Transaction t = new Transaction(TransactionType.valueOf(type), description, BigDecimal.valueOf(Double.valueOf(amount)), acc.getAccountId(), cat.getCategoryId(), newDate, tagsSet);
			t.setTransactionId(transactionId);
			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
			BigDecimal oldValue = AccountDAO.getInstance().getAmountByAccountId((int)acc.getAccountId());
			if (type.equals("EXPENCE")) {
				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.subtract(newValue)));
			} else 
			if (type.equals("INCOME")) {
				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.add(newValue)));
			}
			TransactionDAO.getInstance().removeTransaction(transactionId);
			TransactionDAO.getInstance().updateTransaction(t);
		} catch (SQLException e) {
			System.out.println("neshto katastrofalno se slu4i");
			e.printStackTrace();
		}
		request.setAttribute("user", u);
		request.setAttribute("accountId", acc.getAccountId());
		
		//response.sendRedirect("transactions.jsp");
		request.getRequestDispatcher("transactions.jsp").forward(request, response);
	}
}
