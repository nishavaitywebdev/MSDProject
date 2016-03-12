/**
 * 
 */
package com.neu.msd.dao;

import java.util.List;

import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
public interface AdminDao {

	public List<Topic> loadTopics() throws AdminException;
}
