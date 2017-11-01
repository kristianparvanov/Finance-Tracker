package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Account;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.UserDAO;

@Controller
public class AccountController {
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private UserDAO userDao;

	@RequestMapping(value="/addAccount", method=RequestMethod.POST)
	public String addAccount(HttpServletRequest request, HttpSession session, Model viewModel) {
		String name = request.getParameter("name");
		String amountString = request.getParameter("amount");
		BigDecimal amount = new BigDecimal(amountString);
		
		User user = (User) request.getSession().getAttribute("user");
		Account account = new Account(name, amount, user.getUserId());
		try {
			accountDAO.insertAccount(account);
			
			user.setLastFill(LocalDateTime.now());
			userDao.updateUser(user);
		} catch (SQLException e) {
			System.out.println("Could not add account to database");
			e.printStackTrace();
		}
		
		return "redirect:main";
	}
	
	@RequestMapping(value="/addAccount", method=RequestMethod.GET)
	public String makeAccount(HttpServletRequest request, HttpSession session, Model viewModel) {
		return "addAccount";
	}
	
	@RequestMapping(value="/account/deleteAccount/{accountId}", method=RequestMethod.POST)
	public String deleteAccount(@PathVariable("accountId") Long accountId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		try {
			accountDAO.deleteAccount(accountId);
			
			user.setLastFill(LocalDateTime.now());
			userDao.updateUser(user);
		} catch (SQLException e) {
			System.out.println("nema da trieme acc-ta :(");
		}
		
		return "redirect:/main";
	}
}
