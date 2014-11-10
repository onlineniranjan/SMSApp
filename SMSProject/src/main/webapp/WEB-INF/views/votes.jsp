<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div align="center">
            <h1>Votes</h1>
            <table border="1">
                <th>Vote Option</th>
                <th>Count</th>
                 <c:forEach var="listVotes" items="${listVotes}" varStatus="loop">
					 <tr>
						 <td> <c:out value="${listVotes[0]}" />  </td>
      					 <td> <c:out value="${listVotes[1]}" /> </td>
					 </tr>
				 </c:forEach>            
            </table>
        </div>
</body>
</html>