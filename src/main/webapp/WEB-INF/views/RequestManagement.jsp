<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AreaD Tool</title>
    <style>
        .requestor.ng-valid {
            background-color: lightgreen;
        }
        .requestor.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .requestor.ng-dirty.ng-invalid-minlength {
            background-color: yellow;
        }

        .email.ng-valid {
            background-color: lightgreen;
        }
        .email.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .email.ng-dirty.ng-invalid-email {
            background-color: yellow;
        }

    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet">
</head>
<body ng-app="myApp" class="ng-cloak">
<div class="generic-container" ng-controller="RequestController as ctrl">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Request Registration Form </span></div>
        <div class="formcontainer">
            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                <input type="hidden" ng-model="ctrl.request.id" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <!--<span>CSRF - "${_csrf.parameterName}" - "${_csrf.token}"</span> -->
                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="reqname">Requestor</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.request.requestor" id="reqname" class="requestor form-control input-sm" placeholder="Enter requestor name" required ng-minlength="3"/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.reqname.$error.required">This is a required field</span>
                                <span ng-show="myForm.reqname.$error.minlength">Minimum length required is 3</span>
                                <span ng-show="myForm.reqname.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="description">Request</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.request.description" id="description" class="form-control input-sm" placeholder="Enter your request" required/>
                        </div>
                          <div class="has-error" ng-show="myForm.$dirty">
                               <span ng-show="myForm.description.$error.required">This is a required field</span>
                               <span ng-show="myForm.description.$invalid">This field is invalid </span>
                          </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="email">Email</label>
                        <div class="col-md-7">
                            <input type="email" ng-model="ctrl.request.email" id="email" class="email form-control input-sm" placeholder="Enter your Email" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.email.$error.required">This is a required field</span>
                                <span ng-show="myForm.email.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="assignee">Assignee</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.request.assignee" id="assignee" class="form-control input-sm" placeholder="Enter request assignee. [This field is validation free]"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="status">Status</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.request.status" id="status" class="form-control input-sm" placeholder="Enter request state. [This field is validation free]"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="status">Priority</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.request.priority" id="priority" class="form-control input-sm" placeholder="Enter priority. [This field is validation free]"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="{{!ctrl.request.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Requests List </span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Requestor</th>
                    <th>Request</th>
                    <th>Email</th>
                    <th>Assignee</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrl.requests">
                    <td><span ng-bind="u.id"></span></td>
                    <td><span ng-bind="u.requestor"></span></td>
                    <td><span ng-bind="u.description"></span></td>
                    <td><span ng-bind="u.email"></span></td>
                     <td><span ng-bind="u.assignee"></span></td>
                     <td><span ng-bind="u.status"></span></td>
                      <td><span ng-bind="u.priority"></span></td>
                    <td>
                        <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>


<%--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>--%>
<%--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.0/angular.js"></script>--%>
<%--<script src="//code.angularjs.org/1.4.0/angular.js"></script>--%>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
<script src="<c:url value='/static/js/app.js' />"></script>
<script src="<c:url value='/static/js/service/request_service.js' />"></script>
<script src="<c:url value='/static/js/controller/request_controller.js' />"></script>
</body>
</html>