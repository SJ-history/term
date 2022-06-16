<%-- 
   1. 파일명 : memberList0001
   2. 파일설명 : 회원 정보 조회
   3. 작성일 : 2022-05-27
   4. 작성자 : 박상준
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 헤더파일 include -->
<%@ include file="/WEB-INF/views/pandora3/common/include/header.jsp"%>
<script type="text/javascript">
var member_grid;
var curr_row_idx = 0;
var def_mpp_btn_inf_html = "";
var curr_user_id = "";
var curr_mnu_seq = "";

$(document).ready(function() {
	// 그리드 초기화 : 시스템 회원 목록
	var member_grid_config = {
		gridid	    : 'member_grid',
		pagerid	    : '',
        columns	    : [
						{name : 'USER_ID', width:80, label : '아이디', align : 'center', sortable : false, editable : false},
						{name : 'USER_NAME', width:80, label : '이름', align : 'center', sortable : false, editable: false},
						{name : 'EMAIL1', width:120, label : '이메일', align : 'center', sortable : false, editable : false, formatter : setMail},
						{name : 'PHONE1', width:120, label : '연락처', align : 'center', sortable : false, editable : false, formatter : setPhone},
						{name : 'POST', width:160, label : '주소', align : 'center', sortable : false, editable : false, formatter : setAddr},
						{name : 'SELLER', label : '작가 여부', hidden : true},
						{name : 'JOIN_DATE', width:80, label : '가입날짜', align : 'center', sortable : false, editable : false, formatter : setDateFormat},
			         ],
		editmode	: false, 								// 그리드 editable 여부
		gridtitle	: '회원 목록', 							// 그리드명
		multiselect	: false, 								// checkbox 여부
		formid		: 'search', 							// 조회조건 form id
		height		: 400,	 								// 그리드 높이
		shrinkToFit	: true, 								// true인경우 column의 width 자동조정, false인경우 정해진 width대로 구현
		selecturl	: '/member/memberList0001', 		// 그리드 조회 URL
		events		: {
			onCellSelect : function(event, rowIdx) {
				if(curr_row_idx == rowIdx) return;
        		else curr_row_idx = rowIdx;
                var row = member_grid.getRowData(rowIdx);
                if(isNotEmpty(row.USER_ID)) {
                	// 현재 사용자ID, 상태, 계정활성화 여부 셋팅
                	curr_user_id = row.USER_ID;
                	// 프로그램 상세 입력폼 초기화
                	$("#mpp_btn_inf").html(def_mpp_btn_inf_html);
                	getAdmInf(row);
                }
            },
			gridComplete : function() {
				if($('#member_grid').jqGrid('getGridParam', 'records') > 0) {
					if(isEmpty(curr_mnu_seq)) {
						member_grid.setCellFocus(1, 2);
					}else {
						var idx = member_grid.getRowIndexByValue("USER_ID", curr_user_id);
						if(idx.length < 1) {
							member_grid.setCellFocus(1, 2);
						}else {
							member_grid.setCellFocus(idx[0], 2);
						}
					}
				}
			}
		}
	};
	member_grid = new UxGrid(member_grid_config);
	member_grid.init();
	fn_Search();
});
//추가: 내부 로직 사용자 정의
function fn_AddRow(){
	
	member_grid.add({APLY_START_DATE:$.timestampToString(new Date())});
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
//검색
function fn_Search(){
	member_grid.retreive();
}
//사용자 정보
function getAdmInf(row) {
	//input 초기화
	$("#sampleName").val(row.USER_NAME);
	$("#sampleId").val(row.USER_ID);
	
	var sellerState;
	if (row.SELLER == 'N') {
		sellerState = '미 등록';
	} 
	else if (row.SELLER == 'S') {
		sellerState = '승인 대기';
	} 
	else {
		sellerState = '승인 완료';
	}
	$("#sellerUseYn").val(sellerState);
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
			<table id="member_grid"></table>
			<!-- Grid // -->
			<div class="bgBorder"></div>
			
			<div class="mB60"></div>
			
			<div class="tab-wrap">
				<ul class="tab-list">
					<li class="active"><a href="#tab1" class="tab-link" onclick="return false;" >회원 정보</a></li>
				</ul>
		       	<div id="tab1" class="tabMore active">
		       		<div class="tableType">
			       		<table border="1" class="tblType1" style="border: 1px solid #bbb" id="table1">
			       			<tr>
			       				<th>이름</th>
			       				<td>
			       					<input type="text" name="sampleName" id="sampleName" readonly="readonly"/>
			       				</td>
			       			</tr>
			       			<tr>
			       				<th>아이디</th>
			       				<td>
			       					<input type="text" name="sampleId"  id="sampleId" readonly="readonly"/>
			       				</td>
			       			</tr>
			       			<tr>
			       				<th>작가여부</th>
			       				<td>
			       					<input type="text" name="sellerUseYn" id="sellerUseYn" readonly="readonly" />
			       				</td>
			       			</tr>
			       		</table>
		       		</div>
		       	</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/pandora3/common/include/footer.jsp"%>