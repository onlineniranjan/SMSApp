package com.sms.smsapp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Vote implements Serializable{

		public Vote(){}
	
		@Column(name="Event_ID")
		private int Event_ID;
		
		@Column(name="Voter_MSISDN")
		private long Voter_MSISDN;

		public int getEvent_ID() {
			return Event_ID;
		}

		public void setEvent_ID(int event_ID) {
			Event_ID = event_ID;
		}

		public long getVoter_MSISDN() {
			return Voter_MSISDN;
		}

		public void setVoter_MSISDN(long voter_MSISDN) {
			Voter_MSISDN = voter_MSISDN;
		}

		
}
