<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
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
            <th>Name</th>
            <th>Author</th>
            <th>Year</th>
            <th>Description</th>
            <th></th>
            <th></th>
            <th></th>
        </tr>

            <tr>
                <td>${book.name}</td>
                <td>${book.author}</td>
                <td>${book.year}</td>
                <td>${book.description}</td>
                <!--<td>${book.content}</td>-->
                <td><a href="<c:url value='/edit-${book.name}-book' />">edit</a></td>
                <td><a href="<c:url value='/delete-${book.name}-book' />">delete</a></td>
                <td><a href="<c:url value='/download-${book.name}-book' />">download</a></td>
            </tr>
        <br/>
        Go back to <a href="<c:url value='/list' />">List of All Books</a>
    </table>
</div>

<script type="text/javascript" src='<c:url value="/resources/js/jquery.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>

</body>
</html>
