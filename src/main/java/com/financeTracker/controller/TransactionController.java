package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.financeTracker.model.Account;
import com.financeTracker.model.Category;
import com.financeTracker.model.Tag;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.BudgetsHasTransactionsDAO;
import com.financeTracker.model.db.CategoryDAO;
import com.financeTracker.model.db.TagDAO;
import com.financeTracker.model.db.TransactionDAO;
import com.financeTracker.model.db.UserDAO;


@Controller
@RequestMapping(value="/account")
public class TransactionController {
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TagDAO tagDAO;
	
	@Autowired
	private TransactionDAO transactionDAO;
	
	@Autowired
	private BudgetsHasTransactionsDAO budgetsHasTransactionsDAO;
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value="/{accountId}", method=RequestMethod.GET)
	public String getAllTransactions(HttpServletRequest request, HttpSession session, Model model, @PathVariable("accountId") Long accountId) {
		User user = (User) session.getAttribute("user");
		TreeSet<Transaction> transactions = new TreeSet<>((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
		BigDecimal accountBalance = null;
		String accountName = null;
		Set<Category> allCategories = new HashSet<Category>();
		Set<Category> ownCategories = new HashSet<Category>();
		Set<Category> categories = new HashSet<Category>();
		Set<Account> accounts = new HashSet<Account>();
		Set<Tag> tags = new HashSet<Tag>();
		try {
			request.getSession().setAttribute("accountId", accountId);
			transactions.addAll(transactionDAO.getAllTransactionsByAccountId(accountId));
			accountBalance = accountDAO.getAmountByAccountId(accountId);
			accountName = accountDAO.getAccountNameByAccountId(accountId);
			categories = categoryDao.getAllCategoriesByUserId();
			ownCategories = categoryDao.getAllCategoriesByUserId(user.getUserId());
			accounts = accountDAO.getAllAccountsByUserId(user.getUserId());
			allCategories.addAll(categories);
			allCategories.addAll(ownCategories);
			tags = tagDAO.getAllTagsByUserId(user.getUserId());
		} catch (SQLException e) {
			return "error500";
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
	
	@RequestMapping(value="/addTransaction", method=RequestMethod.GET)
	public String getAddTransaction(HttpServletRequest request, HttpSession session, Model model) {
		User user = new User();
		
		model.addAttribute("user", user);	
		
		return "addTransaction";
	}
	
	@RequestMapping(value="/addTransaction", method=RequestMethod.POST)
	public String postAddTransaction(HttpServletRequest request, HttpSession session, Model m) {
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
			acc = accountDAO.getAccountByAccountNameAndAccountId(account, u.getUserId());
			Category cat = categoryDao.getCategoryByCategoryName(category);
			Transaction t = new Transaction(TransactionType.valueOf(type), description, BigDecimal.valueOf(Double.valueOf(amount)), acc.getAccountId(), cat.getCategoryId(), LocalDateTime.now(), tagsSet);
			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
			BigDecimal oldValue = accountDAO.getAmountByAccountId((int)acc.getAccountId());
			if (type.equals("EXPENCE")) {
				accountDAO.updateAccountAmount(acc, (oldValue.subtract(newValue)));
			} else 
			if (type.equals("INCOME")) {
				accountDAO.updateAccountAmount(acc, (oldValue.add(newValue)));
			}
			transactionDAO.insertTransaction(t);
			
			u.setLastFill(LocalDateTime.now());
			userDao.updateUser(u);
		} catch (SQLException e) {
			return "error500";
		}
		request.setAttribute("user", u);
		request.setAttribute("accountId", acc.getAccountId());
		
		return "redirect:/account/" + acc.getAccountId();
	}
	
	@RequestMapping(value="/transaction/{transactionId}", method=RequestMethod.GET)
	public String getEditTransaction(HttpServletRequest request, HttpSession session, Model model, @PathVariable("transactionId") Long transactionId) {
		try {
			Transaction t = transactionDAO.getTransactionByTransactionId(transactionId);
			String type = t.getType().toString();
			String description = t.getDescription();
			BigDecimal amount = t.getAmount();
			String accountName = accountDAO.getAccountNameByAccountId(t.getAccount());
			String category = categoryDao.getCategoryNameByCategoryId(t.getCategory());
			LocalDateTime date = t.getDate();
			Set<Tag> tags = tagDAO.getTagsByTransactionId(transactionId);
			
			Set<String> tagNames = new HashSet<String>();
			for (Tag tag : tags) {
				tagNames.add(tag.getName());
			}
			
			model.addAttribute("editTransactionType", type);
			model.addAttribute("editTransactionDescription", description);
			model.addAttribute("editTransactionAmount", amount);
			model.addAttribute("editTransactionAccount", accountName);
			model.addAttribute("editTransactionCategory", category);
			model.addAttribute("editTransactionDate", date);
			model.addAttribute("editTransactionTags", tagNames);
			session.setAttribute("transactionId", transactionId);
		} catch (SQLException e) {
			return "error500";
		}
		
		return "editTransaction";
	}

	@RequestMapping(value="/transaction/editTransaction", method=RequestMethod.POST)
	public String postEditTransaction(HttpServletRequest request, HttpSession session, Model model) {
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
		
		User u = (User) session.getAttribute("user");
		
		HashSet<Tag> tagsSet = new HashSet<>();
		if (tags != null) {
			for (String tagName : tags) {
				tagsSet.add(new Tag(tagName, u.getUserId()));
			}
		}
		
		Account acc = null;
		try {
			acc = accountDAO.getAccountByAccountNameAndAccountId(account, u.getUserId());
			Category cat = categoryDao.getCategoryByCategoryName(category);
			Transaction t = new Transaction(TransactionType.valueOf(type), description, BigDecimal.valueOf(Double.valueOf(amount)), acc.getAccountId(), cat.getCategoryId(), newDate, tagsSet);
			t.setTransactionId(transactionId);
			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
			BigDecimal oldValue = accountDAO.getAmountByAccountId((int)acc.getAccountId());
			if (type.equals("EXPENCE")) {
				accountDAO.updateAccountAmount(acc, (oldValue.subtract(newValue)));
			} else 
			if (type.equals("INCOME")) {
				accountDAO.updateAccountAmount(acc, (oldValue.add(newValue)));
			}
			
			tagDAO.deleteAllTagsForTransaction(transactionId);
//			transactionDAO.removeTransaction(transactionId);
			transactionDAO.updateTransaction(t);
			
			u.setLastFill(LocalDateTime.now());
			userDao.updateUser(u);
		} catch (SQLException e) {
			return "error500";
		}
		return "redirect:/account/" + acc.getAccountId();
	}
	
	//transfer/accountId/${accountId}
	//http://localhost:8080/FinanceTracker/account/transfer/accountId/2
	//@RequestMapping(value="/account/transfer/accountId/${accountId}", method=RequestMethod.GET)
	
	@RequestMapping(value="/transfer/accountId/{accountId}", method=RequestMethod.GET)
	public String getTransfer(HttpServletRequest request, Model model, HttpSession session, @PathVariable("accountId") Long originAccountId) {
		User u = (User) session.getAttribute("user");
		
		Account originAccount = null;
		Set<Account> userAccounts = null;
		try {
			originAccount = accountDAO.getAccountByAccountId(originAccountId);
			userAccounts = accountDAO.getAllAccountsByUserId(u.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("firstAccount", originAccount);
		model.addAttribute("userAccounts", userAccounts);
		
		return "transfer";
	}
	
	@RequestMapping(value="/transfer/accountId/transfer", method=RequestMethod.POST)
	public String postTransfer(HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		String amountParam = request.getParameter("amount");
		String fromAccount = request.getParameter("fromAccount");
		String toAccount = request.getParameter("toAccount");
		
		BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountParam));
		
		Account from = null;
		Account to = null;
		try {
			from = accountDAO.getAccountByAccountNameAndAccountId(fromAccount, user.getUserId());
			to = accountDAO.getAccountByAccountNameAndAccountId(toAccount, user.getUserId());
			accountDAO.makeTransferToOtherAccount(from, to, amount);
			
			user.setLastFill(LocalDateTime.now());
			userDao.updateUser(user);
		} catch (SQLException e) {
			System.out.println("Collosal fail when making the transfer");
			e.printStackTrace();
		}
		
		return "redirect:/account/" + from.getAccountId();
	}
	
	@RequestMapping(value="transaction/deleteTransaction/{transactionId}", method=RequestMethod.POST)
	public String deleteTransaction(@PathVariable("transactionId") Long transactionId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		Transaction t = null;
		Account acc = null;
		try {
			t = transactionDAO.getTransactionByTransactionId(transactionId);
			acc = accountDAO.getAccountByAccountId(t.getAccount());
			
			BigDecimal newValue = t.getAmount();
			BigDecimal oldValue = accountDAO.getAmountByAccountId(t.getAccount());
			if (t.getType().equals(TransactionType.EXPENCE)) {
				accountDAO.updateAccountAmount(acc, (oldValue.add(newValue)));
			} else 
			if (t.getType().equals(TransactionType.INCOME)) {
				accountDAO.updateAccountAmount(acc, (oldValue.subtract(newValue)));
			}
			
			transactionDAO.deleteTransaction(t);
			
			user.setLastFill(LocalDateTime.now());
			userDao.updateUser(user);
		} catch (SQLException e) {
			return "error500";
		}
		
		return "redirect:/account/" + t.getAccount();
	}
	
	@ResponseBody
	@RequestMapping(value="/getCategory/{type}", method=RequestMethod.GET)
	public Set<String> getIncomeCategories(HttpSession session, @PathVariable("type") String type, HttpServletResponse response) {
		User user = (User) session.getAttribute("user");
		Set<String> categories = null;
		try {
			categories = categoryDao.getAllCategoriesByType(user.getUserId(), type);
		} catch (SQLException e) {
			response.setStatus(500);
		}
		
		return categories;
	}
}
