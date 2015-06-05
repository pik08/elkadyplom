<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="row-fluid" ng-controller="assignedTopicController">
    <div class="jumbotron">
        <h2 >
            <spring:message code='assignedTopic.title'/>
        </h2>
        <h3 ng-show="!page.source.length">
            <spring:message code="assignedTopic.noTopicMessage"/>
        </h3>
        <table width="50%" ng-repeat="topic in page.source" class=" table table-bordered table-striped table-hover">
            <tr>
                <td width="50%" class="assignedTopicInfo"><spring:message code="topics.title"/></td>
                <td class="tdTopicsCentered">{{topic.title}}</td>
            </tr>
            <tr>
                <td width="50%" class="assignedTopicInfo"><spring:message code="topics.description"/></td>
                <td class="tdTopicsCentered">{{topic.description}}</td>
            </tr>
            <tr>
                <td width="50%" class="assignedTopicInfo"><spring:message code="topics.supervisor"/></td>
                <td class="tdTopicsCentered">{{topic.supervisorName}}</td>
            </tr>
            <tr>
                <td width="50%" class="assignedTopicInfo"><spring:message code="topics.thesisType"/></td>
                <td class="tdTopicsCentered" ng-show="topic.thesisType == 'TYPE_ENGINEER' ">
                    <spring:message code="topics.thesisType.engineer"/></td>
                <td class="tdTopicsCentered" ng-show="topic.thesisType == 'TYPE_MASTER' ">
                    <spring:message code="topics.thesisType.master"/></td>
            </tr>
        </table>
    </div>
</div>
<script src="<c:url value="/resources/js/pages/assignedtopic.js" />"></script>