package com.personal.world.controller;

import com.personal.world.common.ResponseInfo;
import com.personal.world.common.ResultInfo;
import com.personal.world.data.BookContent;
import com.personal.world.data.BookData;
import com.personal.world.data.BookUrl;
import com.personal.world.service.StartingPointBook;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class Book extends ResponseInfo {

    @RequestMapping("/Book/Name")
    public ResultInfo BookName(@Validated BookData bookData , HttpSession session) throws IOException {

        ResultInfo result = new ResultInfo();
        String url = StartingPointBook.BookName(bookData.getBookName());
        if(url == null){
            result.setCode(getFAIL_CODE());
            result.setErrormsg("没有查询到小说！");
        }else {
            result.setCode(getSUCCESS_CODE());
            result.setMsg(getACCOUNT_SUCCESS());
            result.setData(url);
        }
        return result;
    }

    @RequestMapping("/Book/Novel")
    public ResultInfo BookNovel(@Validated BookUrl bookUrl , HttpSession session) throws IOException {

        ResultInfo result = new ResultInfo();
        List<Map<String, Object>> Novel = StartingPointBook.BookNovel(bookUrl.getNovelDirectory());
        if(Novel.size()==0){
            result.setCode(getFAIL_CODE());
            result.setErrormsg("没有查询到小说目录！");
        }else {
            result.setCode(getSUCCESS_CODE());
            result.setMsg(getACCOUNT_SUCCESS());
            result.setData(Novel);
        }

        return result;
    }

    @RequestMapping("/Book/Content")
    public ResultInfo BookContent(@Validated BookContent bookContent , HttpSession session) throws IOException {

        ResultInfo result = new ResultInfo();
        String url = StartingPointBook.BookName(bookContent.getBookName());
        List<Map<String, Object>> Novel = StartingPointBook.BookNovel(url);
        String Content = StartingPointBook.BookContent(Novel,bookContent.getBookNovel());
        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(Content);
        return result;
    }

}
