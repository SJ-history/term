package kr.co.ta9.pandora3.termProject.dto;

import kr.co.ta9.pandora3.app.bean.CommonBean;

public class AgencyBoardDto extends CommonBean {
	private int content_no;
	private String user_id;
	private String title;
	private int ps_num;
	private String category;
	private String write_date;
	private String image0;
	private String head;
	private String body;
	private String image1;
	private String youtube;
	private String cl_addr;
	private String start_date;
	private String end_date;
	private String start_time;
	private String end_time;

	private String user_name;
	private String email1;
	private String email2;
	
	public AgencyBoardDto() {
		super();
	}
	public AgencyBoardDto(int content_no, String user_id, String title, int ps_num, String category, String write_date,
			String image0, String head, String body, String image1, String youtube, String cl_addr, String start_date,
			String end_date, String start_time, String end_time, String user_name, String email1, String email2) {
		super();
		this.content_no = content_no;
		this.user_id = user_id;
		this.title = title;
		this.ps_num = ps_num;
		this.category = category;
		this.write_date = write_date;
		this.image0 = image0;
		this.head = head;
		this.body = body;
		this.image1 = image1;
		this.youtube = youtube;
		this.cl_addr = cl_addr;
		this.start_date = start_date;
		this.end_date = end_date;
		this.start_time = start_time;
		this.end_time = end_time;
		this.user_name = user_name;
		this.email1 = email1;
		this.email2 = email2;
	}
	@Override
	public String toString() {
		return "AgencyBoardDto [content_no=" + content_no + ", user_id=" + user_id + ", title=" + title + ", ps_num="
				+ ps_num + ", category=" + category + ", write_date=" + write_date + ", image0=" + image0 + ", head="
				+ head + ", body=" + body + ", image1=" + image1 + ", youtube=" + youtube + ", cl_addr=" + cl_addr
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", start_time=" + start_time + ", end_time="
				+ end_time + ", user_name=" + user_name + ", email1=" + email1 + ", email2=" + email2 + "]";
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPs_num() {
		return ps_num;
	}
	public void setPs_num(int ps_num) {
		this.ps_num = ps_num;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public String getImage0() {
		return image0;
	}
	public void setImage0(String image0) {
		this.image0 = image0;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getYoutube() {
		return youtube;
	}
	public void setYoutube(String youtube) {
		this.youtube = youtube;
	}
	public String getCl_addr() {
		return cl_addr;
	}
	public void setCl_addr(String cl_addr) {
		this.cl_addr = cl_addr;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
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
