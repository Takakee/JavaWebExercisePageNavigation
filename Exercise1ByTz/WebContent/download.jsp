<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">TZdownloads</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath }/main.jsp">首页</a></li>
					<li><a href="#">下载</a></li>
					<li><a href="#">设计</a></li>
					<li><a href="#">相册</a></li>
					<li><a href="#">论坛</a></li>
					<li><a href="#">关于</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="panel panel-default">
		<div class="panel-heading">Download List:</div>
		<div class="panel-body">
			<div class="list-group">

				<c:if test="${empty DownloadList }">
					out.write("没有下载项！");
				</c:if>
				
				<c:forEach items="${DownloadList }" var="dl">
					<a href="/Exercise1ByTz/controller/DownloadServlet?id=${dl.id }" class="list-group-item">
						<h4>id号：${dl.id } </h4>
						<p>下载文件名称：${dl.name }</p>
					</a>
				</c:forEach>
		
				<a href="#" class="list-group-item">
					<h4>占两排</h4>
					<p>多打点字</p>
				</a>
			</div>
		</div>
	</div>

	
</body>
</html>