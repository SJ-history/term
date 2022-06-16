package kr.co.ta9.pandora3.pbbs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.ta9.pandora3.app.repository.BaseDao;
import kr.co.ta9.pandora3.app.servlet.ParameterMap;

/**
 * TbbsWikiCtegryDao - DAO(Data Access Object) class for table [TBBS_WIKI_CTEGRY].
 *
 * <pre>
 *   Generated by CodeProcessor. You can freely modify this generated file.
 *   Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2020. 04. 07
 */
@Repository
public class TbbsWikiCtegryDao extends BaseDao {

	/**
	 * 위키 트리조회
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPbbs5001List01(ParameterMap parameterMap) throws Exception {
		return getSqlSession().selectList("TbbsWikiCtegry.getPbbs5001List01", parameterMap);
	}

	/**
	 * 위키트리 특정ID 목록 조회 (하위포함)
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPbbs5001List02(ParameterMap parameterMap) throws Exception {
		return getSqlSession().selectList("TbbsWikiCtegry.getPbbs5001List02", parameterMap);
	}

	/**
	 * 위키트리 특정ID 목록 조회 (하위만)
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPbbs5001List03(ParameterMap parameterMap) throws Exception {
		return getSqlSession().selectList("TbbsWikiCtegry.getPbbs5001List03", parameterMap);
	}

	/**
	 * 위키트리 신규 카테고리 CD 조회
	 * @return
	 * @throws Exception
	 */
	public int getPbbs5001CtegryCd() throws Exception {
		return getSqlSession().selectOne("TbbsWikiCtegry.getPbbs5001CtegryCd");
	}

	/**
	 * 위키트리  카테고리/명 조회
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getPbbs5002List01(ParameterMap parameterMap) throws Exception {
		return getSqlSession().selectList("TbbsWikiCtegry.getPbbs5002List01", parameterMap);
	}
	
	/**
	 * 위키 카테고리 복수 삭제
	 * @param parameterMap
	 * @throws Exception
	 */
	public void deletePbbs5001List01(ParameterMap parameterMap) throws Exception {
		getSqlSession().insert("TbbsWikiCtegry.deletePbbs5001List01", parameterMap);
	}

	/**
	 * 위키 카테고리 등록
	 * @param parameterMap
	 * @throws Exception
	 */
	public void insertPbbs5001One(ParameterMap parameterMap) throws Exception {
		getSqlSession().insert("TbbsWikiCtegry.insertPbbs5001One", parameterMap);
	}

	/**
	 * 위키 카테고리 수정
	 * @param parameterMap
	 * @throws Exception
	 */
	public void updatePbbs5001One(ParameterMap parameterMap) throws Exception {
		getSqlSession().update("TbbsWikiCtegry.updatePbbs5001One", parameterMap);
	}

	/**
	 * 위키 카테고리 복수 수정
	 * @param parameterMap
	 * @throws Exception
	 */
	public void updatePbbs5001List01(ParameterMap parameterMap) throws Exception {
		getSqlSession().update("TbbsWikiCtegry.updatePbbs5001List01", parameterMap);
	}

}