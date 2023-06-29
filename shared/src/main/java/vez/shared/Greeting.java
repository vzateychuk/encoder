package vez.shared;

import vez.internal.GreetingUtils;

public class Greeting {

    public static String getGreeting() {
        return new GreetingUtils().getHello();
    }
}