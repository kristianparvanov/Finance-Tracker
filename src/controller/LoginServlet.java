package controller;

import java.io.IOException;
import java.sql.SQLException;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		try {
			if (UserDAO.getInstance().isValidLogin(username, password)) {
				User u = UserDAO.getInstance().getUser(username);
				
				request.getSession().setAttribute("user", u);
				
				/*ServletContext application = getServletConfig().getServletContext();
				synchronized (application) {
					if(application.getAttribute("accounts") == null){
						Set<Account> accounts = AccountDAO.getInstance().getAllAccountsByUserId((int)u.getUserId());
						application.setAttribute("accounts", accounts);
					}
				}
				synchronized (application) {
					if(application.getAttribute("products") == null){
						Set<Tag> tags = TagDAO.getInstance().getAllTagsByUserId((int)u.getUserId());
						application.setAttribute("products", tags);
					}
				}*/
			
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
