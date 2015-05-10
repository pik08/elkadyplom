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

        <h2>Submitted Student Information</h2>
        <table>
            <tr>
                <td>Name</td>
                <td>${name}</td>
            </tr>
            <tr>
                <td>Age</td>
                <td>${age}</td>
            </tr>
            <tr>
                <td>ID</td>
                <td>${id}</td>
            </tr>
        </table>
    </body>
</html>
