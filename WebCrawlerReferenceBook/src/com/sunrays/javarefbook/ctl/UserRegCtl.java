package com.sunrays.javarefbook.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

public class UserRegCtl extends HttpServlet {

	/**
	 * Check is string value is not null or empty
	 */
	private boolean isNull(String val) {
		return val == null || val.length() == 0;
	}

	/**
	 * Validate input data received from HTML form
	 * 
	 * @param model
	 * @return errors
	 */
	private List validateInput(UserModel model) {

		ArrayList errors = new ArrayList();

		if (isNull(model.getFirstName())) {
			errors.add("First Name can't be null.");
		}

		if (isNull(model.getLastName())) {
			errors.add("Last Name can't be null.");
		}

		if (isNull(model.getLogin())) {
			errors.add("Login can't be null.");
		}
		if (isNull(model.getPassword())) {
			errors.add("Password can't be null.");
		}

		return errors;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("UserReg.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Populate request data into Model

		HttpSession session = request.getSession(true);

		UserModel model = new UserModel();

		model.setFirstName(request.getParameter("firstName"));
		model.setLastName(request.getParameter("lastName"));
		model.setLogin(request.getParameter("login"));
		model.setPassword(request.getParameter("password"));
		model.setDateOfBirth(new Date());
		String operation = request.getParameter("operation");

		RequestDispatcher rd = null;

		// Validate input
		List errors = validateInput(model);

		if ("Sign Up".equals(operation)) {

			// If errors then go back to user registration page
			if (errors.size() > 0) {
				request.setAttribute("error", errors);
				doGet(request, response);
			} else {

				// Add new user in the Database
				try {
					UserModel checkModel = new UserModel();
					checkModel.setLogin(model.getLogin());
					List userList = checkModel.search();

					if (userList.size() > 0) {
						errors.add("This LoginId is already exist into database. Please choose different one.");
						request.setAttribute("error", errors);
						rd = request.getRequestDispatcher("/UserReg.jsp");
						rd.forward(request, response);
					} else {
						model.setId(model.nextPK());
						model.setRoleId(3);
						model.add();

						session.setAttribute("user", model);

						request.setAttribute("message",
								"Congratulation! Now you are ready to access application.");
						// Go to success page
						rd = request.getRequestDispatcher("/Welcome.jsp");
						rd.forward(request, response);
					}
				} catch (Exception e) {
					// If database exception then go back to User registration
					// page
					e.printStackTrace();
					errors.add("Database Error.");
					request.setAttribute("error", errors);
					doGet(request, response);
				}
			}
		}
	}
}
