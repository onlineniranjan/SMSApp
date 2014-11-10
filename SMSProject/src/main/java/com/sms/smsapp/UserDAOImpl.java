package com.sms.smsapp;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
	public List<String> listEvents() {
		 @SuppressWarnings("unchecked")
		 Session session = sessionFactory.getCurrentSession();
		 List<String> eventList= session.createQuery("select evts.Event_Name from Events evts").list();
		 return eventList;
		 
	}	
	
	@Transactional
	public List<String> getVotes(String eventId){
		@SuppressWarnings("unchecked")
		 Session session = sessionFactory.getCurrentSession();
		List<String> showVotes= session.createQuery("select V.Vote_Option, count(V.Vote_Option) from Votes  V where V.vote.Event_ID ="+eventId+" group by V.Vote_Option").list();
		 
		/*Criteria criteria = session.createCriteria(Votes.class);
				criteria.add(Restrictions.eq("Event_ID", eventId));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("Vote_Option"))
				.add(Projections.count("Vote_Option"))
				.add(Projections.groupProperty("Vote_Option")));

		List<String> showVotes= criteria.list();*/
		 
		 
		 return showVotes;
	}
	
	@Transactional
	public Integer getEventId(String ename) {
		 @SuppressWarnings("unchecked")
		 Session session = sessionFactory.getCurrentSession();
		 Integer eventId= (Integer) session.createQuery("select evts.Event_ID from Events evts where evts.Event_Name ="+"'"+ename+"'").uniqueResult();
		 return eventId;
		 
	}
	
	@Transactional 
	public void saveVotes(Votes votes){
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(votes);
	}

	
}
