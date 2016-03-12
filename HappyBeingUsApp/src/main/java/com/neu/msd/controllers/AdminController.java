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
	public String loadAdminHome(Model model){
		
		try {
			List<Topic> topics = adminService.loadTopics();
			model.addAttribute("topics", topics);
		} catch (AdminException e) {
			return "errorPage";
		}
		
		return "adminHome";
	}
}
