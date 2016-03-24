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
public class Answer {
@Id
	private int answer_id;
	private String answer_desc;
	
	public Answer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return answer_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.answer_id = id;
	}

	/**
	 * @return the answerText
	 */
	public String getAnswerText() {
		return answer_desc;
	}

	/**
	 * @param answerText the answerText to set
	 */
	public void setAnswerText(String answerText) {
		this.answer_desc = answerText;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer_desc == null) ? 0 : answer_desc.hashCode());
		result = prime * result + answer_id;
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
		Answer other = (Answer) obj;
		if (answer_desc == null) {
			if (other.answer_desc != null)
				return false;
		} else if (!answer_desc.equals(other.answer_desc))
			return false;
		if (answer_id != other.answer_id)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Answer [id=" + answer_id + ", answerText=" + answer_desc + "]";
	}
	
}
