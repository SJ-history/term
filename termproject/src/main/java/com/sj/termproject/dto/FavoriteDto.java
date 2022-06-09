package com.sj.termproject.dto;

import lombok.Data;

@Data
public class FavoriteDto {
	private int content_no;
	private String user_id;
	private String favorite_date;
	private String favorite_condition;
}