package kr.co.ta9.pandora3.termProject.dto;

import kr.co.ta9.pandora3.app.bean.CommonBean;

public class MemberDto extends CommonBean{
	private String user_id;
	private String user_wd;
	private String user_name;
	private String phone1;
	private String phone2;
	private String phone3;
	private String email1;
	private String email2;
	private String post;
	private String addr1;
	private String addr2;
	private String seller;
	private String join_date;
	private String update_date;
	
	public MemberDto() {
		super();
	}
	public MemberDto(String user_id, String user_wd, String user_name, String phone1, String phone2, String phone3,
			String email1, String email2, String post, String addr1, String addr2, String seller, String join_date,
			String update_date) {
		super();
		this.user_id = user_id;
		this.user_wd = user_wd;
		this.user_name = user_name;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.phone3 = phone3;
		this.email1 = email1;
		this.email2 = email2;
		this.post = post;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.seller = seller;
		this.join_date = join_date;
		this.update_date = update_date;
	}
	@Override
	public String toString() {
		return "MemberDto [user_id=" + user_id + ", user_wd=" + user_wd + ", user_name=" + user_name + ", phone1="
				+ phone1 + ", phone2=" + phone2 + ", phone3=" + phone3 + ", email1=" + email1 + ", email2=" + email2
				+ ", post=" + post + ", addr1=" + addr1 + ", addr2=" + addr2 + ", seller=" + seller + ", join_date="
				+ join_date + ", update_date=" + update_date + "]";
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_wd() {
		return user_wd;
	}
	public void setUser_wd(String user_wd) {
		this.user_wd = user_wd;
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
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
}
