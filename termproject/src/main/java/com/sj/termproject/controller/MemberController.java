package com.sj.termproject.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.sj.termproject.dto.AgencyBoardDto;
import com.sj.termproject.dto.ClassReviewDto;
import com.sj.termproject.dto.MemberDto;
import com.sj.termproject.dto.ReservationDto;
import com.sj.termproject.service.AgencyService;
import com.sj.termproject.service.UserService;

@RestController
@RequestMapping("/api")
public class MemberController {
	@Autowired
	private UserService service;
	@Autowired
	private AgencyService service2;
	@Autowired
	private PasswordEncoder passwordEncoder;

	/** 
	 * Controller Test
	 */
	@GetMapping("/time")
	public String test() {
		System.out.println("현재 시각" + service.test());
		return service.test();
	}

	/** 
	 * 회원가입 페이지 
	 * 아이디 중복 검사 기능
	 * Join.vue
	 */
	@PostMapping("/idChecked")
	public String idChecked(String user_id) {
		if (service.idCheck(user_id) == null) {
			return "available";
		}
		return "overlap";
	}

	/** 
	 * 회원가입 페이지 
	 * 회원정보 Insert
	 * Join.vue
	 */
	@PostMapping("/join")
	public String Join(MemberDto dto) throws Exception {
		String encodedPassword = passwordEncoder.encode(dto.getUser_wd());
		dto.setUser_wd(encodedPassword);
		if (service.joinUser(dto) > 0) {
			return "success";
		}
		return "fail";
	}

	/** 
	 * 로그인 페이지 
	 * login.vue
	 */
	@PostMapping("/login")
	public String Login(MemberDto dto) {
		MemberDto user = service.idCheck(dto.getUser_id());
		if (user == null) {
			return "아이디를 확인해주세요";
		} else if (!passwordEncoder.matches(dto.getUser_wd(), user.getUser_wd())) {
			return "비밀번호가 일치하지 않습니다.";
		}
		return "success" + user.getUser_id() + user.getSeller();
	}

	/** 
	 * 회원정보 리턴
	 * MyPageInfo.vue
	 */
	@PostMapping("/myInfo")
	public MemberDto MyInfo(String user_id) {
		return service.idCheck(user_id);
	}

	/** 
	 * 마이 페이지
	 * 회원 정보 수정 기능
	 * MyPageInfo.vue
	 */
	@PostMapping("/infoEdit")
	public String InfoEdit(MemberDto dto) {
		MemberDto user = service.idCheck(dto.getUser_id());
		if (!passwordEncoder.matches(dto.getUser_wd(), user.getUser_wd())) {
			return "비밀번호가 일치하지 않습니다.";
		}
		service.editUser(dto);
		return "success";
	}

	/** 
	 * 아이디 찾기 페이지
	 * 아이디 찾기 기능
	 * FindId.vue
	 */
	@PostMapping("/idSearch")
	public List<MemberDto> idSearch(MemberDto dto) {
		if (dto.getEmail1().contains("@") && (dto.getPhone1().length() >= 10 && dto.getPhone1().length() <= 11)) {
			String[] email = dto.getEmail1().split("@");
			dto.setEmail1(email[0]);
			dto.setEmail2(email[1]);
			String phone = dto.getPhone1();
			dto.setPhone1(phone.substring(0, 3));
			dto.setPhone2(phone.substring(3, 7));
			dto.setPhone3(phone.substring(7, phone.length()));
			List<MemberDto> list = service.idSearch(dto);
			if (list.isEmpty()) {
				return null;
			}
			return list;
		}
		return null;
	}

	/** 
	 * 비밀번호 찾기 페이지
	 * 아이디 일치 여부 확인
	 * FindPass.vue
	 */
	@PostMapping("/compareId")
	public String compareId(MemberDto dto) {
		if (service.idCheck(dto.getUser_id()) == null) {
			return "fail";
		}
		return "success";
	}

