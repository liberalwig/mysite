<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.javaex.vo.UserVo"%>


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
		</div>
		<!-- //header + //nav -->

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
					<h3>회원정보</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>회원</li>
							<li class="last">회원정보</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->

				<div id="user">
					<div id="modifyForm">
						<form action="/mysite/user" method="get">
							<input type="hidden" name="action" value="modify"> <input type="hidden" name="id"
								value="${requestuserVo.id}"
							>

							<!-- 아이디: 수정 폼에 들어가도 유지되어야 하고 안 지워지게 기입돼 있어야 하는 파트 -->
							<div class="form-group">
								<label class="form-text" for="input-uid">아이디</label> <span class="text-large bold" ${requestScope.userVo.id}></span>
							</div>


							<!-- 비밀번호 -->
							<div class="form-group">
								<label class="form-text" for="input-pass">패스워드</label> <input type="password" id="input-pass" name="password"
									value="${requestScope.userVo.password}" placeholder="비밀번호를 입력하세요"
								>
							</div>


							<!-- 이름 -->
							<div class="form-group">
								<label class="form-text" for="input-name">이름</label> <input type="text" id="input-name" name="name"
									value="${requestScope.userVo.name}"  placeholder="이름을 입력하세요"
								>
							</div>

							<!-- //젠더 -->
							<div class="form-group">
								<span class="form-text">성별</span>

								<c:choose>
									<c:when test="${requestScope.userVo.gender == 'female'}">
										<label for="rdo-male">남</label>
										<input type="radio" id="rdo-male" name="gender" value="male" >
										<label for="rdo-female">여</label>
										<input type="radio" id="rdo-female" name="gender" value="female" checked="checked">

									</c:when>
									<c:otherwise>
										<label for="rdo-male">남</label>
										<input type="radio" id="rdo-male" name="gender" value="male" checked="checked">

										<label for="rdo-female">여</label>
										<input type="radio" id="rdo-female" name="gender" value="female">
									</c:otherwise>
								</c:choose>

							</div>

							<!-- 버튼영역 -->
							<div class="button-area">
								<button type="submit" id="btn-submit">회원정보수정</button>
							</div>

							<input type="text" name="action" value="modify">  <input type="text" name="no" value="${requestScope.userVo.no}"> 
							<input type="text" name="id" value="${requestScopeScope.userVo.id}">

						</form>

					</div>
					<!-- //modifyForm -->

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