/**
 * 
 */
package com.neu.msd.service;

import java.util.List;

import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
public interface AdminServie {
	
	public List<Topic> loadTopics() throws AdminException;
}
