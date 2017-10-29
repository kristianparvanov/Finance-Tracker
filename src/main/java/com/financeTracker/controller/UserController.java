package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.financeTracker.util.EmailSender;

@Controller
public class UserController {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;
	
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
			if (userDAO.isValidLogin(username, password)) {
				User u = userDAO.getUser(username);
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
			if (!userDAO.existsUser(username)) {
				User u = new User(username, password, email, firstName, lastName);
				
				userDAO.insertUser(u);
				session.setAttribute("user", u);
				EmailSender.sendSimpleEmail(email, "Welcome to the Finance Tracker", "Your new profile is ready. Track away!");
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
			accounts = accountDAO.getAllAccountsByUserId(u.getUserId());
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
			allTransactions = transactionDAO.getAllTransactionsByUserId(u.getUserId());
		} catch (SQLException e) {
			System.out.println("Could not get all transactions for this user");
			e.printStackTrace();
		}
		
		TreeMap<LocalDateTime, BigDecimal> allTransactionsValues = new TreeMap<LocalDateTime, BigDecimal>(new Comparator<LocalDateTime>() {
			@Override
			public int compare(LocalDateTime d1, LocalDateTime d2) {
				return d1.compareTo(d2);
			}
		});
		TreeMap<LocalDateTime, String> allTransactionsDates = new TreeMap<LocalDateTime, String>(new Comparator<LocalDateTime>() {
			@Override
			public int compare(LocalDateTime d1, LocalDateTime d2) {
				return d1.compareTo(d2);
			}
		});
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (Transaction t : allTransactions) {
			if (t.getType().equals(TransactionType.EXPENCE)) {
				allTransactionsValues.put(t.getDate(), allBalance.add(t.getAmount().negate()));
			} else {
				allTransactionsValues.put(t.getDate(), allBalance.add(t.getAmount()));
			}
			allTransactionsDates.put(t.getDate(), t.getDate().format(formatter));
				
		}
		List<String>allTransactionsDatesList = new ArrayList<>();
		List<BigDecimal>allTransactionsValuesList = new ArrayList<>();
		allTransactionsDatesList.addAll(allTransactionsDates.values());
		allTransactionsValuesList.addAll(allTransactionsValues.values());
		
		TreeMap<BigDecimal, String> categories = null;
		try {
			categories = transactionDAO.getAllCategoriesAndTheirAmountsByUserId(u.getUserId(), "EXPENCE");
		} catch (SQLException e) {
			System.out.println("Could not getAllCategoriesAndTheirAmountsByUserId");
			e.printStackTrace();
		}
		
		viewModel.addAttribute("transactionsCategories", categories);
		viewModel.addAttribute("transactionsDates", allTransactionsDatesList);
		viewModel.addAttribute("transactionsValues", allTransactionsValuesList);
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
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String user(HttpSession session, Model model) {
		User u =  (User) session.getAttribute("user");
		
		String username = u.getUserName();
		String email = u.getEmail();
		String firstName = u.getFirstName();
		String lastName = u.getLastName();
		
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("firstName", firstName);
		model.addAttribute("lastName", lastName);
		session.setAttribute("userId", u.getUserId());
		return "user";
	}
}
