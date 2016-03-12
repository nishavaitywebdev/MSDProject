/**
 * 
 */
package com.neu.msd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
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

}
