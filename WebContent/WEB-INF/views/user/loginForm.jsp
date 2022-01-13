<!-- 2022.01.12(수)11:13수업 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%
UserVo authUser = (UserVo) session.getAttribute("authUser"); //모든 애의 오브젝트이므로 혹시 몰라서 형변환해준다
String result = request.getParameter("result"); //로그인 시도 후 실패 시에는 파라미터에 result=fail이라는 꼬랑지를 달아주기로 함.
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">
</head>

<body>
	<div id="wrap">

		<div id="header" class="clearfix">
			<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
		</div> <!-- //header+ //nav -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>회원</h2>
				<ul>
					<li>회원정보</li>
					<li>로그인</li>
					<li>회원가입</li>
				</ul>
			</div>
			<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>로그인</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>회원</li>
						<li class="last">로그인</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="user">
				<div id="loginForm">
					<form action="/mysite/user" method="get">

						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> <input type="text" id="input-uid" name="id" value=""
								placeholder="아이디를 입력하세요"
							>
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">비밀번호</label> <input type="text" id="input-pass" name="password"
								value="" placeholder="비밀번호를 입력하세요"
							>
						</div>

						<%
						if ("fail".equals(result)) {
						%>
						<p>로그인에 실패했습니다. 다시 로그인 해주세요.</p>
						<!--로그인 시도 후 실패 시에는 파라미터에 result=fail이라는 꼬랑지를 달아주기로 함-->
						<%
						}
						%>

						<!-- 버튼영역 -->
						<div class="button-area">
							<button type="submit" id="btn-submit">로그인</button>
						</div>

						<!--action=login 기능이 사용자에겐 안 보이도록-->
						<input type="text" name="action" value="login">


					</form>
				</div>
				<!-- //loginForm -->
			</div>
			<!-- //user -->
		</div>
		<!-- //content  -->

	</div>
	<!-- //container  -->

	<jsp:include page="/WEB-INF/views/include/footer.jsp"></jsp:include>
	<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

</html>