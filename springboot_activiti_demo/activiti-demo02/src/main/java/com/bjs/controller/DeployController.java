package com.bjs.controller;

import com.bjs.vo.RestMessage;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * @Description
 * @Date 2019-10-29 16:00:33
 * @Author BianJiashuai
 */
@RestController
@Api(description = "部署流程、删除流程")
public class DeployController {

    private final RepositoryService repositoryService;

    public DeployController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping(path = "deploy")
    @ApiOperation(value = "根据bpmnName部署流程", notes = "根据bpmnName部署流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bpmnName", value = "设计的流程图名称", dataType = "String", paramType = "query", example = "myProcess")
    })
    public RestMessage deploy(@RequestParam("bpmnName") String bpmnName) {

        RestMessage restMessage = new RestMessage();
        //创建一个部署对象
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name("请假流程");
        Deployment deployment = null;
        try {
            deployment = deploymentBuilder
                    .addClasspathResource("processes/" + bpmnName + ".bpmn")
                    .addClasspathResource("processes/" + bpmnName + ".png")
                    .deploy();
        } catch (Exception e) {
            restMessage = RestMessage.fail("部署失败", e.getMessage());
            e.printStackTrace();
        }

        return deploySuccess(restMessage, deployment);
    }

    @PostMapping(path = "deployZIP")
    @ApiOperation(value = "根据ZIP压缩包部署流程", notes = "根据ZIP压缩包部署流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zipName", value = "设计的流程图和图片的压缩包名称", dataType = "String", paramType = "query", example = "myProcess")
    })
    public RestMessage deployZIP(@RequestParam("zipName") String zipName) {
        RestMessage restMessage = new RestMessage();
        Deployment deployment = null;
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("processes/leaveProcess.zip");
            ZipInputStream zipInputStream = new ZipInputStream(in);
            deployment = repositoryService.createDeployment()
                    .name("请假流程2")
                    //指定zip格式的文件完成部署
                    .addZipInputStream(zipInputStream)
                    .deploy();//完成部署
            zipInputStream.close();
        } catch (Exception e) {
            restMessage = RestMessage.fail("部署失败", e.getMessage());
            // TODO 上线时删除
            e.printStackTrace();
        }

        return deploySuccess(restMessage, deployment);
    }

    @PostMapping(path = "deleteProcess")
    @ApiOperation(value = "根据部署ID删除流程", notes = "根据部署ID删除流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deploymentId", value = "部署ID", dataType = "String", paramType = "query", example = "")
    })
    public RestMessage deleteProcess(@RequestParam("deploymentId") String deploymentId) {
        RestMessage restMessage;
        /**不带级联的删除：只能删除没有启动的流程，如果流程启动，就会抛出异常*/
        try {
            repositoryService.deleteDeployment(deploymentId);
        } catch (Exception e) {
            restMessage = RestMessage.fail("删除失败", e.getMessage());
            // TODO 上线时删除
            e.printStackTrace();
        }

        /**级联删除：不管流程是否启动，都能可以删除（emmm大概是一锅端）*/
        repositoryService.deleteDeployment(deploymentId, true);
        restMessage = RestMessage.success("删除成功", null);
        return restMessage;
    }

    @GetMapping(path = "getAllProcesses")
    @ApiOperation(value = "获取所有流程实例", notes = "获取所有流程实例")
    public RestMessage getAllProcesses() {
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        final List<Map<String, String>> result = new ArrayList<>();
        final Map<String, String> data = new HashMap<>();
        if (deployments != null && deployments.size() > 0) {
           deployments.stream().forEach(deployment -> {
               data.put("deployID", deployment.getId());
               data.put("deployName", deployment.getName());
               data.put("deployKey", deployment.getKey());
               result.add(data);
           });
        }
        RestMessage restMessage = RestMessage.success("查询成功", result);
        return restMessage;
    }

    private RestMessage deploySuccess(RestMessage restMessage, Deployment deployment) {
        if (deployment != null) {
            Map<String, String> result = new HashMap<>(2);
            result.put("deployID", deployment.getId());
            result.put("deployName", deployment.getName());
            restMessage = RestMessage.success("部署成功", result);
        }
        return restMessage;
    }
}
