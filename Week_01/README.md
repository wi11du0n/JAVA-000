# Week 01
## 课后作业

### 第一课第一题

代码见 Hello.java，运行以下命令

``` sh
cd Week_01
javac ./Hello1/Hello.java #编译
javap -c ./Hello1/Hello.class #查看字节码
```

字节码分析见 ./Hello1/Hello.bytecode

### 第一课第二题

参见 HelloClassLoader.java

运行结果
![](./images/HelloClassLoader.png)

### 第一课第三题

![JMM](./images/JMM.png)

### 第一课第四题

``` sh
[s@deployment-tce-6984cffb5b-7ddqf ~]$ jps -lmv
1 /opt/application/app.jar -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -Xloggc:/opt/logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=100M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/opt/logs/apperror.hprof -Xmx2800M -Xms2800M -Xmn700M -XX:SurvivorRatio=6 -XX:+UseConcMarkSweepGC -Dspring.profiles.active=prd
```

项目使用的是 CMS GC，新生代的比例是 1:1:6。运维已经根据 app 运行的情况设置了新生代 700M，老年代 2100M。

### 第二课
