<%@ page language="java" contentType="text/html; charset=ISO-8859-5" pageEncoding="ISO-8859-5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>RSS Feeds Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet">
</head>
<body ng-app="myApp" class="ng-cloak">
<!--'${newItemJson}'={&quot;channelID&quot;:2}-->
<div class="generic-container" ng-controller="ItemController as ctrl" ng-init="ctrl.getNewItemData('${newItemJson}')"> <!--ng-init="ctrl.getNewItemData('${newItemJson}')"-->
<!--<span>TEST: {{ctrl.newItem.channelID}}</span>-->
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">Items List for ${channelTitle}</span>
            <div class="span3 pull-right">
                <button type="button" ng-click="ctrl.getAllItemsForChannel(ctrl.newItem.channelID,10)" class="btn btn-info custom-width">Show Last 10 Items</button>
            </div>
        </div>
        <div class="tablecontainer">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>ChannelTitle</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Link</th>
                    <th>PubDate</th>
                    <th width="20%"></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="u in ctrl.items"> <!--| limitTo:-5 -->
                    <td><span ng-bind="u.channelTitle"></span></td>
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
 <!-- SCRIPT TO SHOW RSS BY DOWNLOADING <script src="//rss.bloople.net/?url=https%3A%2F%2Fdou.ua%2Ffeed%2F&limit=10&showtitle=false&type=js"> -->

</body>
</html>