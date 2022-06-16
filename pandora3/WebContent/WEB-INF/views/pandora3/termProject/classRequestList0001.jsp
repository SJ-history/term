<%-- 
   1. 파일명   : classRequestList0001
   2. 파일설명 : 클래스 요청 관리
   3. 작성일   : 2022-06-02
   4. 작성자   : TANINE
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 헤더파일 include -->
<%@ include file="/WEB-INF/views/pandora3/common/include/header.jsp"%>
<script type="text/javascript">
var class_request_list_grid;
var code_detail_grid;
var target_row ="";
$(document).ready(function(){
    var up_config = { 
        gridid    : 'class_request_list_grid',	    	// 그리드 id
        pagerid   : '',	// 그리드 페이지 id
        columns	    : [
						{name : 'REQUEST_NO', label :'문의 번호', hidden : true},
						{name : 'CONTENT_NO', label :'클래스 번호', hidden : true},
						{name : 'USER_ID', label :'사용자 아이디', hidden : true},
						{name : 'USER_NAME', width:60, label : '작가 이름', align : 'center', sortable : false, editable : false},
						{name : 'EMAIL1', width:80, label : '이메일', align : 'center', sortable : false, editable : false, formatter : setMail},
						{name : 'REQUEST_TYPE', width:60, label : '문의 종류', align : 'center', sortable : false, editable : false},
						{name : 'REQUEST_TITLE', width:120, label : '요청 제목', align : 'center', sortable : false, editable : false},
						{name : 'REQUEST_CONTENT', width:200, label : '요청 내용', align : 'center', sortable : false, editable : false},
						{name : 'REQUEST_DATE', width:80, label : '요청 날짜', align : 'center', sortable : false, editable : false, formatter : setDateFormat},
			         ],
        editmode       : false,                                 	// 그리드 editable 여부
        gridtitle      : '요청 목록',                           		// 그리드명
        multiselect    : true,                             		// checkbox 여부
        formid         : 'search',                                  // 조회조건 form id
        height         : 190,                                       // 그리드 높이
        selecturl      : '/class/classRequestList0001',  		    // 그리드 조회 URL
        events         : {
							ondblClickRow : function (event, rowid, irow, icol) {
								var row = class_request_list_grid.getRowData(rowid);
								target_row = row;
								if (isNotEmpty(row.CONTENT_NO)) {
									getClassInf(row.CONTENT_NO);
								}
							},
						  }
    };
    
    class_request_list_grid = new UxGrid(up_config);
    class_request_list_grid.init();
    
    fn_Search();
    
	//적용 완료 버튼 클릭 시 
	$("#btn_code_master_save").click(function() {
	    fn_Request_Access();
	});
	//삭제 하기 버튼 클릭 시 
	$("#btn_code_master_delete").click(function(){
		if(document.getElementById("content_no").value == ''){
			alert("요청 사항을 선택해주세요.");
			return false;
		}
		if (!confirm("해당 클래스를 삭제하시겠습니까?")) { return false; }
		else { fn_Request_Delete(); }
	});
	//수정 하기 버튼 클릭 시 
	$("#btn_code_master_update").click(function(){
		if(document.getElementById("content_no").value == ''){
			alert("요청 사항을 선택해주세요.");
			return false;
		}
		if (!confirm("해당 클래스를 수정하시겠습니까?")) { return false; }
		else { fn_Request_Update(); }
	});
});
//jqgrid 날짜 파싱
function setDateFormat(value, options, rowJson) {      
  	return value.substring(0,10);﻿﻿﻿﻿﻿
}
//jqgrid 이메일 값 리턴
function setMail(value, options, rowJson) {      
	return rowJson.EMAIL1 + "@" + rowJson.EMAIL2;
}
//조회: 내부 로직 사용자 정의
function fn_Search(){
	target_row = "";
	class_request_list_grid.retreive();
	classInfoClear();
}
//날짜 min 설정
function dateLimit(){
	document.getElementById("end_date").setAttribute("min", document.getElementById("start_date").value);
	document.getElementById("end_time").setAttribute("min", document.getElementById("start_time").value);
}
//클래스 정보 초기화
function classInfoClear(){
	document.getElementById("content_no").value = '';
	document.getElementById("title").value = '';
	document.getElementById("category").value = '';
	document.getElementById("ps_num").value = '';
	document.getElementById("head").value = '';
	document.getElementById("body").value = '';
	document.getElementById("youtube").value = '';
	document.getElementById("cl_addr").value = '';
	document.getElementById("start_date").value = '';
	document.getElementById("end_date").value = '';
	document.getElementById("start_time").value = '';
	document.getElementById("end_time").value = '';
}
//formatter 버튼 이벤트(적용 완료)
function fn_Request_Access() {
	if(getSaveData("class_request_list_grid") == '[]'){
		alert("목록을 선택해주세요.");
		return false;
	}
	if (!confirm("적용 완료하셨습니까?")) { return false; }
	else {
		var requestData = getSaveData("class_request_list_grid");
		//입력폼데이터 파라미터형태로 변경
		var data = "requestData=" + requestData + "&_pre_url="
				+ parent.preUrl.getPreUrl();
	 	ajax({
			url : '/class/ApplyRequest',
			data : data,
			async : false,
			success : function(data) {
				if (data.RESULT == "S"){
					alert('적용 완료!');
					fn_Search();
				}
				else {
					if (isNotEmpty(data.MSG)){ alert("적용 실패!") }
				}
			},
		});
	}
}

