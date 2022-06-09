package com.sj.termproject.dto;

import lombok.Data;

@Data
public class ClassReviewDto {
	private int review_no;
	private int reserve_no;
	private String user_id;
	private int content_no;
	private String review_content;
	private String review_date;

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
	private String access_mail;
	private String join_date;
	private String ip;
}