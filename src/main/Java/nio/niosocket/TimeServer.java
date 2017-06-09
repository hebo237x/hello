package nio.niosocket;

/**
 * Created by zzqno on 2017-4-13.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8088;
        MutiplexerTimeServer mutiplexerTimeServer = new MutiplexerTimeServer(port);
        new Thread(mutiplexerTimeServer,"MutiplexerTimeServer --001").start();
    }
}
