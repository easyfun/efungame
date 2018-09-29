package com.efun.wallet.entity;

import java.util.Date;

public class Vcoin {
    private String id;

    private String vcoinToken;

    private String vcoinName;

    private String vcoinNameEn;

    private String vcoinNameFull;

    private String icon;

    private String sysAccount;

    private String tradeKey;

    private Integer status;

    private Integer canCash;

    private Integer canRecharge;

    private Integer sort;

    private String updateBy;

    private String updareByName;

    private Date updateTime;

    private String createBy;

    private String createByName;

    private Date createTime;

    private Integer mainCoin;

    private String contractAddress;

    private Integer scale;

    private Integer sysRechargeBlock;

    private Integer sysCashBlock;

    private String thresholdHotToCold;

    private String transferAccountsThreshold;

    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVcoinToken() {
        return vcoinToken;
    }

    public void setVcoinToken(String vcoinToken) {
        this.vcoinToken = vcoinToken == null ? null : vcoinToken.trim();
    }

    public String getVcoinName() {
        return vcoinName;
    }

    public void setVcoinName(String vcoinName) {
        this.vcoinName = vcoinName == null ? null : vcoinName.trim();
    }

    public String getVcoinNameEn() {
        return vcoinNameEn;
    }

    public void setVcoinNameEn(String vcoinNameEn) {
        this.vcoinNameEn = vcoinNameEn == null ? null : vcoinNameEn.trim();
    }

    public String getVcoinNameFull() {
        return vcoinNameFull;
    }

    public void setVcoinNameFull(String vcoinNameFull) {
        this.vcoinNameFull = vcoinNameFull == null ? null : vcoinNameFull.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount == null ? null : sysAccount.trim();
    }

    public String getTradeKey() {
        return tradeKey;
    }

    public void setTradeKey(String tradeKey) {
        this.tradeKey = tradeKey == null ? null : tradeKey.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCanCash() {
        return canCash;
    }

    public void setCanCash(Integer canCash) {
        this.canCash = canCash;
    }

    public Integer getCanRecharge() {
        return canRecharge;
    }

    public void setCanRecharge(Integer canRecharge) {
        this.canRecharge = canRecharge;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdareByName() {
        return updareByName;
    }

    public void setUpdareByName(String updareByName) {
        this.updareByName = updareByName == null ? null : updareByName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName == null ? null : createByName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMainCoin() {
        return mainCoin;
    }

    public void setMainCoin(Integer mainCoin) {
        this.mainCoin = mainCoin;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress == null ? null : contractAddress.trim();
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

    public Integer getSysRechargeBlock() {
        return sysRechargeBlock;
    }

    public void setSysRechargeBlock(Integer sysRechargeBlock) {
        this.sysRechargeBlock = sysRechargeBlock;
    }

    public Integer getSysCashBlock() {
        return sysCashBlock;
    }

    public void setSysCashBlock(Integer sysCashBlock) {
        this.sysCashBlock = sysCashBlock;
    }

    public String getThresholdHotToCold() {
        return thresholdHotToCold;
    }

    public void setThresholdHotToCold(String thresholdHotToCold) {
        this.thresholdHotToCold = thresholdHotToCold == null ? null : thresholdHotToCold.trim();
    }

    public String getTransferAccountsThreshold() {
        return transferAccountsThreshold;
    }

    public void setTransferAccountsThreshold(String transferAccountsThreshold) {
        this.transferAccountsThreshold = transferAccountsThreshold == null ? null : transferAccountsThreshold.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}