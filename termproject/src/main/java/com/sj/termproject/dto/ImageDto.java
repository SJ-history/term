package com.sj.termproject.dto;

import lombok.Data;

@Data
public class ImageDto {
	private String uuid;
	private String fileName;
	private String contentType;

	public ImageDto(String uuid, String fileName, String contentType) {
		this.uuid = uuid;
		this.fileName = fileName;
		this.contentType = contentType;
	}
}