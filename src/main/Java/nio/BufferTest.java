package nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by zzqno on 2016-11-7.
 * Buffer 简单示例
 */
public class BufferTest {

    public static void main(String[] args) {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("d:/nio-data.txt", "rw");
            FileChannel inChannel = aFile.getChannel();
            //create buffer with capacity of 48 bytes
            ByteBuffer buffer = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buffer); //read into buffer.
            while (bytesRead != -1) {
                buffer.flip();  //make buffer ready for read
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get()); // read 1 byte at a time
                }
                buffer.clear(); //make buffer ready for writing
                bytesRead = inChannel.read(buffer);
            }
            aFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                aFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
