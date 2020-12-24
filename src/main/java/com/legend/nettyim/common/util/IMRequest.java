package com.legend.nettyim.common.util;

import lombok.Data;

import java.util.Map;

@Data
public class IMRequest {
    private String url;
    private Map<String, Object> param;
}
