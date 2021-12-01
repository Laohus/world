package com.personal.world.controller;

import com.alibaba.fastjson.JSONObject;
import com.personal.world.common.ResultInfo;
import com.personal.world.service.Md5Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import com.personal.world.common.ResponseInfo;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.personal.world.dao.UserDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@ResponseBody
public class Login extends ResponseInfo {

    private UserDao userService;

    @Autowired
    public void setUserService (UserDao userService) {
        this.userService = userService;
    }

    @RequestMapping("/login/Registers")
    public ResultInfo loginRegisters (HttpServletRequest request){

        ResultInfo result = new ResultInfo();

        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        String age = request.getParameter("age");
        String sex = request.getParameter("sex");
        if(userName.equals("") || passWord.equals("") || age.equals("") || sex.equals("")){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_NO_FOUND());
            return result;
        }
        String resultUser = userService.QueryUser(userName);

        String openId = Md5Message.GetMad5(userName+passWord);

        Map<String,String> temp = new HashMap<>();
        temp.put("name",userName);
        temp.put("password",passWord);
        temp.put("age",age);
        temp.put("sex",sex);
        temp.put("openid",openId);

        if(resultUser.equals("0")){
            boolean ResultRegisters = userService.adduserSystem(temp);

            if(ResultRegisters){
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

    @RequestMapping("/login/account")
    public ResultInfo loginAccount (HttpServletRequest request , HttpSession session){

        ResultInfo result = new ResultInfo();

        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        if(userName.equals("") || passWord.equals("")){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_NO_FOUND());
            return result;
        }
        String resultUser = userService.QueryUser(userName);

        if(resultUser.equals("1")){
            String resultModPassword = userService.QueryUserPass(userName,passWord);

            if(resultModPassword.equals("1")){
                session.setAttribute("username",userName);
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
    public ResultInfo loginAccountQQ (@RequestBody JSONObject data , HttpSession session){

        ResultInfo result = new ResultInfo();

        Map<String,String> userData = new HashMap<>();
        userData.put("name",data.getString("nickname"));
        userData.put("password","");
        userData.put("age",data.getString("year"));
        userData.put("sex",data.getString("gender"));
        userData.put("Head",data.getString("figureurl_qq_2"));
        userData.put("source","qq");
        userData.put("openid",data.getString("openid"));

        String openId = data.getString("openid");
        String resultOpenId = userService.QueryOpenid(openId);
        if(resultOpenId.equals("1")){
            if(userService.updateUser(userData)){
                session.setAttribute("openid",data.getString("openid"));
                session.setAttribute("source","qq");
                result.setCode(getSUCCESS_CODE());
                result.setMsg(getACCOUNT_SUCCESS());
            }else {
                result.setCode(getFAIL_CODE());
                result.setErrormsg(getACCOUNT_ERROR());
            }
        }else {
            if(userService.adduser(userData)){
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
    public ResultInfo userInfo (HttpSession session){

        ResultInfo result = new ResultInfo();
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            String openid = (String) session.getAttribute("openid");
            List<Map<String, Object>> resultUser = userService.QueryUserData2(openid);
            Integer count = userService.BlogCount(openid);
            result.setData(resultUser);
            result.setCount(count);
        }
        if(source.equals("system")){
            String userName = (String) session.getAttribute("username");
            List<Map<String, Object>> resultUser = userService.QuerySystemData(userName);
            Integer count = userService.BlogSystemCount(userName);
            result.setData(resultUser);
            result.setCount(count);
        }

        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        return result;

    }
}
