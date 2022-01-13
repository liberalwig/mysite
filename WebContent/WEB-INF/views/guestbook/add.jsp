<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%
UserVo authUser = (UserVo) session.getAttribute("authUser");
%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
