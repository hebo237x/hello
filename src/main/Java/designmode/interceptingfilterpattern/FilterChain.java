package designmode.interceptingfilterpattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzqno on 2017-3-30.
 * 创建过滤器链
 */
public class FilterChain {

    private List<Filter> filters = new ArrayList<Filter>();
    private Target target;

    public void addFilter(Filter filter){
        filters.add(filter);
    }

    public void execute(String request){
        for (Filter filter : filters) {
            filter.execute(request);
        }
        target.execute(request);
    }

    public void setTarget(Target target){
        this.target = target;
    }
}
