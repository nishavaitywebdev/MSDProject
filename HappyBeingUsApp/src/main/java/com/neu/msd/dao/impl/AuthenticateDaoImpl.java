	/**
 * 
 */
package com.neu.msd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.mysql.jdbc.Statement;
import com.neu.msd.dao.AuthenticateDao;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Mother;
import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;

/**
 * @author Harsh
 *
 */
public class AuthenticateDaoImpl implements AuthenticateDao {
	
	@Autowired
	DataSource dataSource;

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AuthenticateDao#registerDaughter(com.neu.msd.entities.DaughterRegistration)
	 */
	public int registerDaughter(Daughter daughter) throws AuthenticationException {
			
		try {
			Connection connection = dataSource.getConnection();
			String sql = "insert into user (user_type_id, first_name, last_name, birthdate, email_id, parent_id, is_diagnostic_taken) "
					+ " values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, 3);
			stmt.setString(2, daughter.getFirstName());
			stmt.setString(3, daughter.getLastName());
			stmt.setDate(4, new java.sql.Date(daughter.getBirthdate().getTime()));
			stmt.setString(5, daughter.getEmail());
			stmt.setInt(6, daughter.getMother().getId());
			stmt.setBoolean(7, false);
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records inserted: "+records);
			
			ResultSet keys = stmt.getGeneratedKeys();
			
			if (keys.next()) {
				return keys.getInt(1);
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AuthenticateDao#getMotherByEmail(java.lang.String)
	 */
	public Mother getMotherByEmail(String motherEmail) throws AuthenticationException {

		try {
			Mother mother = new Mother();
			Connection connection = dataSource.getConnection();
			String sql = "select * from user where email_id=?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, motherEmail);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				mother.setId(rs.getInt("user_id"));
				mother.getUserType().setId(rs.getInt("user_type_id"));
				mother.setFirstName(rs.getString("first_name"));
				mother.setLastName(rs.getString("last_name"));
				mother.setBirthdate(rs.getDate("birthdate"));
				mother.setEmail(rs.getString("email_id"));
			}
			
			return mother;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
	}

	public Mother createMotherWithEmail(String email) throws AuthenticationException {
		
		try {
			Connection connection = dataSource.getConnection();
			String sql = "insert into user (user_type_id, email_id) "
					+ " values (?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, 2);
			stmt.setString(2, email);
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records inserted: "+records);
			
			ResultSet keys = stmt.getGeneratedKeys();
			
			Mother mother = new Mother();
			mother.setId(keys.getInt("user_id"));
			mother.setEmail(email);
			mother.getUserType().setId(keys.getInt("user_type_id"));
			
			return mother;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
	}

	public int registerDaughterAuthentication(int daughterId, DaughterRegistration daughterRegistration) throws AuthenticationException {

		try {
			Connection connection = dataSource.getConnection();
			String sql = "insert into user_authentication (user_id, username, password, user_type_id) "
					+ " values (?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, daughterId);
			stmt.setString(2, daughterRegistration.getUsername());
			stmt.setString(3, daughterRegistration.getPassword());
			stmt.setInt(4, 3);
			
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records inserted: "+records);
			
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
	
	}
	public int validUser(String username, String password) throws AuthenticationException {
		try {
			int id;
			Connection connection = dataSource.getConnection();
			String sql = "select user_id from user_authentication where username=? and password=?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()==true){
			id=rs.getInt("user_id");
			return id;
			}
			else
				return -1;	
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
	}
}
