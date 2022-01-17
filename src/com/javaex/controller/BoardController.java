package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("/board");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		// 게시판 리스트 읽기
		if ("list".equals(action)) {
			System.out.println("/action > list");

			List<BoardVo> boardList = new BoardDao().getList();
			request.setAttribute("boardList", boardList);

			// 포워드: 포워드인지 리다이렉인지 2022.01.16(일)에 파악
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}

		// 게시판 글쓰기폼
		else if ("writeForm".equals(action)) {
			System.out.println("/action > writeForm");

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		}

		// 게시판 글쓰기
		else if ("write".equals(action)) {
			System.out.println("/action > write");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = authUser.getNo();

			BoardVo boardVo = new BoardVo(title, content, userNo);
			new BoardDao().boardInsert(boardVo);

			// 리다이렉
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}

		// 게시판 글 들어가서 읽기 폼
		else if ("read".equals(action)) {
			System.out.println("/action > read");

			int no = Integer.parseInt(request.getParameter("no"));

			new BoardDao().hitAdd(no);
			BoardVo boardVo = new BoardDao().getBoard(no);
			request.setAttribute("boardVo", boardVo);

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}

		// 게시판 게시글 삭제
		else if ("delete".equals(action)) {// 게시글 삭제
			System.out.println("action > delete");

			int num = Integer.parseInt(request.getParameter("no"));
			new BoardDao().boardDelete(num);

			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}

		// 게시판 수정폼
		else if ("modifyForm".equals(action)) {
			System.out.println("/board > modifyForm");

			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardVo = new BoardDao().getBoard(no);
			request.setAttribute("boardVo", boardVo);

			// 리다이렉
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		}

		// 게시판 게시글 수정
		else if ("modify".equals(action)) {
			System.out.println("/action > modify");

			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");

			BoardVo boardVo = new BoardVo(no, title, content);
			new BoardDao().boardUpdate(boardVo);

			// 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		}

		// 이외 오류
		else {
			System.out.println("파라미터 없음");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
