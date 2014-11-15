package com.sms.smsapp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.sms.smsapp.Events;
import com.sms.smsapp.User;
import com.sms.smsapp.Vote;
import com.sms.smsapp.Votes;
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
    
    @RequestMapping(value="/displayVotes")
    public ModelAndView voteDisplay(@RequestParam(value = "eventId", required = true) String eventId) {
        List<String> listVotes = userDao.getVotes(eventId);
        ModelAndView model = new ModelAndView("votes");
        model.addObject("listVotes", listVotes);
        return model;
    }
	
	@RequestMapping(value="/AddUser")
	@ResponseStatus(value = HttpStatus.OK)
	public void AddUser(@RequestParam(value = "name", required = true) String name,
						@RequestParam(value = "email", required = true) String email,
						@RequestParam(value = "msisdn", required = true) String msisdn){
			
			User us = new User();
			
			us.setUsername(name);
			us.setPassword(name);
			us.setEmail(email);
			us.setMsisdn(msisdn);
			
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
	
	@Autowired
	private Vote vote;
	
	@Autowired
	private Votes votes;
	
	@RequestMapping(value="/AddVote")
	@ResponseStatus(value = HttpStatus.OK)
	public void AddVote(@RequestParam(value = "eventid",required = true) String eventid,
						@RequestParam(value="msisdn") String msisdn,
						@RequestParam(value = "opt")String opt){
		
		logger.info("Received parameters from URL "+eventid+" "+msisdn+" "+opt);
		vote.setEvent_ID(Integer.parseInt(eventid));
		
		
		vote.setVoter_MSISDN(Long.parseLong(msisdn));
		
		votes.setVote(vote);
		votes.setVote_Option(Integer.parseInt(opt));
		try{
			userDao.saveVotes(votes);
		}
		catch(Exception e){
			logger.fatal(e);
		}
		
	}
	
	@RequestMapping("/Processmsg")
	@ResponseStatus(value = HttpStatus.OK)
	public void processmsg(@RequestParam(value = "sender",required = true)String source,
						   @RequestParam(value = "msg",required = true)String msg){
		
		logger.info("Received sms for processing from "+ source + " message: "+msg);
		
		List<String> eventList = userDao.listEvents();
		
		String url; //= "http://localhost:8080/SMSProject/AddVote";
		
		String[] actions = msg.split(" ");
		
		
		if(actions[0].equalsIgnoreCase("taxi")){
			url = "http://localhost:8080/SMSProject/BookTaxi";
			
			try {
				
				String charset = "UTF-8";
				//String url = "http://localhost:8080/SMSProject/AddVote";
				String pickAddress = msg.substring(5);
				String query = String.format("msisdn=%s&address=%s", 
					     URLEncoder.encode(source, charset), 
					     URLEncoder.encode(pickAddress, charset));
				URLConnection connection = new URL(url + "?" + query).openConnection();
				logger.info(url + "?" + query);
				connection.setRequestProperty("Accept-Charset", charset);
				InputStream resp = connection.getInputStream();
				
			} catch (IOException e) {
				logger.fatal(e);
			}
			
		}
		else{
				url = "http://localhost:8080/SMSProject/AddVote";
				
				String event = actions[0];
				
				if(eventList.contains(event)){
					
					
					Integer eventId = userDao.getEventId(event);
					logger.info("Found the event as message: --"+event + " for event ID :"+eventId);
					
					try {
						
						String charset = "UTF-8";
						//String url = "http://localhost:8080/SMSProject/AddVote";
						String opt = actions[1];
						String query = String.format("eventid=%s&msisdn=%s&opt=%s", 
							     URLEncoder.encode(eventId.toString(), charset), 
							     URLEncoder.encode(source, charset),
							     URLEncoder.encode(opt, charset));
						URLConnection connection = new URL(url + "?" + query).openConnection();
						connection.setRequestProperty("Accept-Charset", charset);
						InputStream resp = connection.getInputStream();
						
					} catch (IOException e) {
						logger.fatal(e);
					}
					
					
				}
				
		}
			
		
		
	}
	
	
}
