package kr.co.ta9.pandora3.termProject.manager;


import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ta9.pandora3.app.servlet.ParameterMap;
import kr.co.ta9.pandora3.termProject.dao.ClassDao;
import kr.co.ta9.pandora3.termProject.dto.BoardRequestDto;

/**
 * 1. 클래스명 : ClassMgr 
 * 2. 설명 : 회원 관리 
 * 3. 작성일 : 2022-06-01 
 * 4. 작성자 : 박상준
 */
@Service
public class ClassMgr {
	@Autowired
	private ClassDao classDao;

	/**
	 * 클래스 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject selectClassList(ParameterMap parameterMap) throws Exception {
		return classDao.selectClassList(parameterMap);
	}
	
	/**
	 * 클래스 예약 회원 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject classMemberList0001(ParameterMap parameterMap) throws Exception {
		return classDao.classMemberList0001(parameterMap);
	}
	
	/**
	 * 클래스 문의 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject classRequestList0001(ParameterMap parameterMap) throws Exception {
		return classDao.classRequestList0001(parameterMap);
	}
	
	/**
	 * 문의 클래스 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject requestClass(ParameterMap parameterMap) throws Exception {
		return classDao.requestClass(parameterMap);
	}
	
	/**
	 * 문의 적용 완료 update
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public void applyRequest(ParameterMap parameterMap) throws Exception {
		List<BoardRequestDto> insertList = new ArrayList<BoardRequestDto>();
		List<BoardRequestDto> updateList = new ArrayList<BoardRequestDto>();
		List<BoardRequestDto> deleteList = new ArrayList<BoardRequestDto>();
		parameterMap.populates(BoardRequestDto.class, insertList, updateList, deleteList, "requestData");
		
		BoardRequestDto[] update = updateList.toArray(new BoardRequestDto[0]);
		classDao.updateMany("Class.applyRequest", update);
	}
	
	/**
	 * 요청 사항 수정
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public void classRequestUpdate(ParameterMap parameterMap) throws Exception {
		classDao.classRequestUpdate(parameterMap);
	}
	
	/**
	 * 요청 사항 클래스 삭제
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public void classRequestDelete(ParameterMap parameterMap) throws Exception {
		classDao.classRequestDelete(parameterMap);
	}
}
