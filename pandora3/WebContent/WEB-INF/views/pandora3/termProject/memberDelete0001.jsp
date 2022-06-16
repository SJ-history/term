<%-- 
   1. 파일명   : memberDelete0001
   2. 파일설명 : 회원 정보 삭제
   3. 작성일   : 2022-05-30
   4. 작성자   : TANINE
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 헤더파일 include -->
<%@ include file="/WEB-INF/views/pandora3/common/include/header.jsp" %>
<script type="text/javascript">
var member_grid;

$(document).ready(function(){
	var grid_config = {
		gridid		: 'member_grid',
		pagerid		: '',
		gridBtnYn : 'Y',				// 상단 그리드 버튼 여부 ( 그리드 1개 일때 필수 'Y', 상/하단 그리드 일 경우 상단 그리드만 필수'Y' )
		columns		: [
						{name : 'STATUS', label : '상태', hidden : true},
						{name : 'USER_ID', width:80, label : '아이디', align : 'center', sortable : false, editable : false},
						{name : 'USER_NAME', width:80, label : '이름', align : 'center', sortable : false, editable : false},
						{name : 'EMAIL1', width:120, label : '이메일', align : 'center', sortable : false, editable : false, formatter : setMail},
						{name : 'PHONE1', width:120, label : '연락처', align : 'center', sortable : false, editable : false, formatter : setPhone},
						{name : 'POST', width:160, label : '주소', align : 'center', sortable : false, editable : false, formatter : setAddr},
						{name : 'SELLER', label : '작가 여부', hidden : true},
						{name : 'JOIN_DATE', width:80, label : '가입날짜', align : 'center', sortable : false, editable : false, formatter : setDateFormat},
			         ],
		editmode	: true,										// 그리드 editable 여부
		gridtitle	: '회원 목록',									// 그리드명
		multiselect	: true,										// checkbox 여부
		formid		: 'search',									// 조회조건 form id
		height		: 600,										// 그리드 높이
        shrinkToFit	: true,										// true인경우 column의 width 자동조정, false인경우 정해진 width대로 구현(가로스크롤바필요시 false)
        selecturl	: '/member/memberList0001',				// 그리드 조회 URL
        saveurl		: '/member/deleteMember',				// 그리드 입력/수정/삭제 URL
		events		: {
			onCellSelect: function(event, rowIdx, colIdx, value){ // 셀클릭 시 콜백
            	// 추가된 row만 edit 가능하게 셋팅
            	var row = $('#member_grid').getRowData(rowIdx);
            	// editable 제어
            	if(row.JQGRIDCRUD == "C") {
	          		$('#member_grid').setCell(rowIdx, 'SYS_CD', $("#sys_info").val());
            	}
			}
		}
	};
    member_grid = new UxGrid(grid_config);
    member_grid.init();
    fn_Search();
});
//검색
function fn_Search(){
	member_grid.retreive();
}
//jqgrid 날짜 파싱
function setDateFormat(value, options, rowJson) {      
  	return value.substring(0,10);﻿﻿﻿﻿﻿
}
//jqgrid 이메일 값 리턴
function setMail(value, options, rowJson) {      
	return rowJson.EMAIL1 + "@" + rowJson.EMAIL2;
}
//jqgrid 연락처 값 리턴
function setPhone(value, options, rowJson) {      
	return rowJson.PHONE1 + " - " + rowJson.PHONE2 + " - " + rowJson.PHONE3;
}
//jqgrid 주소 값 리턴
function setAddr(value, options, rowJson) {      
	return "(" + rowJson.POST + ") " + rowJson.ADDR1 + " " + rowJson.ADDR2;
}
//삭제: 내부 로직 사용자 삭제
function fn_Delete() {
	//jqgrid grid 데이터 json 형태로 생성
	var memberData;
	if (getSaveData("member_grid") == '[]') {
		alert("삭제할 회원을 선택해주세요.");
		return false;
	} 
	else {
		if (!confirm("해당 회원정보를 삭제하시겠습니까?")) { return false; }
		else {
			member_grid.remove(); // {success:function(){alert('remove success');}}
			memberData = getSaveData("member_grid"); //grid_id
		}
	}

	//입력폼데이터 파라미터형태로 변경
	var data = "memberData=" + memberData + "&_pre_url="
			+ parent.preUrl.getPreUrl();
	ajax({
		url : '/member/deleteMember',
		data : data,
		async : false,
		success : function(data) {
			if (data.RESULT == "S") {
				document.getElementById("user_name").value = "";
				document.getElementById("id").value = "";
				alert("삭제되었습니다.");
				fn_Search();
				return;
			} else {
				alert("멤버 삭제 실패!")
				e.preventDefault();
				return;
			}
		}
	});
}
</script>

</head>
<body>
	<div class="frameWrap">
		<div class="subCon">
			<%@ include file="/WEB-INF/views/pandora3/common/include/btnList.jsp" %>
			<!-- search -->
			<form id="search" name="search" onsubmit="return false">
				<table class="tblType1 mB60">
					<colgroup>
						<col width="15%" />
						<col width="35%" />
						<col width="15%" />
						<col width="35%" />
					</colgroup>
					<tr>
						<th>사용자 이름</th>
						<td><span class="txt_pw"><input type="text" name="user_name" id="user_name" class="text" /></span></td>
						<th>사용자 아이디</th>
						<td><span class="txt_pw"><input type="text" name="id" id="id" class="text" /></span></td>
					</tr>
				</table>
			</form>
			<br/>
			<!-- search // -->
			<div class="bgBorder"></div><br/>
			<!-- Grid -->
			<table id="member_grid"></table>
			<!-- Grid // -->
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/pandora3/common/include/footer.jsp" %>