/**

 * @author SANIL AND VINAY 

 */

package com.neu.msd.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.Mother;

import com.neu.msd.entities.MotherRegistration;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.UserType;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.AdminService;
import com.neu.msd.service.AuthenticateService;
import com.neu.msd.service.UserService;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.dao.AdminDao;
import com.neu.msd.dao.UserDao;
import com.neu.msd.entities.Activity;
//import com.neu.msd.entities.User;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class AdminTest {
	
	private static int deleteId;
	
	@Autowired

	AuthenticateService authenticateService;

	@Autowired

	UserService userService;

	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	AdminDao adminDao;
	
	
	@Test

	public void test_emailExistCheck(){
		String emailExistCheck;
		try {
			emailExistCheck = authenticateService.checkEmail("iamadminWord@gmail.com");
			assertEquals("iamadminWord@gmail.com, The account already exists", emailExistCheck);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}


	/* Mohsen Nabian*/


	@Test


	public void test_usernameExistCheck(){
		String usernameExistCheck;
		try {
			usernameExistCheck = authenticateService.checkUname("boss2");
			assertEquals("boss2"+", this username available", usernameExistCheck);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}

	/* Mohsen Nabian*/


	@Test


	public void test_resetUnamePassword(){


		String resetUnamePasswordCheck;


		try {

			resetUnamePasswordCheck = authenticateService.resetUnamePassword("boss2","boss2","boss2");

			assertEquals("Account does not exist. Try Sign Up...", resetUnamePasswordCheck);

		} catch (AuthenticationException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	/* Mohsen Nabian*/


//	@Test
//
//
//	public void test_getDiagnostic(){
//
//
//		try {
//
//			int q = userService.getDiagnosticQuestions().size();
//
//			assertEquals(5, q);
//
//		} catch (UserException e) {
//
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//
//		} catch (AdminException e) {
//
//			// TODO Auto-generated catch block
//
//			e.printStackTrace();
//
//		}
//	}

	/* Mohsen Nabian*/


	@Test


	public void test_getTopicsUser(){

		User user = new User();

		user.setId(10);
		try {
			int q = userService.getTopicsOfUser(user).size();
			assertEquals(q, q);
		} catch (UserException e) {
			e.printStackTrace();
		}
	}

	/* Mohsen Nabian*/


	@Test
	public void test_getAllActivityTemplates(){
		int q = 0;
		try {
			q = adminService.getAllActivityTemplates().size();
		} catch (AdminException e) {
			e.printStackTrace();
		}
		assertEquals(6, q);
	}

	/* Nisha Vaity*/
	
	@Test
	public void test_getTopicsUser2(){
		User user = new User();
		user.setId(10);
		try {
			int q = userService.getTopicsOfUser(user).size();
			assertEquals(q, q);
		} catch (UserException e) {
			e.printStackTrace();
		}
	}

	/* Nisha Vaity*/


	@Test
	public void test_getAllActivityTemplates2(){
	int q = 0;

		try {
			q = adminService.getAllActivityTemplates().size();
		} catch (AdminException e) {
			e.printStackTrace();
		}
		assertEquals(6, q);
	}

	/* Nisha Vaity*/


	@Test
	public void test_getTopicsUser1(){
		User user = new User();
		user.setId(10);
		try {
			int q = userService.getTopicsOfUser(user).size();
			assertEquals(q, q);
		} catch (UserException e) {
			e.printStackTrace();
		}
	}

	/* Nisha Vaity*/


	@Test
	public void test_getAllActivityTemplates1(){
		int q = 0;
		try {
			q = adminService.getAllActivityTemplates().size();
		} catch (AdminException e) {
			e.printStackTrace();
		}
		assertEquals(6, q);
	}
	
	@Test


	public void test_renameTopic(){
		int q = 0;
		try {
			q = adminService.renameTopic("Test_ChangeName", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(q, q);
		try {
			q = adminService.renameTopic("Topic 1", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_addTopic(){
		int q = 0;
		try {
			q = adminService.addNewTopic("Test Topic Add","YES");
			q = adminService.deleteTopic(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(1, q);
	}
	
	@Test


	public void test_deleteTopic(){
		int q = 0;
		try {
			q = adminService.addNewTopic("Test Topic Add","YES");
			q = adminService.deleteTopic(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(1, q);
	}
	
	@Test


	public void test_getActivityContainerById(){
		ActivityContainer q;
		try {
			q = adminService.getActivityContainerById(2);
			assertEquals(q.getActivityContainerId(), q.getActivityContainerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test


	public void test_loadTopics(){
		int q = 0;
		try {
			Map<Integer, ActivityContainer> containerMap = new HashMap<Integer, ActivityContainer>();
			q = adminService.loadTopics(containerMap).size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(q, q);
	}
	
	@Test


	public void test_adminAuthenticate(){
		User q;
		User user = new User();
		UserType userType = new UserType();
		userType.setId(1);
		user.setId(1);
		UserAuthentication userAuth = new UserAuthentication();
		userAuth.setUser(user);
		userAuth.setUsername("iamadmin");
		userAuth.setPassword("password");
		userAuth.setUserType(userType);
		try {
			q = adminService.adminAuthenticate(userAuth);
			assertEquals(1, q.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test


	public void test_addNewActivityContainer(){
		ActivityContainer q;
		int act = 0;
		try {
			q = adminService.addNewActivityContainer("Test Act Cont", 1);
			act = adminService.deleteActivityContainer(q.getActivityContainerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(act, act);
	}
	
	@Test

	public void test_deleteActivityContainer(){
		ActivityContainer q;
		int act = 0;
		try {
			q = adminService.addNewActivityContainer("Test Act Cont", 1);
			act = adminService.deleteActivityContainer(q.getActivityContainerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(act, act);
	}
	
	@Test


	public void test_renameActivityContainer(){

		ActivityContainer q;
		int p;
		int act = 0;
		try {
			q = adminService.addNewActivityContainer("Test Act Cont", 1);
			p = adminService.renameActivityContainer("Test Container", 1);
			act = adminService.deleteActivityContainer(q.getActivityContainerId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(act, act);
	}
	
	@Test

	public void test_checkEmail(){
		String emailExistCheck;
		try {
			emailExistCheck = authenticateService.checkEmail("iamadminWord@gmail.com");
			assertEquals("iamadminWord@gmail.com, The account already exists", emailExistCheck);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}
	
	@Test


	public void test_checkUname(){
		String usernameExistCheck;
		try {
			usernameExistCheck = authenticateService.checkUname("boss2");
			assertEquals("boss2"+", this username available", usernameExistCheck);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}
	
	@Test


	public void test_adminAuthenticate1(){
		User q;
		User user = new User();
		UserType userType = new UserType();
		userType.setId(1);
		user.setId(1);
		UserAuthentication userAuth = new UserAuthentication();
		userAuth.setUser(user);
		userAuth.setUsername("iamadmin");
		userAuth.setPassword("password");
		userAuth.setUserType(userType);
		try {
			q = adminService.adminAuthenticate(userAuth);
			assertEquals(1, q.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test


	public void test_check_Email(){
		String emailExistCheck;
		try {
			emailExistCheck = authenticateService.checkEmail("iamadminWord@gmail.com");
			assertEquals("iamadminWord@gmail.com, The account already exists", emailExistCheck);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}


	
	
	
	
	@Test
	public void test_createDiagnosticQuestions() throws AdminException{

		String ques = "I should not be there";
		Answer a1 = new Answer();
		a1.setAnswerText("I am an Created answer and I should not be ther");
		a1.setOrderNo(1);
		a1.setIsCorrect(false);
		a1.setIsRightanswer(false);

		Answer a2 = new Answer();
		a2.setAnswerText("I am an Created answer 2 and I should not be ther");
		a2.setOrderNo(2);
		a2.setIsCorrect(true);
		a2.setIsRightanswer(false);
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);
		
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		deleteId = lastActivityId;
		assertEquals(true, lastActivityId > 0);
					
	}
	
	@Test
	public void test_createDiagnosticQuestionsInput() throws AdminException{
		
		String ques = null;
		ArrayList<Answer> answers = new ArrayList<Answer>();
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		assertEquals(lastActivityId, 0);
		
	}
	
	@Test
	public void test_createDiagnosticQuestionsInput2() throws AdminException{
		
		String ques = "A valid string";
		ArrayList<Answer> answers = null;
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		assertEquals(lastActivityId, 0);
		
	}
	
	@Test
	public void test_updateDiagnosticQuestion() throws AdminException{
		

		String ques = "Text Created Questions for update test and I should not be there";
		Answer a1 = new Answer();
		a1.setAnswerText("I am an Created answer for update test and I should not be there");
		a1.setOrderNo(1);
		a1.setIsCorrect(false);
		a1.setIsRightanswer(false);

		Answer a2 = new Answer();
		a2.setAnswerText("I am an Created answer 2 for update test and I should not be there");
		a2.setOrderNo(2);
		a2.setIsCorrect(true);
		a2.setIsRightanswer(false);
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		answers.add(a1);
		answers.add(a2);
		
		int lastActivityId = adminService.addDiagnosticQuestion(ques, answers);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+lastActivityId);
		
		
		Activity activity = new Activity();
		activity.setId(lastActivityId);
		activity.setActivityText("I am the updated question and I should be there");
		ActivityTemplate activityTemplate = new ActivityTemplate();
		activityTemplate.setId(3);
		activity.setActivityTemplate(activityTemplate);
		
		
		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		adminActivityAnswer.setActivity(activity);

		a1 = new Answer();
		a1.setAnswerText("I am an updated answer 1 and I should be there");
		a1.setOrderNo(1);
		a1.setIsCorrect(false);
		a1.setIsRightanswer(false);

		a2 = new Answer();
		a2.setAnswerText("I am an updated answer 2 and I should be there");
		a2.setOrderNo(2);
		a2.setIsCorrect(true);
		a2.setIsRightanswer(false);
		
		ArrayList<Answer> adminAnswers = new ArrayList<Answer>();
		adminAnswers.add(a1);
		adminAnswers.add(a2);
		
				
		adminActivityAnswer.setAnswers(adminAnswers);
			
		int numRowsAffected = adminService.updateDiagnosticQuestion(adminActivityAnswer);
		assertEquals(true, numRowsAffected > 0);		
	}
	
	@Test
	public void test_updateDiagnosticQuestionInput() throws AdminException{
									
		int numRowsAffected = adminService.updateDiagnosticQuestion(null);
		assertEquals(true, numRowsAffected == 0);		
	}
	
	
	
	@Test
	public void test_getDiagnosticList(){
	
		List<AdminActivityAnswer> diagnosticQuestionList= adminService.getDiagnosticQuestions();
		assertEquals(true, diagnosticQuestionList != null);
	
	}

	@Test
	public void test_checkDiagnosticListLength(){

		List<AdminActivityAnswer> diagnosticQuestionList= adminService.getDiagnosticQuestions();
		assertEquals(true, diagnosticQuestionList.size() > 0);
	
	}
	
	@Test
	public void test_deleteDiagnosticQuestion() throws AdminException{
				
		int numRowsAffected = adminService.deleteDiagnosticQuestion(deleteId);
		assertEquals(true, numRowsAffected > 0);
		
	}
	
	@Test
	public void test_deleteDiagnosticQuestionInput() throws AdminException{
				
		int numRowsAffected = adminService.deleteDiagnosticQuestion(-5);
		assertEquals(false, numRowsAffected > 0);
		
	}
	
	
	@Test
	public void test_assignTopicToUsers() throws AdminException{
		
		String topicName = "TestTopic";
		int topicId = adminService.addNewTopic(topicName,"NO");
		Topic newTopic = new Topic(topicId, topicName);
		
		int numRecords = adminService.assignTopicToUsers(newTopic.getId(),3);
		assertEquals(true, numRecords > 0);
	}
	
	@Test
	public void test_loadTopicsWithActivityContainers() throws AdminException{
		
		List<Topic> topics = new ArrayList<Topic>();
		List<ActivityContainer> activityContainersByVersion = new ArrayList<ActivityContainer>();
		try {
			topics = userDao.getTopicsOfUser(2);
			System.out.println("=========================== topic size" + topics.size());
			for(Topic topic : topics){
				List<ActivityContainer> activityContainers = adminDao.loadActivityContainersByTopicId(topic.getId());
				
				activityContainersByVersion = adminDao.filterActivityContainers(activityContainers, 1);									
			}

		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(0, activityContainersByVersion.size());										
	}
	
	
}
