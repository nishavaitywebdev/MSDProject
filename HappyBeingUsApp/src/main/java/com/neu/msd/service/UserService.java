/**
 * 
 */
package com.neu.msd.service;

import java.sql.SQLException;
import java.util.List;

import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;

/**
 * @author HP
 *
 */
public interface UserService {

	List<AdminActivityAnswer> getDiagnosticQuestions() throws UserException, AdminException;

	void addscore(User user, double score);

	Integer[] getweigh() throws SQLException;

}
