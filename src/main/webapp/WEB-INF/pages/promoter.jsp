<%--
  Created by IntelliJ IDEA.
  User: Sasha
  Date: 3/19/2015
  Time: 10:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Spring MVC Form Handling</title>
</head>
    <body>
        <%@ include file="tags/subhead.jspf" %>
        <h1>Home page</h1>
        <p>
            Welcome to "Shop application".<br/>
            <i>${message}</i><br/>
            <a href="${pageContext.request.contextPath}/shop/create.html">Create a new shop</a><br/>
            <a href="${pageContext.request.contextPath}/shop/list.html">View all shops</a><br/>
        </p>
    </body>
</html>
