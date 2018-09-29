package com.efun.wallet.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AssetTrans {
    private String id;

    private String assetId;

    private String vcoinId;

    private BigDecimal transAmount;

    private BigDecimal totalAmount;

    private BigDecimal balanceAmount;

    private BigDecimal frozenAmount;

    private BigDecimal btcAmount;

    private Date transTime;

    private String transReceipt;

    private Integer transType;

    private String transNo;

    private String tradeType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId == null ? null : assetId.trim();
    }

    public String getVcoinId() {
        return vcoinId;
    }

    public void setVcoinId(String vcoinId) {
        this.vcoinId = vcoinId == null ? null : vcoinId.trim();
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public BigDecimal getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(BigDecimal btcAmount) {
        this.btcAmount = btcAmount;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransReceipt() {
        return transReceipt;
    }

    public void setTransReceipt(String transReceipt) {
        this.transReceipt = transReceipt == null ? null : transReceipt.trim();
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }
}