package com.bjs.service;

import com.bjs.vo.TaskVo;

import java.io.InputStream;

public interface WorkService {

    //1、部署流程
    void deploy();

    //2、运行流程
    void runProcess(String processKey);

    //3、查询代理人的任务情况
    void queryTask(String assignee);

    //4、完成任务
    void completeTask(String taskId, TaskVo data);

    //5、查看流程的具体定义
    void queryProcessDefinition(String processDefinedKey);

    //6、查看流程实例的状态
    void queryProcessInstanceState();

    //7、删除流程定义
    void deleteProcessDefinition(String deploymentId);

    //8、查看历史流程实例信息
    void queryHistoryProcInst();

    //9、查询流程实例历史执行信息
    void queryHistoryTask(String processInstanceId);

    // 部署流程定义
    void deployProcessDefinition(InputStream inputStream, String trim, String trim1);

    // 删除流程实例
    void deleteProcessInstance(String processInstanceId, String deleteReason);
}
