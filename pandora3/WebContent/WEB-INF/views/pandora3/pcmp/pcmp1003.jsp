<%-- 
   1. 파일명 : pcmp1003
   2. 파일설명 : 부서관리 (목록)
   3. 작성일 : 2020-05-19
   4. 작성자 : TANINE
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 헤더파일 include -->
<%@ include file="/WEB-INF/views/pandora3/common/include/header.jsp" %>
<script type="text/javascript">

var admin_menu_grid;
// zTree Setting
var setting = {
    view: {
        selectedMulti: false
    },
    edit: {
        enable: true,
        showRemoveBtn: true,
        showRenameBtn: true
    },
    data: {
        keep: {
            parent:true,
            leaf:true
        },
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: 0
        }
    },
    callback : {
        beforeDrag  : beforeDrag,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        beforeClick : beforeClick,
        onRemove    : onRemove,
        onRename    : onRename,
        onClick     : onClick
    }
};
// 전역변수
var zNodes;                 // 트리노드
var newCount = 1;           // 임시Id SEQ
var editMenuFlag = false;   // 메뉴편집 진행 플래그
var cutMenuFlag = false;    // 메뉴 자르기 진행 플래그
var copyMenuFlag = false;   // 메뉴 복사 진행 플래그
var editMenuInfo;           // 편집 진행중인 메뉴 정보
var tmpMappingInfoList      // 템플릿 매핑 정보 목록
var isSavedMenu = false;    // 저장된 메뉴 여부
var selected_level;         // 클릭된 계층
var selected_tmp_type;      // 클릭된 템플릿 타입
var curSrcNode; 
var curType;
var copyNodesObj;

// 드래그 방지
function beforeDrag(treeId, treeNodes) {
    return false;
}

// 메뉴 클릭 이벤트 발화 전 이벤트
function beforeClick(treeId, treeNode) {
    // 편집 플래그가 true 인 경우 | 자르기 | 복사 플래그가 true 인 경우
    if(editMenuFlag || cutMenuFlag || copyMenuFlag) {
        // 현재 선택 중인 메뉴 확인
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes();
        
        // 현재 선택 중인 메뉴와 새로 선택하려는 메뉴가 다르면
        
        var action;
        if(editMenuFlag) action = "편집";
        else if(cutMenuFlag) action = "이동";
        else if(copyMenuFlag) action = "복사";
        
        if(nodes[0] != treeNode) alert("현재 " +action+ "중인 메뉴가 있습니다.\n내용을 저장 하신 후 다시 진행해주세요.");
        return false;
    }   
}

//click zTree Node Event
function onClick(event, treeId) {
    // form 활성화
    
    $("#btn_get_tmp_url").on("click",pgBtnClick);
    formControl(false);
    
    // 현재 선택 메뉴 확인
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    // 선택 메뉴 데이터를 상세정보 Form에 설정
    $("#tmp_type").val(treeNode.tmp_type).prop("selected", true).trigger('change');;
    $("#mnu_seq").val(treeNode.id);
    $("#up_mnu_seq").val(treeNode.pId); 
    $("#mnu_nm").val(unescapeHtml(treeNode.name));
    $("#srt_seq").val(treeNode.srt_seq);
    $("#pgm_nm").val(treeNode.pgm_nm);
    $("#pgm_id").val(treeNode.pgm_id);
    $("input[name='us_yn']:radio[value='"+treeNode.us_yn+"']").prop('checked',true);
    $("input[name='mnu_yn']:radio[value='"+treeNode.mnu_yn+"']").prop('checked',true);
    $("input[name='prn_inf_scrn_yn']:radio[value='"+treeNode.prn_inf_scrn_yn+"']").prop('checked',true);
    $("input[name='vdi_scrn_yn']:radio[value='"+treeNode.vdi_scrn_yn+"']").prop('checked',true);
    // 선택 메뉴 데이터 조회용 키값 설정
    admin_menu_grid.retreive({data:{up_mnu_seq:treeNode.id, sys_cd:$('#sys_info').val()}});
    
    // 선택 메뉴의 설정정보(DB저장여부)를 전역변수에 저장(복사의 경우 예외)
    if("Y" == treeNode.isSaved && "copy" != curType) {
        isSavedMenu = true;
        selected_level = treeNode.level;
    } else {
        isSavedMenu = false;
    }
    zTree.selectNode(treeNode);
}


