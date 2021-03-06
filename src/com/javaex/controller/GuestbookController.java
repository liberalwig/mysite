//2022.01.11(화)20:45-2022.01.12(수)01:14 완성
package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("/guest");
		String action = request.getParameter("action");

		if ("addList".equals(action)) {
			System.out.println("action > addList");

			GuestbookDao guestbookDao = new GuestbookDao();
			List<GuestbookVo> guestbookList = guestbookDao.getList();

			request.setAttribute("gList", guestbookList);

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("add".equals(action)) {
			System.out.println("action > add");

			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestbookVo guestbookVo = new GuestbookVo(name, password, content);

			GuestbookDao guestbookDao = new GuestbookDao();
			guestbookDao.guestbookInsert(guestbookVo);

			WebUtil.redirect(request, response, "/mysite/guest?action=addList");

		} else if ("deleteForm".equals(action)) {
			System.out.println("action > deletForm");

			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		} else if ("delete".equals(action)) {
			System.out.println("action > delete");

			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			new GuestbookDao().guestbookDelete(no, password);

			WebUtil.redirect(request, response, "/mysite/guest?action=addList");

		} else {
			System.out.println("파라미터 없음");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
