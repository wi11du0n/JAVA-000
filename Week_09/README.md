# Week 09

## 周四

### 3.（必做）改造自定义 RPC 的程序，提交到 GitHub：

- 尝试将服务端写死查找接口实现类变成泛型和反射；

- 尝试将客户端动态代理改成 AOP，添加异常处理；
  - 异常

  ``` text
  req json: {"method":"findOrderById","params":[-1],"serviceClass":"io.kimmking.rpcfx.demo.api.OrderService"}
  Exception in thread "main" io.kimmking.rpcfx.exception.RpcfxServerException
	at io.kimmking.rpcfx.client.Rpcfx$RpcfxInvocationHandler.invoke(Rpcfx.java:67)
	at com.sun.proxy.$Proxy3.findOrderById(Unknown Source)
	at io.kimmking.rpcfx.demo.consumer.RpcfxClientApplication.main(RpcfxClientApplication.java:30)
  ```

- 尝试使用 Netty+HTTP 作为 client 端传输方式。

