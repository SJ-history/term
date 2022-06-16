package kr.co.ta9.pandora3.termProject.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ta9.pandora3.app.servlet.ParameterMap;
import kr.co.ta9.pandora3.termProject.dao.MemberDao;
import kr.co.ta9.pandora3.termProject.dto.MemberDto;

/**
 * 1. 클래스명 : MemberMgr 
 * 2. 설명 : 회원 관리 
 * 3. 작성일 : 2022-05-27 
 * 4. 작성자 : 박상준
 */
@Service
public class MemberMgr {
	@Autowired
	private MemberDao memberDao;

	/**
	 * 회원 리스트 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject selectMemberList(ParameterMap parameterMap) throws Exception {
		return memberDao.selectMemberList(parameterMap);
	}

	/**
	 * 회원 삭제
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public void deleteMember(ParameterMap parameterMap) throws Exception {
		List<MemberDto> insertList = new ArrayList<MemberDto>();
		List<MemberDto> updateList = new ArrayList<MemberDto>();
		List<MemberDto> deleteList = new ArrayList<MemberDto>();
		System.out.println("sadfasfuil"+parameterMap);

		parameterMap.populates(MemberDto.class, insertList, updateList, deleteList, "memberData");

		MemberDto[] delete = deleteList.toArray(new MemberDto[0]);
		
		memberDao.deleteMany("Member.deleteMember", delete);
	}
	
	/**
	 * 작가 등록 요청 목록 조회
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject memberApplyList(ParameterMap parameterMap) throws Exception {
		return memberDao.memberApplyList(parameterMap);
	}
	
	/**
	 * 회원 작가 등록 승인
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject ApplyAccess(ParameterMap parameterMap) throws Exception {
		return memberDao.ApplyAccess(parameterMap);
	}
	
	/**
	 * 회원 작가 등록 거절
	 * 
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject ApplyDelete(ParameterMap parameterMap) throws Exception {
		String subject = "회원님의 작가 등록이 거절되었습니다.";
		String content = "작가 등록 요청을 해주셔서 감사합니다."
				+ "<br/>안타깝게도 다음과 같은 사유로 작가 등록이 거절되었습니다."
				+ "<br/> -----------------------------------------------------------------------------------------------"
				+ "<br/>"
				+ "<br/>" + (String) parameterMap.get("deleteMessage")
				+ "<br/>"
				+ "<br/> -----------------------------------------------------------------------------------------------"
				+ "<br/> 많은 이용 부탁드립니다.";
		String host = "smtp.naver.com";
		final String user = "qkrtkdwns1323@naver.com";
		final String password = "tkdwns1323!@";
		String to = (String) parameterMap.get("email");
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.naver.com");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(user));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		// message.setText("메일 본문 내용 HTML 코딩");
		message.setText(content);
		message.setContent(content, "text/html;charset=euc-kr");
		Transport.send(message);
		
		return memberDao.ApplyDelete(parameterMap);
	}
	
}
