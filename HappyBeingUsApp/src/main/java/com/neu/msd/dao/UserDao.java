/**
 * 
 */
package com.neu.msd.dao;

import java.util.List;

import com.neu.msd.entities.Activity;
import com.neu.msd.exception.UserException;

/**
 * @author Harsh
 *
 */
public interface UserDao {

	int getDiagnosticType() throws UserException;

	List<Activity> getActivitiesByType(int diagnosticType) throws UserException;

}
