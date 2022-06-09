package com.sj.termproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sj.termproject.dao.UserDao;
import com.sj.termproject.dto.AgencyBoardDto;
import com.sj.termproject.dto.ClassReviewDto;
import com.sj.termproject.dto.MemberDto;
import com.sj.termproject.dto.ReservationDto;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao dao;

	@Override
	public String test() {
		return dao.test();
	}

	@Override
	public int joinUser(MemberDto dto) {
		return dao.joinUser(dto);
	}

	@Override
	public MemberDto idCheck(String user_id) {
		return dao.idCheck(user_id);
	}

	@Override
	public int editUser(MemberDto dto) {
		return dao.editUser(dto);
	}

	@Override
	public List<MemberDto> idSearch(MemberDto dto) {
		return dao.idSearch(dto);
	}

	@Override
	public MemberDto userSearch(MemberDto dto) {
		return dao.userSearch(dto);
	}

	@Override
	public int passEdit(MemberDto dto) {
		return dao.passEdit(dto);
	}

	@Override
	public int deleteUser(MemberDto dto) {
		return dao.deleteUser(dto);
	}

	@Override
	public int sellerApply(MemberDto dto) {
		return dao.sellerApply(dto);
	}

	@Override
	public List<AgencyBoardDto> list1(MemberDto dto) {
		return dao.list1(dto);
	}

	@Override
	public List<AgencyBoardDto> list2(MemberDto dto) {
		return dao.list2(dto);
	}

	@Override
	public int reserveDelete(ReservationDto dto) {
		return dao.reserveDelete(dto);
	}

	@Override
	public int reviewWrite(ClassReviewDto dto) {
		return dao.reviewWrite(dto);
	}
}