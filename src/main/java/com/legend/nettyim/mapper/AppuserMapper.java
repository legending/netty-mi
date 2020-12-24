package com.legend.nettyim.mapper;

import com.legend.nettyim.entity.Appuser;

import java.util.Map;

public interface AppuserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Appuser record);

    int insertSelective(Appuser record);

    Appuser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Appuser record);

    int updateByPrimaryKey(Appuser record);


    Map slectInfo(Map map);
}