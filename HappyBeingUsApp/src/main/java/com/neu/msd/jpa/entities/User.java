/**
 * 
 */
package com.neu.msd.jpa.entities;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * @author Harsh + Dahang
 *
 */
@Entity
public class User {
	@Id
	private int user_id;
	@ManyToOne
	@JoinColumn(name = "user_type_id")
	private User_Type user_type;
	private String first_name;
	private String last_name;
	private String email_id;
	private boolean is_diagnostic_taken;
	@ManyToOne
	@JoinColumn(name = "version_id")
	private Version version;
	@ManyToOne
	@JoinColumn(name = "score_id")
	private Score score;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return user_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.user_id = id;
	}

	/**
	 * @return the userType
	 */
	public User_Type getUserType() {
		return user_type;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(User_Type userType) {
		this.user_type = userType;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return first_name;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.first_name = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return last_name;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	/**
	 * @return the birthdate
	 */
//	public Date getBirthdate() {
	//	return birthdate;
	//}

	/**
	 * @param birthdate the birthdate to set
	 */
//	public void setBirthdate(Date birthdate) {
//		this.birthdate = birthdate;
//	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email_id;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email_id = email;
	}


	/**
	 * @return the isDiagnosticTaken
	 */
	public boolean isDiagnosticTaken() {
		return is_diagnostic_taken;
	}

	/**
	 * @param isDiagnosticTaken the isDiagnosticTaken to set
	 */
	public void setDiagnosticTaken(boolean isDiagnosticTaken) {
		this.is_diagnostic_taken = isDiagnosticTaken;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((birthdate == null) ? 0 : birthdate.hashCode());
		result = prime * result + ((email_id == null) ? 0 : email_id.hashCode());
		result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
		result = prime * result + user_id;
		result = prime * result + (is_diagnostic_taken ? 1231 : 1237);
		result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((user_type == null) ? 0 : user_type.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		User other = (User) obj;
//		if (birthdate == null) {
//			if (other.birthdate != null)
//				return false;
//		} else if (!birthdate.equals(other.birthdate))
//			return false;
		if (email_id == null) {
			if (other.email_id != null)
				return false;
		} else if (!email_id.equals(other.email_id))
			return false;
		if (first_name == null) {
			if (other.first_name != null)
				return false;
		} else if (!first_name.equals(other.first_name))
			return false;
		if (user_id != other.user_id)
			return false;
		if (is_diagnostic_taken != other.is_diagnostic_taken)
			return false;
		if (last_name == null) {
			if (other.last_name != null)
				return false;
		} else if (!last_name.equals(other.last_name))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (user_type == null) {
			if (other.user_type != null)
				return false;
		} else if (!user_type.equals(other.user_type))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [id=" + user_id + ", userType=" + user_type + ", firstName=" + first_name + ", lastName=" + last_name
				+ ", birthdate=" + ", email=" + email_id + ", isDiagnosticTaken=" + is_diagnostic_taken
				+ ", version=" + version + ", score=" + score + "]";
	}

	
}
