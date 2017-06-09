package stream;



import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by zzqno on 2016-11-7.
 */
public class CreateStream {
    public static void main(String[] args) {
        Stream stream = Stream.of("a", "b", "v");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        IntStream.of(new int[]{1,2,3}).forEach(System.out::print);



    }
}
