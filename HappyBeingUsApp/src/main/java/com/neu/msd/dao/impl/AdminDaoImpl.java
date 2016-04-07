/**
 * 
 */
package com.neu.msd.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
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
import com.neu.msd.entities.Mother;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.Version;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;

/**
 * @author Harsh
 *
 */
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {
	
	Logger LOGGER = Logger.getLogger(AdminDaoImpl.class);
	
	@Autowired
	DataSource dataSource;
	
	private Connection connection;

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AdminDao#loadTopics()
	 */
	public List<Topic> loadTopics() throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: loadTopics: START");
		
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
			
			LOGGER.debug("AdminDaoImpl: loadTopics: END");
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
		
		LOGGER.debug("AdminDaoImpl: loadActivityContainersByTopicId: START");
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
			
			LOGGER.debug("AdminDaoImpl: loadActivityContainersByTopicId: END");
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

		LOGGER.debug("AdminDaoImpl: loadActivityContainerById: START");
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
			
			LOGGER.debug("AdminDaoImpl: loadActivityContainerById: END");
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
		LOGGER.debug("AdminDaoImpl: loadActivitiesByActivityContainerId: START");

		Map<Integer, ActivityType> activityTypeMap = new HashMap<Integer, ActivityType>();
		Map<Integer, ActivityTemplate> activityTemplateMap = new HashMap<Integer, ActivityTemplate>();
		
		loadActivityType(activityTypeMap);
		loadActivityTemplate(activityTemplateMap);
		try {
			List<Activity> activities= new ArrayList<Activity>();
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity where activity_container_id=? order by order_no";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityContainerId);
			
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
			
			LOGGER.debug("AdminDaoImpl: loadActivitiesByActivityContainerId: END");
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
		
		LOGGER.debug("AdminDaoImpl: loadActivityTemplate: START");
		List<ActivityTemplate> activityTemaplates = getAllActivityTemplates();
		
		for(ActivityTemplate activityTemplate : activityTemaplates){
			activityTemplateMap.put(activityTemplate.getId(), activityTemplate);
		}
		LOGGER.debug("AdminDaoImpl: loadActivityTemplate: END");
	}

	/**
	 * @param activityTypeMap
	 * @throws AdminException
	 */
	public void loadActivityType(Map<Integer, ActivityType> activityTypeMap) throws AdminException {

		LOGGER.debug("AdminDaoImpl: loadActivityType: START");
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
			
			LOGGER.debug("AdminDaoImpl: loadActivityType: END");
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
		
	}

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: getAllActivityTemplates: START");
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
		
		LOGGER.debug("AdminDaoImpl: getAllActivityTemplates: END");
		return activityTemplates;
	}
	

	public int renameTopic(String topicName, int topicId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: renameTopic: START");
		try {
			Connection connection = dataSource.getConnection();
//			update topic set topic_name = ? where topic_id = ?
			String sql = "update topic set topic_name = ? where topic_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, topicName);
			stmt.setInt(2, topicId);
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records updated: "+records);

			LOGGER.debug("AdminDaoImpl: renameTopic: END");
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}
	
	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: getAdminActivityAnswerByActivityId: START");
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

			LOGGER.debug("AdminDaoImpl: getAdminActivityAnswerByActivityId: END");
			return adminActivityAnswer;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	public User authenticateAdminByUsernamePassword(UserAuthentication userAuthentication) throws AdminException {
		LOGGER.debug("AdminDaoImpl: authenticateAdminByUsernamePassword: START");
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

			LOGGER.debug("AdminDaoImpl: authenticateAdminByUsernamePassword: END");
			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	public int addTopic(String topicName) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: addTopic: START");
		try {
			Connection connection = dataSource.getConnection();
			
			int nextTopicId = getNextTopicId(connection);
			String sql = "insert into topic values(?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, nextTopicId);
			stmt.setString(2, topicName);
			
			int records = stmt.executeUpdate();
			
			System.out.println("New topic inserted, no. of records inserted: "+records);

			LOGGER.debug("AdminDaoImpl: addTopic: END");
			return nextTopicId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}
	
	private int getNextTopicId(Connection connection) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextTopicId: START");
		try {
			
			String sql = "select MAX(topic_id) as topic_id from topic";

			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {

				LOGGER.debug("AdminDaoImpl: getNextTopicId: END");
				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	public int deleteTopic(int deletableId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: deleteTopic: START");
		try {
			Connection connection = dataSource.getConnection();
			
			String sql = "delete from topic where topic_id=?";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, deletableId);
			
			int records = stmt.executeUpdate();
			
			System.out.println("Topic with topic id:"+deletableId+", deleted. No. of records deleted: "+records);

			LOGGER.debug("AdminDaoImpl: deleteTopic: END");
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}
	
	public int deleteActivityContainer(int deletableId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: deleteActivityContainer: START");
		
		try {
			Connection connection = dataSource.getConnection();
			
			String sql = "select order_no, topic_id from activity_container where activity_container_id=?";
			
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, deletableId);
			
			ResultSet rs = stmt.executeQuery();
			
			int orderNo = 0, topicId = 0;
			
			while(rs.next())
			{
				orderNo = rs.getInt("order_no");
				topicId = rs.getInt("topic_id");
			}
			
			reorderActivityContainers(connection, orderNo, topicId);

			sql = "delete from activity_container where activity_container_id=?";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, deletableId);
			
			int records = stmt.executeUpdate();
			
			System.out.println("Activity container with activity container id:"+deletableId+", deleted. No. of records deleted: "+records);

			LOGGER.debug("AdminDaoImpl: deleteActivityContainer: END");
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	private void reorderActivityContainers(Connection connection, int orderNo, int topicId) throws AdminException{
		
		LOGGER.debug("AdminDaoImpl: reorderActivityContainers: START");
		try {
			
			List<ActivityContainer> activityContainers = loadActivityContainersByTopicId(topicId);
			
			for(ActivityContainer activityContainer : activityContainers){
				if(activityContainer.getOrderNo() > orderNo){
					String sql = "update activity_container set order_no=? where activity_container_id=?";
					
					PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					stmt.setInt(1, orderNo);
					stmt.setInt(2, activityContainer.getActivityContainerId());

					int records = stmt.executeUpdate();
					System.out.println("Order of the the activity container: "+activityContainer.getActivityContainerId()+" updated to: "+orderNo+", no. of rows inserted: "+records);
					orderNo++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
		LOGGER.debug("AdminDaoImpl: reorderActivityContainers: END");
		
	}

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: addNewActivityContainer: START");
		try {
			Connection connection = dataSource.getConnection();
			
			int nextContainerId = getNextContainerId(connection);
			
			int orderNo = getNextContainerOrderNo(connection, topicId);
			
			String sql = "insert into activity_container values (?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, nextContainerId);
			stmt.setString(2, containerName);
			stmt.setInt(3, orderNo);
			stmt.setInt(4, topicId);
			
			int records = stmt.executeUpdate();
			
			ActivityContainer  activityContainer = new ActivityContainer();
			
			activityContainer.setActivityContainerId(nextContainerId);
			activityContainer.setContainerName(containerName);
			activityContainer.setOrderNo(orderNo);
			
			System.out.println("Added a new Activity container under the topicId: "+topicId+", no. of rows inserted: "+records);

			LOGGER.debug("AdminDaoImpl: addNewActivityContainer: END");
			return activityContainer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	private int getNextContainerId(Connection connection) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextContainerId: START");
		try {
			
			String sql = "select MAX(activity_container_id) as activity_container_id from activity_container";

			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				LOGGER.debug("AdminDaoImpl: getNextContainerId: END");
				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}
	
	private int getNextContainerOrderNo(Connection connection, int topicId) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextContainerOrderNo: START");
		try {
			
			String sql = "select MAX(order_no) as order_no from activity_container where topic_id=?";
			
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, topicId);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()) {
				LOGGER.debug("AdminDaoImpl: getNextContainerOrderNo: END");
				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	@Override
	public int deleteActivity(Integer deletableId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: deleteActivity: START");
		try {
			Connection connection = dataSource.getConnection();
			
			String sql = "select order_no, activity_container_id from activity where activity_id=?";
			
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, deletableId);
			
			ResultSet rs = stmt.executeQuery();
			
			int orderNo = 0, containerId = 0;
			
			while(rs.next())
			{
				orderNo = rs.getInt("order_no");
				containerId = rs.getInt("activity_container_id");
			}
			
			reorderActivites(connection, orderNo, containerId);
			
			sql = "delete from activity where activity_id=?";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, deletableId);
			
			int records = stmt.executeUpdate();
			
			LOGGER.debug("AdminDaoImpl: deleteActivity: END");
			return records;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	private void reorderActivites(Connection connection, int orderNo, int containerId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: reorderActivites: START");
		try {
			
			List<Activity> activities = loadActivitiesByActivityContainerId(containerId);
			
			for(Activity activity : activities){
				if(activity.getOrderNo() > orderNo){
					String sql = "update activity set order_no=? where activity_id=?";
					
					PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					stmt.setInt(1, orderNo);
					stmt.setInt(2, activity.getId());

					int records = stmt.executeUpdate();
					System.out.println("Order of the the activity: "+activity.getId()+" updated to: "+orderNo+", no. of rows inserted: "+records);
					orderNo++;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
		LOGGER.debug("AdminDaoImpl: reorderActivites: END");
	}
	
	public int renameActivityContainer(String containerName, int containerId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: renameActivityContainer: START");
		try {
			Connection connection = dataSource.getConnection();
			String sql = "update activity_container set activity_container_name = ? where activity_container_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, containerName);
			stmt.setInt(2, containerId);
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records updated: "+records);

			LOGGER.debug("AdminDaoImpl: renameActivityContainer: END");
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}
	}

	public List<Version> loadAllVersion() throws AdminException {
		LOGGER.debug("AdminDaoImpl: loadAllVersion: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			String sql = "select * from version";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.executeQuery();
			
			List<Version> versions = new ArrayList<Version>();
			
			while(rs.next()){
				Version version = new Version();
				version.setId(rs.getInt("version_id"));
				version.setVersionName(rs.getString("version_name"));
				versions.add(version);
			}
			
			return versions;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
				try {
					if(null != rs) rs.close();
					if(null != stmt) stmt.close();
					if(null != connection) connection.close();
					LOGGER.debug("AdminDaoImpl: loadAllVersion: END");
				} catch (SQLException e) {
					e.printStackTrace();
					throw new AdminException(e);
				}
		}
	}

	public int assignTopicToVersion(int topicId, int versionId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: assignTopicToVersion: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			String sql = "insert into version_topic values (?, ?)";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, versionId);
			stmt.setInt(2, topicId);
			
			int records = stmt.executeUpdate();
			
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: assignTopicToVersion: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public Activity saveActivity(Activity activity) throws AdminException {
		LOGGER.debug("AdminDaoImpl: saveActivity: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			
			int nextActivityId = getNextActivityId(connection);
			int nextOrderNo = getNextActivityOrderNo(connection, activity.getActivityContainer().getActivityContainerId());
			
			
			String sql = "insert into activity values (?, ?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, nextActivityId);
			stmt.setInt(2, activity.getActivityType().getId());
			stmt.setInt(3, activity.getActivityTemplate().getId());
			stmt.setString(4, activity.getActivityText());
			stmt.setInt(5, nextOrderNo);
			stmt.setInt(6, activity.getActivityContainer().getActivityContainerId());
			
			int records = stmt.executeUpdate();
			
			activity.setId(nextActivityId);
			activity.setOrderNo(nextOrderNo);

			return activity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: saveActivity: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	private int getNextActivityId(Connection connection) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextActivityId: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select MAX(activity_id) as activity_id from activity";

			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {

				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: getNextActivityId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	private int getNextActivityOrderNo(Connection connection, int containerId) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextActivityOrderNo: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select MAX(order_no) as order_no from activity where activity_container_id=?";
			
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, containerId);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: getNextActivityOrderNo: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	@Override
	public Answer saveAnswer(Answer answer) throws AdminException {
		LOGGER.debug("AdminDaoImpl: saveAnswer: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			
			int nextAnswerId = getNextAnswerId(connection);
			
			String sql = "insert into answer values (?, ?, ?)";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, nextAnswerId);
			stmt.setString(2, answer.getAnswerText());
			stmt.setInt(3, answer.getOrderNo());
			
			int records = stmt.executeUpdate();
			
			answer.setId(nextAnswerId);

			return answer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: saveAnswer: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	private int getNextAnswerId(Connection connection) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextAnswerId: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select MAX(answer_id) as answer from answer";

			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {

				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: getNextAnswerId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public void saveAdminActivityAnswer(int activityId, int answerId, boolean isCorrect) throws AdminException {
		LOGGER.debug("AdminDaoImpl: saveAdminActivityAnswer: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "insert into admin_activity_answer values (?, ?, ?)";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, activityId);
			stmt.setInt(2, answerId);
			stmt.setBoolean(3, isCorrect);
			
			int records = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: saveAdminActivityAnswer: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public int deleteFromAdminActivityAnswer(Integer activityId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: deleteFromAdminActivityAnswer: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();

			String sql = "delete from admin_activity_answer where activity_id=?";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, activityId);
			
			int records = stmt.executeUpdate();
			
			return records;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: deleteFromAdminActivityAnswer: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public int deleteFromUserTopicContainerActivity(Integer activityId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: deleteFromUserTopicContainerActivity: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();

			String sql = "delete from user_topic_container_activity_answer where activity_id=?";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, activityId);
			
			int records = stmt.executeUpdate();
			
			return records;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: deleteFromUserTopicContainerActivity: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public Activity loadActivityById(int activityId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: loadActivityById: START");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Map<Integer, ActivityType> activityTypeMap = new HashMap<Integer, ActivityType>();
		Map<Integer, ActivityTemplate> activityTemplateMap = new HashMap<Integer, ActivityTemplate>();
		
		loadActivityType(activityTypeMap);
		loadActivityTemplate(activityTemplateMap);
		try {
			connection = dataSource.getConnection();
			String sql = "select * from activity where activity_id=?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityId);
			
			rs = stmt.executeQuery();
			Activity activity = new Activity();
			if (rs.next()) {
				activity.setId(rs.getInt("activity_id"));
				activity.setActivityType(activityTypeMap.get(rs.getInt("activity_type_id")));
				activity.setActivityTemplate(activityTemplateMap.get(rs.getInt("activity_template_id")));
				activity.setActivityText(rs.getString("activity_text"));
				activity.setOrderNo(rs.getInt("order_no"));
				activity.getActivityContainer().setActivityContainerId(rs.getInt("activity_container_id"));
			}
			
			return activity;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: loadActivityById: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public List<Answer> loadAnswersByActivityId(int activityId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: loadAnswersByActivityId: START");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "select answer.answer_id answer_id, answer_desc, order_no, is_correct from answer join admin_activity_answer on admin_activity_answer.answer_id = answer.answer_id where admin_activity_answer.activity_id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityId);
			
			rs = stmt.executeQuery();
			List<Answer> answers = new ArrayList<Answer>();
			while (rs.next()) {
				Answer answer = new Answer();
				answer.setId(rs.getInt("answer_id"));
				answer.setAnswerText(rs.getString("answer_desc"));
				answer.setOrderNo(rs.getInt("order_no"));
				answer.setCorrect(rs.getBoolean("is_correct"));
				answers.add(answer);
			}
			
			return answers;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		} finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: loadAnswersByActivityId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
}

