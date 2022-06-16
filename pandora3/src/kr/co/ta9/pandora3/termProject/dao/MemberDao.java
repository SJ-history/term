package kr.co.ta9.pandora3.termProject.dao;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import kr.co.ta9.pandora3.app.repository.BaseDao;
import kr.co.ta9.pandora3.app.servlet.ParameterMap;

@Repository
public class MemberDao extends BaseDao {
	
	/**
	 * 회원 리스트 조회 (그리드형)
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject selectMemberList(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Member.selectMemberList", parameterMap);
	}
	
	/**
	 * 작가 등록 요청 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject memberApplyList(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Member.memberApplyList", parameterMap);
	}

	/**
	 * 회원 작가 등록 승인
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject ApplyAccess(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Member.ApplyAccess", parameterMap);
	}
	
	/**
	 * 회원 작가 등록 거절
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject ApplyDelete(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Member.ApplyDelete", parameterMap);
	}
}
