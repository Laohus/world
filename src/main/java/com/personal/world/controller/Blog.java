package com.personal.world.controller;

import com.alibaba.fastjson.JSONObject;
import com.personal.world.dao.BlogDao;
import com.personal.world.common.ResponseInfo;
import com.personal.world.common.ResultInfo;
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
public class Blog extends ResponseInfo {

    private BlogDao userService;

    @Autowired
    public void setUserService (BlogDao userService) {
        this.userService = userService;
    }

    @RequestMapping("/AddNewBlog")
    public ResultInfo addNewBlog(@RequestBody JSONObject data , HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId = "";
        String userName = "";
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openId = (String) session.getAttribute("openid");
            userName = userService.QueryUser(openId);
        }
        if(source.equals("system")){
            userName = (String) session.getAttribute("username");
        }

        String blogTitle = data.getString("BlogTitle");
        String classLfy = data.getString("ClassLfy");
        String content = data.getString("content");

        if(blogTitle.length()==0 || classLfy.length()==0 || content.length()==1){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_ERROR());
        }else {
            Map<String,String> Blogdata = new HashMap<>();
            Blogdata.put("BlogTitle",blogTitle);
            Blogdata.put("ClassLfy",classLfy);
            Blogdata.put("username",userName);
            Blogdata.put("content",content);
            Blogdata.put("openid",openId);
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
    public ResultInfo getBlogLine(HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId;
        String userName;
        List<Map<String, Object>> timeList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openId = (String) session.getAttribute("openid");
            timeList = userService.QueryTimeLine2(openId);
        }
        if(source.equals("system")){
            userName = (String) session.getAttribute("username");
            timeList = userService.QueryTimeLine(userName);
        }

        List<Object> List = new ArrayList<>();
        assert timeList != null;
        for (Map<String, Object> stringObjectMap : timeList) {
            String line = (String) stringObjectMap.get("timeline");
            List<Map<String, Object>> NameList = null;
            if(source.equals("qq")){
                openId = (String) session.getAttribute("openid");
                NameList = userService.QueryTimeName(line,openId);
            }
            if(source.equals("system")){
                userName = (String) session.getAttribute("username");
                NameList = userService.QueryTimeName2(line,userName);
            }
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
    public ResultInfo getBlogContent(HttpServletRequest request, HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId;
        String userName;
        String blogName = request.getParameter("BlogName");
        List<Map<String, Object>> dataList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openId = (String) session.getAttribute("openid");
            dataList = userService.QueryName(blogName,openId);
        }
        if(source.equals("system")){
            userName = (String) session.getAttribute("username");
            dataList = userService.QueryName2(blogName,userName);
        }

        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(dataList);
        return result;


    }

    @RequestMapping("/blog/AddComment")
    public ResultInfo gddComment(HttpServletRequest request, HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId = "";
        String userName = "";
        String comment = request.getParameter("comment");
        if(comment.equals("")){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getACCOUNT_ERROR());
            return result;
        }
        String BlogName = request.getParameter("BlogName");
        List<Map<String, Object>> dataList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openId = (String) session.getAttribute("openid");
            userName = userService.QueryUser(openId);
            dataList = userService.Queryid(BlogName,openId);
        }
        if(source.equals("system")){
            userName = (String) session.getAttribute("username");
            dataList = userService.Queryid2(BlogName,userName);
        }
        Map<String,Object> temp = new HashMap<>();
        assert dataList != null;
        temp.put("blogid", dataList.get(0).get("id"));
        temp.put("comment",comment);
        temp.put("openid",openId);
        temp.put("username",userName);
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
    public ResultInfo queryComment(HttpServletRequest request, HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId;
        String userName;

        String BlogName = request.getParameter("BlogName");

        List<Map<String, Object>> DataList = null;
        String source = (String) session.getAttribute("source");
        if(source.equals("qq")){
            openId = (String) session.getAttribute("openid");
            DataList = userService.Queryid(BlogName,openId);
//            username = userService.QueryUser(openid);
        }
        if(source.equals("system")){
            userName = (String) session.getAttribute("username");
            DataList = userService.Queryid2(BlogName,userName);
        }
        List<Map<String, Object>> CommentData;
        assert DataList != null;
        if(DataList.size()==0){
            result.setCode(getSUCCESS_CODE());
            result.setMsg(getACCOUNT_SUCCESS());
            return result;
        }
        CommentData = userService.QueryCommentData((Integer) DataList.get(0).get("id"));

        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(CommentData);
        return result;


    }


}
