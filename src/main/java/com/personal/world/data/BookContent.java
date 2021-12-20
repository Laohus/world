package com.personal.world.data;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class BookContent {

    @NotNull(message = "小说参数没有传！")
    @Length(min = 1,max = 56,message = "小说标题长度至少为1位,小于56位！")
    private String BookName;

    @NotNull(message = "小说章节参数没有传！")
    @Length(min = 1,max = 56,message = "小说章节长度至少为1位,小于56位！")
    private String BookNovel;
}
