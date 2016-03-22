/**
 * 
 */
package com.neu.msd.service;

import java.util.List;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
public interface AdminServie {
	
	public List<Topic> loadTopics() throws AdminException;

	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException;

	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException;
}
