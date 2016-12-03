/**
 *
 */
package com.neu.msd.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.UserType;
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.service.AdminService;
import com.neu.msd.service.AuthenticateService;

/**
 * @author Harsh
 *
 */
@Controller
public class AdminController {

	Logger LOGGER = Logger.getLogger(AdminController.class);

//	TODO renameActivityContainer.action

//	TODO avoid reinsertion of records on refresh from both the topic and activity container page.

//	TODO Design issue, need a back button from the container page to topics page.


	@Autowired
	private AdminService adminService;

	@Autowired
	private AuthenticateService authenticateService;

	@RequestMapping(value="/adminHome.action", method=RequestMethod.POST)
	public String adminLogin(@RequestParam("username") String username,
			@RequestParam("password") String password,
			HttpSession session, Model model){
		LOGGER.debug("AdminController: adminLogin: START");
		try {

			UserAuthentication userAuthentication = new UserAuthentication();
			userAuthentication.setUsername(username);
			userAuthentication.setPassword(password);
			model.addAttribute("userAuthentication", userAuthentication);
			User admin = adminService.adminAuthenticate(userAuthentication);
			if (admin.getId()!=0)
			{
				session.setAttribute("user", admin);
				System.out.println("The user admin after authentication is: "+admin.toString());
				UserAuthentication newAdminAuthentication = new UserAuthentication();
				model.addAttribute("newAdminAuthentication",newAdminAuthentication);
				return loadHome(session, model);

			}
			else
			{
				model.addAttribute("adminLogonErr", "false");
				return "adminLogon";
			}
		} catch (AdminException e) {
			return "errorPage";
		}finally{
			LOGGER.debug("AdminController: adminLogin: END");
		}
	}

