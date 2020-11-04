# Week 03 课后作业

## 实现列表
1. （必做）整合你上次作业的 httpclient/okhttp；
2. （必做）实现过滤器。
3. （选做）实现路由。

``` text

src/
└── main
    └── java
        └── org
            └── example
                ├── NioGateway.java  //程序入口
                ├── filter
                │   ├── HttpLogRequestFilter.java   //请求 Filter 实例类，与下面接口对应
                │   ├── HttpLogResponseFilter.java   //响应 Filter 实例类，与下面接口对应
                │   ├── HttpRequestFilter.java
                │   └── HttpResponseFilter.java
                ├── inbound
                │   ├── HttpInboundHandler.java   //入站请求处理 Handler，依赖 HttpOutboundHandler
                │   └── HttpInboundServer.java   //Netty Server 配置和初始化
                ├── outbound
                │   └── HttpOutboundHandler.java   // OKHttp 代理实现，并出站响应
                └── router
                    ├── HttpEndpointRouter.java
                    └── RandomHttpEndpointRouter.java
```