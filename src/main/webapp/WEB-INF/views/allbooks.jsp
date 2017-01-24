<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" uri="/WEB-INF/taglibs/customTaglib.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value='/resources/css/bootstrap.css' />"  rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/resources/css/bootstrap.min.css' />"  rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/resources/css/font-awesome.min.css' />"  rel="stylesheet" type="text/css"/>
	<title>Library HiEndSystems</title>
</head>
<body>
<div class="container">
    <div class="well">
        <strong>List of Books</strong>
    </div>
    <table class="table table-stripped">
        <tr>
            <th>No</th>
            <th>Name</th>
            <th>Author</th>
            <th>Year</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${book}" var="book" varStatus="itr">
            <tr>
                <td>${offset + itr.index +1 }</td>
                <td>${book.name}</td>
                <td>${book.author}</td>
                <td>${book.year}</td>
                <td><a href="<c:url value='/edit-${book.name}-book' />">edit</a></td>
                <td><a href="<c:url value='/delete-${book.name}-book' />">delete</a></td>
                <td><a href="<c:url value='/view-${book.name}-book' />">view</a></td>
            </tr>
        </c:forEach>

        <br/>
        <a href="<c:url value='/new' />">Add New Book</a>
    </table>
    <tag:paginate max="15" offset="${offset}" count="${count}"
                  uri="/book/list" next="&raquo;" previous="&laquo;" />
</div>
<br>
<br>
<div class="container">
    <div class="well">
        <strong>Cashed Books</strong>
    </div>
    <table class="table table-stripped">
        <tr>
            <th>Name</th>
            <th>Author</th>
            <th>Year</th>
            <th></th>
        </tr>
        <c:forEach items="${cashed}" var="cashed_book" varStatus="itr">
            <tr>
                <td>${cashed_book.name}</td>
                <td>${cashed_book.author}</td>
                <td>${cashed_book.year}</td>
                <td><a href="<c:url value='/view-${cashed_book.name}-book' />">view</a></td>
            </tr>
        </c:forEach>
        <br/>
    </table>
</div>

<script type="text/javascript" src='<c:url value="/resources/js/jquery.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
</body>
</html>