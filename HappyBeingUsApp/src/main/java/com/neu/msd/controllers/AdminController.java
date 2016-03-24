/**
 * 
 */
package com.neu.msd.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neu.msd.entities.Activity;
import com.neu.msd.entities.ActivityContainer;
import com.neu.msd.entities.ActivityTemplate;
import com.neu.msd.entities.ActivityType;
import com.neu.msd.entities.DaughterRegistration;
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
	
	@RequestMapping(value="/newActivityLink.action", method=RequestMethod.POST)
	public String goToNewActivity(@RequestParam("id") String activityContainerId, Model model){
		
		Activity activity = new Activity();
		ActivityType activityType = new ActivityType();
		activityType.setId(3);
		activity.setActivityType(activityType);
		activity.getActivityContainer().setActivityContainerId(Integer.valueOf(activityContainerId));
		
		try {
			List<ActivityTemplate> activityTemplates = adminService.getAllActivityTemplates();
			model.addAttribute("activityTemplates", activityTemplates);
			model.addAttribute("activity", activity);
			return "newActivity";
		} catch (AdminException e1) {
			return "errorPage";
		}
	}
	
	@RequestMapping(value="/addActivity.action", method=RequestMethod.POST)
	public String addNewActivity(@ModelAttribute("activity") Activity activity, Model model){
		
		System.out.println(activity);
		return "activityContainer";
	}
	
	@ResponseBody
	@RequestMapping(value="/renameTopic.action", method=RequestMethod.POST)
	public String renameTopic(@RequestParam("topicName") String topicName, @RequestParam("topicId") String topicId, Model model){
		
		try {
			return String.valueOf(adminService.renameTopic(topicName, topicId));
		} catch (AdminException e) {
			return "error";
		}
	}
}
