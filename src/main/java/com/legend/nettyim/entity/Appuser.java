package com.legend.nettyim.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Appuser implements Serializable {
    private Integer id;

    private String nickName;

    private String head;
    private Date createTime;


}