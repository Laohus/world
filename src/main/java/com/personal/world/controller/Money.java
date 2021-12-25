package com.personal.world.controller;

import com.personal.world.common.ResponseInfo;
import com.personal.world.common.ResultInfo;
import com.personal.world.data.MoneyNumber;
import com.personal.world.service.Lottery;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class Money extends ResponseInfo {

    @RequestMapping("/Money/Create")
    public ResultInfo MoneyCreate(@Validated MoneyNumber moneyNumber , HttpSession session) throws IOException {

        ResultInfo result = new ResultInfo();
        Integer Blue = Lottery.BlueNumber(moneyNumber.getBlueNum());
        List<Integer> Red = Lottery.RedNumber(moneyNumber.getRedNum());
        Map<String,Object> temp = new HashMap<>();
        temp.put("blue",Blue);
        temp.put("red",Red);
        result.setCode(getSUCCESS_CODE());
        result.setMsg(getACCOUNT_SUCCESS());
        result.setData(temp);
        return result;
    }
}
