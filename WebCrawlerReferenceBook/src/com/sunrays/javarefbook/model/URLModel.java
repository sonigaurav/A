package com.sunrays.javarefbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sunrays.javarefbook.utility.DatabaseUtility;

public class URLModel {

	/**
	 * Model attributes mapped with relational table data.
	 */
	private Integer id = null;

	private String crawlingWord = null;

	private String url = null;

	private Integer pageRank = null;

	private Integer user_id = null;

	private CrawlerModel crawlerModel = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCrawlingWord() {
		return crawlingWord;
	}

	public void setCrawlingWord(String crawlingWord) {
		this.crawlingWord = crawlingWord;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getPageRank() {
		return pageRank;
	}

	public void setPageRank(Integer pageRank) {
		this.pageRank = pageRank;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public CrawlerModel getCrawlerModel() {
		return crawlerModel;
	}

	public void setCrawlerModel(CrawlerModel crawlerModel) {
		this.crawlerModel = crawlerModel;
	}

	/**
	 * Add a new URL
	 * 
	 * @throws Exception
	 */
	public void add() throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO URL_TABLE (ID, CRAWLING_WORD, URL, PAGERANK,USER_ID) VALUES(?,?,?,?,?)");

		stmt.setInt(1, id);
		stmt.setString(2, crawlingWord);
		stmt.setString(3, url);
		stmt.setInt(4, pageRank);
		stmt.setInt(5, user_id);

		int i = stmt.executeUpdate();

		System.out.println("URL is successfully added.");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Update a User Role data
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("UPDATE URL_TABLE SET CRAWLING_WORD=?,URL=?,PAGERANK=? WHERE ID=?");
		stmt.setString(1, crawlingWord);
		stmt.setString(2, url);
		stmt.setInt(3, pageRank);
		stmt.setInt(4, id);

		int i = stmt.executeUpdate();

		System.out.println("URL is successfully Updated. ");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Delete a URLModel
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM URL_TABLE WHERE ID = ?");

		stmt.setInt(1, id);

		int i = stmt.executeUpdate();

		System.out.println("URL is successfully Deleted. ");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Find User Role by Primary Key ID
	 * 
	 * @param id
	 * @return URLModel object
	 * @throws Exception
	 */
	public URLModel findByPK(Integer id) throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM URL_TABLE WHERE ID = ?");

		stmt.setInt(1, id);

		ResultSet resultSet = stmt.executeQuery();

		URLModel model = null;

		while (resultSet.next()) {
			model = new URLModel();
			model.setId(resultSet.getInt(1));
			model.setCrawlingWord(resultSet.getString(2));
			model.setUrl(resultSet.getString(3));
			model.setPageRank(resultSet.getInt(4));
		}

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return model;
	}

	/**
	 * Search User Role on the basis of User Role, User Id and return List of
	 * selected AuthorityModel objects
	 * 
	 * @return List of AuthorityModel objects
	 * @throws Exception
	 */
	public List search() throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		StringBuffer searchQuery = new StringBuffer(
				"SELECT * FROM URL_TABLE WHERE 1=1");

		if (crawlingWord != null && crawlingWord.length() > 0) {
			searchQuery.append(" AND CRAWLING_WORD LIKE '" + crawlingWord
					+ "%' ");
		}

		if (url != null && url.length() > 0) {
			searchQuery.append(" AND URL LIKE '" + url + "%' ");
		}

		if (user_id != null && user_id > 0) {
			searchQuery.append(" AND USER_ID = '" + user_id + "' ");
		}

		List list = new ArrayList();

		// Create Statement
		Statement stmt = conn.createStatement();

		ResultSet resultSet = stmt.executeQuery(searchQuery.toString());

		while (resultSet.next()) {
			URLModel model = new URLModel();
			model.setId(resultSet.getInt(1));
			model.setCrawlingWord(resultSet.getString(2));
			model.setUrl(resultSet.getString(3));
			model.setPageRank(resultSet.getInt(4));
			list.add(model);
		}
		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return list;
	}

	public List getUrls(int id, int role_Id) throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		String searchQuery = "SELECT UT.CRAWLING_WORD,UT.URL,UST.LOGIN FROM URL_TABLE USER_ID = '"
				+ id + "' ORDER BY USER_ID";
		System.out.println("in getURL " + id + " " + role_Id + " "
				+ searchQuery);
		if (role_Id == 1) {
			System.out.println("in getURL in if " + id + " " + role_Id);
			searchQuery = "SELECT CRAWLING_WORD,URL,USER_ID FROM URL_TABLE ORDER BY USER_ID";
		}

		List list = new ArrayList();

		// Create Statement
		Statement stmt = conn.createStatement();

		ResultSet resultSet = stmt.executeQuery(searchQuery);

		while (resultSet.next()) {
			URLModel model = new URLModel();
			model.setCrawlingWord(resultSet.getString(1));
			model.setUrl(resultSet.getString(2));
			model.setUser_id(resultSet.getInt(3));
			list.add(model);
		}
		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return list;
	}

	public boolean checkUrl(String url) throws Exception {
		System.out.println("Url in service checkURL  " + url);
		boolean flag = false;

		Connection conn = DatabaseUtility.openConnection();

		PreparedStatement preparedStatement = conn
				.prepareStatement("SELECT * FROM URL_TABLE WHERE URL = ?");
		preparedStatement.setString(1, url);

		ResultSet resultSet = preparedStatement.executeQuery();

		if (resultSet.next()) {
			flag = true;
		}

		System.out.println("in service value of boolean  " + flag);
		DatabaseUtility.closeConnection(conn);
		return flag;
	}

	/**
	 * Get Next primary key for a new record. It is used, in case of
	 * auto-generated primary key
	 * 
	 * @return Next available primary key.
	 * @throws Exception
	 */

	public int nextPK() throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("SELECT MAX(ID) FROM URL_TABLE");

		ResultSet resultSet = stmt.executeQuery();

		id = 1;

		if (resultSet.next()) {
			id += resultSet.getInt(1);
		}

		// Close Statement
		stmt.close();
		// Close Connection
		DatabaseUtility.closeConnection(conn);

		return id;
	}

}
