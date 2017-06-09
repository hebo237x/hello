package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zzqno on 2016-11-7.
 */
public class SelectorDemo {
    public static void main(String[] args) {
        //打开服务器套接字通道
        ServerSocketChannel ssc = null;
        try {
            ssc = ServerSocketChannel.open();
            //非阻塞模式
            ssc.configureBlocking(false);
            //获取与此通道关联的服务器套接字
            ServerSocket ss = ssc.socket();
            //服务绑定
            ss.bind(new InetSocketAddress(8990));
            //打开一个选择器
            Selector selector = Selector.open();
            SelectionKey registerKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    System.out.println("No Channel Is Ready !");
                    continue;
                }
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isAcceptable()) {
                        System.out.println("接收操作！");
                    } else if (key.isConnectable()) {
                        System.out.println("连接操作！");
                    } else if (key.isReadable()) {
                        System.out.println("读操作！");
                    } else if (key.isWritable()) {
                        System.out.println("写操作！");
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
