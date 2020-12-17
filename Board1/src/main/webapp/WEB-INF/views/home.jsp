<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ include file="include/header.jsp" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<!-- left column -->
			<div class="col-md-12">
				<!-- general form elements -->
				<div class="box">
            		<div class="box-header with-border">
              			<h3 class="box-title">HOME PAGE</h3>
              			<h2>${result}</h2>
            		</div>
          		</div>
			</div>
		</div>
	</section>
</body>
<%@ include file="include/footer.jsp" %>
</html>
