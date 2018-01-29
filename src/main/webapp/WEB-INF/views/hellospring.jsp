<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring 4 MVC -HelloWorld</title>
</head>
<body>
    <center>
        <h2>Hello World</h2>
        <h2>
            ${message} ${name}     </h2>
    </center>
    
    <!-- 输出LIST -->
    <p>信息列表 </p>
    <c:forEach items="${INFOLIST}" var="node">
    	<c:out value="${node}"/><br/>
    </c:forEach>
    
    <!-- 输出Map -->
    <p>学习信息</p>
    <c:forEach items="${STUDYMAP}" var="study">
    	<c:out value="${study.key}  --  "></c:out>
    	<c:out value="${study.value}"></c:out>
    	<br/>
    </c:forEach>
</body>
</html>