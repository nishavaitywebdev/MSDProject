/**
 * 
 */
package com.neu.msd.service;

import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Mother;
import com.neu.msd.entities.MotherRegistration;
import com.neu.msd.exception.AuthenticationException;

/**
 * @author Harsh
 *
 */
public interface AuthenticateService {
	
	public int registerDaughter(DaughterRegistration daughterRegistration) throws AuthenticationException;
	
	public Mother registerMother(MotherRegistration motherRegistration) throws AuthenticationException;
	
	public Mother getMotherByEmail(String motherEmail) throws AuthenticationException;

}
