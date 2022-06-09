package com.sj.termproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sj.termproject.dto.AgencyBoardDto;
import com.sj.termproject.dto.BoardRequestDto;
import com.sj.termproject.dto.ClassReviewDto;
import com.sj.termproject.dto.FavoriteDto;
import com.sj.termproject.dto.MemberDto;
import com.sj.termproject.dto.ReservationDto;

@Mapper
public interface AgencyDao {
	public MemberDto agencyInfo(String user_id);

	public int writeClass(AgencyBoardDto dto);

	public List<AgencyBoardDto> newestList();

	public List<AgencyBoardDto> list(AgencyBoardDto dto);

	public List<AgencyBoardDto> endList(AgencyBoardDto dto);

	public AgencyBoardDto classDetail(AgencyBoardDto dto);

	public List<ReservationDto> reserveSearch(ReservationDto dto);

	public int reserveNum(ReservationDto dto);
	
	public int reserve(ReservationDto dto);

	public ReservationDto reserveChecked(ReservationDto dto);

	public FavoriteDto favoriteList(FavoriteDto dto);

	public int favoriteInsert(FavoriteDto dto);

	public int favoriteInsert2(FavoriteDto dto);
	
	public int favoriteDelete(FavoriteDto dto);

	public List<Integer> favoriteNo(FavoriteDto dto);

	public List<AgencyBoardDto> favoriteClass(FavoriteDto dto);

	public List<ClassReviewDto> reviewList(ClassReviewDto dto);

	public int classRequest(BoardRequestDto dto);

	public List<BoardRequestDto> requestList(BoardRequestDto dto);

	public List<AgencyBoardDto> myClassList(MemberDto dto);

	public List<AgencyBoardDto> searchList(String query);
	
	public List<ReservationDto> reserveUserList(ReservationDto dto);
}