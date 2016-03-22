/**
 * 
 */
package com.neu.msd.jpa.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Harsh
 *
 */
@Entity
public class Topic {
	@Id
	private int topic_id;
	private String topic_name;
	private List<ActivityContainer> activityContainers;
	
	public Topic() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return topic_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.topic_id = id;
	}

	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topic_name;
	}

	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topic_name = topicName;
	}
	
	/**
	 * @return the activityContainers
	 */
	public List<ActivityContainer> getActivityContainers() {
		return activityContainers;
	}

	/**
	 * @param activityContainers the activityContainers to set
	 */
	public void setActivityContainers(List<ActivityContainer> activityContainers) {
		this.activityContainers = activityContainers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activityContainers == null) ? 0 : activityContainers.hashCode());
		result = prime * result + topic_id;
		result = prime * result + ((topic_name == null) ? 0 : topic_name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (activityContainers == null) {
			if (other.activityContainers != null)
				return false;
		} else if (!activityContainers.equals(other.activityContainers))
			return false;
		if (topic_id != other.topic_id)
			return false;
		if (topic_name == null) {
			if (other.topic_name != null)
				return false;
		} else if (!topic_name.equals(other.topic_name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Topic [id=" + topic_id + ", topicName=" + topic_name + ", activityContainers=" + activityContainers + "]";
	}


}
