/**
 * SI01ResponseDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package mx.com.interware.spira.dto;

public class SI01ResponseDTO  implements java.io.Serializable {
	private String  ACCOUNT_CYCLE;
	private String  TRANSFER_STATUS;
	private String  TRANSFER_REASON;
	private String  TRANSFER_DATE;
	private String  LOST_ACCT_STATUS;
	private String  ACCT_DELETE_STATUS;
	private String  CUR_ACCT_COL_STAT;
	private String  OLD_CYC_AFT_CHGOF;
	private String  REISSUE_DAY;
	private String  TYPE_CODE;
	private String  LAST_CRD_RATE_CHG;
	private String  CREDIT_RATING;
	private String  AGENT;
	private String  ISSUE_BANK;
	private String  BRANCH;
	private String  CREDIT_SCORE;
	private String  LAST_DT_SC_CHG;
	private String  OFFCR_CHANGE_SCR;
	private String  ORIG_CREDIT_SCORE;
	private String  STATUS;
	private String  AUTO_MAN_CBR;
	private String  MANUAL_OVRRIDE_CBR;
	private String  OVR_CVR_BREXCEP;
	private String  TST_CBR_ISS_CREXC;
	private String  ACCT_TYPE_EXCEP;
	private String  AUTH_OS_EXCEP;
	private String  ACT_TODAY_EXCEP;
	private String  BEHAVORIAL_SCORE;
	private String  OFFICER;
	private String  OUTSTANDING_AUTHS;
	private String  OS_AUTH_AMT;
	private String  CURRENTLY_OVRLIMIT;
	private String  NUM_OF_STATEMENTS;
	private String  NUM_OF_TM_CD_BAL;
	private String  DAYS_IN_CRED_BAL;
	private String  DAYS_OVERLIMIT;
	private String  DAYS_PAST_DUE;
	private String  NUM_OL_NOTICES;
	private String  OL_SINCE_OPEN;
	private String  DATE_LST_OL_SNT;
	private String  TIMES_PD_CYCLE;
	private String  PD_NOTC_1_SENT;
	private String  PD_NOTC_2_SENT;
	private String  COLL_CARD_SENT;
	private String  PD_NOTC_3_SENT;
	private String  OVERLIM_LET_1;
	private String  OVERLIM_LET_2;
	private String  NON_ACC_STATUS;
	private String  CRED_LIM_INC_LET;
	private String  ACCT_IN_DISPUTE;
	private String  LAST_DISPUTE_DATE;
	private String  OFCR_APPR_LST_DIS;
	private String  AMT_CUR_IN_DISP;
	private String LAST_DISPUTE_SETLD;
	private String NUM_TIMES_DISPUTE;
	private String RESTRICTED_CARD_LS;
	private String REASON_ON_RCL;
	private String REG_CODE_FOR_RCL;
	private String DATE_LAST_ON_RCL;
	private String PURGE_DATE_FOR_RCL;
	private String ACCT_IN_CRB_STAT;
	private String CRB_REASON;
	private String CRB_REGION_CODE;
	private String PURGE_DATE_FOR_CRB;
	private String DAT_LAST_ON_CRB;
	private String DONT_PRINT_PAST_DU;
	private String DONT_PRINT_OVERLMT;
	private String DONT_PRNT_PAST_MSG;
	private String DONT_PRNT_OVLM_MSG;
	private String DONT_PRNT_STATEMNT;
	private String IMMUNE_AT_CYCLE;
	private String FC_PREC_4_DEC;
	private String STRIPE_WAS_READ;
	private String CHG_TAX_THIS_CYCLE;
	private String DATE_NOTC_PRT_CHG;
	private String OFCR_APP_NOTC_CHG;
	private String INS_RATE_CODE;
	private String MONTH_INS_DUE;
	private String TIMES_INS_CAL_OPN;
	private String CH_BIRTH_DATE;
	private String USER_CODE_1;
	private String USER_CODE_2_POS0;
	private String USER_CODE_2_POS1;
	private String USER_CODE_2_POS2;
	private String USER_CODE_2_POS3;
	private String USER_CODE_3;
	private String USER_ACCT_1;
	private String USER_ACCT_2;
	private String USER_ACCT_3;
	private String OTH_ACCT_NUMBER;
	private String DDA_ACCNT_NUMBER;
	private String SAV_ACCT_NUMBER;
	private String INS_LOAN_ACCT_NBR;
	private String TRNACCT_NR_OLD_NW;
	private String CARDS_OUTSTANDING;
	private String CARDS_TOBE_REISSUE;
	private String EXP_DT_CUR_CARD;
	private String TRANSIT_ROUTING_NB;
	private String DOCUTEL_PIN_NUMBER;
	private String DIEBOLD_OFFSET_PIN; // 100
	private String DIEBOLD_ENTRY_NBR;
	private String ISD_FILL_T;
	private String DIEBOLD_WITHDR_LMT;
	private String BUSINESS_LOGO_FLD;
	private String CORP_ACCT_NUMBER;
	private String ISD_FILL_0;
	private String OVERDRAFT_COV_FLAG;
	private String DUAL_VISA_MAST_PL;
	private String CARD_USE_REPORTED;
	private String DATE_CARD_1ST_USED;
	private String PSTDU_MANUALY_CHGD;
	private String TIMES_PSTDUE_CHGMN;
	private String LST_PASTDUE_MAN_CH;
	private String LAST_PASTDUE_NOTIC;
	private String OFCR_APP_PT_DU_CH;
	private String LAST_CRED_RPT_CHCK;
	private String DATE_ACCT_OPENNED;
	private String LAST_STATEMENT_DT;
	private String LAST_MAINT_DATE;
	private String PRIR_LAST_MAINT_DT;
	private String LAST_MAG_MAINT;
	private String LAST_CRED_ON_ACCT;
	private String LAST_PAYMENT_DATE;
	private String LAST_PAYMENT_AMT;
	private String DATE_LAST_PURCHASE;
	private String DATE_LAST_CASH_ADV;
	private String DATE_LAST_PAST_DUE;
	private String DATE_LAST_LATE_CHG;
	private String LAST_TRANSACT_DATE;
	private String ANY_TYPE_LAST_ACTV;
	private String DATE_OF_LAST_LETTR;
	private String DATE_PAYMENT_DUE;
	private String PIN_REQUEST_FLAG;
	private String DATE_OF_PIN_REQUEST;
	private String DATE_SENT_CREDBURU;
	private String SPOUSE_RESP_FR_ACT;
	private String SPOUSE_RESP_CODE;
	private String ADDR_CHG_S_BUREAU;
	private String NAME_LNE1_CHANGE;
	private String NAME_LNE2_CHANGE;
	private String NAME_LINE2_ADDED;
	private String CREDITBUREA_REPORT;
	private String CARD_REQ_TODAY;
	private String CARD_REQ_DEL_TOD;
	private String DEL_TRADE_FM_BUREA;
	private String ORIG_CREDIT_LIMIT;
	private String NBR_TIMES_LIM_CHGD;
	private String OFCR_APPV_LIMT_CHG;
	private String LAST_LIMIT_INC_DEC;
	private String DATE_LAST_LIM_CHG;
	private String TYPE_OF_LIMIT_CHG;
	private String FIXED_PAYMENT_AMT;
	private String DATE_FIXED_PAY_STR;
	private String DATE_FIX_PAYAMT_CH;
	private String OFCR_APPR_FIXED_P;
	private String HIGH_BAL_ON_ACCT;
	private String DATE_HIGH_BAL_REAC;
	private String FIN_CHARGE_CYCLE;
	private String PAST_DUE_LETTER_1;
	private String PAST_DUE_LETTER_2;
	private String PAST_DUE_LETTER_3;
	private String PAST_DUE_LETTER_4;
	private String PAST_DUE_LETTER_5;
	private String PAST_DUE_LETTER_6;
	private String PAST_DUE_LETTER_7;
	private String STATE_CD_FOR_ACCT;
	private String SKIP_PAYMENT_STATE;
	private String TYPE_AUTO_DEDUCTIO;
	private String METHOD_FOR_AUTO_DE;
	private String ACT_REISSUE_RATING;
	private String EXPDATE_BEF_REISSU;
	private String ENCODE_ALL_ACCTS;
	private String STR_CRD_ALPHA_PIN;
	private String CHECK_OK_CARD;
	private String CHG_IN_TERMS_COMP;
	private String REISSU_CRD_PRINTED;
	private String DELETE_PROC_COMP;
	private String CHARGEOFF_PROC_COM;
	private String TRANSFER_PROC_COMP;
	private String ACCT_ACTIVE_TODAY;
	private String ACCT_ACT_THIS_MONT;
	private String ACCT_ACTIVE_T_YEAR;
	private String ACCT_ACT_PRIR_YEAR;
	private String ACCT_ACT_SINCE_OPE;
	private String DEB_ACTIVITY_TODAY;
	private String CRED_ACTVITY_TODAY;
	private String SUPP_AIR_INS_ACT_M;
	private String SALES_DRFT_TH_CYCL;
	private String CASH_ADV_THIS_CYC;
	private String CREDIT_THIS_CYCLE;
	private String PAYMENT_THIS_CYCLE;
	private String SALES_DRAFT_REV_CY;
	private String CASHADV_REV_TH_CYC;
	private String CRED_REV_THIS_CYCL;
	private String PAYMENT_REV_T_CYCL;
	private String PREV_STATE_BAL;
	private String PREV_PURCHASE_BAL;
	private String PREV_CASHADV_BAL;
	private String PREVPUR_FINCHG_OUT;
	private String PREV_CASHADV_FINCH; // 200
	private String PREV_MISC_OUTSTAND;
	private String PREV_STATE_LATECHG;
	private String PREV_STATE_INS_CHG;
	private String PREV_STATE_ACT_DUE;
	private String DATE_LAST_ANNFEE_C;
	private String AMT_LAST_ANNUAL_FE;
	private String ANNUAL_FEE_FLAG;
	private String ANNUAL_FEE_RATECDE;
	private String CHARGEOFF_STATUS;
	private String DATE_CHARGE_OFF;
	private String BAL_CHARGED_OFF;
	private String CHARGEOFF_REASON;
	private String RATECD_UND_BP1;
	private String RATECD_OVR1_U2;
	private String RATECD_OVR_BP2;
	private String THREE_PURCHASE_RT_CDS;
	private String RATECD_CA_UND1;
	private String RATECD_CA_OV1U2;
	private String RATECD_CA_OVR2;
	private String THREE_CASHADV_RATECODE;
	private String RATECODE_CSHADV_FE;
	private String RATECODE_MINPAYMNT;
	private String RATECODE_LATECHG_F;
	private String HOLD_PLASTIC;
	private String ZIP4_NOT_CONVERT;
	private String ZIP4_IS_VALID;
	private String ONL_ADDRCHG_UPD_OV;
	private String UTH_USER1_HASCARD;
	private String AUTH_USER2_HASCARD;
	private String AUTH_USER3_HASCARD;
	private String DATE_LAT_NMADDR_MN;
	private String SSN_FOR_NAME_LINE1;
	private String HOME_PHONE_NUMBER;
	private String BUSINESS_PHONE_NUMBER;
	private String SPOUSE_NAME;
	private String PLACE_OF_EMPLOYMEN;
	private String NAME_LINE1_PRIM_AP;
	private String NAME_LINE2_COAPPL;
	private String ADDRESS_LINE_1;
	private String ADDRESS_LINE_2;
	private String ADDRESS_CITY;
	private String STATE_ABBRE_ADDLN1;
	private String AUTH_USER_NAME1;
	private String AUTH_USER_NAME2;
	private String AUTH_USER_NAME3;
	private String STATEMENT_HOLD_CDE;
	private String ZIPCODE_CARDHOLDER;
	private String LAST_AUTO_PASTDUE;
	private String TIMES_AUTO_PASTDUE;
	private String AGENT_BANK_NAME;
	private String EXT_TRNACCT_NR_OLD;
	private String SHORT_NAME;
	private String RATE_CODE_CH_COMPL;
	private String CAN_CHG_MBRSHIP_FE;
	private String CAN_CHG_OVRLMT_FEE;
	private String ACCT_HAS_VISA_PHONE;
	private String BILL_FEDX_CARD_DE;
	private String CARD_ACTIVATION_ST;
	private String INDICATE_PURCH_IHB;
	private String INDICATE_CASH_IHB;
	private String PAST_DUE_NOTICE_1;
	private String PAST_DUE_NOTICE_2;
	private String AUTO_LTR_1;
	private String AUTO_LTR_2;
	private String AUTO_LTR_3;
	private String AUTO_LTR_4;
	private String ACCT_DNLD_FROM_SSI;
	private String ACCT_NEW_TO_SSI_RM;
	private String ACE_STATUS_BYTE;
	private String QTRLY_PMT_MO_COUNT;
	private String DEF_PMT_SCHD_CNTDN;
	private String DEF_PMT_SCHD_REFRE;
	private String NWB_STATUS;
	private String NWB_ACCT_LISTED;
	private String JVTYPE;
	private String LONG_NAME;
	private String TAXID;
	private String BUSN_EXT;
	private String PREV_POINT_BAL;
	private String ACCUM_POINTS;
	private String POINTS_USED;
	private String EXPIRED_POINTS;
	private String CURR_POINT_BAL;
	private String BONUS_POINT_CDATE;
	private String ISD_FILL3;
	private String FRAUD_MESSAGE_IND;
	private String COMB_DET_STATEMENT;
	private String COMB_MASTER_STATEM;
	private String SYS_GENER_PIN;
	private String CUST_SELECTED_PIN;
	private String BANK_53;
	private String BANK_SPECIFIC_IND;
	private String BANK_SPEC2_IND;
	private String FREE_TEXT_STATUS;
	private String EXT_SEG_1_ON_FILE;
	private String STATE_PROD_THIS_CY;
	private String ZERO_BALANCE_YESTE;
	private String AFFIN_DUES_CG_PREV;
	private String AFFIN_DUES_FIRST_C;
	private String NEW_ACCT_1ST_BBAL;  // 300
	private String RET_CHECK_THIS_CYC;
	private String SENT_TO_MC_TRAV;
	private String ISD_FILL_8;
	private String AUTH_NCNB_SEC_ON;
	private String AUTH_NCNB_SEC_COMP;
	private String ACCT_PENDING_XFR;
	private String CHANGE_IN_TERM_DT;
	private String ISD_FILL4;
	private String FRAUD_COUNTERFEIT;
	private String ISD_FILL5;
	private String PD_FULL_BEF_PAYDUE;
	private String OVERLIMIT_CHRG_COM;
	private String ATM_XFER_TODAY_ACC;
	private String DEBIT_CRDLIM_UPD_T;
	private String LETTER_REQ_TODAY;
	private String LETTER_REQ_DEL_TOD;
	private String ACCT_REISSUED_TH_M;
	private String AUTO_LIM_INCR_TH_M;
	private String CO_STMT_FLAG;
	private String CITY_STATE_COMBINE;
	private String ISD_FILL6;
	private String DATE_TO_CHG_ANN_FE;
	private String ISD_FILL7;
	private String BANK_NUMBER;
	private String CELL_PHONE;
	private String CURP_ID;
	private String GUARANTOR1;
	private String RFC_GUARANTOR1;
	private String GUARANTOR2;
	private String RFC_GUARANTOR2;
	private String GUARANTOR3;
	private String RFC_GUARANTOR3;
	private String EMBOSS_NAME;
	private String PERSONAL_RFC;
	private String EMAIL;
	private String MISC_USER1;
	private String IDS_FILL8;
	/**
	 * @return
	 */
	public String getACCOUNT_CYCLE() {
		return ACCOUNT_CYCLE;
	}

	/**
	 * @return
	 */
	public String getACCT_ACT_PRIR_YEAR() {
		return ACCT_ACT_PRIR_YEAR;
	}

	/**
	 * @return
	 */
	public String getACCT_ACT_SINCE_OPE() {
		return ACCT_ACT_SINCE_OPE;
	}

	/**
	 * @return
	 */
	public String getACCT_ACT_THIS_MONT() {
		return ACCT_ACT_THIS_MONT;
	}

	/**
	 * @return
	 */
	public String getACCT_ACTIVE_T_YEAR() {
		return ACCT_ACTIVE_T_YEAR;
	}

	/**
	 * @return
	 */
	public String getACCT_ACTIVE_TODAY() {
		return ACCT_ACTIVE_TODAY;
	}

	/**
	 * @return
	 */
	public String getACCT_DELETE_STATUS() {
		return ACCT_DELETE_STATUS;
	}

	/**
	 * @return
	 */
	public String getACCT_DNLD_FROM_SSI() {
		return ACCT_DNLD_FROM_SSI;
	}

	/**
	 * @return
	 */
	public String getACCT_HAS_VISA_PHONE() {
		return ACCT_HAS_VISA_PHONE;
	}

	/**
	 * @return
	 */
	public String getACCT_IN_CRB_STAT() {
		return ACCT_IN_CRB_STAT;
	}

	/**
	 * @return
	 */
	public String getACCT_IN_DISPUTE() {
		return ACCT_IN_DISPUTE;
	}

	/**
	 * @return
	 */
	public String getACCT_NEW_TO_SSI_RM() {
		return ACCT_NEW_TO_SSI_RM;
	}

	/**
	 * @return
	 */
	public String getACCT_PENDING_XFR() {
		return ACCT_PENDING_XFR;
	}

	/**
	 * @return
	 */
	public String getACCT_REISSUED_TH_M() {
		return ACCT_REISSUED_TH_M;
	}

	/**
	 * @return
	 */
	public String getACCT_TYPE_EXCEP() {
		return ACCT_TYPE_EXCEP;
	}

	/**
	 * @return
	 */
	public String getACCUM_POINTS() {
		return ACCUM_POINTS;
	}

	/**
	 * @return
	 */
	public String getACE_STATUS_BYTE() {
		return ACE_STATUS_BYTE;
	}

	/**
	 * @return
	 */
	public String getACT_REISSUE_RATING() {
		return ACT_REISSUE_RATING;
	}

	/**
	 * @return
	 */
	public String getACT_TODAY_EXCEP() {
		return ACT_TODAY_EXCEP;
	}

	/**
	 * @return
	 */
	public String getADDR_CHG_S_BUREAU() {
		return ADDR_CHG_S_BUREAU;
	}

	/**
	 * @return
	 */
	public String getADDRESS_CITY() {
		return ADDRESS_CITY;
	}

	/**
	 * @return
	 */
	public String getADDRESS_LINE_1() {
		return ADDRESS_LINE_1;
	}

	/**
	 * @return
	 */
	public String getADDRESS_LINE_2() {
		return ADDRESS_LINE_2;
	}

	/**
	 * @return
	 */
	public String getAFFIN_DUES_CG_PREV() {
		return AFFIN_DUES_CG_PREV;
	}

	/**
	 * @return
	 */
	public String getAFFIN_DUES_FIRST_C() {
		return AFFIN_DUES_FIRST_C;
	}

	/**
	 * @return
	 */
	public String getAGENT() {
		return AGENT;
	}

	/**
	 * @return
	 */
	public String getAGENT_BANK_NAME() {
		return AGENT_BANK_NAME;
	}

	/**
	 * @return
	 */
	public String getAMT_CUR_IN_DISP() {
		return AMT_CUR_IN_DISP;
	}

	/**
	 * @return
	 */
	public String getAMT_LAST_ANNUAL_FE() {
		return AMT_LAST_ANNUAL_FE;
	}

	/**
	 * @return
	 */
	public String getANNUAL_FEE_FLAG() {
		return ANNUAL_FEE_FLAG;
	}

	/**
	 * @return
	 */
	public String getANNUAL_FEE_RATECDE() {
		return ANNUAL_FEE_RATECDE;
	}

	/**
	 * @return
	 */
	public String getANY_TYPE_LAST_ACTV() {
		return ANY_TYPE_LAST_ACTV;
	}

	/**
	 * @return
	 */
	public String getATM_XFER_TODAY_ACC() {
		return ATM_XFER_TODAY_ACC;
	}

	/**
	 * @return
	 */
	public String getAUTH_NCNB_SEC_COMP() {
		return AUTH_NCNB_SEC_COMP;
	}

	/**
	 * @return
	 */
	public String getAUTH_NCNB_SEC_ON() {
		return AUTH_NCNB_SEC_ON;
	}

	/**
	 * @return
	 */
	public String getAUTH_OS_EXCEP() {
		return AUTH_OS_EXCEP;
	}

	/**
	 * @return
	 */
	public String getAUTH_USER_NAME1() {
		return AUTH_USER_NAME1;
	}

	/**
	 * @return
	 */
	public String getAUTH_USER_NAME2() {
		return AUTH_USER_NAME2;
	}

	/**
	 * @return
	 */
	public String getAUTH_USER_NAME3() {
		return AUTH_USER_NAME3;
	}

	/**
	 * @return
	 */
	public String getAUTH_USER2_HASCARD() {
		return AUTH_USER2_HASCARD;
	}

	/**
	 * @return
	 */
	public String getAUTH_USER3_HASCARD() {
		return AUTH_USER3_HASCARD;
	}

	/**
	 * @return
	 */
	public String getAUTO_LIM_INCR_TH_M() {
		return AUTO_LIM_INCR_TH_M;
	}

	/**
	 * @return
	 */
	public String getAUTO_LTR_1() {
		return AUTO_LTR_1;
	}

	/**
	 * @return
	 */
	public String getAUTO_LTR_2() {
		return AUTO_LTR_2;
	}

	/**
	 * @return
	 */
	public String getAUTO_LTR_3() {
		return AUTO_LTR_3;
	}

	/**
	 * @return
	 */
	public String getAUTO_LTR_4() {
		return AUTO_LTR_4;
	}

	/**
	 * @return
	 */
	public String getAUTO_MAN_CBR() {
		return AUTO_MAN_CBR;
	}

	/**
	 * @return
	 */
	public String getBAL_CHARGED_OFF() {
		return BAL_CHARGED_OFF;
	}

	/**
	 * @return
	 */
	public String getBANK_53() {
		return BANK_53;
	}

	/**
	 * @return
	 */
	public String getBANK_NUMBER() {
		return BANK_NUMBER;
	}

	/**
	 * @return
	 */
	public String getBANK_SPEC2_IND() {
		return BANK_SPEC2_IND;
	}

	/**
	 * @return
	 */
	public String getBANK_SPECIFIC_IND() {
		return BANK_SPECIFIC_IND;
	}

	/**
	 * @return
	 */
	public String getBEHAVORIAL_SCORE() {
		return BEHAVORIAL_SCORE;
	}

	/**
	 * @return
	 */
	public String getBILL_FEDX_CARD_DE() {
		return BILL_FEDX_CARD_DE;
	}

	/**
	 * @return
	 */
	public String getBONUS_POINT_CDATE() {
		return BONUS_POINT_CDATE;
	}

	/**
	 * @return
	 */
	public String getBRANCH() {
		return BRANCH;
	}

	/**
	 * @return
	 */
	public String getBUSINESS_LOGO_FLD() {
		return BUSINESS_LOGO_FLD;
	}

	/**
	 * @return
	 */
	public String getBUSINESS_PHONE_NUMBER() {
		return BUSINESS_PHONE_NUMBER;
	}

	/**
	 * @return
	 */
	public String getBUSN_EXT() {
		return BUSN_EXT;
	}

	/**
	 * @return
	 */
	public String getCAN_CHG_MBRSHIP_FE() {
		return CAN_CHG_MBRSHIP_FE;
	}

	/**
	 * @return
	 */
	public String getCAN_CHG_OVRLMT_FEE() {
		return CAN_CHG_OVRLMT_FEE;
	}

	/**
	 * @return
	 */
	public String getCARD_ACTIVATION_ST() {
		return CARD_ACTIVATION_ST;
	}

	/**
	 * @return
	 */
	public String getCARD_REQ_DEL_TOD() {
		return CARD_REQ_DEL_TOD;
	}

	/**
	 * @return
	 */
	public String getCARD_REQ_TODAY() {
		return CARD_REQ_TODAY;
	}

	/**
	 * @return
	 */
	public String getCARD_USE_REPORTED() {
		return CARD_USE_REPORTED;
	}

	/**
	 * @return
	 */
	public String getCARDS_OUTSTANDING() {
		return CARDS_OUTSTANDING;
	}

	/**
	 * @return
	 */
	public String getCARDS_TOBE_REISSUE() {
		return CARDS_TOBE_REISSUE;
	}

	/**
	 * @return
	 */
	public String getCASH_ADV_THIS_CYC() {
		return CASH_ADV_THIS_CYC;
	}

	/**
	 * @return
	 */
	public String getCASHADV_REV_TH_CYC() {
		return CASHADV_REV_TH_CYC;
	}

	/**
	 * @return
	 */
	public String getCELL_PHONE() {
		return CELL_PHONE;
	}

	/**
	 * @return
	 */
	public String getCH_BIRTH_DATE() {
		return CH_BIRTH_DATE;
	}

	/**
	 * @return
	 */
	public String getCHANGE_IN_TERM_DT() {
		return CHANGE_IN_TERM_DT;
	}

	/**
	 * @return
	 */
	public String getCHARGEOFF_PROC_COM() {
		return CHARGEOFF_PROC_COM;
	}

	/**
	 * @return
	 */
	public String getCHARGEOFF_REASON() {
		return CHARGEOFF_REASON;
	}

	/**
	 * @return
	 */
	public String getCHARGEOFF_STATUS() {
		return CHARGEOFF_STATUS;
	}

	/**
	 * @return
	 */
	public String getCHECK_OK_CARD() {
		return CHECK_OK_CARD;
	}

	/**
	 * @return
	 */
	public String getCHG_IN_TERMS_COMP() {
		return CHG_IN_TERMS_COMP;
	}

	/**
	 * @return
	 */
	public String getCHG_TAX_THIS_CYCLE() {
		return CHG_TAX_THIS_CYCLE;
	}

	/**
	 * @return
	 */
	public String getCITY_STATE_COMBINE() {
		return CITY_STATE_COMBINE;
	}

	/**
	 * @return
	 */
	public String getCO_STMT_FLAG() {
		return CO_STMT_FLAG;
	}

	/**
	 * @return
	 */
	public String getCOLL_CARD_SENT() {
		return COLL_CARD_SENT;
	}

	/**
	 * @return
	 */
	public String getCOMB_DET_STATEMENT() {
		return COMB_DET_STATEMENT;
	}

	/**
	 * @return
	 */
	public String getCOMB_MASTER_STATEM() {
		return COMB_MASTER_STATEM;
	}

	/**
	 * @return
	 */
	public String getCORP_ACCT_NUMBER() {
		return CORP_ACCT_NUMBER;
	}

	/**
	 * @return
	 */
	public String getCRB_REASON() {
		return CRB_REASON;
	}

	/**
	 * @return
	 */
	public String getCRB_REGION_CODE() {
		return CRB_REGION_CODE;
	}

	/**
	 * @return
	 */
	public String getCRED_ACTVITY_TODAY() {
		return CRED_ACTVITY_TODAY;
	}

	/**
	 * @return
	 */
	public String getCRED_LIM_INC_LET() {
		return CRED_LIM_INC_LET;
	}

	/**
	 * @return
	 */
	public String getCRED_REV_THIS_CYCL() {
		return CRED_REV_THIS_CYCL;
	}

	/**
	 * @return
	 */
	public String getCREDIT_RATING() {
		return CREDIT_RATING;
	}

	/**
	 * @return
	 */
	public String getCREDIT_SCORE() {
		return CREDIT_SCORE;
	}

	/**
	 * @return
	 */
	public String getCREDIT_THIS_CYCLE() {
		return CREDIT_THIS_CYCLE;
	}

	/**
	 * @return
	 */
	public String getCREDITBUREA_REPORT() {
		return CREDITBUREA_REPORT;
	}

	/**
	 * @return
	 */
	public String getCUR_ACCT_COL_STAT() {
		return CUR_ACCT_COL_STAT;
	}

	/**
	 * @return
	 */
	public String getCURP_ID() {
		return CURP_ID;
	}

	/**
	 * @return
	 */
	public String getCURR_POINT_BAL() {
		return CURR_POINT_BAL;
	}

	/**
	 * @return
	 */
	public String getCURRENTLY_OVRLIMIT() {
		return CURRENTLY_OVRLIMIT;
	}

	/**
	 * @return
	 */
	public String getCUST_SELECTED_PIN() {
		return CUST_SELECTED_PIN;
	}

	/**
	 * @return
	 */
	public String getDAT_LAST_ON_CRB() {
		return DAT_LAST_ON_CRB;
	}

	/**
	 * @return
	 */
	public String getDATE_ACCT_OPENNED() {
		return DATE_ACCT_OPENNED;
	}

	/**
	 * @return
	 */
	public String getDATE_CARD_1ST_USED() {
		return DATE_CARD_1ST_USED;
	}

	/**
	 * @return
	 */
	public String getDATE_CHARGE_OFF() {
		return DATE_CHARGE_OFF;
	}

	/**
	 * @return
	 */
	public String getDATE_FIX_PAYAMT_CH() {
		return DATE_FIX_PAYAMT_CH;
	}

	/**
	 * @return
	 */
	public String getDATE_FIXED_PAY_STR() {
		return DATE_FIXED_PAY_STR;
	}

	/**
	 * @return
	 */
	public String getDATE_HIGH_BAL_REAC() {
		return DATE_HIGH_BAL_REAC;
	}

	/**
	 * @return
	 */
	public String getDATE_LAST_ANNFEE_C() {
		return DATE_LAST_ANNFEE_C;
	}

	/**
	 * @return
	 */
	public String getDATE_LAST_CASH_ADV() {
		return DATE_LAST_CASH_ADV;
	}

	/**
	 * @return
	 */
	public String getDATE_LAST_LATE_CHG() {
		return DATE_LAST_LATE_CHG;
	}

	/**
	 * @return
	 */
	public String getDATE_LAST_LIM_CHG() {
		return DATE_LAST_LIM_CHG;
	}

	/**
	 * @return
	 */
	public String getDATE_LAST_ON_RCL() {
		return DATE_LAST_ON_RCL;
	}

	/**
	 * @return
	 */
	public String getDATE_LAST_PAST_DUE() {
		return DATE_LAST_PAST_DUE;
	}

	/**
	 * @return
	 */
	public String getDATE_LAST_PURCHASE() {
		return DATE_LAST_PURCHASE;
	}

	/**
	 * @return
	 */
	public String getDATE_LAT_NMADDR_MN() {
		return DATE_LAT_NMADDR_MN;
	}

	/**
	 * @return
	 */
	public String getDATE_LST_OL_SNT() {
		return DATE_LST_OL_SNT;
	}

	/**
	 * @return
	 */
	public String getDATE_NOTC_PRT_CHG() {
		return DATE_NOTC_PRT_CHG;
	}

	/**
	 * @return
	 */
	public String getDATE_OF_LAST_LETTR() {
		return DATE_OF_LAST_LETTR;
	}

	/**
	 * @return
	 */
	public String getDATE_OF_PIN_REQUEST() {
		return DATE_OF_PIN_REQUEST;
	}

	/**
	 * @return
	 */
	public String getDATE_PAYMENT_DUE() {
		return DATE_PAYMENT_DUE;
	}

	/**
	 * @return
	 */
	public String getDATE_SENT_CREDBURU() {
		return DATE_SENT_CREDBURU;
	}

	/**
	 * @return
	 */
	public String getDATE_TO_CHG_ANN_FE() {
		return DATE_TO_CHG_ANN_FE;
	}

	/**
	 * @return
	 */
	public String getDAYS_IN_CRED_BAL() {
		return DAYS_IN_CRED_BAL;
	}

	/**
	 * @return
	 */
	public String getDAYS_OVERLIMIT() {
		return DAYS_OVERLIMIT;
	}

	/**
	 * @return
	 */
	public String getDAYS_PAST_DUE() {
		return DAYS_PAST_DUE;
	}

	/**
	 * @return
	 */
	public String getDDA_ACCNT_NUMBER() {
		return DDA_ACCNT_NUMBER;
	}

	/**
	 * @return
	 */
	public String getDEB_ACTIVITY_TODAY() {
		return DEB_ACTIVITY_TODAY;
	}

	/**
	 * @return
	 */
	public String getDEBIT_CRDLIM_UPD_T() {
		return DEBIT_CRDLIM_UPD_T;
	}

	/**
	 * @return
	 */
	public String getDEF_PMT_SCHD_CNTDN() {
		return DEF_PMT_SCHD_CNTDN;
	}

	/**
	 * @return
	 */
	public String getDEF_PMT_SCHD_REFRE() {
		return DEF_PMT_SCHD_REFRE;
	}

	/**
	 * @return
	 */
	public String getDEL_TRADE_FM_BUREA() {
		return DEL_TRADE_FM_BUREA;
	}

	/**
	 * @return
	 */
	public String getDELETE_PROC_COMP() {
		return DELETE_PROC_COMP;
	}

	/**
	 * @return
	 */
	public String getDIEBOLD_ENTRY_NBR() {
		return DIEBOLD_ENTRY_NBR;
	}

	/**
	 * @return
	 */
	public String getDIEBOLD_OFFSET_PIN() {
		return DIEBOLD_OFFSET_PIN;
	}

	/**
	 * @return
	 */
	public String getDIEBOLD_WITHDR_LMT() {
		return DIEBOLD_WITHDR_LMT;
	}

	/**
	 * @return
	 */
	public String getDOCUTEL_PIN_NUMBER() {
		return DOCUTEL_PIN_NUMBER;
	}

	/**
	 * @return
	 */
	public String getDONT_PRINT_OVERLMT() {
		return DONT_PRINT_OVERLMT;
	}

	/**
	 * @return
	 */
	public String getDONT_PRINT_PAST_DU() {
		return DONT_PRINT_PAST_DU;
	}

	/**
	 * @return
	 */
	public String getDONT_PRNT_OVLM_MSG() {
		return DONT_PRNT_OVLM_MSG;
	}

	/**
	 * @return
	 */
	public String getDONT_PRNT_PAST_MSG() {
		return DONT_PRNT_PAST_MSG;
	}

	/**
	 * @return
	 */
	public String getDONT_PRNT_STATEMNT() {
		return DONT_PRNT_STATEMNT;
	}

	/**
	 * @return
	 */
	public String getDUAL_VISA_MAST_PL() {
		return DUAL_VISA_MAST_PL;
	}

	/**
	 * @return
	 */
	public String getEMAIL() {
		return EMAIL;
	}

	/**
	 * @return
	 */
	public String getEMBOSS_NAME() {
		return EMBOSS_NAME;
	}

	/**
	 * @return
	 */
	public String getENCODE_ALL_ACCTS() {
		return ENCODE_ALL_ACCTS;
	}

	/**
	 * @return
	 */
	public String getEXP_DT_CUR_CARD() {
		return EXP_DT_CUR_CARD;
	}

	/**
	 * @return
	 */
	public String getEXPDATE_BEF_REISSU() {
		return EXPDATE_BEF_REISSU;
	}

	/**
	 * @return
	 */
	public String getEXPIRED_POINTS() {
		return EXPIRED_POINTS;
	}

	/**
	 * @return
	 */
	public String getEXT_SEG_1_ON_FILE() {
		return EXT_SEG_1_ON_FILE;
	}

	/**
	 * @return
	 */
	public String getEXT_TRNACCT_NR_OLD() {
		return EXT_TRNACCT_NR_OLD;
	}

	/**
	 * @return
	 */
	public String getFC_PREC_4_DEC() {
		return FC_PREC_4_DEC;
	}

	/**
	 * @return
	 */
	public String getFIN_CHARGE_CYCLE() {
		return FIN_CHARGE_CYCLE;
	}

	/**
	 * @return
	 */
	public String getFIXED_PAYMENT_AMT() {
		return FIXED_PAYMENT_AMT;
	}

	/**
	 * @return
	 */
	public String getFRAUD_COUNTERFEIT() {
		return FRAUD_COUNTERFEIT;
	}

	/**
	 * @return
	 */
	public String getFRAUD_MESSAGE_IND() {
		return FRAUD_MESSAGE_IND;
	}

	/**
	 * @return
	 */
	public String getFREE_TEXT_STATUS() {
		return FREE_TEXT_STATUS;
	}

	/**
	 * @return
	 */
	public String getGUARANTOR1() {
		return GUARANTOR1;
	}

	/**
	 * @return
	 */
	public String getGUARANTOR2() {
		return GUARANTOR2;
	}

	/**
	 * @return
	 */
	public String getGUARANTOR3() {
		return GUARANTOR3;
	}

	/**
	 * @return
	 */
	public String getHIGH_BAL_ON_ACCT() {
		return HIGH_BAL_ON_ACCT;
	}

	/**
	 * @return
	 */
	public String getHOLD_PLASTIC() {
		return HOLD_PLASTIC;
	}

	/**
	 * @return
	 */
	public String getHOME_PHONE_NUMBER() {
		return HOME_PHONE_NUMBER;
	}

	/**
	 * @return
	 */
	public String getIDS_FILL8() {
		return IDS_FILL8;
	}

	/**
	 * @return
	 */
	public String getIMMUNE_AT_CYCLE() {
		return IMMUNE_AT_CYCLE;
	}

	/**
	 * @return
	 */
	public String getINDICATE_CASH_IHB() {
		return INDICATE_CASH_IHB;
	}

	/**
	 * @return
	 */
	public String getINDICATE_PURCH_IHB() {
		return INDICATE_PURCH_IHB;
	}

	/**
	 * @return
	 */
	public String getINS_LOAN_ACCT_NBR() {
		return INS_LOAN_ACCT_NBR;
	}

	/**
	 * @return
	 */
	public String getINS_RATE_CODE() {
		return INS_RATE_CODE;
	}

	/**
	 * @return
	 */
	public String getISD_FILL_0() {
		return ISD_FILL_0;
	}

	/**
	 * @return
	 */
	public String getISD_FILL_8() {
		return ISD_FILL_8;
	}

	/**
	 * @return
	 */
	public String getISD_FILL_T() {
		return ISD_FILL_T;
	}

	/**
	 * @return
	 */
	public String getISD_FILL3() {
		return ISD_FILL3;
	}

	/**
	 * @return
	 */
	public String getISD_FILL4() {
		return ISD_FILL4;
	}

	/**
	 * @return
	 */
	public String getISD_FILL5() {
		return ISD_FILL5;
	}

	/**
	 * @return
	 */
	public String getISD_FILL6() {
		return ISD_FILL6;
	}

	/**
	 * @return
	 */
	public String getISD_FILL7() {
		return ISD_FILL7;
	}

	/**
	 * @return
	 */
	public String getISSUE_BANK() {
		return ISSUE_BANK;
	}

	/**
	 * @return
	 */
	public String getJVTYPE() {
		return JVTYPE;
	}

	/**
	 * @return
	 */
	public String getLAST_AUTO_PASTDUE() {
		return LAST_AUTO_PASTDUE;
	}

	/**
	 * @return
	 */
	public String getLAST_CRD_RATE_CHG() {
		return LAST_CRD_RATE_CHG;
	}

	/**
	 * @return
	 */
	public String getLAST_CRED_ON_ACCT() {
		return LAST_CRED_ON_ACCT;
	}

	/**
	 * @return
	 */
	public String getLAST_CRED_RPT_CHCK() {
		return LAST_CRED_RPT_CHCK;
	}

	/**
	 * @return
	 */
	public String getLAST_DISPUTE_DATE() {
		return LAST_DISPUTE_DATE;
	}

	/**
	 * @return
	 */
	public String getLAST_DISPUTE_SETLD() {
		return LAST_DISPUTE_SETLD;
	}

	/**
	 * @return
	 */
	public String getLAST_DT_SC_CHG() {
		return LAST_DT_SC_CHG;
	}

	/**
	 * @return
	 */
	public String getLAST_LIMIT_INC_DEC() {
		return LAST_LIMIT_INC_DEC;
	}

	/**
	 * @return
	 */
	public String getLAST_MAG_MAINT() {
		return LAST_MAG_MAINT;
	}

	/**
	 * @return
	 */
	public String getLAST_MAINT_DATE() {
		return LAST_MAINT_DATE;
	}

	/**
	 * @return
	 */
	public String getLAST_PASTDUE_NOTIC() {
		return LAST_PASTDUE_NOTIC;
	}

	/**
	 * @return
	 */
	public String getLAST_PAYMENT_AMT() {
		return LAST_PAYMENT_AMT;
	}

	/**
	 * @return
	 */
	public String getLAST_PAYMENT_DATE() {
		return LAST_PAYMENT_DATE;
	}

	/**
	 * @return
	 */
	public String getLAST_STATEMENT_DT() {
		return LAST_STATEMENT_DT;
	}

	/**
	 * @return
	 */
	public String getLAST_TRANSACT_DATE() {
		return LAST_TRANSACT_DATE;
	}

	/**
	 * @return
	 */
	public String getLETTER_REQ_DEL_TOD() {
		return LETTER_REQ_DEL_TOD;
	}

	/**
	 * @return
	 */
	public String getLETTER_REQ_TODAY() {
		return LETTER_REQ_TODAY;
	}

	/**
	 * @return
	 */
	public String getLONG_NAME() {
		return LONG_NAME;
	}

	/**
	 * @return
	 */
	public String getLOST_ACCT_STATUS() {
		return LOST_ACCT_STATUS;
	}

	/**
	 * @return
	 */
	public String getLST_PASTDUE_MAN_CH() {
		return LST_PASTDUE_MAN_CH;
	}

	/**
	 * @return
	 */
	public String getMANUAL_OVRRIDE_CBR() {
		return MANUAL_OVRRIDE_CBR;
	}

	/**
	 * @return
	 */
	public String getMETHOD_FOR_AUTO_DE() {
		return METHOD_FOR_AUTO_DE;
	}

	/**
	 * @return
	 */
	public String getMISC_USER1() {
		return MISC_USER1;
	}

	/**
	 * @return
	 */
	public String getMONTH_INS_DUE() {
		return MONTH_INS_DUE;
	}

	/**
	 * @return
	 */
	public String getNAME_LINE1_PRIM_AP() {
		return NAME_LINE1_PRIM_AP;
	}

	/**
	 * @return
	 */
	public String getNAME_LINE2_ADDED() {
		return NAME_LINE2_ADDED;
	}

	/**
	 * @return
	 */
	public String getNAME_LINE2_COAPPL() {
		return NAME_LINE2_COAPPL;
	}

	/**
	 * @return
	 */
	public String getNAME_LNE1_CHANGE() {
		return NAME_LNE1_CHANGE;
	}

	/**
	 * @return
	 */
	public String getNAME_LNE2_CHANGE() {
		return NAME_LNE2_CHANGE;
	}

	/**
	 * @return
	 */
	public String getNBR_TIMES_LIM_CHGD() {
		return NBR_TIMES_LIM_CHGD;
	}

	/**
	 * @return
	 */
	public String getNEW_ACCT_1ST_BBAL() {
		return NEW_ACCT_1ST_BBAL;
	}

	/**
	 * @return
	 */
	public String getNON_ACC_STATUS() {
		return NON_ACC_STATUS;
	}

	/**
	 * @return
	 */
	public String getNUM_OF_STATEMENTS() {
		return NUM_OF_STATEMENTS;
	}

	/**
	 * @return
	 */
	public String getNUM_OF_TM_CD_BAL() {
		return NUM_OF_TM_CD_BAL;
	}

	/**
	 * @return
	 */
	public String getNUM_OL_NOTICES() {
		return NUM_OL_NOTICES;
	}

	/**
	 * @return
	 */
	public String getNUM_TIMES_DISPUTE() {
		return NUM_TIMES_DISPUTE;
	}

	/**
	 * @return
	 */
	public String getNWB_ACCT_LISTED() {
		return NWB_ACCT_LISTED;
	}

	/**
	 * @return
	 */
	public String getNWB_STATUS() {
		return NWB_STATUS;
	}

	/**
	 * @return
	 */
	public String getOFCR_APP_NOTC_CHG() {
		return OFCR_APP_NOTC_CHG;
	}

	/**
	 * @return
	 */
	public String getOFCR_APP_PT_DU_CH() {
		return OFCR_APP_PT_DU_CH;
	}

	/**
	 * @return
	 */
	public String getOFCR_APPR_FIXED_P() {
		return OFCR_APPR_FIXED_P;
	}

	/**
	 * @return
	 */
	public String getOFCR_APPR_LST_DIS() {
		return OFCR_APPR_LST_DIS;
	}

	/**
	 * @return
	 */
	public String getOFCR_APPV_LIMT_CHG() {
		return OFCR_APPV_LIMT_CHG;
	}

	/**
	 * @return
	 */
	public String getOFFCR_CHANGE_SCR() {
		return OFFCR_CHANGE_SCR;
	}

	/**
	 * @return
	 */
	public String getOFFICER() {
		return OFFICER;
	}

	/**
	 * @return
	 */
	public String getOL_SINCE_OPEN() {
		return OL_SINCE_OPEN;
	}

	/**
	 * @return
	 */
	public String getOLD_CYC_AFT_CHGOF() {
		return OLD_CYC_AFT_CHGOF;
	}

	/**
	 * @return
	 */
	public String getONL_ADDRCHG_UPD_OV() {
		return ONL_ADDRCHG_UPD_OV;
	}

	/**
	 * @return
	 */
	public String getORIG_CREDIT_LIMIT() {
		return ORIG_CREDIT_LIMIT;
	}

	/**
	 * @return
	 */
	public String getORIG_CREDIT_SCORE() {
		return ORIG_CREDIT_SCORE;
	}

	/**
	 * @return
	 */
	public String getOS_AUTH_AMT() {
		return OS_AUTH_AMT;
	}

	/**
	 * @return
	 */
	public String getOTH_ACCT_NUMBER() {
		return OTH_ACCT_NUMBER;
	}

	/**
	 * @return
	 */
	public String getOUTSTANDING_AUTHS() {
		return OUTSTANDING_AUTHS;
	}

	/**
	 * @return
	 */
	public String getOVERDRAFT_COV_FLAG() {
		return OVERDRAFT_COV_FLAG;
	}

	/**
	 * @return
	 */
	public String getOVERLIM_LET_1() {
		return OVERLIM_LET_1;
	}

	/**
	 * @return
	 */
	public String getOVERLIM_LET_2() {
		return OVERLIM_LET_2;
	}

	/**
	 * @return
	 */
	public String getOVERLIMIT_CHRG_COM() {
		return OVERLIMIT_CHRG_COM;
	}

	/**
	 * @return
	 */
	public String getOVR_CVR_BREXCEP() {
		return OVR_CVR_BREXCEP;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_LETTER_1() {
		return PAST_DUE_LETTER_1;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_LETTER_2() {
		return PAST_DUE_LETTER_2;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_LETTER_3() {
		return PAST_DUE_LETTER_3;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_LETTER_4() {
		return PAST_DUE_LETTER_4;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_LETTER_5() {
		return PAST_DUE_LETTER_5;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_LETTER_6() {
		return PAST_DUE_LETTER_6;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_LETTER_7() {
		return PAST_DUE_LETTER_7;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_NOTICE_1() {
		return PAST_DUE_NOTICE_1;
	}

	/**
	 * @return
	 */
	public String getPAST_DUE_NOTICE_2() {
		return PAST_DUE_NOTICE_2;
	}

	/**
	 * @return
	 */
	public String getPAYMENT_REV_T_CYCL() {
		return PAYMENT_REV_T_CYCL;
	}

	/**
	 * @return
	 */
	public String getPAYMENT_THIS_CYCLE() {
		return PAYMENT_THIS_CYCLE;
	}

	/**
	 * @return
	 */
	public String getPD_FULL_BEF_PAYDUE() {
		return PD_FULL_BEF_PAYDUE;
	}

	/**
	 * @return
	 */
	public String getPD_NOTC_1_SENT() {
		return PD_NOTC_1_SENT;
	}

	/**
	 * @return
	 */
	public String getPD_NOTC_2_SENT() {
		return PD_NOTC_2_SENT;
	}

	/**
	 * @return
	 */
	public String getPD_NOTC_3_SENT() {
		return PD_NOTC_3_SENT;
	}

	/**
	 * @return
	 */
	public String getPERSONAL_RFC() {
		return PERSONAL_RFC;
	}

	/**
	 * @return
	 */
	public String getPIN_REQUEST_FLAG() {
		return PIN_REQUEST_FLAG;
	}

	/**
	 * @return
	 */
	public String getPLACE_OF_EMPLOYMEN() {
		return PLACE_OF_EMPLOYMEN;
	}

	/**
	 * @return
	 */
	public String getPOINTS_USED() {
		return POINTS_USED;
	}

	/**
	 * @return
	 */
	public String getPREV_CASHADV_BAL() {
		return PREV_CASHADV_BAL;
	}

	/**
	 * @return
	 */
	public String getPREV_CASHADV_FINCH() {
		return PREV_CASHADV_FINCH;
	}

	/**
	 * @return
	 */
	public String getPREV_MISC_OUTSTAND() {
		return PREV_MISC_OUTSTAND;
	}

	/**
	 * @return
	 */
	public String getPREV_POINT_BAL() {
		return PREV_POINT_BAL;
	}

	/**
	 * @return
	 */
	public String getPREV_PURCHASE_BAL() {
		return PREV_PURCHASE_BAL;
	}

	/**
	 * @return
	 */
	public String getPREV_STATE_ACT_DUE() {
		return PREV_STATE_ACT_DUE;
	}

	/**
	 * @return
	 */
	public String getPREV_STATE_BAL() {
		return PREV_STATE_BAL;
	}

	/**
	 * @return
	 */
	public String getPREV_STATE_INS_CHG() {
		return PREV_STATE_INS_CHG;
	}

	/**
	 * @return
	 */
	public String getPREV_STATE_LATECHG() {
		return PREV_STATE_LATECHG;
	}

	/**
	 * @return
	 */
	public String getPREVPUR_FINCHG_OUT() {
		return PREVPUR_FINCHG_OUT;
	}

	/**
	 * @return
	 */
	public String getPRIR_LAST_MAINT_DT() {
		return PRIR_LAST_MAINT_DT;
	}

	/**
	 * @return
	 */
	public String getPSTDU_MANUALY_CHGD() {
		return PSTDU_MANUALY_CHGD;
	}

	/**
	 * @return
	 */
	public String getPURGE_DATE_FOR_CRB() {
		return PURGE_DATE_FOR_CRB;
	}

	/**
	 * @return
	 */
	public String getPURGE_DATE_FOR_RCL() {
		return PURGE_DATE_FOR_RCL;
	}

	/**
	 * @return
	 */
	public String getQTRLY_PMT_MO_COUNT() {
		return QTRLY_PMT_MO_COUNT;
	}

	/**
	 * @return
	 */
	public String getRATE_CODE_CH_COMPL() {
		return RATE_CODE_CH_COMPL;
	}

	/**
	 * @return
	 */
	public String getRATECD_CA_OV1U2() {
		return RATECD_CA_OV1U2;
	}

	/**
	 * @return
	 */
	public String getRATECD_CA_OVR2() {
		return RATECD_CA_OVR2;
	}

	/**
	 * @return
	 */
	public String getRATECD_CA_UND1() {
		return RATECD_CA_UND1;
	}

	/**
	 * @return
	 */
	public String getRATECD_OVR_BP2() {
		return RATECD_OVR_BP2;
	}

	/**
	 * @return
	 */
	public String getRATECD_OVR1_U2() {
		return RATECD_OVR1_U2;
	}

	/**
	 * @return
	 */
	public String getRATECD_UND_BP1() {
		return RATECD_UND_BP1;
	}

	/**
	 * @return
	 */
	public String getRATECODE_CSHADV_FE() {
		return RATECODE_CSHADV_FE;
	}

	/**
	 * @return
	 */
	public String getRATECODE_LATECHG_F() {
		return RATECODE_LATECHG_F;
	}

	/**
	 * @return
	 */
	public String getRATECODE_MINPAYMNT() {
		return RATECODE_MINPAYMNT;
	}

	/**
	 * @return
	 */
	public String getREASON_ON_RCL() {
		return REASON_ON_RCL;
	}

	/**
	 * @return
	 */
	public String getREG_CODE_FOR_RCL() {
		return REG_CODE_FOR_RCL;
	}

	/**
	 * @return
	 */
	public String getREISSU_CRD_PRINTED() {
		return REISSU_CRD_PRINTED;
	}

	/**
	 * @return
	 */
	public String getREISSUE_DAY() {
		return REISSUE_DAY;
	}

	/**
	 * @return
	 */
	public String getRESTRICTED_CARD_LS() {
		return RESTRICTED_CARD_LS;
	}

	/**
	 * @return
	 */
	public String getRET_CHECK_THIS_CYC() {
		return RET_CHECK_THIS_CYC;
	}

	/**
	 * @return
	 */
	public String getRFC_GUARANTOR1() {
		return RFC_GUARANTOR1;
	}

	/**
	 * @return
	 */
	public String getRFC_GUARANTOR2() {
		return RFC_GUARANTOR2;
	}

	/**
	 * @return
	 */
	public String getRFC_GUARANTOR3() {
		return RFC_GUARANTOR3;
	}

	/**
	 * @return
	 */
	public String getSALES_DRAFT_REV_CY() {
		return SALES_DRAFT_REV_CY;
	}

	/**
	 * @return
	 */
	public String getSALES_DRFT_TH_CYCL() {
		return SALES_DRFT_TH_CYCL;
	}

	/**
	 * @return
	 */
	public String getSAV_ACCT_NUMBER() {
		return SAV_ACCT_NUMBER;
	}

	/**
	 * @return
	 */
	public String getSENT_TO_MC_TRAV() {
		return SENT_TO_MC_TRAV;
	}

	/**
	 * @return
	 */
	public String getSHORT_NAME() {
		return SHORT_NAME;
	}

	/**
	 * @return
	 */
	public String getSKIP_PAYMENT_STATE() {
		return SKIP_PAYMENT_STATE;
	}

	/**
	 * @return
	 */
	public String getSPOUSE_NAME() {
		return SPOUSE_NAME;
	}

	/**
	 * @return
	 */
	public String getSPOUSE_RESP_CODE() {
		return SPOUSE_RESP_CODE;
	}

	/**
	 * @return
	 */
	public String getSPOUSE_RESP_FR_ACT() {
		return SPOUSE_RESP_FR_ACT;
	}

	/**
	 * @return
	 */
	public String getSSN_FOR_NAME_LINE1() {
		return SSN_FOR_NAME_LINE1;
	}

	/**
	 * @return
	 */
	public String getSTATE_ABBRE_ADDLN1() {
		return STATE_ABBRE_ADDLN1;
	}

	/**
	 * @return
	 */
	public String getSTATE_CD_FOR_ACCT() {
		return STATE_CD_FOR_ACCT;
	}

	/**
	 * @return
	 */
	public String getSTATE_PROD_THIS_CY() {
		return STATE_PROD_THIS_CY;
	}

	/**
	 * @return
	 */
	public String getSTATEMENT_HOLD_CDE() {
		return STATEMENT_HOLD_CDE;
	}

	/**
	 * @return
	 */
	public String getSTATUS() {
		return STATUS;
	}

	/**
	 * @return
	 */
	public String getSTR_CRD_ALPHA_PIN() {
		return STR_CRD_ALPHA_PIN;
	}

	/**
	 * @return
	 */
	public String getSTRIPE_WAS_READ() {
		return STRIPE_WAS_READ;
	}

	/**
	 * @return
	 */
	public String getSUPP_AIR_INS_ACT_M() {
		return SUPP_AIR_INS_ACT_M;
	}

	/**
	 * @return
	 */
	public String getSYS_GENER_PIN() {
		return SYS_GENER_PIN;
	}

	/**
	 * @return
	 */
	public String getTAXID() {
		return TAXID;
	}

	/**
	 * @return
	 */
	public String getTHREE_CASHADV_RATECODE() {
		return THREE_CASHADV_RATECODE;
	}

	/**
	 * @return
	 */
	public String getTHREE_PURCHASE_RT_CDS() {
		return THREE_PURCHASE_RT_CDS;
	}

	/**
	 * @return
	 */
	public String getTIMES_AUTO_PASTDUE() {
		return TIMES_AUTO_PASTDUE;
	}

	/**
	 * @return
	 */
	public String getTIMES_INS_CAL_OPN() {
		return TIMES_INS_CAL_OPN;
	}

	/**
	 * @return
	 */
	public String getTIMES_PD_CYCLE() {
		return TIMES_PD_CYCLE;
	}

	/**
	 * @return
	 */
	public String getTIMES_PSTDUE_CHGMN() {
		return TIMES_PSTDUE_CHGMN;
	}

	/**
	 * @return
	 */
	public String getTRANSFER_DATE() {
		return TRANSFER_DATE;
	}

	/**
	 * @return
	 */
	public String getTRANSFER_PROC_COMP() {
		return TRANSFER_PROC_COMP;
	}

	/**
	 * @return
	 */
	public String getTRANSFER_REASON() {
		return TRANSFER_REASON;
	}

	/**
	 * @return
	 */
	public String getTRANSFER_STATUS() {
		return TRANSFER_STATUS;
	}

	/**
	 * @return
	 */
	public String getTRANSIT_ROUTING_NB() {
		return TRANSIT_ROUTING_NB;
	}

	/**
	 * @return
	 */
	public String getTRNACCT_NR_OLD_NW() {
		return TRNACCT_NR_OLD_NW;
	}

	/**
	 * @return
	 */
	public String getTST_CBR_ISS_CREXC() {
		return TST_CBR_ISS_CREXC;
	}

	/**
	 * @return
	 */
	public String getTYPE_AUTO_DEDUCTIO() {
		return TYPE_AUTO_DEDUCTIO;
	}

	/**
	 * @return
	 */
	public String getTYPE_CODE() {
		return TYPE_CODE;
	}

	/**
	 * @return
	 */
	public String getTYPE_OF_LIMIT_CHG() {
		return TYPE_OF_LIMIT_CHG;
	}

	/**
	 * @return
	 */
	public String getUSER_ACCT_1() {
		return USER_ACCT_1;
	}

	/**
	 * @return
	 */
	public String getUSER_ACCT_2() {
		return USER_ACCT_2;
	}

	/**
	 * @return
	 */
	public String getUSER_ACCT_3() {
		return USER_ACCT_3;
	}

	/**
	 * @return
	 */
	public String getUSER_CODE_1() {
		return USER_CODE_1;
	}

	/**
	 * @return
	 */
	public String getUSER_CODE_2_POS0() {
		return USER_CODE_2_POS0;
	}

	/**
	 * @return
	 */
	public String getUSER_CODE_2_POS1() {
		return USER_CODE_2_POS1;
	}

	/**
	 * @return
	 */
	public String getUSER_CODE_2_POS2() {
		return USER_CODE_2_POS2;
	}

	/**
	 * @return
	 */
	public String getUSER_CODE_2_POS3() {
		return USER_CODE_2_POS3;
	}

	/**
	 * @return
	 */
	public String getUSER_CODE_3() {
		return USER_CODE_3;
	}

	/**
	 * @return
	 */
	public String getUTH_USER1_HASCARD() {
		return UTH_USER1_HASCARD;
	}

	/**
	 * @return
	 */
	public String getZERO_BALANCE_YESTE() {
		return ZERO_BALANCE_YESTE;
	}

	/**
	 * @return
	 */
	public String getZIP4_IS_VALID() {
		return ZIP4_IS_VALID;
	}

	/**
	 * @return
	 */
	public String getZIP4_NOT_CONVERT() {
		return ZIP4_NOT_CONVERT;
	}

	/**
	 * @return
	 */
	public String getZIPCODE_CARDHOLDER() {
		return ZIPCODE_CARDHOLDER;
	}

	/**
	 * @param string
	 */
	public void setACCOUNT_CYCLE(String string) {
		ACCOUNT_CYCLE = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_ACT_PRIR_YEAR(String string) {
		ACCT_ACT_PRIR_YEAR = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_ACT_SINCE_OPE(String string) {
		ACCT_ACT_SINCE_OPE = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_ACT_THIS_MONT(String string) {
		ACCT_ACT_THIS_MONT = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_ACTIVE_T_YEAR(String string) {
		ACCT_ACTIVE_T_YEAR = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_ACTIVE_TODAY(String string) {
		ACCT_ACTIVE_TODAY = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_DELETE_STATUS(String string) {
		ACCT_DELETE_STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_DNLD_FROM_SSI(String string) {
		ACCT_DNLD_FROM_SSI = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_HAS_VISA_PHONE(String string) {
		ACCT_HAS_VISA_PHONE = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_IN_CRB_STAT(String string) {
		ACCT_IN_CRB_STAT = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_IN_DISPUTE(String string) {
		ACCT_IN_DISPUTE = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_NEW_TO_SSI_RM(String string) {
		ACCT_NEW_TO_SSI_RM = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_PENDING_XFR(String string) {
		ACCT_PENDING_XFR = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_REISSUED_TH_M(String string) {
		ACCT_REISSUED_TH_M = string;
	}

	/**
	 * @param string
	 */
	public void setACCT_TYPE_EXCEP(String string) {
		ACCT_TYPE_EXCEP = string;
	}

	/**
	 * @param string
	 */
	public void setACCUM_POINTS(String string) {
		ACCUM_POINTS = string;
	}

	/**
	 * @param string
	 */
	public void setACE_STATUS_BYTE(String string) {
		ACE_STATUS_BYTE = string;
	}

	/**
	 * @param string
	 */
	public void setACT_REISSUE_RATING(String string) {
		ACT_REISSUE_RATING = string;
	}

	/**
	 * @param string
	 */
	public void setACT_TODAY_EXCEP(String string) {
		ACT_TODAY_EXCEP = string;
	}

	/**
	 * @param string
	 */
	public void setADDR_CHG_S_BUREAU(String string) {
		ADDR_CHG_S_BUREAU = string;
	}

	/**
	 * @param string
	 */
	public void setADDRESS_CITY(String string) {
		ADDRESS_CITY = string;
	}

	/**
	 * @param string
	 */
	public void setADDRESS_LINE_1(String string) {
		ADDRESS_LINE_1 = string;
	}

	/**
	 * @param string
	 */
	public void setADDRESS_LINE_2(String string) {
		ADDRESS_LINE_2 = string;
	}

	/**
	 * @param string
	 */
	public void setAFFIN_DUES_CG_PREV(String string) {
		AFFIN_DUES_CG_PREV = string;
	}

	/**
	 * @param string
	 */
	public void setAFFIN_DUES_FIRST_C(String string) {
		AFFIN_DUES_FIRST_C = string;
	}

	/**
	 * @param string
	 */
	public void setAGENT(String string) {
		AGENT = string;
	}

	/**
	 * @param string
	 */
	public void setAGENT_BANK_NAME(String string) {
		AGENT_BANK_NAME = string;
	}

	/**
	 * @param string
	 */
	public void setAMT_CUR_IN_DISP(String string) {
		AMT_CUR_IN_DISP = string;
	}

	/**
	 * @param string
	 */
	public void setAMT_LAST_ANNUAL_FE(String string) {
		AMT_LAST_ANNUAL_FE = string;
	}

	/**
	 * @param string
	 */
	public void setANNUAL_FEE_FLAG(String string) {
		ANNUAL_FEE_FLAG = string;
	}

	/**
	 * @param string
	 */
	public void setANNUAL_FEE_RATECDE(String string) {
		ANNUAL_FEE_RATECDE = string;
	}

	/**
	 * @param string
	 */
	public void setANY_TYPE_LAST_ACTV(String string) {
		ANY_TYPE_LAST_ACTV = string;
	}

	/**
	 * @param string
	 */
	public void setATM_XFER_TODAY_ACC(String string) {
		ATM_XFER_TODAY_ACC = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_NCNB_SEC_COMP(String string) {
		AUTH_NCNB_SEC_COMP = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_NCNB_SEC_ON(String string) {
		AUTH_NCNB_SEC_ON = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_OS_EXCEP(String string) {
		AUTH_OS_EXCEP = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_USER_NAME1(String string) {
		AUTH_USER_NAME1 = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_USER_NAME2(String string) {
		AUTH_USER_NAME2 = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_USER_NAME3(String string) {
		AUTH_USER_NAME3 = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_USER2_HASCARD(String string) {
		AUTH_USER2_HASCARD = string;
	}

	/**
	 * @param string
	 */
	public void setAUTH_USER3_HASCARD(String string) {
		AUTH_USER3_HASCARD = string;
	}

	/**
	 * @param string
	 */
	public void setAUTO_LIM_INCR_TH_M(String string) {
		AUTO_LIM_INCR_TH_M = string;
	}

	/**
	 * @param string
	 */
	public void setAUTO_LTR_1(String string) {
		AUTO_LTR_1 = string;
	}

	/**
	 * @param string
	 */
	public void setAUTO_LTR_2(String string) {
		AUTO_LTR_2 = string;
	}

	/**
	 * @param string
	 */
	public void setAUTO_LTR_3(String string) {
		AUTO_LTR_3 = string;
	}

	/**
	 * @param string
	 */
	public void setAUTO_LTR_4(String string) {
		AUTO_LTR_4 = string;
	}

	/**
	 * @param string
	 */
	public void setAUTO_MAN_CBR(String string) {
		AUTO_MAN_CBR = string;
	}

	/**
	 * @param string
	 */
	public void setBAL_CHARGED_OFF(String string) {
		BAL_CHARGED_OFF = string;
	}

	/**
	 * @param string
	 */
	public void setBANK_53(String string) {
		BANK_53 = string;
	}

	/**
	 * @param string
	 */
	public void setBANK_NUMBER(String string) {
		BANK_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setBANK_SPEC2_IND(String string) {
		BANK_SPEC2_IND = string;
	}

	/**
	 * @param string
	 */
	public void setBANK_SPECIFIC_IND(String string) {
		BANK_SPECIFIC_IND = string;
	}

	/**
	 * @param string
	 */
	public void setBEHAVORIAL_SCORE(String string) {
		BEHAVORIAL_SCORE = string;
	}

	/**
	 * @param string
	 */
	public void setBILL_FEDX_CARD_DE(String string) {
		BILL_FEDX_CARD_DE = string;
	}

	/**
	 * @param string
	 */
	public void setBONUS_POINT_CDATE(String string) {
		BONUS_POINT_CDATE = string;
	}

	/**
	 * @param string
	 */
	public void setBRANCH(String string) {
		BRANCH = string;
	}

	/**
	 * @param string
	 */
	public void setBUSINESS_LOGO_FLD(String string) {
		BUSINESS_LOGO_FLD = string;
	}

	/**
	 * @param string
	 */
	public void setBUSINESS_PHONE_NUMBER(String string) {
		BUSINESS_PHONE_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setBUSN_EXT(String string) {
		BUSN_EXT = string;
	}

	/**
	 * @param string
	 */
	public void setCAN_CHG_MBRSHIP_FE(String string) {
		CAN_CHG_MBRSHIP_FE = string;
	}

	/**
	 * @param string
	 */
	public void setCAN_CHG_OVRLMT_FEE(String string) {
		CAN_CHG_OVRLMT_FEE = string;
	}

	/**
	 * @param string
	 */
	public void setCARD_ACTIVATION_ST(String string) {
		CARD_ACTIVATION_ST = string;
	}

	/**
	 * @param string
	 */
	public void setCARD_REQ_DEL_TOD(String string) {
		CARD_REQ_DEL_TOD = string;
	}

	/**
	 * @param string
	 */
	public void setCARD_REQ_TODAY(String string) {
		CARD_REQ_TODAY = string;
	}

	/**
	 * @param string
	 */
	public void setCARD_USE_REPORTED(String string) {
		CARD_USE_REPORTED = string;
	}

	/**
	 * @param string
	 */
	public void setCARDS_OUTSTANDING(String string) {
		CARDS_OUTSTANDING = string;
	}

	/**
	 * @param string
	 */
	public void setCARDS_TOBE_REISSUE(String string) {
		CARDS_TOBE_REISSUE = string;
	}

	/**
	 * @param string
	 */
	public void setCASH_ADV_THIS_CYC(String string) {
		CASH_ADV_THIS_CYC = string;
	}

	/**
	 * @param string
	 */
	public void setCASHADV_REV_TH_CYC(String string) {
		CASHADV_REV_TH_CYC = string;
	}

	/**
	 * @param string
	 */
	public void setCELL_PHONE(String string) {
		CELL_PHONE = string;
	}

	/**
	 * @param string
	 */
	public void setCH_BIRTH_DATE(String string) {
		CH_BIRTH_DATE = string;
	}

	/**
	 * @param string
	 */
	public void setCHANGE_IN_TERM_DT(String string) {
		CHANGE_IN_TERM_DT = string;
	}

	/**
	 * @param string
	 */
	public void setCHARGEOFF_PROC_COM(String string) {
		CHARGEOFF_PROC_COM = string;
	}

	/**
	 * @param string
	 */
	public void setCHARGEOFF_REASON(String string) {
		CHARGEOFF_REASON = string;
	}

	/**
	 * @param string
	 */
	public void setCHARGEOFF_STATUS(String string) {
		CHARGEOFF_STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setCHECK_OK_CARD(String string) {
		CHECK_OK_CARD = string;
	}

	/**
	 * @param string
	 */
	public void setCHG_IN_TERMS_COMP(String string) {
		CHG_IN_TERMS_COMP = string;
	}

	/**
	 * @param string
	 */
	public void setCHG_TAX_THIS_CYCLE(String string) {
		CHG_TAX_THIS_CYCLE = string;
	}

	/**
	 * @param string
	 */
	public void setCITY_STATE_COMBINE(String string) {
		CITY_STATE_COMBINE = string;
	}

	/**
	 * @param string
	 */
	public void setCO_STMT_FLAG(String string) {
		CO_STMT_FLAG = string;
	}

	/**
	 * @param string
	 */
	public void setCOLL_CARD_SENT(String string) {
		COLL_CARD_SENT = string;
	}

	/**
	 * @param string
	 */
	public void setCOMB_DET_STATEMENT(String string) {
		COMB_DET_STATEMENT = string;
	}

	/**
	 * @param string
	 */
	public void setCOMB_MASTER_STATEM(String string) {
		COMB_MASTER_STATEM = string;
	}

	/**
	 * @param string
	 */
	public void setCORP_ACCT_NUMBER(String string) {
		CORP_ACCT_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setCRB_REASON(String string) {
		CRB_REASON = string;
	}

	/**
	 * @param string
	 */
	public void setCRB_REGION_CODE(String string) {
		CRB_REGION_CODE = string;
	}

	/**
	 * @param string
	 */
	public void setCRED_ACTVITY_TODAY(String string) {
		CRED_ACTVITY_TODAY = string;
	}

	/**
	 * @param string
	 */
	public void setCRED_LIM_INC_LET(String string) {
		CRED_LIM_INC_LET = string;
	}

	/**
	 * @param string
	 */
	public void setCRED_REV_THIS_CYCL(String string) {
		CRED_REV_THIS_CYCL = string;
	}

	/**
	 * @param string
	 */
	public void setCREDIT_RATING(String string) {
		CREDIT_RATING = string;
	}

	/**
	 * @param string
	 */
	public void setCREDIT_SCORE(String string) {
		CREDIT_SCORE = string;
	}

	/**
	 * @param string
	 */
	public void setCREDIT_THIS_CYCLE(String string) {
		CREDIT_THIS_CYCLE = string;
	}

	/**
	 * @param string
	 */
	public void setCREDITBUREA_REPORT(String string) {
		CREDITBUREA_REPORT = string;
	}

	/**
	 * @param string
	 */
	public void setCUR_ACCT_COL_STAT(String string) {
		CUR_ACCT_COL_STAT = string;
	}

	/**
	 * @param string
	 */
	public void setCURP_ID(String string) {
		CURP_ID = string;
	}

	/**
	 * @param string
	 */
	public void setCURR_POINT_BAL(String string) {
		CURR_POINT_BAL = string;
	}

	/**
	 * @param string
	 */
	public void setCURRENTLY_OVRLIMIT(String string) {
		CURRENTLY_OVRLIMIT = string;
	}

	/**
	 * @param string
	 */
	public void setCUST_SELECTED_PIN(String string) {
		CUST_SELECTED_PIN = string;
	}

	/**
	 * @param string
	 */
	public void setDAT_LAST_ON_CRB(String string) {
		DAT_LAST_ON_CRB = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_ACCT_OPENNED(String string) {
		DATE_ACCT_OPENNED = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_CARD_1ST_USED(String string) {
		DATE_CARD_1ST_USED = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_CHARGE_OFF(String string) {
		DATE_CHARGE_OFF = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_FIX_PAYAMT_CH(String string) {
		DATE_FIX_PAYAMT_CH = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_FIXED_PAY_STR(String string) {
		DATE_FIXED_PAY_STR = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_HIGH_BAL_REAC(String string) {
		DATE_HIGH_BAL_REAC = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAST_ANNFEE_C(String string) {
		DATE_LAST_ANNFEE_C = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAST_CASH_ADV(String string) {
		DATE_LAST_CASH_ADV = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAST_LATE_CHG(String string) {
		DATE_LAST_LATE_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAST_LIM_CHG(String string) {
		DATE_LAST_LIM_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAST_ON_RCL(String string) {
		DATE_LAST_ON_RCL = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAST_PAST_DUE(String string) {
		DATE_LAST_PAST_DUE = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAST_PURCHASE(String string) {
		DATE_LAST_PURCHASE = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LAT_NMADDR_MN(String string) {
		DATE_LAT_NMADDR_MN = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_LST_OL_SNT(String string) {
		DATE_LST_OL_SNT = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_NOTC_PRT_CHG(String string) {
		DATE_NOTC_PRT_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_OF_LAST_LETTR(String string) {
		DATE_OF_LAST_LETTR = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_OF_PIN_REQUEST(String string) {
		DATE_OF_PIN_REQUEST = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_PAYMENT_DUE(String string) {
		DATE_PAYMENT_DUE = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_SENT_CREDBURU(String string) {
		DATE_SENT_CREDBURU = string;
	}

	/**
	 * @param string
	 */
	public void setDATE_TO_CHG_ANN_FE(String string) {
		DATE_TO_CHG_ANN_FE = string;
	}

	/**
	 * @param string
	 */
	public void setDAYS_IN_CRED_BAL(String string) {
		DAYS_IN_CRED_BAL = string;
	}

	/**
	 * @param string
	 */
	public void setDAYS_OVERLIMIT(String string) {
		DAYS_OVERLIMIT = string;
	}

	/**
	 * @param string
	 */
	public void setDAYS_PAST_DUE(String string) {
		DAYS_PAST_DUE = string;
	}

	/**
	 * @param string
	 */
	public void setDDA_ACCNT_NUMBER(String string) {
		DDA_ACCNT_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setDEB_ACTIVITY_TODAY(String string) {
		DEB_ACTIVITY_TODAY = string;
	}

	/**
	 * @param string
	 */
	public void setDEBIT_CRDLIM_UPD_T(String string) {
		DEBIT_CRDLIM_UPD_T = string;
	}

	/**
	 * @param string
	 */
	public void setDEF_PMT_SCHD_CNTDN(String string) {
		DEF_PMT_SCHD_CNTDN = string;
	}

	/**
	 * @param string
	 */
	public void setDEF_PMT_SCHD_REFRE(String string) {
		DEF_PMT_SCHD_REFRE = string;
	}

	/**
	 * @param string
	 */
	public void setDEL_TRADE_FM_BUREA(String string) {
		DEL_TRADE_FM_BUREA = string;
	}

	/**
	 * @param string
	 */
	public void setDELETE_PROC_COMP(String string) {
		DELETE_PROC_COMP = string;
	}

	/**
	 * @param string
	 */
	public void setDIEBOLD_ENTRY_NBR(String string) {
		DIEBOLD_ENTRY_NBR = string;
	}

	/**
	 * @param string
	 */
	public void setDIEBOLD_OFFSET_PIN(String string) {
		DIEBOLD_OFFSET_PIN = string;
	}

	/**
	 * @param string
	 */
	public void setDIEBOLD_WITHDR_LMT(String string) {
		DIEBOLD_WITHDR_LMT = string;
	}

	/**
	 * @param string
	 */
	public void setDOCUTEL_PIN_NUMBER(String string) {
		DOCUTEL_PIN_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setDONT_PRINT_OVERLMT(String string) {
		DONT_PRINT_OVERLMT = string;
	}

	/**
	 * @param string
	 */
	public void setDONT_PRINT_PAST_DU(String string) {
		DONT_PRINT_PAST_DU = string;
	}

	/**
	 * @param string
	 */
	public void setDONT_PRNT_OVLM_MSG(String string) {
		DONT_PRNT_OVLM_MSG = string;
	}

	/**
	 * @param string
	 */
	public void setDONT_PRNT_PAST_MSG(String string) {
		DONT_PRNT_PAST_MSG = string;
	}

	/**
	 * @param string
	 */
	public void setDONT_PRNT_STATEMNT(String string) {
		DONT_PRNT_STATEMNT = string;
	}

	/**
	 * @param string
	 */
	public void setDUAL_VISA_MAST_PL(String string) {
		DUAL_VISA_MAST_PL = string;
	}

	/**
	 * @param string
	 */
	public void setEMAIL(String string) {
		EMAIL = string;
	}

	/**
	 * @param string
	 */
	public void setEMBOSS_NAME(String string) {
		EMBOSS_NAME = string;
	}

	/**
	 * @param string
	 */
	public void setENCODE_ALL_ACCTS(String string) {
		ENCODE_ALL_ACCTS = string;
	}

	/**
	 * @param string
	 */
	public void setEXP_DT_CUR_CARD(String string) {
		EXP_DT_CUR_CARD = string;
	}

	/**
	 * @param string
	 */
	public void setEXPDATE_BEF_REISSU(String string) {
		EXPDATE_BEF_REISSU = string;
	}

	/**
	 * @param string
	 */
	public void setEXPIRED_POINTS(String string) {
		EXPIRED_POINTS = string;
	}

	/**
	 * @param string
	 */
	public void setEXT_SEG_1_ON_FILE(String string) {
		EXT_SEG_1_ON_FILE = string;
	}

	/**
	 * @param string
	 */
	public void setEXT_TRNACCT_NR_OLD(String string) {
		EXT_TRNACCT_NR_OLD = string;
	}

	/**
	 * @param string
	 */
	public void setFC_PREC_4_DEC(String string) {
		FC_PREC_4_DEC = string;
	}

	/**
	 * @param string
	 */
	public void setFIN_CHARGE_CYCLE(String string) {
		FIN_CHARGE_CYCLE = string;
	}

	/**
	 * @param string
	 */
	public void setFIXED_PAYMENT_AMT(String string) {
		FIXED_PAYMENT_AMT = string;
	}

	/**
	 * @param string
	 */
	public void setFRAUD_COUNTERFEIT(String string) {
		FRAUD_COUNTERFEIT = string;
	}

	/**
	 * @param string
	 */
	public void setFRAUD_MESSAGE_IND(String string) {
		FRAUD_MESSAGE_IND = string;
	}

	/**
	 * @param string
	 */
	public void setFREE_TEXT_STATUS(String string) {
		FREE_TEXT_STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setGUARANTOR1(String string) {
		GUARANTOR1 = string;
	}

	/**
	 * @param string
	 */
	public void setGUARANTOR2(String string) {
		GUARANTOR2 = string;
	}

	/**
	 * @param string
	 */
	public void setGUARANTOR3(String string) {
		GUARANTOR3 = string;
	}

	/**
	 * @param string
	 */
	public void setHIGH_BAL_ON_ACCT(String string) {
		HIGH_BAL_ON_ACCT = string;
	}

	/**
	 * @param string
	 */
	public void setHOLD_PLASTIC(String string) {
		HOLD_PLASTIC = string;
	}

	/**
	 * @param string
	 */
	public void setHOME_PHONE_NUMBER(String string) {
		HOME_PHONE_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setIDS_FILL8(String string) {
		IDS_FILL8 = string;
	}

	/**
	 * @param string
	 */
	public void setIMMUNE_AT_CYCLE(String string) {
		IMMUNE_AT_CYCLE = string;
	}

	/**
	 * @param string
	 */
	public void setINDICATE_CASH_IHB(String string) {
		INDICATE_CASH_IHB = string;
	}

	/**
	 * @param string
	 */
	public void setINDICATE_PURCH_IHB(String string) {
		INDICATE_PURCH_IHB = string;
	}

	/**
	 * @param string
	 */
	public void setINS_LOAN_ACCT_NBR(String string) {
		INS_LOAN_ACCT_NBR = string;
	}

	/**
	 * @param string
	 */
	public void setINS_RATE_CODE(String string) {
		INS_RATE_CODE = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL_0(String string) {
		ISD_FILL_0 = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL_8(String string) {
		ISD_FILL_8 = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL_T(String string) {
		ISD_FILL_T = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL3(String string) {
		ISD_FILL3 = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL4(String string) {
		ISD_FILL4 = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL5(String string) {
		ISD_FILL5 = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL6(String string) {
		ISD_FILL6 = string;
	}

	/**
	 * @param string
	 */
	public void setISD_FILL7(String string) {
		ISD_FILL7 = string;
	}

	/**
	 * @param string
	 */
	public void setISSUE_BANK(String string) {
		ISSUE_BANK = string;
	}

	/**
	 * @param string
	 */
	public void setJVTYPE(String string) {
		JVTYPE = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_AUTO_PASTDUE(String string) {
		LAST_AUTO_PASTDUE = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_CRD_RATE_CHG(String string) {
		LAST_CRD_RATE_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_CRED_ON_ACCT(String string) {
		LAST_CRED_ON_ACCT = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_CRED_RPT_CHCK(String string) {
		LAST_CRED_RPT_CHCK = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_DISPUTE_DATE(String string) {
		LAST_DISPUTE_DATE = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_DISPUTE_SETLD(String string) {
		LAST_DISPUTE_SETLD = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_DT_SC_CHG(String string) {
		LAST_DT_SC_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_LIMIT_INC_DEC(String string) {
		LAST_LIMIT_INC_DEC = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_MAG_MAINT(String string) {
		LAST_MAG_MAINT = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_MAINT_DATE(String string) {
		LAST_MAINT_DATE = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_PASTDUE_NOTIC(String string) {
		LAST_PASTDUE_NOTIC = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_PAYMENT_AMT(String string) {
		LAST_PAYMENT_AMT = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_PAYMENT_DATE(String string) {
		LAST_PAYMENT_DATE = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_STATEMENT_DT(String string) {
		LAST_STATEMENT_DT = string;
	}

	/**
	 * @param string
	 */
	public void setLAST_TRANSACT_DATE(String string) {
		LAST_TRANSACT_DATE = string;
	}

	/**
	 * @param string
	 */
	public void setLETTER_REQ_DEL_TOD(String string) {
		LETTER_REQ_DEL_TOD = string;
	}

	/**
	 * @param string
	 */
	public void setLETTER_REQ_TODAY(String string) {
		LETTER_REQ_TODAY = string;
	}

	/**
	 * @param string
	 */
	public void setLONG_NAME(String string) {
		LONG_NAME = string;
	}

	/**
	 * @param string
	 */
	public void setLOST_ACCT_STATUS(String string) {
		LOST_ACCT_STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setLST_PASTDUE_MAN_CH(String string) {
		LST_PASTDUE_MAN_CH = string;
	}

	/**
	 * @param string
	 */
	public void setMANUAL_OVRRIDE_CBR(String string) {
		MANUAL_OVRRIDE_CBR = string;
	}

	/**
	 * @param string
	 */
	public void setMETHOD_FOR_AUTO_DE(String string) {
		METHOD_FOR_AUTO_DE = string;
	}

	/**
	 * @param string
	 */
	public void setMISC_USER1(String string) {
		MISC_USER1 = string;
	}

	/**
	 * @param string
	 */
	public void setMONTH_INS_DUE(String string) {
		MONTH_INS_DUE = string;
	}

	/**
	 * @param string
	 */
	public void setNAME_LINE1_PRIM_AP(String string) {
		NAME_LINE1_PRIM_AP = string;
	}

	/**
	 * @param string
	 */
	public void setNAME_LINE2_ADDED(String string) {
		NAME_LINE2_ADDED = string;
	}

	/**
	 * @param string
	 */
	public void setNAME_LINE2_COAPPL(String string) {
		NAME_LINE2_COAPPL = string;
	}

	/**
	 * @param string
	 */
	public void setNAME_LNE1_CHANGE(String string) {
		NAME_LNE1_CHANGE = string;
	}

	/**
	 * @param string
	 */
	public void setNAME_LNE2_CHANGE(String string) {
		NAME_LNE2_CHANGE = string;
	}

	/**
	 * @param string
	 */
	public void setNBR_TIMES_LIM_CHGD(String string) {
		NBR_TIMES_LIM_CHGD = string;
	}

	/**
	 * @param string
	 */
	public void setNEW_ACCT_1ST_BBAL(String string) {
		NEW_ACCT_1ST_BBAL = string;
	}

	/**
	 * @param string
	 */
	public void setNON_ACC_STATUS(String string) {
		NON_ACC_STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setNUM_OF_STATEMENTS(String string) {
		NUM_OF_STATEMENTS = string;
	}

	/**
	 * @param string
	 */
	public void setNUM_OF_TM_CD_BAL(String string) {
		NUM_OF_TM_CD_BAL = string;
	}

	/**
	 * @param string
	 */
	public void setNUM_OL_NOTICES(String string) {
		NUM_OL_NOTICES = string;
	}

	/**
	 * @param string
	 */
	public void setNUM_TIMES_DISPUTE(String string) {
		NUM_TIMES_DISPUTE = string;
	}

	/**
	 * @param string
	 */
	public void setNWB_ACCT_LISTED(String string) {
		NWB_ACCT_LISTED = string;
	}

	/**
	 * @param string
	 */
	public void setNWB_STATUS(String string) {
		NWB_STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setOFCR_APP_NOTC_CHG(String string) {
		OFCR_APP_NOTC_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setOFCR_APP_PT_DU_CH(String string) {
		OFCR_APP_PT_DU_CH = string;
	}

	/**
	 * @param string
	 */
	public void setOFCR_APPR_FIXED_P(String string) {
		OFCR_APPR_FIXED_P = string;
	}

	/**
	 * @param string
	 */
	public void setOFCR_APPR_LST_DIS(String string) {
		OFCR_APPR_LST_DIS = string;
	}

	/**
	 * @param string
	 */
	public void setOFCR_APPV_LIMT_CHG(String string) {
		OFCR_APPV_LIMT_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setOFFCR_CHANGE_SCR(String string) {
		OFFCR_CHANGE_SCR = string;
	}

	/**
	 * @param string
	 */
	public void setOFFICER(String string) {
		OFFICER = string;
	}

	/**
	 * @param string
	 */
	public void setOL_SINCE_OPEN(String string) {
		OL_SINCE_OPEN = string;
	}

	/**
	 * @param string
	 */
	public void setOLD_CYC_AFT_CHGOF(String string) {
		OLD_CYC_AFT_CHGOF = string;
	}

	/**
	 * @param string
	 */
	public void setONL_ADDRCHG_UPD_OV(String string) {
		ONL_ADDRCHG_UPD_OV = string;
	}

	/**
	 * @param string
	 */
	public void setORIG_CREDIT_LIMIT(String string) {
		ORIG_CREDIT_LIMIT = string;
	}

	/**
	 * @param string
	 */
	public void setORIG_CREDIT_SCORE(String string) {
		ORIG_CREDIT_SCORE = string;
	}

	/**
	 * @param string
	 */
	public void setOS_AUTH_AMT(String string) {
		OS_AUTH_AMT = string;
	}

	/**
	 * @param string
	 */
	public void setOTH_ACCT_NUMBER(String string) {
		OTH_ACCT_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setOUTSTANDING_AUTHS(String string) {
		OUTSTANDING_AUTHS = string;
	}

	/**
	 * @param string
	 */
	public void setOVERDRAFT_COV_FLAG(String string) {
		OVERDRAFT_COV_FLAG = string;
	}

	/**
	 * @param string
	 */
	public void setOVERLIM_LET_1(String string) {
		OVERLIM_LET_1 = string;
	}

	/**
	 * @param string
	 */
	public void setOVERLIM_LET_2(String string) {
		OVERLIM_LET_2 = string;
	}

	/**
	 * @param string
	 */
	public void setOVERLIMIT_CHRG_COM(String string) {
		OVERLIMIT_CHRG_COM = string;
	}

	/**
	 * @param string
	 */
	public void setOVR_CVR_BREXCEP(String string) {
		OVR_CVR_BREXCEP = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_LETTER_1(String string) {
		PAST_DUE_LETTER_1 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_LETTER_2(String string) {
		PAST_DUE_LETTER_2 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_LETTER_3(String string) {
		PAST_DUE_LETTER_3 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_LETTER_4(String string) {
		PAST_DUE_LETTER_4 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_LETTER_5(String string) {
		PAST_DUE_LETTER_5 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_LETTER_6(String string) {
		PAST_DUE_LETTER_6 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_LETTER_7(String string) {
		PAST_DUE_LETTER_7 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_NOTICE_1(String string) {
		PAST_DUE_NOTICE_1 = string;
	}

	/**
	 * @param string
	 */
	public void setPAST_DUE_NOTICE_2(String string) {
		PAST_DUE_NOTICE_2 = string;
	}

	/**
	 * @param string
	 */
	public void setPAYMENT_REV_T_CYCL(String string) {
		PAYMENT_REV_T_CYCL = string;
	}

	/**
	 * @param string
	 */
	public void setPAYMENT_THIS_CYCLE(String string) {
		PAYMENT_THIS_CYCLE = string;
	}

	/**
	 * @param string
	 */
	public void setPD_FULL_BEF_PAYDUE(String string) {
		PD_FULL_BEF_PAYDUE = string;
	}

	/**
	 * @param string
	 */
	public void setPD_NOTC_1_SENT(String string) {
		PD_NOTC_1_SENT = string;
	}

	/**
	 * @param string
	 */
	public void setPD_NOTC_2_SENT(String string) {
		PD_NOTC_2_SENT = string;
	}

	/**
	 * @param string
	 */
	public void setPD_NOTC_3_SENT(String string) {
		PD_NOTC_3_SENT = string;
	}

	/**
	 * @param string
	 */
	public void setPERSONAL_RFC(String string) {
		PERSONAL_RFC = string;
	}

	/**
	 * @param string
	 */
	public void setPIN_REQUEST_FLAG(String string) {
		PIN_REQUEST_FLAG = string;
	}

	/**
	 * @param string
	 */
	public void setPLACE_OF_EMPLOYMEN(String string) {
		PLACE_OF_EMPLOYMEN = string;
	}

	/**
	 * @param string
	 */
	public void setPOINTS_USED(String string) {
		POINTS_USED = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_CASHADV_BAL(String string) {
		PREV_CASHADV_BAL = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_CASHADV_FINCH(String string) {
		PREV_CASHADV_FINCH = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_MISC_OUTSTAND(String string) {
		PREV_MISC_OUTSTAND = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_POINT_BAL(String string) {
		PREV_POINT_BAL = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_PURCHASE_BAL(String string) {
		PREV_PURCHASE_BAL = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_STATE_ACT_DUE(String string) {
		PREV_STATE_ACT_DUE = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_STATE_BAL(String string) {
		PREV_STATE_BAL = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_STATE_INS_CHG(String string) {
		PREV_STATE_INS_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setPREV_STATE_LATECHG(String string) {
		PREV_STATE_LATECHG = string;
	}

	/**
	 * @param string
	 */
	public void setPREVPUR_FINCHG_OUT(String string) {
		PREVPUR_FINCHG_OUT = string;
	}

	/**
	 * @param string
	 */
	public void setPRIR_LAST_MAINT_DT(String string) {
		PRIR_LAST_MAINT_DT = string;
	}

	/**
	 * @param string
	 */
	public void setPSTDU_MANUALY_CHGD(String string) {
		PSTDU_MANUALY_CHGD = string;
	}

	/**
	 * @param string
	 */
	public void setPURGE_DATE_FOR_CRB(String string) {
		PURGE_DATE_FOR_CRB = string;
	}

	/**
	 * @param string
	 */
	public void setPURGE_DATE_FOR_RCL(String string) {
		PURGE_DATE_FOR_RCL = string;
	}

	/**
	 * @param string
	 */
	public void setQTRLY_PMT_MO_COUNT(String string) {
		QTRLY_PMT_MO_COUNT = string;
	}

	/**
	 * @param string
	 */
	public void setRATE_CODE_CH_COMPL(String string) {
		RATE_CODE_CH_COMPL = string;
	}

	/**
	 * @param string
	 */
	public void setRATECD_CA_OV1U2(String string) {
		RATECD_CA_OV1U2 = string;
	}

	/**
	 * @param string
	 */
	public void setRATECD_CA_OVR2(String string) {
		RATECD_CA_OVR2 = string;
	}

	/**
	 * @param string
	 */
	public void setRATECD_CA_UND1(String string) {
		RATECD_CA_UND1 = string;
	}

	/**
	 * @param string
	 */
	public void setRATECD_OVR_BP2(String string) {
		RATECD_OVR_BP2 = string;
	}

	/**
	 * @param string
	 */
	public void setRATECD_OVR1_U2(String string) {
		RATECD_OVR1_U2 = string;
	}

	/**
	 * @param string
	 */
	public void setRATECD_UND_BP1(String string) {
		RATECD_UND_BP1 = string;
	}

	/**
	 * @param string
	 */
	public void setRATECODE_CSHADV_FE(String string) {
		RATECODE_CSHADV_FE = string;
	}

	/**
	 * @param string
	 */
	public void setRATECODE_LATECHG_F(String string) {
		RATECODE_LATECHG_F = string;
	}

	/**
	 * @param string
	 */
	public void setRATECODE_MINPAYMNT(String string) {
		RATECODE_MINPAYMNT = string;
	}

	/**
	 * @param string
	 */
	public void setREASON_ON_RCL(String string) {
		REASON_ON_RCL = string;
	}

	/**
	 * @param string
	 */
	public void setREG_CODE_FOR_RCL(String string) {
		REG_CODE_FOR_RCL = string;
	}

	/**
	 * @param string
	 */
	public void setREISSU_CRD_PRINTED(String string) {
		REISSU_CRD_PRINTED = string;
	}

	/**
	 * @param string
	 */
	public void setREISSUE_DAY(String string) {
		REISSUE_DAY = string;
	}

	/**
	 * @param string
	 */
	public void setRESTRICTED_CARD_LS(String string) {
		RESTRICTED_CARD_LS = string;
	}

	/**
	 * @param string
	 */
	public void setRET_CHECK_THIS_CYC(String string) {
		RET_CHECK_THIS_CYC = string;
	}

	/**
	 * @param string
	 */
	public void setRFC_GUARANTOR1(String string) {
		RFC_GUARANTOR1 = string;
	}

	/**
	 * @param string
	 */
	public void setRFC_GUARANTOR2(String string) {
		RFC_GUARANTOR2 = string;
	}

	/**
	 * @param string
	 */
	public void setRFC_GUARANTOR3(String string) {
		RFC_GUARANTOR3 = string;
	}

	/**
	 * @param string
	 */
	public void setSALES_DRAFT_REV_CY(String string) {
		SALES_DRAFT_REV_CY = string;
	}

	/**
	 * @param string
	 */
	public void setSALES_DRFT_TH_CYCL(String string) {
		SALES_DRFT_TH_CYCL = string;
	}

	/**
	 * @param string
	 */
	public void setSAV_ACCT_NUMBER(String string) {
		SAV_ACCT_NUMBER = string;
	}

	/**
	 * @param string
	 */
	public void setSENT_TO_MC_TRAV(String string) {
		SENT_TO_MC_TRAV = string;
	}

	/**
	 * @param string
	 */
	public void setSHORT_NAME(String string) {
		SHORT_NAME = string;
	}

	/**
	 * @param string
	 */
	public void setSKIP_PAYMENT_STATE(String string) {
		SKIP_PAYMENT_STATE = string;
	}

	/**
	 * @param string
	 */
	public void setSPOUSE_NAME(String string) {
		SPOUSE_NAME = string;
	}

	/**
	 * @param string
	 */
	public void setSPOUSE_RESP_CODE(String string) {
		SPOUSE_RESP_CODE = string;
	}

	/**
	 * @param string
	 */
	public void setSPOUSE_RESP_FR_ACT(String string) {
		SPOUSE_RESP_FR_ACT = string;
	}

	/**
	 * @param string
	 */
	public void setSSN_FOR_NAME_LINE1(String string) {
		SSN_FOR_NAME_LINE1 = string;
	}

	/**
	 * @param string
	 */
	public void setSTATE_ABBRE_ADDLN1(String string) {
		STATE_ABBRE_ADDLN1 = string;
	}

	/**
	 * @param string
	 */
	public void setSTATE_CD_FOR_ACCT(String string) {
		STATE_CD_FOR_ACCT = string;
	}

	/**
	 * @param string
	 */
	public void setSTATE_PROD_THIS_CY(String string) {
		STATE_PROD_THIS_CY = string;
	}

	/**
	 * @param string
	 */
	public void setSTATEMENT_HOLD_CDE(String string) {
		STATEMENT_HOLD_CDE = string;
	}

	/**
	 * @param string
	 */
	public void setSTATUS(String string) {
		STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setSTR_CRD_ALPHA_PIN(String string) {
		STR_CRD_ALPHA_PIN = string;
	}

	/**
	 * @param string
	 */
	public void setSTRIPE_WAS_READ(String string) {
		STRIPE_WAS_READ = string;
	}

	/**
	 * @param string
	 */
	public void setSUPP_AIR_INS_ACT_M(String string) {
		SUPP_AIR_INS_ACT_M = string;
	}

	/**
	 * @param string
	 */
	public void setSYS_GENER_PIN(String string) {
		SYS_GENER_PIN = string;
	}

	/**
	 * @param string
	 */
	public void setTAXID(String string) {
		TAXID = string;
	}

	/**
	 * @param string
	 */
	public void setTHREE_CASHADV_RATECODE(String string) {
		THREE_CASHADV_RATECODE = string;
	}

	/**
	 * @param string
	 */
	public void setTHREE_PURCHASE_RT_CDS(String string) {
		THREE_PURCHASE_RT_CDS = string;
	}

	/**
	 * @param string
	 */
	public void setTIMES_AUTO_PASTDUE(String string) {
		TIMES_AUTO_PASTDUE = string;
	}

	/**
	 * @param string
	 */
	public void setTIMES_INS_CAL_OPN(String string) {
		TIMES_INS_CAL_OPN = string;
	}

	/**
	 * @param string
	 */
	public void setTIMES_PD_CYCLE(String string) {
		TIMES_PD_CYCLE = string;
	}

	/**
	 * @param string
	 */
	public void setTIMES_PSTDUE_CHGMN(String string) {
		TIMES_PSTDUE_CHGMN = string;
	}

	/**
	 * @param string
	 */
	public void setTRANSFER_DATE(String string) {
		TRANSFER_DATE = string;
	}

	/**
	 * @param string
	 */
	public void setTRANSFER_PROC_COMP(String string) {
		TRANSFER_PROC_COMP = string;
	}

	/**
	 * @param string
	 */
	public void setTRANSFER_REASON(String string) {
		TRANSFER_REASON = string;
	}

	/**
	 * @param string
	 */
	public void setTRANSFER_STATUS(String string) {
		TRANSFER_STATUS = string;
	}

	/**
	 * @param string
	 */
	public void setTRANSIT_ROUTING_NB(String string) {
		TRANSIT_ROUTING_NB = string;
	}

	/**
	 * @param string
	 */
	public void setTRNACCT_NR_OLD_NW(String string) {
		TRNACCT_NR_OLD_NW = string;
	}

	/**
	 * @param string
	 */
	public void setTST_CBR_ISS_CREXC(String string) {
		TST_CBR_ISS_CREXC = string;
	}

	/**
	 * @param string
	 */
	public void setTYPE_AUTO_DEDUCTIO(String string) {
		TYPE_AUTO_DEDUCTIO = string;
	}

	/**
	 * @param string
	 */
	public void setTYPE_CODE(String string) {
		TYPE_CODE = string;
	}

	/**
	 * @param string
	 */
	public void setTYPE_OF_LIMIT_CHG(String string) {
		TYPE_OF_LIMIT_CHG = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_ACCT_1(String string) {
		USER_ACCT_1 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_ACCT_2(String string) {
		USER_ACCT_2 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_ACCT_3(String string) {
		USER_ACCT_3 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_CODE_1(String string) {
		USER_CODE_1 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_CODE_2_POS0(String string) {
		USER_CODE_2_POS0 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_CODE_2_POS1(String string) {
		USER_CODE_2_POS1 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_CODE_2_POS2(String string) {
		USER_CODE_2_POS2 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_CODE_2_POS3(String string) {
		USER_CODE_2_POS3 = string;
	}

	/**
	 * @param string
	 */
	public void setUSER_CODE_3(String string) {
		USER_CODE_3 = string;
	}

	/**
	 * @param string
	 */
	public void setUTH_USER1_HASCARD(String string) {
		UTH_USER1_HASCARD = string;
	}

	/**
	 * @param string
	 */
	public void setZERO_BALANCE_YESTE(String string) {
		ZERO_BALANCE_YESTE = string;
	}

	/**
	 * @param string
	 */
	public void setZIP4_IS_VALID(String string) {
		ZIP4_IS_VALID = string;
	}

	/**
	 * @param string
	 */
	public void setZIP4_NOT_CONVERT(String string) {
		ZIP4_NOT_CONVERT = string;
	}

	/**
	 * @param string
	 */
	public void setZIPCODE_CARDHOLDER(String string) {
		ZIPCODE_CARDHOLDER = string;
	}

}
