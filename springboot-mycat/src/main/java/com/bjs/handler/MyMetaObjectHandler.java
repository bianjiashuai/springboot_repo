package com.bjs.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description
 * @Date 2019-11-09 16:02:08
 * @Author BianJiashuai
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //TODO 应该先判断是否存在该字段
        System.out.println("新增...");
        setInsertFieldValByName("addTime", new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //TODO 应该先判断是否存在该字段
        System.out.println("更新...");
    }
}
