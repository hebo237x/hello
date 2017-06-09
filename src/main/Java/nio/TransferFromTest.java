package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Created by zzqno on 2016-11-7.
 * 通道之间的数据传输
 */
public class TransferFromTest {

    public static void main(String[] args) {
        try {

            //FileChannel 将数据从源通道传输到FileChannel
            RandomAccessFile fromFile = new RandomAccessFile("d:/nio-data.txt", "rw");
            FileChannel fromChannel = fromFile.getChannel();

            RandomAccessFile toFile = new RandomAccessFile("d:/nio-data.txt", "rw");
            FileChannel toChannel = toFile.getChannel();

            long position = 0;
            long count = fromChannel.size();
            // position处开始向目标文件写入数据
            //count 表示最多传输的字节数
            toChannel.transferFrom(fromChannel, position, count);
            fromChannel.transferTo(position,count,toChannel);

        } catch (IOException e) {
            System.out.println();
        }




    }
}
