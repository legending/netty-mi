package com.legend.nettyim.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.legend.nettyim.common.util.Constants.AppValue;
import com.legend.nettyim.entity.Appuser;
import com.legend.nettyim.mapper.AppuserMapper;
import com.legend.nettyim.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppUserServiceImpl implements IAppUserService {
    @Autowired
    private AppuserMapper appuserMapper;

    @Autowired
    private AppValue appValue;

    @Override
    public Map getUserInfo(Map map) {

        Map appuser = appuserMapper.slectInfo(map);
        if (appuser == null) {
            Appuser user = new Appuser();
            //生成图像,目前只有314张图片
            int imgIndex = RandomUtil.randomInt(1, 314);
            user.setHead("head-" + imgIndex);
            String name = appValue.randomRobotName();
            user.setNickName(name);
            user.setCreateTime(new Date());
            appuserMapper.insertSelective(user);
            //appuser=BeanUtil.beanToMap(user);
            //不能直接对象转map,因为数据库字段跟实体类字段不一致,容易混淆,统一返回数据库字段为准,请求以实体类为准
            appuser = new HashMap();
            appuser.put("head", user.getHead());
            appuser.put("nick_name", user.getNickName());
            appuser.put("create_time", user.getCreateTime());
            appuser.put("id", user.getId());
        }
        return appuser;
    }
}
