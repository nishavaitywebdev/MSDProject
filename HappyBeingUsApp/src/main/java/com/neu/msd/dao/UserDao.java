/**
 * 
 */
package com.neu.msd.dao;

import java.sql.SQLException;
import java.util.List;

import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.exception.UserException;

/**
 * @author Harsh
 *
 */
public interface UserDao {

	int getDiagnosticType() throws UserException;

	List<Activity> getActivitiesByType(int diagnosticType) throws UserException;

	Answer getAnswerById(int answerId) throws UserException;

	List<Topic> getTopicsOfUser(int id) throws UserException;

	void addscoreforuser(User user, double score) throws UserException;

	Integer[] getweigh() throws SQLException, AuthenticationException;

	ActivityContainer setcontainer(int cId) throws SQLException;

	Topic settopic(int tId) throws SQLException;

}
