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
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.neu.msd.dao.AuthenticateDao;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Mother;
import com.neu.msd.entities.MotherRegistration;
import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;

/**
 * @author Harsh
 *
 */
@Repository("authenticateDao")
public class AuthenticateDaoImpl implements AuthenticateDao {
	
	@Autowired
	DataSource dataSource;

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AuthenticateDao#registerDaughter(com.neu.msd.entities.DaughterRegistration)
	 */
	public int registerDaughter(Daughter daughter) throws AuthenticationException {
		
		try {
			Connection connection = dataSource.getConnection();
			
			int nextUserId = getNextUserId();
			
			String sql = "insert into user (user_id, user_type_id, first_name, last_name, birthdate, email_id, parent_id, is_diagnostic_taken) "
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, nextUserId);
			stmt.setInt(2, 3);
			stmt.setString(3, daughter.getFirstName());
			stmt.setString(4, daughter.getLastName());
			stmt.setDate(5, new java.sql.Date(daughter.getBirthdate().getTime()));
			stmt.setString(6, daughter.getEmail());
			stmt.setInt(7, daughter.getMother().getId());
			stmt.setBoolean(8, false);
			
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

	private int getNextUserId() throws AuthenticationException{
		try {
			Connection connection = dataSource.getConnection();
			
			String sql = "SELECT MAX(USER_ID) AS USER_ID FROM USER";

			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) + 1;
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
			int nextUserId = getNextUserId();
			String sql = "insert into user (user_id, user_type_id, email_id) "
					+ " values (?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, nextUserId);
			stmt.setInt(2, 2);
			stmt.setString(3, email);
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records inserted: "+records);
			
			ResultSet keys = stmt.getGeneratedKeys();
			
			Mother mother = new Mother();
//			mother.setId(keys.getInt("user_id"));
			mother.setId(nextUserId);
			mother.setEmail(email);
			mother.getUserType().setId(2);
			
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

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AuthenticateDao#updateMotherDetails(com.neu.msd.entities.MotherRegistration)
	 */
	public int updateMotherDetails(MotherRegistration motherRegistration) throws AuthenticationException {
		try {
			Connection connection = dataSource.getConnection();
			String sql = "update user set first_name = ?, last_name = ? where user_id = ?";
					
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, motherRegistration.getMother().getFirstName());
			stmt.setString(2, motherRegistration.getMother().getLastName());
			stmt.setInt(3, motherRegistration.getMother().getId());

			int records = stmt.executeUpdate();
			System.out.println("No. of records updated in the user table: "+records);
			
			String sqlUpdateUserAuth = "insert into user_authentication(user_id, username, password, user_type_id) "
					+ "values (?, ?, ?, ?)";
			
			PreparedStatement stmtUpdateUserAuth = connection.prepareStatement(sqlUpdateUserAuth, Statement.RETURN_GENERATED_KEYS);
			
			stmtUpdateUserAuth.setInt(1, motherRegistration.getMother().getId());
			stmtUpdateUserAuth.setString(2, motherRegistration.getUsername());
			stmtUpdateUserAuth.setString(3, motherRegistration.getPassword());
			stmtUpdateUserAuth.setInt(4, 2);
			
			records = stmtUpdateUserAuth.executeUpdate();
			
			System.out.println("No. of records inserted in the user_authentication table: "+records);
			return records;
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
		
	}

}
