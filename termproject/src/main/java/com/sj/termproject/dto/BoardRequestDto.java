package com.sj.termproject.dto;

import lombok.Data;

@Data
public class BoardRequestDto {
	private int request_no;
	private int content_no;
	private String user_id;
	private String request_type;
	private String request_title;
	private String request_content;
	private String request_condition;
	private String request_date;

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
}
