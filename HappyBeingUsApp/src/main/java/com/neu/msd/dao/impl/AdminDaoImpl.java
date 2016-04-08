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
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			List<Topic> topics = new ArrayList<Topic>();
			connection = dataSource.getConnection();
			String sql = "select * from topic";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
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
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: loadTopics: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AdminDao#loadAdminContainersByTopicId(int)
	 */
	public List<ActivityContainer> loadActivityContainersByTopicId(int topicId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: loadActivityContainersByTopicId: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			List<ActivityContainer> activityContainers = new ArrayList<ActivityContainer>();
			connection = dataSource.getConnection();
			String sql = "select * from activity_container where topic_id="+topicId+" order by order_no";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
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
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: loadActivityContainersByTopicId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.neu.msd.dao.AdminDao#loadActivityContainerById(int)
	 */
	public ActivityContainer loadActivityContainerById(int activityContainerId) throws AdminException {

		LOGGER.debug("AdminDaoImpl: loadActivityContainerById: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			ActivityContainer activityContainer = new ActivityContainer();
			connection = dataSource.getConnection();
			String sql = "select * from activity_container where activity_container_id="+activityContainerId;
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
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
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: loadActivityContainerById: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
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
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			List<Activity> activities= new ArrayList<Activity>();
			connection = dataSource.getConnection();
			String sql = "select * from activity where activity_container_id=? order by order_no";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityContainerId);
			
			rs = stmt.executeQuery();
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
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: loadActivitiesByActivityContainerId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
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
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			String sql = "select * from activity_type";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
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
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: loadActivityType: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public List<ActivityTemplate> getAllActivityTemplates() throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: getAllActivityTemplates: START");
		List<ActivityTemplate> activityTemplates = new ArrayList<ActivityTemplate>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			String sql = "select * from activity_template";
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()){
				ActivityTemplate activityTemplate = new ActivityTemplate();
				int activityTemplateId = rs.getInt("activity_template_id");
				activityTemplate.setId(activityTemplateId);
				activityTemplate.setTemplateName(rs.getString("activity_template_desc"));
				activityTemplates.add(activityTemplate);
			}
			
			return activityTemplates;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: getAllActivityTemplates: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	

	public int renameTopic(String topicName, int topicId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: renameTopic: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
//			update topic set topic_name = ? where topic_id = ?
			String sql = "update topic set topic_name = ? where topic_id = ?";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, topicName);
			stmt.setInt(2, topicId);
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records updated: "+records);

			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: renameTopic: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	public AdminActivityAnswer getAdminActivityAnswerByActivityId(int activityId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: getAdminActivityAnswerByActivityId: START");
		AdminActivityAnswer adminActivityAnswer = new AdminActivityAnswer();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			String sql = "select * from admin_activity_answer where activity_id=?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityId);

			rs = stmt.executeQuery();
			List<Answer> answers = new ArrayList<Answer>();
			while(rs.next()){
				Answer answer = new Answer();
				answer.setId(rs.getInt("answer_id"));
				answer.setCorrect(rs.getInt("is_correct")==1);
				answers.add(answer);
			}
			adminActivityAnswer.setAnswers(answers);

			return adminActivityAnswer;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: getAdminActivityAnswerByActivityId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public User authenticateAdminByUsernamePassword(UserAuthentication userAuthentication) throws AdminException {
		LOGGER.debug("AdminDaoImpl: authenticateAdminByUsernamePassword: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PreparedStatement stmt_get_admin = null;
		ResultSet rs_get_admin = null;
		
		try {
			User admin = new User();
			connection = dataSource.getConnection();
			String sql = "select * from user_authentication where username=? and password = ? and user_type_id = 1";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, userAuthentication.getUsername());
			stmt.setString(2, userAuthentication.getPassword());
			rs = stmt.executeQuery();
			while(rs.next())
			{
				admin.setId(rs.getInt("user_id"));
			}
			
			if (admin.getId()!=0)
			{
				String sql_get_admin = "select * from user where user_id = ?";
				stmt_get_admin = connection.prepareStatement(sql_get_admin);
				stmt_get_admin.setInt(1, admin.getId());
				rs_get_admin = stmt_get_admin.executeQuery();
				while(rs_get_admin.next()){
					admin.setFirstName(rs_get_admin.getString("first_name"));
				}
			}

			return admin;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != rs_get_admin) rs_get_admin.close();
				if(null != stmt_get_admin) stmt_get_admin.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: authenticateAdminByUsernamePassword: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public int addTopic(String topicName) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: addTopic: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			
			int nextTopicId = getNextTopicId(connection);
			String sql = "insert into topic values(?, ?)";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, nextTopicId);
			stmt.setString(2, topicName);
			
			int records = stmt.executeUpdate();
			
			System.out.println("New topic inserted, no. of records inserted: "+records);

			return nextTopicId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: addTopic: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	private int getNextTopicId(Connection connection) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextTopicId: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select MAX(topic_id) as topic_id from topic";

			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {

				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: getNextTopicId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	public int deleteTopic(int deletableId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: deleteTopic: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "delete from topic where topic_id=?";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, deletableId);
			
			int records = stmt.executeUpdate();
			
			System.out.println("Topic with topic id:"+deletableId+", deleted. No. of records deleted: "+records);

			LOGGER.debug("AdminDaoImpl: deleteTopic: END");
			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: getNextTopicId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	public int deleteActivityContainer(int deletableId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: deleteActivityContainer: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "select order_no, topic_id from activity_container where activity_container_id=?";
			
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, deletableId);
			
			rs = stmt.executeQuery();
			
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

			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: deleteActivityContainer: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	private void reorderActivityContainers(Connection connection, int orderNo, int topicId) throws AdminException{
		
		LOGGER.debug("AdminDaoImpl: reorderActivityContainers: START");
		
		PreparedStatement stmt = null;
		try {
			
			List<ActivityContainer> activityContainers = loadActivityContainersByTopicId(topicId);
			
			for(ActivityContainer activityContainer : activityContainers){
				if(activityContainer.getOrderNo() > orderNo){
					String sql = "update activity_container set order_no=? where activity_container_id=?";
					
					stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
		}finally{
			try {
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: reorderActivityContainers: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
		
	}

	public ActivityContainer addNewActivityContainer(String containerName, int topicId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: addNewActivityContainer: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			
			int nextContainerId = getNextContainerId(connection);
			
			int orderNo = getNextContainerOrderNo(connection, topicId);
			
			String sql = "insert into activity_container values (?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
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

			return activityContainer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: addNewActivityContainer: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	private int getNextContainerId(Connection connection) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextContainerId: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select MAX(activity_container_id) as activity_container_id from activity_container";

			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: getNextContainerId: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	private int getNextContainerOrderNo(Connection connection, int topicId) throws AdminException{
		LOGGER.debug("AdminDaoImpl: getNextContainerOrderNo: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			String sql = "select MAX(order_no) as order_no from activity_container where topic_id=?";
			
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, topicId);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return rs.getInt(1) + 1;
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: getNextContainerOrderNo: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	@Override
	public int deleteActivity(Integer deletableId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: deleteActivity: START");
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			connection = dataSource.getConnection();
			
			String sql = "select order_no, activity_container_id from activity where activity_id=?";
			
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, deletableId);
			
			rs = stmt.executeQuery();
			
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
			
			return records;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
				LOGGER.debug("AdminDaoImpl: deleteActivity: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}

	private void reorderActivites(Connection connection, int orderNo, int containerId) throws AdminException {
		
		LOGGER.debug("AdminDaoImpl: reorderActivites: START");
		
		PreparedStatement stmt = null;
		try {
			
			List<Activity> activities = loadActivitiesByActivityContainerId(containerId);
			
			for(Activity activity : activities){
				if(activity.getOrderNo() > orderNo){
					String sql = "update activity set order_no=? where activity_id=?";
					
					stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
		}finally{
			try {
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: reorderActivites: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
	
	public int renameActivityContainer(String containerName, int containerId) throws AdminException {
		LOGGER.debug("AdminDaoImpl: renameActivityContainer: START");
		
		PreparedStatement stmt = null;
		try {
			connection = dataSource.getConnection();
			String sql = "update activity_container set activity_container_name = ? where activity_container_id = ?";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, containerName);
			stmt.setInt(2, containerId);
			
			int records = stmt.executeUpdate();
			
			System.out.println("No. of records updated: "+records);

			return records;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AdminException(e);
		}finally{
			try {
				if(null != stmt) stmt.close();
				LOGGER.debug("AdminDaoImpl: renameActivityContainer: END");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AdminException(e);
			}
		}
	}
}

