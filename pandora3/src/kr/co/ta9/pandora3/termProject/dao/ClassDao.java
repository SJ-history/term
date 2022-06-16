package kr.co.ta9.pandora3.termProject.dao;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import kr.co.ta9.pandora3.app.repository.BaseDao;
import kr.co.ta9.pandora3.app.servlet.ParameterMap;

@Repository
public class ClassDao extends BaseDao {
	
	/**
	 * 클래스 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject selectClassList(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Class.selectClassList", parameterMap);
	}
	
	/**
	 * 클래스 예약 회원 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject classMemberList0001(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Class.classMemberList0001", parameterMap);
	}
	
	/**
	 * 클래스 문의 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject classRequestList0001(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Class.classRequestList0001", parameterMap);
	}
	
	/**
	 * 문의 클래스 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject requestClass(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Class.requestClass", parameterMap);
	}
	
	/**
	 * 요청 사항 수정
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject classRequestUpdate(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Class.classRequestUpdate", parameterMap);
	}
	
	/**
	 * 요청 사항 클래스 삭제
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject classRequestDelete(ParameterMap parameterMap) throws Exception {
		return queryForGrid("Class.classRequestDelete", parameterMap);
	}
}
