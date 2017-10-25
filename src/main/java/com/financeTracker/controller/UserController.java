package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Account;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.TransactionDAO;
import com.financeTracker.model.db.UserDAO;

@Controller
public class UserController {
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String welcome(HttpSession session, Model viewModel) {
		if(session.getAttribute("user") == null){
			return "login";
		}
		else{
			return "redirect:main";
		}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpSession session, Model viewModel) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			if (UserDAO.getInstance().isValidLogin(username, password)) {
				User u = UserDAO.getInstance().getUser(username);
				session.setAttribute("user", u);
				return "redirect:main";
			}
			else{
				request.setAttribute("error", "user does not exist");
				return "login";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "login";
		}
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(HttpServletRequest request, HttpSession session, Model viewModel) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		if(!password.equals(repeatPassword)){
			request.setAttribute("error", "passwords missmatch");
			return "register";
		}
		
		try {
			if (!UserDAO.getInstance().existsUser(username)) {
				User u = new User(username, password, email, firstName, lastName);
				
				UserDAO.getInstance().insertUser(u);
				request.getSession().setAttribute("user", u);
				return "main";
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			return "login";
		}
		return "login";
	}
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main(HttpSession session, Model viewModel) {
		User u =  (User) session.getAttribute("user");
		
		Set<Account> accounts = null;
		try {
			accounts = AccountDAO.getInstance().getAllAccountsByUserId(u.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		viewModel.addAttribute("accounts", accounts);
		
		BigDecimal allBalance = BigDecimal.valueOf(0);
		for (Account account : accounts) {
			allBalance = allBalance.add(account.getAmount());
		}
		String balance = NumberFormat.getCurrencyInstance().format(allBalance);
		
		List<Transaction> allTransactions = null;
		try {
			allTransactions = TransactionDAO.getInstance().getAllTransactionsByUserId(u.getUserId());
		} catch (SQLException e) {
			System.out.println("Could not get all transactions for this user");
			e.printStackTrace();
		}
		
		List<BigDecimal> allTransactionsValues = new ArrayList<BigDecimal>();
		for (Transaction t : allTransactions) {
			if (t.getType().equals(TransactionType.EXPENCE)) {
				allTransactionsValues.add(t.getAmount().negate());
			} else {
				allTransactionsValues.add(t.getAmount());
			}
		}
		
		viewModel.addAttribute("transactionsValues", allTransactionsValues);
		viewModel.addAttribute("balance", balance);
		return "main";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getLogin() {
		return "login";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String getRegister() {
		return "register";
	}
	
	@RequestMapping(value="/about", method=RequestMethod.GET)
	public String about() {
		return "about";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "forward:index";
	}
}
