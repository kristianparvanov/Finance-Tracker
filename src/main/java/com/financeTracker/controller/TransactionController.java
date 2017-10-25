package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
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
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.CategoryDAO;
import com.financeTracker.model.db.TagDAO;
import com.financeTracker.model.db.TransactionDAO;

@Controller
public class TransactionController {
	
	@RequestMapping(value="/transaction/account/{accountId}", method=RequestMethod.GET)
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
}
