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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.Statement;
import com.neu.msd.dao.AdminDao;
import com.neu.msd.dao.UserDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.Answer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.TopicStatus;
import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.exception.UserException;

/**
 * @author Harsh
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Autowired
	DataSource dataSource;
	private Connection connection;
	

	@Autowired
	AdminDao adminDao;

	Map<Integer, ActivityTemplate> activityTemplateMap = new HashMap<Integer, ActivityTemplate>();
	Map<Integer, ActivityType> activityTypeMap = new HashMap<Integer, ActivityType>();

	public int getDiagnosticType() throws UserException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

		    connection = dataSource.getConnection();
			String sql = "select activity_type_id from activity_type where activity_type_desc = 'Diagnostic'";
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("activity_type_id");
			}

			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(e);
		}finally{
			try {
				if(null != keys) keys.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AuthenticationException(e);
			}
		}
	}

	public List<Activity> getActivitiesByType(int activityType) throws UserException {

		try {
			adminDao.loadActivityTemplate(activityTemplateMap);
			adminDao.loadActivityType(activityTypeMap);
		} catch (AdminException e) {
			e.printStackTrace();
		}

		List<Activity> activities = new ArrayList<Activity>();
		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity where activity_type_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityType);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Activity activity = new Activity();
				activity.setId(rs.getInt("activity_id"));
				activity.setActivityType(activityTypeMap.get(activityType));
				activity.setActivityTemplate(activityTemplateMap.get(rs.getInt("activity_template_id")));
				activity.setActivityText(rs.getString("activity_text"));
				activity.setOrderNo(rs.getInt("order_no"));
				activities.add(activity);
			}
			return activities;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(e);
		}finally{
			try {
				if(null != keys) keys.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AuthenticationException(e);
			}
		}
	}


	public Answer getAnswerById(int answerId) throws UserException {

		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from answer where answer_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, answerId);

			ResultSet rs = stmt.executeQuery();

			Answer answer = new Answer();
			while(rs.next()){
				
				answer.setId(rs.getInt("answer_id"));
				answer.setAnswerText(rs.getString("answer_desc"));
				answer.setOrderNo(rs.getInt("order_no"));
			}
			return answer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(e);
		}finally{
			try {
				if(null != keys) keys.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AuthenticationException(e);
			}
		}
	}

	@Override
	public List<Topic> getTopicsOfUser(int id) {
		List<Topic> list_of_topics = new ArrayList<Topic>();
		try {
			Connection connection = dataSource.getConnection();
			String sql = "SELECT uts.user_id, uts.topic_id, t.topic_name, ts.topic_status_id, ts.topic_status_desc "
						+ "FROM hbu.user_topic_status as uts "
						+ "INNER JOIN topic as t "
						+ "ON uts.topic_id = t.topic_id "
						+ "INNER JOIN topic_status as ts "
						+ "ON uts.topic_status_id = ts.topic_status_id where user_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setInt(1, id);
			ResultSet rs_topics_status = stmt.executeQuery();
			
			String sqlTopicProgress = "select max_activity.activity_container_id, max_activity.curr_activity_count, "
											+"max_activity.max_activity_count, "
											+"activity_container.order_no as curr_container_count, max_container.order_no as max_container_count "
											+"from activity_container, "
											+"(select curr_activity.activity_container_id, curr_activity.curr_activity_count, "
											+"count(*) max_activity_count "
											+"from activity, "
											+"(select activity_container_id, count(*) as curr_activity_count "
											+"from user_topic_container_activity_answer "
											+"where user_id = ? and topic_id = ? "
											+"group by activity_container_id "
											+"order by curr_activity_count limit 1) as curr_activity "
											+"where activity.activity_container_id = curr_activity.activity_container_id) max_activity, "
											+"(select order_no from activity_container where topic_id=? order by order_no desc limit 1) max_container "
											+"where activity_container.activity_container_id = max_activity.activity_container_id";
			PreparedStatement stmtTopicProgress = connection.prepareStatement(sqlTopicProgress);
			
			
			
			while (rs_topics_status.next()){
				Topic topic = new Topic();
				topic.setId(rs_topics_status.getInt("topic_id"));
				topic.setTopicName(rs_topics_status.getString("topic_name"));
				TopicStatus ts = new TopicStatus();
				ts.setId(rs_topics_status.getInt("topic_status_id"));
				ts.setTopicStatus(rs_topics_status.getString("topic_status_desc"));
				
				topic.setTopicStatus(ts);
				
				stmtTopicProgress.setInt(1, id);
				stmtTopicProgress.setInt(2, rs_topics_status.getInt("topic_id"));
				stmtTopicProgress.setInt(3, rs_topics_status.getInt("topic_id"));
				ResultSet rsTopicProgress = stmtTopicProgress.executeQuery();
				
				if (rsTopicProgress.next()){
					if (rsTopicProgress.getInt("curr_activity_count") < rsTopicProgress.getInt("max_activity_count")){
						topic.setCompletedActContainers(rsTopicProgress.getInt("curr_container_count")-1);
					}else{
						topic.setCompletedActContainers(rsTopicProgress.getInt("curr_container_count"));
					}
					
					topic.setMaxActContainers(rsTopicProgress.getInt("max_container_count"));
				}
				topic.setProgress();	
				list_of_topics.add(topic);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(null != keys) keys.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AuthenticationException(e);
			}
		}

		return list_of_topics;
	}

	@Override
	public void addscoreforuser(User user, double score) {
		// TODO Auto-generated method stub
		try {
			int sco=(int)score;
			Connection connection = dataSource.getConnection();
			String sql = "select score_range from score where usertype = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1,2 );
			ResultSet rs = stmt.executeQuery();
			List<Integer> score_range=new ArrayList<Integer>();
			while (rs.next()) {

				score_range.add(rs.getInt("score_range"));
			
			}
			int score_id=1;
		 for(Integer r:score_range)
		 {
			 if (sco<=r)
			 {
				 break;
			 }
			 score_id++;
		 }
		 
			sql = "select version_id from version_score where score_id= ?";
		    stmt = connection.prepareStatement(sql);
			stmt.setInt(1,score_id);
			rs = stmt.executeQuery();
			int version_id = 0;
			while (rs.next()) {

				version_id=rs.getInt("version_id");
			
			}

			sql = "update user set is_diagnostic_taken = ?, version_id = ?, score= ? where user_id = ?";
			PreparedStatement stmt3 = connection.prepareStatement(sql);
			stmt3.setInt(1, 1);
			stmt3.setInt(2, version_id);
			stmt3.setInt(3, sco);
			stmt3.setInt(4, user.getId());
			int records = stmt3.executeUpdate();
			System.out.println("No. of records inserted: " + records);
			 
			sql = "select topic_id from version_topic where version_id= ?";
		    stmt = connection.prepareStatement(sql);
			stmt.setInt(1,version_id);
			rs = stmt.executeQuery();
			List<Integer> topics=new ArrayList<Integer>();
			while (rs.next()) {

				topics.add(rs.getInt("topic_id"));
			
			}
                for(Integer topic_id:topics)
                {
                	sql = "insert into user_topic_status (user_id, topic_id, topic_status_id) "
        					+ " values (?, ?, ?)";
        			stmt = connection.prepareStatement(sql );
        			
        			stmt.setInt(1, user.getId());
        			stmt.setInt(2, topic_id);
        			stmt.setInt(3,1);
        			
        			records = stmt.executeUpdate();
        			
        			System.out.println("No. of records inserted: "+records);
                }
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new AuthenticationException(e);
			} catch (AuthenticationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}finally{
			try {
				if(null != keys) keys.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AuthenticationException(e);
			}
		}
	}

	@Override
	public Integer[] getweigh() throws SQLException, AuthenticationException  {
		// TODO Auto-generated method stub
		Connection connection = dataSource.getConnection();
		String sql = "Select * from activity_score";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Integer> weighlist=new ArrayList<Integer>();
		while (rs.next()) {

			weighlist.add(rs.getInt("score"));
		
		}
		Integer[] weighs=weighlist.toArray(new Integer[weighlist.size()]);
		
			try {
				if(null != rs) rs.close();
				if(null != stmt) stmt.close();
				if(null != connection) connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AuthenticationException(e);
			}
	
		
		
		return weighs;
	}

}
