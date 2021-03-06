package kr.co.ta9.pandora3.pcommon.dto.base;

import java.sql.Timestamp;

import  kr.co.ta9.pandora3.app.bean.CommonBean;

/**
 * BaseTbLgapMnurolrtnnH - ValueObject class for table [TB_LGAP_MNUROLRTNN_H].
 *
 * <pre>
 *     Do not modify this file
 *     Copyright &amp;copy 2004 by Pionnet, Inc. All rights reserved.
 * </pre>
 *
 * @since 2019. 12. 02
 */
public class BaseTbLgapMnurolrtnnH extends CommonBean
{
	/** hist_no (hist_no) */
	private int hist_no;
	private Integer obj_hist_no;
	/** sys_cd (sys_cd) */
	private String sys_cd;
	/** sys_nm (sys_nm) */
	private String sys_nm;
	/** mnu_seq (mnu_seq) */
	private int mnu_seq;
	private Integer obj_mnu_seq;
	/** mnu_nm (mnu_nm) */
	private String mnu_nm;
	/** rol_id (rol_id) */
	private String rol_id;
	/** rol_nm (rol_nm) */
	private String rol_nm;
	/** pgm_btn_cd (pgm_btn_cd) */
	private String pgm_btn_cd;
	/** pgm_btn_nm (pgm_btn_nm) */
	private String pgm_btn_nm;
	/** hist_stat_cd (hist_stat_cd) */
	private String hist_stat_cd;
	/** hist_stat_nm (hist_stat_nm) */
	private String hist_stat_nm;
	/** crtr_id (crtr_id) */
	private String crtr_id;
	/** crt_dttm (crt_dttm) */
	private Timestamp crt_dttm;
	/** updr_id (updr_id) */
	private String updr_id;
	/** upd_dttm (upd_dttm) */
	private Timestamp upd_dttm;

	public BaseTbLgapMnurolrtnnH()
	{
		super();

	}

	/**
	 * getter, setter
	 */
	public int getHist_no() { 
		return hist_no; 
	}
	public Integer getObj_hist_no() {
		return obj_hist_no; 
	}
	
	public void setHist_no(int hist_no) {
		this.hist_no = hist_no;
		this.obj_hist_no = hist_no;
	}


	public String getSys_cd() {
		return sys_cd;
	}

	public void setSys_cd(String sys_cd) {
		this.sys_cd = sys_cd;
	}

	public String getSys_nm() {
		return sys_nm;
	}

	public void setSys_nm(String sys_nm) {
		this.sys_nm = sys_nm;
	}

	public int getMnu_seq() { 
		return mnu_seq; 
	}
	public Integer getObj_mnu_seq() {
		return obj_mnu_seq; 
	}
	
	public void setMnu_seq(int mnu_seq) {
		this.mnu_seq = mnu_seq;
		this.obj_mnu_seq = mnu_seq;
	}

	public String getMnu_nm() { 
		return mnu_nm; 
	}
	public void setMnu_nm(String mnu_nm) {
		this.mnu_nm = mnu_nm; 
	}

	public String getRol_id() { 
		return rol_id; 
	}
	public void setRol_id(String rol_id) {
		this.rol_id = rol_id; 
	}

	public String getRol_nm() { 
		return rol_nm; 
	}
	public void setRol_nm(String rol_nm) {
		this.rol_nm = rol_nm; 
	}

	public String getPgm_btn_cd() { 
		return pgm_btn_cd; 
	}
	public void setPgm_btn_cd(String pgm_btn_cd) {
		this.pgm_btn_cd = pgm_btn_cd; 
	}

	public String getPgm_btn_nm() { 
		return pgm_btn_nm; 
	}
	public void setPgm_btn_nm(String pgm_btn_nm) {
		this.pgm_btn_nm = pgm_btn_nm; 
	}

	public String getHist_stat_cd() { 
		return hist_stat_cd; 
	}
	public void setHist_stat_cd(String hist_stat_cd) {
		this.hist_stat_cd = hist_stat_cd; 
	}

	public String getHist_stat_nm() { 
		return hist_stat_nm; 
	}
	public void setHist_stat_nm(String hist_stat_nm) {
		this.hist_stat_nm = hist_stat_nm; 
	}

	public String getCrtr_id() { 
		return crtr_id; 
	}
	public void setCrtr_id(String crtr_id) {
		this.crtr_id = crtr_id; 
	}

	public Timestamp getCrt_dttm() { 
		return crt_dttm; 
	}
	public void setCrt_dttm(Timestamp crt_dttm) {
		this.crt_dttm = crt_dttm; 
	}

	public String getUpdr_id() { 
		return updr_id; 
	}
	public void setUpdr_id(String updr_id) {
		this.updr_id = updr_id; 
	}

	public Timestamp getUpd_dttm() { 
		return upd_dttm; 
	}
	public void setUpd_dttm(Timestamp upd_dttm) {
		this.upd_dttm = upd_dttm; 
	}

}