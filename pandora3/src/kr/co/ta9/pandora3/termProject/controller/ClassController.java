package kr.co.ta9.pandora3.termProject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.ta9.pandora3.app.servlet.ParameterMap;
import kr.co.ta9.pandora3.app.servlet.controller.CommonActionController;
import kr.co.ta9.pandora3.common.conf.Const;
import kr.co.ta9.pandora3.common.util.ResponseUtil;
import kr.co.ta9.pandora3.termProject.manager.ClassMgr;

/**
 * 1. 클래스명 : ClassController 
 * 2. 설명 : 회원관리 컨트롤러 
 * 3. 작성일 : 2022-06-01 
 * 4. 작성자 : 박상준
 */
@Controller
public class ClassController extends CommonActionController {
	@Autowired
	private ClassMgr classMgr;

	/**
	 * 클래스 목록 조회
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/class/classList0001", method = RequestMethod.POST)
	public void classList0001(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			json = classMgr.selectClassList(parameterMap);
		} // 시스템 사용자 목록 그리드 조회
		catch (Exception e) {
			result = Const.BOOLEAN_FAIL;
		} // Exception 일 경우
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
	
	/**
	 * 클래스 예약 회원 목록 조회
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/class/classMemberList0001", method = RequestMethod.POST)
	public void classMemberList0001(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			json = classMgr.classMemberList0001(parameterMap);
		} // 시스템 사용자 목록 그리드 조회
		catch (Exception e) {
			result = Const.BOOLEAN_FAIL;
		} // Exception 일 경우
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
	
	/**
	 * 클래스 문의 목록 조회
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/class/classRequestList0001", method = RequestMethod.POST)
	public void classRequestList0001(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			json = classMgr.classRequestList0001(parameterMap);
		} // 시스템 사용자 목록 그리드 조회
		catch (Exception e) {
			result = Const.BOOLEAN_FAIL;
		} // Exception 일 경우
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}

	/**
	 * 문의 클래스 조회
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/class/requestClass", method = RequestMethod.POST)
	public void requestClass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			json = classMgr.requestClass(parameterMap);
		} // 시스템 사용자 목록 그리드 조회
		catch (Exception e) {
			result = Const.BOOLEAN_FAIL;
		} // Exception 일 경우
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
	
	/**
	 * 문의 적용 완료 update
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/class/ApplyRequest", method = RequestMethod.POST)
	public void ApplyRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			classMgr.applyRequest(parameterMap);
		} // 시스템 사용자 목록 그리드 조회
		catch (Exception e) {
			result = Const.BOOLEAN_FAIL;
		} // Exception 일 경우
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
	
	/**
	 * 요청 사항 수정
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/class/classRequestUpdate", method = RequestMethod.POST)
	public void classRequestUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			classMgr.classRequestUpdate(parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			result = Const.BOOLEAN_FAIL;
		}

		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
	
	/**
	 * 요청 사항 클래스 삭제
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/class/classRequestDelete", method = RequestMethod.POST)
	public void classRequestDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			classMgr.classRequestDelete(parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			result = Const.BOOLEAN_FAIL;
		}
		
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
}
