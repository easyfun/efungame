package com.efun.wallet.dao;

import com.efun.wallet.entity.MemberAsset;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface MemberAssetDAO {
    int deleteByPrimaryKey(String id);

    int insert(MemberAsset record);

    int insertSelective(MemberAsset record);

    MemberAsset selectByPrimaryKey(String id);

    MemberAsset selectByAddressAndToken(@Param("address") String address, @Param("token") String token);

    int updateByPrimaryKeySelective(MemberAsset record);

    int updateByPrimaryKey(MemberAsset record);

    int rechargeUpdate(@Param("assetId") String assetId, @Param("inComeValue") BigDecimal inComeValue);

    MemberAsset queryAsset(@Param("assetAdress") String assetAdress, @Param("vcionId") String vcionId);
}