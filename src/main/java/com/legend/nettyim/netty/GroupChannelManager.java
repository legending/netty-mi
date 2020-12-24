package com.legend.nettyim.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GroupChannelManager {
    private GroupChannelManager() {
    }

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    //记录当前用户与通道Map
    private static ConcurrentMap<String, String> userChannelMap = new ConcurrentHashMap();

    //获取所有的通道
    public static ChannelGroup getAllChannel() {
        return channels;
    }
}
