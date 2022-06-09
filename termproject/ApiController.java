package com.sj.termproject.controller;

import java.net.InetAddress;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sj.termproject.dto.UserDto;
import com.sj.termproject.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired private UserService service;
	@Autowired private PasswordEncoder passwordEncoder;

	@GetMapping("/memos") @ResponseBody public String test() { System.out.println("현재 시각" + service.test()); return service.test(); }
	@PostMapping("/idChecked") @ResponseBody public String idChecked(String USERID) {
		if (service.idCheck(USERID) == null) { return "available"; }
		return "overlap";
	}
	@PostMapping("/join") @ResponseBody public String Join(UserDto dto) throws Exception {
		String encodedPassword = passwordEncoder.encode(dto.getUSERWD());
		dto.setUSERWD(encodedPassword);
		dto.setIP(InetAddress.getLocalHost().getHostAddress());
		if (service.joinUser(dto) > 0) { return "success"; }
		return "fail";
	}
	@PostMapping("/login") @ResponseBody public String Login(UserDto dto) {
		UserDto user = service.idCheck(dto.getUSERID());
		if (user == null) { return "아이디를 확인해주세요"; }
		else if (!passwordEncoder.matches(dto.getUSERWD(), user.getUSERWD())) { return "비밀번호가 일치하지 않습니다."; }
		return "success" + user.getUSERNO();
	}
	@PostMapping("/myInfo") @ResponseBody public UserDto MyInfo(String USERNO) { return service.userInfo(USERNO); }
	@PostMapping("/infoEdit") @ResponseBody public String InfoEdit(UserDto dto) {
		UserDto user = service.idEdit(dto.getUSERNO());
		if (!passwordEncoder.matches(dto.getUSERWD(), user.getUSERWD())) { return "비밀번호가 일치하지 않습니다."; }
		service.editUser(dto);
		return "success";
	}
	@PostMapping("/idSearch") @ResponseBody public List<UserDto> idSearch(UserDto dto) {
		if (dto.getEMAIL1().contains("@") && (dto.getPHONE1().length() >= 10 && dto.getPHONE1().length() <= 11)) {
			String[] email = dto.getEMAIL1().split("@");
			dto.setEMAIL1(email[0]);
			dto.setEMAIL2(email[1]);
			String phone = dto.getPHONE1();
			dto.setPHONE1(phone.substring(0, 3));
			dto.setPHONE2(phone.substring(3, 7));
			dto.setPHONE3(phone.substring(7, phone.length()));
			List<UserDto> list = service.idSearch(dto);
			if (list.isEmpty()) { return null; }
			return list;
		}
		return null;
	}
	@PostMapping("/compareId") @ResponseBody public String compareId(UserDto dto) {
		if (service.idCheck(dto.getUSERID()) == null) { return "fail"; }
		return "success";
	}
	@PostMapping("/mailSend") @ResponseBody public String mailSend(UserDto dto) {
		if (dto.getEMAIL1().contains("@")) {
			String mail = dto.getEMAIL1();
			String[] email = dto.getEMAIL1().split("@");
			dto.setEMAIL1(email[0]);
			dto.setEMAIL2(email[1]);
			if (service.userSearch(dto) != null) {
				String subject = "이메일 인증 번호";
				String content = "";
				for (int i = 0; i < 6; i++) { content += (int) (Math.random() * 10); }
				String host = "smtp.naver.com";
				final String user = "qlla1452@naver.com";
				final String password = "tkdwns3146!@";
				String to = mail;
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "587");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.ssl.trust", "smtp.naver.com");
				props.put("mail.smtp.ssl.protocols", "TLSv1.2");
				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() { return new PasswordAuthentication(user, password); }
				});
				try {
					MimeMessage message = new MimeMessage(session);
					message.setFrom(new InternetAddress(user));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
					message.setSubject(subject);
					// message.setText("메일 본문 내용 HTML 코딩");
					message.setText(content);
					message.setContent(content, "text/html;charset=euc-kr");
					Transport.send(message);
				} 
				catch (MessagingException e) { e.printStackTrace(); }
				return content;
			} 
			else { return null; }
		}
		return null;
	}
	@PostMapping("/passEdit") @ResponseBody public String passEdit(UserDto dto) {
		String encodedPassword = passwordEncoder.encode(dto.getUSERWD());
		dto.setUSERWD(encodedPassword);
		if (service.passEdit(dto) > 0) { return "success"; }
		return "fail";
	}
	@PostMapping("/withDraw") @ResponseBody public String withDraw(UserDto dto) {
		UserDto user = service.idCheck(dto.getUSERID());
		if (passwordEncoder.matches(dto.getUSERWD(), user.getUSERWD())) { service.deleteUser(dto); return "success"; }
		return "비밀번호가 일치하지 않습니다.";
	}

}