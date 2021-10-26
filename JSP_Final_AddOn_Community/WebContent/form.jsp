<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<!doctype html>
<html lang="ko">

<head>
<title>Add-On 게시판</title>
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
		<mytag:sidebar ctgr='${param.bCtgr}'/>
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<div class="panel">
						<!-- 수정 페이지 -->
						<c:if test="${!empty param.bId}">
						<form method="post" action="update.do" name="update">
						<input type="hidden" name="bId" value="${param.bId}">
							<div class="panel-heading">
								<h4 class="text-left">
								<span class="lnr lnr-user"></span>&nbsp;${user.id}
							</h4>
							</div>
							<div class="panel-body">
								<span>제목</span>
								<input type="text" class="form-control" name="bTitle" value="${param.bTitle}" required>
								<br>
								<span>테마</span>
								<input type="text" class="form-control" name="bLang" value="${param.bLang}" required>
								<br>
								<span>게시판 종류</span>
								<input type="text" class="form-control" name="bCtgr"  value="${param.bCtgr}" readonly>
								<br>
								<span>내용</span>
								<textarea name="bContent" rows="20" class="form-control"
									style="resize: none;" required>${param.bContent}</textarea>
								<br>
								<button type="submit" class="btn btn-default">글 수정</button>
								<button type="button" class="btn btn-default box-right" onclick="del()">글 삭제</button>
							</div>
						</form>
						</c:if>
						<!-- 수정 페이지 END -->
						<!-- 작성 페이지 -->
						<c:if test="${empty param.bId}">
						<form method="post" action="form.do" name="insert">
						<input type="hidden" name="bWriter" value="${user.id}">
						<input type="hidden" name="userNum" value="${user.userNum}">
							<div class="panel-heading">
								<h4 class="text-left">
								<span class="lnr lnr-user"></span>&nbsp;${user.id}
							</h4>
							</div>
							<div class="panel-body">
								<span>제목</span>
								<input type="text" class="form-control" name="bTitle" required>
								<br>
								<span>테마</span>
								<input type="text" class="form-control" name="bLang" required>
								<br>
								<span>게시판 종류</span>
								<label class="fancy-radio">
								<input name="bCtgr" value="question" type="radio" required> <span><i></i>Q & A</span>
								</label>
								<label class="fancy-radio">
								<input name="bCtgr" value="board" type="radio"> <span><i></i>자유게시판</span>
								</label>
								<!-- 관리자일 경우 공지사항선택 가능 -->
								<c:if test="${!empty manager}">
								<label class="fancy-radio">
								<input name="bCtgr" value="announce" type="radio"> <span><i></i>공지사항</span>
								</label>
								</c:if>
								<br>
								<span>내용</span>
								<textarea name="bContent" rows="20" class="form-control"
									style="resize: none;" required></textarea>
								<br>
								<button type="submit" class="btn btn-default">글 작성</button>
							</div>
						</form>
						</c:if>
						<!-- 작성 페이지 END -->
					</div>
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

	<script type="text/javascript">
		function del() {
				result = confirm("글을 삭제하시겠습니까?");
				if (result == true) {
					document.update.action = "delete.do";
					document.update.submit();
				} else {
					return;
				}
			}
	</script>
</body>

</html>
