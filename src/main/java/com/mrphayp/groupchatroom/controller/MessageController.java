package com.mrphayp.groupchatroom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class MessageController {
    /**
     * Login Page
     */
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * Chatroom Page
     */
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        //TODO: add code for login to chatroom.
        ModelAndView mv = new ModelAndView("chat");
        if(username == null || username == ""){
            username = "Anonymous";
        }

        mv.addObject("username", username);
        // TODO: To return Route = "ws://localhost:8080/chat"
        System.out.println(request.getRequestURL());
        mv.addObject("url","wss://" + request.getRequestURL() + "/chat");
        return mv;
    }
}
