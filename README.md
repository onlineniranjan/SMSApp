SMSApp
======

SMS Application

There are very few basic applications to send and receive SMS from the server and very few open
source API to deal in SMPP protocol. This project makes use of open source CloudHopper APIs to deal
on SMPP level and makes use of Spring MVC to efficiently creation and management of services and
Hibernate to efficiently mange the database transaction.

Every component of the application is Open source and hence this project is also

integrated with GitHub open source community.
Following is the URL for repository:
https://github.com/onlineniranjan/SMSApp/

Components of the Project:
1. Clouhopper APIs from Github
2. Eclipse IDE Luna 4.4.1
3. MySql Server 5.5.40
4. Spring MVC 4
5. Hibernate 4.0
6. Maven 3.0
7. JAVA JDK 1.8
8. Tomcat 8.0
9. Selenium SMPPSim for simulation
Working of Application:
Cloudhopper will bind to the PORT of SMPP server with host address, port number, username and
password. In real word any telecomm operator will provide thse account details to use its service to
send and receive SMS data. A short code or a mobile number will be registered at Telecomm operator
side and all the SMS sent to this number will be forwarded to the SMPP bind at port as mentioned
above.
Once message received, application will parse the information like its sender and message contents and
will take action according to the keywords defined

For the simulation purpose we will use Selenium SMPPSim to simulate SMPP server.
It has facility to send MO (mobile originated SMS sent by user) and MT ( Mobile Terminated SMS,
send by server to user.)
The following URL call will bind application to the SMPPSim
http://localhost:8080/SMSProject/BindSMSC

Services Implemented:
1. Vote for a Question.
This service is implemented by exposing the URL and the user can send his date though get or post
requests.

1.1 To add an event the user can register though following URL
http://localhost:8080/SMSProject/AddEvent?ename=Test5&opt=3&status=0&stime=2014-11-
22&etime=2014-11-23
Parameters in URL:

Ename: Name of the Event to be registered
opt: How many valid option to be considered for and event/question
status: To make event active and accepts votes 1 or 0 to inactivate the event and stop counting votes

against that event.
stime: Delclate start time of the event
etime: Declared End time of the event
Depending on start time and end time votes will be counted against the event.

1.2: Present a vote:
All the user will send the SMS to answer for an event in the format “Event_Name optio”
e.g if Event Name is Test1 and user wants to vote for option 2 then, he will send SMS as
“TEST1 2”
Once the SMS received from the SMPP server we can get the matching event ID and register a vote for
that event. The application will internally make a following call to AddVote controller
http://localhost:8080/SMSProject/AddVote?eventid=16&msisdn=6073727800&opt=1

1.3 To get the voting result:
Application will fetch the result based on the eventID
http://localhost:8080/SMSProject/displayVotes?eventId=16

Demo:
Start he application and SMPPsim and bind with following controller call
http://localhost:8080/SMSProject/BindSMSC

Add Event: Event1
Following URL call will register an event in Database.
http://localhost:8080/SMSProject/AddEvent?ename=EVENT1&opt=3&status=0&stime=2014-11-
12&etime=2014-11-13

Result can be displayed by following call
http://localhost:8080/SMSProject/displayVotes?eventId=22



2. TAXI BOOKING Service:
2.1. Taxi drivers can be registered by followin URL Call
http://localhost:8080/SMSProject/AddUser?
name=niranjan&msisnd=7165449698&email=abs@gmail.com

2.2. 
Customer requesting a TAXI via SMS as Follows
TAXI 319 Main Street binghamton

2.3. User request is parsed and SMS is sent to all the registered taxi drivers. The nearest Taxi driver can
pickup the customer.
Iternally Application will make a call to send SMS to each registered Driver by URL
2014-11-13 15:49:41 INFO ProcessMsg:56 -
http://localhost:8080/SMSProject/Sendmsg?
dst=6073727800&src=TaxiBooking&msg=319+Main+Street+binghamton

SMPPSim log will show submitted SMS to the SMPP server.
