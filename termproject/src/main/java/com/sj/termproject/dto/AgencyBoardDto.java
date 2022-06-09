package com.sj.termproject.dto;

import lombok.Data;

@Data
public class AgencyBoardDto {
	private int content_no;
	private String user_id;
	private String title;
	private String agency;
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

	private int reserve_no;
	private String class_date;
	private int reserve_num;
	private String reserve_time;

	private int review_no;
	private String review_content;
	private String review_date;
}