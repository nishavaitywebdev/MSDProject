/**
 * 
 */
package com.neu.msd.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neu.msd.exception.AuthenticationException;
import com.neu.msd.service.AuthenticateService;

/**
 * @author Harsh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dispatcher-servlet.xml")
public class AuthenticationTest {

	@Autowired
	AuthenticateService authenticateService;
	
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
}
