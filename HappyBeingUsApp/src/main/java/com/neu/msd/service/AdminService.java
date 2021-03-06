/**
 * 
 *//*
package com.neu.msd.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import java.util.ArrayList;
*//**
 * @author Harsh
 *
 *//*
public interface AdminService {
	
	public List<Topic> loadTopics(Map<Integer, ActivityContainer> containerMap) throws AdminException;

	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException;

	public void loadTopicsWithActivityContainers(Map<Integer, ActivityContainer> containerMap, List<Topic> allTopics, int versionId) throws AdminException;

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException;

	public int renameTopic(String topicName, int topicId) throws AdminException;
	
	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException;

	//Made change to associate a topic with the user i.e. mother or daughter
	public int addNewTopic(String topicName,String isMothers) throws AdminException;

	public int deleteTopic(int deletableId) throws AdminException;

	public int deleteActivityContainer(int deletableId) throws AdminException;

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException;

	public int deleteActivity(Integer deletableId) throws AdminException;

	public int renameActivityContainer(String containerName, int containerId) throws AdminException;

	public List<Version> loadAllVersion() throws AdminException;

//	public void assignTopicToVersion(int topicId, String[] versionIds) throws AdminException;
	
	// ---------- Changes to add version to Activity Container ----------
	public void assignActivityContainerToVersion(int actConId, String[] versionIds, int topicId) throws AdminException;
	// ---------- Changes to add version to Activity Container end here----------


	public AdminActivityAnswer saveAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException;

	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException;

	public AdminActivityAnswer updateAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException;

	public String generateFilePath(MultipartFile uploadFile, String fileType) throws AdminException;
	
	public int registerAdmin(UserAuthentication userAuthentication) throws AdminException;

	public List<AdminActivityAnswer> getDiagnosticQuestions();
	
	public int addDiagnosticQuestion(String questionText, List<Answer> options) throws AdminException;

	public int deleteDiagnosticQuestion(int activityid) throws AdminException;
	
	public int updateDiagnosticQuestion(AdminActivityAnswer adminActivity) throws AdminException;

	public int assignTopicToUsers(int topicId) throws AdminException;
	
	//Adding new filtering criteria for mothers
	public List<Topic> filterTopicForUsers(List<Topic> topics,User user) throws AdminException;
	
}


*//**
 * 
 *//*
package com.neu.msd.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;

import java.util.ArrayList;
*//**
 * @author Harsh
 *
 *//*
public interface AdminService {
	
	public List<Topic> loadTopics(Map<Integer, ActivityContainer> containerMap) throws AdminException;

	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException;

	public void loadTopicsWithActivityContainers(Map<Integer, ActivityContainer> containerMap, List<Topic> allTopics, int versionId) throws AdminException;

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException;

	public int renameTopic(String topicName, int topicId) throws AdminException;
	
	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException;

	public int addNewTopic(String topicName,String isMothers) throws AdminException;

	public int deleteTopic(int deletableId) throws AdminException;

	public int deleteActivityContainer(int deletableId) throws AdminException;

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException;

	public int deleteActivity(Integer deletableId) throws AdminException;

	public int renameActivityContainer(String containerName, int containerId) throws AdminException;

	public List<Version> loadAllVersion() throws AdminException;

//	public void assignTopicToVersion(int topicId, String[] versionIds) throws AdminException;
	
	// ---------- Changes to add version to Activity Container ----------
	public void assignActivityContainerToVersion(int actConId, String[] versionIds, int topicId) throws AdminException;
	// ---------- Changes to add version to Activity Container end here----------


	public AdminActivityAnswer saveAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException;

	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException;

	public AdminActivityAnswer updateAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException;

	public String generateFilePath(MultipartFile uploadFile, String fileType) throws AdminException;
	
	public int registerAdmin(UserAuthentication userAuthentication) throws AdminException;

	public List<AdminActivityAnswer> getDiagnosticQuestions();
	
	public int addDiagnosticQuestion(String questionText, List<Answer> options) throws AdminException;

	public int deleteDiagnosticQuestion(int activityid) throws AdminException;
	
	public int updateDiagnosticQuestion(AdminActivityAnswer adminActivity) throws AdminException;

	public int assignTopicToUsers(int topicId,int user_type_id) throws AdminException;
	
	public List<Topic> filterTopicForUsers(List<Topic> topics,User user) throws AdminException;
}*/

/**
 * 
 */
package com.neu.msd.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;

import java.util.ArrayList;
/**
 * @author Harsh
 *
 */
public interface AdminService {
	
	public List<Topic> loadTopics(Map<Integer, ActivityContainer> containerMap) throws AdminException;

	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException;

	public void loadTopicsWithActivityContainers(Map<Integer, ActivityContainer> containerMap, List<Topic> allTopics, int versionId) throws AdminException;

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException;

	public int renameTopic(String topicName, int topicId) throws AdminException;
	
	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException;

	public int addNewTopic(String topicName,String isMothers) throws AdminException;

	public int deleteTopic(int deletableId) throws AdminException;

	public int deleteActivityContainer(int deletableId) throws AdminException;

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException;

	public int deleteActivity(Integer deletableId) throws AdminException;

	public int renameActivityContainer(String containerName, int containerId) throws AdminException;

	public List<Version> loadAllVersion() throws AdminException;

//	public void assignTopicToVersion(int topicId, String[] versionIds) throws AdminException;
	
	// ---------- Changes to add version to Activity Container ----------
	public void assignActivityContainerToVersion(int actConId, String[] versionIds, int topicId) throws AdminException;
	// ---------- Changes to add version to Activity Container end here----------


	public AdminActivityAnswer saveAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException;

	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException;

	public AdminActivityAnswer updateAdminActivityAnswer(AdminActivityAnswer adminActivityAnswer) throws AdminException;

	public String generateFilePath(MultipartFile uploadFile, String fileType) throws AdminException;
	
	public int registerAdmin(UserAuthentication userAuthentication) throws AdminException;

	public List<AdminActivityAnswer> getDiagnosticQuestions();
	
	public int addDiagnosticQuestion(String questionText, List<Answer> options) throws AdminException;

	public int deleteDiagnosticQuestion(int activityid) throws AdminException;
	
	public int updateDiagnosticQuestion(AdminActivityAnswer adminActivity) throws AdminException;

	public int assignTopicToUsers(int topicId,int user_type_id) throws AdminException;
	
	public List<Topic> filterTopicForUsers(List<Topic> topics,User user) throws AdminException;
}