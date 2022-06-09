package com.sj.termproject.dto;

import lombok.Data;

@Data
public class ReservationDto {
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
}