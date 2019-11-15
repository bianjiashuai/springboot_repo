package com.bjs.controller;

import com.bjs.service.WorkService;
import com.bjs.vo.TaskVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Date 2019-10-28 17:56:49
 * @Author BianJiashuai
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    WorkService workService;


    @PostMapping(value = "/deployments", consumes = "multipart/form-data", produces = "application/json;charset=utf-8")
    public void deployProcessDefinition(@RequestParam("file") MultipartFile zipBpmFile,
                                        @RequestParam("deploymentName") String deploymentName,
                                        @RequestParam("tenantId") String tenantId, HttpServletResponse httpServletResponse){
        try {
            log.debug("deployProcessDefinition, deploymentName: {}, tenantId: {}.", deploymentName, tenantId);
            workService.deployProcessDefinition(zipBpmFile.getInputStream(), deploymentName.trim(), tenantId.trim());
        } catch (Exception e) {
            log.error("deployProcessDefinition failed.", e);
            httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

    }
    @GetMapping("/deploy")
    void deploy() {
        workService.deploy();
    }
    @PostMapping("/run")
    void runProcess(@RequestParam(value = "processKey", required = true) String processKey) {
        workService.runProcess(processKey);
    }

    @GetMapping("/query")
    void queryTask(@RequestParam("assginName")String assginName) {
        workService.queryTask(assginName);
    }

    @PostMapping("/complete")
    void completeTask(@RequestParam("taskId")String taskId, @RequestBody TaskVo taskVo) {
        workService.completeTask(taskId, taskVo);
    }

    @GetMapping("/queryProcess")
    void queryProcessDefination(@RequestParam("processDefikey")String  processDefikey){
        workService.queryProcessDefinition(processDefikey);
        workService.queryProcessInstanceState();

    }

    @DeleteMapping("/processDefi")
    void deleteProcessDefi(@RequestParam("deploymentId")String deploymentId){
        workService.deleteProcessDefinition(deploymentId);
    }

    @DeleteMapping("/processInstance")
    void deleteProcessInstance(@RequestParam("processInstanceId")String processInstanceId, @RequestParam("deleteReason") String deleteReason){
        workService.deleteProcessInstance(processInstanceId, deleteReason);
    }

}