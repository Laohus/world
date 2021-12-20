package com.personal.world.data;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Data
public class BookUrl {
    @NotNull(message = "小说链接参数没有传！")
    @URL(message = "必须是一个合法的地址格式！")
    private String NovelDirectory;
}
