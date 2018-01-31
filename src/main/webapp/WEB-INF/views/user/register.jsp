<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>新增用户</title>
</head>
<body>
<!--  
  
  <form method="post" action="<c:url value="/user.html"/>">
    <table>
	    <tr>
	       <td>用户名：</td>
	       <td><input type="text" name="userName"  value="${user.userName}"/></td>
	    </tr>
	    <tr>
	     <td>密码：</td>
	       <td><input type="password" name="password" value="${user.password}"/></td>
	    </tr>
	     <td colspan="2"><input type="submit" name="提交ddd" value="提交"/></td>
	    </tr>	    
    </table>
  </form>
  -->
  <h2>新用户注册</h2>
     <form:form method="POST" modelAttribute="user" action="register.do" >  
        <table>  
                <tr><!-- tr表示一行，td表示一格 -->  
                    <td>名称</td>  
                    <!-- form:input 会被渲染成HTML的<input>标签     其中path属性会将input标签中value的值设置为模型中的path属性对应的值 -->  
                    <td><form:input type="text" path="userName"/></td>
                </tr>
                <tr>
                    <td>密码</td>   
                    <td><form:password  path="password"/></td>  
                </tr>   
                </tr>
	     			<td colspan="2"><input type="submit" name="提交ddd" value="提交"/></td>
	    		</tr>
        </table>  
    </form:form>  
</body>
</html>