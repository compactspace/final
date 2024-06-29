<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body {
	padding: 0px 0px;
	margin: 0px 0px;
}

a {
	color: rgb(255, 255, 255);
	text-decoration: none;
}





#navebarwrapper{
/* background:white; */

}

.naveulwrapper ul{

font-weight: 600;
line-height: 3em;
color:rgb(255, 255, 255);
margin-right: 70px;
    font-size: 20px;
    width: 100%;
	display: flex;
	justify-content: space-between;
	height: 80px;	
	list-style: none;
	padding: 0px 0px;
	margin: 0px 0px;
}

.naveulwrapper ul li{

padding:10px 10px

}



.right {
	display: flex;
	justify-content: space-between;
}

</style>
</head>
<body>

	<div id="navebarwrapper">

		<div class="naveulwrapper">
			<ul>
				<div class="lefth">
					<li><a href="mainhome.jsp">Home</a></li>
				</div>



				<div class="right">
					<c:choose>

						<c:when test="${userId ne null || user_where=='finalluser'}">
							<li><c:if test="${user_where=='finalluser'}">
									<a class="nav-link" href="mypersonalinfo.do?id=${userId}">정보수정</a>
								</c:if></li>
							<li><a href="generalcartlist.do?id=${userId}">장바구니</a></li>
							<li><a
								href="mypayinfo.do?user_code=${user_code}&id=${userId}">결제현황</a></li>																						
							<li><a href="myreserveinfo.do?user_code=${user_code}">예약현황</a></li>
							<li><a href="logout.do">로그아웃</a></li>

						</c:when>

						<c:otherwise>
							<li><a href="login.jsp">로그인/가입</a></li>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<!-- <a class="nav-link" href="/adminhome.do">관리자 모드</a> -->
								<h3 class="managermode">관리자모드</h3>
								<p>
									principal:
									<sec:authentication property="principal" />
								</p>
								<li id="securitylogout" class="nav-item active"><a
									class="nav-link">시큐리티 유저 로그아웃</a></li>
							</sec:authorize>
						</c:otherwise>
					</c:choose>
				</div>


			</ul>

		</div>
	</div>



</body>
</html>