/**
 * 
 */
package com.neu.msd.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Harsh
 *
 */


@Entity
public class topic_status {
@Id
	private int topic_status_id;
	private String topic_status_desc;
	
	public topic_status() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return topic_status_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.topic_status_id = id;
	}

	/**
	 * @return the topicStatus
	 */
	public String getTopicStatus() {
		return topic_status_desc;
	}

	/**
	 * @param topicStatus the topicStatus to set
	 */
	public void setTopicStatus(String topicStatus) {
		this.topic_status_desc = topicStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + topic_status_id;
		result = prime * result + ((topic_status_desc == null) ? 0 : topic_status_desc.hashCode());
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
		topic_status other = (topic_status) obj;
		if (topic_status_id != other.topic_status_id)
			return false;
		if (topic_status_desc == null) {
			if (other.topic_status_desc != null)
				return false;
		} else if (!topic_status_desc.equals(other.topic_status_desc))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TopicStatus [id=" + topic_status_id + ", topicStatus=" + topic_status_desc + "]";
	}
	
}
