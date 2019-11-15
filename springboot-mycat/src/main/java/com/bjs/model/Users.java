package com.bjs.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Date 2019-11-09 14:28:34
 * @Author BianJiashuai
 */
@Data
@Accessors(chain = true)
@TableName("tb_users")
public class Users implements Serializable {

    private static final long serialVersionUID = 1289023024028512035L;

    private Long id;
    private String name;
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;
}
