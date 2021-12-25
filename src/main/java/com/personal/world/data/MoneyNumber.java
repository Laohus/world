package com.personal.world.data;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Data
public class MoneyNumber {

    @Min(value = 1,message = "蓝球必须是个数字且大于等于1！")
    @Max(value = 16,message = "蓝球必须是个数字且小于等于16！")
    private Integer BlueNum;

    @Min(value = 1,message = "红球必须是个数字且大于等于1！")
    @Max(value = 33,message = "红球必须是个数字且小于等于33！")
    private Integer RedNum;
}
