<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<c:url value='/resources/css/bootstrap.css' />"  rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/css/bootstrap.min.css' />"  rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/css/font-awesome.min.css' />"  rel="stylesheet" type="text/css"/>
<title>Addition Confirmation Page</title>
</head>
<body>
<div class="container">
	<div class="well">
		<strong>List of Books</strong>
	</div>
	message : ${success}
	<br/>
	<br/>
	Go back to <a href="<c:url value='/list' />">List of All Books</a>
</div>

<script type="text/javascript" src='<c:url value="/resources/js/jquery.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
</body>
</html>