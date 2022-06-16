package kr.co.ta9.pandora3.pcommon.dto;

import kr.co.ta9.pandora3.pcommon.dto.base.BaseTdspMnPop;

/**
 * TdspMnPop - ValueObject Extended class for table [TDSP_MN_POP].
 *
 * <pre>
 *     Generated by CodeProcessor. Yon can freely modify this generated file.
 *     Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2019. 02. 15
 */
public class TdspMnPop extends BaseTdspMnPop {
	private String f_st_dttm;
	private String f_ed_dttm;
	private String f_crt_dttm;
	private String sit_nm;
	private String lgn_id;
	public String getF_st_dttm() {
		return f_st_dttm;
	}
	public void setF_st_dttm(String f_st_dttm) {
		this.f_st_dttm = f_st_dttm;
	}
	public String getF_ed_dttm() {
		return f_ed_dttm;
	}
	public void setF_ed_dttm(String f_ed_dttm) {
		this.f_ed_dttm = f_ed_dttm;
	}
	public String getF_crt_dttm() {
		return f_crt_dttm;
	}
	public void setF_crt_dttm(String f_crt_dttm) {
		this.f_crt_dttm = f_crt_dttm;
	}
	public String getSit_nm() {
		return sit_nm;
	}
	public void setSit_nm(String sit_nm) {
		this.sit_nm = sit_nm;
	}
	public String getLgn_id() {
		return lgn_id;
	}
	public void setLgn_id(String lgn_id) {
		this.lgn_id = lgn_id;
	}
	
}