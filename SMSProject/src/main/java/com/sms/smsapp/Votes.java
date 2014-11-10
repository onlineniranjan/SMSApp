package com.sms.smsapp;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.log4j.Logger;

@Entity
@Table(name="Votes")
//uniqueConstraints = { @UniqueConstraint( columnNames = { "NAME", "VERSION" } ) }
public class Votes {
	
	public Votes(){}
	
	
	@EmbeddedId
    private Vote vote;
	
	@Column(name = "Vote_Option")
	private int Vote_Option;

	/*@ManyToOne
	private Events events;*/
	
	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		
		this.vote = vote;
		
	}

	public int getVote_Option() {
		return Vote_Option;
	}

	public void setVote_Option(int vote_Option) {
		Vote_Option = vote_Option;
	}

	/*public Events getEvents() {
		return events;
	}

	public void setEvents(Events events) {
		this.events = events;
	}*/


}


