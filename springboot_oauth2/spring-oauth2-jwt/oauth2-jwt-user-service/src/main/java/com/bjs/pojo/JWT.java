package com.bjs.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2019-09-23 15:50:55
 * @Author BianJiashuai
 */
@Data
public class JWT implements Serializable {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;
}
