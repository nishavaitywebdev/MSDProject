/**
 * 
 */
package com.neu.msd.entities;

/**
 * @author Harsh
 *
 */
public class Score {
	
	private int id;
	private String scoreRange;
	
	public Score() {
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
	 * @return the scoreRange
	 */
	public String getScoreRange() {
		return scoreRange;
	}

	/**
	 * @param scoreRange the scoreRange to set
	 */
	public void setScoreRange(String scoreRange) {
		this.scoreRange = scoreRange;
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
