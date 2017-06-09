package nio.aiosocket;

import nio.aiosocket.client.Client;
import nio.aiosocket.server.Server;

import java.util.Scanner;

/**
 * Created by zzqno on 2017-4-17.
 */
public class AppMain {
    public static void main(String[] args) throws Exception {
        //运行服务器
        Server.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);
        //运行客户端
        Client.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while(Client.sendMsg(scanner.nextLine()));
    }
}
