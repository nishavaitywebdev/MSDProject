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
public class Version {
@Id
	private int version_id;
	private String version_name;
	
	public Version() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return version_id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.version_id = id;
	}

	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return version_name;
	}
	/**
	 * @param versionName the versionName to set
	 */
	public void setVersionName(String versionName) {
		this.version_name = versionName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + version_id;
		result = prime * result + ((version_name == null) ? 0 : version_name.hashCode());
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
		Version other = (Version) obj;
		if (version_id != other.version_id)
			return false;
		if (version_name == null) {
			if (other.version_name != null)
				return false;
		} else if (!version_name.equals(other.version_name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Version [id=" + version_id + ", versionName=" + version_name + "]";
	}
	
}