function unescapeHtml(str) {

    if (str == null) {
     return "";
    }

    return str.replace(/&amp;/g, '&')
              .replace(/&lt;/g, '<')
              .replace(/&gt;/g, '>')
              .replace(/&quot;/g, '"')
              .replace(/&#039;/g, "'")
              .replace(/&#39;/g, "'")
              .replace(/&#40;/g, "(")
              .replace(/&#41;/g, ")");

}

// input,select form control
function formControl(flag) {
    $('input').attr("disabled", flag);
    $('select').attr("disabled", flag);
}

//프로그램검색 버튼 클릭
function pgBtnClick(){
	 bpopup({
         url:"/psys/forward.psys1002p01.adm"
       , params    : {callback : "getPgmInfo", target_id : _menu_id}
       , title     : "프로그램검색팝업"                          
       , type      : "l"
       , height    : "500px"
       , id        : "psys1002p1"
   });
}

// input,select form clear
function formClear() {
    $('input[type=text],input[type=hidden]').val('');
    //$('option').attr('selected', false);
    $('option').not('.passOption').attr('selected', false);
}
//  메뉴 리스트 취득 -- 조회가 그리드를 통하지 않을 경우 _pre_url을 입력하므로써 이전페이지 기능 구현 why? -> 아이프레임 단위의 화면이기때문
function getAdminMenuTree(initFlag, mnu_seq) {
    if(!initFlag) return false;
    $.ajax({
        url: _context + '/psys/getPsys1002List01',
        type: 'POST',
        data: {sys_cd : $('#sys_info').val(), _pre_url:"/psys/forward.psys1002.adm"},
        success: function(data) {
            data = JSON.parse(data);
            
            if (data.AUTH_CHECK_RESULT == _boolean_fail) {
                // 메시지 출력
                alert(data.AUTH_CHECK_MESSAGE);
                
                // 로그인 페이지로 가야 한다면
                if (data.AUTH_CHECK_ACTION == _action_login || data.AUTH_CHECK_ACTION == _action_none) {
                    popup({url:"/login/forward.login.adm"
                        , winname:"_top"
                        , title:"로그인"
                        , scrollbars:false
                        , autoresize:false
                        , params : {_pre_url : parent.preUrl.getPreUrl(), lgn_id : parent.preUrl.getLgnId() , usr_nm : parent.preUrl.getUsrNm(), 
                            usr_eml_addr : parent.preUrl.getUsrEmlAdr(), usr_stat_cd : parent.preUrl.getUsrSsCd(), actv_yn : parent.preUrl.getActvYn()}
                    });
                }
            } else {
                zNodes = data.mapList;
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                if(isNotEmpty(mnu_seq)) {
                    var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                    var nodes = treeObj.getNodesByParam("id", parseInt(mnu_seq, 10), null)
                    if(nodes.length > 0) $("#" + nodes[0].tId + "_a").trigger('click');
                }
            }
            
            
            
        }
    });
}

$(document).ready(function(){
    
    // 적용 템플릿 영역 초기화
    selectBoxReset("#mnu_seq");
    
    // 폼 입력 제한(메뉴순위 : 숫자만 입력되도록)
    $('#disp_num').keydown(function(e){
        var flag = onlyNumber(e);
        return flag;
    }).keyup(function(){
        $(this).val($(this).val().replace(/[^0-9]/g,""));
    });
    
    // 프로그램등록
//     $("#btn_get_tmp_url").click(function() {
//         //var url = $("#mnu_seq option:selected").attr("url");;
//         //$("#url").val(url);  
//         if(!($("#mnu_nm").val("").attr("disabled") == "disabled")){
// 	        bpopup({
// 	              url:"/psys/forward.psys1002p01.adm"
// 	            , params    : {callback : "getPgmInfo", target_id : _menu_id}
// 	            , title     : "프로그램검색팝업"                          
// 	            , type      : "l"
// 	            , height    : "500px"
// 	            , id        : "psys1002p1"
// 	        });
//    	 	}
//     });
    
    
    // 좌측 네비게이션 버튼 이벤트 설정
    $("#addMenu").bind("click", add);
    $("#copy").bind("click", copy);
    $("#cut").bind("click", cut);
    $("#paste").bind("click", paste);
    $("#removeMenu").bind("click", remove);
    $("#resetEdit").bind("click", resetEdit);
    
    $("#expandBtn").bind("click",{type:"expandAll"}, expandNode);
    $("#collapseBtn").bind("click",{type:"collapse"}, expandNode);

    formControl(true);
                                                       
    // form submit process                                            
    $("#menuInfoForm").submit(function(e) {
    	
    	if(isEmpty($("#pgm_nm").val())) {
    		$("#pgm_id").val("");
    	}
    	
        var formData = new FormData($(this)[0]);                      
        formData.append("frnt_yn", "N"); 
        formData.append("stf_only_yn", "N");
        formData.append("lst_mnu_yn", "N");
        formData.append("mnu_tp_cd", "10");
        // 복사 -> 붙여넣기                                                 
        if(copyMenuFlag) {                                            
            //복사할 데이터를 Json 형식으로                                      
            var jsonTop = JSON.stringify(copyNodesObj.topDataInfo);   
            var jsonMid = JSON.stringify(copyNodesObj.midDataInfo);
            var jsonBtm = JSON.stringify(copyNodesObj.btmDataInfo);
            formData.append("jsonTop",jsonTop);
            if(isNotEmpty(jsonMid)) formData.append("jsonMid",jsonMid);
            if(isNotEmpty(jsonBtm)) formData.append("jsonBtm",jsonBtm);
            formData.append("copyFlag", "COPY");
        }
        $.ajax({
            url: _context + '/psys/savePsys100201',
            type: 'POST',
            data: formData,
            //mimeType:"multipart/form-data",
            contentType: false,
            cache: false,
            processData:false,
            success: function(data) {
                data = JSON.parse(data);
                if(data.RESULT == "S") {
                    $("#mnu_seq").val(data.mnu_seq);
                    alert('저장되었습니다');
                    editMenuFlag = false;
                    cutMenuFlag = false;
                    copyMenuFlag = false;
                    editMenuInfo = null;
                    admin_menu_grid.retreive({data:{up_mnu_seq:data.mnu_seq}});
                    getAdminMenuTree(true, data.mnu_seq);
                } else {
                    alert("일시적 오류입니다\n잠시후 다시 시도하세요.")
                }
            },
        });
        e.preventDefault(); //Prevent Default action. 
        
    });
    
    // Grid 영역 설정(하위메뉴)
    var admin_menu_config = { 
        gridid    : 'admin_menu_grid',          // 그리드 id
        pagerid   : 'admin_menu_grid_pager',    // 그리드 페이지 id
//         gridBtnYn : 'Y',                     // 상단 그리드 버튼 여부 ( 그리드 1개 일때 필수 'Y', 상/하단 그리드 일 경우 상단 그리드만 필수'Y' )
        // column info
        columns   :[{name:'MNU_SEQ'     , width:100, label:'메뉴ID', hidden:true},
                    {name:'UP_MNU_SEQ'  , width:100, label:'상위메뉴ID', hidden:true},
                    {name:'URL',        width:100, label:'URL',align:'center', hidden:true},
                    {name:'MNU_NM'  , width:100, label:'메뉴명', align:'center', sortable:false,
                    cellattr: function(rowId, tv, rowObject, cm, rdata) {if(isNotEmpty(rowObject.TMP_NM)){return 'style="cursor: pointer; text-decoration: underline;"'}}},
                    {name:'PGM_NM'  , width:50, label:'화면명',align:'center', sortable:false},
                    {name:'PGM_ID'  , width:50, label:'화면ID',align:'center', sortable:false,hidden:true},
                    {name:'SRT_SEQ' , width:50, label:'메뉴순서',align:'center', sortable:false},
                    {name:'US_YN'   , width:50, label:'사용여부', align:'center', sortable:false, formatter:'select', editoptions:{value:'Y:사용;N:미사용'}},
                    ],
        editmode    : true,                             // 그리드 editable 여부
        gridtitle   : '하위메뉴 목록',                        // 그리드명
        shrinkToFit : true,                             // 컬럼 width 자동조정
        autowidth   : true,
        height      : 280,
        cellEdit    : false,
        selecturl   : '/psys/getPsys1002List03',    // 그리드 조회 URL

    };
        
    admin_menu_grid = new UxGrid(admin_menu_config);
    admin_menu_grid.init();
    admin_menu_grid.setGridWidth($('#menuInfo').width());
    
    // 하위메뉴저장 버튼 클릭시
    $("#btn_down_menu_add").click(function() {
        admin_menu_grid.save({success:function(){getAdminMenuTree(true, $("#mnu_seq option:selected").val());}});
    });
    
    // 하위메뉴삭제 버튼 클릭시
    $("#btn_down_menu_del").click(function() {
        // JQ GRID CHECK BOX 선택대상 확인
        var params = new Array();   // 전송 파라미터 설정(MNU_SEQ)
        var cnt = 0;
        var idArry = $("#admin_menu_grid").jqGrid('getDataIDs'); //grid의 id 값을 배열로 가져옴
        for (var i = 0; i < idArry.length; i++) {
            if($("input:checkbox[id='jqg_admin_menu_grid_"+idArry[i]+"']").is(":checked")){
                cnt++;
                var rowdata = $("#admin_menu_grid").getRowData(idArry[i]);
                params.push(rowdata.MNU_SEQ);
            }
        }
        if(cnt == 0) {
            alert("삭제할 행을 선택해주세요.");
            return;
        }
        if(confirm("선택한 하위메뉴를 삭제하시겠습니까?")) {
            var up_mnu_seq = $("#mnu_seq option:selected").val();
            dbSavedMenuRemoveAjaxCall(params, up_mnu_seq)
        }
    });
    
    $("#sys_info").attr("disabled", false);
    
    $('#sys_info').change(function(){
        fn_Search();
        editMenuFlag = false;
        cutMenuFlag = false;
        copyMenuFlag = false;
        editMenuInfo = null;
        
        $("#mnu_seq").val("").attr("readonly");
        $("#mnu_nm").val("").attr("disabled", true);
        $("#srt_seq").val("").attr("disabled", true);
        $("#pgm_nm").val("").attr("disabled", true);
        $("input[name='us_yn']").prop('checked', false).attr("disabled", true);
        $("input[name='mnu_yn']").prop('checked', false).attr("disabled", true);
        $("input[name='prn_inf_scrn_yn']").prop('checked', false).attr("disabled", true);
        $("input[name='vdi_scrn_yn']").prop('checked', false).attr("disabled", true);
        $("#btn_get_tmp_url").off();
    });
    
    //사이트 리스트 취득
    getSystemList();
    
});

// 하위 메뉴 정보 설정
function getDownMenuInfo(pin) { 
    var pos = admin_menu_grid.getCellLastPosition();
    admin_menu_grid.setCell(pos.rowid, 'MNU_NM', pin.MNU_NM, '', {}, true);
    admin_menu_grid.setCell(pos.rowid, 'TMP_TYPE', pin.TMP_TYPE, '', {}, true);
    admin_menu_grid.setCell(pos.rowid, 'TMP_TYPE_NM', pin.TMP_TYPE_NM, '', {}, true);
    admin_menu_grid.setCell(pos.rowid, 'DISP_NUM', pin.DISP_NUM, '', {}, true);
    admin_menu_grid.setCell(pos.rowid, 'DSPLY_YN', pin.DSPLY_YN, '', {}, true);
    admin_menu_grid.setCell(pos.rowid, 'MENU_YN', pin.MENU_YN, '', {}, true);
    admin_menu_grid.setCell(pos.rowid, 'URL', pin.URL, '', {}, true);
    // 편집용 CHECK BOX 클릭
    $("#admin_menu_grid").setCell(pos.rowid, 'JQGRIDCRUD', 'U');
    $("input:checkbox[id='jqg_admin_menu_grid_"+pos.rowid+"']").prop("checked", true);
}

//grid resizing
$(window).resize(function() {
    admin_menu_grid.setGridWidth($('#menuInfo').width());
});

//입력값 필수 체크
function requiredCheck(arrId) {
    var flag = true;
    for(i=0; i<arrId.length; i++) {
        //console.log(arrId[i]);
        if(isEmpty($("#"+arrId[i]).val())) {
            var tgtName = $("label[for='"+arrId[i]+"']").text();
            alert("["+tgtName+"] - "+"필수항목을 입력해주세요.");
            $("#"+arrId[i]).focus();
            flag = false;
            return false;
        }
    }
    return flag;
}

// 전송 폼 유효성 체크
function validationForm(obj) {
    var arrId = ["mnu_seq","mnu_nm","srt_seq"];
    
    // 입력값 필수 체크
    return requiredCheck(arrId);
}

function submitFormCheck() {
    // 신규 추가한 메뉴가 편집 중인 경우 [Tree에 "메뉴추가"]
    if(editMenuInfo) {
        var arrId = ["mnu_nm","srt_seq"] ;
        return requiredCheck(arrId);
    }
    // 기존 메뉴에 대한 편집 중인 경우 (DB에 저장된 메뉴에 대한 편집)
    else if(isSavedMenu) {
        return validationForm();
    } 
    
    return true;
}

// 템플릿유형 변경
function changeTmpType(obj, param) {
    // 템플릿 유형 변동 시 : 적용 템플릿 영역 초기화
    selectBoxReset("#mnu_seq");
    // 템플릿 유형을 선택 시 
    if(!isEmpty(obj.value) || !isEmpty(param)) {
        // 좌측 트리 정보 갱신
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes();
        
        // 적용 템플릿 영역 Option 값이 없을 때 : 영역 Disabled 
        if($('#mnu_seq > option').length > 1) $("#mnu_seq").attr("disabled", false);
    } 
}

// 메뉴 추가 EVENT
function add(e) {
    formControl(false);
    //메뉴편집 진행 플래그 확인
    if(editMenuFlag) {
        alert("현재 편집 중인 신규 메뉴가 있습니다.\n편집내용을 저장 하신 후 다시 진행해주세요.");
        return false;
    }
    // 현재 선택된 메뉴의 정보를 취득
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    
    // 신규 생성될 메뉴의 설정값
    var menuName = "새 메뉴";      // 메뉴명 기본값
    var isParent = true;        // 하위 메뉴 허용값 (기본 허용)
    var parentId = null;        // 상위 템플릿 ID
    var tmp_type = "1";         // 템플릿 유형 기본값 : (1:[메인])
    var mnu_dpth=1;
    
    // 메뉴 추가 허용값 : 기본 허용
    var addMenuOkFlag = true;
    
    // 현재 등록된 메뉴가 있는지 체크
    if(zNodes.length > 0) {
    }
    // 선택된 메뉴가 존재하면
    if (treeNode) {
        // 선택된 메뉴의 정보 확인 (하위허용여부)
        isParent = treeNode.isParent;
        
        // 하위허용일 경우
        if(isParent) {
            // 생성될 메뉴의 상위 메뉴 취득
            parentId = treeNode.id;
            /*
            // 메인 메뉴 선택 시
            if (treeNode.level == 0) {
                mnu_dpth = 1;
            } 
            
            if (treeNode.mnu_dpth == 1) {
                mnu_dpth = 2;
            } 
            // 1Depth 메뉴 선택 시
            else if (treeNode.mnu_dpth == 2){
                mnu_dpth = 3;
            }
            // 2Depth 메뉴 선택 시 
            else if (treeNode.level == 3){
                // 신규 생성될 3Depth 메뉴는 하위 생성 불가
            
                isParent = false;
                // 생생될 신규 메뉴의 템플릿 유형 기본값 설정 : 3층 페이지(7)
                //tmp_type="7";
            } 
            */
            mnu_dpth = treeNode.level +1;
            
            if (treeNode.level == 3){
                // 신규 생성될 3Depth 메뉴는 하위 생성 불가
            
                isParent = false;
                // 생생될 신규 메뉴의 템플릿 유형 기본값 설정 : 3층 페이지(7)
                //tmp_type="7";
            } 
        } 
        // 하위불가일 경우
        else {
            addMenuOkFlag = false;
        }
    }
    // 선택된 메뉴가 없는 경우(최상층 메뉴 추가 시 : 메인메뉴의 유무를 판단)
    else if(zTree.getNodes()[0]) {
        alert("메인 메뉴는 한개만 설정이 가능합니다. \n등록된 메인 메뉴를 선택하시고 [메뉴추가] 버튼을 눌러주세요.");
        addMenuOkFlag = false;
        formControl(true);
        return false;
    }
    // 메뉴 추가 허용값이 유효하면 
    if(addMenuOkFlag) {
        // 새 메뉴 추가
        treeNode = zTree.addNodes(treeNode, {id:("TMP" + newCount++)
                                            ,pId:parentId
                                            ,name:menuName
                                            ,isParent:isParent
                                            ,tmp_type:tmp_type
                                            ,isSaved:"N"
                                            ,mnu_yn:"Y"
                                            ,mnu_dpth:mnu_dpth
                                            ,dsply_yn:"N"});
        
        // 메뉴 상세정보 문구 변경
        $("#dispMenuTit").addClass("dispMenuAddTit");
        $("#dispMenuTit").text($("#dispMenuTit").text().replace("상세","추가"));
        
        //console.log(treeNode);
        
        // 메뉴 상세정보 Form값 초기화
        formClear();
        // 메뉴 상세정보 Hidden값 설정
        $("#up_mnu_seq").val(parentId);
        
        $("#mnu_dpth").val(mnu_dpth);
        // 새로 생성한 메뉴로 포커스
        zTree.editName(treeNode[0]);
        // 새로 생성한 메뉴의 정보를 전역변수에 임시 저장
        editMenuInfo = treeNode[0];
        // 정상적으로 메뉴추가가 완료되면 메뉴추가진행플래그를 진행중으로 변경
        editMenuFlag = true;
    } else {
        alert("선택하신 메뉴는 하위 메뉴를 추가할 수 없는 메뉴입니다.");
    }
};

// DB 저장 메뉴 삭제
function dbSavedMenuRemove(treeNode, directFlag) {
    if(treeNode.tmp_type == "1") {
        alert("메인 메뉴는 삭제하실 수 없습니다.");
        return false;
    }
    
    var params = new Array();
    params.push(treeNode.id);

    if(!directFlag) {
        if(confirm("[" + treeNode.name + "] 메뉴를 삭제하시겠습니까? \n(상위메뉴 삭제시 하위메뉴도 같이 삭제됩니다.)")) {
            dbSavedMenuRemoveAjaxCall(params, '');  
        }   
    } else {
        dbSavedMenuRemoveAjaxCall(params, '');
    }
}

// DB 저장메뉴 삭제 Ajax Call
function dbSavedMenuRemoveAjaxCall(params, up_mnu_seq) {
    ajax({
        url : "/psys/savePsys100203.adm",
        data: {mnu_seq_arr:params, chgFlag:"DELETE"},
        success: function(data) {
            if(data.RESULT = "S") {
                alert("메뉴가 삭제되었습니다.");
                getAdminMenuTree(true);
                admin_menu_grid.retreive({data:{up_mnu_seq:up_mnu_seq}});
            }
        }
    });
}

//메뉴 삭제 Event
function remove(e) {
    // 현재 선택 중인 메뉴 확인
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes(),
        treeNode = nodes[0];
    
    // 선택된 메뉴가 하나도 없으면
    if (nodes.length == 0) {
        alert("삭제하실 메뉴를 선택해 주세요.");
        return false;
    } 
    // 선택된 메뉴가 DB에 저장된 메뉴인 경우
    if("Y" == treeNode.isSaved) {
        dbSavedMenuRemove(treeNode, false);
    } 
    // 선택된 메뉴가 DB저장 전의 메뉴인 경우
    else {
        //  메뉴 트리정보 삭제
        $("#"+treeNode.tId).find("span.button.remove").trigger('click');
        // 삭제 후 메뉴 상세정보 Form값 초기화
        formClear();
    }
    
};

// 삭제 전 EVENT
function beforeRemove(treeId, treeNode) {
    return confirm("[" + treeNode.name + "] 메뉴를 삭제하시겠습니까? \n(상위메뉴 삭제시 하위메뉴도 같이 삭제됩니다.)");
}

// 삭제 후 EVENT
function onRemove(e, treeId, treeNode) {
    // 선택되었던 메뉴가 DB에 저장된 메뉴인 경우
    if("Y" == treeNode.isSaved) {
        dbSavedMenuRemove(treeNode, true);
    } 
    formClear();
}

//이름 변경 후 EVENT
function onRename(e, treeId, treeNode) {
    $("#mnu_nm").val(treeNode.name);
    $("#tmp_type").val(treeNode.tmp_type).prop("selected", true);
    $("input[name='dsply_yn']:radio[value='"+treeNode.dsply_yn+"']").prop('checked',true);
    $("input[name='mnu_yn']:radio[value='"+treeNode.mnu_yn+"']").prop('checked',true);
    changeTmpType("", treeNode.tmp_type);
}

//적용템플릿 영역 초기화
function selectBoxReset(id) {
    $(id).attr("disabled", true);
    $(id).find("option").remove();
    $(id).append("<option value url>#선택</option> ");
} 

// 이름 변경 전 EVENT
function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0) {
        setTimeout(function() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.cancelEditName();
            alert("메뉴명을 입력 해주세요");
        }, 0);
        return false;
    }
    return true;
}

// 편집중단
function resetEdit(e) {
    if(!confirm("편집을 중단하시면 저장된 메뉴까지만 반영됩니다.\n편집을 중단하시겠습니까?")) return false;
    window.location.reload();
}

// 자르기
function cut(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
    nodes = zTree.getSelectedNodes();
    if (nodes.length == 0) {
        alert("이동할 메뉴를 선택 후 [자르기]버튼을 클릭주세요.");
        return;
    }
    curType = "cut";
    setCurSrcNode(nodes[0]);
}

// 복사
function copy(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
    nodes = zTree.getSelectedNodes();
    if (nodes.length == 0) {
        alert("복사할 메뉴를 선택 후 [복사]버튼을 클릭주세요.");
        return;
    }
    curType = "copy";
    setCurSrcNode(nodes[0]);
}

// 복사(자르기)의 임시값 설정
function setCurSrcNode(treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    if (curSrcNode) {
        delete curSrcNode.isCur;
        var tmpNode = curSrcNode;
        curSrcNode = null;
        fontCss(tmpNode);
    }
    curSrcNode = treeNode;
    if (!treeNode) return;

    curSrcNode.isCur = true;            
    zTree.cancelSelectedNode();
    fontCss(curSrcNode);
}

// 붙이기
function paste(e) {
//  console.log(curSrcNode);
    if (!curSrcNode) {
        alert("메뉴를 선택 후 [복사]나 [자르기] 버튼을 클릭 후 진행해주세요.");
        return;
    }
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
    nodes = zTree.getSelectedNodes();
    targetNode = nodes.length>0? nodes[0]:null;
    // 붙이기할 메뉴가 선택되지 않은 경우
    if (!targetNode) {
        alert("붙여넣을 상위메뉴를 선택 해주세요.");
        $("#" + curSrcNode.tId + "_a").trigger('click');
        return false;
    } else if (curSrcNode === targetNode) {
        alert("기존메뉴와 동일한 위치로는 복사/붙이기를 사용하실 수 없습니다.");
        return false;
    }
    // 1Depth에 사용불가 메뉴 체크
    else if(targetNode && targetNode.level == 0 && !curSrcNode.isParent) {
        // 현재 선택된 메뉴(이동할 메뉴)가 메인 메뉴인 경우
        alert("메인 메뉴의 하위로 지정가능한 메뉴가 아닙니다.");
        return false;
    }// 다른계층간의 이동을 제한(부모의 계층이 동일한 경우만 이동을 허용)
    else if(targetNode && targetNode.level == 0 && curSrcNode.mnu_dpth != 1) {
        alert("메뉴 레벨이 같은 경우만 복사/이동이 가능합니다.");
        $("#" + curSrcNode.tId + "_a").trigger('click');
        return false;
    }

    else if (curType === "copy") {
        //console.log(targetNode);
        var curSrcNodeChg = getChangedNodes(curSrcNode, targetNode);
        targetNode = zTree.copyNode(targetNode, curSrcNodeChg, "inner");
        $("#" + targetNode.tId + "_a").trigger('click');
        copyMenuFlag = true;
    }
    else if (curType === "cut") {
        // 동일한 상위 메뉴를 가진 하위 메뉴간의 이동은 불가
        if((targetNode && curSrcNode.parentTId === targetNode.tId) || (!targetNode && !curSrcNode.parentTId)) {
            alert("이미 선택된 메뉴의 하위메뉴로 지정되어 있습니다.");
            $("#" + curSrcNode.tId + "_a").trigger('click');
            return false;
        }
        // 이동할 메뉴 데이터 설정(상위 메뉴 아이디)
        
        if(isNotEmpty(targetNode)) $("#up_mnu_seq").val(targetNode.id);
        targetNode = zTree.moveNode(targetNode, curSrcNode, "inner");
        targetNode = curSrcNode;
        $("#" + targetNode.tId + "_a").trigger('click');
        cutMenuFlag = true;
    }
    setCurSrcNode();
    delete targetNode.isCur;
}

// 복사 대상 데이터 변경
function getChangedNodes(tgtNodes, parentNodes) {
    // 복사할 데이터 최상위 변경
    tgtNodes.id = "";
    tgtNodes.url = "";
    tgtNodes.mnu_seq = "";
    //tgtNodes.name = tgtNodes.name + "_복사본";
    
    // 복사할 데이터 필수 요소 Object 설정
    var topDataArray = new Array();
    var midDataArray = new Array();
    var btmDataArray = new Array();
    var obj = new Object();
//  obj.up_mnu_seq = tgtNodes.pId;
    obj.up_mnu_seq = parentNodes.id;
    obj.us_yn = "Y";
    obj.frnt_yn = "N";
    obj.mnu_yn = "N";
    obj.required_login = "N";
    obj.stf_only_yn = "N";
    obj.lst_mnu_yn = "N";
    obj.mnu_tp_cd = "10";
    obj.mnu_nm = tgtNodes.name+"_복사본";
    //obj.tmp_nm = tgtNodes.name+"_템플릿복사본"; 
    obj.target_url = tgtNodes.target_url;
    obj.mnu_dpth  = parseInt(parentNodes.level,10) +1;//tgtNodes.mnu_dpth;
    obj.srt_seq =   tgtNodes.srt_seq;
    topDataArray.push(obj);
    var midGrpId =1;
    // 하위메뉴가 존재하는지 판단(최대 Depth가 3Depth이므로 3Depth까지 판단)
    if(isNotEmpty(tgtNodes.children)) {
        // 2Depth판단
        for(i=0; i<tgtNodes.children.length; i++) {
            // 복사할 데이터 변경
            tgtNodes.children[i].id = "";
            tgtNodes.children[i].url = "";
            tgtNodes.children[i].mnu_seq = "";
            tgtNodes.children[i].name = tgtNodes.children[i].name + "_복사본";
            
            // 복사할 데이터 필수요소 Object 설정
            var obj2 = new Object();
            //obj2.tmp_type = tgtNodes.children[i].tmp_type;
            obj2.us_yn = "Y";
            obj2.mnu_yn = "N";
            obj2.required_login = "N";
            obj2.mnu_nm = tgtNodes.children[i].name;
            obj2.target_url = tgtNodes.children[i].target_url;
            obj2.srt_seq = tgtNodes.children[i].srt_seq;
            obj2.mnu_dpth = tgtNodes.children[i].mnu_dpth;
            obj2.frnt_yn = "N";
            obj.stf_only_yn = "N";
            obj.lst_mnu_yn = "N";
            obj.mnu_tp_cd = "10";
            obj2.mid_group=midGrpId;
            midDataArray.push(obj2);
            
            // 3Depth판단
            //console.log(tgtNodes.children[i].children);
            if(isNotEmpty(tgtNodes.children[i].children)) {
                for(j=0; j<tgtNodes.children[i].children.length; j++) {
                    // 복사할 데이터 변경
                    /*
                    tgtNodes.children[i].children[j].id = "";
                    tgtNodes.children[i].children[j].target_url = "";
                    tgtNodes.children[i].children[j].mnu_seq = "";
                    */
                    tgtNodes.children[i].children[j].name = tgtNodes.children[i].children[j].name + "_복사본";
                    
                    // 복사할 데이터 필수요소 Object 설정
                    var obj3 = new Object();
                    //obj3.tmp_type = tgtNodes.children[i].children[j].tmp_type;
                    obj3.us_yn = "Y";
                    obj3.mnu_yn = "N";
                    obj3.required_login = "N";
                    obj3.frnt_yn = "N";
                    obj.stf_only_yn = "N";
                    obj.lst_mnu_yn = "N";
                    obj.mnu_tp_cd = "10";
                    obj3.mnu_nm = tgtNodes.children[i].children[j].name;
                    //obj3.tmp_nm = tgtNodes.children[i].children[j].name+"_템플릿복사본";
                    obj3.target_url = tgtNodes.children[i].children[j].target_url;
                    obj3.srt_seq = tgtNodes.children[i].children[j].srt_seq;
                    obj3.mnu_dpth = tgtNodes.children[i].children[j].mnu_dpth;
                    obj3.mid_group = midGrpId;
                    btmDataArray.push(obj3);
                }
            }
            midGrpId ++;
        }
    }
    var totalInfo = new Object();
    if(topDataArray.length > 0) totalInfo.topDataInfo = topDataArray;
    if(midDataArray.length > 0) totalInfo.midDataInfo = midDataArray;
    if(btmDataArray.length > 0) totalInfo.btmDataInfo = btmDataArray;
    copyNodesObj = totalInfo;
//  copyNodesJsonObj = JSON.stringify(totalInfo);
    
    return tgtNodes
}

// 메뉴 상태 구분(복사/자르기)
function fontCss(treeNode) {
    var aObj = $("#" + treeNode.tId + "_a");
    aObj.removeClass("copy").removeClass("cut");
    if (treeNode === curSrcNode) {
        if (curType == "copy") {
            aObj.addClass(curType);
        } else {
            aObj.addClass(curType);
        }           
    }
}


function expandNode(e) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
    type = e.data.type;
    //[""0""].children
    //nodes = zTree.getSelectedNodes();
    var totNodes = zTree.getNodes();
    var nodes =totNodes[0].children;
    if (type == "expandAll") {
        zTree.expandAll(true);
    } else if (type == "collapseAll") {
        zTree.expandAll(false);
    } else {
        var callbackFlag = $("#callbackTrigger").attr("checked");
        for (var i=0, l=nodes.length; i<l; i++) {
            zTree.setting.view.fontCss = {};
            if (type == "expand") {
                //zTree.expandNode(nodes[i], true, null, null, callbackFlag);
                zTree.expandAll(true);
            } else if (type == "collapse") {
                    zTree.expandNode(nodes[i], false, null, null, callbackFlag);
            }
        }
    }
}

