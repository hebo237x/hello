package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.Selector;

/**
 * Created by zzqno on 2016-11-7.
 */
public class SelectorTest {

    public static void main(String[] args) {
        try {
            RandomAccessFile fromFile = new RandomAccessFile("d:/nio-data.txt", "rw");
            Selector selector = Selector.open();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
