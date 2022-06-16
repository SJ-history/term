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
import kr.co.ta9.pandora3.termProject.manager.MemberMgr;

/**
 * 1. 클래스명 : MemberController 
 * 2. 설명 : 회원관리 컨트롤러 
 * 3. 작성일 : 2022-05-27 
 * 4. 작성자 : 박상준
 */
@Controller
public class MemberController extends CommonActionController {
	@Autowired
	private MemberMgr memberMgr;

	/**
	 * 회원목록 조회
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/memberList0001", method = RequestMethod.POST)
	public void memberList0001(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			json = memberMgr.selectMemberList(parameterMap);
		} // 시스템 사용자 목록 그리드 조회
		catch (Exception e) {
			result = Const.BOOLEAN_FAIL;
		} // Exception 일 경우
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}

	/**
	 * 회원 삭제
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/deleteMember", method = RequestMethod.POST)
	public void deleteMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			memberMgr.deleteMember(parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			result = Const.BOOLEAN_FAIL;
		}

		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
	
	/**
	 * 작가 등록 요청 목록 조회
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/member/memberApplyList", method = RequestMethod.POST)
	public void memberApplyList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			json = memberMgr.memberApplyList(parameterMap);
		} // 시스템 사용자 목록 그리드 조회
		catch (Exception e) {
			result = Const.BOOLEAN_FAIL;
		} // Exception 일 경우
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
	
	/**
	 * 회원 작가 등록 승인
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked"})
	@RequestMapping(value="/member/ApplyAccess", method = RequestMethod.POST)
	public void ApplyAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			// 시스템 사용자 목록 그리드 조회
			json = memberMgr.ApplyAccess(parameterMap);
		} 
		catch (Exception e) {
			// Exception 일 경우
			result = Const.BOOLEAN_FAIL;
		}
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}

	/**
	 * 회원 작가 등록 거절
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings({"unchecked"})
	@RequestMapping(value="/member/ApplyDelete", method = RequestMethod.POST)
	public void ApplyDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// parameterMap 선언
		ParameterMap parameterMap = getParameterGridMap(request, response);
		// result 선언
		String result = Const.BOOLEAN_SUCCESS;
		// json 선언
		JSONObject json = new JSONObject();
		try {
			// 시스템 사용자 목록 그리드 조회
			json = memberMgr.ApplyDelete(parameterMap);
		} 
		catch (Exception e) {
			// Exception 일 경우
			result = Const.BOOLEAN_FAIL;
		}
		// json에 결과 담기
		json.put("RESULT", result);
		ResponseUtil.write(response, json.toJSONString());
	}
}