	@RequestMapping(value="/addNewAdmin.action", method=RequestMethod.POST)
	public String registerAdmin(@ModelAttribute("newAdminAuthentication") UserAuthentication userAuthentication,
			Model model, HttpSession httpSession){
		try {

			System.out.println("Entering Admin Controller for Add New Admin: ");
			//System.out.println("UserAuthentication Object is: "+userAuthentication.toString());
			int uId = adminService.registerAdmin(userAuthentication);
			User user = userAuthentication.getUser();
			user.setId(uId);
			UserType userType = new UserType();
			userType.setId(1);
			user.setUserType(userType);
			user.setDiagnosticTaken(false);
			httpSession.setAttribute("user", user);
			
			return adminLogin(userAuthentication.getUsername(),
					userAuthentication.getPassword(),
					httpSession, model);

		} catch (AdminException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value="/adminLoadHome.action", method=RequestMethod.POST)
	public String loadHome(HttpSession session, Model model) throws AdminException{

		LOGGER.debug("AdminController: loadHome: START");
		// Here Topics fetched by previous team   
		List<Topic> topics = (List<Topic>) session.getAttribute("topics");
		if(null == topics){
			Map<Integer, ActivityContainer> containerMap = new HashMap<Integer, ActivityContainer>();
			topics = adminService.loadTopics(containerMap);
			List<Version> versions = adminService.loadAllVersion();
			session.setAttribute("topics", topics);
			session.setAttribute("containerMap", containerMap);
			session.setAttribute("versions", versions);
		}
		//Inside loadHome
		System.out.println("Inside Load Home ");
		for(Topic t:topics){
			System.out.println(t);
		}
		System.out.println("End inside load home");
		session.removeAttribute("activityContainer");
		LOGGER.debug("AdminController: loadHome: END");
		
		return "adminHomeRefactoredTest";
	}

	@RequestMapping(value="/editActivityContainer.action", method=RequestMethod.POST)
	public String loadContainerById(@RequestParam("id") int activityContainerId, HttpSession session){

		LOGGER.debug("AdminController: loadContainerById: START");

		ActivityContainer activityContainer = (ActivityContainer) session.getAttribute("activityContainer");
		if(null == activityContainer){
			Map<Integer, ActivityContainer> containerMap = (Map<Integer, ActivityContainer>) session.getAttribute("containerMap");
			try {
				if(null == containerMap){
					System.out.println("Could not find the container map on the session. Hence throwing exception.");
					throw new AdminException();
				}else{
					activityContainer = containerMap.get(activityContainerId);
					if(null == activityContainer){
						activityContainer = adminService.getActivityContainerById(Integer.valueOf(activityContainerId));
						containerMap.put(activityContainerId, activityContainer);
						session.setAttribute("containerMap", containerMap);
					}
				}
			} catch (AdminException e) {
				return "errorPage";
			}
			session.setAttribute("activityContainer", activityContainer);
		}
		LOGGER.debug("AdminController: loadContainerById: END");
		return "activityContainer";
	}

	@RequestMapping(value="/newActivityLink.action", method=RequestMethod.POST)
	public String goToNewActivity(@RequestParam("id") int activityContainerId, Model model){

		LOGGER.debug("AdminController: goToNewActivity: START");
		Activity activity = new Activity();
		ActivityType activityType = new ActivityType();
		activityType.setId(3);
		activity.setActivityType(activityType);
		activity.getActivityContainer().setActivityContainerId(Integer.valueOf(activityContainerId));

		try {
			List<ActivityTemplate> activityTemplates = adminService.getAllActivityTemplates();
			model.addAttribute("activityTemplates", activityTemplates);
			model.addAttribute("activity", activity);

			LOGGER.debug("AdminController: goToNewActivity: END");
			return "activity";
		} catch (AdminException e1) {
			return "errorPage";
		}
	}

	/**
	* The following three functions.
	* 1. adminLoadDiagnosticQuestions
	* 2. loadDiagnosticHome
	* 3. deleteDiagnosticQuestion
	*
	* @author  Mukul Bichkar
	* @version 1.0
	* @since   2016-09-11
	*/


	// Function added for loading diagnostic questions
	@RequestMapping(value="/adminDiagnostic.action", method=RequestMethod.GET)
	public String adminLoadDiagnosticQuestions(HttpSession session, Model model) throws AdminException{
		LOGGER.debug("AdminController: adminDiagnosticLoadQuestions: START");

				return loadDiagnosticHome(session,model);

			

	}

	@RequestMapping(value="/adminLoadDiagnostic.action", method=RequestMethod.POST)
	public String loadDiagnosticHome(HttpSession session, Model model) throws AdminException{

		LOGGER.debug("AdminController: loadDiagnosticHome: START");
		// Here Topics fetched by previous team
		List<AdminActivityAnswer> diagnosticQuestions = null;//(List<AdminActivityAnswer>) session.getAttribute("diagnosticQuestions");
		// If diagnostic questions are null
		if(diagnosticQuestions == null){

			diagnosticQuestions = adminService.getDiagnosticQuestions();
			session.setAttribute("diagnosticQuestions",diagnosticQuestions);

		}

		LOGGER.debug("AdminController: loadDiagnosticHome: END");
		return "diagnosticModule";
		
	}


	//Delete Diagnostic Questions
	@RequestMapping(value="/deleteDiagnosticQuestion.action", method=RequestMethod.POST)
	public String deleteDiagnosticQuestion(@RequestParam("deletableId") int deletableId, HttpSession session, Model model){


		System.out.println("Id to be deleted is: "+deletableId);
		LOGGER.debug("AdminController: deleteDiagnosticQuestion: START");

		List<AdminActivityAnswer> diagnosticQuestions = (List<AdminActivityAnswer>) session.getAttribute("diagnosticQuestions");
		List<AdminActivityAnswer> updateddiagnosticQuestions = new ArrayList<AdminActivityAnswer>();
		try {
			adminService.deleteDiagnosticQuestion(Integer.valueOf(deletableId));
			for(AdminActivityAnswer q : diagnosticQuestions){
				if(q.getActivity().getId() != deletableId)
					updateddiagnosticQuestions.add(q);
			}

			session.setAttribute("diagnosticQuestions", updateddiagnosticQuestions);

			LOGGER.debug("AdminController: deleteActivity: END");
			return loadDiagnosticHome(session, model);
		} catch (AdminException e) {
			return "errorPage";
		}


	}
	
	
	/**
	* The following three functions.
	* 1. adminLoadMotherModule
	* 2. adminLoadDaughterModule
	* 	
	* @author  Mukul Bichkar
	* @version 1.0
	* @since   2016-01-12
	*/

	
	//Adding new action for directing the admin to mother's module
	@RequestMapping(value="/mother.action", method=RequestMethod.GET)
	public String adminLoadMotherModule(HttpSession session, Model model) throws AdminException{
		LOGGER.debug("AdminController: adminMotherModule: START");
		//fetch already loaded topics from loadHome i.e. load all the topics
		List<Topic> alltopics = (List<Topic>) session.getAttribute("topics");
		//filter out the topics that belong to mother
		// Create a mother user
		User user = new User();
		user.setUserType(new UserType());
		user.getUserType().setId(2);
		List<Topic> motherTopics = adminService.filterTopicForUsers(alltopics, user);
		System.out.println("--------- Mother Topics ------------");
		for(Topic t:motherTopics){
			System.out.println(t);
		}
		System.out.println("---------------------");
		session.setAttribute("mothertopics", motherTopics); //Update the topics
		
		
		return "motherAdmin";



	}

	//Adding new action for directing the admin to mother's module
		@RequestMapping(value="/daughter.action", method=RequestMethod.GET)
		public String adminLoadDaughterModule(HttpSession session, Model model) throws AdminException{
			LOGGER.debug("AdminController: adminDaughterModule: START");
			//fetch already loaded topics from loadHome i.e. load all the topics
			List<Topic> alltopics = (List<Topic>) session.getAttribute("topics");
			//filter out the topics that belong to mother
			// Create a new user of type daughter
			User user = new User();
			user.setUserType(new UserType());
			user.getUserType().setId(3);
			List<Topic> daughterTopics = adminService.filterTopicForUsers(alltopics, user);
			System.out.println("--------- Daughter Topics ------------");
			for(Topic t:daughterTopics){
				System.out.println(t);
			}
			System.out.println("---------------------");
			session.setAttribute("daughtertopics", daughterTopics); //Update the topics
			
			return "daughterAdmin";



		}




	// Activities
	@RequestMapping(value="/addActivity.action", method=RequestMethod.POST)
	public String addNewActivity(@ModelAttribute("activity") Activity activity,
			@RequestParam(value="uploadFile",required=false) MultipartFile uploadFile,
			@RequestParam(value="card1File",required=false) MultipartFile card1File,
			@RequestParam(value="card2File",required=false) MultipartFile card2File,
			@RequestParam(value="card3File",required=false) MultipartFile card3File,
			Model model, HttpSession session, HttpServletRequest request){


		LOGGER.debug("AdminController: addNewActivity: START");
		Activity act = new Activity();

		try {
			if(activity.getActivityTemplate().getId() == 1){
				AdminActivityAnswer adminActivityAnswer = getAdminActivityAnswerForFile(activity, request, uploadFile, "video");
				act = adminService.saveAdminActivityAnswer(adminActivityAnswer).getActivity();
			}else if(activity.getActivityTemplate().getId() == 2){
				AdminActivityAnswer adminActivityAnswer = getAdminActivityAnswerForFile(activity, request, uploadFile, "image");
				act = adminService.saveAdminActivityAnswer(adminActivityAnswer).getActivity();
			}else if(activity.getActivityTemplate().getId() == 3){
				act = addMCQActivity(activity, request);
			}else if(activity.getActivityTemplate().getId() == 4 || activity.getActivityTemplate().getId() == 6){
				act = addInfoActivity(activity);
			}else if(activity.getActivityTemplate().getId() == 5){
				act = addFlipActivity(activity, request, card1File, card2File, card3File);
			}
		} catch (AdminException e) {
			return "errorPage";
		}

		ActivityContainer activityContainer = (ActivityContainer) session.getAttribute("activityContainer");
		Map<Integer, ActivityContainer> containerMap = (Map<Integer, ActivityContainer>) session.getAttribute("containerMap");
		if(null==activityContainer || null == containerMap)
			return "errorPage";

		List<Activity> activities = activityContainer.getActivities();
		activities.add(act);
		activityContainer.setActivities(activities);
		containerMap.put(activityContainer.getActivityContainerId(), activityContainer);

		session.setAttribute("activityContainer", activityContainer);
		session.setAttribute("containerMap", containerMap);

		LOGGER.debug(activity);

		LOGGER.debug("AdminController: addNewActivity: END");
		return loadContainerById(activityContainer.getActivityContainerId(), session);
	}

	private Activity addInfoActivity(Activity activity) throws AdminException{
		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		adminActivityAnswer.setActivity(activity);

		adminActivityAnswer = adminService.saveAdminActivityAnswer(adminActivityAnswer);

		return adminActivityAnswer.getActivity();
	}

	private Activity addFlipActivity(Activity activity, HttpServletRequest request,
			MultipartFile card1File, MultipartFile card2File, MultipartFile card3File) throws AdminException{


		List<Answer> answers = new ArrayList<Answer>();
		for(int i = 1; i<= 6; i=i+2){
			Answer frontCard = new Answer();
			frontCard.setAnswerText(request.getParameter("card"+i+"Front"));
			frontCard.setOrderNo(i);
			answers.add(frontCard);

			String imageUrl = null;
			if(i==1){
				imageUrl = adminService.generateFilePath(card1File, "image");
			}else if(i==3){
				imageUrl = adminService.generateFilePath(card2File, "image");
			}else if(i==5){
				imageUrl = adminService.generateFilePath(card3File, "image");
			}

			if(null != imageUrl){
				Answer imageFile = new Answer();
				imageFile.setAnswerText(imageUrl);
				imageFile.setOrderNo(i+1);
				answers.add(imageFile);

			}else{
				Answer backCard = new Answer();
				backCard.setAnswerText(request.getParameter("card"+(i+1)+"Back"));
				backCard.setOrderNo(i+1);
				answers.add(backCard);
			}

		}

		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);

		adminActivityAnswer = adminService.saveAdminActivityAnswer(adminActivityAnswer);

		return adminActivityAnswer.getActivity();
	}

