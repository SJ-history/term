package kr.co.ta9.pandora3.termProject.dto;

import kr.co.ta9.pandora3.app.bean.CommonBean;

public class BoardRequestDto extends CommonBean {
	private int request_no;
	private int content_no;
	private String request_type;
	private String request_title;
	private String request_content;
	private String request_condition;
	private String request_date;

	private String user_id;
	private String user_name;
	private String email1;
	private String email2;
	
	public BoardRequestDto() {
		super();
	}
	public BoardRequestDto(int request_no, int content_no, String user_id, String request_type, String request_title,
			String request_content, String request_condition, String request_date, String user_name, String email1,
			String email2) {
		super();
		this.request_no = request_no;
		this.content_no = content_no;
		this.user_id = user_id;
		this.request_type = request_type;
		this.request_title = request_title;
		this.request_content = request_content;
		this.request_condition = request_condition;
		this.request_date = request_date;
		this.user_name = user_name;
		this.email1 = email1;
		this.email2 = email2;
	}
	@Override
	public String toString() {
		return "BoardRequestDto [request_no=" + request_no + ", content_no=" + content_no + ", user_id=" + user_id
				+ ", request_type=" + request_type + ", request_title=" + request_title + ", request_content="
				+ request_content + ", request_condition=" + request_condition + ", request_date=" + request_date
				+ ", user_name=" + user_name + ", email1=" + email1 + ", email2=" + email2 + "]";
	}
	public int getRequest_no() {
		return request_no;
	}
	public void setRequest_no(int request_no) {
		this.request_no = request_no;
	}
	public int getContent_no() {
		return content_no;
	}
	public void setContent_no(int content_no) {
		this.content_no = content_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getRequest_type() {
		return request_type;
	}
	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	public String getRequest_title() {
		return request_title;
	}
	public void setRequest_title(String request_title) {
		this.request_title = request_title;
	}
	public String getRequest_content() {
		return request_content;
	}
	public void setRequest_content(String request_content) {
		this.request_content = request_content;
	}
	public String getRequest_condition() {
		return request_condition;
	}
	public void setRequest_condition(String request_condition) {
		this.request_condition = request_condition;
	}
	public String getRequest_date() {
		return request_date;
	}
	public void setRequest_date(String request_date) {
		this.request_date = request_date;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
}