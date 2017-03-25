<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>RSS Feeds Management</title>
    <style>

        .link.ng-valid {
            background-color: lightgreen;
        }
        .link.ng-dirty.ng-invalid-required {
            background-color: red;
        }
        .link.ng-dirty.ng-invalid-link {
            background-color: yellow;
        }

    </style>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet">
</head>
<body ng-app="myApp" class="ng-cloak">
<div class="generic-container" ng-controller="ChannelController as ctrl">
    <div class="panel panel-default">
        <div class="panel-heading"><span class="lead">Channel Registration Form </span></div>
        <div class="formcontainer">
            <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                <input type="hidden" ng-model="ctrl.channel.id" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <!--<span>CSRF - "${_csrf.parameterName}" - "${_csrf.token}"</span> -->


                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="title">Title</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.channel.title" id="title" class="form-control input-sm" placeholder="Enter your title" required/>
                        </div>
                          <div class="has-error" ng-show="myForm.$dirty">
                               <span ng-show="myForm.title.$error.required">This is a required field</span>
                               <span ng-show="myForm.title.$invalid">This field is invalid </span>
                          </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="description">Description</label>
                        <div class="col-md-7">
                            <input type="text" ng-model="ctrl.channel.description" id="description" class="form-control input-sm" placeholder="Enter channel description. [This field is validation free]"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group col-md-12">
                        <label class="col-md-2 control-lable" for="link">Link</label>
                        <div class="col-md-7">
                            <input type="link" ng-model="ctrl.channel.link" id="link" class="link form-control input-sm" placeholder="Enter your Link" required/>
                            <div class="has-error" ng-show="myForm.$dirty">
                                <span ng-show="myForm.link.$error.required">This is a required field</span>
                                <span ng-show="myForm.link.$invalid">This field is invalid </span>
                            </div>
                        </div>
                    </div>
                </div>


                <div class="row">
                    <div class="form-actions floatRight">
                        <input type="submit"  value="{{!ctrl.channel.id ? 'Add' : 'Update'}}" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Channels List </span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ShortID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Link</th>
                    <th>Language</th>
                    <th>PubDate</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrl.channels">
                    <td><span ng-bind="u.shortid"></span></td>
                    <td><span ng-bind="u.title"></span></td>
                    <td><span ng-bind="u.description"></span></td>
                    <td><span ng-bind="u.link"></span></td>
                     <td><span ng-bind="u.language"></span></td>
                      <td><span ng-bind="u.pubDate"></span></td>
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
<script src="<c:url value='/static/js/service/channel_service.js' />"></script>
<script src="<c:url value='/static/js/controller/channel_controller.js' />"></script>
</body>
</html>