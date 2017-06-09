package designmode.interceptingfilterpattern;

/**
 * Created by zzqno on 2017-3-30.
 * 向target发送请求的对象
 */
public class Client {

    FilterManager filterManager;

    public void setFilterManager(FilterManager filterManager){
        this.filterManager = filterManager;
    }

    public void sendRequest(String request){
        filterManager.filterRequest(request);
    }
}
