package com.bjs.listener;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description
 * @Date 2019-10-28 10:48:20
 * @Author BianJiashuai
 */
@Getter
@Setter
@ToString
public class TaskComment implements Serializable {

    private String assignee;
    private String result;
    private String comment;
}
