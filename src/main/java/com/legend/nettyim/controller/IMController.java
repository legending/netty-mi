package com.legend.nettyim.controller;

import com.legend.nettyim.common.util.Constants.AppVule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IMController {
    @Autowired
    private AppVule appVule;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String toWelCome(ModelMap modelMap){
        modelMap.put("wsUrl",appVule.wsUrl);
        modelMap.put("wsPort",appVule.wsPort);
        return "index";
    }



}
