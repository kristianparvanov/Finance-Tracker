package com.financeTracker.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Account;
import com.financeTracker.model.Budget;
import com.financeTracker.model.Category;
import com.financeTracker.model.Tag;
import com.financeTracker.model.Transaction;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.BudgetDAO;
import com.financeTracker.model.db.BudgetsHasTransactionsDAO;
import com.financeTracker.model.db.CategoryDAO;
import com.financeTracker.model.db.TagDAO;
import com.financeTracker.model.db.TransactionDAO;

@Controller
public class BudgetController {
	
	@Autowired
	BudgetDAO budgetDao = new BudgetDAO();
	
	@RequestMapping(value="/budgets", method=RequestMethod.GET)
	public String getAllBudgets(HttpSession session, Model model) {
		User u = (User) session.getAttribute("user");
		
		Set<Budget> budgets = null;
		BigDecimal percent = new BigDecimal(0.0);
		
		HashMap<Budget, BigDecimal> map = new HashMap<>();
		
		try {
			budgets = budgetDao.getAllBudgetsByUserId(u.getUserId());
			
			for (Budget budget : budgets) {
				percent = budget.getAmount().divide(budget.getInitialAmount(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
				
				map.put(budget, percent);
			}
		} catch (SQLException e) {
			System.out.println("Izgurmqhme li?");
		}
		
//		request.getSession().setAttribute("budgets", map);
		
		model.addAttribute("budgets", map);
		
		return "budgets";
	}
	
	@RequestMapping(value="/addBudget", method=RequestMethod.GET)
	public String mskeBudget(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		
		try {
			Set<Account> accounts = AccountDAO.getInstance().getAllAccountsByUserId(user.getUserId());
			Set<Category> categories = CategoryDAO.getInstance().getAllCategoriesByUserId(user.getUserId());
			categories.addAll(CategoryDAO.getInstance().getAllCategoriesByUserId());
			Set<Tag> tags = TagDAO.getInstance().getAllTagsByUserId(user.getUserId());
			
			model.addAttribute("accounts", accounts);
			model.addAttribute("categories", categories);
			model.addAttribute("tags", tags);
		} catch (SQLException e) {
			System.out.println("NEMA BUDGETI");
		}
		
		return "addBudget";
	}
	
	@RequestMapping (value ="/addBudget", method = RequestMethod.POST)
	public String addBudget(HttpServletRequest request, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		
		try {
			String name = request.getParameter("name");
			Account acc = AccountDAO.getInstance().getAccountByUserIDAndAccountName(user.getUserId(), request.getParameter("account"));
			Category category = CategoryDAO.getInstance().getCategoryByCategoryName(request.getParameter("category"));
			BigDecimal amount = new BigDecimal(request.getParameter("amount"));
			String[] tags = request.getParameterValues("tags");
			String date = request.getParameter("date");
			
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
			
			Set<Tag> tagsSet = new HashSet<>();
			if (tags != null) {
				for (String tagName : tags) {
					tagsSet.add(new Tag(tagName, user.getUserId()));
				}
			}

			Budget b = new Budget(name, amount, dateFrom, dateTo, acc.getAccountId(), category.getCategoryId(), tagsSet);
			
			budgetDao.insertBudget(b);
		} catch (SQLException e) {
			System.out.println("Nemame smetki mai? :(");
		}
		
		
		return "forward:/budgets";
	}
	
	@RequestMapping (value ="/budgets/{budgetId}", method = RequestMethod.GET)
	public String viewBudget(@PathVariable("budgetId") Long budgetId, Model model) {
		try {
			Budget b = budgetDao.getBudgetByBudgetId(budgetId);
			
			model.addAttribute("budgetId", budgetId);
			model.addAttribute("budgetTransactions", b.getTransactions());
		} catch (SQLException e) {
			System.out.println("Mai nqmame tranzakciiki?? ;(");
		}
		
		
		return "budgetInfo";
	}
	
	@RequestMapping (value ="/budgets/{budgetId}/editBudget", method = RequestMethod.POST)
	public String postEditBudget(HttpSession session, HttpServletRequest request, @PathVariable("budgetId") Long budgetId) {
		User user = (User) session.getAttribute("user");
		
		try {
			Budget oldBudget = budgetDao.getBudgetByBudgetId(budgetId);
			
			
			String name = request.getParameter("name");
			Account acc = AccountDAO.getInstance().getAccountByUserIDAndAccountName(user.getUserId(), request.getParameter("account"));
			Category category = CategoryDAO.getInstance().getCategoryByCategoryName(request.getParameter("category"));
			BigDecimal amount = new BigDecimal(request.getParameter("amount"));
			String[] tags = request.getParameterValues("tags");
			String date = request.getParameter("date");
			
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
			
			Set<Tag> tagsSet = new HashSet<>();
			if (tags != null) {
				for (String tagName : tags) {
					tagsSet.add(new Tag(tagName, user.getUserId()));
				}
			}

			Budget newBudget = new Budget(name, amount, dateFrom, dateTo, acc.getAccountId(), category.getCategoryId(), tagsSet);
			newBudget.setBudgetId(budgetId);
			
			boolean asd = newBudget.getCategoryId() != oldBudget.getCategoryId() || newBudget.getAccountId() != oldBudget.getAccountId()
					|| newBudget.getFromDate() != oldBudget.getFromDate() || newBudget.getToDate() != oldBudget.getToDate();
			
			System.out.println(asd);
			
			if (asd) {
				
				Set<Transaction> transactions = BudgetsHasTransactionsDAO.getInstance().getAllTransactionsByBudgetId(budgetId);
				BigDecimal newAmount = new BigDecimal(0.0);
				
				for (Transaction transaction : transactions) {
					newAmount = newAmount.subtract(transaction.getAmount());
				}
				
				newBudget.setAmount(newAmount);
				
				BudgetsHasTransactionsDAO.getInstance().deleteTransactionBudgetByBudgetId(budgetId);
				
				boolean exits = TransactionDAO.getInstance().existsTransaction(newBudget.getFromDate(), newBudget.getToDate(), newBudget.getCategoryId(), newBudget.getAccountId());
				
				if (exits) {
					transactions = TransactionDAO.getInstance().getAllTransactionsForBudget(newBudget.getFromDate(), newBudget.getToDate(), newBudget.getCategoryId(), newBudget.getAccountId());
				
					 newAmount = new BigDecimal(0.0);
					
					for (Transaction transaction : transactions) {
						BudgetsHasTransactionsDAO.getInstance().insertTransactionBudget(newBudget.getBudgetId(), transaction.getTransactionId());
						
						newAmount = newAmount.add(transaction.getAmount());
					}
					
					newBudget.setAmount(newAmount);
					newBudget.setTransactions(transactions);
					
					budgetDao.updateBudget(newBudget);
				}

				TagDAO.getInstance().deleteAllTagsForBydget(budgetId);
				
				for (Tag tag : tagsSet) {
					TagDAO.getInstance().insertTagToBudget(newBudget, tag);
				}
			}
			
		} catch (SQLException e) {
			System.out.println("opala");
			
			e.getMessage();
		}
		
		return "redirect:/budgets/" + budgetId;
	}
	
	@RequestMapping (value ="/budgets/{budgetId}/editBudget", method = RequestMethod.GET)
	public String getEditBudget(HttpSession session, @PathVariable("budgetId") Long budgetId, Model model) {
		User user = (User) session.getAttribute("user");
		
		Budget budget;
		try {
			budget = budgetDao.getBudgetByBudgetId(budgetId);
			Account acc = AccountDAO.getInstance().getAccountByAccountId(budget.getAccountId());
			Set<Account> accounts = AccountDAO.getInstance().getAllAccountsByUserId(user.getUserId());
			BigDecimal amount = budget.getInitialAmount();
			String categoryName = CategoryDAO.getInstance().getCategoryNameByCategoryId(budget.getCategoryId());
			Set<Category> categories = CategoryDAO.getInstance().getAllCategoriesByUserId(user.getUserId());
			categories.addAll(CategoryDAO.getInstance().getAllCategoriesByUserId());
			Set<Tag> tags = TagDAO.getInstance().getAllTagsByUserId(user.getUserId());
			LocalDateTime fromDate = budget.getFromDate();
			LocalDateTime toDate = budget.getToDate();
			
			StringBuilder date = new StringBuilder();
			
			date.append(fromDate.getMonthValue()).append("/").append(fromDate.getDayOfMonth()).append("/").append(fromDate.getYear());
			date.append(" - ");
			date.append(toDate.getMonthValue()).append("/").append(toDate.getDayOfMonth()).append("/").append(toDate.getYear());
			
			Set<String> tagNames = new HashSet<String>();
			for (Tag tag : tags) {
				tagNames.add(tag.getName());
			}
			
			model.addAttribute("categoryName", categoryName);
			model.addAttribute("categories", categories);
			model.addAttribute("tagNames", tagNames);
			model.addAttribute("tags", tags);
			model.addAttribute("editBudgetAmount", amount);
			model.addAttribute("accounts", accounts);
			model.addAttribute("accountName", acc.getName());
			model.addAttribute("budget", budget);
			model.addAttribute("date", date);
		} catch (SQLException e) {
			System.out.println("Nqmame budgetche??");
		}
		
		return "editBudget";
	}
}
