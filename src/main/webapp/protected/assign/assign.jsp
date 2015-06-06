<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="row-fluid" ng-controller="adminAssignTopicsController">
    <button ng-click="doAssign()">Przydziel tematy</button>
    <button ng-click="acceptAssignments()">Akceptacja</button>

    <div id="gridContainer">
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                <th scope="col"><spring:message code="student.name"/></th>
                <th scope="col"><spring:message code="student.average"/></th>
                <th scope="col"><spring:message code="declaredTopics.header"/></th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="assignment in assignments">
                <td class="tdTopicsCentered">{{assignment.studentName}}</td>
                <td class="tdTopicsCentered">{{assignment.cumulativeAverage}}</td>
                <td class="tdTopicsCentered">
                    <table>
                        <tr ng-repeat="topic in assignment.topics">
                            <td ng-show="topic.assigned" style="font-weight: bold">
                                {{topic.title}} ({{topic.supervisorName}})
                            </td>
                            <td ng-show="!topic.assigned">
                                {{topic.title}} ({{topic.supervisorName}})
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>


</div>

<script src="<c:url value="/resources/js/pages/assign.js" />"></script>