package com.sqli.chatUI.controllers.chat;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatPageController {
    @PostMapping("/chat")
    public String chat(Model model,
        HttpSession httpSession,
        @RequestParam("username") String username) {

        httpSession.setAttribute("username",username);
        model.addAttribute("username",username);
            return "chat";
    }

    @RequestMapping("/")
    public String chat(Model model,
                       HttpSession httpSession){
       String username= (String) httpSession.getAttribute("username");

        if (username==null)
            return "index";
        else{
            model.addAttribute("username",username);
            return "/chat";
        }
    }


}
