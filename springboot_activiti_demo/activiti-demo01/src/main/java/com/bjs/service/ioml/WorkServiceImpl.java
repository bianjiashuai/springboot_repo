package com.bjs.service.ioml;

import com.bjs.service.WorkService;
import com.bjs.vo.TaskVo;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;

/**
 * @Description
 * @Date 2019-10-28 10:46:40
 * @Author BianJiashuai
 */
@Slf4j
@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    RepositoryService repositoryService; // 提供一系列管理流程部署和流程定义的API。

    @Autowired
    RuntimeService runtimeService; // 在流程运行时对流程实例进行管理与控制。

    @Autowired
    TaskService taskService; // 对流程任务进行管理，例如任务提醒、任务完成和创建任务等。

    @Autowired
    IdentityService identityService; // 提供对流程角色数据进行管理的API，这些角色数据包括用户组、用户及它们之间的关系。

    @Autowired
    ManagementService managementService; // 提供对流程引擎进行管理和维护的服务。

    @Autowired
    HistoryService historyService; // 对流程的历史数据进行操作，包括查询、删除这些历史数据。

    @Resource
    private FormService formService; // 表单服务。

    @Override
    public void deploy() {
        Deployment deployment = repositoryService.createDeployment() // 创建流程构造器
                .addClasspathResource("processes/first.bpmn")   // 加载流程资源
                .name("工单流程")   // 设置流程名称
                .category("办公类别")   // 设置流程分类
                .deploy();  // 部署
        System.out.println("部署ID: " + deployment.getId());
        System.out.println("部署名称: " + deployment.getName());
    }

    @Override
    public void runProcess(String processKey) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processKey); // 获取流程实例
        System.out.println("流程实例ID: " + processInstance.getId());
        System.out.println("流程定义ID: " + processInstance.getProcessDefinitionId());
    }

    @Override
    public void queryTask(String assignee) {
        // 创建任务查询对象
        TaskQuery taskQuery = taskService.createTaskQuery();
        // 查看班里人的任务列表
        List<Task> list = taskQuery.taskAssignee(assignee).list();
        if (!list.isEmpty()) {
            list.forEach(task -> {
                System.out.println("任务办理人: " + task.getAssignee());
                System.out.println("任务ID: " + task.getId());
                System.out.println("任务名称: " + task.getName());
            });
        }

    }

    @Override
    public void completeTask(String taskId, TaskVo data) {
//        Map<String, Object> var = new HashMap<>();
//        var.put("approvers1", data.getApprovers1());
//        var.put("approvers2", data.getApprovers2());
//        var.put("comment", data.getComment());
//        var.put("result", data.getResult());
        taskService.complete(taskId);
        System.out.println("当前任务已执行完成");
    }

    @Override
    public void queryProcessDefinition(String processDefinedKey) {
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processDefinedKey)
                .latestVersion()
                .orderByProcessDefinitionVersion().desc()
                .list();

        if (!processDefinitionList.isEmpty()) {
            processDefinitionList.forEach(processDefinition -> {
                System.out.println("流程定义的ID: " + processDefinition.getId());
                System.out.println("流程定义的key: " + processDefinition.getKey());
                System.out.println("流程定义的版本: " + processDefinition.getVersion());
                System.out.println("流程定义部署的ID: " + processDefinition.getDeploymentId());
                System.out.println("流程定义的名称: " + processDefinition.getName());
            });
        }
    }

    @Override
    public void queryProcessInstanceState() {
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().list();
        if (!processInstanceList.isEmpty()) {
            processInstanceList.forEach(processInstance -> {
                System.out.println("当前正在运行的流程实例");
                System.out.println("该实例ID: " + processInstance.getProcessInstanceId());
                System.out.println("该实例名字: " + processInstance.getName());
                System.out.println("该实例对象ID: " + processInstance.getId());
            });
        } else {

        }
        System.out.println("当前无流程实例");
    }

    @Override
    public void deleteProcessDefinition(String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
    }

    @Override
    public void queryHistoryProcInst() {
        List<HistoricProcessInstance> historicProcessInstanceList =
                historyService.createHistoricProcessInstanceQuery().list();

        if (!historicProcessInstanceList.isEmpty()) {
            historicProcessInstanceList.forEach(historicProcessInstance -> {
                System.out.println("历史流程实例ID: " + historicProcessInstance.getId());
                System.out.println("历史流程定义的ID: " + historicProcessInstance.getProcessDefinitionId());
                System.out.println("历史流程实例开始时间--结束时间: " +
                        historicProcessInstance.getStartTime() + "-->" + historicProcessInstance.getEndTime());
            });
        }
    }

    @Override
    public void queryHistoryTask(String processInstanceId) {
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();

        if (!historicTaskInstanceList.isEmpty()) {
            historicTaskInstanceList.forEach(historicTaskInstance -> {
                System.out.println("历史流程实例任务ID: " + historicTaskInstance.getId());
                System.out.println("历史流程定义的ID: " + historicTaskInstance.getProcessDefinitionId());
                System.out.println("历史流程实例任务名称: " + historicTaskInstance.getName());
                System.out.println("历史流程实例任务处理人: " + historicTaskInstance.getAssignee());
            });
        }
    }

    @Override
    public void deployProcessDefinition(InputStream inputStream, String trim, String trim1) {

    }

    @Override
    public void deleteProcessInstance(String processInstanceId, String deleteReason) {

    }
}
