package com.sj.termproject.dto;

import lombok.Data;

@Data
public class MemberDto {
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
	
	private int state;
}
