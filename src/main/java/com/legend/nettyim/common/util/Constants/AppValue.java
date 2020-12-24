package com.legend.nettyim.common.util.Constants;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppValue {

    @Value("${websocket.url}")
    public String wsUrl;


    @Value("${websocket.port}")
    public String wsPort;

    @Value("${robotNames}")
    public String robotNames;

    public static String[] robotNameArray = new String[]{};

    //返回随机会员名
    public String randomRobotName() {
        if (robotNameArray.length > 0) {

        } else {
            synchronized (this) {
                if (robotNameArray.length <= 0) {
                    robotNameArray = robotNames.split(",");
                }

            }
        }
        return robotNameArray[RandomUtil.randomInt(0, robotNameArray.length - 1)];
    }


    @Value("${spring.redis.host}")
    public String redisHost;

    @Value("${spring.redis.port}")
    public String redisPort;

    @Value("${spring.redis.password}")
    public String redisPassword;

}
