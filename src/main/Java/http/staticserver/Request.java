package http.staticserver;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zzqno on 2016-9-27.
 */
public class Request {

    private InputStream input;
    private String uri;

    public Request(InputStream input) {
        this.input = input;
    }

    /**
     * 解析请求数据
     */
    public void parse() {
        // Read a set of characters from the socket  
        StringBuffer request = new StringBuffer(2018);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j = 0; j < i; j ++) {
            request.append((char)buffer[j]);
        }

        System.out.println();
        System.out.println("request.toString():");
        System.out.print(request.toString());
        System.out.println();

        uri = parseUri(request.toString());
    }

    /**
     * 去除空格
     * @param requestString
     * @return
     */
    private String parseUri(String requestString) {

        int index1, index2;

        index1 = requestString.indexOf(' ');

        if (index1 != -1) {

            index2 = requestString.indexOf(' ', index1 + 1);

            return requestString.substring(index1 + 1, index2);
        }

        return null;
    }

    public String getUri() {

        return uri;
    }
}