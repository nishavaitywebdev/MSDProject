/**
 * 
 */
package com.neu.msd.service;

import java.util.List;

import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.exception.UserException;

/**
 * @author HP
 *
 */
public interface UserService {

	List<AdminActivityAnswer> getDiagnosticQuestions() throws UserException;

}
