package com.sj.termproject.dto;

import lombok.Data;

@Data
public class ClassReviewDto {
	private int reserve_no;
	private String review_content;
	private String review_date;
	
	private String user_id;
	private String content_no;
}