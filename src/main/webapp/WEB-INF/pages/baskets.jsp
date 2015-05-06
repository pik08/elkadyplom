<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <jsp:include page="common/head.jsp"/>
        <title>Spring MVC Form Handling</title>

        <link href="<c:url value="/resources/css/baskets.css" />" rel="stylesheet">
        <script src="<c:url value="/resources/js/baskets.js" />"></script>
    </head>
    <body>
        <div id="workspace">
            <h2>Submitted Student Information</h2>
            <!-- Table -->
            <table class="table table-striped">
                <tr>
                    <th>Index</th>
                    <th>Name</th>
                    <th>Date Create</th>
                    <th>Date Edit</th>
                    <th>Position</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach var="listVar" items="${baskets}" varStatus="loop">
                    <tr>
                        <td><c:out value="${listVar.id}"/></td>
                        <td>
                            <a href="basket?idBasket=${listVar.id}"><c:out value="${listVar.name}"/></a>
                        </td>
                        <td><c:out value="${listVar.dateCreate}"/></td>
                        <td><c:out value="${listVar.dateEdit}"/></td>
                        <td><c:out value="${listVar.contain}"/></td>
                        <td>
                            <span id="<c:out value="${listVar.id}"/>" class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </td>
                        <td>
                            <span id="<c:out value="${listVar.id}"/>" class="glyphicon glyphicon-trash" onclick="deleteBasket(${listVar.id})" aria-hidden="true"></span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <button type="button" id="add" class="btn btn-success">Add</button>
        </div>
    </body>
</html>
