package com.sms.smsapp;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.sms.smsapp.dao.UserDAO;

public class UserDAOImpl implements UserDAO {

	private Logger logger = Logger.getLogger(UserDAO.class);
	private static SessionFactory sessionFactory;
	
	public UserDAOImpl(SessionFactory inSessionFactory){
		
		this.sessionFactory = inSessionFactory;
	}

	//@Override
	@Transactional
	public List<User> list() {
		// TODO Auto-generated method stub
		 @SuppressWarnings("unchecked")
	        List<User> listUser = (List<User>) sessionFactory.getCurrentSession()
	                .createCriteria(User.class)
	                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	 
	        return listUser;
	}

	
	
	@Transactional
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }
	
	@Transactional
    public void remove(Integer id) {
        Session session = sessionFactory.getCurrentSession();
         
        User user = (User)session.get(User.class, id);
         
        session.delete(user);
    }

	@Transactional
	public void saveEvent(Events event) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		
		session.save(event);
		
	}
	
	@Transactional 
	public void saveVotes(Votes votes){
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(votes);
	}
	
}
