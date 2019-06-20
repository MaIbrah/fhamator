package com.sqli.chatUI.controllers.chat;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

@Controller
public class ChatPageController {

    private static Logger log = Logger.getLogger(ChatPageController.class.getName());

   /* @RequestMapping("/")
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
    }*/

   /* @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }*/

   /* @GetMapping("/chat")
    public ModelAndView chatPage()
    {

       // re: http://localhost:3000/ChatPage";
        return new ModelAndView("redirect: /localhost:3000/ChatPage");
    }*/

}
