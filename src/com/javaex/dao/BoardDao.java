//@@@@@@@@@@@@@@@@@글을 읽는 건 autbboard아니어도 가능


package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.BoardVo;

public class BoardDao {
	// 필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 메소드 일반
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	private void close() {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error." + e);
		}
	}

	// authboard로 인증된 사람들이 게시글을 써서 저장 메소드: 로그인 과정 불필요
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@형태만@@@@@@@@@@@@@@@@@@@
	public int insert(BoardVo boardVo) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += " insert into board ";
			query += " values(seq_board_no.nextval, ?, ?, ?, ?, sysdate, ? ) ";

			// 문자열을=>쿼리문으로
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setInt(1, boardVo.getNo());
			pstmt.setString(2, boardVo.getTitle());
			pstmt.setString(3, boardVo.getContent());
			pstmt.setInt(4, boardVo.getHit());
			pstmt.setInt(6, boardVo.getUserno());

			// 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println(count + "건이 실행되었습니다");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
		return count;
	}

	//  게시글 조회를 위한 회원여부 확인(+비회원도 열람가능)
		public BoardVo getboard(String id, String password) {
			BoardVo boardVo = null;
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				// 문자열@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				String query = "";
				query += " select id, ";
				query += " from boards ";
				query += " where user_no =? " ;
				System.out.println(query);

				// 문자열을=>쿼리문으로
				pstmt = conn.prepareStatement(query);

				// 바인딩
				pstmt.setString(1, id);
				pstmt.setString(2, password);

				// 실행
				rs = pstmt.executeQuery(); // 회원가입은 count = pstmt.executeUpdate(); 였다. 로그인/수정은 db필요하므로 쿼리문 필수.

				// 4. 결과처리
				while (rs.next()) {
					int no = rs.getInt("no");// 회원고유번호(PK격) 쿼리문입력해서 저 아이디,패스워드인 사람의 넘버와 이름을 셀렉하고 그중 넘버를 Int에 담아줘
					String name = rs.getString("name");

					boardVo = new BoardVo(); // 좌항에 자료형 왜 안 써?
					boardVo.setNo(no);
				
				}

			} catch (SQLException e) {
				System.out.println("error: " + e);
			}

			close();
			return boardVo;
		}
	
	
	
	
	
	
	// no유지한 로그인 상태로 게시글 수정@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	public BoardVo getboardVo(int no) {
		BoardVo boardVo = null;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += " select , ";
			query += " from boards ";
			query += " where no = ? ";
			query += " and user_no = ? "

			System.out.println(query);

			// 문자열을=>쿼리문으로
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeQuery();

			// 4. 결과처리
			while (rs.next()) {
				int number = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name"); // '정우성 님 환영합니다'를 안 보여주려면 이 행 안 쓰면 돼
				String gender = rs.getString("gender");
				// String id = rs.getString("id"); //회원정보 수정하고도 세션에 유지될 정보가 이름이 아니라 id이기 위해 위
				// 가리고 이 행 생성

				boardVo = new boardVo(no, id, password, name, gender);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
		return boardVo;
	}

	// no유지한채로 로그인 상태에서 수정한 게시글을 db에 반영업데이트
	public void Update(boardVo boardVo) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열
			String query = "";
			query += "update boards";
			query += " set id = ?, ";
			query += "     password = ?, ";
			query += "     name = ?,";
			query += "     gender = ? ";
			query += "where no = ?; ";

			// 쿼리문을=>문자열로
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, boardVo.getId());
			pstmt.setString(2, boardVo.getPassword());
			pstmt.setString(3, boardVo.getName());
			pstmt.setString(4, boardVo.getGender());
			pstmt.setInt(5, boardVo.getNo());

			// 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println("[" + count + " 건이 업데이트 되었습니다. ]");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
