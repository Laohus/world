package com.personal.world.controller;

import com.alibaba.fastjson.JSONObject;
import com.personal.world.data.Resultinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;


import javax.servlet.http.HttpSession;
import com.personal.world.data.Responseinfo;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.personal.world.Dao.UserDao;


import java.util.HashMap;
import java.util.Map;


@RestController
@ResponseBody
public class Login extends Responseinfo{

    private UserDao userService;

    @Autowired
    public void setUserService (UserDao userService) {
        this.userService = userService;
    }

    @RequestMapping("/login/account")
    public Resultinfo LoginAccount (HttpServletRequest request , HttpSession session){

        Resultinfo result = new Resultinfo();

        String UserName = request.getParameter("username");
        String PassWord = request.getParameter("password");
        String ResultUser = userService.QueryUser(UserName);

        if(ResultUser.equals("1")){
            String ResultModPassword = userService.QueryUserPass(UserName,PassWord);

            if(ResultModPassword.equals("1")){
                session.setAttribute("username",UserName);
                result.setCode(getSUCCESS_CODE());
                result.setMsg(getACCOUNT_SUCCESS());
            }else {
                result.setCode(getFAIL_CODE());
                result.setErrormsg(getACCOUNT_ERROR());
            }
        }else {
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_NO_FOUND());

        }
        return result;

    }

    @RequestMapping("/login/qq")
    public Resultinfo Loginqq (@RequestBody JSONObject data , HttpSession session){

        Resultinfo result = new Resultinfo();

        String nickname = data.getString("nickname");
        String ResultUser = userService.QueryUser(nickname);
        if(ResultUser.equals("1")){
            session.setAttribute("username",nickname);
            result.setCode(getSUCCESS_CODE());
            result.setMsg(getACCOUNT_SUCCESS());
        }else {
            session.setAttribute("username",nickname);
            Map<String,String> UserData = new HashMap<>();

            UserData.put("name",nickname);
            UserData.put("password","");
            UserData.put("age",data.getString("year"));
            UserData.put("sex",data.getString("gender"));
            UserData.put("source","qq");
            boolean res = userService.adduser(UserData);
            if(res){
                result.setCode(getSUCCESS_CODE());
                result.setMsg(getACCOUNT_SUCCESS());
            }else {
                result.setCode(getFAIL_CODE());
                result.setErrormsg(getACCOUNT_ERROR());
            }

        }
        return result;

    }

}
