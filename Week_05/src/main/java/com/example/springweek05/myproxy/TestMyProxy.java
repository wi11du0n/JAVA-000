package com.example.springweek05.myproxy;

import java.lang.reflect.Proxy;

public class TestMyProxy {
    public static void main(String[] args) {
        TestService targetService = new TestServiceImpl();
        TestJdkProxy jdkProxy = new TestJdkProxy(targetService);
        TestService targetProxyService =
                (TestService) Proxy.newProxyInstance(targetService.getClass().getClassLoader(),
                        targetService.getClass().getInterfaces(), jdkProxy);
        targetProxyService.doSomething();
    }
}
