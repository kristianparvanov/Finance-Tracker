package com.financeTracker.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.financeTracker.model.Budget;
import com.financeTracker.model.User;
import com.financeTracker.model.db.BudgetDAO;

@Controller
public class BudgetController {
	
	@RequestMapping(value="/budgets", method=RequestMethod.GET)
	public String getAllBudgets(HttpServletRequest request, HttpSession session) {
		User u = (User) request.getSession().getAttribute("user");
		
		Set<Budget> budgets = null;
		BigDecimal percent = new BigDecimal(0.0);
		
		HashMap<Budget, BigDecimal> map = new HashMap<>();
		
		try {
			budgets = BudgetDAO.getInstance().getAllBudgetsByUserId(u.getUserId());
			
			for (Budget budget : budgets) {
				percent = budget.getAmount().divide(budget.getInitialAmount()).multiply(BigDecimal.valueOf(100));
				
				map.put(budget, percent);
			}
		} catch (SQLException e) {
			System.out.println("Izgurmqhme li?");
		}
		
		request.getSession().setAttribute("budgets", map);
		
		return "budgets";
	}
}
