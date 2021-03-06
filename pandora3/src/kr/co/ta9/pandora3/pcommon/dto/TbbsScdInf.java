package kr.co.ta9.pandora3.pcommon.dto;

import kr.co.ta9.pandora3.pcommon.dto.base.BaseTbbsScdInf;

/**
 * TbbsScdInf - ValueObject Extended class for table [TBBS_SCD_INF].
 *
 * <pre>
 *     Generated by CodeProcessor. Yon can freely modify this generated file.
 *     Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2019. 02. 16
 */
public class TbbsScdInf extends BaseTbbsScdInf {
	
	private String f_st_dttm;
	private String f_ed_dttm;
	private String usr_nm;
	private String tit1;
	private String tit2;
	
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
	public String getUsr_nm() {
		return usr_nm;
	}
	public void setUsr_nm(String usr_nm) {
		this.usr_nm = usr_nm;
	}
	public String getTit1() {
		return tit1;
	}
	public void setTit1(String tit1) {
		this.tit1 = tit1;
	}
	public String getTit2() {
		return tit2;
	}
	public void setTit2(String tit2) {
		this.tit2 = tit2;
	}
	
	
}