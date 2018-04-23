<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<header>
   
</header>
   <title>Custom Login Page</title>

   <style>
      .failed {
         color: red;
      }
   </style>

<body>

<h3>My Custom Login Page</h3>

   <form:form action="${ pageContext.request.contextPath }/authenticateTheUser" method="POST">
   
      <!-- Handle Login Failed... -->
      <c:if test="${ param.error != null }">
         <i class="failed">Sorry! You entered invalid username/password...</i>
      </c:if>
   
      <p>
         User Name: <input type="text" name="username" />  <!-- name MUST BE "username" for Spring Security Filter -->
      </p>
      <p>
         Password: <input type="password" name="password" /> <!-- name MUST BE "password" for Spring Security Filter -->
      </p>   
      
      <input type="submit" value="Login" />
      
   </form:form>


</body>

</html>