	/** 
	 * 비밀번호 찾기 페이지
	 * 이메일 인증번호 기능
	 * FindPass.vue
	 */
	@PostMapping("/mailSend")
	public String mailSend(MemberDto dto) {
		if (dto.getEmail1().contains("@")) {
			String mail = dto.getEmail1();
			String[] email = dto.getEmail1().split("@");
			dto.setEmail1(email[0]);
			dto.setEmail2(email[1]);
			if (service.userSearch(dto) != null) {
				String subject = "이메일 인증 번호";
				String content = "";
				for (int i = 0; i < 6; i++) {
					content += (int) (Math.random() * 10);
				}
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
				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
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
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				return content;
			} else {
				return null;
			}
		}
		return null;
	}

	/** 
	 * 비밀번호 찾기 페이지
	 * 비밀번호 변경 기능
	 * FindPass.vue
	 */
	@PostMapping("/passEdit")
	public String passEdit(MemberDto dto) {
		String encodedPassword = passwordEncoder.encode(dto.getUser_wd());
		dto.setUser_wd(encodedPassword);
		if (service.passEdit(dto) > 0) {
			return "success";
		}
		return "fail";
	}

	/** 
	 * 회원 탈퇴 페이지
	 * 회원 탈퇴 기능
	 * Withdraw.vue
	 */
	@PostMapping("/withDraw")
	public String withDraw(MemberDto dto) {
		MemberDto user = service.idCheck(dto.getUser_id());
		if (passwordEncoder.matches(dto.getUser_wd(), user.getUser_wd())) {
			service.deleteUser(dto);
			return "success";
		}
		return "비밀번호가 일치하지 않습니다.";
	}

	/** 
	 * 작가 인증 기능(이메일, 카카오)
	 * ArtistChange.vue
	 * EmailAccess.vue
	 */
	@PostMapping("/sellerApply")
	public String sellerApply(MemberDto dto) {
		if (service.sellerApply(dto) > 0) {
			return "success";
		}
		return null;
	}

	/** 
	 * 메인 페이지
	 * 작가 승인 여부 확인
	 * Home.vue
	 */
	@PostMapping("/reload")
	public String reload(MemberDto dto) {
		MemberDto user = service.idCheck(dto.getUser_id());
		if (user != null) {
			return user.getSeller();
		}
		return null;
	}

	/** 
	 * 이메일 인증 번호 전송
	 * EmailAccess.vue
	 */
	@PostMapping("/emailAccess")
	public String emailAccess(MemberDto dto) {
		if (dto.getEmail1().contains("@")) {
			String mail = dto.getEmail1();
			String[] email = dto.getEmail1().split("@");
			dto.setEmail1(email[0]);
			dto.setEmail2(email[1]);
			if (service.userSearch(dto) != null) {
				String subject = "이메일 인증 번호";
				String content = "";
				for (int i = 0; i < 6; i++) {
					content += (int) (Math.random() * 10);
				}
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
				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(user, password);
					}
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
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				return content;
			} else {
				return null;
			}
		}
		return null;
	}

	/** 
	 * 마이 페이지 
	 * 나의 클래스 목록 리턴(참여 예정, 참여 완료)
	 * MyClass.vue
	 */
	@PostMapping("/myClassList")
	public List<AgencyBoardDto> myClassList(MemberDto dto, int act) {
		if (act > 0) {
			return service.list2(dto);
		} else {
			return service.list1(dto);
		}
	}

	/** 
	 * 마이 페이지 
	 * 나의 클래스 예약 취소 기능
	 * MyClass.vue
	 */
	@PostMapping("/cancel")
	public String cancel(ReservationDto dto) {
		if (service.reserveDelete(dto) > 0) {
			return "success";
		} else {
			return null;
		}
	}

	/** 
	 * 클래스 detail > 후기 작성 페이지
	 * 마이 페이지 > 후기 작성 기능
	 * Reviews.vue
	 */
	@PostMapping("/reviewWrite")
	public List<ClassReviewDto> reviewWrite(ClassReviewDto dto) {
		if (service.reviewWrite(dto) > 0) {
			return service2.reviewList(dto);
		} else {
			return null;
		}
	}
}