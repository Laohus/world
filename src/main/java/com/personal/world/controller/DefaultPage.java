package com.personal.world.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import com.personal.world.data.Responseinfo;

import javax.servlet.http.HttpSession;

@Controller
public class DefaultPage extends Responseinfo {
    @RequestMapping("/login")
    public String login(){ return "Login"; }

    @RequestMapping("/")
    public String loginPage(){ return "Login"; }

    @RequestMapping("/Welcome")
    public String home(){ return "Welcome"; }

    @RequestMapping("/Blog")
    public String blog(){ return "Blog"; }

    @RequestMapping("/LoginOut")
    public String LoginOut(HttpSession session, SessionStatus sessionStatus){
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:login";
    }
}
