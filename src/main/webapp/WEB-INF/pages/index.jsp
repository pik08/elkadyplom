<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
    <h2>
        Prosze sie zalogowac
    </h2>
    <form:form method="post" action="" modelAttribute="singInForm" commandName="singInForm" enctype="multipart/form-data">
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
                        <form:option value="" label="..." />
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