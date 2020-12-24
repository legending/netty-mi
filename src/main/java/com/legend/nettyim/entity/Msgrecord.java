package com.legend.nettyim.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Msgrecord implements Serializable {
    private Integer id;

    private String content;

    private Integer fromUserId;

    private Integer toUserId;

    private Date createTime;

    private Integer groupId;

    private String ip;


}