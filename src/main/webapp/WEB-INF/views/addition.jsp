<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="<c:url value='/resources/css/bootstrap.css' />"  rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/css/bootstrap.min.css' />"  rel="stylesheet" type="text/css"/>
	<link href="<c:url value='/resources/css/font-awesome.min.css' />"  rel="stylesheet" type="text/css"/>
	<title>Book Addition Form</title>

<style>

	.error {
		color: #ff0000;
	}
</style>

</head>

<body>
<div class="container">
	<div class="well">
	<h2>Addition Form</h2>
	</div>
	<form:form method="POST" modelAttribute="book"  enctype="multipart/form-data" >
		<form:input type="hidden" path="id" id="id"/>
		<table class="table table-stripped">
			<tr>

				<td><label for="name">Name: </label> </td>
				<c:choose>
					<c:when test="${edit}">
						<td>${book.name}</td>
					</c:when>
					<c:otherwise>
						<td><form:input path="name" id="name" class="form-control input-sm"/></td>
						<td><form:errors path="name" cssClass="error"/></td>
					</c:otherwise>
				</c:choose>
		    </tr>
	    
			<tr>
				<td><label for="author">Author: </label> </td>
				<td><form:input path="author" id="author" class="form-control input-sm"/></td>
				<td><form:errors path="author" cssClass="error"/></td>
		    </tr>
	
			<tr>
				<td><label for="year">Year: </label> </td>
				<td><form:input path="year" id="year" class="form-control input-sm"/></td>
				<td><form:errors path="year" cssClass="error"/></td>
		    </tr>
	
			<tr>
				<td><label for="description">Description: </label> </td>
				<td><form:input path="description" id="description" class="form-control input-sm"/></td>
				<td><form:errors path="description" cssClass="error"/></td>
		    </tr>

            <tr>
               <!-- <td><label for="content">Content: </label> </td>-->
                <td><input type="file" name="file" class="form-control input-sm"/></td><!--path="content" class="file"-->
                <td>${fileError}</td>
            </tr>

            <tr>
				<!--<input   name="file" />-->
            </tr>

			<tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">

							<input type="submit" value="Update"/>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Add"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
	<br/>

	<br/>
	Go back to <a href="<c:url value='/list' />">List of All Books</a>
</div>


<script type="text/javascript" src='<c:url value="/resources/js/jquery.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
</body>
</html>