<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="row-fluid" ng-controller="topicsController">
    <h2>
        <p class="text-center">
            <spring:message code='topics.header'/>
            <a href="#searchTopicsModal"
               id="topicsHeaderButton"
               role="button"
               ng-class="{'': displaySearchButton == true, 'none': displaySearchButton == false}"
               title="<spring:message code="search"/>&nbsp;<spring:message code="topic"/>"
               class="btn btn-inverse" data-toggle="modal">
                <i class="icon-search"></i>
            </a>
        </p>
    </h2>
    <h4>
        <div ng-class="{'': state == 'list', 'none': state != 'list'}">
            <p class="text-center">
                <spring:message code="message.total.records.found"/>:&nbsp;{{page.totalTopics}}
            </p>
        </div>
    </h4>

    <div>
        <div id="loadingModal" class="modal hide fade in centering"
             role="dialog"
             aria-labelledby="deleteTopicsModalLabel" aria-hidden="true">
            <div id="divLoadingIcon" class="text-center">
                <div class="icon-align-center loading"></div>
            </div>
        </div>

        <div ng-class="{'alert badge-inverse': displaySearchMessage == true, 'none': displaySearchMessage == false}">
            <h4>
                <p class="messageToUser"><i class="icon-info-sign"></i>&nbsp;{{page.searchMessage}}</p>
            </h4>
            <a href="#"
               role="button"
               ng-click="resetSearch();"
               ng-class="{'': displaySearchMessage == true, 'none': displaySearchMessage == false}"
               title="<spring:message code='search.reset'/>"
               class="btn btn-inverse" data-toggle="modal">
                <i class="icon-remove"></i> <spring:message code="search.reset"/>
            </a>
        </div>

        <div ng-class="{'alert badge-inverse': displayMessageToUser == true, 'none': displayMessageToUser == false}">
            <h4 class="displayInLine">
                <p class="messageToUser displayInLine"><i class="icon-info-sign"></i>&nbsp;{{page.actionMessage}}</p>
            </h4>
        </div>

        <div ng-class="{'alert alert-block alert-error': state == 'error', 'none': state != 'error'}">
            <h4><i class="icon-info-sign"></i> <spring:message code="error.generic.header"/></h4><br/>

            <p><spring:message code="error.generic.text"/></p>
        </div>

        <div ng-class="{'alert alert-info': state == 'noresult', 'none': state != 'noresult'}">
            <h4><i class="icon-info-sign"></i> <spring:message code="topics.emptyData"/></h4><br/>

            <p><spring:message code="topics.emptyData.text"/></p>
        </div>

        <div>
        <security:authorize  ifAnyGranted="ROLE_SUPERVISOR, ROLE_ADMIN">
            <div ng-class="{'text-center': displayCreateTopicButton == true, 'none': displayCreateTopicButton == false}">
                <br/>
                <a id="addTopicsModal"
                   href="#addTopicsModal"
                   role="button"
                   ng-click="resetTopic();"
                   title="<spring:message code='create'/>&nbsp;<spring:message code='topic'/>"
                   class="btn btn-inverse"
                   data-toggle="modal">
                    <i class="icon-plus"></i>
                    &nbsp;&nbsp;<spring:message code="topic.create"/>
                </a>
            </div>
        </security:authorize>
        <security:authorize  ifAnyGranted="ROLE_STUDENT">
            <div ng-class="{'text-center': displayCreateTopicButton == true, 'none': displayCreateTopicButton == false}">
                <br/>
                <a id="declareTopicsModal"
                   href="#declareTopicsModal"
                   role="button"
                   title="<spring:message code="declarations.header"/>"
                   class="btn btn-inverse"
                   data-toggle="modal">
                    <i class="icon-plus"></i>
                    &nbsp;&nbsp;<spring:message code="declarations.header"/>
                </a>
            </div>
        </security:authorize>
            <br/>
        </div>

        <div id="gridContainer" ng-class="{'': state == 'list', 'none': state != 'list'}">
            <table class="table table-bordered table-striped">
                <thead>
                <tr>
                    <th scope="col"><spring:message code="topics.title"/></th>
                    <th scope="col"><spring:message code="topics.description"/></th>
                    <th scope="col"><spring:message code="topics.supervisor"/></th>
                    <security:authorize  ifAnyGranted="ROLE_SUPERVISOR, ROLE_ADMIN">
                        <th scope="col"><spring:message code="topics.student"/></th>
                        <th scope="col"><spring:message code="topics.confirmed"/></th>
                    </security:authorize>
                    <th scope="col"><spring:message code="topics.thesisType"/></th>
                    <th scope="col">
                        <security:authorize  ifAnyGranted="ROLE_STUDENT">
                            <spring:message code="topics.details"/>
                        </security:authorize>
                    </th>

                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="topic in page.source">
                    <td class="tdTopicsCentered">{{topic.title}}</td>
                    <td class="tdTopicsCentered">{{topic.description}}</td>
                    <td class="tdTopicsCentered">{{topic.supervisorName}}</td>
                    <security:authorize  ifAnyGranted="ROLE_SUPERVISOR, ROLE_ADMIN">
                        <td class="tdTopicsCentered">{{topic.studentName}}</td>
                        <td class="tdTopicsCentered" ng-show="topic.confirmed"><spring:message code="yes"/></td>
                        <td class="tdTopicsCentered" ng-show="!topic.confirmed"><spring:message code="no"/></td>
                    </security:authorize>
                    <td class="tdTopicsCentered" ng-show="topic.thesisType == 'TYPE_ENGINEER' ">
                        <spring:message code="topics.thesisType.engineer"/></td>
                    <td class="tdTopicsCentered" ng-show="topic.thesisType == 'TYPE_MASTER' ">
                        <spring:message code="topics.thesisType.master"/></td>

                    <td class="width15">
                        <security:authorize  ifAnyGranted="ROLE_ADMIN">
                            <div class="text-center">
                                <input type="hidden" value="{{topic.id}}"/>
                                <a href="#updateTopicsModal"
                                   ng-click="selectedTopic(topic);"
                                   role="button"
                                   title="<spring:message code="topic.edit"/>"
                                   class="btn btn-inverse" data-toggle="modal">
                                    <i class="icon-pencil"></i>
                                </a>
                                <a href="#deleteTopicsModal"
                                   ng-click="selectedTopic(topic);"
                                   role="button"
                                   title="<spring:message code="topic.delete"/>"
                                   class="btn btn-inverse" data-toggle="modal">
                                    <i class="icon-minus"></i>
                                </a>
                            </div>
                        </security:authorize>
                        <security:authorize  ifAnyGranted="ROLE_SUPERVISOR">
                            <div class="text-center" ng-show="!topic.confirmed">
                                <input type="hidden" value="{{topic.id}}"/>
                                <a href="#updateTopicsModal"
                                   ng-click="selectedTopic(topic);"
                                   role="button"
                                   title="<spring:message code="topic.edit"/>"
                                   class="btn btn-inverse" data-toggle="modal">
                                    <i class="icon-pencil"></i>
                                </a>
                                <a href="#deleteTopicsModal"
                                   ng-click="selectedTopic(topic);"
                                   role="button"
                                   title="<spring:message code="topic.delete"/>"
                                   class="btn btn-inverse" data-toggle="modal">
                                    <i class="icon-minus"></i>
                                </a>
                            </div>
                        </security:authorize>
                        <security:authorize  ifAnyGranted="ROLE_STUDENT">
                            <div class="text-center">
                                <input type="hidden" value="{{topic.id}}"/>
                                <a href="#detailsTopicsModal"
                                   ng-click="selectedTopic(topic);"
                                   role="button"
                                   title="<spring:message code="topic.details"/>"
                                   class="btn btn-inverse" data-toggle="modal">
                                    <i class="icon-eye-open"></i>
                                </a>
                                <input type="hidden" value="{{topic.id}}"/>
                                <a href="#"
                                   ng-click="addTopicToDeclared(topic);"
                                   role="button"
                                   title="<spring:message code="topics.declare"/>"
                                   class="btn btn-inverse" data-toggle="modal">
                                    <i class="icon-plus"></i>
                                </a>
                            </div>
                        </security:authorize>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="text-center">
                <button href="#" class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0" ng-click="changePage(0)"
                        title='<spring:message code="pagination.first"/>'
                        >
                    <spring:message code="pagination.first"/>
                </button>
                <button href="#"
                        class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.currentPage != 0, 'disabled': page.currentPage == 0}"
                        ng-disabled="page.currentPage == 0" class="btn btn-inverse"
                        ng-click="changePage(page.currentPage - 1)"
                        title='<spring:message code="pagination.back"/>'
                        >&lt;</button>
                <span>{{page.currentPage + 1}} <spring:message code="pagination.of"/> {{page.pagesCount}}</span>
                <button href="#"
                        class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.pagesCount - 1 != page.currentPage, 'disabled': page.pagesCount - 1 == page.currentPage}"
                        ng-click="changePage(page.currentPage + 1)"
                        ng-disabled="page.pagesCount - 1 == page.currentPage"
                        title='<spring:message code="pagination.next"/>'
                        >&gt;</button>
                <button href="#"
                        class="btn btn-inverse"
                        ng-class="{'btn-inverse': page.pagesCount - 1 != page.currentPage, 'disabled': page.pagesCount - 1 == page.currentPage}"
                        ng-disabled="page.pagesCount - 1 == page.currentPage"
                        ng-click="changePage(page.pagesCount - 1)"
                        title='<spring:message code="pagination.last"/>'
                        >
                    <spring:message code="pagination.last"/>
                </button>
            </div>
        </div>


        <jsp:include page="dialogs/topicsDialogs.jsp"/>

    </div>
</div>

<br/>
<br/>
<br/>

<script src="<c:url value="/resources/js/pages/topics.js" />"></script>
