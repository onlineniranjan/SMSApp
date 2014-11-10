package com.sms.smsapp;


import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="Events")
public class Events {

	@Id
	@GeneratedValue
	@Column(name = "Event_ID")
	private Integer Event_ID;
	
	@Column(name="Event_Name")
	private String Event_Name;
	
	@Column(name="Event_options")
	private Integer eventOptions;
	
	@Column(name="Start_TIME")
	private String startTime;
	
	@Column(name = "End_time")
	private String End_time;
	
	@Column(name="Active")
	private Integer status;

	@OneToMany
    private Set<Votes> votes;
	
	
	public Integer getEventId() {
		return Event_ID;
	}

	public void setEventId(Integer eventId) {
		this.Event_ID = eventId;
	}

	public String getEventName() {
		return Event_Name;
	}

	public void setEventName(String eventName) {
		this.Event_Name = eventName;
	}

	public Integer getEventOptions() {
		return eventOptions;
	}

	public void setEventOptions(Integer eventOptions) {
		this.eventOptions = eventOptions;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEnd_time() {
		return End_time;
	}

	public void setEnd_time(String end_time) {
		End_time = end_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Votes> getVotes() {
		return votes;
	}

	public void setVotes(Set<Votes> votes) {
		this.votes = votes;
	}

	
	
}
