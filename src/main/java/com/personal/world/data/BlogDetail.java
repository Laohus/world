package com.personal.world.data;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@Data
public class BlogDetail {

    @NotNull(message = "博客标题参数没有传！")
    @Length(min = 1,max = 56,message = "博客标题长度至少为1位,小于56位！")
    private String BlogName;
}
