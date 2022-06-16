package kr.co.ta9.pandora3.psys.dao;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import kr.co.ta9.pandora3.app.repository.BaseDao;
import kr.co.ta9.pandora3.app.servlet.ParameterMap;
import kr.co.ta9.pandora3.pcommon.dto.TsysOrg;
import kr.co.ta9.pandora3.pcommon.dto.usrdef.TsysOrgTree;

/**
 * TsysOrgDao - DAO(Data Access Object) class for table [TSYS_ORG].
 *
 * <pre>
 *   Generated by CodeProcessor. You can freely modify this generated file.
 *   Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2019. 03. 14
 */
@Repository
public class TsysOrgDao extends BaseDao {
	
	/**
	 * 조직 트리 조회
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public List<TsysOrg> selectTsysOrgTreeList(ParameterMap parameterMap) throws Exception {
		return getSqlSession().selectList("TsysOrg.selectTsysOrgTreeList", parameterMap);
	}
	
	/**
	 * 하위 조직 조회
	 * @param parameterMap
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject selectTsysOrgList(ParameterMap parameterMap) throws Exception {
		return queryForGrid("TsysOrg.selectTsysOrgList", parameterMap);
	}
	
	/**
	 * 상하위 조직 조회 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public JSONObject selectTsysOrgGridList(ParameterMap parameterMap) throws Exception{
		return queryForGrid("TsysOrg.selectTsysOrgGridList", parameterMap);
	}

	public List<TsysOrgTree> selectTsysOrgTree(ParameterMap parameterMap) {
		return getSqlSession().selectList("TsysOrg.selectTsysOrgTree", parameterMap);
	}
	
}