package com.sunrays.javarefbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sunrays.javarefbook.utility.DatabaseUtility;

public class CrawlerModel {

	/**
	 * Model attributes mapped with relational table data.
	 */
	private Integer id = null;
	private String title = null;
	private String keyword = null;
	private String description = null;
	private Integer url_id = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUrl_id() {
		return url_id;
	}

	public void setUrl_id(Integer url_id) {
		this.url_id = url_id;
	}

	public void add() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		PreparedStatement preparedStatement = conn
				.prepareStatement("INSERT INTO CRAWLED_DATA(ID,TITLE,KEYWORD,DESCRIPTION,URL_ID) VALUES(?,?,?,?,?)");
		preparedStatement.setInt(1, id);
		preparedStatement.setString(2, title);
		preparedStatement.setString(3, keyword);
		preparedStatement.setString(4, description);
		preparedStatement.setInt(5, url_id);

		int i = preparedStatement.executeUpdate();

		System.out.println(i);

		preparedStatement.close();

		DatabaseUtility.closeConnection(conn);
	}

	public void search() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		PreparedStatement preparedStatement = conn
				.prepareStatement("INSERT INTO CRAWLED_DATA(ID,TITLE,KEYWORD,DESCRIPTION,URL_ID) VALUES(?,?,?,?,?)");
		preparedStatement.setInt(1, id);
		preparedStatement.setString(2, title);
		preparedStatement.setString(3, keyword);
		preparedStatement.setString(4, description);
		preparedStatement.setInt(5, url_id);

		int i = preparedStatement.executeUpdate();

		System.out.println(i);

		preparedStatement.close();

		DatabaseUtility.closeConnection(conn);
	}

	public List getCrawlingData() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		PreparedStatement preparedStatement = conn
				.prepareStatement("SELECT DISTINCT(UT.URL),UT.ID,CRD.TITLE,CRD.DESCRIPTION FROM CRAWLED_DATA CRD INNER JOIN URL_TABLE UT ON (UT.ID=CRD.URL_ID) WHERE CRD.KEYWORD LIKE ? OR UT.CRAWLING_WORD LIKE ? ORDER BY UT.PAGERANK DESC");

		preparedStatement.setString(1, keyword + "%");
		preparedStatement.setString(2, keyword + "%");

		ResultSet resultSet = preparedStatement.executeQuery();

		System.out.println("boolean value of execute() for crawling query "
				+ preparedStatement.execute());

		List list = new ArrayList();

		while (resultSet.next()) {
			URLModel model = new URLModel();
			model.setUrl(resultSet.getString(1));
			model.setId(resultSet.getInt(2));
			CrawlerModel crawlerModel = new CrawlerModel();
			crawlerModel.setTitle(resultSet.getString(3));
			crawlerModel.setDescription(resultSet.getString(4));
			model.setCrawlerModel(crawlerModel);
			list.add(model);
		}

		System.out.println(list.size() + " list size in service");

		DatabaseUtility.closeConnection(conn);

		return list;
	}

	public int nextPK() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("SELECT MAX(ID) FROM CRAWLED_DATA");

		ResultSet resultSet = stmt.executeQuery();

		int nextPK = 1;

		if (resultSet.next()) {
			nextPK += resultSet.getInt(1);
		}

		// Close Statement
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return nextPK;
	}

}
