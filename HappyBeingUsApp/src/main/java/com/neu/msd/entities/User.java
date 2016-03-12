/**
 * 
 */
package com.neu.msd.entities;

import java.util.Date;

/**
 * @author Harsh
 *
 */
public class User {

	private int id;
	private UserType userType;
	private String firstName;
	private String lastName;
	private Date birthdate;
	private String email;
	private User parent;
	private boolean isDiagnosticTaken;
	private Version version;
	private Score score;
	
	public User() {
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
	 * @return the userType
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the parent
	 */
	public User getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(User parent) {
		this.parent = parent;
	}

	/**
	 * @return the isDiagnosticTaken
	 */
	public boolean isDiagnosticTaken() {
		return isDiagnosticTaken;
	}

	/**
	 * @param isDiagnosticTaken the isDiagnosticTaken to set
	 */
	public void setDiagnosticTaken(boolean isDiagnosticTaken) {
		this.isDiagnosticTaken = isDiagnosticTaken;
	}

	/**
	 * @return the version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}

	/**
	 * @return the score
	 */
	public Score getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(Score score) {
		this.score = score;
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
