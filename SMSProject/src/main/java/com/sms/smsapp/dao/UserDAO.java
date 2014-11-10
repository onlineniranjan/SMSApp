package com.sms.smsapp.dao;

import java.util.List;

import com.sms.smsapp.Events;
import com.sms.smsapp.User;
import com.sms.smsapp.Votes;


public interface UserDAO {


	public List<User> list();
	public void save(User user);
	public void remove(Integer id);
	public void saveEvent(Events event);
	public void saveVotes(Votes votes);
	public List<String> listEvents();
	public Integer getEventId(String ename);
	public List<String> getVotes(String eventId);
}
