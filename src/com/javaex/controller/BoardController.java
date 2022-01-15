package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("board");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// 게시판 리스트
		if ("list".equals(action)) {
			System.out.println("/board > list");

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}

		// 게시판 글쓰기폼
		else if ("writeForm".equals(action)) {
			System.out.println("/board > writeForm");

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}

		// 게시판 글 읽기
		else if ("read".equals(action)) {
			System.out.println("/board > read");

			// 리다이렉트
			WebUtil.redirect(request, response, "/mysite/board?action=read");
		}

		// 게시판 겟글 수정폼
		else if ("modifyForm".equals(action)) {
			System.out.println("/board > modify");

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}

		// 이외 오류
		else {
			System.out.println("파라미터 없음");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
