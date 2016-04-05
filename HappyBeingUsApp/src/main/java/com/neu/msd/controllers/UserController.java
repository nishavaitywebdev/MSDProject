/**
 * 
 */
package com.neu.msd.controllers;

import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.neu.msd.entities.AdminActivityAnswer;
import com.neu.msd.entities.Topic;
import com.neu.msd.entities.User;
import com.neu.msd.entities.Scoremodel;
import com.neu.msd.entities.User;
import com.neu.msd.exception.AdminException;
import com.neu.msd.exception.UserException;
import com.neu.msd.service.UserService;

/**
 * @author Harsh
 *
 */
@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
//	TODO render the diagnostic page with the data from the database.
	
	
	/**
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/redirectToDiagnostic.action", method=RequestMethod.GET)
	public String redirectToDiagnostic(Model model){
		
		try {
			List<AdminActivityAnswer> activityAnswers = userService.getDiagnosticQuestions();
			model.addAttribute("activityAnswers", activityAnswers);
			return "diagnostic";
		} catch (UserException e) {
			return "errorPage";
		} catch (AdminException e) {
			return "errorPage";
		}
	}
	@RequestMapping(value="/loadUserTopicsPage.action", method=RequestMethod.GET)
	public String loadTopicsOfUser(HttpSession session, Model model){
		try{
			User user = new User();
			user = (User) session.getAttribute("user");
			if (user.isDiagnosticTaken()== false){
				return redirectToDiagnostic(model);
			}
			else{
				List<Topic> topics = new ArrayList<Topic>();
				topics = userService.getTopicsOfUser(user);
				model.addAttribute("topics", topics);
			}
			
		}
		catch (UserException ex){
			return "error";
		}
		return "topics";
	}
	
	
	
	
	
	
	@RequestMapping(value="/dg.action", method=RequestMethod.POST)
	public String redirectToUserhome(Scoremodel scores,HttpSession session) throws SQLException{
		User user=(User) session.getAttribute("user");
		
	
		Integer[] weigh=userService.getweigh();
		
		
		
		int b=-1;
		double score=0;
		for(Integer a:scores.getScores())
		{
			if (a!=null)
				score+=(double)(((double)(a-1))/(double)weigh[b]);
			b++;
		}
		score=score/5*100;
		userService.addscore(user,score);
		
		//List<AdminActivityAnswer> activityAnswers = userService.getDiagnosticQuestions();
		//model.addAttribute("activityAnswers", activityAnswers);
		return "userHome";
	}

}
