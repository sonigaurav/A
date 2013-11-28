package com.sunrays.javarefbook.ctl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunrays.javarefbook.model.CrawlerModel;
import com.sunrays.javarefbook.model.URLModel;
import com.sunrays.javarefbook.model.UserModel;

public class CrawlerCtl extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String keyword = request.getParameter("keyword");

		String operation = request.getParameter("operation");

		CrawlerModel crawlerModel = new CrawlerModel();

		URLModel urlModel = new URLModel();

		String error = null;

		if ("Search".equals(operation)) {
			try {
				crawlerModel.setKeyword(keyword);
				List list = crawlerModel.getCrawlingData();

				if (list.size() > 0) {
					request.setAttribute("crawledData", list);
				} else {
					error = "Resource not found.";
					request.setAttribute("error", error);
				}
			} catch (Exception e) {
				e.printStackTrace();
				error = "Database Error.";
				request.setAttribute("error", error);
			}
			doGet(request, response);
		}

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String operation = request.getParameter("operation");
		URLModel urlModel = new URLModel();
		RequestDispatcher rd = null;

		if ("UpdatePageRank".equals(operation)) {
			try {

				int id = Integer.parseInt(request.getParameter("id"));

				System.out.println("in updaterank " + id);

				URLModel dbModel = urlModel.findByPK(id);

				int pageRank = dbModel.getPageRank();
				pageRank = pageRank + 1;
				System.out.println("pagerank in ctl " + pageRank);

				urlModel.setId(dbModel.getId());
				urlModel.setCrawlingWord(dbModel.getCrawlingWord());
				urlModel.setUrl(dbModel.getUrl());
				urlModel.setPageRank(pageRank);
				urlModel.setUser_id(dbModel.getUser_id());

				urlModel.update();
				response.sendRedirect(dbModel.getUrl());
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Database Error.");
				rd = request.getRequestDispatcher("Crawler.jsp");
			}
		}

		rd = request.getRequestDispatcher("Crawler.jsp");
		rd.forward(request, response);
	}

}
