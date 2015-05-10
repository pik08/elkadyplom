<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:url var="postLoginUrl" value="/j_spring_security_check" />

<html>
<body>
    <%@ include file="tags/subhead.jspf" %>
    <h2>
        Prosze sie zalogowac
    </h2>
    <h3>Details: ${details}</h3>
    <form:form method="post" action="${postLoginUrl}" modelAttribute="singInForm" commandName="singInForm" enctype="multipart/form-data">
        <table>
            <tr>
                <td><label><form:label path="username">Username</form:label><label></td>
                <td><form:input path="username" /></td>
            </tr>
            <tr>
                <td><label><form:label path="password">Password</form:label><label></td>
                <td><form:input path="password" /></td>
            </tr>
            <tr>
                <td><label>Stay in:<label></td>
                <td><form:checkbox path="staySigned" value="false"/></td>
            </tr>
            <tr>
                <td><label>Please select who you are: </label></td>
                <td>
                    <form:select path="Who">
                        <form:options items="${who}" />
                    </form:select>
                </td>
            </tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
            </td>
            <%--<tr>--%>
                <%--<td><label>File: <label></td>--%>
                <%--<td><form:input path="file" type="file"/></td>--%>
            <%--</tr>--%>
        </table>
    </form:form>
</body>
</html>