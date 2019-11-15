package com.bjs.controller;

import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description
 * @Date 2019-10-31 10:15:37
 * @Author BianJiashuai
 */
@Slf4j
@RestController
public class ProcessDefinitionsController {

    @Autowired
    private ProcessRuntime processRuntime;

    @ApiOperation(value = "获取已部署流程定义", notes = "获取已部署流程定义")
    @GetMapping("/process-definitions")
    public List<ProcessDefinition> getProcessDefinitions() {
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        log.info("> Available Process definitions: " + processDefinitionPage.getTotalItems());

        List<ProcessDefinition> ProcessDefinitions = processDefinitionPage.getContent();
        for (ProcessDefinition pd : ProcessDefinitions) {
            log.info("\t > Process definition: " + pd);
        }

        return ProcessDefinitions;
    }


}
