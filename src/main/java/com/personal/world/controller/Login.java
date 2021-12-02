package com.personal.world.controller;

import com.alibaba.fastjson.JSONObject;
import com.personal.world.common.ResultInfo;
import com.personal.world.data.UserAccount;
import com.personal.world.data.UserAccountQQ;
import com.personal.world.data.UserRegisters;
import com.personal.world.service.Md5Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
    public ResultInfo loginRegisters (@Validated UserRegisters userInformation , BindingResult bindingResult, HttpServletRequest request){

        ResultInfo result = new ResultInfo();

        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                result.setErrormsg(error.getDefaultMessage());
                result.setCode(getFAIL_CODE());
                return result;
            }
        }else {
            String userName = request.getParameter("username");
            String passWord = request.getParameter("password");
            String age = request.getParameter("age");
            String sex = request.getParameter("sex");
            String resultUser = userService.QueryUser(userName);
            if (resultUser.equals("0")) {
                String openId = Md5Message.GetMad5(userName + passWord);
                Map<String, String> temp = new HashMap<>();
                temp.put("name", userName);
                temp.put("password", passWord);
                temp.put("age", age);
                temp.put("sex", sex);
                temp.put("openid", openId);
                boolean ResultRegisters = userService.adduserSystem(temp);
                if (ResultRegisters) {
                    result.setCode(getSUCCESS_CODE());
                    result.setMsg(getACCOUNT_SUCCESS());
                } else {
                    result.setCode(getFAIL_CODE());
                    result.setErrormsg(getREGISTERED_USERS_ERROR());
                }
            } else {
                result.setCode(getFAIL_CODE());
                result.setErrormsg(getACCOUNT_FOUNDED());

            }
        }
        return result;

    }

    @RequestMapping("/login/account")
    public ResultInfo loginAccount (@Validated UserAccount userAccount , BindingResult bindingResult, HttpServletRequest request , HttpSession session){

        ResultInfo result = new ResultInfo();
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                result.setErrormsg(error.getDefaultMessage());
                result.setCode(getFAIL_CODE());
                return result;
            }
        }else {
        String userName = request.getParameter("username");
        String passWord = request.getParameter("password");
        String resultUser = userService.QueryUser(userName);
        if(resultUser.equals("1")){
            String resultModPassword = userService.QueryUserPass(userName,passWord);
            if(resultModPassword.equals("1")){
                session.setAttribute("username",userName);
                String openid = userService.QueryOpenidId(userName,"system");
                session.setAttribute("openid",openid);
                result.setCode(getSUCCESS_CODE());
                result.setMsg(getACCOUNT_SUCCESS());
            }else {
                result.setCode(getFAIL_CODE());
                result.setErrormsg(getACCOUNT_ERROR());
            }
        }else {
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_NO_FOUND());
        }}
        return result;

    }

    @RequestMapping("/login/qq")
    public ResultInfo loginAccountQQ (@RequestBody  @Validated UserAccountQQ userAccountQQ , BindingResult bindingResult , HttpSession session){

        ResultInfo result = new ResultInfo();
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                result.setErrormsg(error.getDefaultMessage());
                result.setCode(getFAIL_CODE());
                return result;
            }
        }else {
            Map<String,String> userData = new HashMap<>();
            userData.put("name",userAccountQQ.getNickname());
            userData.put("password","");
            userData.put("age",userAccountQQ.getYear());
            userData.put("sex",userAccountQQ.getGender());
            userData.put("Head",userAccountQQ.getFigureurl_qq_2());
            userData.put("source","qq");
            userData.put("openid",userAccountQQ.getOpenid());

            String openId = userAccountQQ.getOpenid();
            String resultOpenId = userService.QueryOpenid(openId);
            if(resultOpenId.equals("1")){
                if(userService.updateUser(userData)){
                    session.setAttribute("openid",openId);
                    session.setAttribute("source","qq");
                    result.setCode(getSUCCESS_CODE());
                    result.setMsg(getACCOUNT_SUCCESS());
                }else {
                    result.setCode(getFAIL_CODE());
                    result.setErrormsg(getMODIFY_INFORMATION());
                }
            }else {
                if(userService.adduser(userData)){
                    session.setAttribute("openid",openId);
                    session.setAttribute("source","qq");
                    result.setCode(getSUCCESS_CODE());
                    result.setMsg(getACCOUNT_SUCCESS());
                }else {
                    result.setCode(getFAIL_CODE());
                    result.setErrormsg(getADD_INFORMATION());
                }

            }}
        return result;

    }

    @RequestMapping("/UserInfo")
    public ResultInfo userInfo (HttpSession session){

        ResultInfo result = new ResultInfo();

        String openid = (String) session.getAttribute("openid");
        List<Map<String, Object>> resultUser = userService.QueryUserData2(openid);
        Integer count = userService.BlogCount(openid);
        result.setData(resultUser);
        result.setCount(count);
        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        return result;

    }

}
