package com.sms.smsapp;

import org.apache.log4j.Logger;

import com.cloudhopper.smpp.PduAsyncResponse;
import com.cloudhopper.smpp.SmppConstants;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.type.Address;

public class SmppIncomingSessionHandler extends DefaultSmppSessionHandler {

	private Logger logger = Logger.getLogger(SmppIncomingSessionHandler.class);
	
	@Override
	public void firePduRequestExpired(PduRequest pduRequest) {
		// TODO Auto-generated method stub
		super.firePduRequestExpired(pduRequest);
		
		
	}

	@Override
	public PduResponse firePduRequestReceived(PduRequest pduRequest) {
		// TODO Auto-generated method stub
		
		if(pduRequest.isRequest() && pduRequest.getClass() ==  DeliverSm.class){
			logger.info("Received DELIVER_SM");
		
			DeliverSm dsm = (DeliverSm) pduRequest;
			
			logger.info("Msg id = " + dsm.getOptionalParameter(SmppConstants.TAG_RECEIPTED_MSG_ID));
			logger.info("status = "+ dsm.getOptionalParameter(SmppConstants.TAG_MSG_STATE));
			DeliverSm mo = (DeliverSm) pduRequest;
			
			int length = mo.getShortMessageLength();
			Address source = mo.getSourceAddress();
			Address dest = mo.getDestAddress();
			byte [] smsByte = mo.getShortMessage();
			String sms = new String(smsByte);
			logger.info("Received Message from "+source+" for destination "+dest+" MSG = "+sms);
			
			return pduRequest.createResponse();
		}
		if(pduRequest.getCommandId() == SmppConstants.CMD_ID_DELIVER_SM){
			
			PduResponse response = pduRequest.createResponse();
			
			DeliverSm mo = (DeliverSm) pduRequest;
			
			int length = mo.getShortMessageLength();
			Address source = mo.getSourceAddress();
			Address dest = mo.getDestAddress();
			byte [] smsByte = mo.getShortMessage();
			String sms = new String(smsByte);
			logger.info("Received Message from "+source+" for destination "+dest+" MSG = "+sms);
			
			return response;
		}
		
		return super.firePduRequestReceived(pduRequest);
	}

	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
		// TODO Auto-generated method stub
		
		if(pduAsyncResponse.getResponse().getClass() == SubmitSmResp.class){
			
			SubmitSmResp ssmr = (SubmitSmResp) pduAsyncResponse.getResponse();
			logger.info("Got response with MSG ID {} "+ ssmr.getMessageId()+ "for seqnum {} " +ssmr.getSequenceNumber());
		}
		
		//super.fireExpectedPduResponseReceived(pduAsyncResponse);
	}
	

}
