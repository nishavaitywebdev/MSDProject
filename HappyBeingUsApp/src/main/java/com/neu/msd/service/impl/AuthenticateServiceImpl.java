/**
 * 
 */
package com.neu.msd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.neu.msd.dao.AuthenticateDao;
import com.neu.msd.entities.Daughter;
import com.neu.msd.entities.DaughterRegistration;
import com.neu.msd.entities.Mother;
import com.neu.msd.entities.MotherRegistration;
import com.neu.msd.entities.User;
import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.service.AuthenticateService;

/**
 * @author Harsh
 *
 */
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private AuthenticateDao authenticateDao;

	/**
	 * 
	 */
	public AuthenticateServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.neu.msd.service.AuthenticateService#registerDaughter(com.neu.msd.
	 * entities.DaughterRegistration)
	 */
	public int registerDaughter(DaughterRegistration daughterRegistration) throws AuthenticationException {

		MotherRegistration motherRegistration = new MotherRegistration();
		motherRegistration.setMother(daughterRegistration.getDaughter().getMother());
		// Check if the daughter has already registered mother, if not create a
		// userId for the mother.
		Mother mother = registerMother(motherRegistration);
		daughterRegistration.getDaughter().setMother(mother);
		int daughterId = authenticateDao.registerDaughter(daughterRegistration.getDaughter());
		return authenticateDao.registerDaughterAuthentication(daughterId, daughterRegistration);
	}

	public Mother registerMother(MotherRegistration motherRegistration) throws AuthenticationException {

		String motherEmail = motherRegistration.getMother().getEmail();
		Mother mother = getMotherByEmail(motherEmail);
		if (null == mother.getEmail() || !mother.getEmail().equalsIgnoreCase(motherEmail)) {
			return authenticateDao.createMotherWithEmail(motherEmail);
		}
		return mother;
	}

	public int validUser(UserAuthentication userAuthentication) throws AuthenticationException {

		int id = authenticateDao.validUser(userAuthentication.getUsername(), userAuthentication.getPassword());
		return id;
	}

	public Mother getMotherByEmail(String motherEmail) throws AuthenticationException {
		return authenticateDao.getMotherByEmail(motherEmail);
	}

}
