/**
 * 
 */
package com.neu.msd.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.msd.dao.AdminDao;
import com.neu.msd.dao.UserDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.AdminService;
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
	AdminService adminService;
	
	@Autowired
	AdminDao adminDao;

	public List<AdminActivityAnswer> getDiagnosticQuestions() throws UserException, AdminException {
		int diagnosticType = userDao.getDiagnosticType();
		List<Activity> activities = userDao.getActivitiesByType(diagnosticType);
		List<AdminActivityAnswer> adminActivityAnswers = new ArrayList<AdminActivityAnswer>();
		for(Activity activity : activities){
			AdminActivityAnswer adminActivityAnswer = adminDao.getAdminActivityAnswerByActivityId(activity.getId());
			List<Answer> answers = adminActivityAnswer.getAnswers();
			List<Answer> answers1 = new ArrayList<Answer>();
			for(Answer answer : answers){
				Answer answer1 = userDao.getAnswerById(answer.getId());
			    answers1.add(answer1);
			}
			
			
			
			Collections.sort(answers1,new SortByorder_answer());
			adminActivityAnswer.setActivity(activity);
		    adminActivityAnswer.setAnswers(answers1);
			adminActivityAnswers.add(adminActivityAnswer);
		}
		
		
		
		Collections.sort(adminActivityAnswers,new SortByorder());
		return adminActivityAnswers;
	}

	public List<Topic> getTopicsOfUser(User user) throws UserException {
		List<Topic> topics = new ArrayList<Topic>();
		topics = userDao.getTopicsOfUser(user.getId());
		try {
			adminService.loadTopicsWithActivityContainers(new HashMap<Integer, ActivityContainer>(), topics);
		} catch (AdminException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return topics;
	}


	@Override
	public void addscore(User user, double score) {
		userDao.addscoreforuser(user, score);
		
	}

	@Override
	public Integer[] getweigh() throws SQLException {
		// TODO Auto-generated method stub
		Integer[] weighList;
		try {
			weighList = userDao.getweigh();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   
		return weighList;
	}


}
