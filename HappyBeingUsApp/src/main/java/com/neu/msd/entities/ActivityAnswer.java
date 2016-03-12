/**
 * 
 */
package com.neu.msd.entities;

import java.util.List;

/**
 * @author Harsh
 *
 */
public class ActivityAnswer {
	
	private Activity activity;
	private List<Answer> answers;
	private String answerDesc;
	
	public ActivityAnswer() {
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
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answers to set
	 */
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	/**
	 * @return the answerDesc
	 */
	public String getAnswerDesc() {
		return answerDesc;
	}

	/**
	 * @param answerDesc the answerDesc to set
	 */
	public void setAnswerDesc(String answerDesc) {
		this.answerDesc = answerDesc;
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
