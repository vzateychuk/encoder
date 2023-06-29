package vez;

import vez.shared.Greeting;

public class Main {
    public static void main(String[] args) {
        String greeting = Greeting.getGreeting();
        System.out.println(greeting);
    }
}