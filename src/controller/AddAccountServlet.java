package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.User;
import model.db.AccountDAO;

@WebServlet("/addAccount")
public class AddAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("addAccount.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String amountString = request.getParameter("amount");
		BigDecimal amount = new BigDecimal(amountString);
		
		User user = (User) request.getSession().getAttribute("user");
		Account account = new Account(name, amount, user.getUserId());
		try {
			AccountDAO.getInstance().insertAccount(account);
		} catch (SQLException e) {
			System.out.println("Could not add account to database");
			e.printStackTrace();
		}
		request.getRequestDispatcher("main.jsp").forward(request, response);
	}

}
