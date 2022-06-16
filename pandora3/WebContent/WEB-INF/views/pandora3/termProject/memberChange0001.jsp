<%-- 
   1. 파일명 : memberChange0001
   2. 파일설명 : 사용자 작가 전환
   3. 작성일 : 2022-05-31
   4. 작성자 : 박상준
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 헤더파일 include -->
<%@ include file="/WEB-INF/views/pandora3/common/include/header.jsp"%>

<script type="text/javascript">
var agency_grid;

$(document).ready(function(){
	//페이징 없이 전체 데이터를 표출을 원할 경우 pageId에 값을 ''  
	var grid_config = { 
		gridid	    : 'agency_grid',
		pagerid	    : '',
 		columns		: [
						{name : 'USER_NAME', width:120, label : '이름', align : 'center', sortable : false, editable : false},
						{name : 'USER_ID', width:120, label : '아이디', align : 'center', sortable : false, editable : false},
						{name : 'EMAIL1', width:160, label : '이메일', align : 'center', sortable : false, editable : false, formatter : setMail},
						{name : 'JOIN_DATE', width:80, label : '가입날짜', align : 'center', sortable : false, editable : false, formatter : setDateFormat},
						{name : 'APPLY', width:80, label : '승인 여부', align : 'center', formatter : setApplyBtn}
			         ],
		editmode	: false, 								// 그리드 editable 여부
		gridtitle	: '작가 등록 요청 목록', 						// 그리드명
		multiselect	: false, 								// checkbox 여부
		formid		: 'search', 							// 조회조건 form id
		height		: '600', 								// 그리드 높이
		shrinkToFit	: true, 								// true인경우 column의 width 자동조정, false인경우 정해진 width대로 구현(가로스크롤바필요시 false)
		selecturl	: '/member/memberApplyList', 			// 그리드 조회 URL
	};
 
	agency_grid = new UxGrid(grid_config);
	agency_grid.init();
	fn_Search();

});
//검색
function fn_Search(){
	agency_grid.retreive();
}
//jqgrid 날짜 파싱 이벤트
function setDateFormat(value, options, rowJson) {      
  	return value.substring(0,10);﻿﻿﻿﻿﻿
}
//jqgrid 이메일 값 리턴
function setMail(value, options, rowJson) {      
	return rowJson.EMAIL1 + "@" + rowJson.EMAIL2;
}
//jqgrid 내 버튼 생성 이벤트
function setApplyBtn(value, options, rowJson) {      
	return "<button onclick='fn_Apply_Access(" + options.rowId + ")' class='btn_common btn_default' style='float:center;'>승인</button>"
	     + "<button onclick='fn_Apply_Delete(" + options.rowId + ")' class='btn_common btn_default' style='float:center;'>거절</button>";
}

// formatter 버튼 이벤트(승인)
function fn_Apply_Access(rowId) {
	var id = $("#agency_grid").jqGrid('getCell', rowId, 'USER_ID');
 	ajax({
		url : '/member/ApplyAccess',
		data : {
			id : id,
		},
		async : false,
		success : function(data) {
			if (data.RESULT == "S"){
				alert('작가 승인이 완료되었습니다.');
				fn_Search();
			}
			else {
				if (isNotEmpty(data.MSG)){ alert("승인 실패!") }
			}
		},
	});
}

//formatter 버튼 이벤트(거절)
function fn_Apply_Delete(rowId) {
	var id = $("#agency_grid").jqGrid('getCell', rowId, 'USER_ID');
	var email = $("#agency_grid").jqGrid('getCell', rowId, 'EMAIL1');
	var deleteMessage = prompt("거절 사유를 적어주세요.");
	if(deleteMessage == null || deleteMessage == ''){
		alert('거절 사유를 등록해주세요.');
		return false;
	}
	ajax({
		url : '/member/ApplyDelete',
		data : {
			id : id,
			email : email,
			deleteMessage : deleteMessage,
		},
		async : false,
		success : function(data) {
			if (data.RESULT == "S") {
				alert('해당 요청에 대한 승인이 거절되었습니다.');
				agency_grid.retreive();
			} else {
				if (isNotEmpty(data.MSG)) {
					alert("해당 작업에 실패하였습니다.")
				}
			}
		},
	});
}
</script>
</head>
<body>
	<div class="frameWrap">
		<div class="subCon">
			<%@ include file="/WEB-INF/views/pandora3/common/include/btnList.jsp"%>
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
			<table id="agency_grid"></table>
			<!-- Grid // -->
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/pandora3/common/include/footer.jsp"%>
