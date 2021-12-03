package com.personal.world.data;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@Data
public class AddComment {

    @NotNull(message = "评论内容参数没有传！")
    @Length(min = 6,max = 120,message = "评论内容长度至少为6位,小于120位！")
    private String comment;

    @NotNull(message = "博客标题参数没有传！")
    @Length(min = 1,max = 56,message = "客标题长度至少为1位,小于56位！")
    private String BlogName;
}
