package com.bjs.oauth2.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {
    @Value("${server.port}")
    String port;


    @RequestMapping("demo.hi")
    public String home() {
        return "hi :" + ",i am from port:" + port;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")  //
    @RequestMapping("demo.hello")
    public String hello() {
        return "hello you!";
    }

    @Autowired
    DiscoveryClient client;

    /**
     * 获取所有服务的实例信息
     * @return
     */
    @RequestMapping(value = "service.instance.info.all", method = RequestMethod.GET)
    public Object getServiceInstanceInfo() {
        JSONArray msl = new JSONArray();
        List<String> services = client.getServices();
        for (String service : services) {
            List<ServiceInstance> sis = client.getInstances(service);
            JSONObject obj = new JSONObject();
            obj.put("name", service);
            obj.put("size", sis.size());
            boolean enable = false;
            JSONArray ins = new JSONArray();
            for (ServiceInstance s : sis) {
                EurekaDiscoveryClient.EurekaServiceInstance instance = (EurekaDiscoveryClient.EurekaServiceInstance) s;
                JSONObject sJson = new JSONObject();
                InstanceInfo instanceInfo = instance.getInstanceInfo();
                sJson.put("host", instance.getHost());
                sJson.put("port", instance.getPort());
                InstanceInfo.InstanceStatus status = instanceInfo.getStatus();
                if (!enable && InstanceInfo.InstanceStatus.UP.equals(status)) {
                    enable = true;
                }
                sJson.put("status", status);
                LeaseInfo leaseInfo = instanceInfo.getLeaseInfo();
                sJson.put("registrationTime", leaseInfo.getRegistrationTimestamp());
                sJson.put("renewalTime", leaseInfo.getRenewalTimestamp());
                sJson.put("serviceUpTime", leaseInfo.getServiceUpTimestamp());
                ins.add(sJson);
            }
            obj.put("details", ins);
            obj.put("enable", enable ? 1 : 0);
            msl.add(obj);
        }
        return msl;
    }
}
