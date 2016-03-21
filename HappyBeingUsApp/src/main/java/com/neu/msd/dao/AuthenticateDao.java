/**
 * 
 */
package com.neu.msd.dao;

import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Mother;
import com.neu.msd.entities.MotherRegistration;
import com.neu.msd.exception.AuthenticationException;

/**
 * @author Harsh
 *
 */
public interface AuthenticateDao {
	
	public int registerDaughter(Daughter daughter) throws AuthenticationException;

	public Mother getMotherByEmail(String motherEmail) throws AuthenticationException;

	public Mother createMotherWithEmail(String email) throws AuthenticationException;

	public int registerDaughterAuthentication(int daughterId, DaughterRegistration daughterRegistration) throws AuthenticationException;

	public int updateMotherDetails(MotherRegistration motherRegistration) throws AuthenticationException;

}
