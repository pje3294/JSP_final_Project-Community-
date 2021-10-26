<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!doctype html>
<html lang="ko">

<head>
<title>Add-On Q & A</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<!-- VENDOR CSS -->
<link rel="stylesheet"
	href="assets/vendor/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="assets/vendor/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/vendor/linearicons/style.css">
<!-- MAIN CSS -->
<link rel="stylesheet" href="assets/css/main.css">
<!-- FOR DEMO PURPOSES ONLY. You should remove this in your project -->
<link rel="stylesheet" href="assets/css/demo.css">
<!-- GOOGLE FONTS -->
<link
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700"
	rel="stylesheet">
<!-- ICONS -->
<link rel="apple-touch-icon" sizes="76x76"
	href="assets/img/apple-icon.jpg">
<link rel="icon" type="image/png" sizes="96x96"
	href="assets/img/favicon.jpg">
</head>

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- 상단 바 -->
		<mytag:navbar userName="${user.name}" userNum="${user.userNum}" iconId="${user.iconId}" />
		<!-- 왼쪽 사이드 바 -->
		<mytag:sidebar ctgr='question' />
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title"><c:if test="${param.selUserNum >0}">${param.selUserName}님의 </c:if>
							Q & A</h3>
						</div>
						<div class="panel-body">
							<!-- 정렬 버튼 -->
							<div class="row">
								<div class="col-md-4">
									<button type="button"
										onclick="location.href='question.do?pageOrder=date&selUserNum=${selUserNum}&bTitle=${bTitle}&selUserName=${param.selUserName}'"
										class="btn btn-primary btn-block">최신순</button>
								</div>
								<div class="col-md-4">
									<button type="button"
										onclick="location.href='question.do?pageOrder=hit&selUserNum=${selUserNum}&bTitle=${bTitle}&selUserName=${param.selUserName}'"
										class="btn btn-primary btn-block">조회순</button>
								</div>
								<div class="col-md-4">
									<button type="button"
										onclick="location.href='question.do?pageOrder=reply&selUserNum=${selUserNum}&bTitle=${bTitle}&selUserName=${param.selUserName}'"
										class="btn btn-primary btn-block">댓글순</button>
								</div>
							</div>
							<!-- 정렬 버튼 END -->

							<br> <br>
							<!-- 검색 및 글쓰기 버튼 -->
							<form method="post" action="question.do" name="question">
							<input type="hidden" name="selUserNum" value="${selUserNum}">
							<input type="hidden" name="selUserName" value="${param.selUserName}">
								<div class="input-group">
									<span class="input-group-btn">
									<button class="btn btn-primary" type="submit">검색</button></span>
									<input class="form-control searchBox" type="text" name="bTitle" value="${param.bTitle}">
								</div>
							</form>
							<!-- 검색 및 글쓰기 버튼 END -->
						</div>
					</div>
					<!-- 게시물 리스트 -->
					<c:forEach var="v" items="${questions}">
						<div class="panel">
							<div class="row">
								<div class="col-md-2">
									<div class="panel-body text-center">
										<h4>
											<br> <span class="lnr lnr-home"></span>&nbsp;<a href="myPage.do?selUserNum=${v.userNum}&myListCtgr=question">${v.bWriter}</a>
										</h4>
										<span class="panel-subtitle">${v.bDate}</span>
									</div>
								</div>
								<div class="col-md-8">
									<div class="panel-heading">
										<h3 class="panel-title">
											<a href="detail.do?bId=${v.bId}&addHit=true">${v.bTitle}</a>
										</h3>
										<p class="panel-subtitle">${v.bLang}</p>
									</div>
									<div class="panel-body">
										<textarea name="bContent" rows="5" class="form-control"
											style="resize: none;" readonly>${v.bContent}</textarea>
									</div>
								</div>
								<div class="col-md-2">
									<div class="panel-body text-center div-left">
										<h4>조회수</h4>
										<h3>${v.bHit}</h3>
									</div>
									<div class="panel-body text-center div-left">
										<h4>댓글수</h4>
										<h3>${v.reCnt}</h3>
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
					<div class="text-right">
						<c:if test="${!empty user}">
							<button type="button" onclick="location.href='form.jsp'"
								class="btn btn-default">글쓰기</button>
						</c:if>
					</div>
					<!-- 게시물 리스트 END -->
					<!-- 페이징 버튼 -->
					<!-- 페이징 버튼 -->
							<mytag:paging pageLen="${pageLen}"
								pageNum="${pageNum}" paraName="pageNum"
								path="question.do?bTitle=${bTitle}&pageOrder=${pageOrder}&selUserNum=${selUserNum}&selUserName=${param.selUserName}" />

							<!-- 페이징 버튼 END -->
					<!-- 페이징 버튼 END -->
				</div>
			</div>
			<!-- END MAIN CONTENT -->
		</div>
		<!-- END MAIN -->
		<div class="clearfix"></div>
		<footer>
			<div class="container-fluid">
				<p class="copyright">
					&copy; 2021 <a href="index.jsp" target="_blank">Add-On</a>. All Rights Reserved.
				</p>
			</div>
		</footer>
	</div>
	<!-- END WRAPPER -->
	<!-- Javascript -->
	<script src="assets/vendor/jquery/jquery.min.js"></script>
	<script src="assets/vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/vendor/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<script src="assets/scripts/klorofil-common.js"></script>
</body>

</html>
