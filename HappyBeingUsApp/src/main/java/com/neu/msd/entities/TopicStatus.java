/**
 * 
 */
package com.neu.msd.entities;

/**
 * @author Harsh
 *
 */
public class TopicStatus {

	private int id;
	private String topicStatus;
	
	public TopicStatus() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the topicStatus
	 */
	public String getTopicStatus() {
		return topicStatus;
	}

	/**
	 * @param topicStatus the topicStatus to set
	 */
	public void setTopicStatus(String topicStatus) {
		this.topicStatus = topicStatus;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	 
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
