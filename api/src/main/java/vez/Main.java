package vez;

import vez.internal.GreetingUtils;

public class Main {
    public static void main(String[] args) {
        String greeting = GreetingUtils.getHello();
        System.out.println(greeting);
    }
}