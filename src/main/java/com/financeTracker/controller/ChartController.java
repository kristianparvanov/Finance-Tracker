package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
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
import com.financeTracker.model.Tag;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.CategoryDAO;
import com.financeTracker.model.db.TransactionDAO;

@Controller
public class ChartController {
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;

	@RequestMapping(value="/cashflowStructute", method=RequestMethod.GET)
	public String getAllReports(HttpSession session, Model model) {
		User u =  (User) session.getAttribute("user");
		
		Set<Account> accounts = new HashSet<Account>();
		TreeMap<BigDecimal, String> transactionCategories = null;
		
		try {
			accounts = accountDAO.getAllAccountsByUserId(u.getUserId());
			transactionCategories = transactionDAO.getAllCategoriesAndTheirAmountsByUserId(u.getUserId(), "EXPENCE");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("accounts", accounts);
		model.addAttribute("transactionsCategories", transactionCategories);
		
		return "cashflowStructute";
	}
	
	//getTransactions
	@RequestMapping(value="/getTransactions", method=RequestMethod.POST)
	public String postAddPlannedPayment(HttpServletRequest request, HttpSession session, Model model) {
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		String account = request.getParameter("account");
		
		String[] inputDate = date.split("/");
		int monthFrom = Integer.valueOf(inputDate[0]);
		int dayOfMonthFrom = Integer.valueOf(inputDate[1]);
		String[] temp = inputDate[2].toString().split(" - ");
		
		int yearFrom = Integer.valueOf(temp[0]);
		int monthTo = Integer.valueOf(temp[1]);
		int dayOfMonthTo = Integer.valueOf(inputDate[3]);
		int yearTo = Integer.valueOf(inputDate[4]);
		
		LocalDateTime dateFrom = LocalDateTime.of(yearFrom, monthFrom, dayOfMonthFrom, 0, 0, 0);
		LocalDateTime dateTo = LocalDateTime.of(yearTo, monthTo, dayOfMonthTo, 0, 0, 0);
		
		System.out.println(date);
		System.out.println(type);
		System.out.println(account);
		System.out.println(dateFrom);
		System.out.println(dateTo);
		
		User u =  (User) session.getAttribute("user");
		
		Set<Account> accounts = new HashSet<Account>();
		TreeMap<BigDecimal, String> transactions = null;
		
		try {
			accounts = accountDAO.getAllAccountsByUserId(u.getUserId());
			transactions = transactionDAO.getAllTransactionsByUserDateTypeAccount(u.getUserId(), dateFrom, dateTo, type, account);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("accounts", accounts);
		model.addAttribute("transactionsCategories", transactions);
		
		return "cashflowStructute";
	}
	
}
