package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.Account;
import model.Tag;
import model.db.AccountDAO;
import model.db.TagDAO;
import model.db.UserDAO;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User u = (User) req.getSession().getAttribute("user");
		Set<Account> accounts = null;
		try {
			accounts = AccountDAO.getInstance().getAllAccountsByUserId(u.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		req.getSession().setAttribute("accounts", accounts);
		req.getRequestDispatcher("main.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			if (UserDAO.getInstance().isValidLogin(username, password)) {
				User u = UserDAO.getInstance().getUser(username);
				
				request.getSession().setAttribute("user", u);
				
				Set<Account> accounts = u.getAccounts();
				
				request.getSession().setAttribute("accounts", accounts);
				
				BigDecimal allBalance = BigDecimal.valueOf(0);
				for (Account account : accounts) {
					allBalance = allBalance.add(account.getAmount());
				}
				String balance = NumberFormat.getCurrencyInstance().format(allBalance);
				
				request.getSession().setAttribute("balance", balance);
				
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
			else{
				request.setAttribute("error", "user does not exist");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
		
		}
	}

}
