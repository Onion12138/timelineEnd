package com.ecnu.timeline.handler;

import com.ecnu.timeline.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author onion
 * @date 2019/11/2 -6:21 下午
 */
@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result handleException(RuntimeException e){
        return new Result(null, -1, e.getMessage());
    }
}
