package com.sms.smsapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

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
		
		/*if(pduRequest.isRequest() && pduRequest.getClass() ==  DeliverSm.class){
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
		}*/
		if(pduRequest.getCommandId() == SmppConstants.CMD_ID_DELIVER_SM){
			
			PduResponse response = pduRequest.createResponse();
			
			DeliverSm mo = (DeliverSm) pduRequest;
			
			int length = mo.getShortMessageLength();
			Address source = mo.getSourceAddress();
			Address dest = mo.getDestAddress();
			byte [] smsByte = mo.getShortMessage();
			String sms = new String(smsByte);
			logger.info("Received Message from "+source+" for destination "+dest+" MSG = "+sms);
			
			String str = source.toString();
            int place = str.lastIndexOf("[")+1;
            str = str.substring(place);
            str = str.substring(0,str.length()-1);
            System.out.println(str);
			
			String formatURL = "sender="+str+"&msg="+sms;
			 
			try {
				
				String charset = "UTF-8";
				String url = "http://localhost:8080/SMSProject/Processmsg";
				
				String query = String.format("sender=%s&msg=%s", 
					     URLEncoder.encode(str, charset), 
					     URLEncoder.encode(sms, charset));
				URLConnection connection = new URL(url + "?" + query).openConnection();
				connection.setRequestProperty("Accept-Charset", charset);
				InputStream resp = connection.getInputStream();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
			
			return response;
		}
		
		return super.firePduRequestReceived(pduRequest);
	}

	@Override
	public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
		// TODO Auto-generated method stub
		
		/*if(pduAsyncResponse.getResponse().getClass() == SubmitSmResp.class){
			
			SubmitSmResp ssmr = (SubmitSmResp) pduAsyncResponse.getResponse();
			logger.info("Got response with MSG ID {} "+ ssmr.getMessageId()+ "for seqnum {} " +ssmr.getSequenceNumber());
		}*/
		
		//super.fireExpectedPduResponseReceived(pduAsyncResponse);
	}
	

}
