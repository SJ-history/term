package kr.co.ta9.pandora3.pcommon.dto;

import kr.co.ta9.pandora3.pcommon.dto.base.BaseTsysAdmFvrt;

/**
 * TsysAdmFvrt - ValueObject Extended class for table [TSYS_ADM_FVRT].
 *
 * <pre>
 *     Generated by CodeProcessor. Yon can freely modify this generated file.
 *     Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2019. 03. 12
 */
public class TsysAdmFvrt extends BaseTsysAdmFvrt {
	
	private String top_mnu_seq;     // 최상단메뉴아이디
	private String top_mnu_nm;     // 최상단메뉴아이디
	private String midd_mnu_seq;    // 중단메뉴아이디
	private String midd_mnu_nm;    // 중단메뉴아이디
	private String pgm_id;
	private String target_url;
	private String mnu_depth;
	private String mnu_tp_cd;
	public String getTop_mnu_seq() {
		return top_mnu_seq;
	}
	public void setTop_mnu_seq(String top_mnu_seq) {
		this.top_mnu_seq = top_mnu_seq;
	}
	public String getTop_mnu_nm() {
		return top_mnu_nm;
	}
	public void setTop_mnu_nm(String top_mnu_nm) {
		this.top_mnu_nm = top_mnu_nm;
	}
	public String getMidd_mnu_seq() {
		return midd_mnu_seq;
	}
	public void setMidd_mnu_seq(String midd_mnu_seq) {
		this.midd_mnu_seq = midd_mnu_seq;
	}
	public String getMidd_mnu_nm() {
		return midd_mnu_nm;
	}
	public void setMidd_mnu_nm(String midd_mnu_nm) {
		this.midd_mnu_nm = midd_mnu_nm;
	}
	public String getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(String pgm_id) {
		this.pgm_id = pgm_id;
	}
	public String getTarget_url() {
		return target_url;
	}
	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}
	public String getMnu_depth() {
		return mnu_depth;
	}
	public void setMnu_depth(String mnu_depth) {
		this.mnu_depth = mnu_depth;
	}
	public String getMnu_tp_cd() {
		return mnu_tp_cd;
	}
	public void setMnu_tp_cd(String mnu_tp_cd) {
		this.mnu_tp_cd = mnu_tp_cd;
	}
	

}