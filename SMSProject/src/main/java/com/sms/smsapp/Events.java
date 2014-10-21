package com.sms.smsapp;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="Events")
public class Events {

	@Id
	@GeneratedValue
	@Column(name = "Event_ID")
	private Integer eventId;
	
	@Column(name="Event_Name")
	private String eventName;
	
	@Column(name="Event_options")
	private Integer eventOptions;
	
	@Column(name="Start_TIME")
	private String startTime;
	
	@Column(name = "End_time")
	private String End_time;
	
	@Column(name="Active")
	private Integer status;

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
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
	
	
}
