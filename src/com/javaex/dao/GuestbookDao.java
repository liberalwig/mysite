//2022.01.11(화)23:00
package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle.thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 메소드 일반
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("접속 양호");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);

		} catch (SQLException e) {
			System.out.println("eroor:" + e);
		}

	}

	// 리스트배열화 하는 이 파트 모르겠어서 복붙함
	public List<GuestbookVo> getList() {
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();

		getConnection();
		try {

			// 3. SQL문 준비 / 바인딩 / 실행 // 4.결과처리
			String query = "";
			query += " select   no ";
			query += "          ,name ";
			query += "          ,password ";
			query += "          ,content ";
			query += "          ,to_char(reg_date, 'YYYY-MM-DD HH:MI:SS') reg_date ";
			query += " from     guestbook ";
			query += " order by reg_date desc ";

			// 쿼리
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				GuestbookVo guestbookVo = new GuestbookVo(no, name, password, content, regDate);
				guestbookList.add(guestbookVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return guestbookList;
	}

	// 5.자원 정리 _ close();정의
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

	// 방명록 등록
	public int guestbookInsert(GuestbookVo guestbookVo) {
		int count = 0;
		getConnection();

		try {// 3. 쿼리문 준비 / 바인딩 /실행
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";

			// 쿼리 만들기
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setString(2, guestbookVo.getName());
			pstmt.setString(3, guestbookVo.getPassword());
			pstmt.setString(4, guestbookVo.getContent());

			// 쿼리문 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println("[" + count + "건 추가되었습니다. ]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;

	}

	// 방명록 삭제
	public int delete(int number, String password) {
		int count = 0;
		getConnection();

		try {// 3. 쿼리문 준비 / 바인딩 / 실행
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";

			// 쿼리문 만들기
			pstmt = conn.prepareStatement(query);

			// 바인딩
			pstmt.setInt(1, number);
			pstmt.setString(2, password);

			// 쿼리문 실행
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println("[" + count + "건 삭제되었습니다. ]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count; // 위에 변수 잡아놔서 일단 둠

	}

}
