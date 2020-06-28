# Netty实现简单RPC客户端

## 1. 主要内容

1. 创建被调用服务的代理对象

2. 将服务的调用信息封装后，发送给 RPC 服务端，并接收从服务端返回的调用结果

## 2. 相关组件

- ObjectEncoder

- ObjectDecoder
