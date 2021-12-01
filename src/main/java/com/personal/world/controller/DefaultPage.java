package com.personal.world.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import com.personal.world.common.ResponseInfo;

import javax.servlet.http.HttpSession;

@Controller
public class DefaultPage extends ResponseInfo {
    @RequestMapping("/login")
    public String login(){ return "Login"; }

    @RequestMapping("/")
    public String loginPage(){ return "Login"; }

    @RequestMapping("/Welcome")
    public String home(){ return "Welcome"; }

    @RequestMapping("/Blog")
    public String blog(){ return "Blog"; }

    @RequestMapping("/Newblog")
    public String newBlog(){ return "Newblog"; }

    @RequestMapping("/BlogDetail")
    public String BlogDetail(){ return "BlogDetail"; }

    @RequestMapping("/LoginOut")
    public String loginOut(HttpSession session, SessionStatus sessionStatus){
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:login";
    }
}
