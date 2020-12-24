package com.legend.nettyim.controller;

import com.legend.nettyim.common.util.ApiResult;
import com.legend.nettyim.service.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/appUser")
public class AppUserController {
    @Autowired
    private IAppUserService appUserService;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult getUserInfo(@RequestParam Map map) {
        Map appuser = appUserService.getUserInfo(map);
        return ApiResult.success(appuser);
    }

}
