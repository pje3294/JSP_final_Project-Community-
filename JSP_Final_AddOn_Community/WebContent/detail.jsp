<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
<!-- Star -->
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/fontawesome-stars.css">

<body>
	<!-- WRAPPER -->
	<div id="wrapper">
		<!-- 상단 바 -->
		<mytag:navbar userName="${user.name}" userNum="${user.userNum}" iconId="${user.iconId}" />
		<!-- 왼쪽 사이드 바 -->
		<mytag:sidebar ctgr='${board.bCtgr}' />
		<!-- MAIN -->
		<div class="main">
			<!-- MAIN CONTENT -->
			<div class="main-content">
				<div class="container-fluid">
					<!-- 게시판 종류 -->
					<c:if test="${board.bCtgr=='announce'}">
						<h3 class="panel-title">공지 사항</h3>
					</c:if>
					<c:if test="${board.bCtgr=='question'}">
						<h3 class="panel-title">Q & A</h3>
					</c:if>
					<c:if test="${board.bCtgr=='board'}">
						<h3 class="panel-title">자유게시판</h3>
					</c:if>
					<!-- 게시판 종류 END-->
					<!-- 게시물 -->
					<div class="panel panel-headline"  >
						<div class="panel-heading">
							<h4 class="text-left">
								<span class="lnr lnr-user"></span>&nbsp;<a href="myPage.do?selUserNum=${board.userNum}&myListCtgr=${board.bCtgr}">${board.bWriter}</a>
							</h4>
							<span class="panel-subtitle text-right">${board.bDate}</span>
						</div>
						<div class="panel-heading">
							<h3 class="panel-title">${board.bTitle}</h3>
							<p class="panel-subtitle">${board.bLang}</p>
						</div>
						<div class="panel-body">
							<form method="post" action="form.jsp" name="detail">
								<pre>${board.bContent}</pre>
								<br> <input type="hidden" name="bTitle"
									value="${board.bTitle}"> <input type="hidden"
									name="bWriter" value="${board.bWriter}"> <input
									type="hidden" name="bCtgr" value="${board.bCtgr}"> <input
									type="hidden" name="bLang" value="${board.bLang}"> <input
									type="hidden" name="bId" value="${board.bId}"> <input
									type="hidden" name="bContent" value="${board.bContent}">

								<c:if test="${board.userNum==user.userNum || !empty manager}">
									<button type="submit" class="btn btn-default">글 수정</button>
								</c:if>
							</form>
						</div>
					</div>
					<!-- 게시물 END -->
					<!-- 댓글 -->
					<div class="panel">
						<div class="panel-heading">
							<h3 class="panel-title">댓글 ${param.insertedRid} 확인</h3>
						</div>
						<div class="panel-body">
							<!-- 댓글작성 -->
							<c:if test="${!empty user}">
								<form method="post" action="insertReply.do" name="reply">
									<input type="hidden" name="userNum" value="${user.userNum}">
									<input type="hidden" name="bId" value="${board.bId}">
									<input type="hidden" name="rWriter" value="${user.id}">
									<input type="hidden" name="parentId" value="0">
									<div class="input-group">
										<input class="form-control" type="text" name="rContent" required> 
										<span class="input-group-btn">
											<button class="btn btn-primary" type="submit">댓글 작성</button>
										</span>
									</div>
								</form>
							</c:if>
							<!-- 댓글작성 END -->
							<br>
							<!-- 댓글 리스트 -->
							<table class="table table-condensed replyBox">
								<tbody>
									<c:forEach var="replySet" items="${replySets}">
										<c:set var="reply" value="${replySet.rvo}" />
										<tr>
											<td>${reply.rWriter}</td>
											<td class="panel-action">
												<div class="panel-heading"  <c:if test="${reply.deleteAt == 'Y'}">id="deletedReply"</c:if> >
													<form method="post" action="updateReply.do" name="replyUp" >
														<input type="text" class="form-reply" name="rContent"
															<c:if test="${param.findId eq reply.rId}">id ="findReply"</c:if>  <c:if test="${reply.deleteAt == 'Y'}">id="deletedReplyContent"</c:if>
															<c:if test="${param.insertedRid eq reply.rId}">id ="insertedRid"</c:if>
															value="${reply.rContent}" required readonly>
														<div class="right"> 
															<!-- 댓글 수정 및 삭제 -->
															<c:if test="${(reply.userNum == user.userNum || !empty manager) && reply.deleteAt == 'N'}">
																<input type="hidden" name="rId" value="${reply.rId}">
																<input type="hidden" name="bId" value="${board.bId}">
																<input type="hidden" name="pageNum" value="${pageNum}">
																<button type="button" class="btn-update"><span class="lnr lnr-pencil"></span></button>
																<button type="submit" class="btn updateBtn"><span class="fa fa-pencil"></span></button>
																<button type="button" onclick="location.href='deleteReply.do?bId=${board.bId}&rId=${reply.rId}&pageNum=${pageNum}&parentId=${reply.parentId}&deleteAt=${reply.deleteAt}'"><span class="lnr lnr-cross-circle"></span></button>
															</c:if>
															<!-- 댓글 수정 및 삭제 END -->
															<button type="button" class="btn-toggle-collapse">댓글<span class="badge">${fn:length(replySet.rrlist)}</span>
															</button>
														</div>
													</form>
												</div>
												<div class="panel-body panel-action-body" <c:if test="${param.parentId eq reply.rId}">id="findRreply"</c:if>>
													<!-- 대댓글 작성 -->
													<c:if test="${!empty user}">
														<form method="post" action="insertReply.do" name="rreply">
															<input type="hidden" name="userNum" value="${user.userNum}">
															<input type="hidden" name="bId" value="${board.bId}">
															<input type="hidden" name="rWriter" value="${user.id}">
															<input type="hidden" name="parentId" value="${reply.rId}">
															<input type="hidden" name="pageNum" value="${pageNum}">
															<div class="input-group">
																<input class="form-control" type="text" name="rContent"
																	required> <span class="input-group-btn"><button
																		class="btn btn-primary" type="submit">댓글 작성</button></span>
															</div>
														</form>
													</c:if>
													<!-- 대댓글 작성 END -->
													<br>
													<!-- 대댓글 리스트 -->
													<table class="table table-condensed">
														<tbody>
															<c:forEach var="rreply" items="${replySet.rrlist}">
																<tr>
																	<td>${rreply.rWriter}</td>
																	<td><div class="panel-heading">
																			<form method="post" action="updateReply.do" name="rreplyUp">
																				<input type="text" class="form-reply" name="rContent" value="${rreply.rContent}"<c:if test="${param.findId eq rreply.rId}">id ="findReply"</c:if> 
																				<c:if test="${param.insertedRid eq rreply.rId}">id ="insertedRid"</c:if>
																				required readonly>
																				<div class="right">
																					<!-- 대댓글 수정 및 삭제 -->
																					<c:if test="${rreply.userNum == user.userNum|| !empty manager}">
																						<input type="hidden" name="pageNum" value="${pageNum}">
																						<input type="hidden" name="rId" value="${rreply.rId}">
																						<input type="hidden" name="bId" value="${board.bId}">
																						<button type="button" class="btn-update"><span class="lnr lnr-pencil"></span></button>
																						<button type="submit" class="btn updateBtn"><span class="fa fa-pencil"></span></button>
																						<button type="button" onclick="location.href='deleteReply.do?bId=${board.bId}&rId=${rreply.rId}&pageNum=${pageNum}&parentId=${rreply.parentId}&deleteAt=${reply.deleteAt}'"><span class="lnr lnr-cross-circle"></span></button>
																					</c:if>
																					<!-- 대댓글 수정 및 삭제 END -->
																				</div>
																			</form>
																		</div></td>
																	<td>${rreply.rDate}</td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<!-- 대댓글 리스트 END-->
												</div>
											</td>
											<td>${reply.rDate}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- 댓글 리스트 END -->
						</div>
					<!-- 페이징 버튼 -->
							<mytag:paging pageLen="${pageLen}"
								pageNum="${pageNum}" paraName="pageNum"
								path="detail.do?bId=${board.bId}" />

							<!-- 페이징 버튼 END -->
					</div>
					<!-- 댓글 END -->
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
	<script src="assets/scripts/jquery.barrating.min.js"></script> 

	<script type="text/javascript">
		function replyDel() {
			result = confirm("댓글을 삭제하시겠습니까?");
			if (result == true) {
				document.replyUp.action = "deleteReply.do";
				document.replyUp.submit();
			} else {
				return;
			}
		}
		function rreplyDel() {
			result = confirm("댓글을 삭제하시겠습니까?");
			if (result == true) {
				document.rreplyUp.action = "deleteReply.do";
				document.rreplyUp.submit();
			} else {
				return;
			}
		}
		// 댓글 찾기 --------------------------------------------
		window.onload = function(){
			
			if(document.getElementById("findRreply")){
				var findRreply = document.getElementById("findRreply")
				findRreply.style.display ='block'
			}
			if(document.getElementById("findReply")){
			var findReply = document.getElementById("findReply");
			findReply.focus();
			findReply.scrollIntoView({block:"center"});
			}
			if(document.getElementById("insertedRid")){
				var insertedReply = document.getElementById("insertedRid");
				console.log("확인123");
				insertedReply.scrollIntoView({block:"center"});
				}
		}
	</script>
</body>

</html>