//formatter 버튼 이벤트(삭제 하기)
function fn_Request_Delete() {
	var content_no = document.getElementById("content_no").value;
	
	ajax({
		url : '/class/classRequestDelete',
		data : { content_no : content_no, },
		async : false,
		success : function(data) {
			if (data.RESULT == "S"){
				alert('삭제가 완료되었습니다.');
				fn_Search();
			}
			else {
				if (isNotEmpty(data.MSG)){ alert("삭제 실패!") }
			}
		},
	});
}

//formatter 버튼 이벤트(수정 하기)
function fn_Request_Update() {
	if (document.getElementById("title").value == "" || document.getElementById("title").value > 60) {
        alert("제목을 입력해주세요.\n최대 60글자");
        return false;
    } else if (document.getElementById("category").value == "") {
        alert("카테고리를 설정해주세요.");
        return false;
   	} else if (document.getElementById("ps_num").value == "" || document.getElementById("ps_num").value < 1) {
        alert("수강 인원을 확인해주세요.");
        return false;
    } else if (document.getElementById("head").value == "" || document.getElementById("head").value > 100) {
        alert("주제를 등록해주세요.\n최대 100글자");
        return false;
    } else if (document.getElementById("body").value == "") {
        alert("본문 내용을 등록해주세요.");
        return false;
    } else if (document.getElementById("body").value.length > 200000) {
        alert("본문 내용을 등록해주세요.\n최대 200000글자");
        return false;
    } else if (document.getElementById("cl_addr").value == "") {
        alert("클래스 장소를 등록해주세요.");
        return false;
    } else if (document.getElementById("start_date").value == "") {
        alert("클래스 시작날짜를 등록해주세요.");
        return false;
    } else if (document.getElementById("end_date").value == "") {
        alert("클래스 마감날짜를 등록해주세요.");
        return false;
    } else if (document.getElementById("start_time").value == "") {
        alert("클래스 시작 시간을 등록해주세요.");
        return false;
    } else if (document.getElementById("end_time").value == "") {
        alert("클래스 종료 시간을 등록해주세요.");
        return false;
    } else if (document.getElementById("end_time").value < document.getElementById("start_time").value) {
    	alert("종료시간을 확인해주세요.");
        return false;
    }
	
	//입력폼데이터 파라미터형태로 변경
	var formdata  = $("form[name=update]").serialize();
	var data ="_pre_url="+parent.preUrl.getPreUrl() +"&" + formdata;
	ajax({
		url : '/class/classRequestUpdate',
		data : data,
		async : false,
		success : function(data){
			if(data.RESULT == "S"){
				alert("수정되었습니다.");
				fn_Search();
				return;
			}else{
				alert("수정 실패!!")
   				e.preventDefault();
   				return;
			}
		}
	});	
}

