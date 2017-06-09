package nio;

import java.nio.channels.FileChannel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/**
 * Created by monster_zzq on 2016/8/5.
 */
public class FileUtils {

    /**
     * 读取文件（NIO模式）
     * @param fileName
     * @param buffSize
     */
    public static void readFileByNio(String fileName,int buffSize){
        //第一步 获取通道
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel channel=null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("b://niotest.txt");
            fos = new FileOutputStream("b://test.txt");
            channel=fis.getChannel();
            outChannel = fos.getChannel();
            //文件内容的大小
            //   int size=(int) channel.size();
            //第二步 指定缓冲区
            ByteBuffer rBuffer= ByteBuffer.allocate(buffSize);
            ByteBuffer wBuffer = ByteBuffer.allocate(buffSize);
            //第三步 将通道中的数据读取到缓冲区中
            channel.read(rBuffer);
            fos.flush();
            //   outChannel.read(wBuffer);
            rBuffer.flip();
//		  / wBuffer.flip();
            byte[] bt = rBuffer.array();
            System.out.println(new String(bt,0,buffSize));
            rBuffer.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                channel.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    /**
     * 读写文件(NIO模式)
     * @param bufSize
     * @param filename
     * @param outFilename
     * @param isCopy 是否复制
     */
    public static void readFileByLine(int bufSize,String filename,String outFilename,boolean isCopy){
        FileChannel fcin =null;
        FileChannel fcout =null;
        //获取读取文件的缓冲区
        ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);
        //获取写入文件的缓冲区
        ByteBuffer wBuffer = ByteBuffer.allocate(bufSize);
        try {
            fcin = new RandomAccessFile(filename, "r").getChannel();
            fcout =new RandomAccessFile(outFilename, "rws").getChannel();
            String enterStr = "\n";
            byte[] bs = new byte[bufSize];
            StringBuffer strBuf = new StringBuffer("");
            while(fcin.read(rBuffer) != -1){
                //获取位置
                int rSize = rBuffer.position();
                //Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
                rBuffer.rewind();
                //从缓冲区读取数据
                rBuffer.get(bs);
                //清空緩衝區便于再次书写
                rBuffer.clear();
                String tempString = new String(bs, 0, rSize);
                System.out.println(tempString);
                int fromIndex = 0;
                int endIndex = 0;
                while((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1){
                    String line = tempString.substring(fromIndex, endIndex);
                    line = new String(strBuf.toString() + line);
                    //        System.out.print(line);
                    if(isCopy){
                        writeFileByLine(fcout, wBuffer, line+enterStr);
                    }
                    strBuf.delete(0, strBuf.length());
                    fromIndex = endIndex + 1;
                }
                if(rSize > tempString.length()){
                    strBuf.append(tempString.substring(fromIndex, tempString.length()));
                }else{
                    strBuf.append(tempString.substring(fromIndex, rSize));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 按行写入文件（Nio模式）
     * @param fcout
     * @param wBuffer
     * @param line
     */
    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line){
        try {
            fcout.write(wBuffer.wrap(line.getBytes()), fcout.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        //	readFileByLine(100, "d://niotest.txt", "d://test.txt",true);
        readFileByNio("b://niotest.txt",1024);
    }
}
