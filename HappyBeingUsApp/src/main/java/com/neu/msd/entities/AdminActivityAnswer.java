/**
 * 
 */
package com.neu.msd.entities;

import java.util.Map;

/**
 * @author Harsh
 *
 */
public class AdminActivityAnswer {

	private Activity activity;
	private Map<Answer, Boolean> answers;
	
	public AdminActivityAnswer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	/**
	 * @return the answers
	 */
	public Map<Answer, Boolean> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(Map<Answer, Boolean> answers) {
		this.answers = answers;
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
