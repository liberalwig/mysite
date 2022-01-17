package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// 필드
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 메소드 일반
	private void getConnection() {
		try {
			Class.forName(driver);

			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("접속성공(Board)");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	
	// 게시글리스트 폼	
	public List<BoardVo> getList() {
		List<BoardVo> boardList = new ArrayList<BoardVo>();

		getConnection();
		try {
			// SQL문 준비
			String query = "";
			query += " select   name, ";
			query += "          no, ";
			query += "          title, ";
			query += "          content, ";
			query += "          hit, ";
			query += "          to_char('YY-MM-DD HH:MI') regdate, ";
			query += "          userNo";
			query += " from     board bo, users us ";
			query += " where    board.userNo = users.no ";
			query += " order by board.no desc ";

			// 쿼리문 작성
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				int userNo = rs.getInt("userNo");
				
				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo);
				boardList.add(boardVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardList;
	}
	
	// 게시글 1개 읽기: 두 table join + 로그인, 비로그인 모두 열람 가능
	public BoardVo getBoard(int num) {
		BoardVo boardVo = null;
		
		int userNo = 0;
		getConnection();
	
		try {
			// SQL문 준비
			String query = "";
			query += " select   user.name name, ";
			query += "          board no, ";
			query += "          board.title title, ";
			query += "          board.content content, ";
			query += "          board.hit hit, ";
			query += "          to_char('YY-MM-DD HH:MI') regDate, ";
			query += "          board.userNo userNo";
			query += " from     board bo, users us ";
			query += " where    userNo = no ";
			query += " and		board.no = ? ";

			// 쿼리문 작성
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setInt(1, num);
			
			// 실행
			rs = pstmt.executeQuery();
			
			// 결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("regDate");
				userNo = rs.getInt("userNo");
				
				boardVo = new BoardVo(no, title, content, hit, regDate, userNo);
			}
			
			// SQL문 준비: hit조회수 증가
			query = "";
			query += " update  board ";
			query += " set     hit = hit+1 ";
			query += " where   no = ? ";

			// 쿼리문 작성
			pstmt = conn.prepareStatement(query);
			
			// 바인딩
			pstmt.setInt(1, userNo);
			
			// 실행
			int count = pstmt.executeUpdate();

			// 결과
			System.out.println("["+count+"건 실행되었습니다.(Board)]");
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardVo;
	}
	
	// 게시글 등록
	public void boardInsert(BoardVo boardVo) {

		getConnection();

		try {

			// SQL문 준비
			String query = "";
			query += " insert into board ";
			query += " values (seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";

			// 쿼리문 작성
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getuserNo());

			// 실행
			int count = pstmt.executeUpdate();

			// 결과처리
			System.out.println("[" + count + "건 추가되었습니다.(BoardDao)]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}
	
	// 게시글 삭제
	public void boardDelete(int num) {
		getConnection();

		try {

			// SQL문 준비
			String query = "";
			query += " delete board ";
			query += " where no = ? ";

			// 쿼리문 작성
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setInt(1, num);

			// 실행
			int count = pstmt.executeUpdate();

			// 결과처리
			System.out.println("[" + count + "건 삭제되었습니다.(BoardDao)]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}

	// 게시글 수정 반영
	public void boardUpdate(BoardVo boardVo) {
		getConnection();

		try {

			// SQL문 준비
			String query = "";
			query += " update board ";
			query += " set    title = ?, ";
			query += " 		  content = ? ";
			query += " where  no = ? ";

			// 쿼리문 작성
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getNo());

			// 실행
			int count = pstmt.executeUpdate();

			// 결과처리
			System.out.println("[" + count + "건 수정되었습니다.(BoardDao)]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
	}
	
}
