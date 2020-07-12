# JDK NIO 小结

## 1. 缓冲区（Buffer）

### 1.1 分类

- ByteBuffer (最常用)
- CharBuffer
- ShortBuffer
- IntBuffer
- LongBuffer
- FloatBuffer
- DoubleBuffer

### 1.2 常用方法

Buffer 通过 allocate() 方法获取缓冲区

- allocate(): 分配内存（JVM内存）
- allocateDirect(): 分配直接内存

- put(): 写入Buffer
- get(): 从Buffer中读取

### 1.3 核心属性

- capacity：Buffer最大存储数据的容量
- limit: Buffer可操作数据的大小限制
- position: Buffer中正在操作的位置

大小关系：0 <= mark <= position <= limit <= capacity

### 1.4 直接缓冲区&非直接缓冲区

非直接缓冲区：allocate 方法，分配到 JVM 内存中

直接缓冲区：allocateDirect 方法，分配到堆外内存中

## 2. 通道（Channel）

用于连接源节点和目标节点，在 NIO 中负责 Buffer 中数据的传输，本身不存储数据，需要配合 Buffer 使用。

### 2.1 分类

- FileChannel
- SocketChannel
- ServerSocketChannel
- DatagramChannel

### 2.2 获取Channel

- 本地 IO：getChannel 方法
    - FileInputStream/FileOutputStream
    - RandomAccessFile
- 网络 IO：getChannel 方法
    - Socket
    - ServerSocket
    - DatagramSocket
- JDK 1.7 提供的方法：例如 java.nio.channels.FileChannel#open
    - open 方法
    - newByteChannel 方法

