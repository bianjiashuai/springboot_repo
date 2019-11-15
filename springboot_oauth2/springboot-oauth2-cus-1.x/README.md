基于springboot-1.5.9.RELEASE版本的OAuth2.0权限和业务完全分离的系统设计

* oauth-eureka: 注册中心
* oauth-server: 认证中心（权限集中管理）, 同时可以对接口uri进行配置控制
* server-demo: 依赖config-starter, 单纯的业务模块, 与传统springboot无任何差异
* server-hi: 无依赖config-starter, 配置进行自定义，测试使用
* my-oauth2-config-starter: 通用的ResourceServer和Oauth2Client配置

**整体系统支持集群和轮循的负载均衡策略**

**后续应该以service-demo的方式开发为主**
