<%-- 
   1. 파일명   : classList0001
   2. 파일설명 : 클래스 목록
   3. 작성일   : 2022-06-01
   4. 작성자   : TANINE
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 헤더파일 include -->
<%@ include file="/WEB-INF/views/pandora3/common/include/header.jsp"%>
<script type="text/javascript">
var class_list_grid;
var class_member_grid;
var target_row ="";
$(document).ready(function(){
    var up_config = { 
        gridid    : 'class_list_grid',	    	// 그리드 id
        pagerid   : 'class_list_grid_pager',	// 그리드 페이지 id
        columns	    : [
						{name : 'CONTENT_NO', label :'클래스 번호', hidden : true},
						{name : 'USER_NAME', width:60, label : '작가 이름', align : 'center', sortable : false, editable : false},
						{name : 'EMAIL1', width:80, label : '이메일', align : 'center', sortable : false, editable : false, formatter : setMail},
						{name : 'TITLE', width:200, label : '클래스 명', align : 'center', sortable : false, editable : false},
						{name : 'HEAD', width:140, label : '클래스 주제', align : 'center', sortable : false, editable : false},
						{name : 'CATEGORY', width:40, label : '카테고리', align : 'center', sortable : false, editable : true},
						{name : 'PS_NUM', width:35, label : '수강 최대 인원', align : 'center', sortable : false, editable : false},
						{name : 'START_DATE', width:50, label : '시작 날짜', align : 'center', sortable : false, editable : false},
						{name : 'END_DATE', width:50, label : '종료 날짜', align : 'center', sortable : false, editable : false},
			         ],
        editmode       : true,                                 	// 그리드 editable 여부
        gridtitle      : '클래스 목록',                           		// 그리드명
        multiselect    : false,                             		// checkbox 여부
        formid         : 'search',                                  // 조회조건 form id
        height         : 220,                                       // 그리드 높이
        selecturl      : '/class/classList0001',  		    		// 그리드 조회 URL
        events         : {
	                          ondblClickRow : function (event, rowid, irow, icol) {
	                        	  var row = class_list_grid.getRowData(rowid);
	                        	  target_row = row;
	                        	  if (isNotEmpty(row.CONTENT_NO)) {
									class_member_grid.retreive({data:{content_no:row.CONTENT_NO}});
	                              }
	                          },
                          }
    };
    var down_config = { 
            gridid    : 'class_member_grid',	    	// 그리드 id
            pagerid   : '',	// 그리드 페이지 id
            columns	    : [
							{name : 'RESERVE_NO', label :'예약 번호', hidden : true},
							{name : 'CONTENT_NO', label :'클래스 번호', hidden : true},
							{name : 'USER_NAME', width:80, label : '예약자 이름', align : 'center', sortable : false, editable : true},
							{name : 'PHONE1', width:80, label : '연락처', align : 'center', sortable : false, editable : false, formatter : setPhone},
							{name : 'EMAIL1', width:80, label : '이메일', align : 'center', sortable : false, editable : false, formatter : setMail},
							{name : 'RESERVE_NUM', width:80, label : '예약 인원', align : 'center', sortable : false, editable : false},
							{name : 'CLASS_DATE', width:80, label : '예약 날짜', align : 'center', sortable : false, editable : false},
				         ],
            editmode    : false, 									// 그리드 editable 여부
            gridtitle   : '클래스 예약 회원 목록',							// 그리드명
            multiselect : false,                               		// checkbox 여부
            height      : 220,                                		// 그리드 높이
            shrinkToFit : true,                               		// true인경우 column의 width 자동조정, false인경우 정해진 width대로 구현(가로스크롤바필요시 false)
            selecturl   : '/class/classMemberList0001',       		// 그리드 조회 URL
        };
    
    class_list_grid = new UxGrid(up_config);
    class_list_grid.init();
    
    class_member_grid = new UxGrid(down_config);
    class_member_grid.init();
    fn_Search();
});
//조회: 내부 로직 사용자 정의
function fn_Search(){
	target_row = "";
	class_list_grid.retreive(); //{success:function(){alert('retreive success');}}
	class_member_grid.clearGridData();
}
//jqgrid 이메일 값 리턴
function setMail(value, options, rowJson) {      
	return rowJson.EMAIL1 + "@" + rowJson.EMAIL2;
}
//jqgrid 연락처 값 리턴
function setPhone(value, options, rowJson) {      
	return rowJson.PHONE1 + " - " + rowJson.PHONE2 + " - " + rowJson.PHONE3;
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
						<th>클래스 명</th>
						<td><span class="txt_pw"><input type="text" name="title" id="title" class="text" /></span></td>
						<th>카테고리</th>
						 <td>
		                   <select name="category" id="category" class="select">
		                       <option value="">전체</option>
		                       <option value="디저트">디저트</option>
		                       <option value="패션/잡화">패션/잡화</option>
		                       <option value="리빙">리빙</option>
		                       <option value="기타">기타</option>
		                   </select>
		                </td>
					</tr>
				</table>
			</form>
			<br/>
			<!-- search // -->
			<!-- Master Grid -->
			<table id="class_list_grid"></table>
			<div id="class_list_grid_pager"></div>
			<!-- Master Grid // -->
			
			<!-- Detail Grid -->
			<table id="class_member_grid"></table>
			<!-- Detail Grid // -->
			<br />
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/views/pandora3/common/include/footer.jsp"%>