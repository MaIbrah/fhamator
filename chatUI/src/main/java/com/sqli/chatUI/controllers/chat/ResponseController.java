package com.sqli.chatUI.controllers.chat;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/response")
public class ResponseController {

    @PostMapping("/update")
    public String testUpdate(@RequestBody Map<String,String> params){

        return params.get("id");
    }

}
