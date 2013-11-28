package com.sunrays.javarefbook.ctl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sunrays.javarefbook.model.CrawlerModel;
import com.sunrays.javarefbook.model.URLModel;
import com.sunrays.javarefbook.model.UserModel;

/**
 * Servlet Controller class to register a user
 * 
 * @author Sunil Sahu
 * 
 */
public class AddURLCtl extends HttpServlet {

	/**
	 * Check is string value is not null or empty
	 */

	private int userId = 0;
	private int urlId = 0;

	private boolean isNull(String val) {
		return val == null || val.length() == 0;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("AddURL.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String url = request.getParameter("url");
		String title = request.getParameter("title");
		String keyword = request.getParameter("keyword");
		String description = request.getParameter("description");
		String operation = request.getParameter("operation");

		ArrayList errors = new ArrayList();

		if (isNull(url)) {
			errors.add("URL can't be null.");
		}
		if (isNull(title)) {
			errors.add("Title can't be null.");
		}
		if (isNull(keyword)) {
			errors.add("Keyword can't be null.");
		}
		if (isNull(description)) {
			errors.add("Description can't be null.");
		}

		URLModel urlModel = new URLModel();

		RequestDispatcher rd = null;

		if ("Add URL".equals(operation)) {
			if (errors.size() > 0) {
				request.setAttribute("error", errors);
				doGet(request, response);
			} else {

				// Add new user in the Database
				try {
					UserModel userModel = (UserModel) session
							.getAttribute("user");
					this.userId = userModel.getId();

					if (!url.startsWith("http://")) {
						url = "http://" + url;
					}

					urlModel.setId(urlModel.nextPK());
					this.urlId = urlModel.getId();
					urlModel.setCrawlingWord(keyword.trim());
					urlModel.setPageRank(0);
					urlModel.setUser_id(userId);
					urlModel.setUrl(url.trim());
					urlModel.add();

					CrawlerModel crawlerModel = new CrawlerModel();

					String[] keywords = keyword.split(",");
					for (int i = 0; i < keywords.length; i++) {
						crawlerModel.setId(crawlerModel.nextPK());
						crawlerModel.setTitle(title.trim());
						crawlerModel.setKeyword(keywords[i].trim());
						crawlerModel.setDescription(description.trim());
						crawlerModel.setUrl_id(urlId);
						crawlerModel.add();
					}

					System.out.println("Url Model id " + urlModel.getId()
							+ " UserId " + userModel.getId());

					keywords = keyword.split(",");

					for (int i = 0; i < keywords.length; i++) {
						keyword = keywords[i].replace(" ", "+");
						System.out
								.println("...................in keywords loop.................."
										+ keyword.trim());
						crawlerAndStore(keyword.trim());
					}

					request.setAttribute("message",
							"Congratulation! Your URL is successfully added into Database.");

					doGet(request, response);

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

	public void crawlerAndStore(String keyword) {
		Document doc;

		try {

			CrawlerModel crawlerModel = null;
			URLModel urlModel = new URLModel();

			List urlList = new ArrayList();

			doc = Jsoup.connect(
					"http://www.bing.com/search?q=" + keyword
							+ "&go=&qs=n&form=QBRE&pq=" + keyword
							+ "&sc=0-0&sp=-1&sk=").get();

			System.out.println("title : " + doc.title());

			Elements links = doc.select("a[href]");
			for (Element link : links) {

				String string = link.attr("href");

				if (string.startsWith("http://")) {
					urlList.add(string);
					// System.out.println("\nlink : " + string);
					// System.out.println("text : " + link.text());
				}
			}

			System.out.println("UrlList size " + urlList.size());

			urlList = getUnique(urlList);

			System.out.println("UrlList size unique " + urlList.size());

			long startTime = new Date().getTime();

			long endTime = 0;
			long diffTime = 60000;

			for (int ul = 0; ul < urlList.size(); ul++) {

				endTime = new Date().getTime();

				System.out.println("Time " + startTime + " " + endTime + " "
						+ (endTime - startTime));

				if ((endTime - startTime) > diffTime) {
					break;
				}

				String myurl = (String) urlList.get(ul);

				boolean b = urlModel.checkUrl(myurl);

				System.out.println("boolean value in ctl.............." + b);

				if (!b) {

					urlModel.setId(urlModel.nextPK());
					this.urlId = urlModel.getId();
					keyword = keyword.replace("+", " ");
					urlModel.setCrawlingWord(keyword.trim());
					urlModel.setUrl(myurl.trim());
					urlModel.setPageRank(0);
					urlModel.setUser_id(userId);
					urlModel.add();

					InputStreamReader in = null;

					BufferedReader bufferedReader = null;

					String line = null;

					StringBuffer buffer = new StringBuffer();

					List<String> mainList = new ArrayList<String>();
					List<String> descList = new ArrayList<String>();

					StringBuffer titleBuffer = new StringBuffer();

					try {
						URL url = new URL(myurl);

						URLConnection urlConn = url.openConnection();

						if (urlConn != null)
							urlConn.setReadTimeout(60 * 1000);
						if (urlConn != null && urlConn.getInputStream() != null) {
							in = new InputStreamReader(
									urlConn.getInputStream(),
									Charset.defaultCharset());
							bufferedReader = new BufferedReader(in);

							while ((line = bufferedReader.readLine()
									.toLowerCase()) != null) {

								if (line.contains("<title>")
										&& line.contains("</title>")) {
									titleBuffer.append(line);
								}

								if (line.contains("<meta")
										&& line.contains(">")) {
									System.err.println("in meta............."
											+ line);
									mainList.add(line);
									buffer.append(line);
									buffer.append("\n");

									if (line.contains("description")) {
										System.err.println("Desc.........."
												+ line);
										descList.add(line);
									}
								}
							}
							bufferedReader.close();
						}
						in.close();
					} catch (Exception e) {
						System.out
								.println("...................in catch........................");

						// e.printStackTrace();

						String string = buffer.toString();

						// System.out.println("string meta....." + string);

						String title = titleBuffer.toString();

						if (title == null) {
							title = myurl;
						} else {
							title = title.replaceAll("<title>", "");
							title = title.replaceAll("</title>", "");
							title = title.replaceAll("<TITLE>", "");
							title = title.replaceAll("</TITLE>", "");
							title = title.toUpperCase();
						}

						// System.out.println(title);

						List descIndex = new ArrayList();
						List descendTagIndex = new ArrayList();

						List contentIndex = new ArrayList();
						List endTagIndex = new ArrayList();

						List<String> uniqueList = new ArrayList<String>();

						List<String> descUniqueList = new ArrayList<String>();

						StringBuffer mainBuffer = new StringBuffer();

						StringBuffer descBuffer = new StringBuffer();

						for (int i = 0; i < mainList.size(); i++) {
							string = mainList.get(i);
							if (string.contains("content=")
									&& string.contains(">")) {
								System.out.println(string.indexOf("content=")
										+ "	 " + string.indexOf("/>"));
								contentIndex.add(string.indexOf("content="));
								endTagIndex.add(string.indexOf(">"));
							}
						}

						for (int i = 0; i < descList.size(); i++) {
							string = descList.get(i);
							if (string.contains("content=")
									&& string.contains(">")) {
								System.out.println(string.indexOf("content=")
										+ " " + string.indexOf("/>"));
								descIndex.add(string.indexOf("content="));
								descendTagIndex.add(string.indexOf(">"));
							}
						}

						for (int i = 0; i < contentIndex.size(); i++) {

							String s = mainList.get(i);

							for (int j = Integer.valueOf(contentIndex.get(i)
									.toString()) + 9; j < Integer
									.valueOf(endTagIndex.get(i).toString()) - 3; j++) {
								try {
									mainBuffer.append(s.charAt(j));
								} catch (IndexOutOfBoundsException de) {
									j++;
								}
							}

							System.out
									.println("Main Buffer....................."
											+ mainBuffer);

							uniqueList.add(mainBuffer.toString());

							mainBuffer.delete(0, Integer.valueOf(endTagIndex
									.get(i).toString()) - 3);
							// System.out.println();

						}

						for (int i = 0; i < descIndex.size(); i++) {

							String s = descList.get(i);

							for (int j = Integer.valueOf(descIndex.get(i)
									.toString()) + 9; j < Integer
									.valueOf(descendTagIndex.get(i).toString()) - 3; j++) {

								descBuffer.append(s.charAt(j));

							}

							System.err.println("Desc Buffer........"
									+ descBuffer);

							descUniqueList.add(descBuffer.toString());

							descBuffer.delete(0,
									Integer.valueOf(descendTagIndex.get(i)
											.toString()) - 3);
							// System.out.println();

						}

						uniqueList = getUnique(uniqueList);

						descUniqueList = getUnique(descUniqueList);

						for (String string2 : uniqueList) {
							System.err.println(string2);
							mainBuffer.append(string2);
							mainBuffer.append(", ");
						}

						for (String string2 : descUniqueList) {
							System.err.println(string2);
							descBuffer.append(string2);
							descBuffer.append(", ");
						}

						String description = descBuffer.toString();

						String originalText = mainBuffer.toString();

						System.out.println(originalText);

						originalText = mainBuffer.toString().replace(", ", ",");

						System.err.println("Original text................"
								+ originalText);

						crawlerModel = new CrawlerModel();

						System.out
								.println("..................maxid in url..........."
										+ crawlerModel.nextPK());

						if (description.equals("")) {
							description = myurl;
						}

						crawlerModel.setDescription(description.trim());

						if (title.equals("")) {
							title = myurl;
						}

						crawlerModel.setTitle(title.trim());
						crawlerModel.setUrl_id(urlId);

						StringTokenizer stringTokenizer = new StringTokenizer(
								originalText, ",");

						while (stringTokenizer.hasMoreElements()) {
							System.out
									.println("................in unltimate loop..............");
							String keywords = (String) stringTokenizer
									.nextElement();
							crawlerModel.setId(crawlerModel.nextPK());
							crawlerModel.setKeyword(keywords.toLowerCase()
									.trim());
							try {
								crawlerModel.add();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				} else {
					ul++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List getUnique(List list) {

		Set<String> set = new HashSet<String>();

		Iterator iterator = list.iterator();

		while (iterator.hasNext()) {
			String str = (String) iterator.next();
			set.add(str);
		}

		list.clear();

		iterator = set.iterator();

		while (iterator.hasNext()) {
			String str = (String) iterator.next();
			list.add(str);
		}

		return list;
	}

}
