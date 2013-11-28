package com.sunrays.javarefbook.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sunrays.javarefbook.utility.DatabaseUtility;

public class RoleModel {

	/**
	 * Model attributes mapped with relational table data.
	 */

	private Integer id = null;

	private String role = null;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Ad a new User Role
	 * 
	 * @throws Exception
	 */
	public void add() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("INSERT INTO ROLE(ID, ROLE) values (?,?)");

		stmt.setInt(1, id);
		stmt.setString(2, role);

		int i = stmt.executeUpdate();

		System.out.println("User Role is successfully added. ");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Update a Role data
	 * 
	 * @throws Exception
	 */
	public void update() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("UPDATE ROLE SET ROLE=? WHERE ID=?");

		stmt.setString(1, role);
		stmt.setInt(2, id);

		int i = stmt.executeUpdate();

		System.out.println("User Role is successfully Updated. ");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Delete a User Role
	 * 
	 * @throws Exception
	 */
	public void delete() throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("DELETE FROM ROLE WHERE ID = ?");

		stmt.setInt(1, id);

		int i = stmt.executeUpdate();

		System.out.println("User Role is successfully Deleted.");

		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);
	}

	/**
	 * Find Role by Primary Key ID
	 * 
	 * @param id
	 * @return AuthorityModel object
	 * @throws Exception
	 */
	public RoleModel findByPK(Integer id) throws Exception {

		Connection conn = DatabaseUtility.openConnection();

		// Create Statement
		PreparedStatement stmt = conn
				.prepareStatement("SELECT * FROM ROLE WHERE ID = ?");

		stmt.setInt(1, id);

		ResultSet resultSet = stmt.executeQuery();

		RoleModel model = null;

		while (resultSet.next()) {

			model = new RoleModel();
			model.setId(resultSet.getInt(1));
			model.setRole(resultSet.getString(2));

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
				"SELECT * FROM ROLE WHERE 1=1 ");

		if (role != null && role.length() > 0) {
			searchQuery.append(" AND ROLE LIKE '" + role + "%' ");
		}

		List list = new ArrayList();

		// Create Statement
		Statement stmt = conn.createStatement();

		ResultSet resultSet = stmt.executeQuery(searchQuery.toString());

		while (resultSet.next()) {
			RoleModel model = new RoleModel();
			model.setId(resultSet.getInt(1));
			model.setRole(resultSet.getString(2));
			list.add(model);
		}
		// Close Statement & Connection
		stmt.close();
		DatabaseUtility.closeConnection(conn);

		return list;
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
				.prepareStatement("SELECT MAX(ID) FROM ROLE");

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
