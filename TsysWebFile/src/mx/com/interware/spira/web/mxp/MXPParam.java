/**
 * MXPParam.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package mx.com.interware.spira.web.mxp;

public class MXPParam  implements java.io.Serializable {
    private java.lang.String account;

    private java.lang.String amount;

    private java.lang.String balanceId;

    private java.lang.String promoId;

    private java.lang.String userName;
    
    private String description;

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MXPParam() {
    }

    public MXPParam(
           java.lang.String account,
           java.lang.String amount,
           java.lang.String balanceId,
           java.lang.String promoId,
           java.lang.String userName) {
           this.account = account;
           this.amount = amount;
           this.balanceId = balanceId;
           this.promoId = promoId;
           this.userName = userName;
    }


    /**
     * Gets the account value for this MXPParam.
     * 
     * @return account
     */
    public java.lang.String getAccount() {
        return account;
    }


    /**
     * Sets the account value for this MXPParam.
     * 
     * @param account
     */
    public void setAccount(java.lang.String account) {
        this.account = account;
    }


    /**
     * Gets the amount value for this MXPParam.
     * 
     * @return amount
     */
    public java.lang.String getAmount() {
        return amount;
    }


    /**
     * Sets the amount value for this MXPParam.
     * 
     * @param amount
     */
    public void setAmount(java.lang.String amount) {
        this.amount = amount;
    }


    /**
     * Gets the balanceId value for this MXPParam.
     * 
     * @return balanceId
     */
    public java.lang.String getBalanceId() {
        return balanceId;
    }


    /**
     * Sets the balanceId value for this MXPParam.
     * 
     * @param balanceId
     */
    public void setBalanceId(java.lang.String balanceId) {
        this.balanceId = balanceId;
    }


    /**
     * Gets the promoId value for this MXPParam.
     * 
     * @return promoId
     */
    public java.lang.String getPromoId() {
        return promoId;
    }


    /**
     * Sets the promoId value for this MXPParam.
     * 
     * @param promoId
     */
    public void setPromoId(java.lang.String promoId) {
        this.promoId = promoId;
    }


    /**
     * Gets the userName value for this MXPParam.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this MXPParam.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

 

}
