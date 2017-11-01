package com.financeTracker.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Account;
import com.financeTracker.model.Category;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.CategoryDAO;
import com.financeTracker.model.db.TransactionDAO;

@Controller
public class ReportController {
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private TransactionDAO transactionDao;
	
	@Autowired
	private AccountDAO accountDao;
	
	@RequestMapping(value="/reports", method=RequestMethod.GET)
	public String getAllReports(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		
		Set<Transaction> allTransactions = new TreeSet<>((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
		
		Set<Account> allAccounts = new TreeSet<>((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
		
		Set<Category> categories = new TreeSet<>((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
		try {
			categories.addAll(categoryDao.getAllCategoriesByUserId(user.getUserId()));
			categories.addAll(categoryDao.getAllCategoriesByUserId());
		} catch (SQLException e) {
			System.out.println("mai nemame kategoriiki?");
		}
		
		for (Account acc : user.getAccounts()) {
			allAccounts.add(acc);
			
			allTransactions.addAll(acc.getTransactions());
		}
		
		model.addAttribute("categories", categories);
		model.addAttribute("allAccounts", allAccounts);
		model.addAttribute("allTransactions", allTransactions);
		
		return "reports";
	}
	
	@RequestMapping(value = "/reports/filtered", method = RequestMethod.GET)
	public String filterTransactions(HttpServletRequest request, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		String categoryName = request.getParameter("category");
		String type = request.getParameter("type");
		String accName = request.getParameter("account");
		String date = request.getParameter("date");
		
		String[] inputDate = date.split("/");
		
		int monthFrom = Integer.valueOf(inputDate[0]);
		
		int dayOfMonthFrom = Integer.valueOf(inputDate[1]);
		
		String[] temp = inputDate[2].toString().split(" - ");
		
		int yearFrom = Integer.valueOf(temp[0]);
		
		int monthTo = Integer.valueOf(temp[1]);
		
		int dayOfMonthTo = Integer.valueOf(inputDate[3]);
		
		int yearTo = Integer.valueOf(inputDate[4]);
		
		LocalDateTime from = LocalDateTime.of(yearFrom, monthFrom, dayOfMonthFrom, 0, 0, 0);
		LocalDateTime to = LocalDateTime.of(yearTo, monthTo, dayOfMonthTo, 0, 0, 0);
		
		Set<Transaction> transactions = new TreeSet<>((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
		
		try {
			transactions.addAll(transactionDao.getFilteredTransactions(accountDao.getAccountId(user, accName), type, categoryDao.getCategoryByCategoryName(categoryName).getCategoryId(), from, to));
		} catch (SQLException e) {
			System.out.println("ops");
		}
		
		for (Transaction transaction : transactions) {
			transaction.setCategoryName(categoryName);
		}
		Set<Account> allAccounts = new TreeSet<>((a1, a2) -> a1.getName().compareToIgnoreCase(a2.getName()));
		
		Set<Category> categories = new TreeSet<>((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
		try {
			categories.addAll(categoryDao.getAllCategoriesByUserId(user.getUserId()));
			categories.addAll(categoryDao.getAllCategoriesByUserId());
		} catch (SQLException e) {
			System.out.println("mai nemame kategoriiki?");
		}
		
		for (Account acc : user.getAccounts()) {
			allAccounts.add(acc);
			
		}
		
		model.addAttribute("date", date);
		model.addAttribute("categories", categories);
		model.addAttribute("allAccounts", allAccounts);
		model.addAttribute("allTransactions", transactions);
		
		return "reports";
	}
}
