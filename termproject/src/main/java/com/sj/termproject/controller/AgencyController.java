package com.sj.termproject.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sj.termproject.dto.AgencyBoardDto;
import com.sj.termproject.dto.BoardRequestDto;
import com.sj.termproject.dto.ClassReviewDto;
import com.sj.termproject.dto.FavoriteDto;
import com.sj.termproject.dto.ImageDto;
import com.sj.termproject.dto.MemberDto;
import com.sj.termproject.dto.ReservationDto;
import com.sj.termproject.service.AgencyService;

@RestController
@RequestMapping("/agency")
public class AgencyController {
	@Autowired
	private AgencyService service;
	
	/** 
	 * 클래스 등록 페이지 
	 * 작가 이름 리턴 
	 * WriteClass.vue
	 */
	@PostMapping("/info")
	public String agency(MemberDto dto) {
		MemberDto user = service.agencyInfo(dto.getUser_id());
		return user.getUser_name();
	}

	/** 
	 * 클래스 등록 페이지 
	 * 클래스 등록 
	 * WriteClass.vue
	 */
	@PostMapping("/writeClass")
	public String writeClass(@RequestParam MultipartFile img0, @RequestParam MultipartFile img1, AgencyBoardDto dto)
			throws IllegalStateException, IOException {
		if (!img0.isEmpty()) {
			ImageDto img = new ImageDto(UUID.randomUUID().toString(), img0.getOriginalFilename(),
					img0.getContentType());
			File newFileNameFile = new File(img.getUuid() + "_" + img.getFileName());
			img0.transferTo(newFileNameFile);
			dto.setImage0(String.valueOf(newFileNameFile));
		}
		if (!img1.isEmpty()) {
			ImageDto img = new ImageDto(UUID.randomUUID().toString(), img1.getOriginalFilename(),
					img1.getContentType());
			File newFileNameFile = new File(img.getUuid() + "_" + img.getFileName());
			img1.transferTo(newFileNameFile);
			dto.setImage1(String.valueOf(newFileNameFile));
		}
		if (service.writeClass(dto) > 0) {
			return "success";
		}
		return null;
	}

	/** 
	 * 메인 페이지 
	 * 최신 클래스 4개 리턴
	 * Home.vue
	 */
	@PostMapping("/newestList")
	public List<AgencyBoardDto> newestList() {
		return service.newestList();
	}

	/** 
	 * 진행중인 클래스 페이지
	 * 진행중인 전체 클래스 리턴
	 * ClassList.vue
	 */
	@PostMapping("/list")
	public List<AgencyBoardDto> list(AgencyBoardDto dto) {
		return service.list(dto);
	}

	/** 
	 * 마감된 클래스 페이지
	 * 마감된 전체 클래스 리턴
	 * EndClass.vue
	 */
	@PostMapping("/endList")
	public List<AgencyBoardDto> endList(AgencyBoardDto dto) {
		return service.endList(dto);
	}

