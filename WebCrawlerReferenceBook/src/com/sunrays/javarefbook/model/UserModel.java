package com.sunrays.javarefbook.model;

import java.util.Date;
import java.util.*;
import java.sql.*;

import com.sunrays.javarefbook.utility.DatabaseUtility;

public class UserModel {

	/**
	 * Model attributes mapped with relational table data.
	 */

	private Integer id = null;

	private String firstName = null;

	private String lastName = null;

	private String login = null;

	private String password = null;

	private Date dateOfBirth = null;

	private Integer roleId = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/**
	 * Ad a new user
	 * 
	 * @throws Exception
	 */
	public void add() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO USER_TABLE (ID, FIRST_NAME, LAST_NAME, LOGIN, PASSWORD, DATE_OF_BIRTH, ROLE_ID) values (?,?,?,?,?,?,?)");

		stmt.setInt(1, id);
		stmt.setString(2, firstName);
		stmt.setString(3, lastName);
		stmt.setString(4, login);
		stmt.setString(5, password);
		java.sql.Date date = new java.sql.Date(dateOfBirth.getTime());
		stmt.setDate(6, date);
		stmt.setInt(7, roleId);

		int i = stmt.executeUpdate();

		System.out.println("User is successfully added. ");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Update a User data
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception {
		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("UPDATE USER_TABLE SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DATE_OF_BIRTH=?,ROLE_ID=? WHERE ID=?");

		stmt.setString(1, firstName);
		stmt.setString(2, lastName);
		stmt.setString(3, login);
		stmt.setString(4, password);
		java.sql.Date date = new java.sql.Date(dateOfBirth.getTime());
		stmt.setDate(5, date);
		stmt.setInt(6, roleId);
		stmt.setInt(7, id);

		int i = stmt.executeUpdate();

		System.out.println("User is successfully Updated. ");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Delete a User
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM USER_TABLE WHERE ID = ?");

		stmt.setInt(1, id);

		int i = stmt.executeUpdate();

		System.out.println("User is successfully Deleted.");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Find user by Primary Key ID
	 * 
	 * @param id
	 * @return UserModel object
	 * @throws Exception
	 */
	public UserModel findByPK(Integer id) throws Exception {

		Connection conn = DatabaseUtility.openConnection();
		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM USER_TABLE WHERE ID = ?");

		stmt.setInt(1, id);

		ResultSet resultSet = stmt.executeQuery();

		UserModel model = null;

		while (resultSet.next()) {

			model = new UserModel();
			model.setId(resultSet.getInt(1));
			model.setFirstName(resultSet.getString(2));
			model.setLastName(resultSet.getString(3));
			model.setLogin(resultSet.getString(4));
			model.setPassword(resultSet.getString(5));
			model.setDateOfBirth(resultSet.getDate(6));
			model.setRoleId(resultSet.getInt(7));
		}

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return model;
	}

	/**
	 * Search user on the basis of First Name, Last Name, Login, Password and
	 * return List of of selected UserModel objects
	 * 
	 * @return List of UserModel objects
	 * @throws Exception
	 */
	public List search() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		StringBuffer searchQuery = new StringBuffer(
				"SELECT * FROM USER_TABLE WHERE 1=1 ");

		if (firstName != null && firstName.length() > 0) {
			searchQuery.append(" AND FIRST_NAME LIKE '" + firstName + "%' ");
		}

		if (lastName != null && lastName.length() > 0) {
			searchQuery.append(" AND LAST_NAME LIKE '" + lastName + "%' ");
		}

		if (login != null && login.length() > 0) {
			searchQuery.append(" AND LOGIN = '" + login + "' ");
		}

		if (password != null && password.length() > 0) {
			searchQuery.append(" AND PASSWORD = '" + password + "' ");
		}

		if (roleId != null && roleId > 0) {
			searchQuery.append(" AND ROLE_ID = '" + roleId + "' ");
		}

		List list = new ArrayList();

		// Create Statement
		Statement stmt = conn.createStatement();

		ResultSet resultSet = stmt.executeQuery(searchQuery.toString());

		while (resultSet.next()) {
			UserModel model = new UserModel();
			model.setId(resultSet.getInt(1));
			model.setFirstName(resultSet.getString(2));
			model.setLastName(resultSet.getString(3));
			model.setLogin(resultSet.getString(4));
			model.setPassword(resultSet.getString(5));
			model.setDateOfBirth(resultSet.getDate(6));
			model.setRoleId(resultSet.getInt(7));
			list.add(model);
		}
		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return list;
	}

	/**
	 * Check user credentials and authenticate user.
	 * 
	 * @param login
	 * @param password
	 * @return UserModel object of authenticated user.
	 * @throws Exception
	 */

	public UserModel authenticate() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM USER_TABLE WHERE LOGIN = ? AND PASSWORD = ?");

		stmt.setString(1, login);
		stmt.setString(2, password);

		ResultSet resultSet = stmt.executeQuery();

		UserModel model = null;

		if (resultSet.next()) {
			model = new UserModel();
			model.setId(resultSet.getInt(1));
			model.setFirstName(resultSet.getString(2));
			model.setLastName(resultSet.getString(3));
			model.setLogin(resultSet.getString(4));
			model.setPassword(resultSet.getString(5));
			model.setDateOfBirth(resultSet.getDate(6));
			model.setRoleId(resultSet.getInt(7));

		} else {
			System.out.println("Incorrect Username / Password.");
		}
		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return model;
	}

	/**
	 * Change User's password
	 * 
	 * @param oldPass
	 * @param newPass
	 * @throws Exception
	 */
	public void changePassword(String oldPass, String newPass) throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		List list = new ArrayList();

		// Create Statement
		PreparedStatement stmt = null;

		if (oldPass.equals(password)) {

			stmt = conn
					.prepareStatement("UPDATE USER_TABLE SET PASSWORD=? WHERE ID= ?");

			stmt.setString(1, newPass);
			stmt.setInt(2, id);

			int i = stmt.executeUpdate();

			System.out.println("Password is successfully changed.");

			// Close Statement
			stmt.close();

		} else {
			System.out.println("You are not authorized to change password.");
		}
		// Close Connection

		DatabaseUtility.closeConnection(conn);
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
				.prepareStatement("SELECT MAX(ID) FROM USER_TABLE");

		ResultSet resultSet = stmt.executeQuery();

		int nextPK = 1;

		if (resultSet.next()) {
			nextPK += resultSet.getInt(1);
		}

		// Close Statement
		stmt.close();
		// Close Connection
		DatabaseUtility.closeConnection(conn);

		return nextPK;
	}

}
