/**
 * 
 */
package com.neu.msd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.Topic;
import com.neu.msd.exception.AdminException;
import com.neu.msd.service.AdminServie;

/**
 * @author Harsh
 *
 */
@Controller
public class AdminController {
	
	@Autowired
	private AdminServie adminService;
	
	@RequestMapping(value="/adminHome.action", method=RequestMethod.GET)
	public String loadHome(Model model){
		
		try {
			List<Topic> topics = adminService.loadTopics();
			model.addAttribute("topics", topics);
			return "adminHome";
		} catch (AdminException e) {
			return "errorPage";
		}
	}
	
	@RequestMapping(value="/editActivityContainer.action", method=RequestMethod.POST)
	public String loadContainerById(@RequestParam("id") String activityContainerId, Model model){
		
		ActivityContainer activityContainer = new ActivityContainer();
		try {
			activityContainer = adminService.getActivityContainerById(Integer.valueOf(activityContainerId));
			model.addAttribute("activityContainer", activityContainer);
			return "activityContainer";
		} catch (AdminException e) {
			return "errorPage";
		}
	}
}
