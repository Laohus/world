package com.personal.world.data;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserAccountQQ {

    @NotNull(message = "用户名称参数没有传！")
    @Length(min = 1,max = 24,message = "用户名称长度至少为1位,小于24位！")
    private String nickname;


    @NotNull(message = "年龄名称参数没有传！")
    @Min(value = 0,message = "年龄必须是个数字且大于0！")
    @Max(value = 9999,message = "年龄必须是个数字且小于120！")
    private String year;


    @NotNull(message = "性别名称参数没有传！")
    @Length(min = 1,max = 1,message = "性别长度不能超出1位！")
    @Pattern(regexp = "((^男$|^女$))",message = "必须是男女其中一个值！")
    private String gender;

    @NotNull(message = "头像名称参数没有传！")
    @URL(message = "必须是一个合法的地址格式！")
    private String figureurl_qq_2;

    @NotNull(message = "用户唯一id参数没有传！")
    @Length(min = 32,max = 32,message = "用户id长度为32位！")
    private String openid;

}
