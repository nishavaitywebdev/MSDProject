/**
 * 
 */
package com.neu.msd.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neu.msd.dao.AdminDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AdminException;
import com.neu.msd.service.AdminServie;

/**
 * @author Harsh
 *
 */
@Service("authenticateService")
public class AdminServiceImpl implements AdminServie {

	@Autowired
	private AdminDao adminDao;
	
	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#loadTopics()
	 */
	@Transactional
	public List<Topic> loadTopics() throws AdminException {
		
		List<Topic> allTopics = adminDao.loadTopics();
		
		loadTopicsWithActivityContainers(allTopics);
		
		return allTopics;
	}

	/**
	 * @param allTopics
	 * @throws AdminException
	 */
	private void loadTopicsWithActivityContainers(List<Topic> allTopics) throws AdminException {
			
		for(Topic topic : allTopics){
			List<ActivityContainer> activityContainers = adminDao.loadActivityContainersByTopicId(topic.getId());
			topic.setActivityContainers(activityContainers);
//			loadActivityContainersWithActivities(activityContainers);
		}
	}

	/**
	 * @param activityContainers
	 * @throws AdminException
	 */
	private void loadActivityContainersWithActivities(List<ActivityContainer> activityContainers) throws AdminException {

		for(ActivityContainer activityContainer : activityContainers){
			List<Activity> activities = adminDao.loadActivitiesByActivityContainerId(activityContainer.getActivityContainerId());
			activityContainer.setActivities(activities);
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#getActivityContainerById(int)
	 */
	public ActivityContainer getActivityContainerById(int activityContainerId) throws AdminException {
		
		return adminDao.loadActivityContainerById(activityContainerId);
	}

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException {
		
		return adminDao.getAllActivityTemplates();
	}

	public int renameTopic(String topicName, String topicId) throws AdminException {
		return adminDao.renameTopic(topicName, topicId);
	}
	
	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException {
		// TODO Auto-generated method stub
		return adminDao.authenticateAdminByUsernamePassword(userAuthentication);
	}


}