//클래스 정보
function getClassInf(CONTENT_NO) {
	ajax({
		url : '/class/requestClass',
		data : { content_no : CONTENT_NO },
		async : false,
		success : function(data) {
			if (data.RESULT == "S") {
				var classInfo = data.rows[0];
				document.getElementById("content_no").value = classInfo.CONTENT_NO;
				document.getElementById("title").value = classInfo.TITLE;
				document.getElementById("category").value = classInfo.CATEGORY;
				document.getElementById("ps_num").value = classInfo.PS_NUM;
				document.getElementById("head").value = classInfo.HEAD;
				document.getElementById("body").value = classInfo.BODY;
				document.getElementById("youtube").value = classInfo.YOUTUBE == ''? '-' : classInfo.YOUTUBE;
				document.getElementById("cl_addr").value = classInfo.CL_ADDR;
				document.getElementById("start_date").value = classInfo.START_DATE;
				document.getElementById("end_date").value = classInfo.END_DATE;
				document.getElementById("start_time").value = classInfo.START_TIME;
				document.getElementById("end_time").value = classInfo.END_TIME;

				document.getElementById("end_date").setAttribute("min", document.getElementById("start_date").value);
				document.getElementById("end_time").setAttribute("min", document.getElementById("start_time").value);
				return;
			} else {
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
						<th>사용자 명</th>
						<td><span class="txt_pw"><input type="text" name="user_name" id="user_name" class="text" /></span></td>
						<th>문의 종류</th>
						 <td>
		                   <select name="request_type" id="request_type" class="select">
		                       <option value="">전체</option>
		                       <option value="클래스 삭제">클래스 삭제</option>
		                       <option value="클래스 날짜 변경">클래스 날짜 변경</option>
		                       <option value="기타 요청">기타 요청</option>
		                   </select>
		                </td>
					</tr>
				</table>
			</form>
			<!-- search // -->
			<div class="grid_right_btn">
				<div class="grid_right_btn_in">
					<button id="btn_code_master_save" class="btn_common btn_default">적용 완료</button>
				</div>
			</div>
			<!-- Master Grid -->
			<table id="class_request_list_grid"></table>
			<!-- Master Grid // -->

			<div class="tab-wrap">
				<ul class="tab-list">
					<li class="active"><a href="#tab1" class="tab-link" onclick="return false;" >클래스 정보</a></li>
				</ul>
		       	<div id="tab1" class="tabMore active">
		       		<div class="tableType">
     					<form id="update" name="update" onsubmit="return false">
     						<input type="hidden" name="content_no" id="content_no" />
				       		<table border="1" class="tblType1" style="border: 1px solid #bbb" id="table1">
				       			<tr>
				       				<th>클래스 명</th>
				       				<td>
										<textarea id="title" name="title" class="textarea w100" style="line-height:6px; padding-top:10px"></textarea>
				       				</td>
				       			</tr>
				       			<tr>
				       				<th>카테고리</th>
				       				<td>
					       				<select name="category" id="category" class="select">
					                       <option value="">-</option>
					                       <option value="디저트">디저트</option>
					                       <option value="패션/잡화">패션/잡화</option>
					                       <option value="리빙">리빙</option>
					                       <option value="기타">기타</option>
					                   </select>
				       				</td>
				       			</tr>
				       			<tr>
				       				<th>수강 인원</th>
				       				<td>
					       				<input type="number" min="1" name="ps_num" id="ps_num"/>
				       				</td>
				       			</tr>
				       			<tr>
				       				<th>클래스 주제</th>
				       				<td>
										<textarea id="head" name="head" class="textarea w100" style="line-height:6px; padding-top:10px"></textarea>
				       				</td>
				       			</tr>
				       			<tr>
				       				<th>클래스 내용</th>
				       				<td>
										<textarea id="body" name="body" class="textarea w100" style="height:80px"></textarea>
				       				</td>
				       			</tr>
   				       			<tr>
				       				<th>참고 영상 링크</th>
				       				<td>
										<textarea id="youtube" name="youtube" class="textarea w100" style="line-height:6px; padding-top:10px"></textarea>
				       				</td>
				       			</tr>
				       			<tr>
				       				<th>클래스 장소</th>
				       				<td>
										<textarea id="cl_addr" name="cl_addr" class="textarea w100" style="line-height:6px; padding-top:10px"></textarea>
				       				</td>
				       			</tr>
				       			<tr>
				       				<th>클래스 일정</th>
				       				<td>
				       					시작 날짜 : <input type="date" id="start_date" name="start_date" onchange="dateLimit()" />&emsp;/&emsp; 
				       					마감 날짜 : <input type="date" id="end_date" name="end_date" />
				       				</td>
				       			</tr>
				       			<tr>
				       				<th>클래스 시간</th>
				       				<td>
				       					시작 시간 : <input type="time" id="start_time" name="start_time" onchange="dateLimit()"/>&emsp;/&emsp; 
				       					종료 시간 : <input type="time" id="end_time" name="end_time" />
				       				</td>
				       			</tr>
				       		</table>
	       					<div class="grid_right_btn">
								<div class="grid_right_btn_in">
									<button id="btn_code_master_delete" class="btn_common btn_default">삭제 하기</button>
									<button id="btn_code_master_update" class="btn_common btn_default">수정 하기</button>
								</div>
							</div>
			       		</form>
		       		</div>
		       	</div>
			</div>
			<br />
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/pandora3/common/include/footer.jsp"%>