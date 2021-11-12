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

    @RequestMapping("/api/upload/editor")
    public boolean editMovieInfo(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

//        boolean isFlag = false;
//        for (String type : IMAGE_TYPE) {
//            System.out.println(file.getOriginalFilename());
//            if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
//                isFlag = true;
//                break;
//            }
//        }
//
//        if (isFlag) {
//            PicUploadResult picUploadResult = fileUploadService.uplodadImg(file, request);
//            boolean isLegal = picUploadResult.isLegal();
//
//            if (isLegal) {
//                Map resMap = new HashMap<>();
//                resMap.put("imgPath", picUploadResult.getImgPath());
//                return R.ok(resMap);
//            } else {
//                return R.error("图片上传有误");
//            }
//        } else {
//            return R.error("上传的图片格式必须为:bmp,jpg,jpeg,png");
//        }
        return true;


    }

}
