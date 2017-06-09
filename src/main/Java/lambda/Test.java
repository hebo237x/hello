package lambda;

import java.util.concurrent.Callable;

/**
 * Created by zzqno on 2016-10-7.
 */
public class Test {
    public static void main(String[] args) {
     /*   new Thread(() -> System.out.println("hello world")).start();
        //创建匿名函数
        BinaryOperator<Long> add = (x, y) -> x + y;
        //OR
        BinaryOperator<Long> addLn = (Long x, Long y) -> x + y;
        //OR
        BinaryOperator<Long> addlng = (Long x, Long y) -> {
            long l = x + y;
            return l;
        };
        long result = add.apply(1L, 2L);
        long result1 = addLn.apply(1L, 2L);
        long result2 = addlng.apply(1L, 2L);

        System.out.println(result + "," + result1 + "," + result2);
        //OR
        AddOperator addOperator = (x, y) -> x + y;
        System.out.println(addOperator.add(1, 2));

        HelloService helloService = message -> System.out.println("hello" + message);
        helloService.sayHello("java");*/
        User user = new User();
        Callable<Integer> getId = user::getId;
        try {
            System.out.println(getId.call());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    interface PersonFactory<P extends Test.Person> {
        P create(String firstName, String lastName);
    }


    class Person {
        String firstName;
        String lastName;

        Person() {
        }

        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }


    @FunctionalInterface
    interface HelloService {
        void sayHello(String message);
    }
}
