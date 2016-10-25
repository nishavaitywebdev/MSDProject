/**

 * @author SANIL AND VINAY 

 */

package com.neu.msd.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.Mother;

import com.neu.msd.entities.MotherRegistration;

import com.neu.msd.entities.UserAuthentication;
import com.neu.msd.entities.UserType;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.AdminService;
import com.neu.msd.entities.AdminActivityAnswer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations={"classpath:/dispatcher.xml"})
public class AdminTest {
	
	@Autowired
	AdminService adminService;

	
	
	@Test
	public void test_getDiagnosticList(){

		List<AdminActivityAnswer> diagnosticQuestionList= adminService.getDiagnosticQuestions();
		assertEquals(true, diagnosticQuestionList != null);
	
	}

	@Test
	public void test_checkDiagnosticListLength(){

		List<AdminActivityAnswer> diagnosticQuestionList= adminService.getDiagnosticQuestions();
		assertEquals(true, diagnosticQuestionList.size() > 0);
	
	}
	
}
