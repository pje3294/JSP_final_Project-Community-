<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="userName"%>
<%@ attribute name="userNum"%>
<%@ attribute name="iconId"%>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="brand">
		<a href="index.jsp"><img src="assets/img/logo.png"
			alt="Klorofil Logo" class="img-responsive logo"></a>
	</div>
	<div class="container-fluid">
		<div class="navbar-btn">
			<button type="button" class="btn-toggle-fullwidth">
				<i class="lnr lnr-arrow-left-circle"></i>
			</button>
		</div>
		<div id="navbar-menu">
			<c:if test="${empty userName}">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <span>로그인</span> <i
							class="icon-submenu lnr lnr-chevron-down"></i></a>
						<ul class="dropdown-menu">
							<li><a href="login.jsp"><i class="lnr lnr-user"></i> <span>로그인</span></a></li>
							<li><a href="join.jsp"><i class="lnr lnr-exit"></i> <span>회원가입</span></a></li>
						</ul></li>
				</ul>
			</c:if>
			<c:if test="${!empty userName}">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><img src="images/${iconId}" class="img-circle iconImg" alt="유저사진">
						<span>${userName} 님</span> <i
							class="icon-submenu lnr lnr-chevron-down"></i></a>
						<ul class="dropdown-menu">
							<li><a href="myPage.do?selUserNum=${userNum}&myListCtgr=question"><i class="lnr lnr-user"></i> <span>마이페이지</span></a></li>
							<li><a href="logout.do"><i class="lnr lnr-exit"></i> <span>로그아웃</span></a></li>
						</ul></li>
				</ul>
			</c:if>
		</div>
	</div>
</nav>