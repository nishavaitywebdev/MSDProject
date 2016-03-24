/**
 * 
 */
package com.neu.msd.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author HP
 *
 */
@Entity
public class User_Type {
	@Id
	private int user_type_id;
	private String user_type_desc;
	
	public User_Type() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return user_type_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.user_type_id = id;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return user_type_desc;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.user_type_desc = userType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + user_type_id;
		result = prime * result + ((user_type_desc == null) ? 0 : user_type_desc.hashCode());
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
		User_Type other = (User_Type) obj;
		if (user_type_id != other.user_type_id)
			return false;
		if (user_type_desc == null) {
			if (other.user_type_desc != null)
				return false;
		} else if (!user_type_desc.equals(other.user_type_desc))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserType [id=" + user_type_id + ", userType=" + user_type_desc + "]";
	}
	
}
