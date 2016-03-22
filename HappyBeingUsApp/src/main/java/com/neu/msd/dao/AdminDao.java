/**
 * 
 */
package com.neu.msd.dao;

import java.util.List;

import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
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

	public User authenticateAdminByUsernamePassword(UserAuthentication userAuthentication) throws AdminException;
}
