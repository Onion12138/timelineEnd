package com.ecnu.timeline.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author onion
 * @date 2019/11/2 -6:15 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    private Object data;
    private Integer code;
    private String message;
}
