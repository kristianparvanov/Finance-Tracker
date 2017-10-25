package com.financeTracker.controller;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Account;
import com.financeTracker.model.Category;
import com.financeTracker.model.PlannedPayment;
import com.financeTracker.model.Tag;
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
			System.out.println("Something atrocious happened to the DB");
			e.printStackTrace();
		}
		
		model.addAttribute("categories", allCategories);
		model.addAttribute("accounts", accounts);
		model.addAttribute("tags", tags);
		model.addAttribute("plannedPayments", plannedPayments);
		
		return "plannedPayments";
	}
}
