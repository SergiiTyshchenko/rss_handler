<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>RSS Feeds Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet">
</head>
<body ng-app="myApp" class="ng-cloak">
<div class="generic-container" ng-controller="ItemController as ctrl">
<!--'${newItemJson}'-->
<!--<div class="generic-container" ng-controller="ItemController as ctrl" ng-init="ctrl.fetchAllItemsForChannel(u.channelID)">-->
<!--<span>TEST: {{ctrl.items.channelID}}</span> -->
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Items List </span></div>
        <div class="tablecontainer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ChannelID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Link</th>
                    <th>PubDate</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrl.items">
                    <td><span ng-bind="u.channelID"></span></td>
                    <td><span ng-bind="u.title"></span></td>
                    <td><span ng-bind="u.description.value"></span></td>
                    <td><span ng-bind="u.link"></span></td>
                    <td><span>{{u.pubDate | date:'dd-MM-yyyy HH:mm:ss'}}</span></td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>



<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.9/angular.min.js"></script>
<script src="<c:url value='/static/js/app.js' />"></script>
<script src="<c:url value='/static/js/service/item_service.js' />"></script>
<script src="<c:url value='/static/js/controller/item_controller.js' />"></script>


</body>
</html>