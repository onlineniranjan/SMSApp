package com.sms.smsapp.dao;

import java.util.List;

import com.sms.smsapp.Events;
import com.sms.smsapp.User;

public interface UserDAO {


	public List<User> list();
	public void save(User user);
	public void remove(Integer id);
	public void saveEvent(Events event);
}
