/**
 * 
 */
package com.neu.msd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.neu.msd.dao.AdminDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	DataSource dataSource;

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AdminDao#loadTopics()
	 */
	public List<Topic> loadTopics() throws AdminException {
		
		try {
			List<Topic> topics = new ArrayList<Topic>();
			Connection connection = dataSource.getConnection();
			String sql = "select * from topic";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Topic topic = new Topic();
				topic.setId(rs.getInt("topic_id"));
				topic.setTopicName(rs.getString("topic_name"));
				topics.add(topic);
			}
			
			return topics;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AdminDao#loadAdminContainersByTopicId(int)
	 */
	public List<ActivityContainer> loadActivityContainersByTopicId(int topicId) throws AdminException {
		
		try {
			List<ActivityContainer> activityContainers = new ArrayList<ActivityContainer>();
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity_container where topic_id="+topicId+" order by order_no";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				ActivityContainer activityContainer = new ActivityContainer();
				activityContainer.setActivityContainerId(rs.getInt("activity_container_id"));
				activityContainer.setContainerName(rs.getString("activity_container_name"));
				activityContainer.setOrderNo(rs.getInt("order_no"));
				activityContainers.add(activityContainer);
			}
			
			return activityContainers;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AdminDao#loadActivityContainerById(int)
	 */
	public ActivityContainer loadActivityContainerById(int activityContainerId) throws AdminException {

		try {
			ActivityContainer activityContainer = new ActivityContainer();
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity_container where activity_container_id="+activityContainerId;
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				activityContainer.setActivityContainerId(rs.getInt("activity_container_id"));
				activityContainer.setContainerName(rs.getString("activity_container_name"));
				activityContainer.setOrderNo(rs.getInt("order_no"));
				activityContainer.setActivities(loadActivitiesByActivityContainerId(activityContainerId));
			}
			
			return activityContainer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AdminDao#loadActivitiesByActivityContainerId(int)
	 */
	public List<Activity> loadActivitiesByActivityContainerId(int activityContainerId) throws AdminException {
		Map<Integer, ActivityType> activityTypeMap = new HashMap<Integer, ActivityType>();
		Map<Integer, ActivityTemplate> activityTemplateMap = new HashMap<Integer, ActivityTemplate>();
		
		loadActivityType(activityTypeMap);
		loadActivityTemplate(activityTemplateMap);
		try {
			List<Activity> activities= new ArrayList<Activity>();
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity where activity_container_id="+activityContainerId;
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				Activity activity = new Activity();
				activity.setId(rs.getInt("activity_id"));
				activity.setActivityType(activityTypeMap.get(rs.getInt("activity_type_id")));
				activity.setActivityTemplate(activityTemplateMap.get(rs.getInt("activity_template_id")));
				activity.setActivityText(rs.getString("activity_text"));
				activity.setOrderNo(rs.getInt("order_no"));
				activities.add(activity);
			}
			
			return activities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	/**
	 * @param activityTemplateMap
	 * @throws AdminException
	 */
	public void loadActivityTemplate(Map<Integer, ActivityTemplate> activityTemplateMap) throws AdminException {
		
		List<ActivityTemplate> activityTemaplates = getAllActivityTemplates();
		
		for(ActivityTemplate activityTemplate : activityTemaplates){
			activityTemplateMap.put(activityTemplate.getId(), activityTemplate);
		}
	}

	/**
	 * @param activityTypeMap
	 * @throws AdminException
	 */
	public void loadActivityType(Map<Integer, ActivityType> activityTypeMap) throws AdminException {

		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity_type";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				ActivityType activityType = new ActivityType();
				int activityTypeId = rs.getInt("activity_type_id");
				activityType.setId(activityTypeId);
				activityType.setName(rs.getString("activity_type_desc"));
				activityTypeMap.put(activityTypeId, activityType);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
		
	}

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException {
		
		List<ActivityTemplate> activityTemplates = new ArrayList<ActivityTemplate>();
		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity_template";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				ActivityTemplate activityTemplate = new ActivityTemplate();
				int activityTemplateId = rs.getInt("activity_template_id");
				activityTemplate.setId(activityTemplateId);
				activityTemplate.setTemplateName(rs.getString("activity_template_desc"));
				activityTemplates.add(activityTemplate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
		
		return activityTemplates;
	}
	
	public User authenticateAdminByUsernamePassword(UserAuthentication userAuthentication) throws AdminException {
		try {
			User admin = new User();
			Connection connection = dataSource.getConnection();
			String sql = "select * from user_authentication where username=? and password = ? and user_type_id = 1";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, userAuthentication.getUsername());
			stmt.setString(2, userAuthentication.getPassword());
			ResultSet rs = stmt.executeQuery();
			while(rs.next())
			{
				admin.setId(rs.getInt("user_id"));
			}
			
			if (admin.getId()!=0)
			{
				String sql_get_admin = "select * from user where user_id = ?";
				PreparedStatement stmt_get_admin = connection.prepareStatement(sql_get_admin);
				stmt_get_admin.setInt(1, admin.getId());
				ResultSet rs_get_admin = stmt_get_admin.executeQuery();
				while(rs_get_admin.next()){
					admin.setFirstName(rs_get_admin.getString("first_name"));
				}
			}

			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	public int renameTopic(String topicName, String topicId) throws AdminException {
		try {
			Connection connection = dataSource.getConnection();
//			update topic set topic_name = ? where topic_id = ?
			String sql = "update topic set topic_name = ? where topic_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, topicName);
			stmt.setInt(2, Integer.valueOf(topicId));
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records updated: "+records);
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}
	
	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException {
		
		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from admin_activity_answer where activity_id=?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityId);

			ResultSet rs = stmt.executeQuery();
			List<Answer> answers = new ArrayList<Answer>();
			while(rs.next()){
				Answer answer = new Answer();
				answer.setId(rs.getInt("answer_id"));
				answers.add(answer);
			}
			adminActivityAnswer.setAnswers(answers);
			return adminActivityAnswer;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}


}
