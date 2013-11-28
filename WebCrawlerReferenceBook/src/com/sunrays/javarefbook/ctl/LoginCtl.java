package com.sunrays.javarefbook.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunrays.javarefbook.model.UserModel;

/**
 * Servlet Controller class to register a user
 * 
 * @author Sunil Sahu
 * 
 */
public class LoginCtl extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		UserModel model = new UserModel();

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String operation = request.getParameter("operation");

		List errors = new ArrayList();

		RequestDispatcher rd = null;

		// Add new user in the Database

		if ("Sign In".equals(operation)) {
			try {
				model.setLogin(login);
				model.setPassword(password);

				UserModel dbModel = model.authenticate();

				if (dbModel != null) {
					session.setAttribute("user", dbModel);

					rd = request.getRequestDispatcher("/Welcome.jsp");
					rd.forward(request, response);
				} else {
					errors.add("Invalid Username or Password.");
					request.setAttribute("error", errors);
					doGet(request, response);
				}

			} catch (Exception e) {
				// If database exception then go back to User registration page
				e.printStackTrace();
				errors.add("Database Error.");
				request.setAttribute("error", errors);
				doGet(request, response);
			}
		}

	}

}
