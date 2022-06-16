package kr.co.ta9.pandora3.pcommon.dto;

import kr.co.ta9.pandora3.pcommon.dto.base.BaseTsysAdmGrpRol;

/**
 * TsysAdmGrpRol - ValueObject Extended class for table [tsys_adm_grp_rol].
 *
 * <pre>
 *     Generated by CodeProcessor. Yon can freely modify this generated file.
 *     Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2019. 10. 28
 */
public class TsysAdmGrpRol extends BaseTsysAdmGrpRol {
	
	private String rol_yn;
	private String usr_id;
	private String f_apl_st_dt;
	private String f_apl_ed_dt;
	private String f_upd_dttm;
	private int srt_seq;
	
	public String getRol_yn() {
		return rol_yn;
	}
	public void setRol_yn(String rol_yn) {
		this.rol_yn = rol_yn;
	}
	public String getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(String usr_id) {
		this.usr_id = usr_id;
	}
	public String getF_apl_st_dt() {
		return f_apl_st_dt;
	}
	public void setF_apl_st_dt(String f_apl_st_dt) {
		this.f_apl_st_dt = f_apl_st_dt;
	}
	public String getF_apl_ed_dt() {
		return f_apl_ed_dt;
	}
	public void setF_apl_ed_dt(String f_apl_ed_dt) {
		this.f_apl_ed_dt = f_apl_ed_dt;
	}
	public String getF_upd_dttm() {
		return f_upd_dttm;
	}
	public void setF_upd_dttm(String f_upd_dttm) {
		this.f_upd_dttm = f_upd_dttm;
	}
	public int getSrt_sqn() {
		return srt_seq;
	}
	public void setSrt_sqn(int srt_seq) {
		this.srt_seq = srt_seq;
	}
	
	

}