package com.legend.nettyim.mapper;

import com.legend.nettyim.entity.Msgrecord;

public interface MsgrecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Msgrecord record);

    int insertSelective(Msgrecord record);

    Msgrecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Msgrecord record);

    int updateByPrimaryKey(Msgrecord record);
}