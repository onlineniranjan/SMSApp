package com.sms.smsapp.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sms.smsapp.User;
import com.sms.smsapp.dao.UserDAO;

@Controller
public class ProcessMsg {

	private SessionFactory sf;
	
	@Autowired
    private UserDAO userDao;
	
	private static Logger logger = Logger.getLogger(ProcessMsg.class);
	
	
}
