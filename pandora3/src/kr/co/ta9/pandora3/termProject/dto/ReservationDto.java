package kr.co.ta9.pandora3.termProject.dto;

import kr.co.ta9.pandora3.app.bean.CommonBean;

public class ReservationDto extends CommonBean {
	private int reserve_no;
	private String user_id;
	private int content_no;
	private int ps_num;
	private String class_date;
	private int reserve_num;
	private String reserve_time;

	private String user_name;
	private String phone1;
	private String phone2;
	private String phone3;
	private String email1;
	private String email2;
	
	public ReservationDto() {
		super();
	}

	public ReservationDto(int reserve_no, String user_id, int content_no, int ps_num, String class_date,
			int reserve_num, String reserve_time, String user_name, String phone1, String phone2, String phone3,
			String email1, String email2) {
		super();
		this.reserve_no = reserve_no;
		this.user_id = user_id;
		this.content_no = content_no;
		this.ps_num = ps_num;
		this.class_date = class_date;
		this.reserve_num = reserve_num;
		this.reserve_time = reserve_time;
		this.user_name = user_name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.email1 = email1;
		this.email2 = email2;
	}

	@Override
	public String toString() {
		return "ReservationDto [reserve_no=" + reserve_no + ", user_id=" + user_id + ", content_no=" + content_no
				+ ", ps_num=" + ps_num + ", class_date=" + class_date + ", reserve_num=" + reserve_num
				+ ", reserve_time=" + reserve_time + ", user_name=" + user_name + ", phone1=" + phone1 + ", phone2="
				+ phone2 + ", phone3=" + phone3 + ", email1=" + email1 + ", email2=" + email2 + "]";
	}

	public int getReserve_no() {
		return reserve_no;
	}

	public void setReserve_no(int reserve_no) {
		this.reserve_no = reserve_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getContent_no() {
		return content_no;
	}

	public void setContent_no(int content_no) {
		this.content_no = content_no;
	}

	public int getPs_num() {
		return ps_num;
	}

	public void setPs_num(int ps_num) {
		this.ps_num = ps_num;
	}

	public String getClass_date() {
		return class_date;
	}

	public void setClass_date(String class_date) {
		this.class_date = class_date;
	}

	public int getReserve_num() {
		return reserve_num;
	}

	public void setReserve_num(int reserve_num) {
		this.reserve_num = reserve_num;
	}

	public String getReserve_time() {
		return reserve_time;
	}

	public void setReserve_time(String reserve_time) {
		this.reserve_time = reserve_time;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
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