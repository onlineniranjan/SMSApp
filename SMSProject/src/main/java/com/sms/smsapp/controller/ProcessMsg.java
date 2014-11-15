package com.sms.smsapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.sms.smsapp.User;
import com.sms.smsapp.dao.UserDAO;

@Controller
public class ProcessMsg {

	private SessionFactory sf;
	
	@Autowired
    private UserDAO userDao;
	
	private static Logger logger = Logger.getLogger(ProcessMsg.class);
	
	@RequestMapping("/BookTaxi")
	@ResponseStatus(value = HttpStatus.OK)
	public void booktaxi(@RequestParam(value = "msisdn",required = true)String source,
			   			 @RequestParam(value = "address",required = true)String address){
		
		logger.info("Received booking request for " +address);
		
		List<User> listUsers = userDao.list();
		
		String url = "http://localhost:8080/SMSProject/Sendmsg";
		
		String charset = "UTF-8";
		
		
		for(User user:listUsers){
		try {
				
			String query = String.format("dst=%s&src=%s&msg=%s", 
				     URLEncoder.encode(user.getMsisdn(), charset), 
				     URLEncoder.encode("TaxiBooking", charset),
				     URLEncoder.encode(address, charset));
			URLConnection connection = new URL(url + "?" + query).openConnection();
			logger.info(url+"?"+query);
			connection.setRequestProperty("Accept-Charset", charset);
			//InputStream resp = connection.getInputStream();
			
			/*BufferedReader in = new BufferedReader(new InputStreamReader(resp));

					String line; 
					
					while ((line = in.readLine()) != null) {

					 logger.info(line); 
					}
					resp.close();
*/			} 	catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
