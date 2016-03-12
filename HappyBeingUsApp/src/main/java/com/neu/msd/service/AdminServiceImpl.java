/**
 * 
 */
package com.neu.msd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neu.msd.dao.AdminDao;
import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;

/**
 * @author Harsh
 *
 */
@Service
public class AdminServiceImpl implements AdminServie {

	@Autowired
	private AdminDao adminDao;
	
	/* (non-Javadoc)
	 * @see com.neu.msd.service.AdminServie#loadTopics()
	 */
	public List<Topic> loadTopics() throws AdminException {
		return adminDao.loadTopics();
	}

}