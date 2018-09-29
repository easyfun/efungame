package com.efun.wallet.dao;

import com.efun.wallet.entity.AddressLib;

public interface AddressLibDAO {
    int deleteByPrimaryKey(Integer id);

    int insert(AddressLib record);

    int insertSelective(AddressLib record);

    AddressLib selectByPrimaryKey(Integer id);

    AddressLib selectByAddress(String address);

    int updateByPrimaryKeySelective(AddressLib record);

    int updateByPrimaryKey(AddressLib record);
}