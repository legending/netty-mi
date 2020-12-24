package com.legend.nettyim.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Group implements Serializable {
    private Integer id;

    private String groupName;


}