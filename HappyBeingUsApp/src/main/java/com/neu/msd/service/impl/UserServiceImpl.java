/**
 * 
 */
package com.neu.msd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.msd.dao.UserDao;
import com.neu.msd.dao.AdminDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.UserService;

/**
 * @author Harsh
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AdminDao adminDao;

	public List<AdminActivityAnswer> getDiagnosticQuestions() throws UserException, AdminException {
		int diagnosticType = userDao.getDiagnosticType();
		List<Activity> activities = userDao.getActivitiesByType(diagnosticType);
		List<AdminActivityAnswer> adminActivityAnswers = new ArrayList<AdminActivityAnswer>();
		for(Activity activity : activities){
			AdminActivityAnswer adminActivityAnswer = adminDao.getAdminActivityAnswerByActivityId(activity.getId());
			List<Answer> answers = adminActivityAnswer.getAnswers();
			for(Answer answer : answers){
				String answerText = "";//userDao.getAnswerTextById(answer.getId());
				answer.setAnswerText(answerText);
			}
			adminActivityAnswer.setActivity(activity);
			adminActivityAnswers.add(adminActivityAnswer);
		}
		return adminActivityAnswers;
	}

}
