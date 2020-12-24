package com.legend.nettyim.entity;

import lombok.Data;

//访问者
@Data
public class Visitor {
   private String ip;
   private Integer illegalCount;  //当前警告次数,超过阈值会列入黑名单
   private String lastTime;//上次访问时间
}
