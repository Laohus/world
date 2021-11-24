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
import java.util.List;
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
        if(UserName.equals("") || PassWord.equals("")){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_NO_FOUND());
            return result;
        }
        String ResultUser = userService.QueryUser(UserName);

        if(ResultUser.equals("1")){
            String ResultModPassword = userService.QueryUserPass(UserName,PassWord);

            if(ResultModPassword.equals("1")){
                session.setAttribute("username",UserName);
                session.setAttribute("source","system");
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

        Map<String,String> UserData = new HashMap<>();
        UserData.put("name",data.getString("nickname"));
        UserData.put("password","");
        UserData.put("age",data.getString("year"));
        UserData.put("sex",data.getString("gender"));
        UserData.put("Head",data.getString("figureurl_qq_2"));
        UserData.put("source","qq");
        UserData.put("openid",data.getString("openid"));

        String openid = data.getString("openid");
        String ResultOpenid = userService.QueryOpenid(openid);
        if(ResultOpenid.equals("1")){
            if(userService.updateUser(UserData)){
                session.setAttribute("openid",data.getString("openid"));
                session.setAttribute("source","qq");
                result.setCode(getSUCCESS_CODE());
                result.setMsg(getACCOUNT_SUCCESS());
            }else {
                result.setCode(getFAIL_CODE());
                result.setErrormsg(getACCOUNT_ERROR());
            }
        }else {
            if(userService.adduser(UserData)){
                session.setAttribute("openid",data.getString("openid"));
                session.setAttribute("source","qq");
                result.setCode(getSUCCESS_CODE());
                result.setMsg(getACCOUNT_SUCCESS());
            }else {
                result.setCode(getFAIL_CODE());
                result.setErrormsg(getACCOUNT_ERROR());
            }

        }
        return result;

    }

    @RequestMapping("/UserInfo")
    public Resultinfo UserInfo (HttpSession session){

        Resultinfo result = new Resultinfo();
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            String openid = (String) session.getAttribute("openid");
            List ResultUser = userService.QueryUserData2(openid);
            Integer count = userService.BlogCount(openid);
            result.setData(ResultUser);
            result.setCount(count);
        };
        if(source.equals("system")){
            String username = (String) session.getAttribute("username");
            List ResultUser = userService.QuerySystemData(username);
            Integer count = userService.BlogSystemCount(username);
            result.setData(ResultUser);
            result.setCount(count);
        };

        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        return result;

    }
}
