package com.bjs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Date 2019-10-29 16:01:30
 * @Author BianJiashuai
 */
@Data
@ApiModel(description = "返回响应数据")
public class RestMessage {

    @ApiModelProperty(value = "错误信息")
    private String message;
    @ApiModelProperty(value = "状态码")
    private String code;
    @ApiModelProperty(value = "返回的数据")
    private Object data;


    public static  RestMessage success(String message, Object data){
        return build(message, data, "200");
    }

    public static  RestMessage fail(String message, Object data){
        return build(message, data, "500");
    }

    private static RestMessage build(String message, Object data, String code) {
        RestMessage restMessage = new RestMessage();
        restMessage.setCode(code);
        restMessage.setMessage(message);
        restMessage.setData(data);
        return restMessage;
    }
}