	private AdminActivityAnswer getAdminActivityAnswerForFile(Activity activity, HttpServletRequest request,
			MultipartFile uploadFile, String fileType) throws AdminException {

		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		String idealAnswer = request.getParameter("idealAnswer").trim();

		adminActivityAnswer.setActivity(activity);
		Answer answer = new Answer();
		answer.setAnswerText(idealAnswer);
		answer.setOrderNo(2);
		adminActivityAnswer.getAnswers().add(answer);

		String imageUrl = adminService.generateFilePath(uploadFile, fileType);

		if(null != imageUrl){
			Answer imageFile = new Answer();
			imageFile.setAnswerText(imageUrl);
			imageFile.setOrderNo(1);

			adminActivityAnswer.getAnswers().add(imageFile);
		}

		return adminActivityAnswer;
	}

	

	private Activity addMCQActivity(Activity activity, HttpServletRequest request) throws AdminException {

		List<Answer> answers = getMCQAnswers(request);

		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);

		adminActivityAnswer = adminService.saveAdminActivityAnswer(adminActivityAnswer);

		return adminActivityAnswer.getActivity();
	}

	private List<Answer> getMCQAnswers(HttpServletRequest request) {
//		List<String> correctAnswers = new ArrayList<String>(Arrays.asList(request.getParameterValues("correctAnswer")));
		List<String> correctAnswers = new ArrayList<String>();
		if(request.getParameterValues("correctAnswer") != null)
		{
			correctAnswers = new ArrayList<String>(Arrays.asList(request.getParameterValues("correctAnswer")));
		}


		Enumeration<String> parameters = request.getParameterNames();
		List<Answer> answers = new ArrayList<Answer>();
		while(parameters.hasMoreElements()){
			String param = (String) parameters.nextElement();
			if(param.contains("option")){
				Answer answer = new Answer();
				answer.setAnswerText(request.getParameter(param).trim());
				answer.setOrderNo(Integer.valueOf(param.split("_")[1]));
				answer.setIsCorrect(correctAnswers.contains(param)?true:false);
				answers.add(answer);
			}
		}
		return answers;
	}
	//Done with replacement


	// ---------- Changes for diagnostic module begin here ----------

		// Add diagnostic question --> Neha

		@RequestMapping(value="/addDiagnosticQuestion.action", method=RequestMethod.POST)
		private String addDiagnosticQuestion(@RequestParam("Question") String questionString,
				HttpServletRequest request,HttpSession session, Model model) throws AdminException {

		List<Answer> answers = getMCQAnswers(request);

		adminService.addDiagnosticQuestion(questionString, answers);

		//List<AdminActivityAnswer> diagnosticQuestions = adminService.getDiagnosticQuestions();
		//session.setAttribute("diagnosticQuestions",diagnosticQuestions);

		return loadDiagnosticHome(session,model);
		}


		// Update diagnostic question --> Neha

		@RequestMapping(value="/editDiagnosticQuestion.action")
		public String goToEditDiagnosticQuestion(@RequestParam("id") String parameter, HttpSession session, Model model){

			LOGGER.debug("AdminController: goToEditDiagnosticQuestion: START");

			int activityId = Integer.valueOf(parameter);

			try {
				AdminActivityAnswer adminActivityAnswer = adminService.getAdminActivityAnswerByActivityId(activityId);

				model.addAttribute("adminActivity", adminActivityAnswer);

				LOGGER.debug(adminActivityAnswer);
				LOGGER.debug("AdminController: goToEditDiagnosticQuestion: END");
				return "updateDiagnosticQuestion";
			} catch (AdminException e) {
				return "errorPage";
			}

		}

		@RequestMapping(value="/updateDiagnosticQuestion.action", method=RequestMethod.POST)
		private String updateDiagnosticQuestion(@ModelAttribute("activity") AdminActivityAnswer adminActivityAnswer,
				HttpServletRequest request, HttpSession session, Model model) throws AdminException {

			//AdminActivityAnswer adminActAns = new AdminActivityAnswer();

			List<Answer> answers = getMCQAnswers(request);

			Activity act = adminActivityAnswer.getActivity();
			AdminActivityAnswer newAdminActivityAnswer = new AdminActivityAnswer();
			newAdminActivityAnswer.setActivity(act);
			newAdminActivityAnswer.setAnswers(answers);
			adminService.updateDiagnosticQuestion(newAdminActivityAnswer);

			return loadDiagnosticHome(session, model);
		}

		// This is just to redirect to test the create diagnostic questions page
		@RequestMapping(value="/redirectToAddDQ.action", method=RequestMethod.GET)
		public String redirectToDiagModule() throws AdminException{
			return "createDiagnosticQuestions";
		}


		// ---------- Changes for diagnostic module end here ----------


	@ResponseBody
	@RequestMapping(value="/renameTopic.action", method=RequestMethod.POST)
	public String renameTopic(@RequestParam("topicName") String topicName, @RequestParam("topicId") int topicId, HttpSession session){

		LOGGER.debug("AdminController: renameTopic: START");
		try {
			int returnVal = adminService.renameTopic(topicName, topicId);
			List<Topic> topics = (List<Topic>) session.getAttribute("topics");
			List<Topic> newTopics = new ArrayList<Topic>();
			for(Topic topic : topics){
				if(topic.getId() == topicId)
					topic.setTopicName(topicName);
				newTopics.add(topic);
			}
			session.setAttribute("topics", newTopics);

			LOGGER.debug("AdminController: renameTopic: END");
			return String.valueOf(returnVal);
		} catch (AdminException e) {
			return "error";
		}
	}

	@RequestMapping(value="/addNewTopic.action", method=RequestMethod.POST)
	public String addNewTopic(@RequestParam("topicName") String topicName, HttpServletRequest request, HttpSession session, Model model){

		LOGGER.debug("AdminController: addNewTopic: START");
		try {
			String versioning = request.getParameter("userId");
			System.out.println("Inside add new topic version id is: "+versioning);
			int topicId = adminService.addNewTopic(topicName,versioning);
			Topic topic = new Topic(topicId, topicName);
			//Changes start here
			Integer id = null;
			if(versioning.equals("YES"))
				id = 2;
			else
				id = 3;				
			adminService.assignTopicToUsers(topicId,id);
			// End
			List<Topic> topics = (List<Topic>) session.getAttribute("topics");
			topics.add(topic);
			System.out.println("Adding new topic in AdminController");
			for(Topic t:topics){
				System.out.println(t);
			}
			System.out.println("End of addNewTopic");
			session.setAttribute("topics", topics);

			 LOGGER.debug("AdminController: addNewTopic: END");
			return loadHome(session, model);
			
		}catch (AdminException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value="/deleteTopic.action", method=RequestMethod.POST)
	public String deleteTopic(@RequestParam("deletableId") int deletableId, HttpSession session, Model model){

		LOGGER.debug("AdminController: deleteTopic: START");
		try {
			adminService.deleteTopic(deletableId);
			List<Topic> topics = (List<Topic>) session.getAttribute("topics");
			List<Topic> newTopics = new ArrayList<Topic>();
			for(Topic topic : topics){
				if(topic.getId() == deletableId)
					continue;
				newTopics.add(topic);
			}
			session.setAttribute("topics", newTopics);

			LOGGER.debug("AdminController: deleteTopic: END");
			return loadHome(session, model);
		} catch (AdminException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value="/deleteActivityContainer.action", method=RequestMethod.POST)
	public String deleteActivityContainer(@RequestParam("deletableId") int deletableId, HttpSession session, Model model){

		LOGGER.debug("AdminController: deleteActivityContainer: START");
		try {
			ActivityContainer activityContainer = (ActivityContainer) session.getAttribute("activityContainer");
			adminService.deleteActivityContainer(deletableId);
			if(null==activityContainer){
				throw new AdminException();
			}
			session.removeAttribute("activityContainer");
			session.removeAttribute("topics");
			session.removeAttribute("containerMap");

			LOGGER.debug("AdminController: deleteActivityContainer: END");
			return loadHome(session, model);
		} catch (AdminException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value="/addNewActivityContainer.action", method=RequestMethod.POST)
	public String addNewActivityContainer(@RequestParam("containerName") String containerName, @RequestParam("topicId") int topicId,
			HttpSession session,HttpServletRequest request ,Model model){

		LOGGER.debug("AdminController: addNewActivityContainer: START");
		try {
			ActivityContainer activityContainer = adminService.addNewActivityContainer(containerName, topicId);

			// ---------- Changes to add version to Activity Container ----------
			String[] versionIds = request.getParameterValues("versionIds");
			
			//System.out.println("Inside AdminController addNewActivity: version is: "+versionIds[0]);
			adminService.assignActivityContainerToVersion(activityContainer.getActivityContainerId(), versionIds, topicId);
			// ---------- Changes to add version to Activity Container end here ----------
			String userTypeId = request.getParameter("usertype");
			//System.out.println("Version Id fetched from Mothe's Page is : "+versionIdforMother);

			session.removeAttribute("activityContainer");
			List<Topic> topics = (List<Topic>) session.getAttribute("topics");
			List<Topic> newTopics = new ArrayList<Topic>();
			for(Topic topic : topics){
				if(topic.getId() == topicId){
					topic.getActivityContainers().add(activityContainer);
				}
				newTopics.add(topic);
			}
			session.setAttribute("topics", newTopics);

			LOGGER.debug("AdminController: addNewActivityContainer: END");
			return loadHome(session, model);
		} catch (AdminException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value="/deleteActivity.action", method=RequestMethod.POST)
	public String deleteActivity(@RequestParam("deletableId") int deletableId, HttpSession session, Model model){

		LOGGER.debug("AdminController: deleteActivity: START");
		ActivityContainer activityContainer = (ActivityContainer) session.getAttribute("activityContainer");
		Map<Integer, ActivityContainer> containerMap = (Map<Integer, ActivityContainer>) session.getAttribute("containerMap");
		try {
			adminService.deleteActivity(Integer.valueOf(deletableId));
			containerMap.remove(activityContainer.getActivityContainerId());
			session.removeAttribute("activityContainer");
			session.setAttribute("containerMap", containerMap);

			LOGGER.debug("AdminController: deleteActivity: END");
			return loadContainerById(activityContainer.getActivityContainerId(), session);
		} catch (AdminException e) {
			return "errorPage";
		}
	}

	@ResponseBody
	@RequestMapping(value="/renameActivityContainer.action", method=RequestMethod.POST)
	public String renameActivityContainer(@RequestParam("containerName") String containerName, @RequestParam("containerId") int containerId, HttpSession session){

		LOGGER.debug("AdminController: renameActivityContainer: START");
		try {
			int returnVal = adminService.renameActivityContainer(containerName, containerId);
			ActivityContainer activityContainer = (ActivityContainer) session.getAttribute("activityContainer");
			Map<Integer, ActivityContainer> containerMap = (Map<Integer, ActivityContainer>) session.getAttribute("containerMap");
			List<Topic> topics = (List<Topic>) session.getAttribute("topics");

			activityContainer.setContainerName(containerName);
			containerMap.put(containerId, activityContainer);

			List<Topic> newTopics = new ArrayList<Topic>();
			for(Topic topic : topics){
				List<ActivityContainer> containers = new ArrayList<ActivityContainer>();
				for(ActivityContainer container : topic.getActivityContainers()){
					if(container.getActivityContainerId() == containerId)
						container.setContainerName(containerName); //Made changes by mukul
					containers.add(container);
				}
				newTopics.add(topic);
			}
			session.setAttribute("topics", newTopics);
			session.setAttribute("activityContainer", activityContainer);
			session.setAttribute("containerMap", containerMap);

			LOGGER.debug("AdminController: renameActivityContainer: END");
			return String.valueOf(returnVal);
		} catch (AdminException e) {
			return "error";
		}
	}

	@RequestMapping(value="/editActivity.action", method=RequestMethod.POST)
	public String goToEditActivity(@RequestParam("id") String parameter, HttpSession session, Model model){

		LOGGER.debug("AdminController: goToEditActivity: START");

		int activityId = Integer.valueOf(parameter.split("_")[0]);
		String templateId = parameter.split("_")[1];

		try {
			AdminActivityAnswer adminActivityAnswer = adminService.getAdminActivityAnswerByActivityId(activityId);

			model.addAttribute("adminActivity", adminActivityAnswer);
			model.addAttribute("templateId", templateId);

			LOGGER.debug("AdminController: goToEditActivity: END");
			return "editActivity";
		} catch (AdminException e) {
			return "errorPage";
		}

	}

	@RequestMapping(value="/updateActivity.action", method=RequestMethod.POST)
	public String updateActivity(@ModelAttribute("activity") AdminActivityAnswer adminActivityAnswer,
			@RequestParam(value="uploadFile",required=false) MultipartFile uploadFile,
			@RequestParam(value="card1File",required=false) MultipartFile card1File,
			@RequestParam(value="card2File",required=false) MultipartFile card2File,
			@RequestParam(value="card3File",required=false) MultipartFile card3File,
			Model model, HttpSession session, HttpServletRequest request){


		LOGGER.debug("AdminController: updateActivity: START");
		Activity act = new Activity();

		try {
			if(adminActivityAnswer.getActivity().getActivityTemplate().getId() == 1){
				adminActivityAnswer = getAdminActivityAnswerForFile(adminActivityAnswer.getActivity(), request, uploadFile, "video");
				act = adminService.updateAdminActivityAnswer(adminActivityAnswer).getActivity();
			}else if(adminActivityAnswer.getActivity().getActivityTemplate().getId() == 2){
				adminActivityAnswer = getAdminActivityAnswerForFile(adminActivityAnswer.getActivity(), request, uploadFile, "image");
				act = adminService.updateAdminActivityAnswer(adminActivityAnswer).getActivity();
			}else if(adminActivityAnswer.getActivity().getActivityTemplate().getId() == 3){
				act = updateMCQActivity(adminActivityAnswer.getActivity(), request);
			}else if(adminActivityAnswer.getActivity().getActivityTemplate().getId() == 4 || adminActivityAnswer.getActivity().getActivityTemplate().getId() == 6){
				act = updateInfoActivity(adminActivityAnswer);
			}else if(adminActivityAnswer.getActivity().getActivityTemplate().getId() == 5){
				act = updateFlipActivity(adminActivityAnswer.getActivity(), request, card1File, card2File, card3File);
			}
		} catch (AdminException e) {
			return "errorPage";
		}

		ActivityContainer activityContainer = (ActivityContainer) session.getAttribute("activityContainer");
		Map<Integer, ActivityContainer> containerMap = (Map<Integer, ActivityContainer>) session.getAttribute("containerMap");
		if(null==activityContainer || null == containerMap)
			return "errorPage";

		containerMap.remove(activityContainer.getActivityContainerId());
		session.removeAttribute("activityContainer");
		session.setAttribute("containerMap", containerMap);

		LOGGER.debug(adminActivityAnswer);

		LOGGER.debug("AdminController: updateActivity: END");
		return loadContainerById(activityContainer.getActivityContainerId(), session);
	}

	private Activity updateInfoActivity(AdminActivityAnswer adminActivityAnswer) throws AdminException{

		adminActivityAnswer = adminService.updateAdminActivityAnswer(adminActivityAnswer);

		return adminActivityAnswer.getActivity();
	}

	private Activity updateFlipActivity(Activity activity, HttpServletRequest request, MultipartFile card1File,
			MultipartFile card2File, MultipartFile card3File) throws AdminException{

		List<Answer> answers = new ArrayList<Answer>();
		for(int i = 1; i<= 6; i=i+2){
			Answer frontCard = new Answer();
			frontCard.setAnswerText(request.getParameter("card"+i+"Front"));
			frontCard.setOrderNo(i);
			answers.add(frontCard);

			String backCardParam = request.getParameter("card"+(i+1)+"Back");

			if(backCardParam.isEmpty()){
				String imageUrl = null;
				if(i==1){
					imageUrl = adminService.generateFilePath(card1File, "image");
				}else if(i==3){
					imageUrl = adminService.generateFilePath(card2File, "image");
				}else if(i==5){
					imageUrl = adminService.generateFilePath(card3File, "image");
				}
				if(null != imageUrl){
					Answer imageFile = new Answer();
					imageFile.setAnswerText(imageUrl);
					imageFile.setOrderNo(i+1);
					answers.add(imageFile);

				}
			}else{
				Answer backCard = new Answer();
				backCard.setAnswerText(backCardParam);
				backCard.setOrderNo(i+1);
				answers.add(backCard);
			}
		}

		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);

		adminActivityAnswer = adminService.updateAdminActivityAnswer(adminActivityAnswer);

		return adminActivityAnswer.getActivity();
	}

	private Activity updateMCQActivity(Activity activity, HttpServletRequest request) throws AdminException {

		List<String> correctAnswers = new ArrayList<String>(Arrays.asList(request.getParameterValues("correctAnswer")));

		Enumeration<String> parameters = request.getParameterNames();
		List<Answer> answers = new ArrayList<Answer>();
		while(parameters.hasMoreElements()){
			String param = (String) parameters.nextElement();
			if(param.contains("option")){
				Answer answer = new Answer();
				answer.setAnswerText(request.getParameter(param));
				answer.setOrderNo(Integer.valueOf(param.split("_")[1]));
				answer.setIsCorrect(correctAnswers.contains(param)?true:false);
				answers.add(answer);
			}
		}

		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		adminActivityAnswer.setActivity(activity);
		adminActivityAnswer.setAnswers(answers);

		adminActivityAnswer = adminService.updateAdminActivityAnswer(adminActivityAnswer);

		return adminActivityAnswer.getActivity();
	}

	@RequestMapping(value="/forgotAdminPassword.action", method=RequestMethod.POST)
	public String resetUnamePassword(@RequestParam("emailID") String emailID, @RequestParam("username") String username,
			@RequestParam("password") String password, Model model, HttpSession httpSession) throws AuthenticationException{

		try {
			String status = authenticateService.resetUnamePassword(emailID,username,password);
			httpSession.removeAttribute("user");
			return "adminLogon";
//			remove user from the session before returning to the landing page
		} catch (AuthenticationException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value="/adminLogout.action", method=RequestMethod.GET)
	public String logoutUser(HttpSession httpSession, Model model){
		if(httpSession != null)
			httpSession.invalidate();
		return "adminLogon";
	}

}
