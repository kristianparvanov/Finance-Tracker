package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Account;
import com.financeTracker.model.Category;
import com.financeTracker.model.Tag;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.CategoryDAO;
import com.financeTracker.model.db.TagDAO;
import com.financeTracker.model.db.TransactionDAO;

@Controller
public class TransactionController {
	
	@RequestMapping(value="/account/{accountId}", method=RequestMethod.GET)
	public String getAllTransactions(HttpServletRequest request, HttpSession session, @PathVariable("accountId") Long accountId) {
		User user = (User) request.getSession().getAttribute("user");
		List<Transaction> transactions = null;
		BigDecimal accountBalance = null;
		String accountName = null;
		Set<Category> allCategories = new HashSet<Category>();
		Set<Category> ownCategories = new HashSet<Category>();
		Set<Category> categories = new HashSet<Category>();
		Set<Account> accounts = new HashSet<Account>();
		Set<Tag> tags = new HashSet<Tag>();
		try {
			//long accountId = Long.valueOf(request.getParameter("accountId"));
			request.getSession().setAttribute("accountId", accountId);
			transactions = TransactionDAO.getInstance().getAllTransactionsByAccountId(accountId);
			accountBalance = AccountDAO.getInstance().getAmountByAccountId(accountId);
			accountName = AccountDAO.getInstance().getAccountNameByAccountId(accountId);
			categories = CategoryDAO.getInstance().getAllCategoriesByUserId();
			ownCategories = CategoryDAO.getInstance().getAllCategoriesByUserId(user.getUserId());
			accounts = AccountDAO.getInstance().getAllAccountsByUserId(user.getUserId());
			allCategories.addAll(categories);
			allCategories.addAll(ownCategories);
			tags = TagDAO.getInstance().getAllTagsByUserId(user.getUserId());
		} catch (SQLException e) {
			System.out.println("neshto katastrofalno se slu4i");
			e.printStackTrace();
		}
		
		String balance = NumberFormat.getCurrencyInstance().format(accountBalance);
		request.getSession().setAttribute("categories", allCategories);
		request.getSession().setAttribute("accounts", accounts);
		request.getSession().setAttribute("accountName", accountName);
		request.getSession().setAttribute("balance", balance);
		request.getSession().setAttribute("tags", tags);
		request.getSession().setAttribute("transactions", transactions);
		
		return "transactions";
	}
	
	@RequestMapping(value="/transaction/account/addTransaction", method=RequestMethod.POST)
	public String postAddTransaction(HttpServletRequest request, HttpSession session) {
		String type = request.getParameter("type");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String amount = request.getParameter("amount");
		String[] tags = request.getParameterValues("tags");
		String description = request.getParameter("description");
		
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
		
		//response.sendRedirect("transaction");
		//transaction?accountId=${sessionScope.accountId}
		return "transactions";
		//request.getRequestDispatcher("transaction?accountId=" + acc.getAccountId()).forward(request, response);
	}
	
	@RequestMapping(value="/transaction/account/addTransaction", method=RequestMethod.GET)
	public String getAddTransaction(HttpServletRequest request, HttpSession session) {
		return "addTransaction";
		
	}
}