function getPgmInfo(row){
    //console.log(row);
    $("#pgm_nm").val(row.PGM_NM);
    $("#pgm_id").val(row.PGM_ID);
}
function fn_Search(){
	if(isEmpty($("#sys_info").val())) {
		alert("사이트를 선택해주세요.");
		return false;
	}
	
    getAdminMenuTree(true);
}

function fn_Save(){
    // 편집창 비활성화시
    if($("input[name='mnu_nm']:disabled").length > 0) {
        alert("편집하실 메뉴를 선택해주세요.")
        return false;
    }
    
    $("#dispMenuTit").removeClass("dispMenuAddTit");
    $("#dispMenuTit").text($("#dispMenuTit").text().replace("추가","상세"));
    if(submitFormCheck()) {
        $("#sys_cd").val($("#sys_info").val())
        $("#menuInfoForm").submit();
    }
}

//사이트 리스트 취득
function getSystemList() {
    var html = "";
    ajax({
        url     : "/pdsp/getPdsp1011ListSit",
        data    : {sys_cd : _sys_cd}, 
        success : function (data) {
            if (data.RESULT == "S") {
                var sitListCnt = data.rows.length;
                $(data.rows).each(function (index) {
                    // 조회 건수가 하나일 경우 사이트 하나
                    if(sitListCnt == 1) {
                        html += "<option  class='passOption' value="+ this.SYS_CD +" selected='selected' >"+ this.SYS_NM +"</option>"
                        return false;
                    } else {
                        html += "<option class='passOption' value="+ this.SYS_CD +">"+ this.SYS_NM +"</option>"
                        $("#sys_info").closest('div').show();
                    }
                });
                $("#sys_info").append(html);
            }
        }
    });
}

