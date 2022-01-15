package com.javaex.vo;

public class BoardVo {

	// 필드
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regdate;
	private int userno;
	
	
	// 생성자
	public BoardVo() {
		
	}
	
	public BoardVo(int no, String title, String content, int hit, String regdate, int userno) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.hit = hit;
		this.regdate = regdate;
		this.userno = userno;
	}

	
	// 메소드 gs
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}

	
	// 메소드 일반
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regdate="
				+ regdate + ", userno=" + userno + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
