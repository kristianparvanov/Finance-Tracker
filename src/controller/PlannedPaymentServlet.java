package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PlannedPayment;
import model.Tag;
import model.TransactionType;
import model.User;
import model.db.PlannedPaymentDAO;

@WebServlet("/planned")
public class PlannedPaymentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<PlannedPayment> plannedPayments = null;
//		Long accountId = (Long) request.getAttribute("id");
		try {
			plannedPayments = PlannedPaymentDAO.getInstance().getAllPlannedPaymentsByAccountId(Integer.valueOf(request.getParameter("id")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (PlannedPayment plannedPayment : plannedPayments) {
			response.getWriter().append("<h4>" + plannedPayment.toString() + "</h4>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String from = request.getParameter("from");
		String amount = request.getParameter("amount");
		String description = request.getParameter("description");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String tags = request.getParameter("tags");
		
		User u = (User) request.getSession().getAttribute("user");
		HashSet<Tag> tagsSet = new HashSet<>();
		if (!tags.isEmpty()) {
			String[] tagNames = tags.split(" ");
			for (String tag : tagNames) {
				tagsSet.add(new Tag(tag, u.getUserId()));
			}
		}
		
		PlannedPayment p = new PlannedPayment(name, TransactionType.valueOf(type), LocalDateTime.parse(from), BigDecimal.valueOf(Double.valueOf(amount)), description, Long.parseLong(account), Long.parseLong(category), tagsSet);
		try {
			PlannedPaymentDAO.getInstance().insertPlannedPayment(p);
		} catch (SQLException e) {
			System.out.println("neshto katastrofalno se slu4i");
			e.printStackTrace();
		}
		//request.setAttribute("id", Long.parseLong(account));
		//doGet(request, response);
	}

}
