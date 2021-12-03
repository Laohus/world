package com.personal.world;

import com.personal.world.common.ResponseInfo;
import com.personal.world.common.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;



@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseInfo {


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo exceptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        ResultInfo resultMessage = new ResultInfo();
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                resultMessage.setErrormsg(error.getDefaultMessage());
                resultMessage.setCode(getFAIL_CODE());
            }
        }
        return resultMessage;
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo exception(Exception e){
        ResultInfo resultMessage = new ResultInfo();
        resultMessage.setErrormsg("未知原因错误:"+e);
        resultMessage.setCode(getFAIL_CODE());
        return resultMessage;
    }
}