var expand_count = 0;
function tree_all(thisobj) {
    var $this = $(thisobj);
    
    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
    type = $(thisobj).attr("data-type");
    
    var totNodes = zTree.getNodes();
    var nodes =totNodes[0].children;
    
    if (type == "expandAll" && expand_count == 0) {
        $(thisobj).attr("data-type", "collapseAll");
        $this.text("전체닫기");
        $this.addClass("on");
        zTree.expandAll(true);
        
        expand_count++;
        
    } else if (type == "collapseAll" && expand_count == 1) {
        $(thisobj).attr("data-type", "expandAll");
        $this.text("전체열기");
        $this.removeClass("on");
        zTree.expandAll(false);
        
        expand_count = 0;
    }
    
}
</script>
</head>
<body>
<div class="frameWrap">
    <!-- tab1 contents -->
    <div class="subCon on clear">
        <%@ include file="/WEB-INF/views/pandora3/common/include/btnList2.jsp" %>   
        <div class="tableTopLeft gridBtn">
            <div class="selectInner" style="display: none;">
                <label for="sys_info">사이트 : </label>
                <select id="sys_info" name="sys_info" class="select" >
                    <option  class="passOption" value="">선택</option>
                </select>
            </div>
        </div>
        <div class="treeWrap">
            <div class="treeInner">
                <!--  메뉴 구성 -->
                <div class="tblType1 menuEdit treeList">
                    <div class="leftTop">
                        <h3>메뉴구성</h3>
                        <button class="btn_common btn_gray" id="addMenu" onclick="return false;">추가</button>
                        <div class="treeBtn">
                            <button class="btn_common btn_default treeOpen" data-type="expandAll" id="tree_all" onclick="tree_all(this)">
                                전체열기
                                <%-- <img src="${pageContext.request.contextPath}/resources/pandora3/images/common_new/bg_bottom_arrow.png" alt=""> --%>
                            </button>
                            <button class="btn_common btn_default" id="removeMenu" onclick="return false;">메뉴삭제</button>
                            <button class="btn_common btn_default" id="resetEdit" onclick="return false;">편집중단</button>
                            <button class="btn_common btn_default" id="copy" onclick="return false;">복사</button>
                            <button class="btn_common btn_default" id="cut" onclick="return false;">자르기</button>
                            <button class="btn_common btn_default" id="paste" onclick="return false;">붙이기</button>
                        </div>
                    </div>
                    
                    <div class="leftBottom">
                        <ul id="treeDemo" class="ztree menuEdit h590">
                            
                        </ul>
                    </div>
                </div>
                <!--//  메뉴 구성 -->
                <!--  메뉴 상세 정보 -->
                <div class="detailInfo">
                    <div class="menuInfo">
                        <div class="tableTop first">
                            <h3 class="title">상세정보</h3>
                        </div>
                        <form name="menuInfoForm" id="menuInfoForm"  method="post" onsubmit="return false;">
                        <input type="hidden" name="up_mnu_seq" id="up_mnu_seq" />
                        <input type="hidden" name="pgm_id" id="pgm_id" />
                        <input type="hidden" name="mnu_dpth" id="mnu_dpth" />
                        <input type="hidden" name="pgm_id" id="pgm_id" />
                        <input type="hidden" name="sys_cd" id="sys_cd" />
                        <table id="menuInfo" class="tblType1">
                            <colgroup>
                                <col style="width:" />
                                <col style="width:" />
                                <col style="width:" />
                                <col style="width:" />
                                <col style="width:" />
                                <col style="width:" />
                            </colgroup>
                            <tr>
                                <th>
                                    <label for="mnu_nm" class="vv necessary">메뉴명</label>
                                </th>
                                <td colspan="2"><span class="txt_pw"><input type="text" id="mnu_nm" name="mnu_nm" class="w100" /></span></td>
                                <th>
                                    <label class="vv">메뉴아이디</label>
                                </th>
                                <td colspan="2"><span class="txt_pw"><input type="text" id="mnu_seq" name="mnu_seq" class="w100 readonlyTxt" readonly/></span></td>
                            </tr>
                            <tr>
                                <th>
                                    <label>사용여부</label>
                                </th>
                                <td colspan="2">
                                    <ul class="radioWrap typeOnline">
                                        <li>
                                            <input type="radio" id="use_y" name="us_yn" value="Y">
                                            <label for="use_y" >사용</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="use_n" name="us_yn" value="N">
                                            <label for="use_n" >미사용</label>
                                        </li>
                                    </ul>
                                </td>
                                <th>
                                    <label>직원전용</label>
                                </th>
                                <td colspan="2">
                                    <ul class="radioWrap typeOnline">
                                        <li>
                                            <input type="radio" id="menu_y" name="mnu_yn" value="Y">
                                            <label for="menu_y" >공용</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="menu_n" name="mnu_yn" value="N">
                                            <label for="menu_n" >직원전용</label>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    <label for="mnu_nm" class="vv necessary">메뉴순서</label>
                                </th>
                                <td colspan="5">
                                    <span class="txt_pw"><input type="text" id="srt_seq" name="srt_seq" class="w10" onkeydown='return onlyNumber(event)' onkeyup='removeChar(event)' maxlength="3"/> 번째</span>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    <label>개인정보 화면</label>
                                </th>
                                <td colspan="2">
                                    <ul class="radioWrap typeOnline">
                                        <li>
                                            <input type="radio" id="prinf_scrn_y" name="prn_inf_scrn_yn" value="Y">
                                            <label for="prinf_scrn_y" >사용</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="prinf_scrn_n" name="prn_inf_scrn_yn" value="N">
                                            <label for="prinf_scrn_n" >미사용</label>
                                        </li>
                                    </ul>
                                </td>
                                <th>
                                    <label>VDI 화면</label>
                                </th>
                                <td colspan="2">
                                    <ul class="radioWrap typeOnline">
                                        <li>
                                            <input type="radio" id="vdi_scrn_y" name="vdi_scrn_yn" value="Y">
                                            <label for="vdi_scrn_y" >사용</label>
                                        </li>
                                        <li>
                                            <input type="radio" id="vdi_scrn_n" name="vdi_scrn_yn" value="N">
                                            <label for="vdi_scrn_n" >미사용</label>
                                        </li>
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    <label>화면명</label>
                                </th>
                                <td colspan="5">
                                    <span class="txt_pw"><input type="text" id="pgm_nm" name="pgm_nm" class="w40" /></span>
                                    <a href="#" id="btn_get_tmp_url" class="btn_common btn_default btn_input_right">프로그램검색</a>
                                </td>
                            </tr>
                        </table>
                        </form>
                        <div class="grid_right_btn">
                            <div id="btn_menu_grid" class="grid_right_btn_in">
                            </div>
                        </div>

                        <!-- Grid : TOP GRID-->
                        <table id="admin_menu_grid"></table> 
                        <div id="admin_menu_grid_pager"></div>
                        <!-- Grid : TOP GRID -->
                        
                        <!-- Grid Search Form -->
                        <!-- Grid Search Form -->
                    </div>
                </div>
                <!--//  메뉴 상세 정보 -->
            </div>
        </div>
    </div>
</div>
</body>
<%@ include file="/WEB-INF/views/pandora3/common/include/footer.jsp" %>
