package com.personal.world.data;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
public class UserRegisters {

    @NotNull(message = "用户名称参数没有传！")
    @Length(min = 5,max = 12,message = "用户名称长度至少为5位,小于12位！")
    private String username;


    @NotNull(message = "密码名称参数没有传！")
    @Length(min = 6,max = 12,message = "密码长度至少为6位,小于12位！")
    private String password;


    @NotNull(message = "年龄名称参数没有传！")
    @Min(value = 0,message = "年龄必须是个数字且大于0！")
    @Max(value = 120,message = "年龄必须是个数字且小于120！")
    private String age;


    @NotNull(message = "性别名称参数没有传！")
    @Length(min = 1,max = 1,message = "性别长度不能超出1位！")
    @Pattern(regexp = "((^男$|^女$))",message = "必须是男女其中一个值！")
    private String sex;

}
