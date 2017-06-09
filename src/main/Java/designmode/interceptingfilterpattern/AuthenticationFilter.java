package designmode.interceptingfilterpattern;

/**
 * Created by zzqno on 2017-3-30.
 * 安全验证过滤器
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void execute(String request) {
        System.out.println("Authenticating request: " + request);
    }
}
