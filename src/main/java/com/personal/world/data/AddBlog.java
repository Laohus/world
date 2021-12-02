package com.personal.world.data;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@Data
public class AddBlog {

    @NotNull(message = "博客内容参数没有传！")
    private String content;

    @NotNull(message = "博客分类参数没有传！")
    @Length(min = 1,max = 12,message = "博客分类长度至少为1位,小于12位！")
    private String articleClassification;

    @NotNull(message = "博客标题参数没有传！")
    @Length(min = 1,max = 56,message = "客标题长度至少为1位,小于56位！")
    private String articleTitle;

}
