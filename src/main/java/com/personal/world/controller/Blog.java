package com.personal.world.controller;

import com.alibaba.fastjson.JSONObject;
import com.personal.world.dao.BlogDao;
import com.personal.world.common.ResponseInfo;
import com.personal.world.common.ResultInfo;
import com.personal.world.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
    public ResultInfo addNewBlog(@RequestBody  @Validated AddBlog addBlog , HttpSession session) {

        ResultInfo result = new ResultInfo();

        String openId = (String) session.getAttribute("openid");
        String blogTitle = addBlog.getArticleTitle();
        String classLfy = addBlog.getArticleClassification();
        String content = addBlog.getContent();
        Map<String, String> temp = new HashMap<>();
        temp.put("BlogTitle", blogTitle);
        temp.put("ClassLfy", classLfy);
        temp.put("content", content);
        temp.put("openid", openId);
        boolean resultBlog = userService.AddBlog(temp);
        if (resultBlog) {
            result.setCode(getSUCCESS_CODE());
            result.setMsg(getACCOUNT_SUCCESS());
        } else {
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getNEW_BLOG());
        }
        return result;
    }

    @RequestMapping("/blog/timeline")
    public ResultInfo getBlogLine(HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId;
        List<Map<String, Object>> timeList;
        openId = (String) session.getAttribute("openid");
        timeList = userService.QueryTimeLine2(openId);
        List<Object> List = new ArrayList<>();
        assert timeList != null;
        for (Map<String, Object> stringObjectMap : timeList) {
            String line = (String) stringObjectMap.get("timeline");
            List<Map<String, Object>> NameList = null;
            openId = (String) session.getAttribute("openid");
            NameList = userService.QueryTimeName(line,openId);
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
    public ResultInfo getBlogContent(@Validated BlogDetail blogDetail , HttpSession session) {

        ResultInfo result = new ResultInfo();
        String blogName = blogDetail.getBlogName();
        String openId = (String) session.getAttribute("openid");
        List<Map<String, Object>> temp ;
        temp = userService.QueryName(blogName, openId);
        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(temp);
        return result;
    }

    @RequestMapping("/blog/AddComment")
    public ResultInfo gddComment(@Validated AddComment addComment , HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId = (String) session.getAttribute("openid");
        String comment = addComment.getComment();
        String BlogName = addComment.getBlogName();
        List<Map<String, Object>> temps = null;
        temps = userService.Queryid(BlogName, openId);
        if(temps.size()==0){
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getBLOGS_NOTFOUND());
            return result;

        }
        Map<String, Object> temp = new HashMap<>();
        temp.put("blogid", temps.get(0).get("id"));
        temp.put("comment", comment);
        temp.put("openid", openId);
        boolean res = userService.AddBlogComment(temp);
        if (!res) {
            result.setCode(getFAIL_CODE());
            result.setErrormsg(getADD_COMMENT());
        }
        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        return result;


    }

    @RequestMapping("/blog/QueryComment")
    public ResultInfo queryComment(@Validated Comment comment , HttpSession session) {

        ResultInfo result = new ResultInfo();
        String openId = (String) session.getAttribute("openid");
        String BlogName = comment.getBlogName();
        List<Map<String, Object>> temp;
        temp = userService.Queryid(BlogName, openId);
        List<Map<String, Object>> CommentData;
        assert temp != null;
        if (temp.size() == 0) {
            result.setCode(getSUCCESS_CODE());
            result.setMsg(getACCOUNT_SUCCESS());
            return result;
        }
        CommentData = userService.QueryCommentData((Integer) temp.get(0).get("id"));
        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(CommentData);
        return result;
    }


}
