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

	public int renameTopic(String topicName, String topicId) throws AdminException;

	public void loadActivityTemplate(Map<Integer, ActivityTemplate> activityTemplateMap) throws AdminException;
	
	public void loadActivityType(Map<Integer, ActivityType> activityTypeMap) throws AdminException;

	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException;
}
