package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.User;
import model.db.AccountDAO;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long origin = Long.valueOf(request.getParameter("accountId"));
		User u = (User) request.getSession().getAttribute("user");
		
		Account originAccount = null;
		Set<Account> userAccounts = null;
		try {
			originAccount = AccountDAO.getInstance().getAccountByAccountId(origin);
			userAccounts = AccountDAO.getInstance().getAllAccountsByUserId(u.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.getSession().setAttribute("firstAccount", originAccount);
		request.getSession().setAttribute("userAccounts", userAccounts);
		request.getRequestDispatcher("transfer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String amountParam = request.getParameter("amount");
		String fromAccount = request.getParameter("fromAccount");
		String toAccount = request.getParameter("toAccount");
		
		BigDecimal amount = BigDecimal.valueOf(Double.valueOf(amountParam));
		
		Account from = null;
		Account to = null;
		try {
			from = AccountDAO.getInstance().getAccountByAccountName(fromAccount);
			to = AccountDAO.getInstance().getAccountByAccountName(toAccount);
			AccountDAO.getInstance().makeTransferToOtherAccount(from, to, amount);
		} catch (SQLException e) {
			System.out.println("Collosal fail when making the transfer");
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("transactions.jsp").forward(request, response);
	}

}
