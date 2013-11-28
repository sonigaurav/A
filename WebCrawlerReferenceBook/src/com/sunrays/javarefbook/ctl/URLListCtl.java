package com.sunrays.javarefbook.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunrays.javarefbook.model.RoleModel;
import com.sunrays.javarefbook.model.URLModel;
import com.sunrays.javarefbook.model.UserModel;

/**
 * Servlet Controller class to register a user
 * 
 * @author Sunil Sahu
 * 
 */
public class URLListCtl extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		URLModel urlModel = new URLModel();
		RequestDispatcher rd = null;

		try {
			UserModel userModel = (UserModel) session.getAttribute("user");
			int user_Id = userModel.getId();
			int role_Id = userModel.getRoleId();

			List urlList = urlModel.getUrls(user_Id, role_Id);

			request.setAttribute("urlList", urlList);
		} catch (Exception e) {
			// e.printStackTrace();
			// request.setAttribute("error", "Database Error.");
			rd = request.getRequestDispatcher("URLList.jsp");
		}

		rd = request.getRequestDispatcher("URLList.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}