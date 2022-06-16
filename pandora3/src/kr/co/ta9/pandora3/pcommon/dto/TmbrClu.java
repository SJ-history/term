package kr.co.ta9.pandora3.pcommon.dto;

import kr.co.ta9.pandora3.pcommon.dto.base.BaseTmbrClu;

/**
 * TmbrClu - ValueObject Extended class for table [TMBR_CLU].
 *
 * <pre>
 *     Generated by CodeProcessor. Yon can freely modify this generated file.
 *     Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2019. 02. 16
 */
public class TmbrClu extends BaseTmbrClu {
	private String clu_cls_cd_nm;
	private String clu_tp_cd_nm;
	private int clu_hst_seq;
	
	public String getMst_cd() {
		return clu_cls_cd_nm;
	}
	public void setClu_cls_cd_nm(String clu_cls_cd_nm) {
		this.clu_cls_cd_nm = clu_cls_cd_nm;
	}
	public String getClu_tp_cd_nm() {
		return clu_tp_cd_nm;
	}
	public void setClu_tp_cd_nm(String clu_tp_cd_nm) {
		this.clu_tp_cd_nm = clu_tp_cd_nm;
	}

	public int getClu_hst_seq() {
		return clu_hst_seq;
	}
	public void setClu_hst_seq(int terms_hst_idx) {
		this.clu_hst_seq = terms_hst_idx;
	}


}