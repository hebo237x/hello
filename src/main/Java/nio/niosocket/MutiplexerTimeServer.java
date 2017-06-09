package nio.niosocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zzqno on 2017-4-12.
 * 服务端
 * <p>
 *  NIO编程步骤
 * 1.打开ServerSocketChannel，监听客户端连接
 * 2.绑定监听端口，设置连接为非阻塞模式
 * 3. 创建Reactor线程，创建多路复用器并启动线程
 * 4.将ServerSocketChannel注册到Reactor线程中的Selector上，监听ACCEPT事件
 * 5.Selector轮询准备就绪的key
 * 6.Selector监听到新的客户端接入，处理新的接入请求，完成TCP三次握手，简历物理链路
 * 7.设置客户端链路为非阻塞模式
 * 8.将新接入的客户端连接注册到Reactor线程的Selector上，监听读操作，读取客户端发送的网络消息
 * 9.异步读取客户端消息到缓冲区
 * 10.对Buffer编解码，处理半包消息，将解码成功的消息封装成Task
 * 11.将应答消息编码为Buffer，调用SocketChannel的write将消息异步发送给客户端
 */
public class MutiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    /**
     * 初始化多路复用器  绑定监听端口
     *
     * @param port
     */
    public MutiplexerTimeServer(int port) {
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverSocketChannel = ServerSocketChannel.open();
            //如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //绑定端口 backlog设为1024
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            //监听客户端连接请求
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println(" The time server is start in port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        //循环遍历选择器
        while (!stop) {
            try {
                //无论是否有读写事件发生，selector每隔1s被唤醒一次  
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        //若远程连接关闭会报异常
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //处理新接入的请求信息
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                try {
                    SocketChannel sc = serverSocketChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order:" + body);
                    String currTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    doWrite(socketChannel, currTime);
                } else if (readBytes < 0) {
                    key.cancel();
                    socketChannel.close();
                } else
                    ; //读到0字节忽略
            }
        }
    }


    private void doWrite(SocketChannel socketChannel, String response) throws IOException {
        if (response != null && response.trim().length() > 0) {
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}