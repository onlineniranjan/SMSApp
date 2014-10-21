package com.sms.smsapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.sms.smsapp.Events;
import com.sms.smsapp.User;
import com.sms.smsapp.UserDAOImpl;
import com.sms.smsapp.dao.UserDAO;

@Controller
public class AddUSer {
	
	private Logger logger = Logger.getLogger(AddUSer.class);
	private SessionFactory sf;
	
	@Autowired
    private UserDAO userDao;
     
    @RequestMapping(value="/list")
    public ModelAndView home() {
        List<User> listUsers = userDao.list();
        ModelAndView model = new ModelAndView("home");
        model.addObject("userList", listUsers);
        return model;
    }
	
	@RequestMapping(value="/AddUser")
	@ResponseStatus(value = HttpStatus.OK)
	public void AddUser(@RequestParam(value = "name", required = true) String name){
			
			User us = new User();
			
			us.setUsername(name);
			us.setPassword(name);
			us.setEmail("hhbbmm");
			
			userDao.save(us);
		
	}
	
	@RequestMapping(value="/AddEvent")
	@ResponseStatus(value = HttpStatus.OK)
	public void AddEvent(@RequestParam(value = "ename", required = true) String ename, 
						@RequestParam (value = "opt") String opt, 
						@RequestParam (value = "stime") String start,
						@RequestParam (value = "etime") String end,
						@RequestParam (value = "status") String active){
			
		Events event = new Events();
		event.setEventName(ename);
		event.setEventOptions(Integer.parseInt(opt));
		event.setStartTime(start);
		event.setEnd_time(end);
		event.setStatus(Integer.parseInt(active));
		
		userDao.saveEvent(event);
	}
	
	

}
