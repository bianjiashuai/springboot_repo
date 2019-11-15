package com.bjs.pojo;

import com.bjs.entity.User;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2019-09-23 15:27:52
 * @Author BianJiashuai
 */
@Data
public class UserLoginDto implements Serializable {
    private JWT jwt;
    private User user;
}
