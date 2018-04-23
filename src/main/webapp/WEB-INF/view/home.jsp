<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<header>
</header>
<title>Home Page</title>

<body>
   <h1>Home Sweet Home</h1>
   <hr>
   
   <p>
      Welcome to the home page!!
   </p>
   
   <hr>
   
	   <!-- Display username and roles -->
	   Your Username: <security:authentication property="principal.username"/>	   
	   <br><br>	   
	   Your Role(s): <security:authentication property="principal.authorities"/>
   
   <hr>
   
   <security:authorize access="hasRole('MANAGER')">
	   <!-- Link to /leaders for the MANAGERS -->
	   <p>
	      <a href="${ pageContext.request.contextPath }/leaders">Leadership Meeting</a> 
	      (Only for managers)
	   </p>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
	   <!-- Link to /systems for the ADMINS -->
	   <p>
	      <a href="${ pageContext.request.contextPath }/systems">System Meeting</a> 
	      (Only for IT admins)
	   </p>   
   </security:authorize>
   
   
   <br><br>
   <!-- /logout is auto-provided by Spring Security -->
   <form:form action="${ pageContext.request.contextPath }/logout" method="POST">
   
      <input type="submit" value="Logout" />
   
   </form:form>
   
</body>
</html>