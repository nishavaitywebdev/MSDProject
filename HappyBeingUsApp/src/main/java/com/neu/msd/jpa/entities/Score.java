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
public class Score {
	@Id
	private int score_id;
	private String score_range;
	
	public Score() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return score_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.score_id = id;
	}

	/**
	 * @return the scoreRange
	 */
	public String getScoreRange() {
		return score_range;
	}

	/**
	 * @param scoreRange the scoreRange to set
	 */
	public void setScoreRange(String scoreRange) {
		this.score_range = scoreRange;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + score_id;
		result = prime * result + ((score_range == null) ? 0 : score_range.hashCode());
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
		Score other = (Score) obj;
		if (score_id != other.score_id)
			return false;
		if (score_range == null) {
			if (other.score_range != null)
				return false;
		} else if (!score_range.equals(other.score_range))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Score [id=" + score_id + ", scoreRange=" + score_range + "]";
	}
	
}
