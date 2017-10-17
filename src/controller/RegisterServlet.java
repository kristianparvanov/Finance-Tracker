package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.db.UserDAO;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repeatPassword = request.getParameter("repeatPassword");
		String email = request.getParameter("email");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		if(!password.equals(repeatPassword)){
			request.setAttribute("error", "passwords missmatch");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			return;
		}
		
		try {
			if (!UserDAO.getInstance().existsUser(username)) {
				User u = new User(username, password, email, firstName, lastName);
				
				UserDAO.getInstance().insertUser(u);
				request.getSession().setAttribute("user", u);
				request.getRequestDispatcher("main.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			request.setAttribute("error", "database problem : " + e.getMessage());
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
		
		
	}

}
