package com.sj.termproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sj.termproject.dao.AgencyDao;
import com.sj.termproject.dto.AgencyBoardDto;
import com.sj.termproject.dto.BoardRequestDto;
import com.sj.termproject.dto.ClassReviewDto;
import com.sj.termproject.dto.FavoriteDto;
import com.sj.termproject.dto.MemberDto;
import com.sj.termproject.dto.ReservationDto;

@Service
public class AgencyServiceImpl implements AgencyService {
	@Autowired
	AgencyDao dao;

	@Override
	public MemberDto agencyInfo(String user_id) {
		return dao.agencyInfo(user_id);
	}

	@Override
	public int writeClass(AgencyBoardDto dto) {
		return dao.writeClass(dto);
	}

	@Override
	public List<AgencyBoardDto> newestList() {
		return dao.newestList();
	}

	@Override
	public List<AgencyBoardDto> list(AgencyBoardDto dto) {
		return dao.list(dto);
	}

	@Override
	public List<AgencyBoardDto> endList(AgencyBoardDto dto) {
		return dao.endList(dto);
	}

	@Override
	public AgencyBoardDto classDetail(AgencyBoardDto dto) {
		return dao.classDetail(dto);
	}

	@Override
	public List<ReservationDto> reserveSearch(ReservationDto dto) {
		return dao.reserveSearch(dto);
	}
	
	@Override
	public int reserveNum(ReservationDto dto) {
		return dao.reserveNum(dto);
	}

	@Override
	public int reserve(ReservationDto dto) {
		return dao.reserve(dto);
	}

	@Override
	public ReservationDto reserveChecked(ReservationDto dto) {
		return dao.reserveChecked(dto);
	}

	@Override
	public FavoriteDto favoriteList(FavoriteDto dto) {
		return dao.favoriteList(dto);
	}

	@Override
	public int favoriteInsert(FavoriteDto dto) {
		return dao.favoriteInsert(dto);
	}

	@Override
	public int favoriteInsert2(FavoriteDto dto) {
		return dao.favoriteInsert2(dto);
	}

	@Override
	public int favoriteDelete(FavoriteDto dto) {
		return dao.favoriteDelete(dto);
	}

	@Override
	public List<Integer> favoriteNo(FavoriteDto dto) {
		return dao.favoriteNo(dto);
	}

	@Override
	public List<AgencyBoardDto> favoriteClass(FavoriteDto dto) {
		return dao.favoriteClass(dto);
	}

	@Override
	public List<ClassReviewDto> reviewList(ClassReviewDto dto) {
		return dao.reviewList(dto);
	}

	@Override
	public int classRequest(BoardRequestDto dto) {
		return dao.classRequest(dto);
	}

	@Override
	public List<BoardRequestDto> requestList(BoardRequestDto dto) {
		return dao.requestList(dto);
	}

	@Override
	public List<AgencyBoardDto> myClassList(MemberDto dto) {
		return dao.myClassList(dto);
	}

	@Override
	public List<AgencyBoardDto> searchList(String query) {
		return dao.searchList(query);
	}

	@Override
	public List<ReservationDto> reserveUserList(ReservationDto dto) {
		return dao.reserveUserList(dto);
	}
}