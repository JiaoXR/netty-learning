# 模拟Dubbo服务端实现

## 1. 要点

注册中心：ZooKeeper  

版本：3.5.8

官网链接：https://zookeeper.apache.org/

## 2. 遇到问题

- KeeperException$UnimplementedException: KeeperErrorCode

详细信息：
```bash

org.apache.zookeeper.KeeperException$UnimplementedException: KeeperErrorCode = Unimplemented for /my-dubbo/com.jaxer.dubbo.api.service.HelloService

	at org.apache.zookeeper.KeeperException.create(KeeperException.java:106)
	at org.apache.zookeeper.KeeperException.create(KeeperException.java:54)
	at org.apache.zookeeper.ZooKeeper.create(ZooKeeper.java:1637)
	at org.apache.curator.framework.imps.CreateBuilderImpl$17.call(CreateBuilderImpl.java:1180)
	at org.apache.curator.framework.imps.CreateBuilderImpl$17.call(CreateBuilderImpl.java:1156)
	at org.apache.curator.connection.StandardConnectionHandlingPolicy.callWithRetry(StandardConnectionHandlingPolicy.java:67)
	at org.apache.curator.RetryLoop.callWithRetry(RetryLoop.java:81)
	at org.apache.curator.framework.imps.CreateBuilderImpl.pathInForeground(CreateBuilderImpl.java:1153)
	at org.apache.curator.framework.imps.CreateBuilderImpl.protectedPathInForeground(CreateBuilderImpl.java:607)
	at org.apache.curator.framework.imps.CreateBuilderImpl.forPath(CreateBuilderImpl.java:597)
	at org.apache.curator.framework.imps.CreateBuilderImpl.forPath(CreateBuilderImpl.java:51)
	at com.jaxer.dubbo.provider.registey.ZKRegistryCenter.register(ZKRegistryCenter.java:34)
	at com.jaxer.dubbo.provider.ZKTest.testRegister(ZKTest.java:20)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)
	at org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)
	at org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:413)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
Process finished with exit code 255
```

- 原因

ZooKeeper和Curator的版本兼容问题。

- 改后版本
    - ZooKeeper版本：3.5.8
    - Curator版本：2.13.0
