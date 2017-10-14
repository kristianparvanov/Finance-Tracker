package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Tag;
import model.Transaction;
import model.TransactionType;
import model.db.AccountDAO;
import model.db.TransactionDAO;

/**
 * Servlet implementation class TransactionServlet
 */
@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransactionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String type = request.getParameter("type");
		String amount = request.getParameter("amount");
		String account = request.getParameter("account");
		String category = request.getParameter("category");
		String ownCategory = request.getParameter("ownCategory");
		String date = request.getParameter("date");
		Transaction t = new Transaction(TransactionType.valueOf(type), BigDecimal.valueOf(Double.valueOf(amount)), Long.parseLong(account), Long.parseLong(category), Long.parseLong(ownCategory), LocalDateTime.parse(date), new HashSet<Tag>());
		try {
			Account acc = AccountDAO.getInstance().getAccountByAccountId(Long.parseLong(account));
			if (type.equals("EXPENCE")) {
				BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
				//BigDecimal oldValue = AccountDAO.getInstance().
				//AccountDAO.getInstance().updateAccountAmmount(acc, (oldValue - newValue));
			} else 
			if (type.equals("INCOME")) {
				BigDecimal newValue = BigDecimal.valueOf(Double.valueOf(amount));
				//BigDecimal oldValue = AccountDAO.getInstance().
				//AccountDAO.getInstance().updateAccountAmmount(acc, (oldValue + newValue));
			}
			TransactionDAO.getInstance().insertTransaction(t);
		} catch (SQLException e) {
			System.out.println("neshto katastrofalno se slu4i");
			e.printStackTrace();
		}
	}

}
