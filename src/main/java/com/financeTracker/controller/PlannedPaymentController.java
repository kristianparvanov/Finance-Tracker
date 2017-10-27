package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

@Controller
public class PlannedPaymentController {

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
			plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPayments();
			categories = CategoryDAO.getInstance().getAllCategoriesByUserId();
			ownCategories = CategoryDAO.getInstance().getAllCategoriesByUserId(user.getUserId());
			accounts = AccountDAO.getInstance().getAllAccountsByUserId(user.getUserId());
			allCategories.addAll(categories);
			allCategories.addAll(ownCategories);
			tags = TagDAO.getInstance().getAllTagsByUserId(user.getUserId());
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
			acc = AccountDAO.getInstance().getAccountByAccountName(account);
			Category cat = CategoryDAO.getInstance().getCategoryByCategoryName(category);
			PlannedPayment p = new PlannedPayment(name, TransactionType.valueOf(type), newDate, BigDecimal.valueOf(Double.valueOf(amount)), description, acc.getAccountId(), cat.getCategoryId(), tagsSet);
//			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
//			BigDecimal oldValue = AccountDAO.getInstance().getAmountByAccountId((int)acc.getAccountId());
			
//			if (type.equals("EXPENCE")) {
//				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.subtract(newValue)));
//			} else 
//			if (type.equals("INCOME")) {
//				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.add(newValue)));
//			}
			PlannedPaymentDAO.getInstance().insertPlannedPayment(p);
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
			PlannedPayment p = PlannedPaymentDAO.getInstance().getPlannedPaymentByPlannedPaymentId(plannedPaymentId);
			String name = p.getName();
			String type = p.getPaymentType().toString();
			String description = p.getDescription();
			BigDecimal amount = p.getAmount();
			String accountName = AccountDAO.getInstance().getAccountNameByAccountId(p.getAccount());
			String category = CategoryDAO.getInstance().getCategoryNameByCategoryId(p.getCategory());
			LocalDateTime date = p.getFromDate();
			Set<Tag> tags = TagDAO.getInstance().getTagsByPlannedPaymentId(plannedPaymentId);
			
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
			acc = AccountDAO.getInstance().getAccountByAccountName(account);
			Category cat = CategoryDAO.getInstance().getCategoryByCategoryName(category);
			PlannedPayment p = new PlannedPayment(name, TransactionType.valueOf(type), newDate, BigDecimal.valueOf(Double.valueOf(amount)), description, acc.getAccountId(), cat.getCategoryId(), tagsSet);
			p.setPlannedPaymentId(plannedPaymentId);
//			BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
//			BigDecimal oldValue = AccountDAO.getInstance().getAmountByAccountId((int)acc.getAccountId());
//			if (type.equals("EXPENCE")) {
//				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.subtract(newValue)));
//			} else 
//			if (type.equals("INCOME")) {
//				AccountDAO.getInstance().updateAccountAmount(acc, (oldValue.add(newValue)));
//			}
			TagDAO.getInstance().deleteAllTagsForPlannedPayment(plannedPaymentId);
			PlannedPaymentDAO.getInstance().updatePlannedPayment(p);
		} catch (SQLException e) {
			System.out.println("Could not edit planned payment");
			e.printStackTrace();
		}
		return "redirect:/plannedPayments";
	}
	
	@RequestMapping(value="/payment/deletePlannedPayment/{plannedPaymentId}", method=RequestMethod.POST)
	public String deletePlannedPayment(@PathVariable("plannedPaymentId") Long plannedPaymentId) {
		try {
			TagDAO.getInstance().deleteAllTagsForPlannedPayment(plannedPaymentId);
			PlannedPaymentDAO.getInstance().deletePlannedPayment(plannedPaymentId);
		} catch (SQLException e) {
			System.out.println("Could not delete planned payment");
			e.printStackTrace();
		}
		return "redirect:/plannedPayments";
	}
}
