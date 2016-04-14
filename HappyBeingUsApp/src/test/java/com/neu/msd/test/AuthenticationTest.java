/**

 * 

 */

package com.neu.msd.test;



import static org.junit.Assert.assertEquals;

import com.neu.msd.entities.Mother;

import com.neu.msd.entities.MotherRegistration;

import com.neu.msd.entities.User;



import org.junit.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



import com.neu.msd.exception.AdminException;

import com.neu.msd.exception.AuthenticationException;

import com.neu.msd.exception.UserException;

import com.neu.msd.service.AdminService;

import com.neu.msd.service.AuthenticateService;

import com.neu.msd.service.UserService;



/**

 * @author Harsh

 *

 */

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"classpath:/dispatcher.xml"})

public class AuthenticationTest {



	@Autowired

	AuthenticateService authenticateService;


	@Autowired

	AdminService adminService;


	@Autowired

	UserService userService;


	@Test

	public void test_emailExistCheck(){


		String emailExistCheck;


		try {

			emailExistCheck = authenticateService.checkEmail("itsnisha07@gmail.com");

			assertEquals("", emailExistCheck);

		} catch (AuthenticationException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}


	/* Mohsen Nabian*/


	@Test


	public void test_usernameExistCheck(){


		String usernameExistCheck;


		try {

			usernameExistCheck = authenticateService.checkUname("boss2");

			assertEquals("boss2"+", this username available", usernameExistCheck);

		} catch (AuthenticationException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}


	/* Mohsen Nabian*/


	@Test


	public void test_resetUnamePassword(){


		String resetUnamePasswordCheck;


		try {

			resetUnamePasswordCheck = authenticateService.resetUnamePassword("boss2","boss2","boss2");

			assertEquals("Account does not exist. Try Sign Up...", resetUnamePasswordCheck);

		} catch (AuthenticationException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}



	/* Mohsen Nabian*/


	@Test


	public void test_getDiagnostic(){


		try {

			int q = userService.getDiagnosticQuestions().size();

			assertEquals(5, q);

		} catch (UserException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		} catch (AdminException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}


	}



	/* Mohsen Nabian*/


	@Test


	public void test_getTopicsUser(){

		User user = new User();

		user.setId(9);

		try {

			int q = userService.getTopicsOfUser(user).size();

			assertEquals(3, q);

		} catch (UserException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}


	}

	/* Mohsen Nabian*/


	@Test


	public void test_getAllActivityTemplates(){



		int q = 0;

		try {

			q = adminService.getAllActivityTemplates().size();

		} catch (AdminException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);



	}




	/* Mohsen Nabian*/


	@Test


	public void test_getTopicsUser2(){

		User user = new User();

		user.setId(9);

		try {

			int q = userService.getTopicsOfUser(user).size();

			assertEquals(3, q);

		} catch (UserException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}


	}

	/* Mohsen Nabian*/


	@Test


	public void test_getAllActivityTemplates2(){



		int q = 0;

		try {

			q = adminService.getAllActivityTemplates().size();

		} catch (AdminException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);



	}




	/* Mohsen Nabian*/


	@Test


	public void test_getTopicsUser1(){

		User user = new User();

		user.setId(9);

		try {

			int q = userService.getTopicsOfUser(user).size();

			assertEquals(3, q);

		} catch (UserException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}


	}

	/* Mohsen Nabian*/


	@Test


	public void test_getAllActivityTemplates1(){



		int q = 0;

		try {

			q = adminService.getAllActivityTemplates().size();

		} catch (AdminException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);



	}
	
	@Test


	public void test_renameTopic(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);



	}
	
	@Test


	public void test_addTopic(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);



	}
	
	@Test


	public void test_deleteTopic(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);



	}
	
	@Test


	public void test_getActivityContainerById(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);
	}
	
	@Test


	public void test_loadTopics(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);



	}
	
	@Test


	public void test_adminAuthenticate(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	
	@Test


	public void test_addNewActivityContainer(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	
	@Test


	public void test_deleteActivityContainer(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	
	@Test


	public void test_renameActivityContainer(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	
	@Test


	public void test_checkUname(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	
	@Test


	public void test_checkEmail(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	
	@Test


	public void test_validUser(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	
	@Test


	public void test_validUser1(){



		int q = 0;

		try {

			//q = adminService.getAllActivityTemplates().size();

		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		assertEquals(5, q);

	}
	


	/* Mohsen Nabian*/

	//getAllActivityTemplates()

	//@SuppressWarnings("null")

	//@Test

	//

	//public void test_updateMotherDetails(){

	//

	//	int updateMotherDetailsCheck;

	//	Mother motherobj = null;

	//	MotherRegistration motherregobj;

	//	MotherRegistration motherregobjTest;

	//	int x = 0;

	//	try {

	//	motherregobj = authenticateService.getMotherRegistrationByEmail("nishaMother@gmail.com");

	//	motherobj.setFirstName("NishaMom");

	//	motherobj.setLastName("Vaity");

	//	motherregobj.setUsername("username");

	//	motherregobj.setPassword("password");

	//	motherregobj.setMother(motherobj);

	//	x = authenticateService.updateMotherDetails(motherregobj);

	//	} catch (AuthenticationException e1) {

	//	// TODO Auto-generated catch block

	//	e1.printStackTrace();

	//	}

	//

	//

	//	assertEquals(1, x);

	//

	//}



}

