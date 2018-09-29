package com.efun.wallet.dao;

import com.efun.wallet.entity.Vcoin;
import com.mexc.dao.model.vcoin.MexcVCoin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VcoinDAO {
    int deleteByPrimaryKey(String id);

    int insert(Vcoin record);

    int insertSelective(Vcoin record);

    Vcoin selectByPrimaryKey(String id);

    Vcoin selectByVcoinId(String vcoinId);

    int updateByPrimaryKeySelective(Vcoin record);

    int updateByPrimaryKeyWithBLOBs(Vcoin record);

    int updateByPrimaryKey(Vcoin record);

    Vcoin selectByContractAddress(String contractAddress);

    Vcoin queryByEth();

    List<Vcoin> selectAll();
}