package com.sj.termproject.service;

import java.util.List;

import com.sj.termproject.dto.AgencyBoardDto;
import com.sj.termproject.dto.ClassReviewDto;
import com.sj.termproject.dto.MemberDto;
import com.sj.termproject.dto.ReservationDto;

public interface UserService {
	public String test();

	public int joinUser(MemberDto dto);

	public MemberDto idCheck(String user_id);

	public int editUser(MemberDto dto);

	public List<MemberDto> idSearch(MemberDto dto);

	public MemberDto userSearch(MemberDto dto);

	public int passEdit(MemberDto dto);

	public int deleteUser(MemberDto dto);

	public int sellerApply(MemberDto dto);

	public List<AgencyBoardDto> list1(MemberDto dto);

	public List<AgencyBoardDto> list2(MemberDto dto);

	public int reserveDelete(ReservationDto dto);

	public int reviewWrite(ClassReviewDto dto);
}