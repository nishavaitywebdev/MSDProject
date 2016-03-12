/**
 * 
 */
package com.neu.msd.entities;

/**
 * @author Harsh
 *
 */
public class Activity {

	private int id;
	private String activityText;
	private int orderNo;
	private ActivityType activityType;
	private ActivityTemplate activityTemplate;
	private Topic topic;
	
	public Activity() {
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
	 * @return the activityText
	 */
	public String getActivityText() {
		return activityText;
	}

	/**
	 * @param activityText the activityText to set
	 */
	public void setActivityText(String activityText) {
		this.activityText = activityText;
	}

	/**
	 * @return the orderNo
	 */
	public int getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the activityType
	 */
	public ActivityType getActivityType() {
		return activityType;
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(ActivityType activityType) {
		this.activityType = activityType;
	}

	/**
	 * @return the activityTemplate
	 */
	public ActivityTemplate getActivityTemplate() {
		return activityTemplate;
	}

	/**
	 * @param activityTemplate the activityTemplate to set
	 */
	public void setActivityTemplate(ActivityTemplate activityTemplate) {
		this.activityTemplate = activityTemplate;
	}

	/**
	 * @return the topic
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
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
