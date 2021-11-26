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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

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
        String openid = "";
        String username = "";
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openid = (String) session.getAttribute("openid");
            username = userService.QueryUser(openid);
        };
        if(source.equals("system")){
            username = (String) session.getAttribute("username");
        };

//        username = (String) session.getAttribute("username");

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
            Blogdata.put("openid",openid);
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


    @RequestMapping("/blog/timeline")
    public Resultinfo GetBlogLine(HttpSession session) {

        Resultinfo result = new Resultinfo();
        String openid = "";
        String username = "";
        List<Map<String, Object>> timeList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openid = (String) session.getAttribute("openid");
            timeList = userService.QueryTimeLine2(openid);
        };
        if(source.equals("system")){
            username = (String) session.getAttribute("username");
            timeList = userService.QueryTimeLine(username);
        };


//        String username = (String) session.getAttribute("username");
//        List<Map<String, Object>> timeList = userService.QueryTimeLine(username);
        List<Object> List = new ArrayList<>();
        assert timeList != null;
        for (Map<String, Object> stringObjectMap : timeList) {
            String line = (String) stringObjectMap.get("timeline");
            List<Map<String, Object>> NameList = null;
//            List<Map<String, Object>> NameList = userService.QueryTimeName(line);
            if(source.equals("qq")){
                openid = (String) session.getAttribute("openid");
                NameList = userService.QueryTimeName(line,openid);
            };
            if(source.equals("system")){
                username = (String) session.getAttribute("username");
                NameList = userService.QueryTimeName2(line,username);
            };
            List<String> lineList = new ArrayList<>();
            for (Map<String, Object> stringObjectMap2 : NameList) {
                lineList.add((String) stringObjectMap2.get("name"));
            }
            Map<String ,Object> temp = new HashMap<>();
            temp.put("timeline",line);
            temp.put("name",lineList);
            List.add(temp);
        }

        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(List);
        return result;


    }

    @RequestMapping("/blog/content")
    public Resultinfo GetBlogContent(HttpServletRequest request,HttpSession session) {

        Resultinfo result = new Resultinfo();
        String openid = "";
        String username = "";
        String BlogName = request.getParameter("BlogName");
        List<Map<String, Object>> DataList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openid = (String) session.getAttribute("openid");
            DataList = userService.QueryName(BlogName,openid);
        };
        if(source.equals("system")){
            username = (String) session.getAttribute("username");
            DataList = userService.QueryName2(BlogName,username);
        };

        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(DataList);
        return result;


    }

    @RequestMapping("/blog/AddComment")
    public Resultinfo AddComment(HttpServletRequest request,HttpSession session) {

        Resultinfo result = new Resultinfo();
        String openid = "";
        String username = "";
        String comment = request.getParameter("comment");
        if(comment.equals("")){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_ERROR());
            return result;
        }
        String BlogName = request.getParameter("BlogName");
        List<Map<String, Object>> DataList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openid = (String) session.getAttribute("openid");
            username = userService.QueryUser(openid);
            DataList = userService.Queryid(BlogName,openid);
        };
        if(source.equals("system")){
            username = (String) session.getAttribute("username");
            DataList = userService.Queryid2(BlogName,username);
        };
        Map<String,Object> temp = new HashMap<>();
        assert DataList != null;
        temp.put("blogid",(Integer) DataList.get(0).get("id"));
        temp.put("comment",comment);
        temp.put("openid",openid);
        temp.put("username",username);
        boolean res = userService.AddBlogComment(temp);
        if(!res){

            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_ERROR());
        }
        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());

//
//        result.setData(DataList);
        return result;


    }

    @RequestMapping("/blog/QueryComment")
    public Resultinfo QueryComment(HttpServletRequest request,HttpSession session) {

        Resultinfo result = new Resultinfo();
        String openid = "";
        String username = "";

        String BlogName = request.getParameter("BlogName");

        List<Map<String, Object>> DataList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openid = (String) session.getAttribute("openid");
            DataList = userService.Queryid(BlogName,openid);
//            username = userService.QueryUser(openid);
        };
        if(source.equals("system")){
            username = (String) session.getAttribute("username");
            DataList = userService.Queryid2(BlogName,username);
        };
        List<Map<String, Object>> CommentData = null;
        assert DataList != null;
        if(DataList.size()==0){
            result.setCode(getSUCCESS_CODE());
            result.setMsg(getACCOUNT_SUCCESS());
            return result;
        }
        assert DataList != null;
        CommentData = userService.QueryCommentData((Integer) DataList.get(0).get("id"));

        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(CommentData);
        return result;


    }


}
