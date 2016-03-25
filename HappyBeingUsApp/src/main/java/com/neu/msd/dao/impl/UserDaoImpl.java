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
import com.neu.msd.dao.UserDao;
import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;

/**
 * @author Harsh
 *
 */

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	AdminDao adminDao;
	
	Map<Integer, ActivityTemplate> activityTemplateMap = new HashMap<Integer, ActivityTemplate>();
	Map<Integer, ActivityType> activityTypeMap = new HashMap<Integer, ActivityType>();
	
	public UserDaoImpl() {
		try {
			adminDao.loadActivityTemplate(activityTemplateMap);
			adminDao.loadActivityType(activityTypeMap);
		} catch (AdminException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getDiagnosticType() throws UserException {
		
		try {
			
			Connection connection = dataSource.getConnection();
			String sql = "select activity_type_id from activity_type where activity_type_desc = 'Diagnostic'";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("activity_type_id");
			}
			
			throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(e);
		}	
	}

	public List<Activity> getActivitiesByType(int activityType) throws UserException {
		
		List<Activity> activities = new ArrayList<Activity>();
		try {
			Connection connection = dataSource.getConnection();
			String sql = "select * from activity where activity_type_id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, activityType);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
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
		}
	}

}
