package com.personal.world.controller;

import com.alibaba.fastjson.JSONObject;
import com.personal.world.Dao.BlogDao;
import com.personal.world.data.Responseinfo;
import com.personal.world.data.Resultinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
public class Blog extends Responseinfo {

    private BlogDao userService;

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".png"};

    @Autowired
    public void setUserService (BlogDao userService) {
        this.userService = userService;
    }



    @RequestMapping("/AddNewBlog")
    public Resultinfo AddNewBlog(@RequestBody JSONObject data , HttpSession session) {

        Resultinfo result = new Resultinfo();
        String username = (String) session.getAttribute("username");

        String BlogTitle = data.getString("BlogTitle");
        String ClassLfy = data.getString("ClassLfy");
        String content = data.getString("content");

        if(BlogTitle.length()==0 || ClassLfy.length()==0 || content.length()==1){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_ERROR());
        }else {
            Map<String,String> Blogdata = new HashMap<>();
            Blogdata.put("BlogTitle",BlogTitle);
            Blogdata.put("ClassLfy",ClassLfy);
            Blogdata.put("username",username);
            Blogdata.put("content",content);
            boolean resultBlog = userService.AddBlog(Blogdata);
            if(resultBlog){
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
