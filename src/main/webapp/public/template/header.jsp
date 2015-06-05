<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<div class="masthead">
    <h3 class="muted">
        <spring:message code='header.message'/>
    </h3>

    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav" ng-controller="LocationController">
                    <li ng-class="{'active': activeURL == 'home', '': activeURL != 'home'}" >
                        <a href="<c:url value="/"/>"
                           title='<spring:message code="header.home"/>'
                                >
                            <p><spring:message code="header.home"/></p>
                        </a>
                    </li>
                    <li ng-class="{'active': activeURL == 'topics', '': activeURL != 'topics'}">
                        <a title='<spring:message code="header.topics"/>' href="<c:url value='/protected/topics'/>">
                            <p><spring:message code="header.topics"/></p>
                        </a>
                    </li>
                    <security:authorize  ifAnyGranted="ROLE_STUDENT">
                    <li ng-class="{'active': activeURL == 'assignedTopic', '': activeURL != 'assignedTopic'}">
                        <a title='<spring:message code="header.student.topic"/>' href="<c:url value='/protected/assignedTopic/'/>">
                            <p><spring:message code="header.student.topic"/></p>
                        </a>
                    </li>
                    </security:authorize>
                </ul>
                <ul class="nav pull-right">
                    <li>
                        <a href="<c:url value='/logout' />" title='<spring:message code="header.logout"/>'>
                            <p class="displayInLine"><spring:message code="header.logout"/>&nbsp;(${user.name})</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
