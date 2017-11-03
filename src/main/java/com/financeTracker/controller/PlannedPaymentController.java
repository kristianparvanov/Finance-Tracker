package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Account;
import com.financeTracker.model.Category;
import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.Tag;
import com.financeTracker.model.TransactionType;
import com.financeTracker.model.User;
import com.financeTracker.model.db.AccountDAO;
import com.financeTracker.model.db.CategoryDAO;
import com.financeTracker.model.db.PlannedPaymentDAO;
import com.financeTracker.model.db.TagDAO;
import com.financeTracker.model.db.UserDAO;
import com.financeTracker.threads.PlannedPaymentService;

@Controller
public class PlannedPaymentController {
	@Autowired
	private PlannedPaymentDAO plannedPaymentDAO;
	
	@Autowired
	private PlannedPaymentService plannedPaymentService;
	
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private TagDAO tagDAO;
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value="/plannedPayments", method=RequestMethod.GET)
	public String getAllPlannedPayments(HttpServletRequest request, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<PlannedPayment> plannedPayments = null;
		Set<Category> allCategories = new HashSet<Category>();
		Set<Category> ownCategories = new HashSet<Category>();
		Set<Category> categories = new HashSet<Category>();
		Set<Account> accounts = new HashSet<Account>();
		Set<Tag> tags = new HashSet<Tag>();
		try {
			plannedPayments = plannedPaymentDAO.getAllPlannedPaymentsByUserId(user.getUserId());
			categories = categoryDao.getAllCategoriesByUserId();
			ownCategories = categoryDao.getAllCategoriesByUserId(user.getUserId());
			accounts = accountDAO.getAllAccountsByUserId(user.getUserId());
			allCategories.addAll(categories);
			allCategories.addAll(ownCategories);
			tags = tagDAO.getAllTagsByUserId(user.getUserId());
		} catch (SQLException e) {
			System.out.println("Could not get all planned payments");
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("categories", allCategories);
		request.getSession().setAttribute("accounts", accounts);
		request.getSession().setAttribute("tags", tags);
		request.getSession().setAttribute("plannedPayments", plannedPayments);
		
		return "plannedPayments";
	}
	
	@RequestMapping(value="/addPlannedPayment", method=RequestMethod.GET)
	public String getAddPlannedPayment(HttpServletRequest request, HttpSession session) {
		return "addPlannedPayment";
	}
	
	@RequestMapping(value="/addPlannedPayment", method=RequestMethod.POST)
	public String postAddPlannedPayment(HttpServletRequest request, HttpSession session) {
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String amount = request.getParameter("amount");
		String date = request.getParameter("date");
		String[] tags = request.getParameterValues("tags");
		String description = request.getParameter("description");
		
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
			acc = accountDAO.getAccountByAccountNameAndAccountId(account, u.getUserId());
			Category cat = categoryDao.getCategoryByCategoryName(category);
			PlannedPayment p = new PlannedPayment(name, TransactionType.valueOf(type), newDate, BigDecimal.valueOf(Double.valueOf(amount)), description, acc.getAccountId(), cat.getCategoryId(), tagsSet);
//			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
//			BigDecimal oldValue = accountDAO.getAmountByAccountId((int)acc.getAccountId());
			
//			if (type.equals("EXPENCE")) {
//				accountDAO.updateAccountAmount(acc, (oldValue.subtract(newValue)));
//			} else 
//			if (type.equals("INCOME")) {
//				accountDAO.updateAccountAmount(acc, (oldValue.add(newValue)));
//			}
			plannedPaymentDAO.insertPlannedPayment(p);
			plannedPaymentService.initPaymentThread(p);
			
			u.setLastFill(LocalDateTime.now());
			userDao.updateUser(u);
		} catch (SQLException e) {
			System.out.println("Could not add planned payment");
			e.printStackTrace();
		}
		request.setAttribute("user", u);
		request.setAttribute("accountId", acc.getAccountId());
		
		//response.sendRedirect("transaction");
		//transaction?accountId=${sessionScope.accountId}
		
		return "redirect:/plannedPayments";
		//request.getRequestDispatcher("transaction?accountId=" + acc.getAccountId()).forward(request, response);
	}
	
	//"payment/${payment.plannedPaymentId}
	@RequestMapping(value="/payment/{plannedPaymentId}", method=RequestMethod.GET)
	public String getEditPlannedPayment(HttpServletRequest request, HttpSession session, Model model, @PathVariable("plannedPaymentId") Long plannedPaymentId) {
		try {
			PlannedPayment p = plannedPaymentDAO.getPlannedPaymentByPlannedPaymentId(plannedPaymentId);
			String name = p.getName();
			String type = p.getPaymentType().toString();
			String description = p.getDescription();
			BigDecimal amount = p.getAmount();
			String accountName = accountDAO.getAccountNameByAccountId(p.getAccount());
			String category = categoryDao.getCategoryNameByCategoryId(p.getCategory());
			LocalDateTime date = p.getFromDate();
			Set<Tag> tags = tagDAO.getTagsByPlannedPaymentId(plannedPaymentId);
			
			Set<String> tagNames = new HashSet<String>();
			for (Tag tag : tags) {
				tagNames.add(tag.getName());
			}
			
			model.addAttribute("editPlannedPaymentName", name);
			model.addAttribute("editPlannedPaymentType", type);
			model.addAttribute("editTPlannedPaymentDescription", description);
			model.addAttribute("editPlannedPaymentAmount", amount);
			model.addAttribute("editPlannedPaymentAccount", accountName);
			model.addAttribute("editPlannedPaymentCategory", category);
			model.addAttribute("editPlannedPaymentDate", date);
			model.addAttribute("editPlannedPaymentTags", tagNames);
			session.setAttribute("plannedPaymentId", plannedPaymentId);
		} catch (SQLException e) {
			System.out.println("Could not extract planned payment from database");
			e.printStackTrace();
		}
	
		return "editPlannedPayment";
	}
	
	@RequestMapping(value="/payment/editPlannedPayment", method=RequestMethod.POST)
	public String postEditPlannedPayment(HttpServletRequest request, HttpSession session, Model model) {
		
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String amount = request.getParameter("amount");
		String date = request.getParameter("date");
		String[] tags = request.getParameterValues("tags");
		String description = request.getParameter("description");
		long plannedPaymentId = (long) request.getSession().getAttribute("plannedPaymentId");
		
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
			PlannedPayment p = new PlannedPayment(name, TransactionType.valueOf(type), newDate, BigDecimal.valueOf(Double.valueOf(amount)), description, acc.getAccountId(), cat.getCategoryId(), tagsSet);
			p.setPlannedPaymentId(plannedPaymentId);
//			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
//			BigDecimal oldValue = accountDAO.getAmountByAccountId((int)acc.getAccountId());
//			if (type.equals("EXPENCE")) {
//				accountDAO.updateAccountAmount(acc, (oldValue.subtract(newValue)));
//			} else 
//			if (type.equals("INCOME")) {
//				accountDAO.updateAccountAmount(acc, (oldValue.add(newValue)));
//			}
			tagDAO.deleteAllTagsForPlannedPayment(plannedPaymentId);
			plannedPaymentDAO.updatePlannedPayment(p);
			
			u.setLastFill(LocalDateTime.now());
			userDao.updateUser(u);
		} catch (SQLException e) {
			System.out.println("Could not edit planned payment");
			e.printStackTrace();
		}
		return "redirect:/plannedPayments";
	}
	
	@RequestMapping(value="/payment/deletePlannedPayment/{plannedPaymentId}", method=RequestMethod.POST)
	public String deletePlannedPayment(@PathVariable("plannedPaymentId") Long plannedPaymentId, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		try {
			tagDAO.deleteAllTagsForPlannedPayment(plannedPaymentId);
			plannedPaymentDAO.deletePlannedPayment(plannedPaymentId);
			
			user.setLastFill(LocalDateTime.now());
			userDao.updateUser(user);
		} catch (SQLException e) {
			System.out.println("Could not delete planned payment");
			e.printStackTrace();
		}
		return "redirect:/plannedPayments";
	}
}
