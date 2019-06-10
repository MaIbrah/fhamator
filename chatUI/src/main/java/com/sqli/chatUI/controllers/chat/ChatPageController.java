package com.sqli.chatUI.controllers.chat;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChatPageController {

    private static Logger log = Logger.getLogger(ChatPageController.class.getName());

    @RequestMapping("/")
    public String chat(Model model,
                       HttpSession httpSession){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username =  ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        httpSession.setAttribute("username",username);
        model.addAttribute("username",username);
        log.info(username + " is ready to chat");
        return "chat";
    }

    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }


}
