package com.example.springweek05.myproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestJdkProxy implements InvocationHandler {

    private Object targetObj;

    public TestJdkProxy(Object targetObj) {
        this.targetObj = targetObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before target method ...");
        Object object = method.invoke(targetObj, args);
        System.out.println("after target method ...");
        return object;
    }
}
