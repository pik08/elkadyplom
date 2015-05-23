<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!-- NEW TOPIC ------------------------------------------------------------------>

<div id="addTopicsModal"
     class="modal hide fade in centering insertAndUpdateDialogs"
     role="dialog"
     aria-labelledby="addTopicsModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="addTopicsModalLabel" class="displayInLine">
            <spring:message code="topic.create"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="newTopicForm" novalidate >
            <div class="pull-left">
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.title"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               required
                               autofocus
                               ng-model="topic.title"
                               name="name"
                               />
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && newTopicForm.title.$error.required">
                                        <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.description"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               required
                               ng-model="topic.description"
                               name="description"
                               />
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && newTopicForm.description.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.supervisor"/>:</label>
                    </div>
                    <div class="input-append">
                        <select ng-options="s.id as s.name for s in supervisors"
                                required
                                ng-model="topic.supervisorId"
                                name="supervisor"
                                ></select>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && newTopicForm.supervisorId.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.thesisType"/>:</label>
                    </div>
                    <div class="input-append">
                        <label>
                        <input type="radio"
                               required
                               ng-model="topic.thesisType"
                               value="TYPE_ENGINEER"
                               name="thesisType"
                                />
                        <spring:message code="topics.thesisType.engineer"/>
                        </label><label>
                        <input type="radio"
                               required
                               ng-model="topic.thesisType"
                               value="TYPE_MASTER"
                               name="thesisType"
                                />
                        <spring:message code="topics.thesisType.master"/>
                        </label>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && newTopicForm.thesisType.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label><spring:message code="topics.confirmed"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="checkbox"
                               ng-model="topic.confirmed"
                               name="confirmed"
                                />
                    </div>
                </div>
                <input type="submit"
                       class="btn btn-inverse"
                       ng-click="createTopic(newTopicForm);"
                       value='<spring:message code="create"/>'/>
                <button class="btn btn-inverse"
                        data-dismiss="modal"
                        ng-click="exit('#addTopicsModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/>
                </button>
            </div>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
</div>

<!-- END OF NEW TOPIC ------------------------------------------------------------>

<!-- EDIT TOPIC ------------------------------------------------------------------>

<div id="updateTopicsModal"
     class="modal hide fade in centering insertAndUpdateDialogs"
     role="dialog"
     aria-labelledby="updateTopicsModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="updateTopicsModalLabel" class="displayInLine">
            <spring:message code="topic.edit"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="updateTopicForm" novalidate>
            <input type="hidden"
                   required
                   ng-model="topic.id"
                   name="id"
                   value="{{topic.id}}"/>

            <div class="pull-left">
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.title"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               autofocus
                               required
                               ng-model="topic.title"
                               name="title"
                               />
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && updateTopicForm.title.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.description"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="text"
                               required
                               ng-model="topic.description"
                               name="description"
                               />
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && updateTopicForm.description.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.supervisor"/>:</label>
                    </div>
                    <div class="input-append">
                        <select ng-options="s.id as s.name for s in supervisors"
                               required
                               ng-model="topic.supervisorId"
                               name="supervisor"
                               ></select>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && updateTopicForm.supervisorId.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label>* <spring:message code="topics.thesisType"/>:</label>
                    </div>
                    <div class="input-append">
                        <label>
                            <input type="radio"
                                   required
                                   ng-model="topic.thesisType"
                                   value="TYPE_ENGINEER"
                                   name="thesisType"
                                    />
                            <spring:message code="topics.thesisType.engineer"/>
                        </label><label>
                        <input type="radio"
                               required
                               ng-model="topic.thesisType"
                               value="TYPE_MASTER"
                               name="thesisType"
                                />
                        <spring:message code="topics.thesisType.master"/>
                    </label>
                    </div>
                    <div class="input-append">
                        <label>
                                <span class="alert alert-error"
                                      ng-show="displayValidationError && newTopicForm.thesisType.$error.required">
                                    <spring:message code="required"/>
                                </span>
                        </label>
                    </div>
                </div>
                <div>
                    <div class="input-append">
                        <label><spring:message code="topics.confirmed"/>:</label>
                    </div>
                    <div class="input-append">
                        <input type="checkbox"
                               ng-model="topic.confirmed"
                               name="confirmed"
                                />
                    </div>
                </div>
                <input type="submit"
                       class="btn btn-inverse"
                       ng-click="updateTopic(updateTopicForm);"
                       value='<spring:message code="update"/>'/>
                <button class="btn btn-inverse"
                        data-dismiss="modal"
                        ng-click="exit('#updateTopicsModal');"
                        aria-hidden="true">
                    <spring:message code="cancel"/></button>
            </div>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
</div>

<!-- END OF EDIT TOPIC ------------------------------------------------------------->
<!-- DELETE TOPIC ------------------------------------------------------------------>

<div id="deleteTopicsModal"
     class="modal hide fade in centering"
     role="dialog"
     aria-labelledby="searchTopicsModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="deleteTopicsModalLabel" class="displayInLine">
            <spring:message code="topic.delete"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="deleteTopicForm" novalidate>
            <p><spring:message code="delete.confirm"/>:&nbsp;{{topic.title}}?</p>

            <input type="submit"
                   class="btn btn-inverse"
                   ng-click="deleteTopic();"
                   value='<spring:message code="delete"/>'/>
            <button class="btn btn-inverse"
                    data-dismiss="modal"
                    ng-click="exit('#deleteTopicsModal');"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorIllegalAccess">
        <spring:message code="request.illegal.access"/>
    </span>
</div>

<!-- END OF DELETE TOPIC ----------------------------------------------------------->
<!-- SEARCH TOPIC ------------------------------------------------------------------>

<div id="searchTopicsModal"
     class="modal hide fade in centering"
     role="dialog"
     aria-labelledby="searchTopicsModalLabel"
     aria-hidden="true">
    <div class="modal-header">
        <h3 id="searchTopicsModalLabel" class="displayInLine">
            <spring:message code="search"/>
        </h3>
    </div>
    <div class="modal-body">
        <form name="searchTopicForm" novalidate>
            <label><spring:message code="search.for"/></label>

            <div>
                <div class="input-append">
                    <input type="text"
                           autofocus
                           required
                           ng-model="searchFor"
                           name="searchFor"
                           />
                </div>
                <div class="input-append displayInLine">
                    <label class="displayInLine">
                        <span class="alert alert-error"
                              ng-show="displayValidationError && searchTopicForm.searchFor.$error.required">
                            <spring:message code="required"/>
                        </span>
                    </label>
                </div>
            </div>
            <input type="submit"
                   class="btn btn-inverse"
                   ng-click="searchTopic(searchTopicForm, false);"
                   value='<spring:message code="search"/>'
                    />
            <button class="btn btn-inverse"
                    data-dismiss="modal"
                    ng-click="exit('#searchTopicsModal');"
                    aria-hidden="true">
                <spring:message code="cancel"/>
            </button>
        </form>
    </div>
    <span class="alert alert-error dialogErrorMessage"
          ng-show="errorOnSubmit">
        <spring:message code="request.error"/>
    </span>
</div>

<!-- END OF SEARCH TOPIC ------------------------------------------------------------->
