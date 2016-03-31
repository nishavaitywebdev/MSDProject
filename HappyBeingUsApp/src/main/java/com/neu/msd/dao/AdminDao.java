/**
 * 
 */
package com.neu.msd.dao;

import java.util.List;
import java.util.Map;

import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
public interface AdminDao {
	
	public List<Topic> loadTopics() throws AdminException;

	public List<ActivityContainer> loadActivityContainersByTopicId(int topicId) throws AdminException;

	public List<Activity> loadActivitiesByActivityContainerId(int activityContainerId) throws AdminException;

	public ActivityContainer loadActivityContainerById(int activityContainerId) throws AdminException;

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException;

	public int renameTopic(String topicName, int topicId) throws AdminException;

	public void loadActivityTemplate(Map<Integer, ActivityTemplate> activityTemplateMap) throws AdminException;
	
	public void loadActivityType(Map<Integer, ActivityType> activityTypeMap) throws AdminException;

	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException;

	public User authenticateAdminByUsernamePassword(UserAuthentication userAuthentication) throws AdminException;

	public int addTopic(String topicName) throws AdminException;

	public int deleteTopic(int deletableId) throws AdminException;
	
	public int deleteActivityContainer(int deletableId) throws AdminException;

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException;

	public int deleteActivity(Integer deletableId) throws AdminException;

	public int renameActivityContainer(String containerName, int containerId) throws AdminException;
}
