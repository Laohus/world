package com.personal.world.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
public class Blog {

    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg", ".jpeg", ".png"};

//    @RequestMapping("/api/upload/editor")
//    public boolean editMovieInfo(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
//
//        return true;
//
//
//    }

}
