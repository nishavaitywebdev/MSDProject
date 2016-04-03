/**
 * 
 */
package com.neu.msd.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import com.neu.msd.service.AdminService;

/**
 * @author Harsh
 *
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	Logger LOGGER = Logger.getLogger(AdminServiceImpl.class);

	@Autowired
	private AdminDao adminDao;
	
	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#loadTopics()
	 */
	@Transactional
	public List<Topic> loadTopics(Map<Integer, ActivityContainer> containerMap) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadTopics: START");
		
		List<Topic> allTopics = adminDao.loadTopics();
		
		loadTopicsWithActivityContainers(containerMap, allTopics);
		
		LOGGER.debug("AdminServiceImpl: loadTopics: END");
		return allTopics;
	}

	/**
	 * @param containerActivitiesMap 
	 * @param containerMap 
	 * @param allTopics
	 * @throws AdminException
	 */
	public void loadTopicsWithActivityContainers(Map<Integer, ActivityContainer> containerMap, List<Topic> allTopics) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadTopicsWithActivityContainers: START");
			
		for(Topic topic : allTopics){
			List<ActivityContainer> activityContainers = adminDao.loadActivityContainersByTopicId(topic.getId());
			topic.setActivityContainers(activityContainers);
			loadActivityContainersWithActivities(containerMap, activityContainers);
		}
		LOGGER.debug("AdminServiceImpl: loadTopicsWithActivityContainers: END");
	}

	/**
	 * @param containerMap 
	 * @param activityContainers
	 * @throws AdminException
	 */
	private void loadActivityContainersWithActivities(Map<Integer, ActivityContainer> containerMap, List<ActivityContainer> activityContainers) throws AdminException {
		LOGGER.debug("AdminServiceImpl: loadActivityContainersWithActivities: START");

		for(ActivityContainer activityContainer : activityContainers){
			List<Activity> activities = adminDao.loadActivitiesByActivityContainerId(activityContainer.getActivityContainerId());
			activityContainer.setActivities(activities);
			containerMap.put(activityContainer.getActivityContainerId(), activityContainer);
		}
		LOGGER.debug("AdminServiceImpl: loadActivityContainersWithActivities: END");
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

	public int renameTopic(String topicName, int topicId) throws AdminException {
		return adminDao.renameTopic(topicName, topicId);
	}
	
	public User adminAuthenticate(UserAuthentication userAuthentication) throws AdminException {
		return adminDao.authenticateAdminByUsernamePassword(userAuthentication);
	}

	public int addNewTopic(String topicName) throws AdminException {
		return adminDao.addTopic(topicName); 
	}

	public int deleteTopic(int deletableId) throws AdminException {
		return adminDao.deleteTopic(deletableId);
	}
	
	public int deleteActivityContainer(int deletableId) throws AdminException {
		return adminDao.deleteActivityContainer(deletableId);
	}

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException {
		return adminDao.addNewActivityContainer(containerName, topicId);
	}

	public int deleteActivityContainer(Integer deletableId) throws AdminException {
		return adminDao.deleteActivity(deletableId);
	}
	
	public int renameActivityContainer(String containerName, int containerId) throws AdminException {
		return adminDao.renameActivityContainer(containerName, containerId);
	}

	public List<Version> loadAllVersion() throws AdminException {
		return adminDao.loadAllVersion();
	}

	public int assignTopicToVersion(int topicId, int versionId) throws AdminException {
		return adminDao.assignTopicToVersion(topicId, versionId);
	}
}