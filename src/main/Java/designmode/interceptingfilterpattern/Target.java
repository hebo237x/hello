package designmode.interceptingfilterpattern;

/**
 * Created by zzqno on 2017-3-30.
 * 请求处理
 */
public class Target {

    public void execute(String request){
        System.out.println("Executing request: " + request);
    }
}
