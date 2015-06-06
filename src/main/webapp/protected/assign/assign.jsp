<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="row-fluid" ng-controller="adminAssignTopicsController">
    <button ng-click="doAssign()">Przydziel tematy</button>
    {{result}}
</div>

<script src="<c:url value="/resources/js/pages/assign.js" />"></script>