	/** 
	 * 이미지 경로 리턴
	 */
	@GetMapping("display/{name}")
	public ResponseEntity<InputStreamResource> getTermsConditions(@PathVariable("name") String name)
			throws FileNotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "inline;filename=" + name);
		File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upload\\" + name);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		return ResponseEntity.ok().headers(headers).contentLength(file.length()).body(resource);
	}

	/** 
	 * 클래스 detail 페이지
	 * 클릭한 클래스 정보 리턴
	 * ClassDetail.vue
	 * ClassManageApply.vue 
	 */
	@PostMapping("/classDetail")
	public AgencyBoardDto classDetail(AgencyBoardDto dto) {
		return service.classDetail(dto);
	}

	/** 
	 * 클래스 detail 페이지
	 * 해당 클래스의 선택한 날짜에 대한 인원 수 리턴
	 * ClassDetail.vue
	 */
	@PostMapping("/reserveSearch")
	public int reserveSearch(ReservationDto dto) {
		return service.reserveNum(dto);
	}

	/** 
	 * 클래스 detail 페이지
	 * 클래스 예약 기능
	 * ClassDetail.vue
	 */
	@PostMapping("/reserve")
	public String reserve(ReservationDto dto) {
		if((service.reserveNum(dto) + dto.getReserve_num()) > dto.getPs_num()) {			return "해당 날짜의 인원이 마감되었습니다.\n다시 확인부탁드립니다.";
		}
		ReservationDto reservation = service.reserveChecked(dto);
		if(reservation == null) {
			if(service.reserve(dto) > 0) {
				return "success";
			}
			else {
				return "예약 실패";
			}
		}
		return "해당 날짜의 예약 내역이 존재합니다.";
	}

	/** 
	 * 클래스 달력 페이지
	 * 예약 불가능한 날짜 리턴
	 * Calander.vue
	 */
	@PostMapping("/calList")
	public List<String> calList(ReservationDto dto) {
		int ps_num = dto.getPs_num();
		List<ReservationDto> list = service.reserveSearch(dto);
		List<String> cal = new ArrayList<String>();
		if (!list.isEmpty()) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (ReservationDto dt : list) {
				map.put(dt.getClass_date(),
						map.get(dt.getClass_date()) != null ? map.get(dt.getClass_date()) + dt.getReserve_num()
								: dt.getReserve_num());
			}
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String date = iter.next();
				if (ps_num <= map.get(date)) {
					String[] yearMonthDate = date.split("-");
					yearMonthDate[1] = yearMonthDate[1].replace("0", "");
					cal.add(yearMonthDate[0] + "-" + yearMonthDate[1] + "-" + yearMonthDate[2]);
				}
			}
			return cal;
		}
		return null;
	}

	/** 
	 * 클래스 좋아요 기능
	 * 버튼 클릭 시 등록 / 삭제
	 * ClassList.vue
	 * EndClass.vue
	 * FavoriteList.vue
	 * Home.vue
	 * MyClass.vue
	 * SearchList.vue
	 */
	@PostMapping("/favorite")
	public List<Integer> favorite(FavoriteDto dto) {
		FavoriteDto favorite = service.favoriteList(dto);
		if (favorite == null) {
			service.favoriteInsert(dto);
		} else {
			if (favorite.getFavorite_condition().equals("Y")) { service.favoriteDelete(dto); }
			else { service.favoriteInsert2(dto); }
		}
		return service.favoriteNo(dto);
	}

	/** 
	 * 관심 목록 페이지
	 * 관심 목록 클래스 리턴
	 * FavoriteList.vue
	 */
	@PostMapping("/favoriteClass")
	public List<AgencyBoardDto> favoriteClass(FavoriteDto dto) {
		if (service.favoriteClass(dto).isEmpty()) {
			return null;
		} else {
			return service.favoriteClass(dto);
		}
	}

	/** 
	 * 관심 목록 리턴
	 * ClassList.vue
	 * EndClass.vue
	 * FavoriteList.vue
	 * Home.vue
	 * MyClass.vue
	 * SearchList.vue
	 */
	@PostMapping("/favoriteList")
	public List<Integer> favoriteList(FavoriteDto dto) {
		return service.favoriteNo(dto);
	}

	/** 
	 * 클래스 후기 페이지
	 * 해당 클래스의 후기 리턴
	 * Reviews.vue
	 */
	@PostMapping("/reviewList")
	public List<ClassReviewDto> reviewList(ClassReviewDto dto) {
		return service.reviewList(dto);
	}

	/** 
	 * 클래스 수정 요청 페이지
	 * 해당 클래스의 변경 사항 요청 기능
	 * ClassManageApply.vue
	 */
	@PostMapping("/classRequest")
	public int classRequest(BoardRequestDto dto) {
		if (service.classRequest(dto) > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	/** 
	 * 클래스 요청 목록 페이지
	 * 작가 클래스 요청 목록 리턴
	 * MyRequest.vue
	 */
	@PostMapping("/requestList")
	public List<BoardRequestDto> requestList(BoardRequestDto dto) {
		return service.requestList(dto);
	}

	/** 
	 * 클래스 등록 List 페이지
	 * 작가 클래스 등록 목록 리턴
	 * ClassManage.vue
	 */
	@PostMapping("/myClassList")
	public List<AgencyBoardDto> myClassList(MemberDto dto) {
		return service.myClassList(dto);
	}

	/** 
	 * 클래스 검색 페이지
	 * 클래스 검색 기능
	 * SearchList.vue
	 */
	@PostMapping("/searchList")
	public List<AgencyBoardDto> searchList(String query) {
		return service.searchList(query);
	}
	
	/** 
	 * 클래스 예약자 페이지
	 * 클래스 예약자 조회 기능
	 * ClassReservationList.vue
	 * ClassReservationListPaging.vue
	 */
	@PostMapping("/reserveUserList")
	public List<ReservationDto> reserveUserList(ReservationDto dto) {
		return service.reserveUserList(dto);
	}
}
