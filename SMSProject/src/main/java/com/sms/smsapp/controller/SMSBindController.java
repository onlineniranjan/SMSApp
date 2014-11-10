package com.sms.smsapp.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.type.Address;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppBindException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppInvalidArgumentException;
import com.cloudhopper.smpp.type.SmppTimeoutException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import com.sms.smsapp.SmppIncomingSessionHandler;
import com.sms.smsapp.User;
import com.sms.smsapp.dao.UserDAO;


@Controller
public class SMSBindController {

	private Logger logger = Logger.getLogger(SMSBindController.class);
	private SmppSession session = null; 
	
	String message = "Bind controller initiated";
	
	@RequestMapping("/hello")
	public ModelAndView showMessage(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		System.out.println("in Taxi Basic controller");
 
		ModelAndView mv = new ModelAndView("helloworld");
		mv.addObject("message", message);
		mv.addObject("name", name);
		logger.info("This is message from logger" + message);
		return mv;
	}
	
	@RequestMapping("/BindSMSC")
	@ResponseStatus(value = HttpStatus.OK)	
	public void bindSmsc(){
		
		logger.info("hit bind..........******");
		DefaultSmppClient client = new DefaultSmppClient();
		
		SmppSessionConfiguration  sessionConfig = new SmppSessionConfiguration();
		
							sessionConfig.setType(SmppBindType.TRANSCEIVER);
							sessionConfig.setHost("127.0.0.1");
							sessionConfig.setPort(2775);
							sessionConfig.setSystemId("smppclient1");
							sessionConfig.setPassword("password");
							
						
								try {
									session = client.bind(sessionConfig, new SmppIncomingSessionHandler());
								} catch (SmppBindException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SmppTimeoutException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SmppChannelException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (UnrecoverablePduException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						
							
		
							
	}
	
	@RequestMapping("/Sendmsg")
	@ResponseStatus(value = HttpStatus.OK)
	public void sendmsg(@RequestParam(value = "src",required = true)String src, 
						@RequestParam(value = "dst",required = true)String dst, 
						@RequestParam(value = "msg",required = true)String msg){
		
		String charset = "UCS-2";
		
		
		SubmitSm sm = createSubmitSm(src,dst,msg,charset);
		
		
		//SubmitSmResp ssmr = session.submit(sm, TimeUnit.SECONDS.toMillis(60));
		
		//logger.info("Submitted sms for MSG ID = "+ssmr.getMessageId()+" and seqno # = "+ssmr.getSequenceNumber());
		
		WindowFuture<Integer, PduRequest, PduResponse> future2;
		try {
			
			future2 = session.sendRequestPdu (sm,TimeUnit.SECONDS.toMillis(60),false);
			
			while (!future2.isDone()) {
			     //logger.info ("Not done Succes is {}"+ future2.isSuccess ());
			}
			
			logger.info("Message Sent and waiting.....");
			
			logger.info("Response is "+future2.getResponse());
			logger.info("Done Success status is "+future2.isSuccess());
			logger.info("Response time is "+future2.getAcceptToDoneTime());
			//TimeUnit.SECONDS.sleep(10);
			
			//logger.info("Destroy session");
			//session.close();
			
			//session.destroy();
			
			//logger.info("Destroy client");
			
			//client.destroy();
			
			//logger.info("....and done");
			
		} catch (RecoverablePduException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnrecoverablePduException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmppTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmppChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  SubmitSm createSubmitSm(String src, String dst, String text, String charset){
		
		SubmitSm sm = new SubmitSm();
		
		// For alpha numeric will use
	     // TON = 5
	     // NPI = 0
		sm.setSourceAddress(new Address ((byte) 5, (byte) 0, src ));
		
		// For national numbers will use
	     // TON = 1
	     // NPI = 1
		
		sm.setDestAddress(new Address((byte) 1, (byte) 1, dst));
		
		// Set datacoding to UCS-2
		sm.setDataCoding((byte) 8);
		
		try {
			
			
			sm.setShortMessage(CharsetUtil.encode(text, charset));
			
			
		} catch (SmppInvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//sm.setRegisteredDelivery((byte)1);
		
		return sm;
		
	}
	
	
}
